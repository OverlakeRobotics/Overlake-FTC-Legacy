package org.firstinspires.ftc.teamcode.timers;

import junit.framework.Assert;

import org.junit.Test;

public class IntervalTimerTests {
    @Test
    public void IntervalTimer_getCurrentTime_ReturnsSomeValue()
    {
        IntervalTimer intervalTimer = new IntervalTimer(0);
        Assert.assertTrue(intervalTimer.getCurrentTime() > 0);
    }

    @Test
    public void IntervalTimer_getPreviousTime_Returns0()
    {
        IntervalTimer intervalTimer = new IntervalTimer(0);
        Assert.assertEquals(0, intervalTimer.getPreviousTime());
    }

    @Test
    public void IntervalTimer_update_PreviousTimeHasChanged()
    {
        IntervalTimer intervalTimer = new IntervalTimer(0);
        intervalTimer.update();
        Assert.assertTrue(intervalTimer.getPreviousTime() != 0);
    }

    @Test
    public void IntervalTimer_hasCurrentIntervalPassed_ReturnsTrue()
    {
        IntervalTimer intervalTimer = new IntervalTimer(1);
        Assert.assertTrue(intervalTimer.hasCurrentIntervalPassed());
    }
}
