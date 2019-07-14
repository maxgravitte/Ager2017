/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boot;

import static Boot.Game.Logic.checkOpen;
import Entity.Structure;
import java.lang.Thread;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import res.Tile;


public class Map {
    private int sx;
    private int sy;
    private int[][] map;
    
    static Tile grass = new Tile("grass");
    static Tile water = new Tile("water");
    static Tile flowers = new Tile("flowers");
    static Tile soil = new Tile("soil");
    static Tile open = new Tile("open");
    static Tile error = new Tile("error");
    
    public Map(int x, int y)
    {  
        Random r = new Random();
        
        map = new int[x][y];
        
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {   
                int b = r.nextInt(100); 
                map[i][j]=0;
            }
        }
        sx=x;
        sy=y;
        
    }
    public void cleanup()
    {
        water.getTex().release();
        grass.getTex().release();
    }
    public void setTile(int x, int y, int tileID)
    {
        map[x][y]=tileID;
    }
    public int getTile(int x, int y)
    {
        return map[x][y];
    }
    public void testDraw(List<Structure> structs)
    {
        for(int i=sx-1;i>=0;i--)//X values
        {
            for(int j=0;j<sy;j++)//Y Values
            {
                if(checkOpen(j,i, this, structs))
                    open.getTex().bind();
                else
                    error.getTex().bind();
                
                int x = 16*i + 16*j;
                int y = (8*j) - (8*i) + (sx/2)*16;
                
                GL11.glBegin(GL11.GL_QUADS);
                    GL11.glTexCoord2f(0,0);
                    GL11.glVertex2f(x,y);
                    GL11.glTexCoord2f(1,0);
                    GL11.glVertex2f(x+grass.getTex().getTextureWidth(),y);
                    GL11.glTexCoord2f(1,1);
                    GL11.glVertex2f(x+grass.getTex().getTextureWidth(),y+grass.getTex().getTextureHeight());
                    GL11.glTexCoord2f(0,1);
                    GL11.glVertex2f(x,y+grass.getTex().getTextureHeight());
                GL11.glEnd();          
            }
        }   
    }
    public void draw()
    {
        for(int i=sx-1;i>=0;i--)//X values
        {
            for(int j=0;j<sy;j++)//Y Values
            {
                if(map[j][i]==0)
                    grass.getTex().bind();
                if(map[j][i]==1)
                    water.getTex().bind();
                if(map[j][i]==2)
                    flowers.getTex().bind();
                if(map[j][i]==3)
                    soil.getTex().bind();
                int x = 16*i + 16*j;
                int y = (8*j) - (8*i) + (sx/2)*15;
                
                GL11.glBegin(GL11.GL_QUADS);
                    GL11.glTexCoord2f(0,0);
                    GL11.glVertex2f(x,y);
                    GL11.glTexCoord2f(1,0);
                    GL11.glVertex2f(x+grass.getTex().getTextureWidth(),y);
                    GL11.glTexCoord2f(1,1);
                    GL11.glVertex2f(x+grass.getTex().getTextureWidth(),y+grass.getTex().getTextureHeight());
                    GL11.glTexCoord2f(0,1);
                    GL11.glVertex2f(x,y+grass.getTex().getTextureHeight());
                GL11.glEnd();
            
            }
        }   
    }
    public int getSX()
    {
        return sx;
    }
    public int getSY()
    {
        return sy;
    }
}
