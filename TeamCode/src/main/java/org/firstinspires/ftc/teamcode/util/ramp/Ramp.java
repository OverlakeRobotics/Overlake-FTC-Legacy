package org.firstinspires.ftc.teamcode.util.ramp;

import android.content.res.Resources;

abstract public class Ramp
{
    // Domain and range of the ramp functions
    // Domain of expRamp is (x1, x2) and range is (y1, y2)
    // logRamp is the inverse of expRamp, so its domain
    // and range are (y1, y2) to (x1, x2)
    public double x1;
    public double y1;
    public double x2;
    public double y2;

    public Ramp(double x1, double y1, double x2, double y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    abstract public double value(double x);
    abstract public double inverse(double y);

    public Double CheckDomain(double x)
    {
        if (this.x1 < this.x2)
        {
            if (x < this.x1)
                return new Double(this.y1);
            if (x > this.x2)
                return new Double(this.y2);
        }
        else
        {
            if (x < this.x2)
                return new Double(this.y2);
            if (x > this.x1)
                return new Double(this.y1);
        }

        return null;
    }

    public Double CheckRange(double y)
    {
        if (this.y1 < this.y2)
        {
            if (y < this.y1)
                return new Double(this.x1);
            if (y > this.y2)
                return new Double(this.x2);
        }
        else
        {
            if (y < this.y2)
                return new Double(this.x2);
            if (y > this.y1)
                return new Double(this.x1);
        }

        return null;
    }
}

