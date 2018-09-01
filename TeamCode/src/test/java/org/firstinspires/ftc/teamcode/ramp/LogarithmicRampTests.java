package org.firstinspires.ftc.teamcode.ramp;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LogarithmicRampTests
{
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void Value_X1Is0_ReturnsRampedValue()
    {
        LogarithmicRamp ramp = new LogarithmicRamp(0,0,2,2);
        Assert.assertTrue(Double.isNaN(ramp.value(0.5)));
    }

    @Test
    public void Value_XIs2_ReturnsRampedValue()
    {
        LogarithmicRamp ramp = new LogarithmicRamp(1,1,3,10);
        Assert.assertEquals(6.67836778, ramp.value(2), 0.0001);
    }

    @Test
    public void Value_Xis1_ReturnsRampedValue()
    {
        LogarithmicRamp ramp = new LogarithmicRamp(1,1,3,10);
        exceptionRule.expect(IllegalArgumentException.class);
        ramp.value(10);
    }

    @Test
    public void Inverse_Yis5_ReturnsRampedValue()
    {
        LogarithmicRamp ramp = new LogarithmicRamp(1,1,3,10);
        Assert.assertEquals(1.6294, ramp.inverse(5), 0.0001);
    }

    @Test
    public void Inverse_Yis50_ReturnsRampedValue() {
        LogarithmicRamp ramp = new LogarithmicRamp(1,1,3,10);
        exceptionRule.expect(IllegalArgumentException.class);
        ramp.inverse(50);
    }
}
