package ct.buildcraft.core;

import java.util.EnumMap;
import java.util.function.Supplier;

import ct.buildcraft.api.enums.EnumEngineType;
import ct.buildcraft.api.enums.EnumSpring;
import ct.buildcraft.core.item.ItemEngine_BC8;
import ct.buildcraft.core.item.ItemFragileFluidContainer;
import ct.buildcraft.core.item.ItemPaintbrush_BC8;
import ct.buildcraft.core.item.ItemWrench;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
    public static final RegistryObject<ItemPaintbrush_BC8> PAINT_BRUSH = ITEMS.register("paintbrush/clean", () -> new ItemPaintbrush_BC8(null, new Item.Properties().tab(BCCore.BUILDCRAFT_TAB))); 
    public static final RegistryObject<ItemFragileFluidContainer> FRAGILE_FLUID_SHARD = ITEMS.register("fragile_fluid_shard", ItemFragileFluidContainer::new);
    public static final EnumMap<DyeColor, RegistryObject<ItemPaintbrush_BC8>> PAINT_BRUSHS = new EnumMap<>(DyeColor.class);
/*    public static final RegistryObject<BlockItem> ENGINE_RESTONE_ITEM = ITEMS.register("engine_redstone", () -> new BlockItem(BCCoreBlocks.ENGINE_RESTONE_BLOCK.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> ENGINE_CREATIVE_ITEM = ITEMS.register("engine_creative", () -> new BlockItem(BCCoreBlocks.ENGINE_CREATIVE_BLOCK.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    */
    public static final RegistryObject<BlockItem> ENGINE_RESTONE_ITEM_BC8 = ITEMS.register("engine_redstone", () -> new ItemEngine_BC8<EnumEngineType>(BCCoreBlocks.ENGINE_BC8.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB),EnumEngineType.WOOD));
    public static final RegistryObject<BlockItem> ENGINE_CREATIVE_ITEM_BC8 = ITEMS.register("engine_creative", () -> new ItemEngine_BC8<EnumEngineType>(BCCoreBlocks.ENGINE_BC8.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB),EnumEngineType.CREATIVE));
    
    private static final Supplier<BlockItem> springItem = () -> new BlockItem(BCCoreBlocks.SPRING.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)) 
    {   public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        if (this.allowedIn(tab)) {list.add(new ItemStack(this));}}};
    public static final RegistryObject<BlockItem> SPRING_WATER = ITEMS.register("spring_water", springItem);
    public static final RegistryObject<BlockItem> SPRING_OIL = ITEMS.register("spring_oil", springItem);

    public static final EnumMap<EnumEngineType, RegistryObject<BlockItem>> ENGINE_ITEM_MAP = new EnumMap<EnumEngineType, RegistryObject<BlockItem>>(EnumEngineType.class);
    public static final EnumMap<EnumSpring, RegistryObject<BlockItem>> SPRING_ITEM_MAP = new EnumMap<EnumSpring, RegistryObject<BlockItem>>(EnumSpring.class);

    static void registry(IEventBus m) {
        ITEMS.register(m);
        ENGINE_ITEM_MAP.put(EnumEngineType.WOOD, ENGINE_RESTONE_ITEM_BC8);
        ENGINE_ITEM_MAP.put(EnumEngineType.CREATIVE, ENGINE_CREATIVE_ITEM_BC8);
        SPRING_ITEM_MAP.put(EnumSpring.WATER, SPRING_WATER);
        SPRING_ITEM_MAP.put(EnumSpring.OIL, SPRING_OIL);
        for(DyeColor color : DyeColor.values()) 
        	creatBrush(color);
    }
    
    private static void creatBrush(DyeColor color) {
    	PAINT_BRUSHS.put(color, ITEMS.register("paintbrush/"+color.getName(), () -> new ItemPaintbrush_BC8(color, (new Item.Properties()).durability(64).tab(BCCore.BUILDCRAFT_TAB))));
    }
}
