/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boot;

import static Boot.Map.grass;
import java.util.Random;
import org.lwjgl.opengl.GL11;
import res.Tile;

/**
 *
 * @author Maxwell
 */
public class Weather {
    
    private Tile rain1 = new Tile("rain1");
    private Tile rain2 = new Tile("rain2");
    Random r = new Random();
    private int state=0;
    private int flicker=1;
    //State 0=Sunny
    //State 1=Rainy
    public void update()
    { 
        int change = r.nextInt(10000);
        if(change%10==0)
            flicker=flicker*-1;//flips to the next rain image.
        if(state==0)//IF SUNNY
        {
            if(change==1)//10 percent chance of raining
            {
               state =1; 
            }
            //90 percent chance of not changing
        }
        else if(state==1)//rain
        {
            if(change <10)
            {
                state =0;
            }
        }
        
    }
    public void display()
    {
        
        int sx= 20;
        int sy=20;
        if(state!=0)
        for(int i=sx-1;i>=0;i--)//X values
        {
            for(int j=0;j<sy;j++)//Y Values
            {
                if(state==1)
                {
                    if(flicker==1)
                        rain1.getTex().bind();
                    else
                        rain2.getTex().bind();
                }
                int x = 16*i + 16*j;
                int y = (8*j) - (8*i) + (sx/2)*15;
                
                GL11.glBegin(GL11.GL_QUADS);
                    GL11.glTexCoord2f(0,0);
                    GL11.glVertex2f(x,y);
                    GL11.glTexCoord2f(1,0);
                    GL11.glVertex2f(x+rain1.getTex().getTextureWidth(),y);
                    GL11.glTexCoord2f(1,1);
                    GL11.glVertex2f(x+rain1.getTex().getTextureWidth(),y+rain1.getTex().getTextureHeight());
                    GL11.glTexCoord2f(0,1);
                    GL11.glVertex2f(x,y+rain1.getTex().getTextureHeight());
                GL11.glEnd();
            
            }
        }
    }
}
