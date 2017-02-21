package steering;

import processing.core.PVector;

/**
 * Created by ujansengupta on 2/14/17.
 */



public class KinematicOutput
{
    public PVector velocity;
    public float rotation;

    public KinematicOutput()
    {
        velocity = new PVector(0, 0);
        rotation = 0f;
    }
}
