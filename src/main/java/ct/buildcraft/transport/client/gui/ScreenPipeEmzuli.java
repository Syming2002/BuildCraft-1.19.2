package ct.buildcraft.transport.client.gui;

import ct.buildcraft.core.BCCoreItems;
import ct.buildcraft.transport.BCTransportSprites;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ScreenPipeEmzuli extends AbstractContainerScreen<MenuPipeEmzuli>{

	private static final ResourceLocation TEXTURE_BASE = BCTransportSprites.EMZULI_GUI;
	
	private final EmzuliSwitch[] buttons = new EmzuliSwitch[4];
	
	public ScreenPipeEmzuli(MenuPipeEmzuli be, Inventory p_97742_, Component p_97743_) {
		super(be, p_97742_, p_97743_);
		titleLabelY -= 36;
		inventoryLabelY -= 34;
		
	}

	@Override
	public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) {
	    this.renderBackground(pose);
	    super.render(pose, mouseX, mouseY, partialTick);
	    for(EmzuliSwitch button : buttons)
	    	button.render(pose, mouseX, mouseY, partialTick);
	    this.renderTooltip(pose, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(PoseStack pose, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShaderTexture(0, TEXTURE_BASE);
		this.blit(pose, this.leftPos, this.topPos-36, 0, 0, this.imageWidth, this.imageHeight+52);
	}
	
	@Override
	protected void renderTooltip(PoseStack pose, int x, int y) {
		super.renderTooltip(pose, x, y);
	}

	@Override
	public boolean mouseClicked(double x, double y, int p_97750_) {
		for(var b : buttons)
			b.onPress();
		return super.mouseClicked(x, y, p_97750_);
	}
	
	@Override
	protected void init() {
		super.init();
		buttons[0] = new EmzuliSwitch(50,21,0,Component.empty());
		buttons[1] = new EmzuliSwitch(50,49,1,Component.empty());
		buttons[2] = new EmzuliSwitch(105,21,2,Component.empty());
		buttons[3] = new EmzuliSwitch(105,49,3,Component.empty());
		this.topPos = (this.height+76 - this.imageHeight) / 2;
	}
	
	



	@Override
	public boolean mouseReleased(double p_97812_, double p_97813_, int p_97814_) {
		for(var b : buttons){
			b.mousePress = false;
		}
		return super.mouseReleased(p_97812_, p_97813_, p_97814_);
	}





	class EmzuliSwitch extends AbstractButton{
		private final int index ;
		protected boolean mousePress = false;
		
		public EmzuliSwitch(int x, int y, int index, Component message) {
			super(leftPos + x, topPos + y, 18, 18, message);
			this.index = index;
		}

		@Override
		public void updateNarration(NarrationElementOutput n) {
			this.defaultButtonNarrationText(n);
		}

		@Override
		public void onPress() {
			if(!isHoveredOrFocused())
				return;
			mousePress = true;
			menu.clickMenuButton(null, index);
			minecraft.gameMode.handleInventoryButtonClick((menu).containerId, index);
		}
		
		@Override
		public void renderButton(PoseStack pose, int mouseX, int mouseY, float p_93679_) {
	//		super.renderButton(pose, mouseX, mouseY, p_93679_);
			RenderSystem.setShaderTexture(0, TEXTURE_BASE);
			int num = ScreenPipeEmzuli.this.menu.data.get(index);
			
			this.blit(pose, x, y, imageWidth, (mousePress ? 20 : 0), 20, 20);
			if(num == 128) {
				this.blit(pose, x+2, y+2, imageWidth, 40, 16, 16);
				return;
			}
			Item brush = BCCoreItems.PAINT_BRUSHS.get(DyeColor.byId(num));
			itemRenderer.renderGuiItem(new ItemStack(brush), x+2, y+2);
		}

		public void renderToolTip(PoseStack p_98016_, int p_98017_, int p_98018_) {
		}

	}
	
	
	
	


}
