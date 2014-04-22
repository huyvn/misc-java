import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.net.*;

public class SpriteTest extends JFrame implements Runnable{

	int screenWidth = 640;
	int screenHeight = 480;

	BufferedImage backbuffer;
	Graphics2D g2d;

	Sprite asteroid;
	ImageEntity background;
	Thread gameloop;
	Random rand = new Random();

	public static void main(String[] args){
		new SpriteTest();
	}

	public SpriteTest(){
		super("Sprite Test");
		setSize(640,480);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		backbuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();

		background = new ImageEntity(this);
		background.load("bluespace.png");

		asteroid = new Sprite(this,g2d);
		asteroid.load("asteroid2.png");

		gameloop = new Thread(this);
		gameloop.start();
	}

	public void run(){
		Thread t = Thread.currentThread();
		while (t== gameloop){
			try {
				Thread.sleep(30);
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}

			g2d.drawImage(background.getImage(),0,0,screenWidth-1,screenHeight-1,this);

			int width = screenWidth - asteroid.imageWidth()-1;
			int height = screenHeight - asteroid.imageHeight()-1;

			for (int i=0; i<25; i++) {
				Point point = new Point(rand.nextInt(width),rand.nextInt(height));
				asteroid.setPosition(point);
				asteroid.transform();
				asteroid.draw();
			}

			repaint();
		}
	}

	public void paint(Graphics g){
		g.drawImage(backbuffer,0,0,this);
	}
}