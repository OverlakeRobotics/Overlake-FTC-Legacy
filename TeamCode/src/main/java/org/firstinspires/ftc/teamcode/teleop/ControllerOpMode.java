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
        controller2.rightTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.goToLoadPosition();
            }
        };

        controller2.leftTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.goToReleasePosition();
            }
        };

        controller2.leftTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.setLoadPosition();
            }
        };

        controller2.leftTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.setReleasePosition();
            }
        };

        controller2.dPadRight.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.incrementServo();
            }
        };

        controller2.dPadRight.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.decrementServo();
            }
        };
//
//        //ELEVATOR
//
//        //Goes to zero
//        this.elevatorLoadPosition1 = new Button();
//        this.elevatorLoadPosition1.isPressed =
//                new Func<Boolean>()
//                {
//                    @Override
//                    public Boolean value()
//                    {
//                        return gamepad2.a;
//                    }
//                };
//        this.elevatorLoadPosition1.pressedHandler =
//                new Handler()
//                {
//                    @Override
//                    public void invoke()
//                    {
//                        elevator.runMotorDown();
//                    }
//                };
//
//        //Goes to Block 2
//        this.elevatorUnloadPosition1 = new Button();
//        this.elevatorUnloadPosition1.isPressed =
//                new Func<Boolean>()
//                {
//                    @Override
//                    public Boolean value()
//                    {
//                        return gamepad2.b && !gamepad2.left_bumper ;
//                    }
//                };
//        this.elevatorUnloadPosition1.pressedHandler =
//                new Handler()
//                {
//                    @Override
//                    public void invoke()
//                    {
//                        elevator.goToUnloadBlock2();
//                    }
//                };
//
//        this.elevatorUnloadPosition2 = new Button();
//        this.elevatorUnloadPosition2.isPressed =
//                new Func<Boolean>()
//                {
//                    @Override
//                    public Boolean value()
//                    {
//                        return gamepad2.x && !gamepad2.left_bumper;
//                    }
//                };
//        this.elevatorUnloadPosition2.pressedHandler =
//                new Handler()
//                {
//                    @Override
//                    public void invoke()
//                    {
//                        elevator.goToUnloadBlock3();
//                    }
//                };
//
//        this.elevatorUnloadPosition3 = new Button();
//        this.elevatorUnloadPosition3.isPressed =
//                new Func<Boolean>()
//                {
//                    @Override
//                    public Boolean value()
//                    {
//                        return gamepad2.y;
//                    }
//                };
//        this.elevatorUnloadPosition3.pressedHandler =
//                new Handler()
//                {
//                    @Override
//                    public void invoke()
//                    {
//                        elevator.runMotorUp();
//                    }
//                };
//
//        this.elevatorIncrementUp = new Button();
//        this.elevatorIncrementUp.isPressed =
//                new Func<Boolean>()
//                {
//                    @Override
//                    public Boolean value()
//                    {
//                        return gamepad2.dpad_up;
//                    }
//                };
//        this.elevatorIncrementUp.pressedHandler =
//                new Handler()
//                {
//                    @Override
//                    public void invoke()
//                    {
//                        elevator.incrementUp();
//                    }
//                };
//
//        this.elevatorIncrementDown = new Button();
//        this.elevatorIncrementDown.isPressed =
//                new Func<Boolean>()
//                {
//                    @Override
//                    public Boolean value()
//                    {
//                        return gamepad2.dpad_down;
//                    }
//                };
//        this.elevatorIncrementDown.pressedHandler =
//                new Handler()
//                {
//                    @Override
//                    public void invoke()
//                    {
//                        elevator.incrementDown();
//                    }
//                };
//
//        this.elevatorSetBlock2Pos = new Button();
//        this.elevatorSetBlock2Pos.isPressed =
//                new Func<Boolean>()
//                {
//                    @Override
//                    public Boolean value()
//                    {
//                        return gamepad2.left_bumper && gamepad2.b;
//                    }
//                };
//        this.elevatorSetBlock2Pos.pressedHandler =
//                new Handler()
//                {
//                    @Override
//                    public void invoke()
//                    {
//                        elevator.setPositionBlock2();
//                    }
//                };
//
//        this.elevatorSetBlock3Pos = new Button();
//        this.elevatorSetBlock3Pos.isPressed =
//                new Func<Boolean>()
//                {
//                    @Override
//                    public Boolean value()
//                    {
//                        return gamepad2.left_bumper && gamepad2.x;
//                    }
//                };
//        this.elevatorSetBlock3Pos.pressedHandler =
//                new Handler()
//                {
//                    @Override
//                    public void invoke()
//                    {
//                        elevator.setPositionBlock3();
//                    }
//                };

    }


    @Override
    public void loop() {
//        controller1.handle();
//        controller2.handle();

        this.driveSystem.mecanumDrive(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y);
        telemetry.update();
    }


    @Override
    public void stop() {

    }


}