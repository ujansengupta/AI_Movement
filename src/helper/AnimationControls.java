package helper;

import objects.Kinematic;

public interface AnimationControls
{

    void start();

    void pause();

    void restart();

    Kinematic.State getState();

}
