package object;

import object.GameObject;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by ujansengupta on 2/11/17.
 */

@SuppressWarnings("WeakerAccess")

public class Crumbs
{

    public PApplet app;

    int prevTime;

    ArrayList<PVector> crumbList = new ArrayList<>();
    GameObject object;

    PShape breadCrumb;

    PVector lastCrumb;

    public Crumbs(PApplet theApplet, GameObject character)
    {
        app = theApplet;
        object = character;
        prevTime = app.millis();

        lastCrumb = new PVector(character.position.x, character.position.y);
    }

    public void drawCrumbs(boolean drawTrail)
    {

        int currTime = app.millis();

        if (currTime-prevTime > 300)
        {
            crumbList.add(new PVector(object.position.x, object.position.y));
            prevTime = currTime;

            lastCrumb = new PVector(crumbList.get(crumbList.size() - 1).x, crumbList.get(crumbList.size() - 1).y);

            if (crumbList.size() > 5 && drawTrail)
            {
                crumbList.remove(0);
            }
        }


        for (PVector crumb : crumbList)
        {
            breadCrumb = app.createShape(app.ELLIPSE, crumb.x, crumb.y, 2, 2);
            breadCrumb.setFill(0);

            app.shape(breadCrumb);
        }
    }

    public void clearCrumbs()
    {
        crumbList.clear();
    }



}
