package org.firstinspires.ftc.teamcode.robot.components.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;

import org.firstinspires.ftc.teamcode.robot.components.GearChain;

/**
 * Created by EvanCoulson on 10/9/17.
 */

public class GearedMotor implements IGearedMotor {

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

    public Direction getDirection() {
        return motor.getDirection();
    }

    public void setOutputGearTargetTicks(int ticks) {
        int outputTicks = chain.calculateOuputTicks(ticks);
        setTargetPosition(outputTicks);
    }

    public void setOutputGearTargetRevolutions(double revolutions) {
        int ticks = chain.calculateOutputRevolutionTicks(revolutions);
        setTargetPosition(ticks);
    }

    public void setInputGearTargetTicks(int ticks) {
        setTargetPosition(ticks);
    }

    public void setInputGearTargetRevolutions(double revolutions) {
        int ticks = chain.calculateOutputRevolutionTicks(revolutions);
        setTargetPosition(ticks);
    }

    public void runMotor(int ticks, double power) {
        setTargetPosition(ticks);
        motor.setPower(power);
    }

    public void run(double power) {
        EnsurePowerIsInRange(power);
        motor.setPower(power);
    }

    private void EnsurePowerIsInRange(double power) {
        if (power > 1) {
            throw new IllegalArgumentException("Power must be less than 1.0");
        }
    }

    public void setRunMode(DcMotor.RunMode mode) {
        motor.setMode(mode);
    }

    public DcMotor.RunMode getRunMode() {
        return motor.getMode();
    }

    public void setTargetPosition(int ticks) {
        int current = motor.getCurrentPosition();
        motor.setTargetPosition(current + ticks);
    }

    public int getTargetPosition() {
        return motor.getTargetPosition();
    }

    public double getPower() {
        return motor.getPower();
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    public int getDistanceToTargetPosition() {
        return getTargetPosition() - getCurrentPosition();
    }
}
