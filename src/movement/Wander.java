package movement;

import processing.core.PVector;
import steering.*;
import objects.Character;

@SuppressWarnings("WeakerAccess")

public class Wander
{
    static float timeToTargetRotation = 10f;

    public static KinematicOutput getKinematic(Character character, float maxVelocity, float maxRotation)
    {
        KinematicOutput output = new KinematicOutput();

        output.velocity = PVector.fromAngle(character.orientation).mult(maxVelocity);
        output.rotation = randomBinomial()*maxRotation;

        return output;
    }

    public static SteeringOutput getSteeringAlign(Character character, float targetRot, float maxRotation, float maxAccel, float ROS, float ROD)
    {
        SteeringOutput steering = new SteeringOutput();

        float rotation = targetRot - character.orientation;

        rotation = Character.mapToRange(rotation);
        float rotationSize = Math.abs(rotation);

        if (rotationSize < ROS)
        {
            character.rotation = 0;
            return new SteeringOutput();
        }

        if (rotationSize > ROD)
            targetRot = (rotation/rotationSize) * maxRotation;
        else
            targetRot = (rotation/rotationSize) * rotationSize * maxRotation / ROD;


        steering.angular = targetRot - character.rotation;
        steering.angular /= timeToTargetRotation;

        float angularAcc = Math.abs(steering.angular);

        if (angularAcc > maxAccel)
        {
            steering.angular /= angularAcc;
            steering.angular *= maxAccel;
        }

        return steering;
    }

    public static float randomBinomial()
    {
        return (float)(Math.random() - Math.random());
    }
}
