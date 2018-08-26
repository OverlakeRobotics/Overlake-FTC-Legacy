package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.fakes.FakeDcMotor;
import org.firstinspires.ftc.teamcode.hardware.dcmotors.MotorType;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GearedMotorTest {
    private GearChain chain;
    private DcMotor dcMotor;
    private GearedMotor motor;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void GearedMotor_MotorIsBusy_ReturnsFalse() {
        GearedMotor motor = getMotor(1);
        assertFalse(motor.isBusy());
    }

    @Test
    public void GearedMotor_setDirection_HasValueOfForward() {
        GearedMotor motor = getMotor(1);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        assertEquals(DcMotorSimple.Direction.FORWARD, motor.getDirection());
    }

    @Test
    public void GearedMotor_runOutputGearTicks_CurrentPositionIs100() {
        GearedMotor motor = getMotor(1);
        motor.runOutputGearTicks(100, 1);
        assertEquals(100, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_runOutputGearTicks_CurrentPositionIs200() {
        GearedMotor motor = getMotor(1, 2);
        motor.runOutputGearTicks(100, 1);
        assertEquals(200, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_runOutputGearRevolutions_CurrentPositionIs2() {
        GearedMotor motor = getMotor(1);
        motor.runOutputGearRevolutions(2.0, 1.0);
        assertEquals(2, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_runOutputGearRevolutions_CurrentPositionIs4() {
        GearedMotor motor = getMotor(1, 2);
        motor.runOutputGearRevolutions(2, 1);
        assertEquals(4, motor.getCurrentPosition());
    }
    
    @Test
    public void GearedMotor_runInputGearRevolutions_Returns100() {
        GearedMotor motor = getMotor(1);
        motor.runInputGearTicks(100, 1);
        assertEquals(100, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_runInputGearTicks_Returns100() {
        GearedMotor motor = getMotor(1);
        motor.runInputGearTicks(100, 1);
        assertEquals(100, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_setPower_PowerIs1() {
        GearedMotor motor = getMotor(1);
        motor.setPower(1);
        assertEquals(1, motor.getPower(), 0);
    }

    @Test
    public void GearedMotor_setPower_ThrowsException() {
        GearedMotor motor = getMotor(1);
        exceptionRule.expect(IllegalArgumentException.class);
        motor.setPower(100);
    }

    @Test
    public void GearedMotor_getCurrentPosition_Returns0() {
        GearedMotor motor = getMotor(1);
        assertEquals(0, motor.getCurrentPosition());
    }

    @Test
    public void GearedMotor_getDirection_ReturnsForwardDirection() {
        GearedMotor motor = getMotor(1);
        assertEquals(DcMotorSimple.Direction.FORWARD, motor.getDirection());
    }

    @Test
    public void GearedMotor_getDistance_Returns0() {
        GearedMotor motor = getMotor(1);
        motor.setTargetPosition(100);
        assertEquals(0, motor.getDistance());
    }

    @Test
    public void GearedMotor_setRunMode_ReturnsRunToPosition() {
        GearedMotor motor = getMotor(1);
        motor.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        assertEquals(DcMotor.RunMode.RUN_USING_ENCODER, motor.getRunMode());
    }

    @Test
    public void GearedMotor_setTargetPosition_Returns100() {
        GearedMotor motor = getMotor(1);
        motor.setTargetPosition(100);
        assertEquals(100, motor.getTargetPosition());
    }

    private GearedMotor getMotor(double ...teeth) {
        GearChain chain = new GearChain(MotorType.TEST, teeth);
        DcMotor dcMotor = new FakeDcMotor();
        return new GearedMotor(chain, dcMotor);
    }
}