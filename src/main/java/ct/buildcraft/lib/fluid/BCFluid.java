/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */
package ct.buildcraft.lib.fluid;


import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class BCFluid extends ForgeFlowingFluid {
	private int colour = 0xFFFFFFFF, light = 0xFF_FF_FF_FF, dark = 0xFF_FF_FF_FF;
    private boolean isFlammable = false;
    private int lightOpacity = 0;
    private MaterialColor mapColour = MaterialColor.COLOR_BLACK;
    private int heatLevel;//int heat
    private boolean heatable;
    private String blockName;
    
    protected BCFluid(Properties properties) {
		super(properties);
		
	}


    public Component getBareLocalizedName(FluidStack stack) {
        return super.getFluidType().getDescription(stack);
    }


	@Override
	public int getAmount(FluidState p_164509_) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean isSource(FluidState p_76140_) {
		// TODO Auto-generated method stub
		return false;
	}


	public void setMapColour(MaterialColor mapColour) {
        this.mapColour = mapColour;
    }

    public final MaterialColor getMapColour() {
        return this.mapColour;
    }

    public void setFlammable(boolean isFlammable) {
        this.isFlammable = isFlammable;
    }

    public final boolean isFlammable() {
        return isFlammable;
    }

    public void setLightOpacity(int lightOpacity) {
        this.lightOpacity = lightOpacity;
    }

    public final int getLightOpacity() {
        return lightOpacity;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockName() {
        return blockName;
    }

    public int getColor() {
        return colour;
    }

    public int getLightColour() {
        return light;
    }

    public int getDarkColour() {
        return dark;
    }

    public BCFluid setColour(int colour) {
        this.colour = colour;
        return this;
    }

    public BCFluid setColour(int light, int dark) {
        this.light = light;
        this.dark = dark;
        this.colour = 0xFF_FF_FF_FF;
        return this;
    }

    public BCFluid setHeat(int heat) {
        this.heatLevel = heat;
        return this;
    }

    public int getHeatValue() {
        return heatLevel;
    }

    public BCFluid setHeatable(boolean value) {
        heatable = value;
        return this;
    }

    public boolean isHeatable() {
        return heatable;
    }




    public static class Flowing extends BCFluid
    {
        public Flowing(Properties properties)
        {
            super(properties);
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends BCFluid
    {
        public Source(Properties properties)
        {
            super(properties);
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }




}
