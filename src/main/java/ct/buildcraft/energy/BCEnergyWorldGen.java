package ct.buildcraft.energy;

import ct.buildcraft.api.core.BCLog;
import ct.buildcraft.core.BCCoreConfig;
import ct.buildcraft.energy.generation.BiomeInitializer;
import ct.buildcraft.energy.generation.BiomeOilDesert;
import ct.buildcraft.energy.generation.BiomeOilOcean;
import ct.buildcraft.energy.generation.OilGenerator;

import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BCEnergy.MODID)
public class BCEnergyWorldGen {
	terrablender.api.ModifiedVanillaOverworldBuilder
    public static void init() {
    	ForgeRegistries.BIOMES.re
    	
        boolean log = OilGenerator.DEBUG_OILGEN_BASIC;
        if (BCEnergyConfig.enableOilOceanBiome) {
            BiomeDictionary.addTypes(
                BiomeOilOcean.INSTANCE,
                BiomeDictionary.Type.OCEAN
            );
            BCLog.logger.info("[energy.oilgen] Registered the ocean oil biome.");
        } else {
            BCLog.logger.info("[energy.oilgen] Not registering the ocean oil biome, as it has been disabled by the config file.");
        }
        if (BCEnergyConfig.enableOilDesertBiome) {
            BiomeDictionary.addTypes(
                BiomeOilDesert.INSTANCE,
                BiomeDictionary.Type.HOT,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.SANDY
            );
            BCLog.logger.info("[energy.oilgen] Registered the desert oil biome.");
        } else {
            BCLog.logger.info("[energy.oilgen] Not registering the desert oil biome, as it has been disabled by the config file.");
        }
        if (BCCoreConfig.worldGen) {
            if (BCEnergyConfig.enableOilGeneration) {
                MinecraftForge.EVENT_BUS.register(OilGenerator.class);
                BCLog.logger.info("[energy.oilgen] Registered the oil spout generator");
            } else {
                BCLog.logger.info("[energy.oilgen] Not registering the oil spout generator, as it has been disabled by the config file.");
            }
            if (BCEnergyConfig.enableOilOceanBiome || BCEnergyConfig.enableOilDesertBiome) {
                MinecraftForge.TERRAIN_GEN_BUS.register(new BiomeInitializer());
                BCLog.logger.info("[energy.oilgen] Registered the oil biome initiializer");
            } else {
                BCLog.logger.info("[energy.oilgen] Not registering the oil biome initiializer, as it has been disabled by the config file.");
            }
        } else {
            BCLog.logger.info("[energy.oilgen] Not registering any world-gen, as everything has been disabled by the config file.");
        }
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
    	ForgeRegistries.BIOMES.register(null, null);
        if (BCEnergyConfig.enableOilDesertBiome) {
            event.getRegistry().register(new BiomeOilOcean());
        }
        if (BCEnergyConfig.enableOilDesertBiome) {
            event.getRegistry().register(new BiomeOilDesert());
        }
    }
}
