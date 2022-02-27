package com.fernando.gui.reacao;

import com.fernando.gui.*;
import com.fernando.gui.enums.EventGuiEnum;
import com.fernando.gui.enums.ReactionStatusEnum;
import com.fernando.gui.event.EventGui;
import com.fernando.gui.graphics.builder.NoGuiBuilder;

public class NodeReaction extends Reaction {
    public NodeReaction(Manager manager) {
        super(manager);
    }

    @Override
    protected void executeReaction(EventGui e) {
        if (e.getEventType().equals(EventGuiEnum.PRESSURE)) {
            status = ReactionStatusEnum.INITIALIZED;
            var noGuiBuilder = new NoGuiBuilder();
            noGuiBuilder.setId(manager.getNextFigureId());
            noGuiBuilder.buildShape(e.getXy());
            manager.addNode(noGuiBuilder.getResult());
            status = ReactionStatusEnum.FINALIZED;
        }
    }
}
