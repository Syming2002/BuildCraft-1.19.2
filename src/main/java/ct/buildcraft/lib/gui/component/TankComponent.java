package ct.buildcraft.lib.gui.component;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;

import ct.buildcraft.lib.client.render.fluid.FluidRenderer;
import ct.buildcraft.lib.gui.TankContainer;
import ct.buildcraft.lib.misc.LocaleUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;

public class TankComponent extends AbstractComponent{
	
	protected int typeCache;
	protected Fluid fluidCache;
	
	public TankComponent(int x, int y, int sx, int sy) {
		super(x, y, sx, sy);
	}
	
	@Override
	public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) {
		int leftpos = screen.getGuiLeft();
		int toppos = screen.getGuiTop();
		int type = data.get(offset);
		if(type != typeCache)  {
			typeCache = type;
			fluidCache = TankContainer.getFluid(type);
		}
		if(this.fluidCache != null)
			FluidRenderer.drawFluidForGui(this.fluidCache,leftpos+x,toppos+y+ys, leftpos+x+xs, toppos+y+ys-(ys*data.get(offset+1)/10000f), pose.last());
	}

	@Override
	public void renderTooltip(PoseStack pose, int x, int y) {
		if(super.isHovering(x-screen.getGuiLeft(), y-screen.getGuiTop()))
			screen.renderComponentTooltip(pose, getToolTip(fluidCache, data.get(offset+1)), x, y);
	}


	@Override
	public int getNeedDataSize() {
		return 2;
	}

	protected List<Component> getToolTip(Fluid fluid,int amount) {
        List<Component> toolTip = Lists.newArrayList();
        if (amount > 0) 
        	toolTip.add(fluid.getFluidType().getDescription());
        toolTip.add(Component.translatable(LocaleUtil.localizeFluidStaticAmount(amount, 10000)).withStyle(ChatFormatting.GRAY));//TODO
        return toolTip ;
    }
	
	

}
