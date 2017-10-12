package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.ramp.*;

import java.util.Map;
import java.util.Set;

public class MecanumDriveSystem extends Component
{

    public Map<String, GearedMotor> motors;

    public final int MOTOR_PULSES = GearChain.NEVEREST40_PULSES;

    /* Constructor */
    public MecanumDriveSystem(HardwareMap hwMap, Telemetry telemetry) {
        super(hwMap, telemetry, "MecanumDrive");

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

        // Set all drive motors to zero power
        setPower(0);
    }


    public void setTargetPositionInches(double inches)
    {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.runOutputInches(inches, 0.8);
        }
    }

    public void setTargetPositionRevs(double revolutions)
    {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.runOutputGear(revolutions, 0.8);
        }
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.setDirection(direction);
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

    public int getMinimumDistanceFromTarget()
    {
        int minDistance = Integer.MAX_VALUE;
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            int difference = motor.getTargetPosition() - motor.getCurrentPosition();
            minDistance = closestToZero(minDistance, difference);
        }

        return minDistance;
    }

    private int closestToZero(int i, int j)
    {
        if (Math.abs(i) < Math.abs(j))
            return i;

        return j;
    }

    public void setPower(double power)
    {
        for (String key : motors.keySet()) {
            GearedMotor motor = motors.get(key);
            motor.setPower(power);
        }
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
        double x = Math.cos(radians)*power;
        double y = Math.sin(radians)*power;

        mecanumDriveXY(x, y);
    }


    float scaleJoystickValue(float joystickValue)
    {
        return joystickValue > 0
                ? (float)((joystickValue*joystickValue)*.62)
                : (float)(-(joystickValue*joystickValue)*.62);
    }
}

