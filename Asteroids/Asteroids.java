
package asteroid;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

public class Asteroids extends Applet implements Runnable, KeyListener {

    Thread gameloop;

    BufferedImage backbuffer;

    Graphics2D g2d;

    boolean showBounds = false;

    int ASTEROIDS = 20;
    Asteroid[] ast = new Asteroid[ASTEROIDS];

    int BULLETS = 10;
    Bullet[] bullet = new Bullet[BULLETS];
    int currentBullet = 0;

    Ship ship = new Ship();

    AffineTransform identity = new AffineTransform();

    Random rand = new Random();

    public void init(){
        backbuffer = new BufferedImage(640,480, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();

        ship.setX(320);
        ship.setY(240);

        for (int n=0; n<ASTEROIDS; n++){
            bullet[n]= new Bullet();
        }

        for (int n=0; n<ASTEROIDS; n++){
            ast[n]= new Asteroid();
            ast[n].setRotationVelocity(rand.nextInt(3)+1);
            ast[n].setX((double)rand.nextInt(600)+2);
            ast[n].setY((double)rand.nextInt(440)+20);
            ast[n].setMoveAngle(rand.nextInt(360));
            double ang = ast[n].getMoveAngle() - 90;
            ast[n].setVelX(calcAngleMoveX(ang));
            ast[n].setVelY(calcAngleMoveY(ang));
        }

        addKeyListener(this);
    }

    public void update(Graphics g){
        g2d.setTransform(identity);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getSize().width, getSize().height);

        g2d.setColor(Color.WHITE);
        g2d.drawString("Ship: " + Math.round(ship.getX())+ "." + Math.round(ship.getY()), 5, 10);
        g2d.drawString("Move angle: " + Math.round(ship.getMoveAngle())+ 90, 5, 25);
        g2d.drawString("Face angle: " + Math.round(ship.getFaceAngle()), 5, 40);

        drawShip();
        drawBullets();
        drawAsteroids();

        paint(g);
    }

    public void drawShip(){
        g2d.setTransform(identity);
        g2d.translate(ship.getX(), ship.getY());
        g2d.rotate(Math.toRadians(ship.getFaceAngle()));
        g2d.setColor(Color.ORANGE);
        g2d.fill(ship.getShape());
    }

    public void drawBullets(){
        for (int n=0; n<BULLETS; n++){
            if (bullet[n].isAlive()){
                g2d.setTransform(identity);
                g2d.translate(bullet[n].getX(), bullet[n].getY());
                g2d.setColor(Color.MAGENTA);
                g2d.draw(bullet[n].getShape());
            }
        }
    }

    public void drawAsteroids(){
        for (int n=0; n<ASTEROIDS; n++){
            if (ast[n].isAlive()){
                g2d.setTransform(identity);
                g2d.translate(ast[n].getX(), ast[n].getY());
                g2d.rotate(Math.toRadians(ast[n].getMoveAngle()));
                g2d.setColor(Color.DARK_GRAY);
                g2d.fill(ast[n].getShape());
            }
        }
    }

    public void paint(Graphics g){
        g.drawImage(backbuffer, 0, 0, this);
    }

    public void start(){
        gameloop = new Thread(this);
        gameloop.start();
    }

