/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.transport.client.render;

import ct.buildcraft.api.transport.pipe.IPipeFlowRenderer;
import ct.buildcraft.api.transport.pipe.PipeBehaviour;
import ct.buildcraft.api.transport.pipe.PipeFlow;
import ct.buildcraft.api.transport.pluggable.IPlugDynamicRenderer;
import ct.buildcraft.api.transport.pluggable.PipePluggable;
import ct.buildcraft.transport.BCTransportModels;
import ct.buildcraft.transport.client.PipeRegistryClient;
import ct.buildcraft.transport.client.model.ModelPipe;
import ct.buildcraft.transport.pipe.Pipe;
import ct.buildcraft.transport.tile.TilePipeHolder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer;



public class RenderPipeHolder implements BlockEntityRenderer<TilePipeHolder> {
	

	public static final Direction[] renderFacing = {Direction.UP,Direction.NORTH,Direction.WEST,Direction.SOUTH,Direction.EAST,Direction.DOWN};
	public static int[] CENTER_UV = {4,12,4,12};
	public static int[] EAST_UV = {0,8,4,16};
	public static int[] WEST_UV = {0,8,4,16};
	public static int[] SOUTH_UV = {0,8,4,16};
	public static int[] NORTH_UV = {0,8,4,16};
	public static int[] UP_UV = {4,12,0,4};
	public static int[] DOWN_UV = {4,12,12,16};
	
	
	private static BakedQuad faces;
	public RenderPipeHolder(BlockEntityRendererProvider.Context ctx) {
		VertexConsumer builder = new QuadBakingVertexConsumer.Buffered();
//		ctx.getBlockRenderDispatcher().getModelRenderer().
    }
	
	@Override
	public void render(TilePipeHolder pipe, float partialTicks, PoseStack matrix, MultiBufferSource buffer,
			int combinedLight, int combinedOverlay) {
		Pipe p = pipe.getPipe();
		PipeBehaviour b = p.getBehaviour();
		if(p == Pipe.EMPTY) return;
//		TextureAtlasSprite tex = p.definition.getTexture();
		matrix.pushPose();
//		
        Matrix4f matrix4f = matrix.last().pose();
        Matrix3f matrix3f = matrix.last().normal();
        float conSize = 0;
        VertexConsumer builder = buffer.getBuffer(RenderType.cutoutMipped());
/*        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(matrix.last(), builder, null, 
        		Minecraft.getInstance().getModelManager().getModel(BCTransportModels.BLOCKER), 1.0f, 1.0f, 1.0f, combinedLight, combinedOverlay, 
        		ModelData.EMPTY, RenderType.cutout());e
 /*         Minecraft.getInstance().getBlockRenderer().getModelRenderer().//tesselateBlock(
      tesselateWithoutAO(pipe.getLevel(), 
        		ModelPipe.INSTANCE, 
        		pipe.getBlockState(), pipe.getBlockPos(), matrix, builder, false, 
        		pipe.getLevel().random, combinedLight, combinedOverlay, ModelData.builder().with(ModelPipe.PipeTypeModelKey, pipe).build(), RenderType.cutout());
        
/*        renderModel(matrix.last(), builder, pipe.getBlockState(), ModelPipe.INSTANCE, 1.0f, 1.0f, 1.0f, combinedLight, combinedOverlay, 
        		ModelData.builder().with(ModelPipe.PipeTypeModelKey, pipe).build(), RenderType.cutout());*/
        
/*        matrix.translate(0.5f, 0.5f, 0.5f);
        for(Direction face : renderFacing) {
        	conSize = p.getConnectedDist(face);
        	if(conSize >0 ) {	
        		int[] UV = b.getTextureUVs(face);
        		for(int i = 0; i<4;i++) {
        			
//        			renderContected(tex, matrix4f, matrix3f, builder, combinedLight, combinedOverlay, UV, 0.25f+conSize);
//        			matrix.mulPose(Vector3f.YP.rotationDegrees(90));
        		}
        	}
        	else {
//        		matrix.translate(-0.5f, 0.5f, 0);
        		
//        		renderCenter(tex, matrix4f, matrix3f, builder, combinedLight, combinedOverlay);
        	}   	
//        	matrix.mulPose(getQuater(face));
        }*/

 //       var face = ModelPipe.INSTANCE.getQuads(null, null, pipe.getPipeWorld().random, ModelData.builder().with(ModelPipe.PipeTypeModelKey, pipe).build(), RenderType.cutout());
//        if(!face.isEmpty())
//        builder.putBulkData(matrix.last(), , 1f, 1f, 1f, combinedLight, combinedOverlay);
        renderContents(pipe, partialTicks, matrix, buffer, combinedLight, combinedOverlay);
        renderPluggables(pipe, conSize, matrix, buffer, combinedOverlay, combinedOverlay);
		matrix.popPose();
		
	}
	
