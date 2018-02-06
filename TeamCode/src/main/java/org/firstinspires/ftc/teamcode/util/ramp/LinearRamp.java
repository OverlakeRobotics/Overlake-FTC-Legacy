package org.firstinspires.ftc.teamcode.util.ramp;

/*
The linear ramp maps from the domain to the range at a constant rate.
 */

public class LinearRamp extends Ramp
{
    // Computed constants for the linear ramp
    double m;
    double b;

    public LinearRamp(double x1, double y1, double x2, double y2)
    {
        super(x1, y1, x2, y2);

        // Compute the constants for the linear ramp
        // f(x) = m * x + b that goes through (x1, y1) and (x2, y2)
        m = (y2 - y1) / (x2 - x1);

        // y = m*x + b
        // y1 = m * x1 + b
        // so, b = y1 - m*x1
        b = y1 - m*x1;
    }

    @Override
    public double value(double x)
    {
        if (x < this.x1)
            return this.y1;
        if (x > this.x2)
            return this.y2;

        return m * x + b;
    }

    @Override
    public double inverse(double y)
    {
        /*
        y = f(x) = m*x + b
        (y - b) = m*x
        x = (y - b)/m
        */

        return (y - b)/m;
    }
}
