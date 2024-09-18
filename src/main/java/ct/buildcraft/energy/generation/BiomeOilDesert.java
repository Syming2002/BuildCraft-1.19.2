/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.energy.generation;


import ct.buildcraft.energy.BCEnergy;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;

public final class BiomeOilDesert {
    public static Biome INSTANCE ;
    public static final ResourceKey<Biome> BIOME = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(BCEnergy.MODID+"oil_desert"));
    static {
    	Biome desert = OverworldBiomes.desert();
    	BiomeGenerationSettings.Builder feature$builder = new BiomeGenerationSettings.Builder();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addFossilDecoration(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultSprings(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultOres(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultGrass(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDesertVegetation(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDesertExtraVegetation(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDesertExtraDecoration(biomegenerationsettings$builder);
        INSTANCE = new Biome.BiomeBuilder().downfall(desert.getDownfall()).generationSettings(biomegenerationsettings$builder.build())
        		.mobSpawnSettings(desert.getMobSettings()).precipitation(desert.getPrecipitation()).specialEffects(desert.getModifiedSpecialEffects())
        		.temperature(desert.getBaseTemperature()).build();
    	
    }

    public BiomeOilDesert() {
        super(new BiomeProperties("Desert Oil Field").setBaseHeight(0.125F).setHeightVariation(0.05F)
            .setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
        setRegistryName();
    }
    
}
