import java.awt.*;
import java.applet.*;
import java.util.*;
import java.awt.geom.*;
/**
 *
 * @author Viet Huy
 */
public class RandomPolygons extends Applet {
    private int[] xpoints1 = {0,-10,-7,7,10};
    private int[] ypoints1 = {-10,-2,10,10,-2};
    private int[] xpoints2 = {4,-8,-8,10,9};
    private int[] ypoints2 = {-5, -10,12, 4, -3};

    private Polygon poly1,poly2;
    
    public void init(){
        poly1 = new Polygon(xpoints1, ypoints1, xpoints1.length);
        poly2 = new Polygon(xpoints2, ypoints2, xpoints2.length);
    }
    
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        
        AffineTransform identity = new AffineTransform();
        
        Random rand = new Random();
        
        int width = getSize().width;
        int height = getSize().height;
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        
        for (int n =0; n<300; n++){
            g2d.setTransform(identity);
            
            g2d.translate(rand.nextInt() % width, rand.nextInt() % height);
            g2d.rotate(Math.toRadians(360*rand.nextDouble()));
            g2d.scale(5*rand.nextDouble(), 5*rand.nextDouble());
            
            g2d.setColor(new Color(rand.nextInt()));
            g2d.fill(poly1);

            g2d.setColor(new Color(rand.nextInt()));
            g2d.fill(poly2);
        }
    }
}
