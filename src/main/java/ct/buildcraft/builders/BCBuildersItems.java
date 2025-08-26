package ct.buildcraft.builders;

import ct.buildcraft.builders.item.ItemFillerPlanner;
import ct.buildcraft.builders.item.ItemSchematicSingle;
import ct.buildcraft.builders.item.ItemSnapshot;
import ct.buildcraft.core.BCCore;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BCBuildersItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BCBuilders.MODID);
	
    public static final RegistryObject<ItemSnapshot> SNAPSHOT = ITEMS.register("snapshot", () -> new ItemSnapshot(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<ItemSchematicSingle> SCHEMATIC_SINGLE = ITEMS.register("schematic_single", () -> new ItemSchematicSingle(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB).stacksTo(1)));
    public static final RegistryObject<ItemFillerPlanner> FILLER_PLANNER = ITEMS.register("filler_planner", () -> new ItemFillerPlanner(new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));


    public static final RegistryObject<BlockItem> FILLER_BLOCK_ITEM = ITEMS.register("filler", () -> new BlockItem(BCBuildersBlocks.FILLER.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> BUILDER_BLOCK_ITEM = ITEMS.register("builder", () -> new BlockItem(BCBuildersBlocks.BUILDER.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> ARCHITECT_BLOCK_ITEM = ITEMS.register("architect", () -> new BlockItem(BCBuildersBlocks.ARCHITECT.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> LIBRARY_BLOCK_ITEM = ITEMS.register("library", () -> new BlockItem(BCBuildersBlocks.LIBRARY.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> REPLACER_BLOCK_ITEM = ITEMS.register("replacer", () -> new BlockItem(BCBuildersBlocks.REPLACER.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> FRAME_BLOCK_ITEM = ITEMS.register("frame", () -> new BlockItem(BCBuildersBlocks.FRAME.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    public static final RegistryObject<BlockItem> QUARRY_BLOCK_ITEM = ITEMS.register("quarry", () -> new BlockItem(BCBuildersBlocks.QUARRY.get(),new Item.Properties().tab(BCCore.BUILDCRAFT_TAB)));
    
    
    

    public static void registry(IEventBus b) {
    	ITEMS.register(b);
    }
}
