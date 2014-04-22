import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;
import java.net.*;

public class RandomImages extends JFrame{
	private Image image1, image2;

	public static void main (String[] args){
		new RandomImages();
	}

	public RandomImages(){
		super("RandomImages");
		setSize(600,500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit tk = Toolkit.getDefaultToolkit();
		image1 = tk.getImage(getURL("spaceship.png"));
		image2 = tk.getImage(getURL("asteroid2.png"));
	}

	AffineTransform identity = new AffineTransform();

	private URL getURL(String filename){
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		}
		catch (Exception e){}
		return url;
	}

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		AffineTransform trans = new AffineTransform();

		Random rand = new Random();

		int width = getSize().width;
		int height = getSize().height;

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0,getSize().width,getSize().height);

		for (int n=0; n<50; n++){
			trans.setTransform(identity);

			trans.translate(rand.nextInt()%width, rand.nextInt()%height);
			trans.rotate(Math.toRadians(360*rand.nextDouble()));
			double scale = rand.nextDouble() +1;
			trans.scale(scale,scale);

			g2d.drawImage(image1, trans, this);
		}

		for (int n=0; n<50; n++){
			trans.setTransform(identity);

			trans.translate(rand.nextInt()%width, rand.nextInt()%height);
			trans.rotate(Math.toRadians(360*rand.nextDouble()));
			double scale = rand.nextDouble() +1;
			trans.scale(scale,scale);

			g2d.drawImage(image2, trans, this);
		}
	}
}