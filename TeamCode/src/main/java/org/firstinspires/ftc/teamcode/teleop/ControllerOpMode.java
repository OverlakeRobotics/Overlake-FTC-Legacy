package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.*;
import org.firstinspires.ftc.teamcode.util.Handler;

/**
 * Created by jacks on 10/5/2017.
 */
@TeleOp(name="ContollerOpMode", group="TeleOp")
public class ControllerOpMode extends BaseOpMode {
    private ClawSystem claw;
    private ElevatorSystem elevator;

    public ControllerOpMode(){
        super("ControllerOpMode");
    }

    @Override
    public void init(){
        claw = new ClawSystem(this.hardwareMap);
        elevator = new ElevatorSystem(this.hardwareMap, telemetry);

    //Claw

        // load position claw
        controller2.rightTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.goToLoadPosition();
            }
        };

        // release claw
        controller2.leftTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.goToReleasePosition();
            }
        };

        // save load position
        controller2.leftTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.setLoadPosition();
            }
        };

        // save release position
        controller2.leftTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.setReleasePosition();
            }
        };

        // increment servo
        controller2.dPadRight.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.incrementServo();
            }
        };

        // decrement servo
        controller2.dPadRight.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.decrementServo();
            }
        };

    //ELEVATOR

        //goes to 0
        controller2.a.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.runMotorDown();
            }
        };

        // goes to Block 2
        controller2.b.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.goToUnloadBlock2();
            }
        };

        // goes to Block 3
        controller2.x.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.goToUnloadBlock3();
            }
        };

        // run motor up
        controller2.y.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.runMotorUp();
            }
        };

        // increment elevator up
        controller2.dPadUp.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.incrementUp();
            }
        };

        // decrements elevator down
        controller2.dPadDown.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.incrementDown();
            }
        };

        // save block 2 position
        controller2.bShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.setPositionBlock2();
            }
        };

        // save block 3 position
        controller2.xShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.setPositionBlock3();
            }
        };
    }


    @Override
    public void loop() {
        controller1.handle();
        controller2.handle();

        float rx = controller1.gamepad.right_stick_x;
        float ry = controller1.gamepad.right_stick_y;
        float lx = controller1.gamepad.left_stick_x;
        float ly = controller1.gamepad.left_stick_y;

        this.driveSystem.mecanumDrive(rx, ry, lx, ly);
        telemetry.update();
    }


    @Override
    public void stop() {

    }


}