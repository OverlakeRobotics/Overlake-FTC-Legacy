package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.util.ramp.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MecanumDriveSystem
{
    public DcMotor motorFrontLeft = null;
    public DcMotor motorFrontRight = null;
    public DcMotor motorBackLeft = null;
    public DcMotor motorBackRight = null;

    private static final double ticksPerRotation = 1120; // This is for the Andymark Neverest motor
    private static final double motorGearSize = 32; //TODO: This is a placeholder, use actual value for this
    private static final double wheelGearSize = 16; //TODO: This is a placeholder, use actual value for this
    private static final double wheelDiameterInches = 4.0; //TODO: This is a placeholder, use actual value for this
    private static final double gearRatio = wheelGearSize / motorGearSize;
    private static final double ticksPerInch = (ticksPerRotation * gearRatio) / (wheelDiameterInches * Math.PI);

    private HardwareMap hwMap = null;
    private IMUSystem imuSystem;

    private double initialHeading;
    public final double minimumPower = 0.1; //TODO: Figure out the best value for this.

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap)
    {
        this.hwMap = hwMap;

        this.motorFrontLeft = this.hwMap.dcMotor.get("motor_front_left");
        this.motorFrontRight = this.hwMap.dcMotor.get("motor_front_right");
        this.motorBackLeft = this.hwMap.dcMotor.get("motor_back_left");
        this.motorBackRight = this.hwMap.dcMotor.get("motor_back_right");
        this.imuSystem = new IMUSystem();
        this.imuSystem.init(hwMap);
        this.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorFrontRight.setDirection(DcMotor.Direction.FORWARD);
        this.motorBackRight.setDirection(DcMotor.Direction.FORWARD);

        this.initialHeading = this.imuSystem.getHeading();

        // Set all drive motors to zero power
        setPower(0);

        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setMode(DcMotor.RunMode runMode)
    {
        motorFrontLeft.setMode(runMode);
        motorFrontRight.setMode(runMode);
        motorBackLeft.setMode(runMode);
        motorBackRight.setMode(runMode);
    }

    public void setTargetPositionInches(double inches)
    {
        int ticks = this.inchesToTicks(inches);

        setTargetPosition(ticks);
    }

    public void setTargetPositionRevs(double revolutions)
    {
        int ticks = this.revolutionsToTicks(revolutions);

        setTargetPosition(ticks);

    }

    public void setTargetPosition(int position)
    {
        int frontLeftTarget = this.motorFrontLeft.getCurrentPosition() + position;
        int frontRightTarget = this.motorFrontRight.getCurrentPosition() + position;
        int backLeftTarget = this.motorBackLeft.getCurrentPosition() + position;
        int backRightTarget = this.motorBackRight.getCurrentPosition() + position;

        // Tell the motors where we are going
        this.motorFrontLeft.setTargetPosition(frontLeftTarget);
        this.motorFrontRight.setTargetPosition(frontRightTarget);
        this.motorBackLeft.setTargetPosition(backLeftTarget);
        this.motorBackRight.setTargetPosition(backRightTarget);

        setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean anyMotorsBusy()
    {
        return (this.motorFrontLeft.isBusy() && this.motorFrontRight.isBusy() && this.motorBackRight.isBusy() && this.motorBackLeft.isBusy());
    }

    public int revolutionsToTicks(double revolutions)
    {
        return (int) Math.round(revolutions * this.ticksPerRotation);
    }

    public double ticksToRevolutions(int ticks) {
        return ((double) ticks / this.ticksPerRotation);
    }

    public int inchesToTicks(double inches) {
        return (int) Math.round(inches * this.ticksPerInch);
    }

    public double ticksToInches(int ticks) {
        return ((double) ticks / this.ticksPerInch);
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

    // Tweeks the left and right motors by increment.
    // increment is added to the current power of the left wheels
    /// and subtracted from the current power of the right wheels.
    public void tweakTankDrive(double increment)
    {
        double leftMotorPower = this.motorBackLeft.getPower();
        double rightMotorPower = this.motorBackRight.getPower();

        tankDrive(leftMotorPower + increment, rightMotorPower - increment);
    }

    public void tankDrive(double leftPower, double rightPower)
    {
        this.motorFrontLeft.setPower(leftPower);
        this.motorBackLeft.setPower(leftPower);
        this.motorFrontRight.setPower(rightPower);
        this.motorBackRight.setPower(rightPower);
    }

    public void setPower(double power)
    {
        this.motorFrontLeft.setPower(power);
        this.motorFrontRight.setPower(power);
        this.motorBackLeft.setPower(power);
        this.motorBackRight.setPower(power);
    }

    public void adjustPower(Ramp ramp)
    {
        // Adjust the motor power as we get closer to the target
        int minDistance = this.getMinimumDistanceFromTarget();

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

    public void mecanumDrive(float rightX, float rightY, float leftX, float leftY, boolean slowDrive)
    {
        rightX = Range.clip(rightX, -1, 1);
        rightY = Range.clip(rightY, -1, 1);
        leftX = Range.clip(leftX, -1, 1);
        leftY = Range.clip(leftY, -1, 1);

        rightX = scaleJoystickValue(rightX, slowDrive);
        rightY = scaleJoystickValue(rightY, slowDrive);
        leftX = scaleJoystickValue(leftX, slowDrive);
        leftY = scaleJoystickValue(leftY, slowDrive);

        // write the values to the motors
        double frontRightPower = leftY + rightX + leftX;
        double backRightPower = leftY + rightX - leftX;
        double frontLeftPower = leftY - rightX - leftX;
        double backLeftPower = leftY - rightX + leftX;
        motorFrontRight.setPower(Range.clip(frontRightPower, -1, 1));
        motorBackRight.setPower(Range.clip(backRightPower, -1, 1));
        motorFrontLeft.setPower(Range.clip(frontLeftPower, -1, 1));
        motorBackLeft.setPower(Range.clip(backLeftPower, -1, 1));
    }

    public void driveGodMode(double rightX, float rightY, float leftX, float leftY) {
        double currentHeading = Math.toRadians(imuSystem.getHeading());
        double headingDiff = initialHeading - currentHeading;


        double speed = Math.sqrt(leftX * leftX + leftY * leftY);
        double angle = Math.atan2(leftX, leftY) + (Math.PI / 2) + headingDiff;
        double changeOfDirectionSpeed = rightX;
        double x = speed * Math.cos(angle);
        double y = speed * Math.sin(angle);

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
        motorFrontRight.setPower(Range.clip(y + x, -1, 1));
        motorBackRight.setPower(Range.clip(y - x, -1, 1));
        motorFrontLeft.setPower(Range.clip(y - x, -1, 1));
        motorBackLeft.setPower(Range.clip(y + x, -1, 1));
    }

    public void mecanumDrivePolar(double radians, double power)
    {
        double x = Math.cos(radians)*power;
        double y = Math.sin(radians)*power;

        mecanumDriveXY(x, y);
    }


    float scaleJoystickValue(float joystickValue, boolean slowDrive)
    {
        double scale = slowDrive ? 0.31 : 0.62;
        if(joystickValue > 0)
        {
            return (float)((joystickValue*joystickValue)*scale);
        }
        else
        {
            return (float)(-(joystickValue*joystickValue)*scale);
        }
    }



}

