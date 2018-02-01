package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.controller.TriggerType;
import org.firstinspires.ftc.teamcode.robot.systems.ClawSystem;
import org.firstinspires.ftc.teamcode.robot.systems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robot.systems.ParallelLiftSystem;
import org.firstinspires.ftc.teamcode.util.Handler;
import org.firstinspires.ftc.teamcode.util.logger.Logger;
import org.firstinspires.ftc.teamcode.util.logger.LoggingService;

/**
 * Created by jacks on 10/5/2017.
 */
@TeleOp(name="ContollerOpMode", group="TeleOp")
public class ControllerOpMode extends BaseOpMode {
    private ClawSystem claw;
    private ElevatorSystem elevator;
    private ParallelLiftSystem lifter;

    public ControllerOpMode(){
        super("ControllerOpMode");
    }

    private static boolean slowDrive = false;

    @Override
    public void init() {
        this.initBaseSystems();
        claw = new ClawSystem(this);
        elevator = new ElevatorSystem(this);

        controller1.setTriggerValue(TriggerType.LEFT, 0.5f);
    }

    @Override
    public void loop() {
        controller1.handle();
        controller2.handle();

        float rx = controller1.gamepad.right_stick_x;
        float ry = controller1.gamepad.right_stick_y;
        float lx = controller1.gamepad.left_stick_x;
        float ly = controller1.gamepad.left_stick_y;

        if (config.getBoolean("superDrive")) {
            float coefficient = slowDrive == true ? 0.5f : 1f;
            this.driveSystem.driveGodMode(rx, ry, lx, ly, coefficient);
        } else  {
            this.driveSystem.mecanumDrive(rx, ry, lx, ly, slowDrive);
        }

        telemetry.update();
    }


    @Override
    public void stop() {

    }

    @Override
    public void initButtons() {
        //Claw

        // load position claw
        controller2.rightTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                claw.goToLoadPosition();
                telemetry.addData("c2 press", "r trig");
            }
        };

        // release claw
        controller2.leftTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                claw.goToReleasePosition();
                telemetry.addData("c2 press", "l trig");
            }
        };

        // save load position
        controller2.rightTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                claw.setLoadPosition();
                telemetry.addData("c2 press", "S r trig");
            }

        };

        // save release position
        controller2.leftTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                claw.setReleasePosition();
                telemetry.addData("c2 press", "S l trig");
            }
        };

        // increment servo
        controller2.dPadRight.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                claw.incrementServo();
                telemetry.addData("c2 press", "dpad r");
            }
        };

        // decrement servo
        controller2.dPadLeft.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                claw.decrementServo();
                telemetry.addData("c2 press", "dpad l");
            }
        };

        //ELEVATOR

        //goes to 0
        controller2.a.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                elevator.runMotorDown();
                telemetry.addData("c2 press", "a");
            }
        };

        // goes to Block 2
        controller2.b.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                elevator.goToUnloadBlock2();
                telemetry.addData("c2 press", "b");
            }
        };

        // goes to Block 3
        controller2.x.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                elevator.goToUnloadBlock3();
                telemetry.addData("c2 press", "x");
            }
        };

        // run motor up
        controller2.y.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                elevator.runMotorUp();
                telemetry.addData("c2 press", "y");
            }
        };

        // increment elevator up
        controller2.dPadUp.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                elevator.incrementUp();
                telemetry.addData("c2 press", "dpad up");
            }
        };

        // decrements elevator down
        controller2.dPadDown.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                elevator.incrementDown();
                telemetry.addData("c2 press", "dpad down");
            }
        };

        // save block 2 position
        controller2.bShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                elevator.setPositionBlock2();
                telemetry.addData("c2 press", "b");
            }
        };

        // save block 3 position
        controller2.xShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                elevator.setPositionBlock3();
                telemetry.addData("c2 press", "S x");
            }
        };

        controller1.leftTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                slowDrive = !slowDrive;
                telemetry.addData("c1 press", "l trig");
            }
        };

        controller1.x.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                driveSystem.resetInitialHeading();
                telemetry.addData("c1 press", "x");
            }
        };

        controller1.a.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
//                lifter.goToBottom();
                telemetry.addData("c1 press", "a");
//                throw new IllegalStateException("Lifter Not Implemented Yet");
            }
        };
    }
}