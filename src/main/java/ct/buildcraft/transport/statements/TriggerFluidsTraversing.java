package ct.buildcraft.transport.statements;

import ct.buildcraft.api.core.render.ISprite;
import ct.buildcraft.api.gates.IGate;
import ct.buildcraft.api.statements.IStatementContainer;
import ct.buildcraft.api.statements.IStatementParameter;
import ct.buildcraft.api.statements.ITriggerInternal;
import ct.buildcraft.api.transport.pipe.PipeFlow;

import ct.buildcraft.core.statements.BCStatement;
import ct.buildcraft.transport.BCTransportSprites;
import ct.buildcraft.transport.pipe.flow.PipeFlowFluids;

import net.minecraft.network.chat.Component;

public class TriggerFluidsTraversing extends BCStatement implements ITriggerInternal {

    public TriggerFluidsTraversing() {
        super("buildcraft:pipe_contains_fluids");
    }

    @Override
    public Component getDescription() {
        return Component.translatable("gate.trigger.pipe.containsFluids");
    }

    @Override
    public ISprite getSprite() {
        return BCTransportSprites.TRIGGER_FLUIDS_TRAVERSING;
    }

    @Override
    public boolean isTriggerActive(IStatementContainer source, IStatementParameter[] parameters) {
        if (source instanceof IGate) {
            PipeFlow flow = ((IGate) source).getPipeHolder().getPipe().getFlow();
            if (flow instanceof PipeFlowFluids) {
                return ((PipeFlowFluids) flow).doesContainFluid();
            }
        }
        return false;
    }
}
