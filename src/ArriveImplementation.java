import helper.AnimationControls;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by ujansengupta on 2/14/17.
 */

@SuppressWarnings("WeakerAccess")

public class ArriveImplementation implements AnimationControls
{
    enum Type{KINEMATIC, STEERING}

    PApplet app;
    int scrWidth, scrHeight;

    float maxVelocity = 7, maxAcceleration = 1;                         // Keep acceleration low and velocity relatively high to see the effect of acceleration
    float ROS = 3;
    float ROD = 30;

    Character character;
    Crumbs crumbs;
    PVector targetPos, startPos;

    Type type;


    public ArriveImplementation(PApplet app, int scrWidth, int scrHeight)
    {
        this.app = app;
        this.scrWidth = scrWidth;
        this.scrHeight = scrHeight;

        startPos = new PVector(scrWidth/2, scrHeight/2);
        character = new Character(app, new PVector(startPos.x, startPos.y));
        crumbs = new Crumbs(this.app, character);
        targetPos = new PVector(startPos.x, startPos.y);

        type = Type.STEERING;                                   //Change between STEERING and KINEMATIC for different implementations

    }

    public void update()
    {
        if (character.state == Kinematic.State.MOVING)
        {
            switch (type)
            {
                case KINEMATIC:
                    character.velocity = Arrive.getKinematic(character.position, targetPos, maxVelocity, ROS).velocity;
                    break;
                case STEERING:
                    character.velocity.add(Arrive.getSteering(character, targetPos, maxVelocity, maxAcceleration, ROS, ROD).linear);
                    break;
            }

            character.orientation = character.getOrientation();
            character.move();
        }

        character.drawShape();
        crumbs.drawCrumbs(true);                    //True for trail, False for regular crumbs
    }

    public void changeTarget(PVector target)
    {
        this.targetPos = target;
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
