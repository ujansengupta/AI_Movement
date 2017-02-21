package movement;

import processing.core.PVector;
import steering.*;

/**
 * Created by ujansengupta on 2/14/17.
 */
public class Seek
{
    public static KinematicOutput getKinematic(PVector character, PVector target, float maxVelocity)
    {
        KinematicOutput output = new KinematicOutput();
        output.velocity = PVector.sub(target, character);

        if (output.velocity.mag() > maxVelocity)
        {
            output.velocity.normalize();
            output.velocity.mult(maxVelocity);
        }

        return output;
    }

}
