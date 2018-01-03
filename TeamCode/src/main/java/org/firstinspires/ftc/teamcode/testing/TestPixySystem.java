package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.robot.PixySystem;

/**
 * Created by lexis on 05-Nov-17.
 */
@Disabled
@Autonomous(name="TextPixySystem", group="Bot")
public class TestPixySystem extends LinearOpMode {
    //GOOD ONE
    private PixySystem pixySystem;
    @Override
    public void runOpMode() {
        //send in "this" and if the team color is blue (true) or red (false)
        pixySystem = new PixySystem(this, false);
        waitForStart();
        pixySystem.initPixyStuff();
        pixySystem.doServoStuff();
    }
}