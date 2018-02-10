package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.controller.TriggerType;
import org.firstinspires.ftc.teamcode.robot.systems.ClawSystemNoMergeConflictPlease;
import org.firstinspires.ftc.teamcode.robot.systems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robot.systems.ParallelLiftSystem;
import org.firstinspires.ftc.teamcode.util.Handler;

/**
 * Created by jacks on 10/5/2017.
 */
@TeleOp(name="ContollerOpMode", group="TeleOp")
public class ControllerOpMode extends BaseOpMode {
    private ClawSystemNoMergeConflictPlease claw;
    private ElevatorSystem elevator;
    private ParallelLiftSystem lifter;

    public ControllerOpMode(){
        super("ControllerOpMode");
    }

    private static boolean slowDrive = false;
    private boolean superDrive;

    Telemetry.Item lastButtonTelemetryItem;

    @Override
    public void init() {
        this.initBaseSystems();
        this.claw = new ClawSystemNoMergeConflictPlease(this);
        elevator = new ElevatorSystem(this);
        lifter = new ParallelLiftSystem(this);
        controller1.setTriggerValue(TriggerType.LEFT, 0.5f);
        this.superDrive = config.getBoolean("superDrive");
        lastButtonTelemetryItem = telemetry.addData("LastButton", "none");
    }

    @Override
    public void loop() {
        controller1.handle();
        controller2.handle();

        elevator.checkForBottom(telemetry);
        elevator.checkForTop();
        claw.loop();
        lifter.checkForBottom();

        float rx = controller1.gamepad.right_stick_x;
        float ry = controller1.gamepad.right_stick_y;
        float lx = controller1.gamepad.left_stick_x;
        float ly = controller1.gamepad.left_stick_y;

            float coefficient = slowDrive == true ? 0.5f : 1f;
            //this.driveSystem.driveGodMode(rx, ry, lx, ly, coefficient);
        this.driveSystem.mecanumDrive(rx, ry, lx, ly, false);

    }


    @Override

    public void stop() {

    }

    @Override
    public void initButtons() {
        //Claw
        /////////////////////////////////////////////////////////////
        // load position claw
        controller2.leftTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.goToBottom();
                lastButtonTelemetryItem.setValue("c2 r trig");
            }
        };

        // release claw
        controller2.rightTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.goToTop();
                lastButtonTelemetryItem.setValue("c2 l trig");
            }
        };

        // save load position
        controller2.rightTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                lastButtonTelemetryItem.setValue("c2 S r trig");
            }

        };

        // save release position
        controller2.leftTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                claw.setReleasePosition();
                lastButtonTelemetryItem.setValue("c2 S l trig");
            }
        };

        // increment servo
        controller2.dPadRight.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {

                lastButtonTelemetryItem.setValue("c2 dpad r");
            }
        };

        // decrement servo
        controller2.dPadLeft.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
//                claw.decrementServo();
                lastButtonTelemetryItem.setValue("c2 dpad l");
            }
        };



        //ELEVATOR
        //////////////////////////////////////////////////////////////////////
        //goes to 0
        controller2.b.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.runMotorDown();
                lastButtonTelemetryItem.setValue("c2 b");
            }
        };

        // goes to Block 2
        controller2.a.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.positionDown();
                lastButtonTelemetryItem.setValue("c2 a");
            }
        };


        // run motor up
        controller2.y.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.positionUp();
                lastButtonTelemetryItem.setValue("c2 y");
            }
        };

        // increment elevator up
        controller2.dPadUp.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.incrementUp();
                lastButtonTelemetryItem.setValue("c2 dpad up");
            }
        };

        // decrements elevator down
        controller2.dPadDown.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.incrementDown();
                lastButtonTelemetryItem.setValue("c2 dpad down");
            }
        };

        // save block 2 position
        controller2.bShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.setPosition();
                lastButtonTelemetryItem.setValue("c2 bShift");
            }
        };

        controller1.rightTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                slowDrive = !slowDrive;
                lastButtonTelemetryItem.setValue("c1 l trig");
            }
        };

        controller1.b.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                driveSystem.resetInitialHeading();
                lastButtonTelemetryItem.setValue("c1 b");
            }
        };

        controller1.rightBumper.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                superDrive = !superDrive;
            }
        };

        controller1.a.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
                elevator.incrementDown();
                lastButtonTelemetryItem.setValue("c1 a");
//                throw new IllegalStateException("Lifter Not Implemented Yet");
            }
        };
        controller1.y.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
                elevator.incrementUp();
                lastButtonTelemetryItem.setValue("c1 y");
//                throw new IllegalStateException("Lifter Not Implemented Yet");
            }
        };


        //Lifter
        ///////////////////////////////////////
        //
        controller2.dPadDown.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
                lifter.positionDown();
                lastButtonTelemetryItem.setValue("c1 dpadDown");

            }
        };

        controller2.dPadUp.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
                lifter.positionUp();
                lastButtonTelemetryItem.setValue("c1 dpadUp");
            }
        };

        controller1.dPadDown.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
                lifter.incrementDown();
                lastButtonTelemetryItem.setValue("c1 dpadDown");
            }
        };

        controller1.dPadUp.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
                lifter.incrementUp();
                lastButtonTelemetryItem.setValue("c1 dpadUp");

            }
        };

        controller1.dPadDownShifted.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
                lifter.setPosition();
                lastButtonTelemetryItem.setValue("c1 dpadDownShift");
            }
        };

        controller1.dPadUpShifted.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
                lifter.setPosition();
                lastButtonTelemetryItem.setValue("c1 dpadDownShift");
            }
        };

        controller2.x.pressedHandler = new Handler() {
            @Override
            public void invoke()
            {
                lifter.runMotorDown();
                lastButtonTelemetryItem.setValue("c1 b");
            }
        };
    }
}