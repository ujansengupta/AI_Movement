import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * Created by ujansengupta on 2/14/17.
 */


public class Character extends GameObject
{

    private static float CHAR_DIAMETER = 20;
    private static PVector CHAR_COLOR = new PVector(0, 0, 0);


    public Character(PApplet app, PVector startPos)
    {
        super(app, startPos, CHAR_DIAMETER, CHAR_COLOR);
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
}
