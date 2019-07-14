package Entity;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Structure extends Sprite
{
    private Texture t;
    protected static float x;
    protected static float y;
    private String name;
    public Structure(int x, int y, String tex)
    {
        super(x,y,tex);
        name = tex;
    }
    public String getName()
    {
        return name;
    }
}
