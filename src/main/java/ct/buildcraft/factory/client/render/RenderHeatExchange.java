package ct.buildcraft.factory.client.render;

import ct.buildcraft.api.core.BCLog;
import ct.buildcraft.core.blockEntity.TileHeatExchange;
import ct.buildcraft.core.blockEntity.TileHeatExchange.ExchangeSectionEnd;
import ct.buildcraft.core.blockEntity.TileHeatExchange.ExchangeSectionStart;
import ct.buildcraft.lib.client.render.fluid.FluidRenderer;
import ct.buildcraft.lib.client.render.fluid.FluidSpriteType;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;

public class RenderHeatExchange implements BlockEntityRenderer<TileHeatExchange>{

    public RenderHeatExchange(BlockEntityRendererProvider.Context ctx) {
    }

	
	@Override
	public void render(TileHeatExchange tile, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int light, int overlay) {
		var section = tile.getSection();
		ExchangeSectionStart start = section instanceof ExchangeSectionStart ? (ExchangeSectionStart)section : null;
		ExchangeSectionEnd end = section instanceof ExchangeSectionEnd ? (ExchangeSectionEnd)section : null;
		if(end == null)
			return;
		var forRender = end.smoothedTankInput.getFluidForRender(partialTicks);
		if(forRender ==null)
			return;
		matrix.pushPose();
		var bb = buffer.getBuffer(RenderType.cutout());
		FluidStack fluid = forRender.fluid;
        int blocklight0 = light&0x0000F0;
        int skylight0 = light&0xF00000;
        int blocklight = fluid.getFluid().getFluidType().getLightLevel(fluid)<<4;
        blocklight = blocklight > blocklight0 ? blocklight : blocklight0;
        int combinedLight = (skylight0)+(blocklight);
        
        var min = new Vec3(0,0,0);
        var max = new Vec3(0.5,0.5,0.5);
        boolean[] sideRender = { true, true, true, true, true, true };
        
        BCLog.logger.debug("v");


        
        
        
        
        FluidRenderer.vertex.lighti(combinedLight);

        FluidRenderer.renderFluid(FluidSpriteType.STILL, fluid, forRender.amount, 2000, min, max,
            bb, matrix.last(), sideRender);
		
		matrix.popPose();
	}

}