	public static void renderSide(TextureAtlasSprite back,Matrix4f matrix4f, Matrix3f normalMatrix, VertexConsumer builder, int light, int overlay) {
		
	}
	
/*
	private static void renderCenter(TextureAtlasSprite back,Matrix4f matrix4f, Matrix3f normalMatrix, VertexConsumer builder, int light, int overlay) {
		float width = 0.5f;
		
        float minU = back.getU(4);
        float maxU = back.getU(12);
        float minV = back.getV(4);
        float maxV = back.getV(12);
        
        
        builder.vertex(matrix4f, -4/16f, 4/16f-0.001f, (width / 2)).color(1f, 1f, 1f,1f)
        	.uv(minU, minV)
        	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
        	.endVertex();

        builder.vertex(matrix4f, 4/16f, 4/16f-0.001f, (width / 2)).color(1f, 1f, 1f,1f)
        	.uv(maxU, minV)
        	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
        	.endVertex();

        builder.vertex(matrix4f, 4/16f, 4/16f -0.001f, (-width / 2)).color(1f, 1f, 1f,1f)
        	.uv(maxU, maxV)
        	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
        	.endVertex();

        builder.vertex(matrix4f, -4/16f, 4/16f-0.001f, (-width / 2)).color(1f, 1f, 1f,1f)
        	.uv(minU, maxV)
        	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
        	.endVertex();
        
        builder.vertex(matrix4f, 4/16f, 4/16f-0.001f, (width / 2)).color(0.8f, 0.8f, 0.8f,0.8f)
	    	.uv(minU, minV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(matrix4f, -4/16f, 4/16f-0.001f, (width / 2)).color(0.8f, 0.8f, 0.8f,0.8f)
	    	.uv(maxU, minV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(matrix4f, -4/16f, 4/16f -0.001f, (-width / 2)).color(0.8f, 0.8f, 0.8f,0.8f)
	    	.uv(maxU, maxV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(matrix4f, 4/16f, 4/16f-0.001f, (-width / 2)).color(0.8f, 0.8f, 0.8f,0.8f)
	    	.uv(minU, maxV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();

	    
        if(faces == null) {
        	builder = new QuadBakingVertexConsumer((a) -> faces = a);
        builder.vertex(-4/16f, 4/16f-0.001f, (width / 2)).color(1f, 1f, 1f,1f)
        	.uv(minU, minV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(4/16f, 4/16f-0.001f, (width / 2)).color(1f, 1f, 1f,1f)
	    	.uv(maxU, minV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(4/16f, 4/16f -0.001f, (-width / 2)).color(1f, 1f, 1f,1f)
	    	.uv(maxU, maxV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(-4/16f, 4/16f-0.001f, (-width / 2)).color(1f, 1f, 1f,1f)
	    	.uv(minU, maxV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
        }
        
        
	}

	private static void renderContected(TextureAtlasSprite back,Matrix4f matrix4f, Matrix3f normalMatrix, VertexConsumer builder, int light, int overlay, int[] uv, float extendsion) {
		float width = 0.5f;
		
        float minU = back.getU(uv[0]);
        float maxU = back.getU(uv[1]);
        float minV = back.getV(uv[2]);
        float maxV = back.getV(uv[3]);
        
       
        builder.vertex(matrix4f, -4/16f, 4/16f, -(width / 2)).color(0.8f, 0.8f, 0.8f,0.8f)
	        .uv(minU, minV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(matrix4f, 4/16f, 4/16f, -(width / 2)).color(0.8f, 0.8f, 0.8f,0.8f)
	    	.uv(maxU, minV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(matrix4f, 4/16f, extendsion, (-width / 2)).color(0.8f, 0.8f, 0.8f,0.8f)
	    	.uv(maxU, maxV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(matrix4f, -4/16f, extendsion, (-width / 2)).color(0.8f, 0.8f, 0.8f,0.8f)
	    	.uv(minU, maxV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	    
        builder.vertex(matrix4f, 4/16f, 4/16f, -(width / 2)).color(1f, 1f, 1f,1f)
	        .uv(minU, minV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(matrix4f, -4/16f, 4/16f, -(width / 2)).color(1f, 1f, 1f,1f)
	    	.uv(maxU, minV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(matrix4f, -4/16f, extendsion, (-width / 2)).color(1f, 1f, 1f,1f)
	    	.uv(maxU, maxV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
	
	    builder.vertex(matrix4f, 4/16f, extendsion, (-width / 2)).color(1f, 1f, 1f,1f)
	    	.uv(minU, maxV)
	    	.overlayCoords(overlay).uv2(light).normal(normalMatrix, 0, 0, 1)
	    	.endVertex();
        
	}
	
	private static Quaternion getQuater(Direction facing) {
		switch (facing) {
		case UP: return Vector3f.XP.rotationDegrees(-90.0F);
		case NORTH : return Vector3f.ZP.rotationDegrees(90.0F);
		case WEST : return Vector3f.ZP.rotationDegrees(90.0F);
		case SOUTH : return Vector3f.ZP.rotationDegrees(90.0F);
		case EAST : return Vector3f.XP.rotationDegrees(-90.0F);
		case DOWN: return Vector3f.ZP.rotationDegrees(180.0F);
		default : 
			return Quaternion.ONE.copy();
		}
//		return Quaternion.ONE.copy();
	}*/
	
	
    private static void renderPluggables(TilePipeHolder pipe,  float partialTicks, PoseStack matrix, MultiBufferSource buffer,
			int combinedLight, int combinedOverlay) {
        for (Direction face : Direction.values()) {
            PipePluggable plug = pipe.getPluggable(face);
            if (plug == PipePluggable.EMPTY) {
                continue;
            }
            renderPlug(plug, partialTicks, matrix, buffer, combinedOverlay, combinedOverlay);
        }
    }

