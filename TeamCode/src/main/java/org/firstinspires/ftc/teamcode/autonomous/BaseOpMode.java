package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.ClawSystem;
import org.firstinspires.ftc.teamcode.robot.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robot.Eye;
import org.firstinspires.ftc.teamcode.robot.IMUSystem;
import org.firstinspires.ftc.teamcode.robot.LineFollowingSystem;
import org.firstinspires.ftc.teamcode.robot.MecanumDriveSystem;
import org.firstinspires.ftc.teamcode.robot.PixySystem;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by EvanCoulson on 10/11/17.
 */

public abstract class BaseOpMode extends LinearOpMode {
    public ConfigParser config;
    public MecanumDriveSystem driveSystem;
    public IMUSystem imuSystem;
    public Eye eye;
    public  ElevatorSystem elevator;
    public ClawSystem claw;
    public PixySystem pixySystem;

    public BaseOpMode(String opModeName) {
        config = new ConfigParser(opModeName + ".omc");
        this.driveSystem = new MecanumDriveSystem(this);
        this.imuSystem = new IMUSystem(this);
        this.eye = new Eye();
        this.eye.init(hardwareMap);
        this.elevator = new ElevatorSystem(hardwareMap, telemetry);
        this.claw = new ClawSystem(hardwareMap);
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
