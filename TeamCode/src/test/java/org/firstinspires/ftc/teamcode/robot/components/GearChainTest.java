package org.firstinspires.ftc.teamcode.robot.components;

import org.firstinspires.ftc.teamcode.TestBase;
import org.firstinspires.ftc.teamcode.hardware.dcmotors.MotorType;
import org.firstinspires.ftc.teamcode.test.constants.GearChainTestConstants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.*;
import static org.firstinspires.ftc.teamcode.test.constants.GearChainTestConstants.*;

public class GearChainTest extends TestBase {
    private GearChainTestConstants constants = new GearChainTestConstants();

    public GearChainTest() {
        super("GearChainTest");
    }

    @Rule
    public TestName name = new TestName();

    @Test
    public void GearChain_CalculateGearChainRatioWithOtherGears_Return2() {
        logTestName(name.getMethodName());

        GearChain chain = new GearChain(MotorType.TEST, GearChainTeeth);
        double ratio = chain.getChainRatio();
        logTestCase(ExpectedGearChainRatioWithOtherGears, ratio);

        assertTrue(ratio == ExpectedGearChainRatioWithOtherGears);
    }

    @Test
    public void GearChain_CalculateGearChainRatioWithOutAnyOtherGears_Return1() {
        logTestName(name.getMethodName());

        GearChain chain = new GearChain(MotorType.TEST);
        double ratio = chain.getChainRatio();
        logTestCase(ExpectedGearChainRatioWithOutAnyOtherGears, ratio);

        assertTrue(ratio == ExpectedGearChainRatioWithOutAnyOtherGears);
    }

    @Test
    public void GearChain_CalculateOutputTicks_Return200() {
        logTestName(name.getMethodName());

        GearChain chain = new GearChain(MotorType.TEST, GearChainTeeth);
        int ticks = chain.calculateOuputTicks(100);
        logTestCase(ExpectedOutputTicks, ticks);

        assertTrue(ticks == ExpectedOutputTicks);
    }

    @Test
    public void GearChain_CalculateOutputRevolutionTicks_Return50() {
        logTestName(name.getMethodName());

        GearChain chain = new GearChain(MotorType.TEST, GearChainTeeth);
        int outputRevolutionTicks = chain.calculateOutputRevolutionTicks(25);
        logTestCase(ExpectedOutputRevolutionTicks, outputRevolutionTicks);

        assertTrue(outputRevolutionTicks == ExpectedOutputRevolutionTicks);
    }

    @Test
    public void GearChain_CalculateInputRevolutionTicks_Return50() {
        logTestName(name.getMethodName());

        GearChain chain = new GearChain(MotorType.TEST, GearChainTeeth);
        double revolutions = 25;
        int inputRevolutionTicks = chain.calculateInputRevolutionTicks(50);
        logTestCase(ExpectedInputRevolutionTicks, inputRevolutionTicks);

        assertTrue(inputRevolutionTicks == ExpectedInputRevolutionTicks);
    }

}