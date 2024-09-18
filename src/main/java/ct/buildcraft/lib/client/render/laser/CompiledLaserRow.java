/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.lib.client.render.laser;

import ct.buildcraft.lib.client.render.laser.LaserData_BC8.LaserRow;
import ct.buildcraft.lib.client.render.laser.LaserData_BC8.LaserSide;
import com.mojang.math.Matrix4f;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;

public class CompiledLaserRow {
    public final LaserRow[] rows;
    private final TextureAtlasSprite[] sprites;
    public final double width;
    public final double height;
    private int currentRowIndex;

    public CompiledLaserRow(LaserRow row) {
        this(new LaserRow[] { row });
    }

    public CompiledLaserRow(LaserRow[] rows) {
        if (rows.length < 1) throw new IllegalArgumentException("Not enough rows!");
        this.rows = rows;
        this.width = rows[0].width;
        this.height = rows[0].height;
        this.sprites = new TextureAtlasSprite[rows.length];
        for (int i = 0; i < rows.length; i++) {
            sprites[i] = rows[i].sprite;
        }
    }

    private double texU(double between) {
    	TextureAtlasSprite sprite = sprites[currentRowIndex];
        LaserRow row = rows[currentRowIndex];
        if (between == 0) return sprite.getU(row.uMin*16);
        if (between == 1) return sprite.getU(row.uMax*16);
        double interp = row.uMin * (1 - between) + row.uMax * between;
        return sprite.getU(interp*16);
    }

    private double texV(double between) {
    	TextureAtlasSprite sprite = sprites[currentRowIndex];
        LaserRow row = rows[currentRowIndex];
        if (between == 0) return sprite.getV(row.vMin*16);
        if (between == 1) return sprite.getV(row.vMax*16);
        double interp = row.vMin * (1 - between) + row.vMax * between;
        return sprite.getV(interp*16);
    }

    public void bakeStartCap(LaserContext context) {
        this.currentRowIndex = 0;
        double h = height / 2;
        BlockPos pose = context.pos;
        context.setFaceNormal(-1, 0, 0);
        context.addPoint(0, h, h, texU(1), texV(1), pose);
        context.addPoint(0, h, -h, texU(1), texV(0), pose);
        context.addPoint(0, -h, -h, texU(0), texV(0), pose);
        context.addPoint(0, -h, h, texU(0), texV(1), pose);
    }

    public void bakeEndCap(LaserContext context) {
        this.currentRowIndex = 0;
        double h = height / 2;
        BlockPos pose = context.pos;
        context.setFaceNormal(1, 0, 0);
        context.addPoint(context.length, -h, h, texU(0), texV(1), pose);
        context.addPoint(context.length, -h, -h, texU(0), texV(0), pose);
        context.addPoint(context.length, h, -h, texU(1), texV(0), pose);
        context.addPoint(context.length, h, h, texU(1), texV(1), pose);
    }

    public void bakeStart(LaserContext context, double length) {
        this.currentRowIndex = 0;
        final double h = height / 2;
        final double l = length;
        final double i = 1 - (length / width);
        BlockPos pose = context.pos;
        // TOP
        context.setFaceNormal(0, 1, 0);
        context.addPoint(0, h, -h, texU(i), texV(0), pose);// 1
        context.addPoint(0, h, h, texU(i), texV(1), pose);// 2
        context.addPoint(l, h, h, texU(1), texV(1), pose);// 3
        context.addPoint(l, h, -h, texU(1), texV(0), pose);// 4
        // BOTTOM
        context.setFaceNormal(0, -1, 0);
        context.addPoint(l, -h, -h, texU(1), texV(0), pose);// 4
        context.addPoint(l, -h, h, texU(1), texV(1), pose);// 3
        context.addPoint(0, -h, h, texU(i), texV(1), pose);// 2
        context.addPoint(0, -h, -h, texU(i), texV(0), pose);// 1
        // LEFT
        context.setFaceNormal(0, 0, -1);
        context.addPoint(0, -h, -h, texU(i), texV(0), pose);// 1
        context.addPoint(0, h, -h, texU(i), texV(1), pose);// 2
        context.addPoint(l, h, -h, texU(1), texV(1), pose);// 3
        context.addPoint(l, -h, -h, texU(1), texV(0), pose);// 4
        // RIGHT
        context.setFaceNormal(0, 0, 1);
        context.addPoint(l, -h, h, texU(1), texV(0), pose);// 4
        context.addPoint(l, h, h, texU(1), texV(1), pose);// 3
        context.addPoint(0, h, h, texU(i), texV(1), pose);// 2
        context.addPoint(0, -h, h, texU(i), texV(0), pose);// 1
    }

