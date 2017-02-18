import helper.AnimationControls;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ujansengupta on 2/17/17.
 */
public class Flocking implements AnimationControls
{
    PApplet app;
    static Random rand;

    int scrWidth, scrHeight;
    int numBoids;
    int radiusOfSeparation = 30;
    int cohesionDiv = 2000, alignmentDiv = 20;

    float weightSeparation = 0.5f, weightCohesion = 0.3f, weightAlignment = 0.2f;

    Kinematic.State state;
    ArrayList<Character> boidList;
    PVector centerOfMass;

    PVector cohesion, separation, alignment;
    PVector tempSeparation, tempAlignment;

    public Flocking(PApplet app, int scrWidth, int scrHeight, int numBoids)
    {
        this.app = app;
        this.scrWidth = scrWidth;
        this.scrHeight = scrHeight;
        this.numBoids = numBoids;
        centerOfMass = new PVector(0, 0);
        cohesion = new PVector(0, 0);
        separation = new PVector(0, 0);
        alignment = new PVector(0, 0);
        tempSeparation = new PVector(0,0);
        tempAlignment = new PVector(0,0);

        rand = new Random();
        boidList = new ArrayList<>();

        for (int i = 0; i<numBoids; i++)
        {
            boidList.add(new Character(this.app, new PVector(randInt(0, scrWidth), randInt(0, scrHeight))));
        }

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

        for (Character boid : boidList)
        {
            cohesion = updateCohesion(boid);
            separation = updateSeparation(boid);
            alignment = updateAlignment(boid);

            boid.velocity.add(separation.mult(weightSeparation));
            boid.velocity.add(cohesion.mult(weightCohesion));
            boid.velocity.add(alignment.mult(weightAlignment));

            if (checkBounds(boid))
            {
                boid.rotation += Math.PI;
                boid.velocity = new PVector(- boid.velocity.x, - boid.velocity.y);
            }

            boid.move();
            boid.orientation = boid.getOrientation();
        }
    }

    public PVector updateCohesion(Character boid)
    {
        return PVector.sub(centerOfMass, boid.position).div(cohesionDiv);
    }

    public  PVector updateSeparation(Character boid)
    {
        tempSeparation = new PVector(0,0);

        for (Character c : boidList)
        {
            if (c != boid && PVector.sub(c.position, boid.position).mag() < radiusOfSeparation)
                tempSeparation.sub(c.position.sub(boid.position));
        }

        return tempSeparation;
    }

    public PVector updateAlignment(Character boid)
    {
        tempAlignment = new PVector(0,0);

        for (Character c : boidList)
        {
            if (c != boid)
                tempAlignment.add(c.velocity);
        }

        tempAlignment.div(numBoids - 1);

        return tempAlignment.sub(boid.velocity).div(alignmentDiv);
    }

    public void getCOM()
    {
        centerOfMass.setMag(0);

        for (Character boid : boidList)
            centerOfMass.add(boid.position);

        centerOfMass.div(numBoids);
    }

    public boolean checkBounds(Character boid)
    {
        if (boid.position.x < 0 || boid.position.x > scrWidth || boid.position.y < 0 || boid.position.y > scrHeight)
            return true;

        return false;
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
