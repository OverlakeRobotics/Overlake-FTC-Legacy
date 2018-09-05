package org.firstinspires.ftc.teamcode.scale;

import junit.framework.Assert;

import org.junit.Test;

public class LinearRampTests
{
    @Test
    public void Value_Xis2_Returns4()
    {
        IScale ramp = new LinearRamp(new Point(0, 0), new Point(4, 8));
        Assert.assertEquals(4d, ramp.scaleX(2), 0.0000001);
    }

    @Test
    public void Value_Yis4_Returns2()
    {
        IScale ramp = new LinearRamp(new Point(0, 0), new Point(4, 8));
        Assert.assertEquals(2d, ramp.scaleY(4), 0.0000001);
    }
}
