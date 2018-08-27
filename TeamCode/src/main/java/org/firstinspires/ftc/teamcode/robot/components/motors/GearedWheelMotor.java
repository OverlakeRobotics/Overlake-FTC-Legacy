package org.firstinspires.ftc.teamcode.robot.components.motors;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.components.GearChain;

public class GearedWheelMotor extends GearedMotor {
    private double ticksPerInch;

    public GearedWheelMotor(GearChain chain, DcMotor motor, double wheelDiameter) {
        super(chain, motor);
        this.ticksPerInch = (chain.calculateOutputRevolutionTicks(1)) / (wheelDiameter * Math.PI);
    }

    public void setOutputWheelTargetInches(double inches) {
        int ticks = inchesToTicks(inches);
        setTargetPosition(ticks);
    }

    public int inchesToTicks(double inches) {
        return (int)Math.round(inches * ticksPerInch);
    }
}
