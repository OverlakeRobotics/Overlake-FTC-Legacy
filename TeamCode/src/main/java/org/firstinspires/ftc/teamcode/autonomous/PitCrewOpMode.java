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

        //claw.goToBottom();
        elevator.goToTopSynch();
        parallelLiftSystem.goToBottomSync();
        parallelLiftSystem.goToPostitionSync(parallelLiftSystem.positions[0]);
        elevator.goToBottomSynch();
        elevator.goToPosition(elevator.bottomLifterDown);
        parallelLiftSystem.goToPostitionSync(parallelLiftSystem.park);
        claw.goToTop();

        telemetry.addLine("Physical system calibration complete.");
        telemetry.update();
    }
}
