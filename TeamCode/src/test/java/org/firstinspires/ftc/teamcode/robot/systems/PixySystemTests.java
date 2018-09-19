package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import junit.framework.Assert;

import environment.SystemTest;
import fakes.FakeDcMotor;
import fakes.FakeHardwareMap;
import fakes.FakeLinearOpMode;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by EvanCoulson on 9/17/18.
 */

public class PixySystemTests extends SystemTest
{
    @Before
    public void setupConfigs() throws IOException
    {
        initializeConfig("PixySystem");
    }

    @Test
    public void TurnOnLight_TurningOnLight_PowerIs05() {
        LinearOpMode opMode = getOpMode();
        PixySystem pixy = new PixySystem(opMode, 0);
        pixy.turnOnLight();
        DcMotor lightMotor = opMode.hardwareMap.dcMotor.get("light");
        Assert.assertEquals(0.5d, lightMotor.getPower(), 0.00000001);
    }

    private LinearOpMode getOpMode() {
        LinearOpMode opMode = new FakeLinearOpMode();
        opMode.hardwareMap = getHardwareMap();
        return opMode;
    }

    private HardwareMap getHardwareMap() {
        FakeHardwareMap hardwareMap = new FakeHardwareMap();
        hardwareMap.addFakeDcMotor("light", new FakeDcMotor());
        return hardwareMap;
    }
}
