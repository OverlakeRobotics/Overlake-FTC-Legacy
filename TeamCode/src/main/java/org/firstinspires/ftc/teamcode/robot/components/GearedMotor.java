package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.dcmotors.MotorType;

/**
 * Created by EvanCoulson on 10/9/17.
 */

public class GearedMotor {
    public final static double MIN_POWER = 0.1d;
    public final static double FOWARD_DIRECTION = 1d;
    public final static double BACKWARD_DIRECTION = -1d;

    public GearChain chain;
    public DcMotor motor;
    private double wheelDiameter;
    public double ticksPerInch;

    public GearedMotor(MotorType type, DcMotor motor) {
        this(type, 0, motor, 1, 1);
    }

    public GearedMotor(MotorType type, DcMotor motor, double... teeth) {
        this(type, 0, motor, teeth);
    }

    public GearedMotor(MotorType type, double wheelDiameter, DcMotor motor, double... teeth) {
        this.chain = new GearChain(type, teeth);
        this.wheelDiameter = wheelDiameter;
        this.ticksPerInch = (chain.calculateOutputRevolutionTicks(1)) / (wheelDiameter * Math.PI);
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

    public void runOutputGearTicks(int ticks, double power) {
        int outputTicks = chain.calculateOuputTicks(ticks);
        runMotor(outputTicks, power);
    }

    public void runOutputGearRevolutions(double revolutions, double power) {
        int ticks = chain.calculateOutputRevolutionTicks(revolutions);
        runMotor(ticks, power);
    }

    public void runInputGearTicks(int ticks, double power) {
        runMotor(ticks, power);
    }

    public void runInputGearRevolutions(double revolutions, double power) {
        int ticks = chain.calculateOutputRevolutionTicks(revolutions);
        runMotor(ticks, power);
    }

    public void runOutputWheelInches(double inches, double power) {
        int ticks = (int) Math.round(inches * ticksPerInch);
        runMotor(ticks, power);
    }

    private void runMotor(int ticks, double power) {
        setTargetPosition(ticks);
        motor.setPower(power);
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public void setRunMode(DcMotor.RunMode mode) {
        motor.setMode(mode);
    }

    private void setTargetPosition(int ticks) {
        int current = motor.getCurrentPosition();
        motor.setTargetPosition(current + ticks);
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

    public int getDistance() {
        return getTargetPosition() - getCurrentPosition();
    }

    public int inchesToTicks(double inches) {
        return (int)Math.round(inches * ticksPerInch);
    }

}
