package ct.buildcraft.energy.client.gui;

import ct.buildcraft.energy.BCEnergySprites;
import ct.buildcraft.lib.gui.ContainerScreenBase;
import ct.buildcraft.lib.gui.component.ProgressComponent;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ScreenEngineStone extends ContainerScreenBase<MenuEngineStone_BC8>{

	private static final ResourceLocation TEXTURE_BASE = BCEnergySprites.ENGINE_STONE_GUI;
	protected static final ProgressComponent prog = new ProgressComponent(81, 24, 14, 14, 176, 0, ProgressComponent.U_TO_D, TEXTURE_BASE);
	
	public ScreenEngineStone(MenuEngineStone_BC8 be, Inventory p_97742_, Component p_97743_) {
		super(be, p_97742_, p_97743_, 1, TEXTURE_BASE);
		add(prog, false);
		setup(menu.data);
	}

	@Override
	public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) {
	    this.renderBackground(pose);
	    super.render(pose, mouseX, mouseY, partialTick);
	    this.renderTooltip(pose, mouseX, mouseY);
	}
	
	


}
