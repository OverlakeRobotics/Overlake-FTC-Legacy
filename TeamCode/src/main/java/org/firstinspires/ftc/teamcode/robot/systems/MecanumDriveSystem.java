package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.robot.components.GearChain;
import org.firstinspires.ftc.teamcode.robot.components.GearedMotor;
import org.firstinspires.ftc.teamcode.util.logger.LoggingService;
import org.firstinspires.ftc.teamcode.util.ramp.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private double initialHeading;

    /* Constructor */
    public MecanumDriveSystem(OpMode opMode) {
        super(opMode, "MecanumDrive");
        logger.setLoggingServices(LoggingService.FILE);

        imuSystem = new IMUSystem(opMode);

        initialHeading = Math.toRadians(imuSystem.getHeading());

        this.motorFrontLeft = new GearedMotor(MOTOR_PULSES, WHEEL_DIAMETER_INCHES, map.dcMotor.get(config.getString("motorFL")), 80, 64);
        this.motorFrontRight = new GearedMotor(MOTOR_PULSES, WHEEL_DIAMETER_INCHES, map.dcMotor.get(config.getString("motorFR")), 80, 64);
        this.motorBackRight = new GearedMotor(MOTOR_PULSES, WHEEL_DIAMETER_INCHES, map.dcMotor.get(config.getString("motorBR")), 80, 64);
        this.motorBackLeft = new GearedMotor(MOTOR_PULSES, WHEEL_DIAMETER_INCHES, map.dcMotor.get(config.getString("motorBL")), 80, 64);

        this.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorFrontRight.setDirection(DcMotor.Direction.FORWARD);
        this.motorBackRight.setDirection(DcMotor.Direction.FORWARD);

        // Set PID coeffiecents
        setAllMotorsPID(config.getDouble("P"), config.getDouble("I"), config.getDouble("D"));
        this.initialHeading = Math.toRadians(this.imuSystem.getHeading());

        // Set all drive motors to zero power
        setPower(0);
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

    public void setTargetPosition(int ticks) {
        motorBackLeft.motor.setTargetPosition(ticks);
        motorBackRight.motor.setTargetPosition(ticks);
        motorFrontLeft.motor.setTargetPosition(ticks);
        motorFrontRight.motor.setTargetPosition(ticks);
    }

    public void setPower(double power)
    {
        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);
    }

    public void setRunMode(DcMotor.RunMode runMode) {
        logger.log(String.format("Run Mode: %s", runMode));
        motorFrontLeft.setRunMode(runMode);
        motorFrontRight.setRunMode(runMode);
        motorBackLeft.setRunMode(runMode);
        motorBackRight.setRunMode(runMode);
    }

    public void mecanumDrive(float rightX, float rightY, float leftX, float leftY, boolean slowDrive)
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

    public void driveCircle(float power) {
        float lx = power;
        float rx = 0.33f * lx;

        mecanumDrive(rx, 0f, lx, 0f, false);
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

    public void driveGodMode(double rightX, float rightY, float leftX, float leftY) {
        driveGodMode(rightX, rightY, leftX, leftY, 1);
    }

    public void driveGodMode(double rightX, float rightY, float leftX, float leftY, float coeff) {
        double currentHeading = Math.toRadians(imuSystem.getHeading());
        double headingDiff = initialHeading - currentHeading;


        double speed = Math.sqrt(leftX * leftX + leftY * leftY);
        double angle = Math.atan2(leftX, leftY) + (Math.PI / 2) + headingDiff;
        double changeOfDirectionSpeed = rightX;
        double x = coeff * speed * Math.cos(angle);
        double y = coeff * speed * Math.sin(angle);

        double frontLeft = y - changeOfDirectionSpeed + x;
        double frontRight = y + changeOfDirectionSpeed - x;
        double backLeft = y - changeOfDirectionSpeed - x;
        double backRight = y + changeOfDirectionSpeed + x;

        List<Double> powers = Arrays.asList(frontLeft, frontRight, backLeft, backRight);
        clampPowers(powers);

        motorFrontLeft.setPower(powers.get(0));
        motorFrontRight.setPower(powers.get(1));
        motorBackLeft.setPower(powers.get(2));
        motorBackRight.setPower(powers.get(3));
    }

    public void resetInitialHeading() {
        this.initialHeading = Math.toRadians(this.imuSystem.getHeading());
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

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveTicksXY(int x, int y, double maxPower, int deltaTicks) {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveRevolutionsXY(double x, double y, double maxPower, double deltaRevs) {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveRevolutionsXY(double x, double y, double power) {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveInchesXY(double x, double y, double maxPower, double deltaInches) {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveInchesXY(double x, double y, double power) {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    public void driveToPositionInches(double inches, double power) {
        driveToPositionInches(inches, power, inches / 10);
    }

    public int getMinimumDistanceFromTarget()
    {
        int d = this.motorFrontLeft.getTargetPosition() - this.motorFrontLeft.getCurrentPosition();
        d = closestToZero(d, this.motorFrontRight.getTargetPosition() - this.motorFrontRight.getCurrentPosition());
        d = closestToZero(d, this.motorBackLeft.getTargetPosition() - this.motorBackLeft.getCurrentPosition());
        d = closestToZero(d, this.motorBackRight.getTargetPosition() - this.motorBackRight.getCurrentPosition());

        return d;
    }

    private int closestToZero(int v1, int v2)
    {
        if (Math.abs(v1) < Math.abs(v2))
            return v1;

        return v2;
    }

    public void driveToPositionInches(double inches, double power, double rampLength) {
        Ramp ramp = new ExponentialRamp(0, 0.1, motorBackLeft.inchesToTicks(rampLength), power);

        while(anyMotorsBusy()) {
            int minDistance = getMinimumDistanceFromTarget();

            // ramp assumes the distance away from the target is positive,
            // so we make it positive here and account for the direction when
            // the motor power is set.
            double direction = 1.0;
            if (minDistance < 0) {
                minDistance = -minDistance;
                direction = -1.0;
            }

            double scaledPower = ramp.value(minDistance);

            setPower(direction * scaledPower);
        }
        setPower(0);
    }

    public void driveToPositionRevolutions(double revs, double power) {
        motorBackLeft.runOutputGearRevolutions(revs, power);
        motorBackRight.runOutputGearRevolutions(revs, power);
        motorFrontRight.runOutputGearRevolutions(revs, power);
        motorFrontLeft.runOutputGearRevolutions(revs, power);
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
            motorBackLeft.setPower(power);
            motorFrontLeft.setPower(power);
            motorBackRight.setPower(-power);
            motorFrontRight.setPower(-power);
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

