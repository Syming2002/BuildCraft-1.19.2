package ct.buildcraft.core;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

import ct.buildcraft.core.client.model.ModelEngine;
import ct.buildcraft.core.client.render.RenderEngine_BC8;
import ct.buildcraft.energy.BCEnergyFluids;
import ct.buildcraft.energy.blockEntity.TileSpringOil;
import ct.buildcraft.lib.CreativeTabManager;
import ct.buildcraft.lib.CreativeTabManager.CreativeTabBC;
import ct.buildcraft.transport.BCTransportModels;
import ct.buildcraft.transport.client.model.PipeBaseModelGenStandard;
import com.mojang.logging.LogUtils;
import ct.buildcraft.api.core.BCLog;
import ct.buildcraft.api.enums.EnumSpring;
import ct.buildcraft.api.items.FluidItemDrops;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent.BakingCompleted;
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(BCCore.MODID)
public class BCCore {
	public static final String MODID = "buildcraftcore";
	private static final Logger LOGGER = LogUtils.getLogger();
    public static final CreativeTabBC BUILDCRAFT_TAB = CreativeTabManager.createTab("buildcraft.core");
    
	
    public static final Map<String,Object> ENGINE_MAP = new HashMap<>();

    
    public BCCore() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);//DataGenerator

        // Register the Deferred Register to the mod event bus so blocks get registered
        // Register the Deferred Register to the mod event bus so items get registered
        BCCoreBlocks.registry(modEventBus);
        BCCoreItems.registry(modEventBus);

//        MessageContainer.registry();;
        // Register ourselves for server and other game events we are interested in
        BCCoreRecipes.init();
        BCCoreConfig.preInit();
        ModLoadingContext.get().registerConfig(Type.COMMON, BCCoreConfig.config);
        MinecraftForge.EVENT_BUS.register(this);
//        MessageManager.preint();
 //       MinecraftForge.EVENT_BUS.register(EntityBlockPump::new);
    }

    public void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
            event.includeServer(),
            new BCCoreRecipes(event.getGenerator())
        );
    }
    
    private void commonSetup(final FMLCommonSetupEvent event)
    {
//      EnumSpring.OIL.liquidBlock = BCEnergyFluids.crudeOil[0].getBlock().getDefaultState();
    	EnumSpring.OIL.liquidBlock = BCEnergyFluids.OIL_COOL_BLOCK.get().defaultBlockState();
    	EnumSpring.OIL.tileConstructor = TileSpringOil::new;
    	BCCoreConfig.reloadConfig(MODID);
        BUILDCRAFT_TAB.setItem(BCCoreItems.WRENCH.get());
        FluidItemDrops.item = BCCoreItems.FRAGILE_FLUID_SHARD.get();
/*    	ENGINE_MAP.put("redstone", BCCoreBlocks.ENGINE_REDSTONE_TILE.get());
    	ENGINE_MAP.put("creative", BCCoreBlocks.ENGINE_CREATIVE_TILE.get());*/
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    
    
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
    	public static final ResourceLocation TRUNK_LIGHT = new ResourceLocation("buildcraftcore:blocks/engine/trunk_light");
    	public static final ResourceLocation CHAMBER = new ResourceLocation("buildcraftlib:blocks/engine/chamber_base");
    	public static final ResourceLocation TRUNK = new ResourceLocation("buildcraftcore:blocks/engine/trunk");
    	public static final ResourceLocation ENGINE_MODEL = new ResourceLocation("buildcraftlib:block/engine_base");
    	
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent
        public static void registryRender(EntityRenderersEvent.RegisterRenderers e) {

/*        	e.registerBlockEntityRenderer(BCCoreBlocks.ENGINE_CREATIVE_TILE.get(), RenderEngine::new);
        	e.registerBlockEntityRenderer(BCCoreBlocks.ENGINE_REDSTONE_TILE.get(), RenderEngine::new);*/
        	e.registerBlockEntityRenderer(BCCoreBlocks.ENGINE_REDSTONE_TILE_BC8.get(), RenderEngine_BC8::new);
        	e.registerBlockEntityRenderer(BCCoreBlocks.ENGINE_CREATIVE_TILE_BC8.get(), RenderEngine_BC8::new);
        }
        @SubscribeEvent
        public static void registrtTexture(Pre e){
        	
        	LOGGER.info(e.getAtlas().location().getPath());
        	if("textures/atlas/blocks.png".equals(e.getAtlas().location().getPath())) {
        		e.addSprite(TRUNK_LIGHT);
        		e.addSprite(CHAMBER);
        	}
        }
        @SubscribeEvent
        public static void onModelBakePre(RegisterAdditional event) {
        	event.register(ENGINE_MODEL);
        }
        
        @SubscribeEvent
        public static void onModelBake(BakingCompleted event) {
        	ModelEngine.init(event.getModels().get(ENGINE_MODEL));
        	event.getModels().put(new ModelResourceLocation("buildcraftcore:engine#type=wood"), new ModelEngine(RenderEngine_BC8.REDSTONE_BACK, RenderEngine_BC8.REDSTONE_SIDE));
        	event.getModels().put(new ModelResourceLocation("buildcraftcore:engine#type=creative"), new ModelEngine(RenderEngine_BC8.CREATIVE_BACK, RenderEngine_BC8.CREATIVE_SIDE));
        	event.getModels().put(new ModelResourceLocation("buildcraftcore:engine#type=stone"), new ModelEngine(RenderEngine_BC8.STONE_BACK, RenderEngine_BC8.STONE_SIDE));
        	event.getModels().put(new ModelResourceLocation("buildcraftcore:engine#type=iron"), new ModelEngine(RenderEngine_BC8.IRON_BACK, RenderEngine_BC8.IRON_SIDE));
        	ModelEngine.release();
        }
        	
    }


}

