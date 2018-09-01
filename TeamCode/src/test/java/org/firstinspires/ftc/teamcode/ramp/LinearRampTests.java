package org.firstinspires.ftc.teamcode.ramp;

import junit.framework.Assert;

import org.junit.Test;

public class LinearRampTests
{
    @Test
    public void Value_Xis2_Returns4()
    {
        LinearRamp ramp = new LinearRamp(0,0,4,8);
        Assert.assertEquals(4d, ramp.value(2), 0.0000001);
    }

    @Test
    public void Value_XisNegative1_Returns4()
    {
        LinearRamp ramp = new LinearRamp(0,0,4,8);
        Assert.assertEquals(0d, ramp.value(-1), 0.0000001);
    }

    @Test
    public void Value_Xis10_Returns4()
    {
        LinearRamp ramp = new LinearRamp(0,0,4,8);
        Assert.assertEquals(8d, ramp.value(10), 0.0000001);
    }

    @Test
    public void Value_Yis4_Returns2()
    {
        LinearRamp ramp = new LinearRamp(0,0,4,8);
        Assert.assertEquals(2d, ramp.inverse(4), 0.0000001);
    }
}
