package ct.buildcraft.api.transport.pipe;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IPipeBehaviourRenderer<B extends PipeBehaviour> {
    void render(B behaviour, double x, double y, double z, float partialTicks, FriendlyByteBuf bb);
}
