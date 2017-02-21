package movement;

import processing.core.PVector;
import java.util.ArrayList;
import  objects.Character;

@SuppressWarnings("WeakerAccess")


public class Flocking
{
    public static PVector updateCohesion(Character boid, PVector centerOfMass, float maxCohesionVelocity, int radiusOfSeparation)
    {
        return Arrive.getKinematic(boid.position, centerOfMass, maxCohesionVelocity, radiusOfSeparation).velocity;
    }

    public static PVector updateSeparation(Character boid, ArrayList<Character> boidList, ArrayList<Character> leaderList, float maxFleeVelocity, int radiusOfSeparation)
    {
        PVector tempSeparation = new PVector(0,0);

        for (Character otherBoid : boidList)
        {
            if (otherBoid != boid && PVector.sub(otherBoid.position, boid.position).mag() < radiusOfSeparation)
                tempSeparation.add(PVector.sub(boid.position, otherBoid.position));
        }

        for (Character leader : leaderList)
        {
            if (PVector.sub(leader.position, boid.position).mag() < radiusOfSeparation)
                tempSeparation.add(PVector.sub(boid.position, leader.position));
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

    public static Character getClosestLeader(Character boid, ArrayList<Character> leaderList)
    {
        float distanceFromLeader = 10000000;
        Character leader = null;

        for (Character tempLeader : leaderList)
        {
            if (PVector.sub(tempLeader.position, boid.position).mag() < distanceFromLeader)
            {
                distanceFromLeader = PVector.sub(tempLeader.position, boid.position).mag();
                leader = tempLeader;
            }
        }

        return leader;
    }
}
