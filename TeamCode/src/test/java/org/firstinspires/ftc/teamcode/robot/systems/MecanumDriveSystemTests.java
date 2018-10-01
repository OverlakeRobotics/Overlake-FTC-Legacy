package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import junit.framework.Assert;

import environment.ConfigValue;
import environment.SystemTest;
import fakes.FakeDcMotor;
import fakes.FakeHardwareMap;
import fakes.FakeIMUDevice;
import fakes.FakeOpMode;

import org.firstinspires.ftc.teamcode.robot.systems.physical.MecanumDriveSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MecanumDriveSystemTests extends SystemTest
{
    @Before
    public void setupTest() throws IOException {
        initializeConfig("MecanumDrive");
        initializeConfig("IMUSystem");
        addConfigValues("MecanumDrive",
            new ConfigValue("String", "motorFL", "motorFL"),
            new ConfigValue("String", "motorBL", "motorBL"),
            new ConfigValue("String", "motorFR", "motorFR"),
            new ConfigValue("String", "motorBR", "motorBR"),
            new ConfigValue("double", "P", "0"),
            new ConfigValue("double", "I", "0"),
            new ConfigValue("double", "D", "0")
        );
    }

    @Test
    public void inchesToTicks_5inches_Returns223Ticks()
    {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        Assert.assertEquals(223, mecanumDriveSystem.inchesToTicks(5));
    }

    @Test
    public void setDirection_SetDirectionReverse_AllMotorsInReverseDirection() {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        mecanumDriveSystem.setDirection(DcMotorSimple.Direction.REVERSE);
        Assert.assertEquals(DcMotorSimple.Direction.REVERSE, mecanumDriveSystem.motorFrontRight.getDirection());
        Assert.assertEquals(DcMotorSimple.Direction.REVERSE, mecanumDriveSystem.motorFrontLeft.getDirection());
        Assert.assertEquals(DcMotorSimple.Direction.REVERSE, mecanumDriveSystem.motorBackLeft.getDirection());
        Assert.assertEquals(DcMotorSimple.Direction.REVERSE, mecanumDriveSystem.motorBackRight.getDirection());
    }

    @Test
    public void isAnyMotorsBusy_NoMotorsBusy_ReturnsFalse() {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        Assert.assertFalse(mecanumDriveSystem.anyMotorsBusy());
    }

    @Test
    public void setTargetPosition_TargetPositionIs100_Returns100ForAllMotors() {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        mecanumDriveSystem.setTargetPosition(100);
        Assert.assertEquals(100, mecanumDriveSystem.motorFrontRight.getTargetPosition());
        Assert.assertEquals(100, mecanumDriveSystem.motorFrontLeft.getTargetPosition());
        Assert.assertEquals(100, mecanumDriveSystem.motorBackLeft.getTargetPosition());
        Assert.assertEquals(100, mecanumDriveSystem.motorBackRight.getTargetPosition());
    }

    @Test
    public void mecanumDrive_SetsPowerToMotors_PowersAreCorrectValues() {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        mecanumDriveSystem.mecanumDrive(0.5f, 0.25f, 0.25f, 0.5f, false);
        Assert.assertEquals(-0.0775, mecanumDriveSystem.motorFrontLeft.getPower(), 0.00001f);
        Assert.assertEquals(0.34875, mecanumDriveSystem.motorFrontRight.getPower(), 0.00001f);
        Assert.assertEquals(0.0775, mecanumDriveSystem.motorBackLeft.getPower(), 0.00001f);
        Assert.assertEquals(0.27125, mecanumDriveSystem.motorBackRight.getPower(), 0.00001f);
    }

    @Test
    public void setRunmode_SetsRunModeOfMotors_RunWithEncoders() {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        mecanumDriveSystem.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Assert.assertEquals(DcMotor.RunMode.RUN_USING_ENCODER, mecanumDriveSystem.motorFrontLeft.getRunMode());
        Assert.assertEquals(DcMotor.RunMode.RUN_USING_ENCODER, mecanumDriveSystem.motorFrontRight.getRunMode());
        Assert.assertEquals(DcMotor.RunMode.RUN_USING_ENCODER, mecanumDriveSystem.motorBackLeft.getRunMode());
        Assert.assertEquals(DcMotor.RunMode.RUN_USING_ENCODER, mecanumDriveSystem.motorBackRight.getRunMode());
    }

    @Test
    public void godDrive_SetsPowerToMotors_PowersAreCorrectValues() {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        mecanumDriveSystem.driveGodMode(1f, 0.5f, 0.25f, 0.66f, 1f);
        Assert.assertEquals(-0.388678, mecanumDriveSystem.motorFrontLeft.getPower(), 0.00001f);
        Assert.assertEquals(0.851322, mecanumDriveSystem.motorBackRight.getPower(), 0.00001f);
        Assert.assertEquals(0.928822, mecanumDriveSystem.motorFrontRight.getPower(), 0.00001f);
        Assert.assertEquals(-0.311178, mecanumDriveSystem.motorBackLeft.getPower(), 0.00001f);
    }

    @Test
    public void resetInitialHeading_SetsCurrentHeading_CurrentHeadingIs0() {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        mecanumDriveSystem.resetInitialHeading();
        Assert.assertEquals(0, mecanumDriveSystem.initialHeading, 0.000001d);
    }

    @Test
    public void mecanumDriveXY_SetsPowerToMotors_CorrectMotorPowers() {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        mecanumDriveSystem.mecanumDriveXY(0.5, 0.5);
        Assert.assertEquals(0, mecanumDriveSystem.motorFrontLeft.getPower(), 0.00001f);
        Assert.assertEquals(0, mecanumDriveSystem.motorBackRight.getPower(), 0.00001f);
        Assert.assertEquals(1, mecanumDriveSystem.motorFrontRight.getPower(), 0.00001f);
        Assert.assertEquals(1, mecanumDriveSystem.motorBackLeft.getPower(), 0.00001f);
    }

    @Test
    public void mecanumDrivePolar_SetsPowerToMotors_CorrectMotorPowers() {
        MecanumDriveSystem mecanumDriveSystem = new MecanumDriveSystem(getOpMode());
        mecanumDriveSystem.mecanumDrivePolar(Math.PI, 0.5);
        Assert.assertEquals(0.5, mecanumDriveSystem.motorFrontLeft.getPower(), 0.00001f);
        Assert.assertEquals(0.5, mecanumDriveSystem.motorBackRight.getPower(), 0.00001f);
        Assert.assertEquals(-0.5, mecanumDriveSystem.motorFrontRight.getPower(), 0.00001f);
        Assert.assertEquals(-0.5, mecanumDriveSystem.motorBackLeft.getPower(), 0.00001f);
    }

    //TODO: Refactor in to the environemnt test
    private OpMode getOpMode()
    {
        FakeOpMode mode = new FakeOpMode();
        mode.hardwareMap = getHardwareMap();
        return mode;
    }

    private FakeHardwareMap getHardwareMap()
    {
        FakeHardwareMap fakeHardwareMap = new FakeHardwareMap();
        fakeHardwareMap.addFakeIMU("imu", new FakeIMUDevice());
        fakeHardwareMap.addFakeDcMotor("motorFL", new FakeDcMotor());
        fakeHardwareMap.addFakeDcMotor("motorFR", new FakeDcMotor());
        fakeHardwareMap.addFakeDcMotor("motorBL", new FakeDcMotor());
        fakeHardwareMap.addFakeDcMotor("motorBR", new FakeDcMotor());
        return fakeHardwareMap;
    }
}
