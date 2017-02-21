package implementations;

import processing.core.PApplet;
import processing.core.PVector;
import helper.*;
import steering.KinematicOutput;
import objects.Character;
import objects.Kinematic;
import movement.Seek;

import java.util.ArrayList;

/**
 * Created by ujansengupta on 2/14/17.
 */

@SuppressWarnings("WeakerAccess")

public class BasicMotion implements AnimationControls
{

    PApplet app;
    int scrWidth, scrHeight;
    int offset = 20;
    int maxVelocity = 3;
    int targetIndex = 0;

    float ROS = 3;                             //For basic motion, ROS must be <= maxVelocity, or the object will constantly update targets

    ArrayList<PVector> targetList = new ArrayList<>();

    public Character character;
    PVector targetPos;

    KinematicOutput seekOutput;

    public BasicMotion(PApplet app, int scrWidth, int scrHeight)
    {
        this.app = app;
        this.scrWidth = scrWidth;
        this.scrHeight = scrHeight;
        seekOutput = new KinematicOutput();

        assignTargets();

        PVector startPos = targetList.get(targetIndex);
        character = new Character(app, new PVector(startPos.x, startPos.y));
        character.initCrumbs();
        targetPos = acquireNextTarget();

        start();
    }

    public void update()
    {
        if (character.state == Kinematic.State.MOVING)
        {
            character.velocity = Seek.getKinematic(character.position, targetPos, maxVelocity).velocity;
            character.orientation = character.getOrientation();
            character.move();

            if (character.velocity.mag() < ROS)
                targetPos = acquireNextTarget();

            character.drawShape();
            character.drawCrumbs(true);                    //True for trail, False for regular crumbs
        }
    }

    public PVector acquireNextTarget()
    {
        if (targetIndex == targetList.size() - 1)
            targetIndex = 0;
        else
            targetIndex++;

        if (targetIndex == 1)
            character.clearCrumbs();

        return targetList.get(targetIndex);
    }

    public void assignTargets()
    {
        targetList.add(0, new PVector(offset, scrHeight-offset));                  //Bottom left
        targetList.add(1, new PVector(scrWidth - offset, scrHeight - offset));     //Bottom right
        targetList.add(2, new PVector(scrWidth - offset, offset));                 //Top right
        targetList.add(3, new PVector(offset, offset));                            //Top left
    }

    @Override
    public void start() {
        character.state = Kinematic.State.MOVING;
    }

    @Override
    public void pause() {
        app.noLoop();
        character.state = Kinematic.State.PAUSED;
    }

    @Override
    public void restart() {
        app.loop();
        character.state = Kinematic.State.MOVING;
    }
}
