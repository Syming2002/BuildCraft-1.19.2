package ct.buildcraft.lib.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ContainerData;

public abstract class AbstractComponent implements ContainerComponent{

	protected AbstractContainerScreen<?> screen;
	protected ContainerData data ;
	
	protected int offset;
	
	protected boolean isPressing;
	
	protected int x;
	protected int y;
	protected int xs;//X Size
	protected int ys;//Y Size
	
	public AbstractComponent(int x, int y, int xs, int ys) {
		this.x = x;
		this.y = y;
		this.xs = xs;
		this.ys = ys;
	}
	
	
	@Override
	public boolean isHovering(int x, int y) {
		int dx = x-this.x;
		int dy = y-this.y;  
		return ((dx>0)&&(dx<xs))&&((dy>0)&&(dy<ys));
	}
	

	@Override
	public boolean onClick(double x, double y, int mouse) {
		if(isHovering((int)(x-screen.getGuiLeft()), (int)(y-screen.getGuiTop()))) {
			isPressing = true;
			return ClickedAction(y, y, mouse);
		}
		return false;
	}
	
	public boolean ClickedAction(double x, double y, int mouse) {
		Minecraft mc = screen.getMinecraft();
		mc.gameMode.handleInventoryButtonClick(screen.getMenu().containerId, offset);
		return true;
	}
	

	@Override
	public boolean mouseRelease(double x, double y, int mouse) {
		isPressing = false;
		return false;
	}


	@Override
	public void setup(AbstractContainerScreen<?> screen, ContainerData data) {
		this.screen = screen;
		this.data = data;
	}

	@Override
	public void onClose() {
		screen = null;
		data = null;
	}
	
	
    @Override
	public void setDataoffset(int offset) {
		this.offset = offset;
	}
	

	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getXsize() {
		return xs;
	}

	@Override
	public int getYsize() {
		return ys;
	}
	
	
	
}
