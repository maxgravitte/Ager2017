/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import org.lwjgl.input.Keyboard;


/**
 *
 * @author Maxwell
 */
public class Boat extends Vehicle{
    private boolean ready = false;
    public Boat(int x, int y, String t)
    {
        super(x,y,t);
    }
    public void update()
    {
        if(ready)
        {
                y=y+.1f;
            if(y>25)
            {                
                ready=false;
                y=3;
            } 
        }        
        if(Keyboard.getEventKeyState())
        {
            if(Keyboard.getEventKey()== Keyboard.KEY_SPACE)
                ready=true;
        }        
    }
    public void setReady(boolean r)
    {
        ready = r;
    }
}
