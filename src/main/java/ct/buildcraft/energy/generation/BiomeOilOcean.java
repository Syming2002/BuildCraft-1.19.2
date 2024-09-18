/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.energy.generation;

import ct.buildcraft.energy.BCEnergy;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;

public final class BiomeOilOcean {
    public static final Biome INSTANCE = new BiomeBuilder()
    		.temperature(0.5f)
    		.downfall(0.5f)
    		.generationSettings(null);

    
    public BiomeOilOcean() {
        super(new BiomeProperties("Ocean Oil Field").setBaseHeight(-1.0F).setHeightVariation(0.1F));
        setRegistryName("oil_ocean");
    }
}
