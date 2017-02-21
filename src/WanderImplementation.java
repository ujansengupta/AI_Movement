import helper.AnimationControls;
import movement.Wander;
import object.Character;
import object.Crumbs;
import object.Kinematic;
import processing.core.PApplet;
import processing.core.PVector;
import steering.KinematicOutput;
import steering.SteeringOutput;

/**
 * Created by ujansengupta on 2/16/17.
 */

@SuppressWarnings("WeakerAccess")

public class WanderImplementation implements AnimationControls
{
    public enum Type{KINEMATIC, STEERING}

    PApplet app;
    int scrWidth, scrHeight;

    float maxVelocity = 1f;

    /* object.Kinematic maxRotation = 0.5f; Steering maxRotation = 2 * (float)Math.PI */

    float maxRotation = 2 * (float)Math.PI, maxAngularAccel = 0.0005f;

    float ROS = 1.5f;
    float ROD = 2.5f;

    Character character;
    PVector targetPos, startPos;

    Type type;

    KinematicOutput kinematic;
    SteeringOutput steering;

    float targetRotation;

    public WanderImplementation(PApplet app, int scrWidth, int scrHeight, Character character)
    {
        this.app = app;
        this.scrWidth = scrWidth;
        this.scrHeight = scrHeight;

        startPos = new PVector(scrWidth/2, scrHeight/2);
        this.character = (character == null) ? new Character(app, new PVector(startPos.x, startPos.y)) : character;
        this.character.initCrumbs();
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
                    character.checkBounds(scrWidth, scrHeight);
                    character.velocity = kinematic.velocity;
                    character.rotation = kinematic.rotation;
                    break;
                case STEERING:
                    steering = Wander.getSteeringAlign(character, targetRotation, maxRotation, maxAngularAccel, ROS, ROD);
                    character.checkBounds(scrWidth, scrHeight);
                    character.velocity = PVector.fromAngle(character.orientation).mult(maxVelocity);
                    character.rotation += steering.angular;
                    break;
            }

            if (steering.angular == 0)
                targetRotation = Wander.randomBinomial() * maxRotation;       // UnComment for steering

            character.move();
        }

        character.drawShape();
        character.drawCrumbs(true);     //True for trail, False for regular crumbs
    }


    public Character getCharacter()
    {
        return character;
    }

    public void changeVelocity(float velocity)
    {
        maxVelocity = velocity;
    }

    public void changeAngularAcc(float angularAcc)
    {
        maxAngularAccel = angularAcc;
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
