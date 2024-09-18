/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package ct.buildcraft.transport;

import ct.buildcraft.transport.client.gui.MenuFilteredBuffer;
import ct.buildcraft.transport.client.gui.MenuPipeDiamond;
import ct.buildcraft.transport.client.gui.MenuPipeDiawood;
import ct.buildcraft.transport.client.gui.MenuPipeEmzuli;
import ct.buildcraft.transport.client.gui.ScreenFilteredBuffer;
import ct.buildcraft.transport.client.gui.ScreenPipeDiamond;
import ct.buildcraft.transport.client.gui.ScreenPipeDiawood;
import ct.buildcraft.transport.client.gui.ScreenPipeEmzuli;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class BCTransportGuis {
/*    FILTERED_BUFFER,
    PIPE_DIAMOND,
    ,
    PIPE_EMZULI;*/

    public static final RegistryObject<MenuType<MenuPipeDiawood>> MENU_PIPE_DIAMOND_WOOD = BCTransport.MENUS.register("pipe_diawood_menu", () -> new MenuType<>(MenuPipeDiawood::new));
    public static final RegistryObject<MenuType<MenuPipeDiamond>> MENU_PIPE_DIAMOND = BCTransport.MENUS.register("pipe_diamond_menu", () -> new MenuType<>(MenuPipeDiamond::new));
    public static final RegistryObject<MenuType<MenuFilteredBuffer>> MENU_FILTERED_BUFFER = BCTransport.MENUS.register("pipe_filtered_buffer", () -> new MenuType<>(MenuFilteredBuffer::new));
    public static final RegistryObject<MenuType<MenuPipeEmzuli>> MENU_PIPE_EMZULI = BCTransport.MENUS.register("pipe_emzuli_menu", () -> new MenuType<>(MenuPipeEmzuli::new));


    public static void clientInit(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> {
                	MenuScreens.register(MENU_PIPE_DIAMOND_WOOD.get(), ScreenPipeDiawood::new);
                	MenuScreens.register(MENU_PIPE_DIAMOND.get(), ScreenPipeDiamond::new);
                	MenuScreens.register(MENU_FILTERED_BUFFER.get(), ScreenFilteredBuffer::new);
                	MenuScreens.register(MENU_PIPE_EMZULI.get(), ScreenPipeEmzuli::new);
                }
        );
    }
    

    public void openGui(Player player) {
        openGui(player, 0, -1, 0);
    }

    public void openGui(Player player, BlockPos pos) {
        openGui(player, pos.getX(), pos.getY(), pos.getZ());
    }

    public void openGui(Player player, int x, int y, int z) {
//    	player.openMenu(null);
//        player.openGui(BCTransport, ordinal(), player.getLevel(), x, y, z);
    }
    static void preInit() {}
}
