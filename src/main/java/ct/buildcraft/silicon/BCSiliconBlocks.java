package ct.buildcraft.silicon;

import ct.buildcraft.builders.BCBuilders;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BCSiliconBlocks {
	
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BCSilicon.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITYS = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BCSilicon.MODID);


    public static void registry(IEventBus b) {
    	BLOCKS.register(b);
    	BLOCK_ENTITYS.register(b);
    }
}
