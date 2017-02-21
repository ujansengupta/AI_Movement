package steering;

import processing.core.PVector;

@SuppressWarnings("WeakerAccess")

public class SteeringOutput
{
    public PVector linear;
    public float angular;

    public SteeringOutput()
    {
        linear = new PVector(0, 0);
        angular = 0f;
    }

}
