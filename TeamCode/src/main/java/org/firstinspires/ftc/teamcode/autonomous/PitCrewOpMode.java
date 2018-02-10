package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.systems.ParallelLiftSystem;

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

        waitForStart();

        //claw.goToBottom();
        elevator.goToTopSynch(0.4);
        parallelLiftSystem.goToBottomSync();
        telemetry.update();
        sleep(5000);
        parallelLiftSystem.goToIndexSync(ParallelLiftSystem.middleIndex);
        telemetry.update();
        sleep(5000);
        elevator.goToIndexSynch(elevator.MIDDLE_INDEX);
        telemetry.update();
        sleep(3000); // TODO: remove... just for debugging
        parallelLiftSystem.goToPostitionSync(parallelLiftSystem.park);
        claw.goToTop();

        telemetry.addLine("Physical system calibration complete.");
        telemetry.update();
    }
}
