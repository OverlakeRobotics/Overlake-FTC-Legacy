package org.firstinspires.ftc.teamcode.autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/*
 * Created by EvanCoulson on 10/26/16.
 *
 * _______   __       __  __   ______       ______   ______   ___    __   _________  ______   ______        ______   ________   ______    ___   ___
 /_______/\ /_/\     /_/\/_/\ /_____/\     /_____/\ /_____/\ /__/\ /__/\ /________/\/_____/\ /_____/\      /_____/\ /_______/\ /_____/\  /___/\/__/\
 \::: _  \ \\:\ \    \:\ \:\ \\::::_\/_    \:::__\/ \::::_\/_\::\_\\  \ \\__.::.__\/\::::_\/_\:::_ \ \     \:::_ \ \\::: _  \ \\:::_ \ \ \::.\ \\ \ \
  \::(_)  \/_\:\ \    \:\ \:\ \\:\/___/\    \:\ \  __\:\/___/\\:. `-\  \ \  \::\ \   \:\/___/\\:(_) ) )_    \:(_) \ \\::(_)  \ \\:(_) ) )_\:: \/_) \ \
   \::  _  \ \\:\ \____\:\ \:\ \\::___\/_    \:\ \/_/\\::___\/_\:. _    \ \  \::\ \   \::___\/_\: __ `\ \    \: ___\/ \:: __  \ \\: __ `\ \\:. __  ( (
    \::(_)  \ \\:\/___/\\:\_\:\ \\:\____/\    \:\_\ \ \\:\____/\\. \`-\  \ \  \::\ \   \:\____/\\ \ `\ \ \    \ \ \    \:.\ \  \ \\ \ `\ \ \\: \ )  \ \
     \_______\/ \_____\/ \_____\/ \_____\/     \_____\/ \_____\/ \__\/ \__\/   \__\/    \_____\/ \_\/ \_\/     \_\/     \__\/\__\/ \_\/ \_\/ \__\/\__\/

 */
@Autonomous(name="AutonomousMode", group="Bot")
public class BlueCenterPark extends AutonomousOpMode {

    private final double DRIVE_POWER = 0.7;

    @Override
    public void runOpMode() {
        initializeAllDevices();
        waitForStart();
        driveToPositionRevs(-1.75, DRIVE_POWER);
        shoot();
        load();
        shoot();
        driveToPositionRevs(-1.5, DRIVE_POWER);
    }
}
