package ct.buildcraft.core;

import java.util.EnumMap;
import ct.buildcraft.api.enums.EnumEngineType;
import ct.buildcraft.api.enums.EnumSpring;
import ct.buildcraft.core.item.ItemFragileFluidContainer;
import ct.buildcraft.core.item.ItemMarkerConnector;
import ct.buildcraft.core.item.ItemPaintbrush_BC8;
import ct.buildcraft.core.item.ItemVolumeBox;
import ct.buildcraft.core.item.ItemWrench;
import ct.buildcraft.lib.item.MultiBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BCCoreItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BCCore.MODID);
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", ItemWrench::new);
    public static final RegistryObject<Item> GEAR_WOOD = ITEMS.register("gears/gear_wood", () -> new Item(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB))); 
    public static final RegistryObject<Item> GEAR_STONE = ITEMS.register("gears/gear_stone", () -> new Item(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB))); 
    public static final RegistryObject<Item> GEAR_IRON = ITEMS.register("gears/gear_iron", () -> new Item(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB))); 
    public static final RegistryObject<Item> GEAR_GOLD = ITEMS.register("gears/gear_gold", () -> new Item(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB))); 
    public static final RegistryObject<Item> GEAR_DIAMOND = ITEMS.register("gears/gear_diamond", () -> new Item(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB))); 
    public static final RegistryObject<ItemPaintbrush_BC8> PAINT_BRUSH = ITEMS.register("paintbrush/clean", () -> new ItemPaintbrush_BC8(null, new Item.Properties().tab(BCCore.BUILDCRAFT_TAB), null));
    public static final RegistryObject<Item> MARKER_CONNECTOR = ITEMS.register("marker_connector", () -> new ItemMarkerConnector(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<Item> VOLUME_BOX = ITEMS.register("volume_box", () -> new ItemVolumeBox(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));

    public static final RegistryObject<ItemFragileFluidContainer> FRAGILE_FLUID_SHARD = ITEMS.register("fragile_fluid_shard", ItemFragileFluidContainer::new);
    
    
    
    public static final EnumMap<DyeColor, ItemPaintbrush_BC8> PAINT_BRUSHS = new EnumMap<>(DyeColor.class);
    public static final EnumMap<EnumEngineType, MultiBlockItem<EnumEngineType>> ENGINE_ITEM_MAP = new EnumMap<EnumEngineType, MultiBlockItem<EnumEngineType>>(EnumEngineType.class);
    public static final EnumMap<EnumSpring, MultiBlockItem<EnumSpring>> SPRING_ITEM_MAP = new EnumMap<EnumSpring, MultiBlockItem<EnumSpring>>(EnumSpring.class);
    
    public static final RegistryObject<MultiBlockItem<EnumEngineType>> ENGINE_RESTONE_ITEM_BC8 = ITEMS.register("engine_redstone", () -> new MultiBlockItem<EnumEngineType>(BCCoreBlocks.ENGINE_BC8.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB),EnumEngineType.WOOD, ENGINE_ITEM_MAP));
    public static final RegistryObject<MultiBlockItem<EnumEngineType>> ENGINE_CREATIVE_ITEM_BC8 = ITEMS.register("engine_creative", () -> new MultiBlockItem<EnumEngineType>(BCCoreBlocks.ENGINE_BC8.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB),EnumEngineType.CREATIVE, ENGINE_ITEM_MAP));
    
    public static final RegistryObject<MultiBlockItem<EnumSpring>> SPRING_WATER = ITEMS.register("spring_water", () -> new MultiBlockItem<EnumSpring>(BCCoreBlocks.SPRING.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB), EnumSpring.WATER, SPRING_ITEM_MAP));
    public static final RegistryObject<MultiBlockItem<EnumSpring>> SPRING_OIL = ITEMS.register("spring_oil", () -> new MultiBlockItem<EnumSpring>(BCCoreBlocks.SPRING.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB), EnumSpring.OIL, SPRING_ITEM_MAP));

    public static final RegistryObject<BlockItem> MARKER_PATH = ITEMS.register("marker_path", () -> new BlockItem(BCCoreBlocks.MARKER_PATH.get(), new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> MARKER_VOLUME = ITEMS.register("marker_volume", () -> new BlockItem(BCCoreBlocks.MARKER_VOLUME.get(), new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));

    static void registry(IEventBus m) {
        ITEMS.register(m);
        for(DyeColor color : DyeColor.values()) 
        	creatBrush(color);
    }
    
    private static void creatBrush(DyeColor color) {
    	ITEMS.register("paintbrush/"+color.getName(), () -> new ItemPaintbrush_BC8(color, (new Item.Properties()).durability(64).tab(BCCore.BUILDCRAFT_TAB), PAINT_BRUSHS));
//    	System.out.println("\"item.buildcraftcore."+"paintbrush/"+color.getName().replace('/', '.')+"\":\"\",");
    }
}
