package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.ramp.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MecanumDriveSystem extends System
{
    private final float SCALE_FACTOR = 0.62f;
    private final double WHEEL_DIAMETER_INCHES = 4.0;

    public IMUSystem imuSystem;
    public final int MOTOR_PULSES = GearChain.NEVEREST40_PULSES;

    public GearedMotor motorFrontLeft;
    public GearedMotor motorFrontRight;
    public GearedMotor motorBackLeft;
    public GearedMotor motorBackRight;

    private double startingIMUHeading;

    /* Constructor */
    public MecanumDriveSystem(OpMode opMode) {
        super(opMode, "MecanumDrive");

        imuSystem = new IMUSystem(opMode);
        startingIMUHeading = imuSystem.getHeading();

        this.motorFrontLeft = new GearedMotor(MOTOR_PULSES, WHEEL_DIAMETER_INCHES, map.dcMotor.get(config.getString("motorFL")), 80, 64);
        this.motorFrontRight = new GearedMotor(MOTOR_PULSES, WHEEL_DIAMETER_INCHES, map.dcMotor.get(config.getString("motorFR")), 80, 64);
        this.motorBackRight = new GearedMotor(MOTOR_PULSES, WHEEL_DIAMETER_INCHES, map.dcMotor.get(config.getString("motorBR")), 80, 64);
        this.motorBackLeft = new GearedMotor(MOTOR_PULSES, WHEEL_DIAMETER_INCHES, map.dcMotor.get(config.getString("motorBL")), 80, 64);

        this.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.motorFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        this.motorBackLeft.setDirection(DcMotor.Direction.FORWARD);
        this.motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        // Set PID coeffiecents
        setAllMotorsPID(config.getDouble("P"), config.getDouble("I"), config.getDouble("D"));
        // Set all drive motors to zero power
        setPower(0);
    }

    public void driveTicks(int ticks, double power) {
        motorFrontLeft.runInputGearTicks(ticks, power);
        motorFrontRight.runInputGearTicks(ticks, power);
        motorBackLeft.runInputGearTicks(ticks, power);
        motorBackRight.runInputGearTicks(ticks, power);
    }

    public void driveRevs(double revolutions, double power) {
        motorFrontLeft.runOutputGearRevolutions(revolutions, power);
        motorFrontRight.runOutputGearRevolutions(revolutions, power);
        motorBackLeft.runOutputGearRevolutions(revolutions, power);
        motorBackRight.runOutputGearRevolutions(revolutions, power);
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        motorFrontLeft.setDirection(direction);
        motorFrontRight.setDirection(direction);
        motorBackLeft.setDirection(direction);
        motorBackRight.setDirection(direction);
    }

    public void setAllMotorsPID(double P, double I, double D) {
        //TODO
    }

    public boolean anyMotorsBusy()
    {
        return motorFrontLeft.isBusy() ||
                motorFrontRight.isBusy() ||
                motorBackLeft.isBusy() ||
                motorBackRight.isBusy();
    }

    public void setPower(double power)
    {
        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);
    }

    public void setRunMode(DcMotor.RunMode runMode) {
        motorFrontLeft.setRunMode(runMode);
        motorFrontRight.setRunMode(runMode);
        motorBackLeft.setRunMode(runMode);
        motorBackRight.setRunMode(runMode);
    }

    public void setRightPower(double power) {
        motorBackRight.setPower(power);
        motorFrontRight.setPower(power);
    }

    public void setLeftPower(double power) {
        motorFrontLeft.setPower(power);
        motorBackLeft.setPower(power);
    }

    public void setForwardPower(double power) {
        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
    }

    public void setBackPower(double power) {
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);
    }

    public void mecanumDrive(float rightX, float rightY, float leftX, float leftY)
    {
        rightX = Range.clip(rightX, -1, 1);
        leftX = Range.clip(leftX, -1, 1);
        leftY = Range.clip(leftY, -1, 1);

        rightX = scaleJoystickValue(rightX);
        leftX = scaleJoystickValue(leftX);
        leftY = scaleJoystickValue(leftY);

        // write the values to the motors
        double frontRightPower = leftY + rightX + leftX;
        double backRightPower = leftY + rightX - leftX;
        double frontLeftPower = leftY - rightX - leftX;
        double backLeftPower = leftY - rightX + leftX;
        this.motorFrontRight.setPower(Range.clip(frontRightPower, -1, 1));
        this.motorBackRight.setPower(Range.clip(backRightPower, -1, 1));
        this.motorFrontLeft.setPower(Range.clip(frontLeftPower - leftX, -1, 1));
        this.motorBackLeft.setPower(Range.clip(backLeftPower + leftX, -1, 1));
    }

    public void driveGodMode(double rightX, float rightY, float leftX, float leftY) {
        double speed = Math.sqrt(leftX * leftX + leftY * leftY);
        double angle = Math.atan2(leftX, leftY);
        double changeOfDirectionSpeed = -rightX;

        double frontLeft = speed * Math.sin(angle + Math.PI / 4) + changeOfDirectionSpeed;
        double frontRight = speed * Math.cos(angle + Math.PI / 4) - changeOfDirectionSpeed;
        double backLeft = speed * Math.cos(angle + Math.PI / 4) + changeOfDirectionSpeed;
        double backRight = speed * Math.sin(angle + Math.PI / 4) - changeOfDirectionSpeed;

        List<Double> powers = Arrays.asList(frontLeft, frontRight, backLeft, backRight);
        clampPowers(powers);

        motorFrontLeft.setPower(frontLeft);
        motorFrontRight.setPower(frontRight);
        motorBackLeft.setPower(backLeft);
        motorBackRight.setPower(backRight);
    }

    private void clampPowers(List<Double> powers) {
        double minPower = Collections.min(powers);
        double maxPower = Collections.max(powers);
        double maxMag = Math.max(Math.abs(minPower), Math.abs(maxPower));

        if (maxMag > 1.0) {
            for (int i = 0; i < powers.size(); i++) {
                powers.set(i, powers.get(i) / maxMag);
            }
        }
    }

    public void mecanumDriveXY(double x, double y)
    {
        this.motorFrontRight.setPower(Range.clip(y + x, -1, 1));
        this.motorBackRight.setPower(Range.clip(y - x, -1, 1));
        this.motorFrontLeft.setPower(Range.clip(y - x, -1, 1));
        this.motorBackLeft.setPower(Range.clip(y + x, -1, 1));
    }

    public void mecanumDrivePolar(double radians, double power)
    {
        double x = Math.cos(radians) * power;
        double y = Math.sin(radians) * power;
        mecanumDriveXY(x, y);
    }

    public void driveInchesPolar(double inches, double direction, double maxPower, double deltaInches) {
        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        GearedMotor.runMotorsRampedInches(
                inches, deltaInches, maxPower, motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft
        );
    }

    public void driveInchesPolar(double inches, double direction, double power) {
        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motorFrontRight.runOutputWheelInches(inches, power);
        this.motorBackRight.runOutputWheelInches(inches, power);
        this.motorFrontLeft.runOutputWheelInches(inches, power);
        this.motorBackLeft.runOutputWheelInches(inches, power);
    }

    public void driveInchesXY(double x, double y, double maxPower, double deltaInches) {
        double inches = Math.sqrt(x * x + y * y);
        double direction = Math.atan2(y,x);
        driveInchesPolar(inches, direction, maxPower, deltaInches);
    }

    public void driveInchesXY(double x, double y, double power) {
        double inches = Math.sqrt(x * x + y * y);
        double direction = Math.atan2(y,x);
        driveInchesPolar(inches, direction, power);
    }

    public void turn(double angle, double maxPower) {
        double heading = imuSystem.getHeading();
        double targetHeading = heading + angle;
        if (targetHeading > 0) {
            targetHeading = targetHeading % 360;
        } else {
            targetHeading = (360 + targetHeading) % 360;
        }

        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Ramp ramp = new ExponentialRamp(2.0, 0.1, 130.0, maxPower);
        while (computeDegreesDiff(targetHeading, heading) > 1) {
            double sign = 1.0;
            double diff = computeDegreesDiff(targetHeading, heading);
            if (diff < 0)
            {
                sign = -1.0;
                diff = -diff;
            }

            double power = sign*ramp.value(diff);
            setLeftPower(power);
            setRightPower(-power);
            heading = imuSystem.getHeading();
        }
    }

    private double computeDegreesDiff(double targetHeading, double heading) {
        double diff = targetHeading - heading;
        //TODO: This needs to be commented. Also, might be able to compute using mod.
        if (Math.abs(diff) > 180)
        {
            diff = -(360 * (diff / Math.abs(diff)));
        }

        return Math.abs(diff);
    }

    float scaleJoystickValue(float joystickValue)
    {
        return joystickValue > 0
                ? ((joystickValue * joystickValue) * SCALE_FACTOR)
                : (-(joystickValue * joystickValue) * SCALE_FACTOR);
    }
}

