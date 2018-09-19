package org.firstinspires.ftc.teamcode.robot.components.motors;

import com.qualcomm.robotcore.hardware.DcMotor;

import junit.framework.Assert;

import fakes.FakeDcMotor;
import org.firstinspires.ftc.teamcode.hardware.dcmotors.MotorType;
import org.firstinspires.ftc.teamcode.robot.components.GearChain;
import org.junit.Test;

public class GearedWheelMotorTests
{
    @Test
    public void GearedWheelMotor_setOutputWheelTargetInches_TargetPositionIs357()
    {
        GearChain chain = new GearChain(MotorType.NEVEREST40_PULSES, 3, 6);
        DcMotor dcMotor = new FakeDcMotor();
        GearedWheelMotor motor = new GearedWheelMotor(chain, dcMotor, 2);

        motor.setOutputWheelTargetInches(1);

        Assert.assertEquals(357, motor.getTargetPosition());
    }
}
