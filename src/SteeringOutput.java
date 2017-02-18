import processing.core.PVector;

/**
 * Created by ujansengupta on 2/14/17.
 */
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
