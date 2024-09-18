package ct.buildcraft.factory;

import ct.buildcraft.core.BCCore;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BCFactoryItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BCFactory.MODID);

    public static final RegistryObject<BlockItem> PUMP_BLOCK_ITEM = ITEMS.register("pump", () -> new BlockItem(BCFactoryBlocks.PUMP_BLOCK.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> TANK_BLOCK_ITEM = ITEMS.register("tank", () -> new BlockItem(BCFactoryBlocks.TANK_BLOCK.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> CHUTE_BLOCK_ITEM = ITEMS.register("chute", () -> new BlockItem(BCFactoryBlocks.CHUTE_BLOCK.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> FLOOD_GATE_BLOCK_ITEM = ITEMS.register("flood_gate", () -> new BlockItem(BCFactoryBlocks.FLOOD_GATE_BLOCK.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> MINING_WELL_BLOCK_ITEM = ITEMS.register("mining_well", () -> new BlockItem(BCFactoryBlocks.MINING_WELL_BLOCK.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> DISTILLER_BLOCK_ITEM = ITEMS.register("distiller", () -> new BlockItem(BCFactoryBlocks.DISTILLER_BLOCK.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> HEAT_EXCHANGE_BLOCK_ITEM = ITEMS.register("heat_exchange", () -> new BlockItem(BCFactoryBlocks.HEATEXCHANGE_BLOCK.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    
    
    static void preInit(IEventBus bus) {
    	ITEMS.register(bus);
    }
}
