package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.systems.physical.ParallelLiftSystem;

/**
 * Created by Steven Abbott on 1/29/2018.
 */

@Autonomous(name = "PitCrewOpMode", group = "Bot")
public class PitCrewOpMode extends BaseOpMode
{

    public static final int TIME = 1000;

    public PitCrewOpMode()
    {
        super("PitCrew");
    }

    @Override
    public void runOpMode()
    {
        this.initSystems();

        waitForStart();

        //claw.goToBottom();
        elevator.goToTopSynch(0.4);
        parallelLiftSystem.goToBottomSync();
        parallelLiftSystem.goToIndexSync(ParallelLiftSystem.middleIndex);
        elevator.goToPostionSynch(elevator.autoInit);
        sleep(3000);
        parallelLiftSystem.goToPostitionSync(parallelLiftSystem.park);
        claw.goToTop();
    }
}
