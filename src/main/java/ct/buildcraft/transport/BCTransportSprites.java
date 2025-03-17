/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.transport;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Locale;

import ct.buildcraft.api.transport.pipe.PipeDefinition;
import ct.buildcraft.lib.client.sprite.SpriteHolderRegistry;
import ct.buildcraft.lib.client.sprite.SpriteHolderRegistry.SpriteHolder;
import ct.buildcraft.lib.misc.ColourUtil;
import ct.buildcraft.transport.client.model.PipeBaseModelGenStandard;
import ct.buildcraft.transport.client.render.PipeFlowRendererItems;
import ct.buildcraft.transport.pipe.behaviour.PipeBehaviourEmzuli.SlotIndex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.client.event.TextureStitchEvent;

public class BCTransportSprites {

	public static final LinkedHashMap<PipeDefinition,ResourceLocation> PIPE_TEX = new LinkedHashMap<>();
	public static TextureAtlasSprite[] TEX_SPRITES;
	
	public static final ResourceLocation STRUCTURE = new ResourceLocation("buildcrafttransport:pipes/structure");
	
	public static final ResourceLocation WOOD_ITEM = new ResourceLocation("buildcrafttransport:pipes/wood_item");
	public static final ResourceLocation WOOD_FLUID= new ResourceLocation("buildcrafttransport:pipes/wood_fluid");
	public static final ResourceLocation WOOD_POWER= new ResourceLocation("buildcrafttransport:pipes/wood_power");

	public static final ResourceLocation STONE_ITEM = new ResourceLocation("buildcrafttransport:pipes/stone_item");
	public static final ResourceLocation STONE_FLUID= new ResourceLocation("buildcrafttransport:pipes/stone_fluid");
	public static final ResourceLocation STONE_POWER= new ResourceLocation("buildcrafttransport:pipes/stone_power");

	public static final ResourceLocation COBBLE_ITEM = new ResourceLocation("buildcrafttransport:pipes/cobblestone_item");
	public static final ResourceLocation COBBLE_FLUID= new ResourceLocation("buildcrafttransport:pipes/cobblestone_fluid");
	public static final ResourceLocation COBBLE_POWER= new ResourceLocation("buildcrafttransport:pipes/cobblestone_power");

	public static final ResourceLocation QUARTZ_ITEM = new ResourceLocation("buildcrafttransport:pipes/quartz_item");
	public static final ResourceLocation QUARTZ_FLUID= new ResourceLocation("buildcrafttransport:pipes/quartz_fluid");
	public static final ResourceLocation QUARTZ_POWER= new ResourceLocation("buildcrafttransport:pipes/quartz_power");

	public static final ResourceLocation GOLD_ITEM = new ResourceLocation("buildcrafttransport:pipes/gold_item");
	public static final ResourceLocation GOLD_FLUID= new ResourceLocation("buildcrafttransport:pipes/gold_fluid");
	public static final ResourceLocation GOLD_POWER= new ResourceLocation("buildcrafttransport:pipes/gold_power");

	public static final ResourceLocation SANDSTONE_ITEM = new ResourceLocation("buildcrafttransport:pipes/sandstone_item");
	public static final ResourceLocation SANDSTONE_FLUID= new ResourceLocation("buildcrafttransport:pipes/sandstone_fluid");
	public static final ResourceLocation SANDSTONE_POWER= new ResourceLocation("buildcrafttransport:pipes/sandstone_power");

	public static final ResourceLocation IRON_ITEM = new ResourceLocation("buildcrafttransport:pipes/iron_item");
	public static final ResourceLocation IRON_FLUID= new ResourceLocation("buildcrafttransport:pipes/iron_fluid");
//	public static final ResourceLocation IRON_POWER= new ResourceLocation("buildcrafttransport:pipes/iron_power");

	public static final ResourceLocation DIAMOND_ITEM = new ResourceLocation("buildcrafttransport:pipes/diamond_item");
	public static final ResourceLocation DIAMOND_FLUID= new ResourceLocation("buildcrafttransport:pipes/diamond_fluid");
//	public static final ResourceLocation DIAMOND_POWER= new ResourceLocation("buildcrafttransport:pipes/diamond_power");

