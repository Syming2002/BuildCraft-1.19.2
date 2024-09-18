package ct.buildcraft.energy.client.gui;

import java.util.List;

import ct.buildcraft.energy.BCEnergySprites;
import ct.buildcraft.lib.client.render.fluid.FluidRenderer;
import ct.buildcraft.lib.misc.FluidUtilBC;
import ct.buildcraft.lib.misc.LocaleUtil;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraftforge.fluids.FluidStack;

public class ScreenEngineIron extends AbstractContainerScreen<MenuEngineIron_BC8>{

	private static final ResourceLocation TEXTURE_BASE = BCEnergySprites.ENGINE_IRON_GUI;
	protected final ContainerData data;
	protected int fuelId;
	protected int coolantId;
	protected int residueId;
	protected FluidStack fuel;
	protected FluidStack coolant;
	protected FluidStack residue;
	
	public ScreenEngineIron(MenuEngineIron_BC8 be, Inventory p_97742_, Component p_97743_) {
		super(be, p_97742_, p_97743_);
		data = be.data;
		inventoryLabelY += 12;
	}

	@Override
	public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) {
	    this.renderBackground(pose);
	    super.render(pose, mouseX, mouseY, partialTick);
	    this.renderTooltip(pose, mouseX, mouseY);
	    int fuel = data.get(0);
	    int coolant = data.get(2);
	    int residue = data.get(4);
	    if(fuelId != data.get(1))  {fuelId = data.get(1);this.fuel = new FluidStack(Registry.FLUID.byId(fuelId), fuel);}
	    if(coolantId != data.get(3))  {coolantId = data.get(3);this.coolant = new FluidStack(Registry.FLUID.byId(coolantId), coolant);}
	    if(residueId != data.get(5))  {residueId = data.get(5);this.residue = new FluidStack(Registry.FLUID.byId(residueId), residue);}
	    if(fuel != 0)
	    	FluidRenderer.drawFluidForGui(this.fuel, leftPos+26, topPos+78, leftPos+42, topPos+78-(60*fuel/10000f), pose.last());
	    if(coolant != 0)
	    	FluidRenderer.drawFluidForGui(this.coolant, leftPos+80, topPos+78, leftPos+96, topPos+78-(60*coolant/10000f), pose.last());
	    if(residue != 0)
	    	FluidRenderer.drawFluidForGui(this.residue, leftPos+134, topPos+78, leftPos+150, topPos+78-(60*residue/10000f), pose.last());
	    RenderSystem.setShaderTexture(0, TEXTURE_BASE);
	    this.blit(pose, this.leftPos+26, this.topPos+18, 176, 0, 16, 60);
	    this.blit(pose, this.leftPos+80, this.topPos+18, 176, 0, 16, 60);
	    this.blit(pose, this.leftPos+134, this.topPos+18, 176, 0, 16, 60);
	    if(this.isHovering(26, 18, 16, 60, mouseX, mouseY)) this.renderComponentTooltip(pose, getToolTip(this.fuel, fuel), mouseX, mouseY);
	    if(this.isHovering(80, 18, 16, 60, mouseX, mouseY)) this.renderComponentTooltip(pose, getToolTip(this.coolant, coolant), mouseX, mouseY);
	    if(this.isHovering(134, 18, 16, 60, mouseX, mouseY)) this.renderComponentTooltip(pose, getToolTip(this.residue, residue), mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(PoseStack pose, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShaderTexture(0, TEXTURE_BASE);
		this.blit(pose, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight+10);
	}

    protected List<Component> getToolTip(FluidStack fluidStack,int amount) {
        List<Component> toolTip = Lists.newArrayList();
        if (amount > 0) 
        	toolTip.add(Component.translatable(fluidStack.getTranslationKey()));
        toolTip.add(Component.translatable(LocaleUtil.localizeFluidStaticAmount(amount, 10000)).withStyle(ChatFormatting.GRAY));
        return toolTip ;
    }

	@Override
	public boolean mouseClicked(double x, double y, int mouse) {
/*		FluidStack tank = null;
		if(this.isHovering(26, 18, 16, 60, x, y)) tank = fuel;
		else if(this.isHovering(80, 18, 16, 60, x, y)) tank = coolant;
		else if(this.isHovering(134, 18, 16, 60, x, y)) tank = residue;
		
		if(tank != null) {
			FluidUtil.
			FluidUtilBC.onTankActivated(minecraft.player, minecraft.player.blockPosition(), mouse == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, tank);
		}*/
		return super.mouseClicked(x, y, mouse);
	}
    
    
	


}
