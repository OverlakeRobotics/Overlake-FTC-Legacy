package org.firstinspires.ftc.teamcode.robot.components.servos;

import fakes.FakeAnalogInput;
import fakes.FakeConfig;
import fakes.FakeDcMotor;
import org.firstinspires.ftc.teamcode.config.IConfig;
import org.junit.Assert;
import org.junit.Test;

public class DcMotorServoTests
{
    private final double POTENTIOMETER_VOLTAGE = 13.2d;

    @Test
    public void DcMotorServo_getCurrentPosition_Returns4()
    {
        DcMotorServo motorServo = getMotorServo();
        double position = motorServo.getCurrentPosition();
        Assert.assertEquals(4d, position, 0.000000001);
    }

    @Test
    public void DcMotorServo_stop_PowerIs0()
    {
        DcMotorServo motorServo = getMotorServo();
        motorServo.stop();
        Assert.assertEquals(0d, motorServo.getPower(), 0.000000001);
    }

    @Test
    public void DcMotorServo_runMotor_PowerIs05()
    {
        DcMotorServo motorServo = getMotorServo();
        motorServo.runMotor();
        Assert.assertEquals(0.5d, motorServo.getPower(), 0.000000001);
    }

    @Test
    public void DcMotorServo_runMotorBack_PowerIsNegative02()
    {
        DcMotorServo motorServo = getMotorServo();
        motorServo.runMotorBack();
        Assert.assertEquals(-0.2d, motorServo.getPower(), 0.000000001);
    }

    @Test
    public void DcMotor_loop_PowerIs048() throws InterruptedException
    {
        DcMotorServo motorServo = getMotorServo();
        Thread.sleep(10);
        motorServo.setTargetPosition(100);
        motorServo.loop();
        Assert.assertEquals(0.48d, motorServo.getPower(), 0.000000001);
    }

    @Test
    public void DcMotor_getPowerRelativeToPosition_PowerIsApproximately093()
    {
        DcMotorServo motorServo = getMotorServo();
        motorServo.setTargetPosition(85);
        double power = motorServo.getAdjustedPowerFromCurrentPosition(1d);
        Assert.assertEquals(0.93, power, 0.01);
    }

    @Test
    public void DcMotor_getPowerRelativeToPosition_PowerIs0()
    {
        DcMotorServo motorServo = getMotorServo();
        motorServo.setTargetPosition(4);
        double power = motorServo.getAdjustedPowerFromCurrentPosition(1d);
        Assert.assertEquals(0d, power, 0.000000001);
    }

    @Test
    public void DcMotor_getPowerRelativeToPosition_PowerAShouldEqualPowerB() {
        DcMotorServo motorServo = getMotorServo();
        motorServo.setTargetPosition(50);
        double powerA = motorServo.getAdjustedPowerFromCurrentPosition(1d);
        motorServo.setTargetPosition(50);
        double powerB = motorServo.getAdjustedPowerFromCurrentPosition(1d);
        Assert.assertTrue(powerA == powerB);
    }

    @Test
    public void DcMotor_getPowerRelativeToPosition_PowerAShouldNotEqualPowerB() {
        DcMotorServo motorServo = getMotorServo();
        motorServo.setTargetPosition(50);
        double powerA = motorServo.getAdjustedPowerFromCurrentPosition(1d);
        motorServo.setTargetPosition(100);
        double powerB = motorServo.getAdjustedPowerFromCurrentPosition(1d);
        Assert.assertTrue(powerA != powerB);
    }

    private DcMotorServo getMotorServo()
    {
        return new DcMotorServo(new FakeDcMotor(), getFakePotentiometer(), getFakeConfig());
    }

    private FakeAnalogInput getFakePotentiometer()
    {
        FakeAnalogInput fakeAnalogInput = new FakeAnalogInput();
        fakeAnalogInput.setVoltage(POTENTIOMETER_VOLTAGE);
        return fakeAnalogInput;
    }

    private IConfig getFakeConfig()
    {
        FakeConfig config = new FakeConfig();
        config.addConfigItem("P", 0.005d);
        config.addConfigItem("I", 0.05d);
        config.addConfigItem("D", 0.5d);
        config.addConfigItem("OutputLimits", 1.0d);
        return config;
    }
}
