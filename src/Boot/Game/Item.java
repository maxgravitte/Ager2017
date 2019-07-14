/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boot.Game;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Maxwell
 */
public class Item {
    private int count;
    private String name;
    private Texture t;
    private int price;
    private boolean isStruct;
    public void add(int num)
    {
        this.count = count + num;
    }
    
    public Item(String name, int num, int price, boolean isStruct)
    {
        try {
             this.t = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+ name + ".png"));            
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.name=name;
        this.count=num;
        this.price=price;
        this.isStruct = isStruct;
    }
    public int getAmt()
    {
        return count;
    }
    public String getName()
    {
        return name;
    }
    public int getPrice()
    {
        return price;
    }
    public boolean getS()
    {
        return isStruct;
    }
}
