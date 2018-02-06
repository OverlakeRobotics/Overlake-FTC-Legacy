package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

// Combines a DcMotor and a Potentiometer into make a servo
public class DcMotorServo
{
    private DcMotor motor;
    private AnalogInput armPotentiometer;
    public double targetPosition;
    public double forwardPower;
    public double reversePower;

    public DcMotorServo(HardwareMap hardwareMap, String motorName, String potentiometerName)
    {
        this.motor = hardwareMap.dcMotor.get(motorName);
        this.armPotentiometer = hardwareMap.analogInput.get(potentiometerName);
        this.targetPosition = this.getCurrentPosition();
        this.forwardPower = 0.5;
        this.reversePower = 0.1;
    }

    public double getCurrentPosition()
    {
        return this.armPotentiometer.getVoltage();
    }

    public void loop()
    {
        double currentPosition = getCurrentPosition();

        if (this.targetPosition > currentPosition)
        {
            this.motor.setPower(adjustedPower(currentPosition, this.targetPosition, this.forwardPower));
        }
        else
        {
            this.motor.setPower(adjustedPower(currentPosition, this.targetPosition, this.reversePower));
        }
    }

    double adjustedPower(double currentPos, double targetPos, double maxPower)
    {
        double delta = targetPos - currentPos;
        double absDelta = Math.abs(delta);
        if (delta < 0)
            maxPower = -maxPower;

        return maxPower * (Math.log((Math.min(Math.E - 1, (Math.E - 1) * absDelta / 130.0) + 1))); // TODO: 130.0 is wrong
    }
}
