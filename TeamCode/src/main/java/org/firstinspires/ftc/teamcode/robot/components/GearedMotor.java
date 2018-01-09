package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.util.ramp.LogarithmicRamp;
import org.firstinspires.ftc.teamcode.util.ramp.Ramp;

/**
 * Created by EvanCoulson on 10/9/17.
 */

public class GearedMotor {
    public final static double MIN_POWER = 0.1d;
    public final static double FOWARD_DIRECTION = 1d;
    public final static double BACKWARD_DIRECTION = -1d;

    private GearChain chain;
    private DcMotor motor;
    private int pulses;
    private double wheelDiameter;
    public double ticksPerInch;

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
        this.ticksPerInch = (chain.calculateOutputRevolutions(pulses, 1)) / (wheelDiameter * Math.PI);
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
        int ticks = chain.calculateOutputRevolutions(pulses, revolutions);
        runMotor(ticks, power);
    }

    public void runInputGearTicks(int ticks, double power) {
        runMotor(ticks, power);
    }

    public void runInputGearRevolutions(double revolutions, double power) {
        int ticks = chain.calculateInputRevolutions(pulses, revolutions);
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

    public static void runMotorsRampedInches(double inches, double deltaInches, double maxPower, GearedMotor... motors) {
        checkLength(motors);
        checkPulses(motors);
        checkWheels(motors);
        int pulses = motors[0].pulses;
        int ticks = (int) Math.round(inches * motors[0].ticksPerInch);
        int deltaTicks = (int) Math.round(deltaInches * motors[0].ticksPerInch);
        runMotorsRampedTicks(ticks, deltaTicks, maxPower, motors);
    }

    public static void runMotosRampedInputRevolutions(double revolutions, double deltaRevolutions, double maxPower, GearedMotor... motors) {
        checkLength(motors);
        checkPulses(motors);
        int pulses = motors[0].pulses;
        int ticks = motors[0].chain.calculateInputRevolutions(pulses, revolutions);
        int deltaTicks = motors[0].chain.calculateInputRevolutions(pulses, deltaRevolutions);
        runMotorsRampedTicks(ticks, deltaTicks, maxPower, motors);
    }

    public static void runMotorsRampedOuputRevolutions(double revolutions, double deltaRevolutions, double maxPower, GearedMotor... motors) {
        checkLength(motors);
        checkPulses(motors);
        int pulses = motors[0].pulses;
        int ticks = motors[0].chain.calculateOutputRevolutions(pulses, revolutions);
        int deltaTicks = motors[0].chain.calculateOutputRevolutions(pulses, deltaRevolutions);
        runMotorsRampedTicks(ticks, deltaTicks, maxPower, motors);
    }

    public static void runMotorsRampedOutputTicks(int ticks, int deltaTicks, double maxPower, GearedMotor... motors) {
        checkLength(motors);
        checkPulses(motors);
        int newTicks = motors[0].chain.calculateOuputTicks(ticks);
        deltaTicks = motors[0].chain.calculateOuputTicks(deltaTicks);
        runMotorsRampedTicks(newTicks, deltaTicks, maxPower, motors);
    }

    public static void runMotorsRampedInputTicks(int ticks, int deltaTicks, double deltaRevs, double maxPower, GearedMotor... motors) {
        checkLength(motors);
        checkPulses(motors);
        runMotorsRampedTicks(ticks, deltaTicks, maxPower, motors);
    }


    private static void checkLength(GearedMotor... motors) {
        if (motors.length == 0) {
            throw new IllegalArgumentException("Can not ramp on 0 motors");
        }
    }

    private static void checkPulses(GearedMotor... motors) {
        int testPulse = motors[0].pulses;
        for (GearedMotor motor : motors) {
            if (motor == null) {
                throw new IllegalArgumentException("Null Motor");
            }
            if (motor.pulses != testPulse) {
                throw new IllegalArgumentException("All motors must have the same pulses.");
            }
        }
    }

    private static void checkWheels(GearedMotor... motors) {
        double testDiameter = motors[0].wheelDiameter;
        for (GearedMotor motor : motors) {
            if (motor.wheelDiameter != testDiameter) {
                throw new IllegalArgumentException("All wheels must have the same diameter.");
            }
        }
    }

    private static boolean motorsAreBusy(GearedMotor... motors) {
        for (GearedMotor motor : motors) {
            if (motor.isBusy()) {
                return true;
            }
        }
        return false;
    }

    private static int closestToZero(int i, int j) {
        if (Math.abs(i) < Math.abs(j))
            return i;

        return j;
    }

    public static int getMinDistanceFromTarget(GearedMotor... motors)
    {
        int minDistance = Integer.MAX_VALUE;
        for (GearedMotor motor : motors) {
            int difference = motor.getTargetPosition() - motor.getCurrentPosition();
            minDistance = closestToZero(minDistance, difference);
        }
        return minDistance;
    }

    private static void runMotorsRampedTicks(int ticks, int deltaTicks, double maxPower, GearedMotor... motors) {
        setTargetPosition(ticks, motors);
        Ramp ramp = new LogarithmicRamp(0, MIN_POWER, deltaTicks, maxPower);
        while (motorsAreBusy(motors)) {
            Thread.yield();
            int distanceFromTarget = getMinDistanceFromTarget(motors);
            double direction = FOWARD_DIRECTION;

            if (distanceFromTarget < 0) {
                distanceFromTarget = -distanceFromTarget;
                direction = BACKWARD_DIRECTION;
            }

            double scaledPower;
            if (ticks - distanceFromTarget < deltaTicks) {
                scaledPower = ramp.value(ticks - distanceFromTarget);
            } else {
                scaledPower = ramp.value(distanceFromTarget);
            }

            for (GearedMotor motor : motors) {
                motor.setPower(direction * scaledPower);
            }
        }
    }

    public static void setTargetPosition(int ticks, GearedMotor... motors) {
        for (GearedMotor motor : motors) {
            motor.setTargetPosition(ticks);
        }
    }

    public int inchesToTicks(double inches) {
        return (int)Math.round(inches * ticksPerInch);
    }

}
