package org.firstinspires.ftc.teamcode.scale;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by EvanCoulson on 8/31/18.
 */

public class RampTests
{
    @Test
    public void ScaleX_InsideDomain_Returns2()
    {
        IScale ramp = new LinearRamp(new Point(0, 0), new Point(5, 5));
        Assert.assertEquals(2d, ramp.scaleX(2), 0.00001);
    }

    @Test
    public void ScaleX_OutsideLeftDomain_Returns0()
    {
        IScale ramp = new LinearRamp(new Point(0, 0), new Point(5, 5));
        Assert.assertEquals(0d, ramp.scaleX(-1), 0.00001);
    }

    @Test
    public void ScaleX_NegativeSlopeAndOutsideLeftDomain_Returns5()
    {
        IScale ramp = new LinearRamp(new Point(0, 5), new Point(5, 0));
        Assert.assertEquals(5d, ramp.scaleX(-10), 0.00001);
    }

    @Test
    public void ScaleX_OutsideRightDomain_Returns5()
    {
        IScale ramp = new LinearRamp(new Point(0, 0), new Point(5, 5));
        Assert.assertEquals(5d, ramp.scaleX(10), 0.00001);
    }

    @Test
    public void ScaleX_NegativeSlopeAndOutsideRightDomain_Returns0()
    {
        IScale ramp = new LinearRamp(new Point(0, 5), new Point(5, 0));
        Assert.assertEquals(0d, ramp.scaleX(10), 0.00001);
    }

    @Test
    public void ScaleX_ValuesReversedOutsideLeftDomain_Returns0()
    {
        IScale ramp = new LinearRamp(new Point(5, 5), new Point(0, 0));
        Assert.assertEquals(0d, ramp.scaleX(-1), 0.00001);
    }

    @Test
    public void ScaleX_ValuesReversedOutsideRightDomain_Returns5()
    {
        IScale ramp = new LinearRamp(new Point(5, 5), new Point(0, 0));
        Assert.assertEquals(5d, ramp.scaleX(10), 0.00001);
    }

    @Test
    public void ScaleY_InsideRange_Returns2()
    {
        IScale ramp = new LinearRamp(new Point(0, 0), new Point(5, 5));
        Assert.assertEquals(2d, ramp.scaleY(2), 0.00001);
    }

    @Test
    public void ScaleY_OutsideLeftRange_Returns0()
    {
        IScale ramp = new LinearRamp(new Point(0, 0), new Point(5, 5));
        Assert.assertEquals(0d, ramp.scaleY(-1), 0.00001);
    }

    @Test
    public void ScaleY_NegativeSlopeAndOutsideLeftRange_Returns5()
    {
        IScale ramp = new LinearRamp(new Point(0, 5), new Point(5, 0));
        Assert.assertEquals(5d, ramp.scaleY(-10), 0.00001);
    }

    @Test
    public void ScaleY_OutsideRightRange_Returns5()
    {
        IScale ramp = new LinearRamp(new Point(0, 0), new Point(5, 5));
        Assert.assertEquals(5d, ramp.scaleY(10), 0.00001);
    }

    @Test
    public void ScaleY_NegativeSlopeAndOutsideRightRange_Returns0()
    {
        IScale ramp = new LinearRamp(new Point(0, 5), new Point(5, 0));
        Assert.assertEquals(0d, ramp.scaleY(10), 0.00001);
    }

    @Test
    public void ScaleY_ValuesReversedOutsideLeftRange_Returns0()
    {
        IScale ramp = new LinearRamp(new Point(5, 5), new Point(0, 0));
        Assert.assertEquals(0d, ramp.scaleY(-1), 0.00001);
    }

    @Test
    public void ScaleY_ValuesReversedOutsideRightRange_Returns5()
    {
        IScale ramp = new LinearRamp(new Point(5, 5), new Point(0, 0));
        Assert.assertEquals(5d, ramp.scaleY(10), 0.00001);
    }
}