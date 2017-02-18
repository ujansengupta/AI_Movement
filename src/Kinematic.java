import processing.core.PVector;

/**
 * Created by ujansengupta on 2/14/17.
 */

public class Kinematic
{
    public enum State{MOVING, PAUSED}

    public PVector position, velocity;
    public float orientation, rotation;

    public Kinematic()
    {
        position = new PVector();
        velocity = new PVector();
        orientation = 0f;
        rotation = 0f;
    }

}