    public void run(){
        Thread t = Thread.currentThread();

        while (t == gameloop){
            try {
                gameUpdate();
                Thread.sleep(20);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            repaint();
        }
    }

    public void stop(){
        gameloop = null;
    }

    public void gameUpdate(){
        updateShip();
        updateBullets();
        updateAsteroids();
        checkCollisions();
    }

    public void updateShip(){
        ship.incX(ship.getVelX());

        if (ship.getX() < -10){
            ship.setX(getSize().width + 10);
        } else if (ship.getX() > getSize().width +10){
            ship.setX(-10);
        }

        ship.incY(ship.getVelY());

        if (ship.getY() < -10){
            ship.setY(getSize().height + 10);
        } else if (ship.getY() > getSize().height + 10){
            ship.setY(-10);
        }
    }

    public void updateBullets(){
        for (int n = 0; n<BULLETS; n++){
            if (bullet[n].isAlive()){
                bullet[n].incX(bullet[n].getVelX());

                if (bullet[n].getX() < 0 || bullet[n].getX() > getSize().width) {
                    bullet[n].setAlive(false);
                }

                bullet[n].incY(bullet[n].getVelY());

                if (bullet[n].getY() < 0 || bullet[n].getX() > getSize().height) {
                    bullet[n].setAlive(false);
                }
            }
        }
    }

    public void updateAsteroids(){
        for (int n =0; n< ASTEROIDS; n++){
            if (ast[n].isAlive()){

                ast[n].incX(ast[n].getVelX());

                if (ast[n].getX() < -20)
                    ast[n].setX(getSize().width + 20);
                else if (ast[n].getX() > getSize().width + 20)
                    ast[n].setX(-20);

                ast[n].incY(ast[n].getVelY());

                if (ast[n].getY() < -20)
                    ast[n].setY(getSize().height + 20);
                else if (ast[n].getY() > getSize().height + 20)
                    ast[n].setY(-20);

                ast[n].incMoveAngle(ast[n].getRotationVelocity());

                if (ast[n].getMoveAngle() < 0)
                    ast[n].setMoveAngle(360 - ast[n].getRotationVelocity());
                else if (ast[n].getMoveAngle() > 360){
                    ast[n].setMoveAngle(ast[n].getRotationVelocity());
                }
            }
        }
    }

    public void checkCollisions(){
        for (int m =0; m<ASTEROIDS; m++){
            if (ast[m].isAlive()){
                for(int n =0; n< BULLETS; n++){
                    if (bullet[n].isAlive()){
                        if (ast[m].getBounds().contains(bullet[n].getX(), bullet[n].getY())){
                            bullet[n].setAlive(false);
                            ast[m].setAlive(false);
                            continue;
                        }
                    }
                }

                if (ast[m].getBounds().intersects(ship.getBounds())){
                    ast[m].setAlive(false);
                    ship.setX(320);
                    ship.setY(240);
                    ship.setFaceAngle(0);
                    ship.setVelX(0);
                    ship.setVelY(0);
                    continue;
                }
            }
        }
    }

    public void keyReleased(KeyEvent k){}
    public void keyTyped(KeyEvent k){}
    public void keyPressed(KeyEvent k){
        int keyCode = k.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_LEFT:
                if (ship.getFaceAngle() -5 < 0)
                    ship.incFaceAngle(360 - 5);
                else ship.incFaceAngle(-5);
                break;
            case KeyEvent.VK_RIGHT:
                if (ship.getFaceAngle() +5 > 360)
                    ship.setFaceAngle(ship.getFaceAngle() - 355);
                else ship.incFaceAngle(5);
                break;
            case KeyEvent.VK_UP:
                ship.setMoveAngle(ship.getFaceAngle()-90);
                ship.incVelX(calcAngleMoveX(ship.getMoveAngle()*0.1));
                ship.incVelY(calcAngleMoveY(ship.getMoveAngle()*0.1));
                break;
            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                currentBullet++;
                if (currentBullet > BULLETS -1) currentBullet = 0;
                bullet[currentBullet].setAlive(true);

                bullet[currentBullet].setX(ship.getX());
                bullet[currentBullet].setY(ship.getY());
                bullet[currentBullet].setMoveAngle(ship.getFaceAngle() - 90);

                double angle = bullet[currentBullet].getMoveAngle();
                double svx = 2.0;
                double svy = 2.0;
                bullet[currentBullet].setVelX(svx + calcAngleMoveX(angle)*2);
                bullet[currentBullet].setVelY(svy + calcAngleMoveY(angle)*2);
                break;
        }
    }

    public double calcAngleMoveX(double angle){
        return (double)(Math.cos(angle*Math.PI/180));
    }
    public double calcAngleMoveY(double angle){
        return (double)(Math.sin(angle*Math.PI/180));
    }

    public static void main(String[] args) {

    }
}
