/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.energy;

import ct.buildcraft.energy.client.gui.MenuEngineIron_BC8;
import ct.buildcraft.energy.client.gui.MenuEngineStone_BC8;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;

public class BCEnergyGuis {

	public static final RegistryObject<MenuType<MenuEngineStone_BC8>> MENU_STONE = BCEnergy.MENUS.register("engine_stone_menu", () -> new MenuType<>(MenuEngineStone_BC8::new));
	public static final RegistryObject<MenuType<MenuEngineIron_BC8>> MENU_IRON = BCEnergy.MENUS.register("engine_iron_menu", () -> new MenuType<>(MenuEngineIron_BC8::new));

    public static void openGUI(Player player, BlockPos pos) {
    	if(player instanceof ServerPlayer)
        NetworkHooks.openScreen((ServerPlayer)player, new SimpleMenuProvider(
        		  (containerId, playerInventory, player0) -> new MenuEngineStone_BC8(containerId, playerInventory), Component.translatable("menu.title.examplemod.mymenu"))
        		  ,pos);
    }
    
    public static void openGUI(Player player) {
    	if(player instanceof ServerPlayer)
            NetworkHooks.openScreen((ServerPlayer)player, new SimpleMenuProvider(
            		  (containerId, playerInventory, player0) -> new MenuEngineStone_BC8(containerId, playerInventory), Component.translatable("menu.title.examplemod.mymenu")));
    }
    
    static void init() {}

}
