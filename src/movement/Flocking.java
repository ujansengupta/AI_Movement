package movement;

import movement.Arrive;
import processing.core.PVector;

import java.util.ArrayList;
import  object.Character;

/**
 * Created by ujansengupta on 2/21/17.
 */
public class Flocking
{
    public static PVector updateCohesion(Character boid, PVector centerOfMass, float maxCohesionVelocity, int radiusOfSeparation)
    {
        return Arrive.getKinematic(boid.position, centerOfMass, maxCohesionVelocity, radiusOfSeparation).velocity;
    }

    public static PVector updateSeparation(Character boid, ArrayList<Character> boidList, float maxFleeVelocity, int radiusOfSeparation)
    {
        PVector tempSeparation = new PVector(0,0);

        for (Character otherBoid : boidList)
        {
            if (otherBoid != boid && PVector.sub(otherBoid.position, boid.position).mag() < radiusOfSeparation)
                tempSeparation.add(PVector.sub(boid.position, otherBoid.position));
        }

        if (tempSeparation.mag() > maxFleeVelocity)
        {
            tempSeparation.normalize();
            tempSeparation.mult(maxFleeVelocity);
        }

        return tempSeparation;
    }

    public static PVector updateAlignment(Character boid, ArrayList<Character> boidList, float maxAlignVelocity)
    {
        PVector tempAlignment = new PVector(0,0);

        for (Character c : boidList)
        {
            if (c != boid)
                tempAlignment.add(c.velocity);
        }

        tempAlignment.div(boidList.size() - 1);

        //COM.velocity = tempAlignment;
        //COM.orientation = tempAlignment.heading();

        return PVector.sub(tempAlignment, boid.velocity).mult(maxAlignVelocity);
    }

    public static PVector updateGoal(Character boid, Character leader, float maxGoalVelocity, int radiusOfSeparation)
    {

        return Arrive.getKinematic(boid.position, leader.getLastCrumbPosition(), maxGoalVelocity, radiusOfSeparation).velocity;
    }
}
