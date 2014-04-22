package asteroid;
import java.awt.*;

public class Bullet extends BaseVectorShape {
    
    public Rectangle getBounds(){
        Rectangle r;
        r = new Rectangle((int)getX(), (int)getY(),1,1);
        return r;
    }
    
    Bullet(){
        setShape(new Rectangle(0,0,1,1));
        setAlive(false);
    }
}