    private static <P extends PipePluggable> void renderPlug(P plug, float partialTicks, PoseStack matrix, MultiBufferSource buffer,
			int combinedLight, int combinedOverlay) {
        IPlugDynamicRenderer<P> renderer = PipeRegistryClient.getPlugRenderer(plug);
        if (renderer != null) {
            renderer.render(plug, partialTicks, matrix, buffer, combinedOverlay, combinedOverlay);
        }
    }

    private static void renderContents(TilePipeHolder pipe, float partialTicks, PoseStack matrix, MultiBufferSource buffer,
			int combinedLight, int combinedOverlay) {
        Pipe p = pipe.getPipe();
        if (p == null) {
            return;
        }
        if (p.flow != null) {
            renderFlow(p.flow,  partialTicks, matrix, buffer, combinedLight, combinedOverlay);
        }
/*        if (p.behaviour != null) {
            renderBehaviour(p.behaviour, x, y, z, partialTicks, bb);
        }*/
    }

    private static <F extends PipeFlow> void renderFlow(F flow, float partialTicks, PoseStack matrix, MultiBufferSource buffer,
			int combinedLight, int combinedOverlay) {
        IPipeFlowRenderer<F> renderer = PipeRegistryClient.getFlowRenderer(flow);
        if (renderer != null) {
            renderer.render(flow, partialTicks, matrix, buffer, combinedLight, combinedOverlay);
        }
    }
/*
    private static <B extends PipeBehaviour> void renderBehaviour(B behaviour, double x, double y, double z,
        float partialTicks, BufferBuilder bb) {
        IPipeBehaviourRenderer<B> renderer = PipeRegistryClient.getBehaviourRenderer(behaviour);
        if (renderer != null) {
            Minecraft.getMinecraft().mcProfiler.startSection(behaviour.getClass());
            renderer.render(behaviour, x, y, z, partialTicks, bb);
            Minecraft.getMinecraft().mcProfiler.endSection();
        }
    }

*/
}
