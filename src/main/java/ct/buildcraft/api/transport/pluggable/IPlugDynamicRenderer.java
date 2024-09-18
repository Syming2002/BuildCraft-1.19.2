package ct.buildcraft.api.transport.pluggable;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;

public interface IPlugDynamicRenderer<P extends PipePluggable> {
    void render(P plug, float partialTicks, PoseStack matrix, MultiBufferSource buffer,int combinedLight, int combinedOverlay);
}
