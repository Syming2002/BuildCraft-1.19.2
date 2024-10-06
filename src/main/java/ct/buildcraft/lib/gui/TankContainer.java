package ct.buildcraft.lib.gui;

import ct.buildcraft.api.core.BCLog;
import ct.buildcraft.lib.fluid.Tank;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TankContainer implements ContainerData{
	
	@SuppressWarnings("deprecation")
	private static final Registry<Fluid> fluids = Registry.FLUID;
	protected final Tank[] tanks ;
	protected final int size;
	
	public TankContainer(Tank... tanks) {
		this.tanks = tanks;
		this.size = tanks.length;
	}

	@Override
	public int get(int p) {
		if(p >= 2*size||p<0) {
			BCLog.logger.error("TankContainer.get: %d index out of range : %d",p , size*2);
			return -1;
		}
		return (p&1)==0 ? fluids.getId(tanks[p/2].getFluidType()) : tanks[p/2].getFluidAmount();
	}

	@Override
	public void set(int index, int info) {
/*		if(playerId != 0xFFFF&&(index <size*2 && index >= 0&&(index&1)==0))
			tanks[index/2].onGuiClicked(playerId);*/
	}

	@Override
	public int getCount() {
		return size*2;
	}
	
	public FluidStack getFluidStack(int index) {
		return tanks[index].getFluidInTank(0);
	}
	
	
	public static Fluid getFluid(int index) {
		return fluids.byId(index);
	}
	
	public static FluidStack getFluidStack(int id, int amount) {
		return new FluidStack(fluids.byId(id), amount);
	}
	

}
