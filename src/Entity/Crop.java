/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Random;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Maxwell
 */
public class Crop extends Sprite{
    private int state = 0;
    Random r = new Random();
    public Crop(int x, int y, String tex)    
    {
        super(x,y,tex);
    }
    public void draw()
    {
        
        float tx = 16*y + 16*x;
        float ty = (8*x) - (8*y) + (20/2)*15;
        t.bind();
        if(state==0)
        {
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0,0);
                GL11.glVertex2f(tx,ty);
                GL11.glTexCoord2f(.5f,0);
                GL11.glVertex2f(tx+t.getTextureWidth()/2,ty);
                GL11.glTexCoord2f(.5f,.5f);
                GL11.glVertex2f(tx+t.getTextureWidth()/2,ty+t.getTextureHeight()/2);
                GL11.glTexCoord2f(0,.5f);
                GL11.glVertex2f(tx,ty+t.getTextureHeight()/2);
            GL11.glEnd();
        }
        if(state == 1)
        {
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(.5f,0);
                GL11.glVertex2f(tx,ty);
                GL11.glTexCoord2f(1,0);
                GL11.glVertex2f(tx+t.getTextureWidth()/2,ty);
                GL11.glTexCoord2f(1,.5f);
                GL11.glVertex2f(tx+t.getTextureWidth()/2,ty+t.getTextureHeight()/2);
                GL11.glTexCoord2f(.5f,.5f);
                GL11.glVertex2f(tx,ty+t.getTextureHeight()/2);
            GL11.glEnd();
        }
        if(state==2)
        {
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0,.5f);
                GL11.glVertex2f(tx,ty);
                GL11.glTexCoord2f(.5f,.5f);
                GL11.glVertex2f(tx+t.getTextureWidth()/2,ty);
                GL11.glTexCoord2f(.5f,1f);
                GL11.glVertex2f(tx+t.getTextureWidth()/2,ty+t.getTextureHeight()/2);
                GL11.glTexCoord2f(0,1f);
                GL11.glVertex2f(tx,ty+t.getTextureHeight()/2);
            GL11.glEnd();
        }
        if(state == 3)
        {
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(.5f,.5f);
                GL11.glVertex2f(tx,ty);
                GL11.glTexCoord2f(1,.5f);
                GL11.glVertex2f(tx+t.getTextureWidth()/2,ty);
                GL11.glTexCoord2f(1,1f);
                GL11.glVertex2f(tx+t.getTextureWidth()/2,ty+t.getTextureHeight()/2);
                GL11.glTexCoord2f(.5f,1f);
                GL11.glVertex2f(tx,ty+t.getTextureHeight()/2);
            GL11.glEnd();
        }
        
    }
    public void update()
    {
        if(state <3)
        {
            if(r.nextInt(100)==1)
            {
                state = state +1;
            }
        }
    }
    public int getState()
    {
        return state;
    }
    public void setState(int s)
    {
        this.state=s;
    }
    public int getX()
    {
        return (int)x;
    }
    public int getY()
    {
        return (int)y;
    }
}
