/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.transport.item;

import ct.buildcraft.lib.item.ItemBC_Neptune;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemWire extends ItemBC_Neptune {
    public ItemWire(String id, Item.Properties p) {
        super(id, p);
    }
/*
    @Override
    public void addSubItems(CreativeModeTab tab, NonNullList<ItemStack> subItems) {
        for (int i = 0; i < 16; i++) {
            subItems.add(new ItemStack(this, 1, i));
        }
    }
/*
    @Override
    @OnlyIn(Dist.CLIENT)
    public void addModelVariants(TIntObjectHashMap<ModelResourceLocation> variants) {
        for (DyeColor color : DyeColor.values()) {
            addVariant(variants, color.getId(), color.getName());
        }
    }*/
/*
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return ColourUtil.getTextFullTooltipSpecial(DyeColor.byId(stack.getId())) + " " + super.getItemStackDisplayName(stack);
    }
/*
    @Override
    @OnlyIn(Dist.CLIENT)
    public FontRenderer getFontRenderer(ItemStack stack) {
        return SpecialColourFontRenderer.INSTANCE;
    }
    */
}
