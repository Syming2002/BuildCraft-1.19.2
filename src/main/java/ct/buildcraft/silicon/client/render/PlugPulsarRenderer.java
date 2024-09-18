/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.silicon.client.render;

import ct.buildcraft.api.transport.pluggable.IPlugDynamicRenderer;
import ct.buildcraft.lib.client.model.MutableQuad;
import com.mojang.blaze3d.vertex.BufferBuilder;

import buildcraft.lib.client.model.AdvModelCache;
import buildcraft.silicon.BCSiliconModels;
import buildcraft.silicon.plug.PluggablePulsar;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum PlugPulsarRenderer implements IPlugDynamicRenderer<PluggablePulsar> {
    INSTANCE;

    private static final AdvModelCache cache =
        new AdvModelCache(BCSiliconModels.PULSAR_DYNAMIC, PluggablePulsar.MODEL_VAR_INFO);

    public static void onModelBake() {
        cache.reset();
    }

    
    @Override
    public void render(PluggablePulsar pulsar, double x, double y, double z, float partialTicks, BufferBuilder bb) {
        bb.setTranslation(x, y, z);
        if (pulsar.clientModelData.hasNoNodes()) {
            pulsar.clientModelData.setNodes(BCSiliconModels.PULSAR_DYNAMIC.createTickableNodes());
        }
        pulsar.setModelVariables(partialTicks);
        pulsar.clientModelData.refresh();
        for (MutableQuad q : cache.getCutoutQuads()) {
            q.render(bb);
        }
        bb.setTranslation(0, 0, 0);
    }
}
