package ct.buildcraft.lib.gui;

import com.mojang.blaze3d.vertex.PoseStack;

public interface GuiIcon {
	public void render(PoseStack pose, float partialTick, int mouseX, int mouseY);
}
