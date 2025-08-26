package ct.buildcraft.core.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper;

public class RenderFragileItem extends BlockEntityWithoutLevelRenderer{
	
	public static final RenderFragileItem INSTANCE = new RenderFragileItem();
    // Depth offsets to prevent Z-fighting
    protected static final Transformation FLUID_TRANSFORM = new Transformation(Vector3f.ZERO, Quaternion.ONE, new Vector3f(1, 1, 1.002f), Quaternion.ONE);
    protected static final Transformation COVER_TRANSFORM = new Transformation(Vector3f.ZERO, Quaternion.ONE, new Vector3f(1, 1, 1.004f), Quaternion.ONE);
    protected static final SimpleModelState TRANSFORMED_STATE = new SimpleModelState(Transformation.identity(), false);
    protected static final TextureAtlasSprite COVER = null;
    

	protected RenderFragileItem() {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
	}

	@Override
	public void renderByItem(ItemStack stack, ItemTransforms.TransformType transform, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		// TODO Auto-generated method stub
		
		super.renderByItem(stack, transform, poseStack, bufferSource, packedLight, packedOverlay);
	}
	
	private void generateQuad() {
        var unbaked = UnbakedGeometryHelper.createUnbakedItemMaskElements(2, COVER); // Use cover as mask
        var quads = UnbakedGeometryHelper.bakeElements(unbaked, $ -> null, TRANSFORMED_STATE, null); // Bake with selected texture
	}

	
}
