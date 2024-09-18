/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.transport.block;

import ct.buildcraft.factory.blockEntity.TileMiningWell;
import ct.buildcraft.lib.block.BlockBCTile_Neptune;
import ct.buildcraft.lib.block.IBlockWithFacing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlockMiningWell extends BlockBCTile_Neptune implements IBlockWithFacing, EntityBlock {
    public BlockMiningWell(BlockBehaviour.Properties prop) {
        super(prop);
    }

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileMiningWell(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lev, BlockState state,
			BlockEntityType<T> bet) {
		return super.getTicker(lev, state, bet);
	}
	
	
}
