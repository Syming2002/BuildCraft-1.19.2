/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.builders.client.render;

import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;

import ct.buildcraft.builders.tile.TileBuilder;
import ct.buildcraft.core.client.BuildCraftLaserManager;
import ct.buildcraft.lib.client.render.laser.LaserBoxRenderer;
import ct.buildcraft.lib.client.render.laser.LaserData_BC8;
import ct.buildcraft.lib.client.render.laser.LaserRenderer_BC8;
import ct.buildcraft.lib.misc.VecUtil;
import ct.buildcraft.lib.misc.data.Box;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class RenderBuilder implements BlockEntityRenderer<TileBuilder> {
    private static final double OFFSET = 0.1;

	protected final ItemRenderer itemRenderer; 
	
	public RenderBuilder(BlockEntityRendererProvider.Context bpc) {
		itemRenderer = bpc.getItemRenderer();
	}
    
    @Override
    public void render(@Nonnull TileBuilder tile, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int light, int overlay) {
//        Minecraft.getMinecraft().mcProfiler.startSection("bc");
//        Minecraft.getMinecraft().mcProfiler.startSection("builder");

		VertexConsumer bb = buffer.getBuffer(RenderType.cutout());
//		matrix.translate(0.5f, 0.5f, 0.5f);
		Matrix4f pose = matrix.last().pose();
		Matrix3f normal = matrix.last().normal();
//        Minecraft.getMinecraft().mcProfiler.startSection("box");
        Box box = tile.getBox();
        LaserBoxRenderer.renderLaserBoxDynamic(box, BuildCraftLaserManager.STRIPES_WRITE, pose, normal, bb, true);

//        Minecraft.getMinecraft().mcProfiler.endStartSection("path");

        List<BlockPos> path = tile.path;
        if (path != null) {
            BlockPos last = null;
            for (BlockPos p : path) {
                if (last != null) {
                    Vec3 from = Vec3.atLowerCornerOf(last).add(VecUtil.VEC_HALF);
                    Vec3 to = Vec3.atLowerCornerOf(p).add(VecUtil.VEC_HALF);
                    Vec3 one = offset(from, to);
                    Vec3 two = offset(to, from);
                    LaserData_BC8 data = new LaserData_BC8(BuildCraftLaserManager.STRIPES_WRITE_DIRECTION, one, two, 1 / 16.1);
                    LaserRenderer_BC8.renderLaserDynamic(pose, normal, data, bb);
                }
                last = p;
            }
        }

//        Minecraft.getMinecraft().mcProfiler.endSection();

        if (tile.getBuilder() != null) {
            RenderSnapshotBuilder.render(tile.getBuilder(), tile.getLevel(), tile.getBlockPos(), partialTicks, matrix, buffer, bb, itemRenderer);
        }

//        Minecraft.getMinecraft().mcProfiler.endSection();
//        Minecraft.getMinecraft().mcProfiler.endSection();
    }

    private static Vec3 offset(Vec3 from, Vec3 to) {
        Vec3 dir = to.subtract(from).normalize();
        return from.add(VecUtil.scale(dir, OFFSET));
    }

    @Override
    public boolean shouldRenderOffScreen(TileBuilder te) {
        return true;
    }
}
