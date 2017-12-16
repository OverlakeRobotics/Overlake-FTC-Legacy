package org.firstinspires.ftc.teamcode.autonomous;


import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.google.gson.annotations.Expose;

import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;
import org.firstinspires.ftc.teamcode.robot.*;
import org.firstinspires.ftc.teamcode.util.ramp.*;
import java.io.*;
import com.google.gson.*;
import com.google.gson.annotations.*;

public abstract class AutonomousOpMode extends LinearOpMode
{
    MecanumDriveSystem driveSystem;
    //LineFollowingSystem lineFollowingSystem;
    IMUSystem imuSystem;
    Eye eye;
    ElevatorSystem elevator;
    ClawSystem claw;


    void initializeAllDevices()
    {
        this.driveSystem = new MecanumDriveSystem();
        this.driveSystem.init(this.hardwareMap);
        /*
        telemetry.addLine("drive system done");
        telemetry.update();
        sleep(1000);
        */
        this.imuSystem = new IMUSystem();
        this.imuSystem.init(this.hardwareMap);
        /*
        telemetry.addLine("imu system done");
        telemetry.update();
        sleep(1000);
        */
        //this.lineFollowingSystem = new LineFollowingSystem();
        this.eye = new Eye();
        this.eye.init(hardwareMap);
        /*
        telemetry.addLine("eye system done");
        telemetry.update();
        sleep(1000);
        */
        this.elevator = new ElevatorSystem(hardwareMap, telemetry);
        /*
        telemetry.addLine("elevator system done");
        telemetry.update();
        sleep(1000);
        */
        this.claw = new ClawSystem(hardwareMap);
        /*
        telemetry.addLine("claw system done");
        telemetry.update();
        sleep(1000);
        */

//        this.lineFollowingSystem.init(this.hardwareMap);
    }

    //colorSide tells if the color of the line we are following is on the left or right of the sensor
    /*public void followColor(HueData hue, boolean followRightEdge)
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
        driveSystem.tweakTankDrive(increment);
    }*/

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

        this.driveSystem.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

            this.driveSystem.tankDrive(power, -power);

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
        double minPower = 0.1;

        this.driveSystem.setTargetPositionRevs(revolutions);

        /*
            Create a Ramp that will map a distance in revolutions between 0.01 and 1.0
            onto power values between minPower and maxPower.
            When the robot is greater than 1.0 revolution from the target the power
            will be set to maxPower, but when it gets within 1.0 revolutions, the power
            will be ramped down to minPower
        */
        Ramp ramp = new ExponentialRamp(driveSystem.revolutionsToTicks(0.01), minPower,
                                        driveSystem.revolutionsToTicks(1.0), maxPower);

        // Wait until they are done
        driveSystem.setPower(maxPower);
        while (this.driveSystem.anyMotorsBusy())
        {
            telemetry.update();

            this.idle();

            this.driveSystem.adjustPower(ramp);
        }

        // Now that we've arrived, kill the motors so they don't just sit there buzzing
        driveSystem.setPower(0);

        // Always leave the screen looking pretty
        telemetry.update();
    }

    void driveToPositionInches(double inches, double maxPower)
    {
        double minPower = 0.1;

        this.driveSystem.setTargetPositionInches(inches);

        /*
            Create a Ramp that will map a distance in revolutions between 0.01 and 1.0
            onto power values between minPower and maxPower.
            When the robot is greater than 1.0 revolution from the target the power
            will be set to maxPower, but when it gets within 1.0 revolutions, the power
            will be ramped down to minPower
        */
        Ramp ramp = new ExponentialRamp(driveSystem.revolutionsToTicks(0.01), minPower,
                driveSystem.revolutionsToTicks(1.0), maxPower);

        // Wait until they are done
        driveSystem.setPower(maxPower);
        while (this.driveSystem.anyMotorsBusy())
        {
            telemetry.update();

            this.idle();

            this.driveSystem.adjustPower(ramp);
        }

        // Now that we've arrived, kill the motors so they don't just sit there buzzing
        driveSystem.setPower(0);

        // Always leave the screen looking pretty
        telemetry.update();
    }


    public void park() {
        try {
            driveToPositionRevs(0,0);
        } catch (Exception e) {

        }
    }
}
