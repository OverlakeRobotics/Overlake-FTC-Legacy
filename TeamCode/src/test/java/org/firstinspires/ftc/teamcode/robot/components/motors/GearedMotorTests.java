package org.firstinspires.ftc.teamcode.robot.components.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import fakes.FakeDcMotor;
import org.firstinspires.ftc.teamcode.hardware.dcmotors.MotorType;
import org.firstinspires.ftc.teamcode.robot.components.GearChain;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class GearedMotorTests
{
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void GearedMotor_MotorIsBusy_ReturnsFalse()
    {
        GearedMotor motor = getMotor(1);
        assertFalse(motor.isBusy());
    }

    @Test
    public void GearedMotor_setDirection_HasValueOfForward()
    {
        GearedMotor motor = getMotor(1);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        assertEquals(DcMotorSimple.Direction.FORWARD, motor.getDirection());
    }

    @Test
    public void GearedMotor_setOutputGearTargetTicks_CurrentPositionIs100()
    {
        GearedMotor motor = getMotor(1);
        motor.setOutputGearTargetTicks(100);
        assertEquals(100, motor.getTargetPosition());
    }

    @Test
    public void GearedMotor_setOutputGearTargetTicks_CurrentPositionIs200()
    {
        GearedMotor motor = getMotor(1, 2);
        motor.setOutputGearTargetTicks(100);
        assertEquals(200, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_setOutputGearTargetRevolutions_CurrentPositionIs2()
    {
        GearedMotor motor = getMotor(1);
        motor.setOutputGearTargetRevolutions(2);
        assertEquals(2, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_setOutputGearTargetRevolutions_CurrentPositionIs4()
    {
        GearedMotor motor = getMotor(1, 2);
        motor.setOutputGearTargetRevolutions(2);
        assertEquals(4, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_setInputGearTargetRevolutions_Returns100()
    {
        GearedMotor motor = getMotor(1);
        motor.setInputGearTargetRevolutions(100);
        assertEquals(100, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_setInputGearTargetTicks_Returns100()
    {
        GearedMotor motor = getMotor(1);
        motor.setInputGearTargetTicks(100);
        assertEquals(100, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_run_PowerIs1()
    {
        GearedMotor motor = getMotor(1);
        motor.run(1);
        assertEquals(1, motor.getPower(), 0);
    }

    @Test
    public void GearedMotor_run_ThrowsException()
    {
        GearedMotor motor = getMotor(1);
        exceptionRule.expect(IllegalArgumentException.class);
        motor.run(100);
    }

    @Test
    public void GearedMotor_getCurrentPosition_Returns0()
    {
        GearedMotor motor = getMotor(1);
        assertEquals(0, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_getDirection_ReturnsForwardDirection()
    {
        GearedMotor motor = getMotor(1);
        assertEquals(DcMotorSimple.Direction.FORWARD, motor.getDirection());
    }

    @Test
    public void GearedMotor_getDistance_Returns0()
    {
        GearedMotor motor = getMotor(1);
        motor.setTargetPosition(100);
        assertEquals(0, motor.getDistanceToTargetPosition());
    }

    @Test
    public void GearedMotor_setRunMode_ReturnsRunToPosition()
    {
        GearedMotor motor = getMotor(1);
        motor.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        assertEquals(DcMotor.RunMode.RUN_USING_ENCODER, motor.getRunMode());
    }

    @Test
    public void GearedMotor_setTargetPosition_Returns100()
    {
        GearedMotor motor = getMotor(1);
        motor.setTargetPosition(100);
        assertEquals(100, motor.getTargetPosition());
    }

    private GearedMotor getMotor(double ...teeth)
    {
        GearChain chain = new GearChain(MotorType.TEST, teeth);
        DcMotor dcMotor = new FakeDcMotor();
        return new GearedMotor(chain, dcMotor);
    }
}