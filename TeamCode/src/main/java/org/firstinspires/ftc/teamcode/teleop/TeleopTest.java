package org.firstinspires.ftc.teamcode.teleop;

/**
 * Created by EvanCoulson on 9/26/17.
 */

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.systems.IMUSystem;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

@TeleOp(name="TeleOpTest", group="TeleOp")
public class TeleopTest extends BaseOpMode {
    private IMUSystem imu;

    public TeleopTest() {
        super("TeleOpTest");
    }

    public void init() {
        this.imu = new IMUSystem(this);
    }

    public void loop() {
        logger.log(this.imu.)
    }

    @Override
    public void initButtons() {

    }
}