	public static final ResourceLocation DIAWOOD_ITEM = new ResourceLocation("buildcrafttransport:pipes/diamond_wood_item");
	public static final ResourceLocation DIAWOOD_FLUID= new ResourceLocation("buildcrafttransport:pipes/diamond_wood_fluid");

	public static final ResourceLocation CLAY_ITEM = new ResourceLocation("buildcrafttransport:pipes/clay_item");
	public static final ResourceLocation CLAY_FLUID= new ResourceLocation("buildcrafttransport:pipes/clay_fluid");
	
	public static final ResourceLocation VOID_ITEM = new ResourceLocation("buildcrafttransport:pipes/void_item");
	public static final ResourceLocation VOID_FLUID= new ResourceLocation("buildcrafttransport:pipes/void_fluid");
	
	public static final ResourceLocation OBSIDIAN_ITEM = new ResourceLocation("buildcrafttransport:pipes/obsidian_item");
	public static final ResourceLocation OBSIDIAN_FLUID= new ResourceLocation("buildcrafttransport:pipes/obsidian_fluid");
	
	public static final ResourceLocation LAPIS_ITEM = new ResourceLocation("buildcrafttransport:pipes/lapis_item");
	public static final ResourceLocation DIAZULI_ITEM = new ResourceLocation("buildcrafttransport:pipes/diazuli_item");
	public static final ResourceLocation EMZULI_ITEM = new ResourceLocation("buildcrafttransport:pipes/emzuli_item");
	public static final ResourceLocation STRIPES_ITEM = new ResourceLocation("buildcrafttransport:pipes/stripes_item");
	
	public static final ResourceLocation DIAWOOD_GUI = new ResourceLocation("buildcrafttransport:textures/gui/pipe_emerald.png");
	public static final ResourceLocation DIAWOOD_BUTTON_GUI = new ResourceLocation("buildcrafttransport:textures/gui/pipe_emerald_button.png");
	
	public static final ResourceLocation DIAMOND_GUI = new ResourceLocation("buildcrafttransport:textures/gui/filter.png");
//	public static final ResourceLocation DIAMOND_CB_GUI = new ResourceLocation("buildcrafttransport:textures/gui/filter_cb.png");
	
	public static final ResourceLocation FILTERED_BUFFER_GUI = new ResourceLocation("buildcrafttransport:textures/gui/filtered_buffer.png");
	public static final ResourceLocation FILTERED_BUFFER_NOTTHING_SLOT_GUI = new ResourceLocation("buildcrafttransport:gui/nothing_filtered_buffer_slot");
	public static final ResourceLocation FILTERED_BUFFER_EMPTY_SLOT_GUI = new ResourceLocation("buildcrafttransport:gui/empty_filtered_buffer_slot");
	
	public static final ResourceLocation EMZULI_GUI = new ResourceLocation("buildcrafttransport:textures/gui/pipe_emzuli.png");

	
	
//    public static final SpriteHolder EMPTY_FILTERED_BUFFER_SLOT;
//    public static final SpriteHolder NOTHING_FILTERED_BUFFER_SLOT;
    public static final SpriteHolder PIPE_COLOUR, COLOUR_ITEM_BOX;
    public static final SpriteHolder PIPE_COLOUR_BORDER_OUTER;
    public static final SpriteHolder PIPE_COLOUR_BORDER_INNER;

    public static final SpriteHolder TRIGGER_POWER_REQUESTED;
    public static final SpriteHolder TRIGGER_ITEMS_TRAVERSING;
    public static final SpriteHolder TRIGGER_FLUIDS_TRAVERSING;
    
    public static final SpriteHolder[] ACTION_PIPE_COLOUR;
    public static final EnumMap<SlotIndex, SpriteHolder> ACTION_EXTRACTION_PRESET;
    private static final EnumMap<DyeColor, SpriteHolder> PIPE_SIGNAL_ON;
    private static final EnumMap<DyeColor, SpriteHolder> PIPE_SIGNAL_OFF;
    private static final EnumMap<Direction, SpriteHolder> ACTION_PIPE_DIRECTION;

