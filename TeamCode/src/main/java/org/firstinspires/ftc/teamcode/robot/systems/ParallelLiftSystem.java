package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.DcMotorServo;

/**
 * Created by jacks on 11/28/2017.
 */



public class ParallelLiftSystem extends System {
    private DcMotorServo motor;
    private double bottom;
    private double middle;
    private double top;


    public ParallelLiftSystem(OpMode opMode) {
        super(opMode, "ParallelLiftSystem");
        this.motor = new DcMotorServo(this.map, "lifter", "potentiometer");
        bottom = config.getDouble("bottom");
        middle = config.getDouble("middle");
        top  = config.getDouble("top");
    }

    public void loop() {
        motor.loop();
    }

    public void goToBottom() {
        telemetry.addData("current position: ", motor.getCurrentPosition());
        //motor.targetPosition = 0.0;
    }

    public void goToMiddle() {
        telemetry.addData("current position: ", motor.getCurrentPosition());
        //motor.targetPosition = 0.5;
    }
    public void goToTop() {
        telemetry.addData("current position: ", motor.getCurrentPosition());
        //motor.targetPosition = 1.0;
    }
}
