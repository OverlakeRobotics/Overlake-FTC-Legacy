package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    Telemetry.Item distanceItem;
    Telemetry.Item powerItem;

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

        powerItem = telemetry.addData("power", 0);
        distanceItem = telemetry.addData("distance", 0);

        // Set all drive motors to zero power
        setPower(0);
    }

    public int inchesToTicks(double inches) {
        return (int) Math.round(inches * this.ticksPerInch);
    }

    private static final double ticksPerRotation = 1120;
    private static final double motorGearSize = 32; //TODO: This is a placeholder, use actual value for this
    private static final double wheelGearSize = 16; //TODO: This is a placeholder, use actual value for this
    private static final double gearRatio = wheelGearSize / motorGearSize;
    private static final double wheelDiameterInches = 4.0; //TODO: This is a placeholder, use actual value for this
    private static final double ticksPerInch = (ticksPerRotation * gearRatio) / (wheelDiameterInches * Math.PI);

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

    public void driveGodMode(float rightX, float rightY, float leftX, float leftY) {
        driveGodMode(rightX, rightY, leftX, leftY, 1);
    }

    public void driveGodMode(float rightX, float rightY, float leftX, float leftY, float coeff) {
        double currentHeading = Math.toRadians(imuSystem.getHeading());
        double headingDiff = initialHeading - currentHeading;

        rightX = scaleJoystickValue(rightX);
        leftX = scaleJoystickValue(leftX);
        leftY = scaleJoystickValue(leftY);

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
        distanceItem.setValue(d);
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
        ExponentialRamp ramp = new ExponentialRamp(2.0, 0.1, 130.0, maxPower);

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
            catch (Exception e) {
            }

            heading = this.imuSystem.getHeading();
        }
        this.setPower(0);
    }

    public void tankDrive(double leftPower, double rightPower)
    {
        this.motorFrontLeft.setPower(leftPower);
        this.motorBackLeft.setPower(leftPower);
        this.motorFrontRight.setPower(rightPower);
        this.motorBackRight.setPower(rightPower);
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

        return sign*ramp.value(diff);
    }

    private double computeDegreesDiff(double targetHeading, double heading) {
        double diff = targetHeading - heading;
        //TODO: This needs to be commented. Also, might be able to compute using mod.
        if (Math.abs(diff) > 180)
        {
            diff += (-360 * (diff / Math.abs(diff)));
        }
        return diff;
    }

    float scaleJoystickValue(float joystickValue)
    {
        return joystickValue > 0
                ? ((joystickValue * joystickValue) * SCALE_FACTOR)
                : (-(joystickValue * joystickValue) * SCALE_FACTOR);
    }


    public void adjustPowerBackwordz(Ramp ramp)
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
        Ramp ramp = new ExponentialRamp(revolutionsToTicks(0.01), minPower,
                revolutionsToTicks(1.0), maxPower);

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
        Ramp ramp = new ExponentialRamp(revolutionsToTicks(0.01), minPower,
                revolutionsToTicks(1.0), maxPower);

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

    double scaledPower = -ramp.value(minDistance);

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

