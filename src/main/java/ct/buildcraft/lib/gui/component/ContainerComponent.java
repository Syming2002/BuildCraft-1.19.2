package ct.buildcraft.lib.gui.component;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ContainerData;

public interface ContainerComponent{
	
	public void render(PoseStack pose, int mouseX, int mouseY, float partialTick);
	
	public default boolean onClick(double x, double y, int mouse) {return false;}
	
	public default boolean isHovering(int x, int y) {return false;}
	
	public default void renderTooltip(PoseStack pose, int x, int y) {}
	
	
	
	public default int getX() {return 0;}
		
	public default int getY() {return 0;}
	
	public default int getXsize() {return 0;}
	
	public default int getYsize() {return 0;}
	
	
	
	public default void setup(AbstractContainerScreen<?> screen, ContainerData data) {}
	
	public default void setDataoffset(int offset) {}
	
	public default int getNeedDataSize() {return 0;}
	
	public default void onClose() {};
}
