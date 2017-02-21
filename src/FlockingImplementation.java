import helper.AnimationControls;
import movement.Flocking;
import movement.Wander;
import object.Character;
import object.Kinematic;
import processing.core.PApplet;
import processing.core.PVector;
import steering.SteeringOutput;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ujansengupta on 2/17/17.
 */

@SuppressWarnings("WeakerAccess")

public class FlockingImplementation implements AnimationControls
{
    PApplet app;
    static Random rand;

    int scrWidth, scrHeight;
    int numBoids;
    int radiusOfSeparation = 50;

    float weightSeparation = 0.8f, weightCohesion = 0.1f, weightAlignment = 0.1f, weightGoal = 0.5f;
    float maxCohesionVelocity = 5f, maxFleeVelocity = 5f, maxAlignVelocity = 5f, maxGoalVelocity = 5f;
    float MAX_VELOCITY = 1.5f;
    float maxRotation = 2 * (float)Math.PI, maxAngularAccel = 0.01f;
    float ROS = 0.5f;
    float ROD = 2f;

    ArrayList<Character> boidList;

    Character leader;

    PVector cohesionVelocity, separationVelocity, alignmentVelocity, goalVelocity;
    PVector tempSeparation, tempAlignment;
    PVector centerOfMass;
    PVector tempVelocity;

    SteeringOutput steering;

    WanderImplementation leaderWander;

    Kinematic.State state;

    public FlockingImplementation(PApplet app, int scrWidth, int scrHeight, int numBoids)
    {
        this.app = app;
        this.scrWidth = scrWidth;
        this.scrHeight = scrHeight;
        this.numBoids = numBoids;

        init();
    }

    public void init()
    {
        centerOfMass = new PVector(0, 0);
        cohesionVelocity = new PVector(0, 0);
        separationVelocity = new PVector(0, 0);
        alignmentVelocity = new PVector(0, 0);
        goalVelocity = new PVector(0,0);

        tempSeparation = new PVector(0,0);
        tempAlignment = new PVector(0,0);
        tempVelocity = new PVector(0,0);

        centerOfMass = new PVector(0,0);

        steering = new SteeringOutput();

        rand = new Random();
        boidList = new ArrayList<>();

        for (int i = 0; i<numBoids; i++)
        {
            boidList.add(new Character(this.app, new PVector(randInt(0, scrWidth), randInt(0, scrHeight))));
        }

        leader = boidList.get(0);
        leaderWander = new WanderImplementation(this.app, scrWidth, scrHeight, leader);
        leaderWander.changeVelocity(MAX_VELOCITY);
        leader.initCrumbs();
        leader.changeColor(new PVector(255, 0, 0));

        state = Kinematic.State.MOVING;
    }

    public void update()
    {
        if (state == Kinematic.State.MOVING)
            updateBoids();

        for (Character boid : boidList)
            boid.drawShape();
    }

    public void updateBoids()
    {
        getCOM();
        leaderWander.update();

        for (Character boid : boidList)
        {

            tempVelocity.setMag(0);

            cohesionVelocity = Flocking.updateCohesion(boid, centerOfMass, maxCohesionVelocity, radiusOfSeparation);
            separationVelocity = Flocking.updateSeparation(boid, boidList, maxFleeVelocity, radiusOfSeparation);
            alignmentVelocity = Flocking.updateAlignment(boid, boidList, maxAlignVelocity);
            goalVelocity = Flocking.updateGoal(boid, leader, maxGoalVelocity, radiusOfSeparation);

            tempVelocity.add(cohesionVelocity.mult(weightCohesion));
            tempVelocity.add(separationVelocity.mult(weightSeparation));
            tempVelocity.add(alignmentVelocity.mult(weightAlignment));
            tempVelocity.add(goalVelocity.mult(weightGoal));

            if (tempVelocity.mag() > MAX_VELOCITY)
            {
                tempVelocity.normalize();
                tempVelocity.mult(MAX_VELOCITY);
            }

            if (boid != leader)
            {
                steering = Wander.getSteeringAlign(boid, tempVelocity.heading(), maxRotation, maxAngularAccel, ROS, ROD);
                boid.checkBounds(scrWidth, scrHeight);
                boid.velocity = new PVector(tempVelocity.x, tempVelocity.y);
                boid.rotation += steering.angular;
                boid.move();
            }

        }
    }

    public void getCOM()
    {
        centerOfMass.setMag(0);

        for (Character boid : boidList)
            centerOfMass.add(boid.position);

        centerOfMass.div(numBoids);
    }

    public static int randInt(int min, int max)
    {
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void start() {

    }

    @Override
    public void pause()
    {
        app.noLoop();
        state = Kinematic.State.PAUSED;
    }

    @Override
    public void restart()
    {
        app.loop();
        state = Kinematic.State.MOVING;
    }
}
