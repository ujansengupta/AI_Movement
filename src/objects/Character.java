package objects;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import implementations.WanderImplementation;

@SuppressWarnings("WeakerAccess")

public class Character extends GameObject
{

    static float CHAR_DIAMETER = 20;
    PVector CHAR_COLOR = new PVector(0, 0, 0);

    Crumbs crumbs;
    WanderImplementation wander;

    public Character(PApplet app, PVector startPos)
    {
        super(app, startPos, CHAR_DIAMETER);
        setColor(CHAR_COLOR);
    }

    public void changeColor(PVector color)
    {
        CHAR_COLOR = new PVector(color.x, color.y, color.z);
        setColor(CHAR_COLOR);
    }

    public void move()
    {
        position.x += velocity.x;
        position.y += velocity.y;

        rotation = mapToRange(rotation);
        orientation += rotation;
    }


    public static float mapToRange(float rotation) {
        float r = rotation % (2 * PConstants.PI);
        if (Math.abs(r) <= Math.PI)
            return r;
        else {
            if (r > Math.PI)
                return (r - 2 * PConstants.PI);
            else
                return (r + 2 * PConstants.PI);
        }
    }

    public void initCrumbs()
    {
        if (crumbs == null)
            crumbs = new Crumbs(this.app, this);
    }

    public void drawCrumbs(boolean drawTail)
    {
        crumbs.drawCrumbs(drawTail);
    }

    public void clearCrumbs()
    {
        crumbs.clearCrumbs();
    }

    public void enableWander(int scrWidth, int scrHeight)
    {
        wander = new WanderImplementation(this.app, scrWidth, scrHeight, this);
    }

    public void changeWanderVelocity(float velocity)
    {
        wander.changeVelocity(velocity);
    }

    public void updateWander()
    {
        wander.update();
    }



    public PVector getLastCrumbPosition()
    {
        return crumbs.lastCrumb;
    }

    public void checkBounds(int scrWidth, int scrHeight)
    {
        if (this.position.x < 0 || this.position.x > scrWidth || this.position.y < 0 || this.position.y > scrHeight)
            this.orientation += Math.PI;

    }

}
