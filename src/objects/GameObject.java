package objects;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

@SuppressWarnings("WeakerAccess")

public class GameObject extends Kinematic
{
    protected PApplet app;
    protected PShape object, circle, triangle;

    float HEAD_DIA;
    PVector COLOR;

    public Kinematic.State state;

    public GameObject(PApplet theApplet, PVector startPos, float diameter)
    {
        app = theApplet;
        HEAD_DIA = diameter;

        float XPos = 0;
        float YPos = 0;

        if (app != null)
        {
            object = app.createShape(app.GROUP);
            circle = app.createShape(app.ELLIPSE, XPos, YPos, diameter, diameter);
            triangle = app.createShape(app.TRIANGLE, XPos + diameter/5f, YPos - diameter/2.15f,
                    XPos + diameter/5f, YPos + diameter/2.15f, XPos + diameter, YPos);

            object.addChild(circle);
            object.addChild(triangle);
        }

        position = startPos;
        //orientation = (float)Math.PI/2;
        velocity = new PVector();
    }

    public void setColor( PVector color)
    {
        COLOR = color;
    }

    public float getOrientation()
    {
        if (velocity.mag()>0)
            return velocity.heading(); //(float)Math.atan2(velocity.y, velocity.x);
        else
            return orientation;
    }


    public void drawShape()
    {
        app.pushMatrix();
        object.rotate(orientation);// - (float)Math.PI/2);
        PShape[] children = object.getChildren();
        for (PShape child : children)
        {
            child.setStroke(app.color(COLOR.x, COLOR.y, COLOR.z, 255));
            child.setFill(app.color(COLOR.x, COLOR.y, COLOR.z, 255));
        }
        app.shape(object, position.x, position.y);
        object.resetMatrix();
        app.popMatrix();
    }

    public PShape getObject()
    {
        return object;
    }


}
