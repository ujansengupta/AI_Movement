import processing.core.PApplet;

/**
 * Created by ujansengupta on 2/14/17.
 */

public class MainApp extends PApplet
{
    int scrWidth = 800, scrHeight = 800;

    /*ArriveImplementation arrive;
    int startCounter = 0;*/
    //WanderImplementation wander;

    Flocking flocking;

    public static void main(String[] args)
    {
        PApplet.main("MainApp", args);
    }

    public void settings()
    {
        size(scrWidth, scrHeight);
    }

    public void setup()
    {
        //arrive = new ArriveImplementation(this, scrWidth, scrHeight);
        //wander = new WanderImplementation(this, scrWidth, scrHeight);

        flocking = new Flocking(this, scrWidth, scrHeight, 5);

        frameRate(50);
    }

    public void draw()
    {
        background(170);
        flocking.update();
        //wander.update();
        //arrive.update();

        flocking.update();
    }

    public void keyPressed()
    {
        if (keyCode == 32)
        {
            switch (flocking.state)                       // arrive.character.state || wander.character.state
            {
                case PAUSED:
                    flocking.restart();
                    //wander.restart();
                    //arrive.restart();
                    break;
                case MOVING:
                    flocking.pause();
                    //wander.pause();
                    //arrive.pause();
                    break;
            }

        }
    }

    /*public void mousePressed()
    {
        checkStart();
        arrive.changeTarget(new PVector(mouseX, mouseY));
    }

    public void mouseDragged()
    {
        arrive.changeTarget(new PVector(mouseX, mouseY));
    }

    public void checkStart()
    {
        if (startCounter == 0)
        {
            arrive.start();
            startCounter++;
        }
    }*/

}
