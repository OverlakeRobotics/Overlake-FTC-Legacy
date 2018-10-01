package org.firstinspires.ftc.teamcode.robot.systems.physical;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.dcmotors.MotorType;
import org.firstinspires.ftc.teamcode.robot.components.GearChain;
import org.firstinspires.ftc.teamcode.robot.components.motors.GearedWheelMotor;
import org.firstinspires.ftc.teamcode.logger.LoggingService;
import org.firstinspires.ftc.teamcode.robot.systems.base.System;
import org.firstinspires.ftc.teamcode.scale.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MecanumDriveSystem extends System
{
    private final IScale JOYSTICK_SCALE = new LinearScale(0.62, 0);
    private final double WHEEL_DIAMETER_INCHES = 4.0;

    private final double ticksPerRotation = 1120;
    private final double motorGearSize = 32; //TODO: This is a placeholder, use actual scale for this
    private final double wheelGearSize = 16; //TODO: This is a placeholder, use actual scale for this
    private final double gearRatio = wheelGearSize / motorGearSize;
    private final double wheelDiameterInches = 4.0; //TODO: This is a placeholder, use actual scale for this
    private final double ticksPerInch = (ticksPerRotation * gearRatio) / (wheelDiameterInches * Math.PI);

    public IMUSystem imuSystem;

    public GearedWheelMotor motorFrontLeft;
    public GearedWheelMotor motorFrontRight;
    public GearedWheelMotor motorBackLeft;
    public GearedWheelMotor motorBackRight;

    public double initialHeading;

    Telemetry.Item distanceItem;
    Telemetry.Item powerItem;

    /* Constructor */
    public MecanumDriveSystem(OpMode opMode)
    {
        super(opMode, "MecanumDrive");
        logger.setLoggingServices(LoggingService.FILE);

        imuSystem = new IMUSystem(opMode);

        initialHeading = Math.toRadians(imuSystem.getHeading());

        GearChain driveChain = new GearChain(MotorType.NEVEREST40_PULSES, 80, 64);
        this.motorFrontLeft = new GearedWheelMotor(driveChain, map.dcMotor.get(config.getString("motorFL")), WHEEL_DIAMETER_INCHES);
        this.motorFrontRight = new GearedWheelMotor(driveChain, map.dcMotor.get(config.getString("motorFR")), WHEEL_DIAMETER_INCHES);
        this.motorBackRight = new GearedWheelMotor(driveChain, map.dcMotor.get(config.getString("motorBR")), WHEEL_DIAMETER_INCHES);
        this.motorBackLeft = new GearedWheelMotor(driveChain, map.dcMotor.get(config.getString("motorBL")), WHEEL_DIAMETER_INCHES);

        this.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorFrontRight.setDirection(DcMotor.Direction.FORWARD);
        this.motorBackRight.setDirection(DcMotor.Direction.FORWARD);

        // Set PID coeffiecents
        setAllMotorsPID(config.getDouble("P"), config.getDouble("I"), config.getDouble("D"));

        powerItem = telemetry.addData("power", 0);
        distanceItem = telemetry.addData("distance", 0);

        // Set all drive motors to zero power
        setPower(0);
    }

    public int inchesToTicks(double inches)
    {
        return (int) Math.round(inches * this.ticksPerInch);
    }

    public void setDirection(DcMotorSimple.Direction direction)
    {
        motorFrontLeft.setDirection(direction);
        motorFrontRight.setDirection(direction);
        motorBackLeft.setDirection(direction);
        motorBackRight.setDirection(direction);
    }

    public void setAllMotorsPID(double P, double I, double D)
    {
        //TODO
    }

    public boolean anyMotorsBusy()
    {
        return motorFrontLeft.isBusy() ||
                motorFrontRight.isBusy() ||
                motorBackLeft.isBusy() ||
                motorBackRight.isBusy();
    }

    public void setTargetPosition(int ticks)
    {
        motorBackLeft.setTargetPosition(ticks);
        motorBackRight.setTargetPosition(ticks);
        motorFrontLeft.setTargetPosition(ticks);
        motorFrontRight.setTargetPosition(ticks);
    }

    public void setRunMode(DcMotor.RunMode runMode)
    {
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
        this.motorFrontRight.run(Range.clip(frontRightPower, -1, 1));
        this.motorBackRight.run(Range.clip(backRightPower, -1, 1));
        this.motorFrontLeft.run(Range.clip(frontLeftPower - leftX, -1, 1));
        this.motorBackLeft.run(Range.clip(backLeftPower + leftX, -1, 1));
    }

    private float scaleJoystickValue(float joystickValue)
    {
        return (float) JOYSTICK_SCALE.scaleX(joystickValue * joystickValue);
    }

    public void driveGodMode(float rightX, float rightY, float leftX, float leftY)
    {
        driveGodMode(rightX, rightY, leftX, leftY, 1);
    }

    public void driveGodMode(float rightX, float rightY, float leftX, float leftY, float coefficient)
    {
        double currentHeading = Math.toRadians(imuSystem.getHeading());
        double headingDiff = initialHeading - currentHeading;

        rightX = scaleJoystickValue(rightX);
        leftX = scaleJoystickValue(leftX);
        leftY = scaleJoystickValue(leftY);

        double speed = Math.sqrt(leftX * leftX + leftY * leftY);
        double angle = Math.atan2(leftX, leftY) + (Math.PI / 2) + headingDiff;
        double x = coefficient * speed * Math.cos(angle);
        double y = coefficient * speed * Math.sin(angle);

        double frontLeft = y - rightX + x;
        double frontRight = y + rightX - x;
        double backLeft = y - rightX - x;
        double backRight = y + rightX + x;

        List<Double> powers = Arrays.asList(frontLeft, frontRight, backLeft, backRight);
        clampPowers(powers);

        motorFrontLeft.run(powers.get(0));
        motorFrontRight.run(powers.get(1));
        motorBackLeft.run(powers.get(2));
        motorBackRight.run(powers.get(3));
    }

    private void clampPowers(List<Double> powers)
    {
        double minPower = Collections.min(powers);
        double maxPower = Collections.max(powers);
        double maxMag = Math.max(Math.abs(minPower), Math.abs(maxPower));

        if (maxMag > 1.0)
        {
            for (int i = 0; i < powers.size(); i++)
            {
                powers.set(i, powers.get(i) / maxMag);
            }
        }
    }

    public void resetInitialHeading()
    {
        this.initialHeading = Math.toRadians(this.imuSystem.getHeading());
    }

    public void mecanumDriveXY(double x, double y)
    {
        this.motorFrontRight.run(Range.clip(y + x, -1, 1));
        this.motorBackRight.run(Range.clip(y - x, -1, 1));
        this.motorFrontLeft.run(Range.clip(y - x, -1, 1));
        this.motorBackLeft.run(Range.clip(y + x, -1, 1));
    }

    public void mecanumDrivePolar(double radians, double power)
    {
        double x = Math.cos(radians) * power;
        double y = Math.sin(radians) * power;
        mecanumDriveXY(x, y);
    }

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveTicksXY(int x, int y, double maxPower, int deltaTicks)
    {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveRevolutionsXY(double x, double y, double maxPower, double deltaRevs)
    {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveRevolutionsXY(double x, double y, double power)
    {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveInchesXY(double x, double y, double maxPower, double deltaInches)
    {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    // this funciton wil strafe an X Y distance. Need a good way to find distance traveled
    public void driveInchesXY(double x, double y, double power)
    {
        throw new IllegalArgumentException("Unimplemented Function");
    }

    public void driveToPositionInches(double inches, double power)
    {
        driveToPositionInches(inches, power, inches / 10);
    }

    private int getMinimumDistanceFromTarget()
    {
        int d = this.motorFrontLeft.getTargetPosition() - this.motorFrontLeft.getCurrentPosition();
        d = closestToZero(d, this.motorFrontRight.getTargetPosition() - this.motorFrontRight.getCurrentPosition());
        d = closestToZero(d, this.motorBackLeft.getTargetPosition() - this.motorBackLeft.getCurrentPosition());
        d = closestToZero(d, this.motorBackRight.getTargetPosition() + this.motorBackRight.getCurrentPosition());
        distanceItem.setValue(d);
        return d;
    }

    private int closestToZero(int v1, int v2)
    {
        if (Math.abs(v1) < Math.abs(v2))
            return v1;

        return v2;
    }

    public void driveToPositionInches(double inches, double power, double rampLength)
    {
        Ramp ramp = new ExponentialRamp(new Point(0, 0.1), new Point(motorBackLeft.inchesToTicks(rampLength), power));

        setTargetPositionInches(inches);
        setPower(0.1);

        while (anyMotorsBusy())
        {
            int minDistance = getMinimumDistanceFromTarget();

            // ramp assumes the distance away from the target is positive,
            // so we make it positive here and account for the direction when
            // the motor power is set.
            double direction = 1.0;
            if (minDistance < 0)
            {
                minDistance = -minDistance;
                direction = -1.0;
            }

            double scaledPower = ramp.scaleX(minDistance);

            setPower(direction * scaledPower);
        }
        setPower(0);
    }

    public void driveToPositionRevolutions(double revs, double power)
    {
        motorBackLeft.setOutputGearTargetRevolutions(revs);
        motorBackRight.setOutputGearTargetRevolutions(revs);
        motorFrontRight.setOutputGearTargetRevolutions(revs);
        motorFrontLeft.setOutputGearTargetRevolutions(revs);
    }

    public void turn(double degrees, double maxPower, LinearOpMode mode)
    {
        double heading = this.imuSystem.getHeading();
        double targetHeading = 0;

        targetHeading = heading + degrees;

        if (targetHeading > 360)
        {
            targetHeading -= 360;
        }
        else if (targetHeading < 0)
        {
            targetHeading += 360;
        }

        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Between 130 and 2 degrees away from the target
        // we want to slow down from maxPower to 0.1
        ExponentialRamp ramp = new ExponentialRamp(new Point(2.0, 0.1), new Point(130.0, maxPower));

        while (!mode.isStopRequested() && Math.abs(computeDegreesDiff(targetHeading, heading)) > 1)
        {
            telemetry.update();
            double power = getTurnPower(ramp, targetHeading, heading);
            telemetry.addLine("heading: " + heading);
            telemetry.addLine("target heading: " + targetHeading);
            telemetry.addLine("power: " + power);

            tankDrive(power, -power);

            try
            {
                mode.sleep(50);
            }
            catch (Exception e)
            {
            }

            heading = this.imuSystem.getHeading();
        }
        this.setPower(0);
    }

    public void tankDrive(double leftPower, double rightPower)
    {
        this.motorFrontLeft.run(leftPower);
        this.motorBackLeft.run(leftPower);
        this.motorFrontRight.run(rightPower);
        this.motorBackRight.run(rightPower);
    }

    private double getTurnPower(Ramp ramp, double targetHeading, double heading)
    {
        double sign = 1.0;
        double diff = computeDegreesDiff(targetHeading, heading);
        if (diff < 0)
        {
            sign = -1.0;
            diff = -diff;
        }

        return sign * ramp.scaleX(diff);
    }

    private double computeDegreesDiff(double targetHeading, double heading)
    {
        double diff = targetHeading - heading;
        //TODO: This needs to be commented. Also, might be able to compute using mod.
        if (Math.abs(diff) > 180)
        {
            diff += (-360 * (diff / Math.abs(diff)));
        }
        return diff;
    }

    public void adjustPowerBackwordz(Ramp ramp)
    {
        // Adjust the motor power as we get closer to the target
        int minDistance = this.getMinimumDistanceFromTarget();

        // ramp assumes the distance away from the target is positive,
        // so we make it positive here and account for the direction when
        // the motor power is set.
        double direction = 1.0;
        if (minDistance < 0)
        {
            minDistance = -minDistance;
            direction = -1.0;
        }

        double scaledPower = ramp.scaleX(minDistance);

        setPower(direction * scaledPower);
    }

    public void driveToPositionInchezBackwordz(double inches, double maxPower, LinearOpMode opMode)
    {
        double minPower = 0.1;

        setTargetPositionInches(inches);


        //    Create a Ramp that will map a distance in revolutions between 0.01 and 1.0
        //    onto power values between minPower and maxPower.
        //    When the robot is greater than 1.0 revolution from the target the power
        //    will be set to maxPower, but when it gets within 1.0 revolutions, the power
        //    will be ramped down to minPower
        //
        Ramp ramp = new ExponentialRamp(
            new Point(revolutionsToTicks(0.01), minPower),
            new Point(revolutionsToTicks(1.0), maxPower)
        );

        // Wait until they are done
        setPower(maxPower);
        powerItem.setValue(maxPower);
        while (anyMotorsBusy())
        {
            telemetry.update();

            opMode.idle();

            adjustPowerBackwordz(ramp);
        }

        // Now that we've arrived, kill the motors so they don't just sit there buzzing
        setPower(0);

        // Always leave the screen looking pretty
        telemetry.update();
    }

    public void driveToPositionInchez(double inches, double maxPower, LinearOpMode opMode)
    {
        double minPower = 0.1;

        setTargetPositionInches(inches);


        //    Create a Ramp that will map a distance in revolutions between 0.01 and 1.0
        //    onto power values between minPower and maxPower.
        //    When the robot is greater than 1.0 revolution from the target the power
        //    will be set to maxPower, but when it gets within 1.0 revolutions, the power
        //    will be ramped down to minPower
        //
        Ramp ramp = new ExponentialRamp(
            new Point(revolutionsToTicks(0.01), minPower),
            new Point(revolutionsToTicks(1.0), maxPower)
        );

        // Wait until they are done
        setPower(-maxPower);
        powerItem.setValue(maxPower);
        while (anyMotorsBusy())
        {
            telemetry.update();

            opMode.idle();

            adjustPower(ramp);
        }

        // Now that we've arrived, kill the motors so they don't just sit there buzzing
        setPower(0);

        // Always leave the screen looking pretty
        telemetry.update();
    }

    public void setPower(double power)
    {
        this.motorFrontLeft.run(power);
        this.motorFrontRight.run(power);
        this.motorBackLeft.run(power);
        this.motorBackRight.run(power);
    }

    public void adjustPower(Ramp ramp)
    {
        // Adjust the motor power as we get closer to the target
        int minDistance = this.getMinimumDistanceFromTarget();

        // ramp assumes the distance away from the target is positive,
        // so we make it positive here and account for the direction when
        // the motor power is set.
        double direction = 1.0;
        if (minDistance < 0)
        {
            minDistance = -minDistance;
            direction = -1.0;
        }

        double scaledPower = -ramp.scaleX(minDistance);

        setPower(direction * scaledPower);
    }

    public int revolutionsToTicks(double revolutions)
    {
        return (int) Math.round(revolutions * this.ticksPerRotation);
    }

    public void setTargetPositionInches(double inches)
    {
        int ticks = inchesToTicks(inches);

        setTargetPosition(ticks);
    }

}

