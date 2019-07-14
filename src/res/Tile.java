/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author GMD061
 */
public class Tile {
    private Texture t;
    public Tile(String tex)
    {
        try {
            t = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+ tex + ".png"));                       
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Texture getTex()
    {
        return t;
    }
}
