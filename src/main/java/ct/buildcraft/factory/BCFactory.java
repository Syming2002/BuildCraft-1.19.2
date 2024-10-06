package ct.buildcraft.factory;

import ct.buildcraft.BCFactorySprites;
import ct.buildcraft.factory.blockEntity.TileDistiller;
import ct.buildcraft.factory.blockEntity.TileTank;
import ct.buildcraft.factory.client.render.RenderDistiller;
import ct.buildcraft.factory.client.render.RenderHeatExchange;
import ct.buildcraft.factory.client.render.RenderMiningWell;
import ct.buildcraft.factory.client.render.RenderPump;
import ct.buildcraft.factory.client.render.RenderTank;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BCFactory.MODID)
public class BCFactory
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "buildcraftfactory";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace




    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
//    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
//    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public BCFactory()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
//        modEventBus.addListener(this::gatherData);//DataGenerator
        BCFactoryBlocks.preInit(modEventBus);
        BCFactoryItems.preInit(modEventBus);
        BCFactoryGuis.preInit(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        vaildID();
 //       MinecraftForge.EVENT_BUS.register(EntityBlockPump::new);
    }
    
    public void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
            event.includeServer(),
            new BCFactoryRecipesProvider(event.getGenerator())
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    
    @SuppressWarnings("unused")
	private void vaildID() {
    	int i0 = TileTank.NET_FLUID_DELTA;
    	int i1 = TileDistiller.NET_TANK_GAS_OUT;
    	int i2 = TileDistiller.NET_TANK_IN;
    	int i3 = TileDistiller.NET_TANK_LIQUID_OUT;
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
           
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        
        @SubscribeEvent
        public static void registryRender(EntityRenderersEvent.RegisterRenderers e) {
        	e.registerBlockEntityRenderer(BCFactoryBlocks.ENTITYBLOCKTANK.get(), RenderTank::new);
        	e.registerBlockEntityRenderer(BCFactoryBlocks.ENTITYBLOCKPUMP.get(), RenderPump::new);
//        	e.registerBlockEntityRenderer(BCFactoryBlocks.ENTITYBLOCKTUBE.get(), RenderTube::new);
        	e.registerBlockEntityRenderer(BCFactoryBlocks.ENTITYBLOCKMININGWELL.get(), RenderMiningWell::new);
        	e.registerBlockEntityRenderer(BCFactoryBlocks.ENTITYBLOCKDISTILLER.get(), RenderDistiller::new);
        	e.registerBlockEntityRenderer(BCFactoryBlocks.ENTITYBLOCKHEATEXCHANGE.get(), RenderHeatExchange::new);
        }
        
        @SubscribeEvent
        public static void registrtTexture(Pre e){
        	if("textures/atlas/blocks.png".equals(e.getAtlas().location().getPath())) {
        		BCFactorySprites.registrtTexture(e);
        	}
        }
    }

    

}
