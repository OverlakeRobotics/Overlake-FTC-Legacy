package org.firstinspires.ftc.teamcode.robot.systems;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import junit.framework.Assert;

import environment.ConfigValue;
import environment.SystemTest;
import fakes.FakeDcMotor;
import fakes.FakeHardwareMap;
import fakes.FakeIMUDevice;
import fakes.FakeOpMode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileWriter;
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
