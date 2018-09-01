package org.firstinspires.ftc.teamcode.ramp;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by EvanCoulson on 8/31/18.
 */

public class RampTests
{
    @Test
    public void CheckDomain_InsideDomain_ReturnsTrue()
    {
        Ramp ramp = new LinearRamp(0, 0, 5, 5);
        Assert.assertTrue(ramp.IsInDomain(2));
    }

    @Test
    public void CheckDomain_OutsideLeftDomain_ReturnsFalse()
    {
        Ramp ramp = new LinearRamp(0, 0, 5, 5);
        Assert.assertFalse(ramp.IsInDomain(-1));
    }

    @Test
    public void CheckDomain_OutsideRightDomain_ReturnsFalse()
    {
        Ramp ramp = new LinearRamp(0, 0, 5, 5);
        Assert.assertFalse(ramp.IsInDomain(10));
    }

    @Test
    public void CheckDomain_ValuesReversedOutsideLeftDomain_ReturnsFalse()
    {
        Ramp ramp = new LinearRamp(5, 5, 0, 0);
        Assert.assertFalse(ramp.IsInDomain(-1));
    }

    @Test
    public void CheckDomain_ValuesReversedOutsideRightDomain_ReturnsFalse()
    {
        Ramp ramp = new LinearRamp(5, 5, 0, 0);
        Assert.assertFalse(ramp.IsInDomain(10));
    }

    @Test
    public void CheckRange_InsideRange_ReturnsTrue()
    {
        Ramp ramp = new LinearRamp(0, 0, 5, 5);
        Assert.assertTrue(ramp.IsInRange(2));
    }

    @Test
    public void CheckRange_OutsideLeftRange_ReturnsFalse()
    {
        Ramp ramp = new LinearRamp(0, 0, 5, 5);
        Assert.assertFalse(ramp.IsInRange(-1));
    }

    @Test
    public void CheckRange_OutsideRightRange_ReturnsFalse()
    {
        Ramp ramp = new LinearRamp(0, 0, 5, 5);
        Assert.assertFalse(ramp.IsInRange(10));
    }

    @Test
    public void CheckRange_ValuesReversedOutsideLeftRange_ReturnsFalse()
    {
        Ramp ramp = new LinearRamp(5, 5, 0, 0);
        Assert.assertFalse(ramp.IsInRange(-1));
    }

    @Test
    public void CheckRange_ValuesReversedOutsideRightRange_ReturnsFalse()
    {
        Ramp ramp = new LinearRamp(5, 5, 0, 0);
        Assert.assertFalse(ramp.IsInRange(10));
    }
}