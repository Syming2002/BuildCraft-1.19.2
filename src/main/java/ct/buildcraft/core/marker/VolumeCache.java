/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package ct.buildcraft.core.marker;

import ct.buildcraft.lib.marker.MarkerCache;
import net.minecraft.world.level.Level;

public class VolumeCache extends MarkerCache<VolumeSubCache> {
    public static final VolumeCache INSTANCE = new VolumeCache();

    private VolumeCache() {
        super("volume");
    }

    @Override
    protected VolumeSubCache createSubCache(Level world) {
        return new VolumeSubCache(world);
    }
}
