/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author GMD061
 */
public abstract class Sprite {
    protected Texture t;
    protected float x;
    protected float y;
    protected float mx;
    protected float my;
    public Sprite(int x, int y, String tex)
    {
        this.x=x;
        this.y=y;
        try {
             this.t = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+ tex + ".png"));            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        mx=16*y + 16*x;
        my=(8*x) - (8*y) + (20/2)*15;
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
    public int getX()
    {
        return (int) x;
    }
    public int getY()
    {
        return (int) y;
    }
}
