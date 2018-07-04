package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.DcMotor;

public class GearedMotorWithWheel extends GearedMotor {
    private double ticksPerInch;

    public GearedMotorWithWheel(GearChain chain, DcMotor motor, double wheelDiameter) {
        super(chain, motor);
        this.ticksPerInch = (chain.calculateOutputRevolutionTicks(1)) / (wheelDiameter * Math.PI);
    }

    public int inchesToTicks(double inches) {
        return (int)Math.round(inches * ticksPerInch);
    }

    public void runOutputWheelInches(double inches, double power) {
        int ticks = (int) Math.round(inches * ticksPerInch);
        runMotor(ticks, power);
    }
}
