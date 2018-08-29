package org.firstinspires.ftc.teamcode.robot.components.servos;

import com.qualcomm.robotcore.hardware.HardwareMap;

import junit.framework.Assert;

import org.firstinspires.ftc.teamcode.fakes.FakeAnalogInput;
import org.firstinspires.ftc.teamcode.fakes.FakeConfig;
import org.firstinspires.ftc.teamcode.fakes.FakeDcMotor;
import org.firstinspires.ftc.teamcode.fakes.FakeHardwareMap;
import org.firstinspires.ftc.teamcode.fakes.FakeTelemetry;
import org.firstinspires.ftc.teamcode.config.IConfig;
import org.junit.Test;

public class DcMotorServoTests
{
    private final double POTENTIOMETER_VOLTAGE = 13.2d;

    @Test
    public void DcMotorServo_getCurrentPosition_Returns4()
    {
        DcMotorServo motorServo = getMotorServo();
        double position = motorServo.getCurrentPosition();
        Assert.assertEquals(4d, position);
    }

    @Test
    public void DcMotorServo_stop_PowerIs0()
    {
        DcMotorServo motorServo = getMotorServo();
        motorServo.stop();
        Assert.assertEquals(0d, motorServo.getPower());
    }

    @Test
    public void DcMotorServo_runMotor_PowerIs05()
    {
        DcMotorServo motorServo = getMotorServo();
        motorServo.runMotor();
        Assert.assertEquals(0.5d, motorServo.getPower());
    }

    @Test
    public void DcMotorServo_runMotorBack_PowerIsNegative02()
    {
        DcMotorServo motorServo = getMotorServo();
        motorServo.runMotorBack();
        Assert.assertEquals(-0.2d, motorServo.getPower());
    }

    @Test
    public void DcMotor_loop_PowerIs048() throws InterruptedException
    {
        DcMotorServo motorServo = getMotorServo();
        Thread.sleep(10);
        motorServo.loop(100);
        Assert.assertEquals(0.48d, motorServo.getPower());
    }

    @Test
    public void DcMotor_adjustedPower_PowerIsApproximately079()
    {
        DcMotorServo motorServo = getMotorServo();
        double power = motorServo.adjustedPower(15, 100, 1d);
        Assert.assertEquals(0.79, Math.round(power * 100d) / 100d);
    }

    @Test
    public void DcMotor_adjustedPower_PowerIs0()
    {
        DcMotorServo motorServo = getMotorServo();
        double power = motorServo.adjustedPower(1,1, 1d);
        Assert.assertEquals(0d, power);
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
        return config;
    }
}
