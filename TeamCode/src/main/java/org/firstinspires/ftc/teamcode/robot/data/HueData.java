package org.firstinspires.ftc.teamcode.robot.data;

import com.google.gson.annotations.Expose;

public class HueData
{
    @Expose public double hueMin;
    @Expose public double hueMax;
    @Expose public double hueAverage;

    @Expose public double red;
    @Expose public double blue;
    @Expose public double green;

    public boolean hasData;

    private double runningTotal;
    private int count;

    public HueData()
    {
        this.hueMin = 180.0;
        this.hueMax = -180.0;
        this.hueAverage = 0.0;
        this.runningTotal = 0.0;
        this.count = 0;
    }

    public static double fromRGB(int r, int g, int b)
    {
        double y = Math.sqrt(3) * (g - b);
        double x = 2 * r - g - b;
        return Math.atan2(y, x) * (360.0 / (2 * Math.PI));
    }

    public void addSample(double value)
    {
        hasData = true;

        if (value < this.hueMin)
        {
            this.hueMin = value;
        }

        if (value > this.hueMax)
        {
            this.hueMax = value;
        }

        this.runningTotal += value;
        this.count++;
        this.hueAverage = this.runningTotal / this.count;
    }

    public boolean isHue(double value)
    {
        return (value >= hueMin) && (value <= hueMax);
    }
}

