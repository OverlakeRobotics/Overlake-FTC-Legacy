package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Steven Abbott on 1/29/2018.
 */

@Autonomous(name="PitCrewOpMode", group="Bot")
public class PitCrewOpMode extends BaseOpMode {

    public static final int TIME = 1000;

    public PitCrewOpMode() {
        super("PitCrew");
    }

    @Override
    public void runOpMode() {
        this.initSystems();

        elevator.runMotorUp();                  // calibrate motor on top limit switch
        sleep(TIME);
        parallelLiftSystem.runMotorDown(-0.1);                     // put lifter arm down slowly (hence the -0.1) to calibrate it
        sleep(TIME);
        parallelLiftSystem.goToMiddle();                           // Move the lifter to the middle so we don't hit the bottom plate moving into park position
        sleep(TIME);
        elevator.runMotorDown();                                    // double check/ calibrate the elevator on the bottom limit switch
        sleep(TIME);
        elevator.goToBottomLifterDown();                            // Move the elevator to its bottom with the lifter arms down
        sleep(TIME);
        claw.goToBottom();                                       //
        sleep(TIME);
        claw.goToTop();                                          // Make sure the claw is working and the three positions are
        sleep(TIME);
        claw.goToMiddle();                                       // properly set... also it looks cool
        sleep(TIME);
        claw.goToTop();                                          //
        sleep(TIME);
        parallelLiftSystem.goToPark();                             // move the lifter down beyond its normal operating limits to the park position to fit in 18inches

        telemetry.addLine("Physical system calibration complete.");
        telemetry.update();
    }
}
