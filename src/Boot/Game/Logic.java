/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boot.Game;

import Boot.Map;
import Entity.Crop;
import Entity.Structure;
import java.util.List;

/**
 *
 * @author Maxwell
 */
public abstract class Logic {
    
    public static void buy(List<Item> shop, List<Item> inventory, int index)
    {
        Item test = shop.get(index);
        for(int i = 0; i<inventory.size();i++)
        {
            if(test.getName().equals(inventory.get(i).getName()))
            {
                System.out.println(test.getName());
                inventory.get(i).add(1);
                return;
            }           
        }       
        inventory.add(new Item(test.getName(),1,test.getPrice(),test.getS()));
    }
    public static void sell(List<Item> inventory, int index)
    {
        if(inventory.get(index).getAmt()>0)
        {
            inventory.get(index).add(-1);           
        }
    }
    public static int findPlant(List<Crop> p, int x, int y)
    {
        for(int i = 0; i<p.size();i++)
        {
            if(p.get(i).getX()==x && p.get(i).getY()==y)
            {
                return i;
            }
        }
        return -1;
    }
    public static boolean checkOpen(int x, int y, Map m, List<Structure> s)
    {
        for(int i =0;i<s.size();i++)
        {
            if(s.get(i).getX()==x && s.get(i).getY()==y)
                return false;
        }
        for(int i=0;i<m.getSX();i++)
        {
            for(int j=0;j<m.getSY();j++)
            {
                if (m.getTile(i,j)!=0)
                    return false;
            }
        }
        return true;
    }
    public static boolean checkForSoil(int x, int y, Map m)
    {
        if(m.getTile(x,y)==3)
            return true;
        else
            return false;
    }
    public static boolean checkForPlants(int x, int y, List<Crop> p)
    {
        for(int i =0;i<p.size();i++)
        {
            if(p.get(i).getX()==x && p.get(i).getY()==y)
                return true;
        }
        return false;
    }
    public static boolean checkForStructures(int x, int y, List<Structure> s)
    {
        for(int i = 0;i<s.size();i++)
        {
            if(s.get(i).getX()==x && s.get(i).getY()==y)
                return true;
        }
        return false;
    }
}
