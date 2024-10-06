package ct.buildcraft.api.transport.pipe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IPipeBehaviourRenderer<B extends PipeBehaviour> {
    void render(B behaviour, float partialTicks, PoseStack matrix, VertexConsumer buffer, int combinedLight, int combinedOverlay);
}
