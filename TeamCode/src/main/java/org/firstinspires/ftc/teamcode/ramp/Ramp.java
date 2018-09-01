package org.firstinspires.ftc.teamcode.ramp;

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

    public boolean IsInDomain(double x)
    {
        if (this.x1 < this.x2)
        {
            return x >= this.x1 && x <= this.x2;
        }
        else
        {
            return x <= this.x1 && x >= this.x2;
        }
    }

    public boolean IsInRange(double y)
    {
        if (this.y1 < this.y2)
        {
            return y >= this.y1 && y <= this.y2;
        }
        else
        {
            return y <= this.y1 && y >= this.y2;
        }
    }
}

