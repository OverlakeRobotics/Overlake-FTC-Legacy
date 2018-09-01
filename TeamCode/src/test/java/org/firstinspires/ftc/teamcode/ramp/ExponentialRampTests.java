package org.firstinspires.ftc.teamcode.ramp;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by EvanCoulson on 8/29/18.
 */

public class    ExponentialRampTests
{
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void Value_XIs5_ReturnsRampedValue()
    {
        ExponentialRamp ramp = new ExponentialRamp(1,1,3,10);
        Assert.assertEquals(3.16227766017, ramp.value(2), 0.0001);
    }

    @Test
    public void Value_Xis1_ReturnsRampedValue()
    {
        ExponentialRamp ramp = new ExponentialRamp(1,1,3,10);
        Assert.assertEquals(1, ramp.value(1), 0.00000001);
    }

    @Test
    public void Inverse_Yis5_ReturnsRampedValue()
    {
        ExponentialRamp ramp = new ExponentialRamp(1,1,3,10);
        Assert.assertEquals(2.398, ramp.inverse(5), 0.0001);
    }

    @Test
    public void Inverse_Yis50_ReturnsRampedValue() {
        ExponentialRamp ramp = new ExponentialRamp(1,1,3,10);
        exceptionRule.expect(IllegalArgumentException.class);
        ramp.inverse(50);
    }
}
