package ct.buildcraft.energy.blockEntity;

import ct.buildcraft.core.blockEntity.TileEngineBase;
import ct.buildcraft.core.client.render.RenderEngine;
import ct.buildcraft.energy.BCEnergyBlocks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Deprecated
public class TileEngineIron extends TileEngineBase{

	public TileEngineIron(BlockPos p_155229_, BlockState bs) {
		super(BCEnergyBlocks.ENGINE_IRON_TILE_BC8.get(), p_155229_, bs);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public TextureAtlasSprite getTextureBack() {
		return RenderEngine.IRON_BACK;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public TextureAtlasSprite getTextureSide() {
		return RenderEngine.IRON_SIDE;
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public boolean isBurning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMaxPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float explosionRange() {
		// TODO Auto-generated method stub
		return 0;
	}


}
