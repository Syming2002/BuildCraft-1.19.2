package ct.buildcraft.lib.gui;

import ct.buildcraft.api.transport.pipe.IPipeHolder;

import net.minecraft.world.entity.player.Player;

public abstract class ContainerPipe extends ContainerBC_Neptune {

    public final IPipeHolder pipeHolder;

    public ContainerPipe(Player player, IPipeHolder pipeHolder) {
        super(player);
        this.pipeHolder = pipeHolder;
    }

    @Override
    public final boolean canInteractWith(Player player) {
        return pipeHolder.canPlayerInteract(player);
    }
}
