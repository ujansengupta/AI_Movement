import implementations.BasicMotion;
import implementations.*;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by ujansengupta on 2/14/17.
 */

@SuppressWarnings("WeakerAccess")

public class MainApp extends PApplet
{
    int scrWidth = 1200, scrHeight = 800;

    enum Mode {BASIC, ARRIVE, WANDER, FLOCKING}

    BasicMotion motion;
    WanderImplementation wander;
    FlockingImplementation flocking;
    ArriveImplementation arrive;

    Mode mode;

    public static void main(String[] args)
    {
        PApplet.main("MainApp", args);
    }

    public void settings()
    {
        size(scrWidth, scrHeight);

        mode = Mode.FLOCKING;
    }

    public void setup()
    {
        switch (mode)
        {
            case BASIC:
                motion = new BasicMotion(this, scrWidth, scrHeight);
                break;
            case ARRIVE:
                arrive = new implementations.ArriveImplementation(this, scrWidth, scrHeight);
                break;
            case WANDER:
                wander = new implementations.WanderImplementation(this, scrWidth, scrHeight, null);
                break;
            case FLOCKING:
                flocking = new FlockingImplementation(this, scrWidth, scrHeight, 20);
                break;
        }

        frameRate(60);
    }

    public void draw()
    {
        background(170);

        switch (mode)
        {
            case BASIC:
                motion.update();
                break;
            case ARRIVE:
                arrive.update();
                break;
            case WANDER:
                wander.update();
                break;
            case FLOCKING:
                flocking.update();
                break;
        }
    }

    public void keyPressed()
    {
        if (keyCode == 32)
        {
            switch (flocking.state)                       // arrive.character.state || wander.character.state
            {
                case PAUSED:
                    restartAnimation();
                    break;
                case MOVING:
                    pauseAnimation();
                    break;
            }

        }
    }

    /* Uncomment for Arrive implementation*/

    public void mousePressed()
    {
        switch (mode)
        {
            case ARRIVE:
                arrive.changeTarget(new PVector(mouseX, mouseY));
                break;
            case FLOCKING:
                flocking.spawnLeader(new PVector(mouseX, mouseY));
                break;
        }
    }

    public void mouseDragged()
    {
        arrive.changeTarget(new PVector(mouseX, mouseY));
    }

    public void pauseAnimation()
    {
        switch (mode)
        {
            case BASIC:
                motion.pause();
                break;
            case ARRIVE:
                arrive.pause();
                break;
            case WANDER:
                wander.pause();
                break;
            case FLOCKING:
                flocking.pause();
                break;
        }
    }

    public void restartAnimation()
    {
        switch (mode)
        {
            case BASIC:
                motion.restart();
                break;
            case ARRIVE:
                arrive.restart();
                break;
            case WANDER:
                wander.restart();
                break;
            case FLOCKING:
                flocking.restart();
                break;
        }
    }

}
