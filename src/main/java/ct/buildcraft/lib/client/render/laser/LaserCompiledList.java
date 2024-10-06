/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.lib.client.render.laser;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LaserCompiledList {
    public abstract void render();

    public abstract void delete();

    public static class Builder implements ILaserRenderer, AutoCloseable {
        private final BufferBuilder bufferBuilder;
        private final boolean useColour;

        public Builder(boolean useNormalColour) {
            this.useColour = useNormalColour;
            bufferBuilder = Tesselator.getInstance().getBuilder();
        }

        @Override
        public void vertex(float x, float y, float z, float u, float v, int lmap, float nx, float ny, float nz,
            float diffuse) {
            bufferBuilder.vertex(x, y, z);
            bufferBuilder.uv(u, v);
            bufferBuilder.uv2((lmap >> 16) & 0xFFFF, lmap & 0xFFFF);
            if (useColour) {
                bufferBuilder.color(diffuse, diffuse, diffuse, 1.0f);
            }
            bufferBuilder.endVertex();
        }

        public LaserCompiledList build() {
/*            if (1!=0) {
                VertexBuffer vertexBuffer = new VertexBuffer(bufferBuilder.getVertexFormat());
                bufferBuilder.finishDrawing();
                bufferBuilder.reset();
                vertexBuffer.bufferData(bufferBuilder.getByteBuffer());
                return new Vbo(useColour, vertexBuffer);
            } else {
                int glList = GLAllocation.generateDisplayLists(1);
                GL11.glNewList(glList, GL11.GL_COMPILE);
                tess.tessellator.draw();
                GL11.glEndList();
                return new GlList(glList);
            }*/
        	return null;
        }

        @Override
        public void close() {
//            tess.close();
        }
    }

    private static class GlList extends LaserCompiledList {
        private final int glListId;

        private GlList(int glListId) {
            this.glListId = glListId;
        }

        @Override
        public void render() {
            GL11.glCallList(glListId);
        }

        @Override
        public void delete() {
            GL11.glDeleteLists(glListId, 1);
        }
    }

    private static class Vbo extends LaserCompiledList {
        private final boolean useColour;
        private final VertexBuffer vertexBuffer;

        private Vbo(boolean useColour, VertexBuffer vertexBuffer) {
            this.useColour = useColour;
            this.vertexBuffer = vertexBuffer;
        }

        @Override
        public void render() {
/*            final int stride = useColour ? 28 : 24;

            vertexBuffer.bind();
            GlStateManager.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GlStateManager.glVertexPointer(3, GL11.GL_FLOAT, stride, 0);

            GlStateManager.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            GlStateManager.glTexCoordPointer(2, GL11.GL_FLOAT, stride, 12);

            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            GlStateManager.glTexCoordPointer(2, GL11.GL_SHORT, stride, 20);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);

            if (useColour) {
                GlStateManager.glEnableClientState(GL11.GL_COLOR_ARRAY);
                GlStateManager.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, stride, 24);
            }

            vertexBuffer.drawArrays(GL11.GL_QUADS);
            vertexBuffer.unbindBuffer();

            GlStateManager.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            GlStateManager.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);

            if (useColour) {
                GlStateManager.glDisableClientState(GL11.GL_COLOR_ARRAY);
                GlStateManager.color(1, 1, 1, 1);
            }*/
        }

        @Override
        public void delete() {
///            vertexBuffer.deleteGlBuffers();
        }
    }
}
