/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package ct.buildcraft.lib;

import ct.buildcraft.api.core.BCLog;
import ct.buildcraft.lib.client.model.ModelHolderRegistry;
import ct.buildcraft.lib.client.sprite.SpriteHolderRegistry;
import ct.buildcraft.lib.misc.FakePlayerProvider;
import ct.buildcraft.lib.misc.MessageUtil;
import ct.buildcraft.lib.net.cache.BuildCraftObjectCaches;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RegisterTextureAtlasSpriteLoadersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.NetworkEvent.ClientCustomPayloadLoginEvent;


public class BCLibEventDist {
	
	@Mod.EventBusSubscriber(modid = BCLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class Client {
		
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
        
        @SubscribeEvent
        public static void onTextureAtlasSpriteLoadersSetup(RegisterTextureAtlasSpriteLoadersEvent event)
        {
        }
		
	    @SubscribeEvent
	    @OnlyIn(Dist.CLIENT)
	    public static void textureStitchPre(TextureStitchEvent.Pre event) {
	    	if("textures/atlas/blocks.png".equals(event.getAtlas().location().getPath())) {
//	    		ReloadManager.INSTANCE.preReloadResources();
	    		SpriteHolderRegistry.onTextureStitchPre(event);
	    		ModelHolderRegistry.onTextureStitchPre(event);
	    	}
	    }
	/*
	    @SubscribeEvent(priority = EventPriority.LOWEST)
	    @OnlyIn(Dist.CLIENT)
	    public static void textureStitchPreLow(TextureStitchEvent.Pre event) {
	        FluidRenderer.onTextureStitchPre(event.getMap());
	    }*/

	    @SubscribeEvent
	    @OnlyIn(Dist.CLIENT)
	    public static void textureStitchPost(TextureStitchEvent.Post event) {
	        TextureAtlas map = event.getAtlas();
	        SpriteHolderRegistry.onTextureStitchPost();
	    }
	/*
	    @SubscribeEvent
	    @OnlyIn(Dist.CLIENT)
	    public static void modelBake(ModelBakeEvent event) {
	        SpriteHolderRegistry.exportTextureMap();
	        LaserRenderer_BC8.clearModels();
	        ModelHolderRegistry.onModelBake();
	        ModelVariableData.onModelBake();
	    }

	    @SubscribeEvent
	    @OnlyIn(Dist.CLIENT)
	    public static void renderWorldLast(RenderWorldLastEvent event) {
	        Minecraft mc = Minecraft.getInstance();
	        Player player = mc.player;
	        if (player == null) return;
	        float partialTicks = event.getPartialTicks();

//	        DetachedRenderer.INSTANCE.renderWorldLastEvent(player, partialTicks);
	    }
	*/
	}

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayer) {
            ServerPlayer playerMP = (ServerPlayer) entity;
            // Delay sending join messages to player as it makes it work when in single-player
 //           MessageUtil.doDelayedServer(() -> MarkerCache.onPlayerJoinWorld(playerMP));
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(LevelEvent.Unload event) {
//        MarkerCache.onWorldUnload(event.getLevel());
        if (event.getLevel() instanceof ServerLevel) {
            FakePlayerProvider.INSTANCE.unloadWorld((ServerLevel) event.getLevel());
        }
    }
/*
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onReloadFinish(EventBuildCraftReload.FinishLoad event) {
        // Note: when you need to add server-side listeners the client listeners need to be moved to BCLibProxy
        GuideManager.INSTANCE.onRegistryReload(event);
    }
*/
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onConnectToServer(ClientPlayerNetworkEvent.LoggingIn event) {
    	BCLog.logger.debug("ssss");
        BuildCraftObjectCaches.onClientJoinServer();
    }


    @SubscribeEvent
    public static void serverTick(ServerTickEvent event) {
        if (event.phase == Phase.END) {
//            BCAdvDebugging.INSTANCE.onServerPostTick();
            MessageUtil.postServerTick();
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void clientTick(ClientTickEvent event) {
        if (event.phase == Phase.END) {
            BuildCraftObjectCaches.onClientTick();
            MessageUtil.postClientTick();
 /*           Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (player != null && ItemDebugger.isShowDebugInfo(player)) {
                HitResult mouseOver = mc.hitResult;
                if (mouseOver != null) {
                    IDebuggable debuggable = ClientDebuggables.getDebuggableObject(mouseOver);
                    if (debuggable instanceof BlockEntity) {
                        BlockEntity tile = (BlockEntity) debuggable;
                        MessageManager.sendToServer(new MessageDebugRequest(tile.getBlockPos(), Direction.getNearest(mouseOver.getLocation().x, mouseOver.getLocation().y, mouseOver.getLocation().z)));
                    } else if (debuggable instanceof Entity) {
                        // TODO: Support entities!
                    }
                }
            }*/
        }
    }
}
