package org.firstinspires.ftc.teamcode.scale;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by EvanCoulson on 8/29/18.
 */

public class    ExponentialRampTests
{
    @Test
    public void Value_XIs5_ReturnsRampedValue()
    {
        IScale ramp = new ExponentialRamp(new Point(1, 1), new Point(3, 10));
        Assert.assertEquals(3.16227766017, ramp.scaleX(2), 0.0001);
    }

    @Test
    public void Inverse_Yis5_ReturnsRampedValue()
    {
        IScale ramp = new ExponentialRamp(new Point(1, 1), new Point(3, 10));
        Assert.assertEquals(2.398, ramp.scaleY(5), 0.0001);
    }
}
