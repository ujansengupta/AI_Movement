package objects;

import processing.core.PVector;

@SuppressWarnings("WeakerAccess")


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
