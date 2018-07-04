package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.dcmotors.MotorType;

/**
 * Created by EvanCoulson on 10/9/17.
 */

public class GearedMotor {

    private GearChain chain;
    private DcMotor motor;

    public GearedMotor(GearChain chain, DcMotor motor) {
        this.chain = chain;
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

    public DcMotorSimple.Direction getDirection() {
        return motor.getDirection();
    }

    public void runOutputGearTicks(int ticks, double power) {
        int outputTicks = chain.calculateOuputTicks(ticks);
        setTargetPosition(ticks);
    }

    public void runOutputGearRevolutions(double revolutions, double power) {
        int ticks = chain.calculateOutputRevolutionTicks(revolutions);
        setTargetPosition(ticks);
    }

    public void runInputGearTicks(int ticks, double power) {
        setTargetPosition(ticks);
    }

    public void runInputGearRevolutions(double revolutions, double power) {
        int ticks = chain.calculateOutputRevolutionTicks(revolutions);
        setTargetPosition(ticks);
    }

    protected void runMotor(int ticks, double power) {
        setTargetPosition(ticks);
        motor.setPower(power);
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public void setRunMode(DcMotor.RunMode mode) {
        motor.setMode(mode);
    }

    public void setTargetPosition(int ticks) {
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
}
