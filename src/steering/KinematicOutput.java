package steering;

import processing.core.PVector;

@SuppressWarnings("WeakerAccess")

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
