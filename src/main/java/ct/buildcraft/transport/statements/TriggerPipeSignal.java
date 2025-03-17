/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.transport.statements;

import java.util.Locale;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;

import ct.buildcraft.api.gates.IGate;
import ct.buildcraft.api.statements.IStatement;
import ct.buildcraft.api.statements.IStatementContainer;
import ct.buildcraft.api.statements.IStatementParameter;
import ct.buildcraft.api.statements.ITriggerInternal;
import ct.buildcraft.api.transport.IWireManager;

import ct.buildcraft.lib.client.sprite.SpriteHolderRegistry.SpriteHolder;
import ct.buildcraft.lib.misc.ColourUtil;
import ct.buildcraft.lib.misc.LocaleUtil;

import ct.buildcraft.core.statements.BCStatement;
import ct.buildcraft.transport.BCTransportSprites;
import ct.buildcraft.transport.BCTransportStatements;

public class TriggerPipeSignal extends BCStatement implements ITriggerInternal {

    private final boolean active;
    private final DyeColor colour;

    public TriggerPipeSignal(boolean active, DyeColor colour) {
        super(
            "buildcraft:pipe.wire.input." + colour.getName().toLowerCase(Locale.ROOT)
                + (active ? ".active" : ".inactive"), //
            "buildcraft.pipe.wire.input." + colour.getName().toLowerCase(Locale.ROOT)
                + (active ? ".active" : ".inactive"));

        this.active = active;
        this.colour = colour;
    }

    public static boolean doesGateHaveColour(IGate gate, DyeColor c) {
        // FIXME: replace with a check to wires.hasWire(colour)!
        return gate.getPipeHolder().getWireManager().hasPartOfColor(c);
    }

    @Override
    public int maxParameters() {
        return 3;
    }

    @Override
    public Component getDescription() {
        return (Component.translatable("gate.trigger.pipe.wire." + (active ? "active" : "inactive")));
//            ColourUtil.getTextFullTooltip(colour));
    }

    @Override
    public boolean isTriggerActive(IStatementContainer container, IStatementParameter[] parameters) {
        if (!(container instanceof IGate)) {
            return false;
        }

        IGate gate = (IGate) container;
        IWireManager wires = gate.getPipeHolder().getWireManager();

        if (this.active != wires.isAnyPowered(this.colour)) {
            return false;
        }

        for (IStatementParameter param : parameters) {
            if (param != null && param instanceof TriggerParameterSignal) {
                TriggerParameterSignal signal = (TriggerParameterSignal) param;
                if (signal.colour == null) {
                    continue;
                }
                if (signal.active != wires.isAnyPowered(signal.colour)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IStatementParameter createParameter(int index) {
        return TriggerParameterSignal.EMPTY;
    }

    @Override
    public SpriteHolder getSprite() {
        return BCTransportSprites.getPipeSignal(active, colour);
    }

    @Override
    public IStatement[] getPossible() {
        return BCTransportStatements.TRIGGER_PIPE_SIGNAL;
    }
}
