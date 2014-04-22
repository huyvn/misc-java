import java.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
/**
 *
 * @author Viet Huy
 */
public class RotatePolygon extends Applet 
	implements KeyListener, MouseListener, MouseMotionListener{
    private int[] xpoints = {0,-10,-7,7,10};
    private int[] ypoints = {-10, -2, 10, 10, -2};
    
    private Polygon poly;

    double rotation = 0;

    public void init(){
    	poly = new Polygon(xpoints, ypoints, xpoints.length);

    	addKeyListener(this);
    	addMouseListener(this);
    	addMouseMotionListener(this);

    }

    public void paint(Graphics g){
    	Graphics2D g2d = (Graphics2D)g;

    	AffineTransform identity = new AffineTransform();

    	int width = getSize().width;
    	int height = getSize().height;

    	g2d.setColor(Color.BLACK);
    	g2d.fillRect(0,0,width,height);

    	g2d.translate(width/2,height/2);
    	g2d.scale(20,20);
    	g2d.rotate(Math.toRadians(rotation));

    	g2d.setColor(Color.RED);
    	g2d.fill(poly);
    	g2d.setColor(Color.BLUE);
    	g2d.draw(poly);

    }

    public void keyReleased(KeyEvent k){}
    public void keyTyped(KeyEvent k){}
    public void keyPressed(KeyEvent k){
    	switch (k.getKeyCode()) {
    	case KeyEvent.VK_LEFT:
    		rotation--;
    		if (rotation <0) rotation = 359;
    		repaint();
    		break;
    	case KeyEvent.VK_RIGHT:
    		rotation++;
    		if (rotation >360) rotation = 1;
    		repaint();
    		break;
    	}
    }

    public void mouseEntered(MouseEvent m){}
    public void mouseExited(MouseEvent m){}
    public void mouseReleased(MouseEvent m){}
    public void mouseClicked(MouseEvent m){}
    public void mousePressed(MouseEvent m){
    	switch (m.getButton()){
    	case MouseEvent.BUTTON1:
    		rotation--;
    		if (rotation < 0) rotation = 359;
    		repaint();
    		break;
    	case MouseEvent.BUTTON3:
    		rotation++;
    		if (rotation > 360) rotation = 1;
    		repaint();
    		break;
    	}
    }

    public void mouseDragged(MouseEvent m){}
    public void mouseMoved(MouseEvent m){
    	int x = m.getX();
    	int y = m.getY();
    	double rotationTheta = Math.atan2(y- getSize().height/2, x- getSize().width/2);
    	rotation = Math.toDegrees(rotationTheta);
    	repaint();
    }
}
