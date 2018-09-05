package org.firstinspires.ftc.teamcode.scale;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LogarithmicRampTests
{
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void ScaleX_X1Is0_ThrowsException()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        new LogarithmicRamp(new Point(0, 0), new Point(2,2));
    }

    @Test
    public void ScaleX_X2Is0_ThrowsException()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        new LogarithmicRamp(new Point(0, 0), new Point(2,2));
    }

    @Test
    public void ScaleX_XIs2_ReturnsRampedValue()
    {
        IScale ramp = new LogarithmicRamp(new Point(1, 1), new Point(3, 10));
        Assert.assertEquals(6.67836778, ramp.scaleX(2), 0.0001);
    }

    @Test
    public void ScaleY_Yis5_ReturnsRampedValue()
    {
        IScale ramp = new LogarithmicRamp(new Point(1, 1), new Point(3, 10));
        Assert.assertEquals(1.6294, ramp.scaleY(5), 0.0001);
    }
}
