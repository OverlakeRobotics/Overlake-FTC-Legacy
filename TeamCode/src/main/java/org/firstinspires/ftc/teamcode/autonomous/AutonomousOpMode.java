package org.firstinspires.ftc.teamcode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.MecanumDriveSystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.google.gson.annotations.Expose;

import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;
import org.firstinspires.ftc.teamcode.robot.*;
import org.firstinspires.ftc.teamcode.util.ramp.*;

public abstract class AutonomousOpMode extends BaseOpMode
{
    MecanumDriveSystem driveSystem;
    LineFollowingSystem lineFollowingSystem;
    IMUSystem imuSystem;
    Eye eye;
    ElevatorSystem elevator;
    ClawSystem claw;
    //PixyCam pixyCam;

    public AutonomousOpMode() {
        super("AutonomousOpMode");
    }

    void initializeAllDevices()
    {
        this.driveSystem = new MecanumDriveSystem(this);
        this.imuSystem = new IMUSystem(this);
        this.lineFollowingSystem = new LineFollowingSystem();
        this.eye = new Eye();
        this.eye.init(hardwareMap);
        this.elevator = new ElevatorSystem(hardwareMap, telemetry);
        this.claw = new ClawSystem(hardwareMap);
        //this.pixyCam = hardwareMap.get(PixyCam.class, "pixycam");
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
}
