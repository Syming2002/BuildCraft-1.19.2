/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package ct.buildcraft.core.blockEntity;

import ct.buildcraft.api.core.IPathProvider;
import ct.buildcraft.core.marker.PathCache;
import ct.buildcraft.core.marker.PathConnection;
import ct.buildcraft.lib.tile.TileMarker;
import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileMarkerPath extends TileMarker<PathConnection> implements IPathProvider {

    public TileMarkerPath(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
		// TODO Auto-generated constructor stub
	}

	@Override
    public ImmutableList<BlockPos> getPath() {
        PathConnection connection = getCurrentConnection();
        if (connection == null) {
            return ImmutableList.of();
        }
        return connection.getMarkerPositions();
    }

    @Override
    public void removeFromWorld() {
        for (BlockPos pos : getPath()) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public PathCache getCache() {
        return PathCache.INSTANCE;
    }

    @Override
    public boolean isActiveForRender() {
        PathConnection connection = getCurrentConnection();
        return connection != null;
    }

    public void reverseDirection() {
        if (level.isClientSide) {
            return;
        }
        PathConnection connection = getCurrentConnection();
        if (connection == null) {
            return;
        }
        connection.reverseDirection();
    }
}
