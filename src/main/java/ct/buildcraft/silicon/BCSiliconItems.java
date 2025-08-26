package ct.buildcraft.silicon;

import ct.buildcraft.builders.BCBuilders;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BCSiliconItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BCBuilders.MODID);

    public static void registry(IEventBus b) {
    	ITEMS.register(b);
    }
}