    public static final SpriteHolder POWER_FLOW;
    public static final SpriteHolder POWER_FLOW_OVERLOAD;
    
    static {
//        EMPTY_FILTERED_BUFFER_SLOT = getHolder("gui/empty_filtered_buffer_slot");
//        NOTHING_FILTERED_BUFFER_SLOT = getHolder("gui/nothing_filtered_buffer_slot");
        PIPE_COLOUR = getHolder("pipes/overlay_stained");
        COLOUR_ITEM_BOX = getHolder("pipes/colour_item_box");
        PIPE_COLOUR_BORDER_OUTER = getHolder("pipes/colour_border_outer");
        PIPE_COLOUR_BORDER_INNER = getHolder("pipes/colour_border_inner");

        ACTION_PIPE_COLOUR = new SpriteHolder[ColourUtil.COLOURS.length];
        for (DyeColor colour : ColourUtil.COLOURS) {
            ACTION_PIPE_COLOUR[colour.ordinal()] = getHolder("core", "items/paintbrush/" + colour.getName());
        }

        PIPE_SIGNAL_OFF = new EnumMap<>(DyeColor.class);
        PIPE_SIGNAL_ON = new EnumMap<>(DyeColor.class);

        for (DyeColor colour : ColourUtil.COLOURS) {
            String pre = "triggers/trigger_pipesignal_" + colour.getName().toLowerCase(Locale.ROOT) + "_";
            PIPE_SIGNAL_OFF.put(colour, getHolder(pre + "inactive"));
            PIPE_SIGNAL_ON.put(colour, getHolder(pre + "active"));
        }

        ACTION_EXTRACTION_PRESET = new EnumMap<>(SlotIndex.class);
        for (SlotIndex index : SlotIndex.VALUES) {
            ACTION_EXTRACTION_PRESET.put(index, getHolder("triggers/extraction_preset_" + index.colour.getName()));
        }

        ACTION_PIPE_DIRECTION = new EnumMap<>(Direction.class);
        for (Direction face : Direction.values()) {
            ACTION_PIPE_DIRECTION.put(face,
                getHolder("core", "triggers/trigger_dir_" + face.getName().toLowerCase(Locale.ROOT)));
        }

        POWER_FLOW = getHolder("pipes/power_flow");
        POWER_FLOW_OVERLOAD = getHolder("pipes/power_flow_overload");

        TRIGGER_POWER_REQUESTED = getHolder("transport", "triggers/trigger_pipecontents_requestsenergy");
        TRIGGER_ITEMS_TRAVERSING = getHolder("transport", "triggers/trigger_pipecontents_containsitems");
        TRIGGER_FLUIDS_TRAVERSING = getHolder("transport", "triggers/trigger_pipecontents_containsfluids");
    }
	
    private static SpriteHolder getHolder(String loc) {
        return SpriteHolderRegistry.getHolder("buildcrafttransport:" + loc);
    }

    private static SpriteHolder getHolder(String module, String loc) {
        return SpriteHolderRegistry.getHolder("buildcraft" + module + ":" + loc);
    }
    
