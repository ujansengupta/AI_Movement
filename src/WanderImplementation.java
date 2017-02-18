import helper.AnimationControls;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by ujansengupta on 2/16/17.
 */
public class WanderImplementation implements AnimationControls
{
    public enum Type{KINEMATIC, STEERING}

    PApplet app;
    int scrWidth, scrHeight;

    float maxVelocity = 3;

    /* Kinematic maxRotation = 0.5f; Steering maxRotation = 2 * (float)Math.PI */

    float maxRotation = 2 * (float)Math.PI, maxAngularAccel = 0.01f;

    float ROS = 1.5f;
    float ROD = 2f;

    Character character;
    Crumbs crumbs;
    PVector targetPos, startPos;

    Type type;

    KinematicOutput kinematic;
    SteeringOutput steering;

    float targetRotation;

    public WanderImplementation(PApplet app, int scrWidth, int scrHeight)
    {
        this.app = app;
        this.scrWidth = scrWidth;
        this.scrHeight = scrHeight;

        startPos = new PVector(scrWidth/2, scrHeight/2);
        character = new Character(app, new PVector(startPos.x, startPos.y));
        crumbs = new Crumbs(this.app, character);
        targetPos = new PVector(startPos.x, startPos.y);
        targetRotation = 0;

        type = Type.STEERING;                                   //Change between STEERING and KINEMATIC for different implementations

        start();
    }

    public void update()
    {
        if (character.state == Kinematic.State.MOVING)
        {
            switch (type)
            {
                case KINEMATIC:
                    kinematic = Wander.getKinematic(character, maxVelocity, maxRotation);
                    if (checkBounds()) {
                        character.rotation += Math.PI;
                        character.velocity = new PVector(-kinematic.velocity.x, -kinematic.velocity.y);
                    }
                    else {
                        character.velocity = kinematic.velocity;
                        character.rotation = kinematic.rotation;
                    }
                    break;
                case STEERING:
                    steering = Wander.getSteeringAlign(character, targetRotation, maxRotation, maxAngularAccel, ROS, ROD);
                    if (checkBounds()) {
                        character.rotation += Math.PI;
                        character.velocity = new PVector(- 2 * character.velocity.x, - 2 * character.velocity.y);
                    }
                    else {
                        if (character.rotation >= Math.PI)
                            character.rotation = 0;
                        character.velocity = PVector.fromAngle(character.orientation).mult(maxVelocity);
                        character.rotation += steering.angular;
                    }
                    break;
            }

            if (steering.angular == 0)
                targetRotation = Wander.randomBinomial() * maxRotation;       // UnComment for steering

            character.move();
        }

        character.drawShape();
        crumbs.drawCrumbs(true);                    //True for trail, False for regular crumbs
    }

    public boolean checkBounds()
    {
        if (character.position.x < 0 || character.position.x > scrWidth || character.position.y < 0 || character.position.y > scrHeight)
            return true;

        return false;
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
