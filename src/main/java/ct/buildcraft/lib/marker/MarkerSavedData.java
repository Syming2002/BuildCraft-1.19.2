/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.lib.marker;

import java.util.ArrayList;
import java.util.List;

import ct.buildcraft.api.core.BCLog;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.world.level.saveddata.SavedData;

public abstract class MarkerSavedData<S extends MarkerSubCache<C>, C extends MarkerConnection<C>> extends SavedData {
    protected static final boolean DEBUG_FULL = MarkerSubCache.DEBUG_FULL;
    
    public final String mapName;

    protected final List<BlockPos> markerPositions = new ArrayList<>();
    protected final List<List<BlockPos>> markerConnections = new ArrayList<>();
    private S subCache;
    
    public MarkerSavedData(String name) {
    	this.mapName = name;
	}
    
    public MarkerSavedData(CompoundTag nbt, String name) {
        mapName = name;
    	markerPositions.clear();
        markerConnections.clear();

        LongArrayTag positionList = (LongArrayTag) nbt.get("positions");
        int s = positionList.size();
        for (int i = 0; i < s; i++) {
            markerPositions.add(BlockPos.of(positionList.get(i).getAsLong()));
        }

        ListTag connectionList = (ListTag) nbt.get("connections");
        int s1 = connectionList.size();
        for (int i = 0; i < s1; i++) {
            positionList = (LongArrayTag) connectionList.get(i);
            List<BlockPos> inner = new ArrayList<>();
            markerConnections.add(inner);
            s = positionList.size();
            for (int j = 0; j < s; j++) {
                inner.add(BlockPos.of(positionList.get(j).getAsLong()));
            }
        }

        if (DEBUG_FULL) {
            BCLog.logger.info("[lib.marker.full] Reading from NBT (" + mapName + ")");
            BCLog.logger.info("[lib.marker.full]  - Positions:");
            for (BlockPos pos : markerPositions) {
                BCLog.logger.info("[lib.marker.full]   - " + pos);
            }
            BCLog.logger.info("[lib.marker.full]  - Connections:");
            for (List<BlockPos> list : markerConnections) {
                BCLog.logger.info("[lib.marker.full]   - Single Connection:");
                for (BlockPos pos : list) {
                    BCLog.logger.info("[lib.marker.full]     - " + pos);
                }
            }
        }
    }

	@Override
    public CompoundTag save(CompoundTag nbt) {
        markerPositions.clear();
        markerConnections.clear();

        markerPositions.addAll(subCache.getAllMarkers());
        for (C connection : subCache.getConnections()) {
            markerConnections.add(new ArrayList<>(connection.getMarkerPositions()));
        }

        int len = markerPositions.size();
        long[] positionList = new long[len];
        for(int i=0;i<len;i++)
        	positionList[i] = (markerPositions.get(i).asLong());
        nbt.put("positions", new LongArrayTag(positionList));

        ListTag connectionList = new ListTag();
        len = markerConnections.size();
        for (int counter=0;counter<len;counter++) {
        	List<BlockPos> connection = markerConnections.get(counter);
            int size = connection.size();
            long[] inner = new long[size];
            for(int j=0;j<size;j++) {
            	inner[j] = connection.get(j).asLong();
            }
            connectionList.addTag(counter, new LongArrayTag(inner));
        }
        nbt.put("connections", connectionList);

        if (DEBUG_FULL) {
            BCLog.logger.info("[lib.marker.full] Writing to NBT (" + mapName + ")");
            BCLog.logger.info("[lib.marker.full]  - Positions:");
            for (BlockPos pos : markerPositions) {
                BCLog.logger.info("[lib.marker.full]   - " + pos);
            }
            BCLog.logger.info("[lib.marker.full]  - Connections:");
            for (List<BlockPos> list : markerConnections) {
                BCLog.logger.info("[lib.marker.full]   - Single Connection:");
                for (BlockPos pos : list) {
                    BCLog.logger.info("[lib.marker.full]     - " + pos);
                }
            }
        }

        return nbt;
    }

    @Override
    public boolean isDirty() {
        return true;
    }

    public final void setCache(S subCache) {
        this.subCache = subCache;
    }
}
