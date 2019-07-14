/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Maxwell
 */
public class Vehicle extends Sprite{
    

    public Vehicle(int x, int y, String t)
    {
        super(x,y,t);
    }
    
    public void draw()
    {
        float tx = 16*y + 16*x;
        float ty = (8*x) - (8*y) + (20/2)*15;
        t.bind();
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0,0);
                GL11.glVertex2f(tx,ty);
                GL11.glTexCoord2f(1,0);
                GL11.glVertex2f(tx+t.getTextureWidth(),ty);
                GL11.glTexCoord2f(1,1);
                GL11.glVertex2f(tx+t.getTextureWidth(),ty+t.getTextureHeight());
                GL11.glTexCoord2f(0,1);
                GL11.glVertex2f(tx,ty+t.getTextureHeight());
            GL11.glEnd();
        
    }
}
