package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.ramp.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MecanumDriveSystem extends System
{ 
    // 2) remove motor map, too unreadable and makes work to verbose
    private final float SCALE_FACTOR = 0.62f;

    public IMUSystem imuSystem;
    public Map<String, GearedMotor> motors;
    public final int MOTOR_PULSES = GearChain.NEVEREST40_PULSES;

    public GearedMotor motorFrontLeft;
    public GearedMotor motorFrontRight;
    public GearedMotor motorBackLeft;
    public GearedMotor motorBackRight;

    private double startingIMUHeading;

    /* Constructor */
    public MecanumDriveSystem(HardwareMap hwMap, Telemetry telemetry) {
        super(hwMap, telemetry, "MecanumDrive");

        imuSystem = new IMUSystem(hwMap, telemetry);
        motors = new HashMap<String, GearedMotor>();

        startingIMUHeading = imuSystem.getHeading();
        Set<String> motorConfigKeys = config.getKeysContaining("motor");

        for (String motorKey : motorConfigKeys) {
            String motorName = config.getString(motorKey);
            DcMotor motor = map.dcMotor.get(motorName);
            GearedMotor gearedMotor = new GearedMotor(MOTOR_PULSES, motor, 32, 16);
            motors.put(motorName, gearedMotor);
        }

        this.motors.get(config.getString("motorFL")).setDirection(DcMotor.Direction.REVERSE);
        this.motors.get(config.getString("motorBL")).setDirection(DcMotor.Direction.REVERSE);
        this.motors.get(config.getString("motorFR")).setDirection(DcMotor.Direction.FORWARD);
        this.motors.get(config.getString("motorBR")).setDirection(DcMotor.Direction.FORWARD);

        // Set PID coeffiecents
        setAllMotorsPID(config.getDouble("P"), config.getDouble("I"), config.getDouble("D"));
        // Set all drive motors to zero power
        setPower(0);
    }

    public void driveTicks(int ticks, double power) {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.runInputGearTicks(ticks, power);
        }
    }

    public void driveRevs(double revolutions, double power)
    {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.runOutputGearRevolutions(revolutions, power);
        }
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.setDirection(direction);
        }
    }

    public void setAllMotorsPID(double P, double I, double D) {
        for (String key : motors.keySet()) {
            setPIDCoefficients(key, P, I, D);
        }
    }

    public boolean anyMotorsBusy()
    {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            if (!motor.isBusy()) {
                return false;
            }
        }
        return true;
    }

    public void setPower(double power)
    {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.setPower(power);
        }
    }

    public void setRunMode(DcMotor.RunMode runMode) {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.setRunMode(runMode);
        }
    }

    private void setSectionPower(String section, double power) {
        for (String key : motors.keySet()) {
            if (key.toLowerCase().contains(section)) {
                GearedMotor motor = motors.get(key);
                motor.setPower(power);
            }
        }
    }

    public void setRightPower(double power) {
        setSectionPower("right", power);
    }

    public void setLeftPower(double power) {
        setSectionPower("left", power);
    }

    public void setForwardPower(double power) {
        setSectionPower("forward", power);
    }

    public void setBackPower(double power) {
        setSectionPower("back", power);
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
        motors.get(config.getString("motorFR")).setPower(Range.clip(frontRightPower, -1, 1));
        motors.get(config.getString("motorBR")).setPower(Range.clip(backRightPower, -1, 1));
        motors.get(config.getString("motorFL")).setPower(Range.clip(frontLeftPower - leftX, -1, 1));
        motors.get(config.getString("motorBL")).setPower(Range.clip(backLeftPower + leftX, -1, 1));
    }

    public void mecanumDriveXY(double x, double y)
    {
        motors.get(config.getString("motorFR")).setPower(Range.clip(y + x, -1, 1));
        motors.get(config.getString("motorBR")).setPower(Range.clip(y - x, -1, 1));
        motors.get(config.getString("motorFL")).setPower(Range.clip(y - x, -1, 1));
        motors.get(config.getString("motorBL")).setPower(Range.clip(y + x, -1, 1));
    }

    public void mecanumDrivePolar(double radians, double power)
    {
        double x = Math.cos(radians) * power;
        double y = Math.sin(radians) * power;
        mecanumDriveXY(x, y);
    }

    public void driveInchesPolar(double inches, double direction, double maxPower, double deltaInches) {
        turn(direction, maxPower);
        GearedMotor.runMotorsRampedInches(
                inches, deltaInches, maxPower, motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft
        );
    }

    public void driveInchesXY(double x, double y, double maxPower, double deltaInches) {
        double inches = Math.sqrt(x * x + y * y);
        double direction = Math.atan2(y,x);
        driveInchesPolar(inches, direction, maxPower, deltaInches);
    }

    public void driveInchesPolar(double inches, double direction, double power) {
        turn(direction, power);
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.runOutputWheelInches(inches, power);
        }
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

