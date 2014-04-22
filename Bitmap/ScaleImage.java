import java.util.*;
import java.awt.*;
import java.applet.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.net.*;


public class ScaleImage extends Applet implements KeyListener {
	private Image image;
	private double scale =1;

	public void init(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		image = tk.getImage(getURL("castle.png"));
		addKeyListener(this);
	}

	private URL getURL(String filename){
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		}
		catch (Exception e){}
		return url;
	}

    AffineTransform identity = new AffineTransform();

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		AffineTransform trans = new AffineTransform();

    	int width = getSize().width;
    	int height = getSize().height;

    	g2d.setColor(Color.BLACK);
    	g2d.fillRect(0,0,width,height);

    	trans.setTransform(identity);
        trans.scale(scale,scale);

    	g2d.drawImage(image,trans,this);
	}

	public void keyReleased(KeyEvent k){}
    public void keyTyped(KeyEvent k){}
    public void keyPressed(KeyEvent k){
    	switch (k.getKeyCode()) {
    	case KeyEvent.VK_UP:
    		scale -= 0.1;
    		repaint();
    		break;
    	case KeyEvent.VK_DOWN:
    		scale += 0.1;
    		repaint();
    		break;
    	}
    }
}