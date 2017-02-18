package helper;

public interface AnimationControls
{
    enum State{MOVING, PAUSED}

    void start();

    void pause();

    void restart();

}
