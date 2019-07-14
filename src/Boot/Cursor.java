/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boot;

import java.io.IOException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Cursor {
    private static Texture cursor;
    private static int cx;
    private static int cy;
    //tool 0 = null
    //tool 1 = till
    //tool 2 = plant
    private static int tool = 0;
    
    public Cursor(int x, int y)
    {
        this.cx=x;
        this.cy=y;
        try {
             cursor = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/cursor.png"));            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void display(Map map)
    { 
        //Draw the Cursor on the screen
        float tx = 16*cy + 16*cx;
        float ty = (8*cx) - (8*cy) + (20/2)*15;
        
        cursor.bind();
            
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0,0);
                GL11.glVertex2f(tx,ty);
                GL11.glTexCoord2f(1,0);
                GL11.glVertex2f(tx+cursor.getTextureWidth(),ty);
                GL11.glTexCoord2f(1,1);
                GL11.glVertex2f(tx+cursor.getTextureWidth(),ty+cursor.getTextureHeight());
                GL11.glTexCoord2f(0,1);
                GL11.glVertex2f(tx,ty+cursor.getTextureHeight());
            GL11.glEnd();            
    }
 
    public int getTool()
    {
        return tool;
    }
    public void setTool(int t)
    {
        tool = t;
    }
    public void setCX(int x)
    {
        this.cx = x;
    }
     public void setCY(int y)
     {
         this.cy = y;
     }
    public int getCX()
    {
        return cx;
    }
    public int getCY()
    {
        return cy;
    }
    
}
