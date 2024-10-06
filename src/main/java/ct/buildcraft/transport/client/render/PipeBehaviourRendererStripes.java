/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.transport.client.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexConsumer;

import ct.buildcraft.api.transport.pipe.IPipeBehaviourRenderer;

import ct.buildcraft.lib.client.model.MutableQuad;

import ct.buildcraft.transport.BCTransportModels;
import ct.buildcraft.transport.pipe.behaviour.PipeBehaviourStripes;
import ct.buildcraft.transport.tile.TilePipeHolder;

@OnlyIn(Dist.CLIENT)
public enum PipeBehaviourRendererStripes implements IPipeBehaviourRenderer<PipeBehaviourStripes> {
    INSTANCE;

    @Override
    public void render(PipeBehaviourStripes stripes, float partialTicks, PoseStack matrix, VertexConsumer buffer,
			int combinedLight, int combinedOverlay) {
/*        Direction dir = stripes.direction;
        if (dir == null) return;
        MutableQuad[] quads = BCTransportModels.getStripesDynQuads(dir);
        int light = stripes.pipe.getHolder().getPipeWorld().getLightEngine().getRawBrightness(stripes.pipe.getHolder().getPipePos(), 0);//TODO
        Pose pose = matrix.last();
        for (MutableQuad q : quads) {
            q.multShade();
            q.lighti(light);
            q.render(pose.pose(), pose.normal(), (VertexConsumer) buffer);
        }*/
    }
}
