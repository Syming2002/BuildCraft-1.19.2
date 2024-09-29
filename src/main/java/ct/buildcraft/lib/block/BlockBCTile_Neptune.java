/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package ct.buildcraft.lib.block;

import ct.buildcraft.lib.tile.TileBC_Neptune;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BlockBCTile_Neptune extends BlockBCBase_Neptune implements EntityBlock {

	
    public BlockBCTile_Neptune(Properties material) {
        super(material);
    }
    
    public BlockBCTile_Neptune() {}

	@Override
	public void wasExploded(Level world, BlockPos pos, Explosion explosion) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileBC_Neptune) {
            TileBC_Neptune tileBC = (TileBC_Neptune) tile;
            tileBC.onExplode(explosion);
        }
        super.wasExploded(world, pos, explosion);
	}

    @Override
	public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof TileBC_Neptune) {
            TileBC_Neptune tileBC = (TileBC_Neptune) tile;
            tileBC.onRemove();
        }
		super.onBlockExploded(state, level, pos, explosion);
	}

	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileBC_Neptune) {
            TileBC_Neptune tileBC = (TileBC_Neptune) tile;
            tileBC.onRemove();
        }
        super.playerWillDestroy(world, pos, state, player);
	}

    @Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer,
			ItemStack stack) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileBC_Neptune) {
            TileBC_Neptune tileBC = (TileBC_Neptune) tile;
            tileBC.onPlacedBy(placer, stack);
            tileBC.onNeighbourBlockChanged(Blocks.AIR, pos);
        }
		super.setPlacedBy(world, pos, state, placer, stack);
	}



    @SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileBC_Neptune) {
            TileBC_Neptune tileBC = (TileBC_Neptune) tile;
            return tileBC.onActivated(player, hand, hit);
        }
		return super.use(state, world, pos, player, hand, hit);
	}


	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block,
			BlockPos fromPos, boolean p_60514_) {
		super.neighborChanged(state, level, pos, block, fromPos, p_60514_);
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof TileBC_Neptune) {
            TileBC_Neptune tileBC = (TileBC_Neptune) tile;
            tileBC.onNeighbourBlockChanged(block, fromPos);
        }
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> bet) {
		return (a,b,c,blockEntity) -> {
			if(blockEntity instanceof TileBC_Neptune tile) {
				tile.update();
			}
		};
	}
	
	
    
    
}