    public void bakeEnd(LaserContext context, double length) {
        this.currentRowIndex = 0;
        final double h = height / 2;
        final double ls = context.length - length;
        final double lb = context.length;
        final double i = length / width;
        BlockPos pose = context.pos;
        // TOP
        context.setFaceNormal(0, 1, 0);
        context.addPoint(ls, h, -h, texU(0), texV(0), pose);// 1
        context.addPoint(ls, h, h, texU(0), texV(1), pose);// 2
        context.addPoint(lb, h, h, texU(i), texV(1), pose);// 3
        context.addPoint(lb, h, -h, texU(i), texV(0), pose);// 4
        // BOTTOM
        context.setFaceNormal(0, -1, 0);
        context.addPoint(lb, -h, -h, texU(i), texV(0), pose);// 4
        context.addPoint(lb, -h, h, texU(i), texV(1), pose);// 3
        context.addPoint(ls, -h, h, texU(0), texV(1), pose);// 2
        context.addPoint(ls, -h, -h, texU(0), texV(0), pose);// 1
        // LEFT
        context.setFaceNormal(0, 0, -1);
        context.addPoint(ls, -h, -h, texU(0), texV(0), pose);// 1
        context.addPoint(ls, h, -h, texU(0), texV(1), pose);// 2
        context.addPoint(lb, h, -h, texU(i), texV(1), pose);// 3
        context.addPoint(lb, -h, -h, texU(i), texV(0), pose);// 4
        // RIGHT
        context.setFaceNormal(0, 0, 1);
        context.addPoint(lb, -h, h, texU(i), texV(0), pose);// 4
        context.addPoint(lb, h, h, texU(i), texV(1), pose);// 3
        context.addPoint(ls, h, h, texU(0), texV(1), pose);// 2
        context.addPoint(ls, -h, h, texU(0), texV(0), pose);// 1
    }

    public void bakeFor(LaserContext context, LaserSide side, double startX, int count) {
        double xMin = startX;
        double xMax = startX + width;
        double h = height / 2;
        BlockPos pose = context.pos;
        for (int i = 0; i < count; i++) {
            this.currentRowIndex = i % rows.length;
            double ls = xMin;
            double lb = xMax;
            if (side == LaserSide.TOP) {
                context.setFaceNormal(0, 1, 0);
                context.addPoint(ls, h, -h, texU(0), texV(0), pose);// 1
                context.addPoint(ls, h, h, texU(0), texV(1), pose);// 2
                context.addPoint(lb, h, h, texU(1), texV(1), pose);// 3
                context.addPoint(lb, h, -h, texU(1), texV(0), pose);// 4
            } else if (side == LaserSide.BOTTOM) {
                context.setFaceNormal(0, -1, 0);
                context.addPoint(lb, -h, -h, texU(1), texV(0), pose);// 4
                context.addPoint(lb, -h, h, texU(1), texV(1), pose);// 3
                context.addPoint(ls, -h, h, texU(0), texV(1), pose);// 2
                context.addPoint(ls, -h, -h, texU(0), texV(0), pose);// 1
            } else if (side == LaserSide.LEFT) {
                context.setFaceNormal(0, 0, -1);
                context.addPoint(ls, -h, -h, texU(0), texV(0), pose);// 1
                context.addPoint(ls, h, -h, texU(0), texV(1), pose);// 2
                context.addPoint(lb, h, -h, texU(1), texV(1), pose);// 3
                context.addPoint(lb, -h, -h, texU(1), texV(0), pose);// 4
            } else if (side == LaserSide.RIGHT) {
                context.setFaceNormal(0, 0, 1);
                context.addPoint(lb, -h, h, texU(1), texV(0), pose);// 4
                context.addPoint(lb, h, h, texU(1), texV(1), pose);// 3
                context.addPoint(ls, h, h, texU(0), texV(1), pose);// 2
                context.addPoint(ls, -h, h, texU(0), texV(0), pose);// 1
            }
            xMin += width;
            xMax += width;
        }
    }
}
