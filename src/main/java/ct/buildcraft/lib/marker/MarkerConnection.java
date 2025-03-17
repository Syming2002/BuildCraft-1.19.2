/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.lib.marker;

import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import ct.buildcraft.lib.tile.TileMarker;

import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class MarkerConnection<C extends MarkerConnection<C>> {
    public final MarkerSubCache<C> subCache;

    public MarkerConnection(MarkerSubCache<C> subCache) {
        this.subCache = subCache;
    }

    /** Removes the specified marker from this connection. This should be called via
     * {@link MarkerSubCache#removeMarker(BlockPos)}. This may need to remove itself and split itself up (if the resulting
     * connection is invalid). */
    public abstract void removeMarker(BlockPos pos);

    public abstract Collection<BlockPos> getMarkerPositions();

    @OnlyIn(Dist.CLIENT)
    public abstract void renderInWorld(PoseStack pose, Matrix4f matrix);

    public void getDebugInfo(BlockPos caller, List<String> left) {
    	left.add("MarkerConnection:DEBUG TODO");
/*        Collection<BlockPos> positions = getMarkerPositions();
        List<BlockPos> list = new ArrayList<>(positions);
        if (positions instanceof Set) {
            Collections.sort(list);
        }
        for (BlockPos pos : list) {
            TileMarker<C> marker = subCache.getMarker(pos);
            String s = "  " + pos + " [";
            if (marker == null) {
                s += TextFormatting.RED + "U";
            } else {
                s += TextFormatting.GREEN + "L";
            }
            if (pos.equals(caller)) {
                s += TextFormatting.BLACK + "S";
            } else {
                s += TextFormatting.AQUA + "C";
            }
            s += getTypeInfo(pos, marker);
            s += TextFormatting.RESET + "]";
            left.add(s);
        }*/
    }

    protected String getTypeInfo(BlockPos pos, @Nullable TileMarker<C> value) {
        return "";
    }
}
