package ct.buildcraft.energy;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;

import ct.buildcraft.api.core.BCLog;
import ct.buildcraft.energy.generation.BCOverWorldRegion;
import ct.buildcraft.energy.generation.BCSurfaceRuleData;
import ct.buildcraft.energy.generation.BiomeOilDesert;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(BCEnergy.MODID)
public class BCEnergy {
	public static final String MODID = "buildcraftenergy";
	static final Logger LOGGER = LogUtils.getLogger();
	
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    

    
    public BCEnergy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //TEST_CODE_START
        try {
//			test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        //TEST_CODE_END
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);
        BCEnergyFluids.registry(modEventBus);
        BCEnergyBlocks.init(modEventBus);
        BCEnergyGuis.init();
        BCEnergyBiomes.init(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BCEnergyClientProxy.class);
        // Register the Deferred Register to the mod event bus so blocks get registered
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        MENUS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in

 //       MinecraftForge.EVENT_BUS.register(EntityBlockPump::new);
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
    	BCEnergyFluids.init();
    	BCEnergyRecipes.init();
    	Regions.register(new BCOverWorldRegion(40));
    	SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, BCSurfaceRuleData.oilDesertRule());
    	LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
        
    }
    public void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
            event.includeServer(),
            new BCEnergyRecipes.BCEnergyRecipeProvider(event.getGenerator())
        );
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    
    private void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> list = new ArrayList<Pair<Climate.ParameterPoint, ResourceKey<Biome>>>();
        List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> target = new ArrayList<Pair<Climate.ParameterPoint, ResourceKey<Biome>>>();

		Constructor<?> constructor = OverworldBiomeBuilder.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		OverworldBiomeBuilder builder = (OverworldBiomeBuilder) constructor.newInstance();
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> con = list::add;
		
		for(Method method : OverworldBiomeBuilder.class.getDeclaredMethods()) {
			method.setAccessible(true);
			BCLog.logger.debug("energy_test:methodName:"+method.getName());
			if(method.getName().equals("addBiomes")) {
				method.invoke(builder, con);
			} 
		}
		BCLog.logger.debug("energy_test:methodName:successed");
		for(var pair : list) {
			if(pair.getSecond().equals(Biomes.DESERT)&&pair.getFirst().weirdness().max()>0) {
				target.add(pair);
				BCLog.logger.debug("energy_test:methodName:get desert biome for "+pair.getFirst());
			}
		}
		BCLog.logger.debug("energy_test:methodName:breakpoint");
		
    }







}