	public static void init() {
/*		PIPE_TEX.put(BCTransportPipes.structure, STRUCTURE);
		PIPE_TEX.put(BCTransportPipes.woodItem, WOOD_ITEM);
		PIPE_TEX.put(BCTransportPipes.woodFluid, WOOD_FLUID);
		PIPE_TEX.put(BCTransportPipes.woodPower, WOOD_POWER);
		PIPE_TEX.put(BCTransportPipes.stoneItem, STONE_ITEM);
		PIPE_TEX.put(BCTransportPipes.stoneFluid, STONE_FLUID);
		PIPE_TEX.put(BCTransportPipes.stonePower, STONE_POWER);
		PIPE_TEX.put(BCTransportPipes.cobbleItem, COBBLE_ITEM);
		PIPE_TEX.put(BCTransportPipes.cobbleFluid, COBBLE_FLUID);
		PIPE_TEX.put(BCTransportPipes.cobblePower, COBBLE_POWER);
		PIPE_TEX.put(BCTransportPipes.quartzItem, QUARTZ_ITEM);
		PIPE_TEX.put(BCTransportPipes.quartzFluid, QUARTZ_FLUID);
		PIPE_TEX.put(BCTransportPipes.quartzPower, QUARTZ_POWER);
		PIPE_TEX.put(BCTransportPipes.goldItem, GOLD_ITEM);
		PIPE_TEX.put(BCTransportPipes.goldFluid, GOLD_FLUID);
		PIPE_TEX.put(BCTransportPipes.goldPower, GOLD_POWER);
		PIPE_TEX.put(BCTransportPipes.sandstoneItem, SANDSTONE_ITEM);
		PIPE_TEX.put(BCTransportPipes.sandstoneFluid, SANDSTONE_FLUID);
		PIPE_TEX.put(BCTransportPipes.sandstonePower, SANDSTONE_POWER);
		PIPE_TEX.put(BCTransportPipes.ironItem, IRON_ITEM);
		PIPE_TEX.put(BCTransportPipes.ironFluid, IRON_FLUID);
		PIPE_TEX.put(BCTransportPipes.diamondItem, DIAMOND_ITEM);
		PIPE_TEX.put(BCTransportPipes.diamondFluid, DIAMOND_FLUID);
		PIPE_TEX.put(BCTransportPipes.diaWoodItem, DIAWOOD_ITEM);
		PIPE_TEX.put(BCTransportPipes.diaWoodFluid, DIAWOOD_FLUID);
		PIPE_TEX.put(BCTransportPipes.clayItem, CLAY_ITEM);
		PIPE_TEX.put(BCTransportPipes.clayFluid, CLAY_FLUID);
		PIPE_TEX.put(BCTransportPipes.voidItem, VOID_ITEM);
		PIPE_TEX.put(BCTransportPipes.voidFluid, VOID_FLUID);
		PIPE_TEX.put(BCTransportPipes.obsidianItem, OBSIDIAN_ITEM);
		PIPE_TEX.put(BCTransportPipes.lapisItem, LAPIS_ITEM);
		PIPE_TEX.put(BCTransportPipes.daizuliItem, DIAZULI_ITEM);
		PIPE_TEX.put(BCTransportPipes.emzuliItem, EMZULI_ITEM);
		PIPE_TEX.put(BCTransportPipes.stripesItem, STRIPES_ITEM);*/
	}
	
    public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
    	if(InventoryMenu.BLOCK_ATLAS == (event.getAtlas().location())) {
//    		PIPE_TEX.values().forEach(event::addSprite);
    		PipeBaseModelGenStandard.INSTANCE.onTextureStitchPre(event);
/*    		event.addSprite(DIAWOOD_GUI);
    		event.addSprite(DIAWOOD_BUTTON_GUI);
    		event.addSprite(DIAMOND_GUI);
//    		event.addSprite(DIAMOND_CB_GUI);
    		event.addSprite(FILTERED_BUFFER_GUI);*/
    		event.addSprite(FILTERED_BUFFER_NOTTHING_SLOT_GUI);
    		event.addSprite(FILTERED_BUFFER_EMPTY_SLOT_GUI);
    		PipeFlowRendererItems.onModelBake();
    	}

    }
    
    public static SpriteHolder getPipeSignal(boolean active, DyeColor colour) {
        return (active ? PIPE_SIGNAL_ON : PIPE_SIGNAL_OFF).get(colour);
    }
    
    public static TextureAtlasSprite[] getTexArray() {
    	if(TEX_SPRITES == null) {
    		TEX_SPRITES = PIPE_TEX.values().stream().map(
    				Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
    				::apply).toList().toArray(new TextureAtlasSprite[1]);
    	}
    	return TEX_SPRITES;
    }

    public static SpriteHolder getPipeDirection(Direction face) {
        return ACTION_PIPE_DIRECTION.get(face);
    }

}
