/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.builders.filler;

import ct.buildcraft.api.filler.IFillerPattern;
import ct.buildcraft.api.statements.IStatementParameter;
import ct.buildcraft.api.statements.containers.IFillerStatementContainer;
import ct.buildcraft.builders.snapshot.Template;
import ct.buildcraft.builders.snapshot.Template.BuildingInfo;
import ct.buildcraft.lib.statement.FullStatement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;

public class FillerUtil {
    public static Template.BuildingInfo createBuildingInfo(IFillerStatementContainer filler,
                                                           FullStatement<IFillerPattern> patternStatement,
                                                           IStatementParameter[] params,
                                                           boolean inverted) {
        Template.FilledTemplate filledTemplate = (Template.FilledTemplate) patternStatement.get().createTemplate(
            filler,
            params
        );
        if (filledTemplate == null) {
            return null;
        }
        if (inverted) {
            filledTemplate.getTemplate().invert();
        }
        return filledTemplate.getTemplate().new BuildingInfo(BlockPos.ZERO, Rotation.NONE);
    }
}
