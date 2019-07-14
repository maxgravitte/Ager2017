/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Maxwell
 */
public class Box {
    private int x;
    private int y;
    private Texture t;
    public Box(int x, int y, String tex)
    {
        this.x=x;
        this.y=y;
        try {
             this.t = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+ tex + ".png"));            
            System.out.println("Texture loaded: "+ t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void display()
    {
        t.bind();
        GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0,0);
                GL11.glVertex2f(x,y);
                GL11.glTexCoord2f(1,0);
                GL11.glVertex2f(x+t.getTextureWidth(),y);
                GL11.glTexCoord2f(1,1);
                GL11.glVertex2f(x+t.getTextureWidth(),y+t.getTextureHeight());
                GL11.glTexCoord2f(0,1);
                GL11.glVertex2f(x,y+t.getTextureHeight());
            GL11.glEnd();
    }
}
