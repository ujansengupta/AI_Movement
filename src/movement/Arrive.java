package movement;

import processing.core.PVector;
import steering.*;
import object.Character;

/**
 * Created by ujansengupta on 2/16/17.
 */
public class Arrive
{
    static float timeToTargetVelocity = 10f;
    static int prevTime = 0, currTime = 0;

    public static KinematicOutput getKinematic(PVector character, PVector target, float maxVelocity, float ROS)
    {
        KinematicOutput output = new KinematicOutput();
        output.velocity = PVector.sub(target, character);

        if (output.velocity.mag() < ROS)
            return new KinematicOutput();                   // return default kinematic output with no velocity or rotation

        output.velocity.div(timeToTargetVelocity);

        if (output.velocity.mag() > maxVelocity)
        {
            output.velocity.normalize();
            output.velocity.mult(maxVelocity);
        }

        return output;
    }

    public static SteeringOutput getSteering(Character character, PVector target, float maxSpeed, float maxAcceleration, float ROS, float ROD)
    {

        SteeringOutput output = new SteeringOutput();
        float targetSpeed;

        PVector direction = new PVector(target.x - character.position.x, target.y - character.position.y);
        float distance = direction.mag();

        if (distance < ROS)
        {
            character.velocity.setMag(0);
            return new SteeringOutput();
        }

        if (distance > ROD)
            targetSpeed = maxSpeed;
        else
            targetSpeed = maxSpeed * direction.mag() / ROD;

        direction.normalize();
        direction.mult(targetSpeed);

        output.linear = PVector.sub(direction, character.velocity);             //linear acceleration

        //output.linear = new PVector(direction.x, direction.y);
        output.linear.div(timeToTargetVelocity);

        if (Math.abs(output.linear.mag()) > maxAcceleration)
        {
            output.linear.normalize();
            output.linear.mult(maxAcceleration);
        }

        return output;
    }
}
