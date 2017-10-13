package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by EvanCoulson on 10/9/17.
 */

public class GearedMotor {
    private GearChain chain;
    private DcMotor motor;
    private int pulses;
    private double wheelDiameter;
    private double ticsPerInch;

    public GearedMotor(int pulses, DcMotor motor) {
        this(pulses, 0, motor, 1, 1);
    }

    public GearedMotor(int pulses, DcMotor motor, double... teeth) {
        this(pulses, 0, motor, teeth);
    }

    public GearedMotor(int pulses, double wheelDiameter, DcMotor motor, double... teeth) {
        this.chain = new GearChain(teeth);
        this.pulses = pulses;
        this.wheelDiameter = wheelDiameter;
        this.ticsPerInch = (pulses * chain.calculateOutputRevolutions(pulses, 1)) / (wheelDiameter * Math.PI);
        this.motor = motor;
        this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motor.setPower(0);
    }

    public boolean isBusy() {
        return motor.isBusy();
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        motor.setDirection(direction);
    }

    public void runOutputGearTics(int tics, double power) {
        int outputTics = chain.calculateOuputTics(tics);
        runMotor(outputTics, power);
    }

    public void runOutputGearRevolutions(double revolutions, double power) {
        int tics = chain.calculateOutputRevolutions(pulses, revolutions);
        runMotor(tics, power);
    }

    public void runInputGearTics(int tics, double power) {
        runMotor(tics, power);
    }

    public void runInputGearRevolutions(double revolutions, double power) {
        int tics = chain.calculateInputRevolutions(pulses, revolutions);
        runMotor(tics, power);
    }

    public void runOutputWheelInches(double inches, double power) {
        int tics = (int) Math.round(inches * ticsPerInch);
        runMotor(tics, power);
    }

    private void runMotor(int tics, double power) {
        int current = motor.getCurrentPosition();
        motor.setTargetPosition(current + tics);
        motor.setPower(power);
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public double getPower() {
        return motor.getPower();
    }

    public int getTargetPosition() {
        return motor.getTargetPosition();
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }
}
