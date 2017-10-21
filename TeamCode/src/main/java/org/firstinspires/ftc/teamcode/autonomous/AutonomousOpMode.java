package org.firstinspires.ftc.teamcode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.MecanumDriveSystem;
import org.firstinspires.ftc.teamcode.robot.*;
import org.firstinspires.ftc.teamcode.util.ramp.*;

public abstract class AutonomousOpMode extends LinearOpMode
{
    MecanumDriveSystem driveSystem;
    LineFollowingSystem lineFollowingSystem;
    IMUSystem imuSystem;
    ColorSensorData colorSensorData;


    void initializeAllDevices()
    {
        this.driveSystem = new MecanumDriveSystem(hardwareMap, telemetry);
        this.imuSystem = new IMUSystem(this.hardwareMap, telemetry);
        this.lineFollowingSystem = new LineFollowingSystem();
    }

    //colorSide tells if the color of the line we are following is on the left or right of the sensor
    public void followColor(HueData hue, boolean followRightEdge)
    {
        double increment = .05;

        if (this.lineFollowingSystem.isLineColorSensorOverHue(hue))
        {
            if (followRightEdge) //if color side is left, veer right
            {
                increment = increment;
            }
            else //if color side is right, veer left
            {
                increment = -increment;
            }
        }
        else
        {
            //the opposite of above, so the robot turns towards the colored line
            if (followRightEdge) //if color side is left, veer left to find line
            {
                increment = -increment;
            }
            else //if color side is right, veer right to find line
            {
                increment = increment;
            }
        }


        // positive increment forces it to drive a little to the right,
        // negative increment drives it a little to the left.
//        driveSystem.tweakTankDrive(increment);
    }

    void turn(double degrees, double maxPower)
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

//        this.driveSystem.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Between 130 and 2 degrees away from the target
        // we want to slow down from maxPower to 0.1
        ExponentialRamp ramp = new ExponentialRamp(2.0, 0.1, 130.0, maxPower);

        while (!isStopRequested() && Math.abs(computeDegrees(targetHeading, heading)) > 1)
        {
            telemetry.update();
            double power = getTurnPower(ramp, targetHeading, heading);
            telemetry.addLine("heading: " + heading);
            telemetry.addLine("target heading: " + targetHeading);
            telemetry.addLine("power: " + power);

//            this.driveSystem.tankDrive(power, -power);

            try
            {
                sleep(50);
            }
            catch (Exception e)
            {
            }

            heading = this.imuSystem.getHeading();
        }

        this.driveSystem.setPower(0);
    }

    private double computeDegrees(double targetHeading, double heading)
    {
        double diff = targetHeading - heading;
        //TODO: This needs to be commented. Also, might be able to compute using mod.
        if (Math.abs(diff) > 180)
        {
            diff += (-360 * (diff / Math.abs(diff)));
        }

        return diff;
    }

    private double getTurnPower(Ramp ramp, double targetHeading, double heading)
    {
        double sign = 1.0;
        double diff = computeDegrees(targetHeading, heading);
        if (diff < 0)
        {
            sign = -1.0;
            diff = -diff;
        }

        return sign*ramp.value(diff);
    }

    void driveToPositionRevs(double revolutions, double maxPower)
    {
        driveSystem.driveRevs(revolutions, maxPower);
    }
}
