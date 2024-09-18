package ct.buildcraft.energy.client.gui;

import ct.buildcraft.energy.BCEnergySprites;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ScreenEngineStone extends AbstractContainerScreen<MenuEngineStone_BC8>{

	private static final ResourceLocation TEXTURE_BASE = BCEnergySprites.ENGINE_STONE_GUI;
	
	public ScreenEngineStone(MenuEngineStone_BC8 be, Inventory p_97742_, Component p_97743_) {
		super(be, p_97742_, p_97743_);
	}

	@Override
	public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) {
	    this.renderBackground(pose);
	    super.render(pose, mouseX, mouseY, partialTick);
	    this.renderTooltip(pose, mouseX, mouseY);
	    RenderSystem.setShaderTexture(0, TEXTURE_BASE);
		int pro = 14 - (int) Math.ceil(menu.getBurnProgress(partialTick)*14);
		this.blit(pose, this.leftPos+81, this.topPos+24+pro, 176, pro, 14, 14-pro);
	}
	
	@Override
	protected void renderBg(PoseStack pose, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShaderTexture(0, TEXTURE_BASE);
		this.blit(pose, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}
	
	@Override
	protected void renderTooltip(PoseStack p_97791_, int p_97792_, int p_97793_) {
		super.renderTooltip(p_97791_, p_97792_, p_97793_);
	}
	


}
