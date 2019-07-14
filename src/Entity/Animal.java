/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Boot.Map;
import java.text.DecimalFormat;
import java.util.Random;
import org.lwjgl.opengl.GL11;

public class Animal extends Sprite
{
    DecimalFormat df = new DecimalFormat("###.##");
    Random r = new Random();
    
    
    float targetx;
    float targety;
    int face;
    public Animal(int x, int y, String tex)
    {
        super(x,y,tex);
        targetx=r.nextInt(20);
        targety=r.nextInt(20);
        face =1;
    }
    @Override
    public void draw()
    {
        float tx = 16*y + 16*x;
        float ty = (8*x) - (8*y) + (20/2)*15;
        t.bind();
        if(face==1)
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
        if(face ==-1)
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
    }
    public void update(Map m)//move until the colonist reaches his target position
    {   
        
        float d = .05f;
        if(x != targetx)
        {            
            if(x<targetx)
                x = Float.parseFloat(df.format(x + d));//use format the string and then reconvert back to float.               
            if(x>targetx)
                x = Float.parseFloat(df.format(x - d));  
            
          
        }
        if(x==targetx && y!=targety)
        {
            if(y<targety)
                y = Float.parseFloat(df.format(y + d));//use format the string and then reconvert back to float.
            if(y>targety)
                y = Float.parseFloat(df.format(y - d));
        }
        if(x==targetx && y==targety)
        {
            //m.setTile((int)x,(int)y,0);
            
            targetx = r.nextInt(20);
            targety= r.nextInt(20);
        }
        if((x>targetx) || (y>targety))
            face =1;
        else
            face = -1;
        
    }
    public void setFace(int f)
    {
        if(f>0)
            face = 1;
        else
            face = -1;
    }
}
