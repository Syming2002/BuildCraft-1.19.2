/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package ct.buildcraft.core.blockEntity;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;

import ct.buildcraft.api.core.EnumPipePart;
import ct.buildcraft.api.tiles.ITileAreaProvider;
import ct.buildcraft.api.tiles.TilesAPI;
import ct.buildcraft.core.BCCoreBlocks;
import ct.buildcraft.core.marker.VolumeCache;
import ct.buildcraft.core.marker.VolumeConnection;
import ct.buildcraft.lib.marker.MarkerSubCache;
import ct.buildcraft.lib.misc.PositionUtil;
import ct.buildcraft.lib.misc.data.Box;
import ct.buildcraft.lib.misc.data.IdAllocator;
import ct.buildcraft.lib.tile.TileBC_Neptune;
import ct.buildcraft.lib.tile.TileMarker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

public class TileMarkerVolume extends TileMarker<VolumeConnection> implements ITileAreaProvider {
    public static final IdAllocator IDS = TileBC_Neptune.IDS.makeChild("marker_volume");
    public static final int NET_SIGNALS_ON = IDS.allocId("SIGNALS_ON");
    public static final int NET_SIGNALS_OFF = IDS.allocId("SIGNALS_OFF");

    private boolean showSignals = false;

    public TileMarkerVolume(BlockPos pos, BlockState state) {
    	super(BCCoreBlocks.MARKER_VOLUME_TILE_BC8.get(), pos, state);
        caps.addCapabilityInstance(TilesAPI.CAP_TILE_AREA_PROVIDER, this, EnumPipePart.VALUES);
    }

    @Override
    public IdAllocator getIdAllocator() {
        return IDS;
    }

    public boolean isShowingSignals() {
        return showSignals;
    }

    @Override
    public VolumeCache getCache() {
        return VolumeCache.INSTANCE;
    }

    @Override
    public boolean isActiveForRender() {
        return showSignals || getCurrentConnection() != null;
    }

    
    @Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
        nbt.putBoolean("showSignals", showSignals);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		showSignals = nbt.getBoolean("showSignals");
	}

    public void switchSignals() {
        if (!level.isClientSide) {
            showSignals = !showSignals;
            markChunkDirty();
            sendNetworkUpdate(showSignals ? NET_SIGNALS_ON : NET_SIGNALS_OFF);
        }
    }

    private void readNewSignalState(boolean shouldShow) {
        boolean before = isActiveForRender();
        showSignals = shouldShow;
        if (before != isActiveForRender()) {
            redrawBlock();
        }
    }

    @Override
    public void writePayload(int id, FriendlyByteBuf buffer, LogicalSide side) {
        super.writePayload(id, buffer, side);
        if (side == LogicalSide.SERVER) {
            if (id == NET_RENDER_DATA) {
                buffer.writeBoolean(showSignals);
            }
        }
    }

    @Override
    public void readPayload(int id, FriendlyByteBuf buffer, LogicalSide side, NetworkEvent.Context ctx) throws IOException {
        super.readPayload(id, buffer, side, ctx);
        if (side == LogicalSide.CLIENT) {
            if (id == NET_SIGNALS_ON) {
                readNewSignalState(true);
            } else if (id == NET_SIGNALS_OFF) {
                readNewSignalState(false);
            } else if (id == NET_RENDER_DATA) {
                readNewSignalState(buffer.readBoolean());
            }
        }
    }
    
    

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
    
    

/*	@Override
    @OnlyIn(Dist.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return BCCoreConfig.markerMaxDistance * 4 * BCCoreConfig.markerMaxDistance;//TODO getViewDistence()
    }*/

    public void onManualConnectionAttempt(Player player) {
        MarkerSubCache<VolumeConnection> cache = this.getLocalCache();
        for (BlockPos other : cache.getValidConnections(getBlockPos())) {
            cache.tryConnect(getBlockPos(), other);
        }
        VolumeConnection c = getCurrentConnection();
        if (c != null) {
            for (BlockPos corner : PositionUtil.getCorners(c.getBox().min(), c.getBox().max())) {
                if (!c.getMarkerPositions().contains(corner) && cache.hasLoadedOrUnloadedMarker(corner)) {
                    c.addMarker(corner);
                }
            }
        }
    }

    @Override
    public void onPlacedBy(LivingEntity placer, ItemStack stack) {
        super.onPlacedBy(placer, stack);
        // Check if we are the corner of an existing box
        MarkerSubCache<VolumeConnection> cache = this.getLocalCache();
        for (BlockPos other : cache.getValidConnections(getBlockPos())) {
            VolumeConnection c = cache.getConnection(other);
            if (c != null && c.getBox().isCorner(worldPosition)) {
                if (c.addMarker(worldPosition)) {
                    // In theory we can't be the corner for multiple boxes
                    break;
                }
            }
        }
    }

    @Override
    public void getDebugInfo(List<String> left, List<String> right, Direction side) {
        super.getDebugInfo(left, right, side);
        left.add("Min = " + min());
        left.add("Max = " + max());
        left.add("Signals = " + showSignals);
    }

    // ITileAreaProvider

    @Override
    public BlockPos min() {
        VolumeConnection connection = getCurrentConnection();
        return connection == null ? getBlockPos() : connection.getBox().min();
    }

    @Override
    public BlockPos max() {
        VolumeConnection connection = getCurrentConnection();
        return connection == null ? getBlockPos() : connection.getBox().max();
    }

    @Override
    public void removeFromWorld() {
        if (level.isClientSide) {
            return;
        }
        VolumeConnection connection = getCurrentConnection();
        if (connection != null) {
            // Copy the list over because the iterator doesn't like it if you change the connection while using it
            List<BlockPos> allPositions = ImmutableList.copyOf(connection.getMarkerPositions());
            for (BlockPos p : allPositions) {
                level.destroyBlock(p, true);
            }
        }
    }

    @Override
    public boolean isValidFromLocation(BlockPos pos) {
        VolumeConnection connection = getCurrentConnection();
        if (connection == null) {
            return false;
        }
        Box box = connection.getBox();
        if (box.contains(pos)) {
            return false;
        }
        for (BlockPos p : PositionUtil.getCorners(box.min(), box.max())) {
            if (PositionUtil.isNextTo(p, pos)) {
                return true;
            }
        }
        return false;
    }
}
