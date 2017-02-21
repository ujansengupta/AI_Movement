import implementations.BasicMotion;
import implementations.FlockingImplementation;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by ujansengupta on 2/14/17.
 */

@SuppressWarnings("WeakerAccess")

public class MainApp extends PApplet
{
    int scrWidth = 1200, scrHeight = 800;

    /*implementations.ArriveImplementation arrive;
    int startCounter = 0;*/

    //implementations.WanderImplementation wander;

    //FlockingImplementation flocking;

    BasicMotion motion;

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
        //arrive = new implementations.ArriveImplementation(this, scrWidth, scrHeight);
        //wander = new implementations.WanderImplementation(this, scrWidth, scrHeight, null);

        //flocking = new FlockingImplementation(this, scrWidth, scrHeight, 10);

        motion = new BasicMotion(this, scrWidth, scrHeight);

        frameRate(60);
    }

    public void draw()
    {
        background(170);
        //flocking.update();
        //wander.update();
        //arrive.update();
        motion.update();

    }

    public void keyPressed()
    {
        if (keyCode == 32)
        {
            switch (motion.character.state)                       // arrive.character.state || wander.character.state
            {
                case PAUSED:
                    //flocking.restart();
                    //wander.restart();
                    //arrive.restart();
                    motion.restart();
                    break;
                case MOVING:
                    //flocking.pause();
                    //wander.pause();
                    //arrive.pause();
                    motion.pause();
                    break;
            }

        }
    }

    /* Uncomment for Arrive implementation*/

    public void mousePressed()
    {
        /*checkStart();
        arrive.changeTarget(new PVector(mouseX, mouseY));*/

        //flocking.spawnLeader(new PVector(mouseX, mouseY));
    }

    /*
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
