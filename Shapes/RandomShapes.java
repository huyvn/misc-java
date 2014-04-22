
import java.awt.*;
import java.applet.*;
import java.awt.geom.*;
import java.util.*;
/**
 *
 * @author Viet Huy
 */
public class RandomShapes extends Applet  {

    private Shape shape;
    
    public void init(){
        shape = new Rectangle2D.Double(-1.0, -1.0, 1.0, 1.0);
    }
    
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        
        AffineTransform identity = new AffineTransform();
        
        Random rand = new Random();
        
        int width = getSize().width;
        int height = getSize().height;
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        
        for (int n = 0; n<300; n++){
            g2d.setTransform(identity);
            
            g2d.translate(rand.nextInt()%width, rand.nextInt()%height);
            g2d.rotate(Math.toRadians(360*rand.nextDouble()));
            g2d.scale(60*rand.nextDouble(), 60*rand.nextDouble());
            
            g2d.setColor(new Color(rand.nextInt()));
            g2d.fill(shape);
        }
    }
}
