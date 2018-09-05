package org.firstinspires.ftc.teamcode.robot.systems;

import android.os.Environment;

import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceImpl;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

import junit.framework.Assert;

import org.firstinspires.ftc.teamcode.fakes.FakeDcMotor;
import org.firstinspires.ftc.teamcode.fakes.FakeHardwareMap;
import org.firstinspires.ftc.teamcode.fakes.FakeIMUDevice;
import org.firstinspires.ftc.teamcode.fakes.FakeOpMode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Environment.class})
public class MecanumDriveSystemTests
{
    @Rule
    public TemporaryFolder storageDirectory = new TemporaryFolder();

    private File nonExistentDirectory;
    private File existentDirectory;

    @Before
    public void setupFileSystem() throws IOException
    {
        nonExistentDirectory = Mockito.mock(File.class);
        Mockito.when(nonExistentDirectory.exists()).thenReturn(false);
        existentDirectory = storageDirectory.getRoot();
        PowerMockito.mockStatic(Environment.class);
        initializeConfig("MecanumDrive");
        initializeConfig("IMUSystem");
        addConfigValues("MecanumDrive", new String[] {
                ":String: motorFL motorFL",
                ":String: motorFR motorFR",
                ":String: motorBL motorBL",
                ":String: motorBR motorBR",
                ":double: P 0",
                ":double: I 0",
                ":double: D 0"
        });
        Mockito.when(Environment.getExternalStorageDirectory()).thenReturn(existentDirectory);
    }

    private void initializeConfig(String name) throws IOException
    {
        File root = new File(existentDirectory.getAbsolutePath() + "/Android/data/com.overlake.ftc.configapp/files", "configurations");
        root.mkdirs();
        File configFile = new File(root.getPath() + "/" + name + ".omc");
        configFile.createNewFile();
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

    private void addConfigValues(String name, String[] configValues) throws IOException
    {
        File root = new File(existentDirectory.getAbsolutePath() + "/Android/data/com.overlake.ftc.configapp/files", "configurations");
        File configFile = new File(root.getPath() + "/" + name + ".omc");
        FileWriter writer = new FileWriter(configFile);
        for (String configString : configValues)
        {
            writer.write(configString + "\n");
        }
        writer.flush();
        writer.close();
    }
}
