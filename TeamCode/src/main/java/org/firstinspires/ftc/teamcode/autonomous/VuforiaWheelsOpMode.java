package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by Xiao on 1/11/2017.
 */

@Autonomous(name = "VuforiaWheelsOpMode", group = "Bot")
@Disabled
public class VuforiaWheelsOpMode extends VuforiaBaseOpMode
{
    public void runOpMode()
    {
        initialize();
        waitForStart();
        driveToTarget(Target.Wheels, 0.2, 0.75);
    }
}
