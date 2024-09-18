/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.lib.marker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ct.buildcraft.api.core.BCDebugging;
import ct.buildcraft.api.core.BCLog;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public abstract class MarkerCache<S extends MarkerSubCache<?>> {
    public static final boolean DEBUG = BCDebugging.shouldDebugLog("lib.markers");
    public static final List<MarkerCache<?>> CACHES = new ArrayList<>();

    public final String name;

    private final Map<ResourceKey<Level>, S> cacheClient = new ConcurrentHashMap<>();
    private final Map<ResourceKey<Level>, S> cacheServer = new ConcurrentHashMap<>();

    public MarkerCache(String name) {
        this.name = name;
    }

    public static void registerCache(MarkerCache<?> cache) {
/*        if (Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            throw new IllegalStateException("Registered too late!");
        }*/
/*        ModContainer mod = FMLLoader..();
        if (mod == null) {
            throw new IllegalStateException("Tried to register a cache without an active mod!");
        }*/
        CACHES.add(cache);
        if (DEBUG) {
            BCLog.logger.info("[lib.markers] Registered a cache " + cache.name + " with an ID of " + (CACHES.size() - 1) + " from "/* + mod.getModId()*/);
        }
    }

    public static void postInit() {
        if (DEBUG) {
            BCLog.logger.info("[lib.markers] Sorted list of cache types:");
            for (int i = 0; i < CACHES.size(); i++) {
                final MarkerCache<?> cache = CACHES.get(i);
                BCLog.logger.info("  " + i + " = " + cache.name);
            }
            BCLog.logger.info("[lib.markers] Total of " + CACHES.size() + " cache types");
        }
    }

    public static void onPlayerJoinLevel(ServerPlayer player) {
        for (MarkerCache<?> cache : CACHES) {
            Level Level = player.getLevel();
            cache.getSubCache(Level).onPlayerJoinLevel(player);
        }
    }

    public static void onLevelUnload(Level level) {
        for (MarkerCache<?> cache : CACHES) {
            cache.onLevelUnloadImpl(level);
        }
    }

    private void onLevelUnloadImpl(Level level) {
        Map<ResourceKey<Level>, S> cache = level.isClientSide ? cacheClient : cacheServer;
        ResourceKey<Level> key = level.dimension();
        cache.remove(key);
    }

    protected abstract S createSubCache(Level level);

    public S getSubCache(Level level) {
        Map<ResourceKey<Level>, S> cache = level.isClientSide ? cacheClient : cacheServer;
        ResourceKey<Level> key = level.dimension();
        return cache.computeIfAbsent(key, k -> createSubCache(level));
    }
}
