package Boot;



import static Boot.Cursor.*;

import Boot.Game.Item;

import Entity.Animal;
import Entity.Boat;
import Entity.Crop;
import Entity.Structure;
import GUI.Box;
import GUI.Icon;
import java.awt.Font;

import static Boot.Game.Logic.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import res.Tile;


public class Main {
    private static int sx = 20;
    private static int sy = 20;
    private static int width = sx*32;
    private static int height = 600;
    private static long time;
    private static long cooldown=0;
    
    private static int money = 1000;
    
    //state0 = Start Menu
    //State1 = Main Game Loop
    //State2 = Inventory
    //State3 = Market
    private static int state = 0;
    static TrueTypeFont font;
    
    
    public static void start() {        
        initGL();//get all of the OPENGL stuff ready
        Cursor c = new Cursor(0,0);
        //Create the map of tiles
        Map m = new Map(20,20);
        //Holds the buildings and other objects on the map
        List<Structure> structures = new ArrayList();
        //Holds all of the Animal objects
        List<Animal> animals = new ArrayList();
        List<Crop> plants = new ArrayList();
        Structure house = new Structure(4,8,"house");  
        Structure dock = new Structure(-1,2,"dock");
        Boat boat = new Boat(-1,3,"ship");
        structures.add(house);
        structures.add(dock);
        Weather w = new Weather();
        Box menu = new Box(0,0 ,"menu2");
        Box market = new Box(0,0,"market");
        Box inv = new Box(0,0,"inventory");      
        Box bar = new Box(44,400,"bar");
        animals.add(new Animal(7,8,"sheep"));
        
        List<Item> inventory = new ArrayList();
        List<Item> shop = new ArrayList();
        Item seeds = new Item("seeds", 0,5,false);
        Item grain = new Item("grain", 0,10,false);
        Item itemsilo = new Item("itemsilo",0,1000,true);
        inventory.add(seeds);
        inventory.add(grain);
        shop.add(seeds);
        shop.add(grain);
        shop.add(itemsilo);
        Icon hoe = new Icon(0,0,"hoe");
        Icon seeding = new Icon(0,0,"seeds");
        Icon scythe = new Icon(0,0,"scythe");
        
        int silocount = 0;
        int inventorySize = 10;
        int shopindex = 0;
        int invindex = 0;
        boolean buy = true;
        //THE MAIN GAME LOOP
        int day = 1; //keep track of number of days (5 minutes long)
        int count =0; //Count number of cycles (18000 cycles in a day)
        
        float blue = 0;
        int sun =0;
        while (!Display.isCloseRequested())
        {   
            if(state == 0)
            {                
                menu.display();
                while(Keyboard.next())
                {
                    if(Keyboard.getEventKeyState())
                    {
                        if(Keyboard.getEventKey()== Keyboard.KEY_RETURN)                            
                            state = 1;
                    }
                }
            }
            if(state == 1)
            {                
                if(count ==1000)
                {
                    day++;
                    sun =1;
                }
                if(count ==0)
                    sun = 0;
                if(sun ==0)
                    count++;
                if(sun ==1)
                    count--;
                blue = (float) count/1000;
                GL11.glClearColor(0f, 0.01f, blue, 0.0f);
                
                           
                //Erase the Screen
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
                //draw the map/tiles first
                m.draw();
                bar.display();
                
                
                
                //update and draw the animals
                for (Animal a: animals)
                {
                    a.update(m);
                    a.draw();
                }
                
                for (Crop p: plants)
                {
                    p.update();
                    p.draw();
                }
                //Draw the structures 
                for (Structure s: structures)
                {
                    s.draw();
                }
                
                boat.update();
                boat.draw();
                
                w.update();
                w.display();
               
                if(c.getTool()==1)
                    hoe.display();
                if(c.getTool()==2)
                    seeding.display();
                if(c.getTool()==3)
                    scythe.display();
                //Print Game Information
                font.drawString(50, 400, "Crops: " + plants.size(), Color.white);
                font.drawString(50, 430,"Day " + day, Color.white);
                font.drawString(50, 460,"Money $" + money, Color.white);
                
                Color.white.bind();
                c.display(m);
                //Keyboard Controls
                    while (Keyboard.next()) 
                    {
                        //ASDF move the cursor
                        if (Keyboard.getEventKeyState()) {
                            if (Keyboard.getEventKey() == Keyboard.KEY_D && c.getCY()<19) {
                                c.setCY(c.getCY()+1);
                            }
                            if (Keyboard.getEventKey() == Keyboard.KEY_A && c.getCY()>0) {
                                c.setCY(c.getCY()-1);
                            }
                            if (Keyboard.getEventKey() == Keyboard.KEY_W && c.getCX()>0) {
                                c.setCX(c.getCX()-1);
                            }
                            if (Keyboard.getEventKey() == Keyboard.KEY_S && c.getCX()<19) {
                                c.setCX(c.getCX()+1);
                            } 
                            
                            //SET AND USE CURSOR
                            if (Keyboard.getEventKey() == Keyboard.KEY_T)//T=Till
                            {
                                c.setTool(1);
                            }                            
                            if (Keyboard.getEventKey() == Keyboard.KEY_G)//G=plant crop
                            {
                                c.setTool(2);                                
                            }
                            if(Keyboard.getEventKey() == Keyboard.KEY_C)
                                c.setTool(3);
                            if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
                                c.setTool(0);
                            if(Keyboard.getEventKey() == Keyboard.KEY_F)//F= activate
                            {
                                if(c.getTool()==1)//Till the Land
                                {
                                    if(!checkForStructures(c.getCX(),c.getCY(),structures))
                                        m.setTile(c.getCX(), c.getCY(), 3);
                                }
                                if(c.getTool()==2)//Plant Seed
                                {                                   
                                    if(checkForSoil(c.getCX(),c.getCY(), m))
                                    {
                                        if(!checkForPlants(c.getCX(),c.getCY(),plants))
                                            if(inventory.get(0).getAmt()>0)//Check if you have seeds to plant
                                            {
                                                plants.add(new Crop(c.getCX(),c.getCY(),"wheat"));
                                                inventory.get(0).add(-1);
                                            }
                                    }
                                }  
                                if(c.getTool()==3)//harvest
                                {
                                    if(checkForPlants(c.getCX(),c.getCY(),plants))
                                    {
                                        plants.remove(findPlant(plants,c.getCX(),c.getCY()));
                                            grain.add(1);                                                                                   
                                    }
                                }
                            }                            
                            if(Keyboard.getEventKey() == Keyboard.KEY_E)
                            {
                                state = 2;                                        
                            }
                            if(Keyboard.getEventKey() == Keyboard.KEY_R)
                            {
                                state = 3;                                
                            }
                        }
                    }
            }            
            if(state == 2)//inventory
            {
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
                inv.display();
                for(int i=0;i<inventory.size();i++)
                {
                    if(i==invindex)
                        font.drawString(50, 100 + i*30, inventory.get(i).getName() + " " + inventory.get(i).getAmt(), Color.yellow);
                    else
                        font.drawString(50, 100 + i*30, inventory.get(i).getName() + " " + inventory.get(i).getAmt(), Color.black);
                }
                while (Keyboard.next()) 
                {
                    if(Keyboard.getEventKeyState())
                    { 
                        if (Keyboard.getEventKey() == Keyboard.KEY_W && invindex>0)
                        {
                            invindex --;
                        } 
                        if (Keyboard.getEventKey() == Keyboard.KEY_S && invindex<inventory.size()-1)
                        {
                            invindex ++;
                        } 
                        if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
                        {
                            state =1;
                        } 
                        if (Keyboard.getEventKey() == Keyboard.KEY_RETURN )
                        {
                            System.out.println(inventory.get(invindex).getName());
                            if(inventory.get(invindex).getS())
                            {                                
                                state = 4;
                            }
                        }
                    }
                }
               
                
                Color.white.bind();
            }
            if(state == 3)//Market
            {
               
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
                market.display();
                font.drawString(50, 440,"Money " + money, Color.white);
                for(int i =0; i<shop.size();i++)//BUYING LOOP
                {     
                    if(buy)//SELECT BUYING ITEM
                    {
                        if(shopindex==i)
                            font.drawString(50, 200+ i*30, shop.get(i).getName(), Color.yellow); 
                        else
                            font.drawString(50, 200+ i*30, shop.get(i).getName(), Color.black);
                    }  
                    else
                        font.drawString(50, 200+ i*30, shop.get(i).getName(), Color.black);
                }
                for(int i =0; i<inventory.size();i++)//SELLING LOOP
                {  
                    if(!buy)
                    {
                        if(shopindex==i)
                            font.drawString(450, 200+ i*30, inventory.get(i).getName()+ " " + inventory.get(i).getAmt(), Color.yellow); 
                        else
                            font.drawString(450, 200+ i*30, inventory.get(i).getName()+ " " + inventory.get(i).getAmt(), Color.black);
                    }
                    else
                        font.drawString(450, 200+ i*30, inventory.get(i).getName()+ " " + inventory.get(i).getAmt(), Color.black);  
                }
                while (Keyboard.next())
                {
                    if(Keyboard.getEventKeyState())
                    {                       
                        if(Keyboard.getEventKey() == Keyboard.KEY_D)
                        {
                            buy=false;
                            shopindex =0;
                        }
                        if(Keyboard.getEventKey() == Keyboard.KEY_A)
                        {
                            buy=true;
                            shopindex =0;
                        }
                        if(Keyboard.getEventKey() == Keyboard.KEY_W && shopindex > 0)
                        {
                                shopindex--;
                        }
                        if(Keyboard.getEventKey() == Keyboard.KEY_S)
                        {
                            if(buy && shopindex<shop.size()-1)
                                shopindex++;
                            if(!buy && shopindex<inventory.size()-1)
                                shopindex++;
                        }
                        if(Keyboard.getEventKey() == Keyboard.KEY_RETURN)//BUY OR SELL
                        {
                            if(buy && money >= shop.get(shopindex).getPrice())//CHECK IF YOU HAVE ENOUGH MONEY
                            {
                                buy(shop,inventory,shopindex);
                                money = money - shop.get(shopindex).getPrice();
                            }
                            if(!buy && inventory.get(shopindex).getAmt()>0)//Check if you have atleast 1 to sell
                            {
                                sell(inventory,shopindex);
                                money = money + inventory.get(shopindex).getPrice();
                                boat.setReady(true);
                            }
                        }
                        if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
                        {
                            state = 1;
                        }                        
                    }
                }
                Color.white.bind();               
            }
            if(state==4)//PLACEMENT MODE
            {
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                
                m.testDraw(structures);               
                c.display(m);
                while (Keyboard.next())
                {
                    if(Keyboard.getEventKeyState())
                    {                       
                        if(Keyboard.getEventKey() == Keyboard.KEY_RETURN)
                        {
                            if(checkOpen(c.getCX(),c.getCY(),m,structures))
                            {
                                structures.add(new Structure(c.getCX(),c.getCY(),inventory.get(invindex).getName()));                               
                                state = 1;
                            }
                        }                    
                        if (Keyboard.getEventKey() == Keyboard.KEY_D && c.getCY()<19) {
                            c.setCY(c.getCY()+1);
                        }
                        if (Keyboard.getEventKey() == Keyboard.KEY_A && c.getCY()>0) {
                            c.setCY(c.getCY()-1);
                        }
                        if (Keyboard.getEventKey() == Keyboard.KEY_W && c.getCX()>0) {
                            c.setCX(c.getCX()-1);
                        }
                        if (Keyboard.getEventKey() == Keyboard.KEY_S && c.getCX()<19) {
                            c.setCX(c.getCX()+1);
                        } 
                    }
                }
            }
            Display.update();
            Display.sync(60);
        }
    
    Display.destroy();
    }
    public static void main(String args[])
    {
        start();
    }    
    
    public static void initGL()
    {
        try {
        Display.setDisplayMode(new DisplayMode(sx*32,600));
        Display.create();
    } catch (LWJGLException e) {
        e.printStackTrace();
        System.exit(0);
    }    
        Display.setTitle("AGER");
                
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);//This game uses 2D sprites                        
        GL11.glClearColor(0.0f, 0.01f, 0.8f, 0.0f);//makes the background black                 
        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);         
        GL11.glViewport(0,0,width,height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); 
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);  
    
    Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
    font = new TrueTypeFont(awtFont, true);
       
    }
    
}
