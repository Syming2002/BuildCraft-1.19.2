package ct.buildcraft.energy.generation.features;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class OilGenFeature extends Feature<NoneFeatureConfiguration>{

	public OilGenFeature(Codec<NoneFeatureConfiguration> p_65786_) {
		super(p_65786_);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
		return false;
	}
	
	

}
