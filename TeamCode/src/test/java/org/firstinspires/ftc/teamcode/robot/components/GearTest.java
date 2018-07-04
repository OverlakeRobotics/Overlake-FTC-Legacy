package org.firstinspires.ftc.teamcode.robot.components;

import org.junit.Test;

import static org.junit.Assert.*;

public class GearTest {

    @Test
    public void getRatio_RatioBetweenFiveToothAndTenTooth_Returns2() {
        Gear tenTooth = new Gear(10);
        Gear fiveTooth = new Gear(5, tenTooth);

        double ratio = fiveTooth.getRatio();
        assertTrue(ratio == 2);
    }

    @Test
    public void getRatio_RatioOfGearWithNoNextGear_Returns1() {
        Gear gear = new Gear(100);

        double ratio = gear.getRatio();
        assertTrue(ratio == 1);
    }
}