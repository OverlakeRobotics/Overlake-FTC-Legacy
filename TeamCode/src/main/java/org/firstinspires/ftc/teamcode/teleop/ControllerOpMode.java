package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.*;
import org.firstinspires.ftc.teamcode.util.Handler;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by jacks on 10/5/2017.
 */
@TeleOp(name="ContollerOpMode", group="TeleOp")
public class ControllerOpMode extends OpMode {
    private ClawSystem claw;
    private ElevatorSystem elevator;
    private ParallelLiftSystem lifter;
    private ConfigParser config;
    private Claw2png meMotor;


    private Button clawLoadPosition;
    private Button clawReleasePosition;
    private Button clawPinchPosition;
    private Button parallelBottom;
    private Button parallelMiddle;
    private Button parallelTop;

    private Button clawBottom;
    private Button clawMiddle;
    private Button clawTop;
    private Button runMotor;
    private Button runMotorBack;


    private Button clawIncrement;
    private Button clawDecrement;

    private Button clawSetLoadPosition;
    private Button clawSetReleasePosition;
    private Button clawSetPinchPosition;

    private Button elevatorLoadPosition1;
    private Button elevatorUnloadPosition1;
    private Button elevatorUnloadPosition2;
    private Button elevatorUnloadPosition3;
    private Button elevatorIncrementUp;
    private Button elevatorIncrementDown;
    private Button elevatorSetBlock2Pos;
    private Button elevatorSetBlock3Pos;

    private Button checkSlow;

    private Button resetHedingButton;

    private Button checkPotentiometerPos;
    MecanumDriveSystem driveSystem;
    boolean slowDrive = false;

    public ControllerOpMode(){

    }

    @Override
    public void init() {
        this.config = new ConfigParser("TeleOpMecanum.omc");
        claw = new ClawSystem(this.hardwareMap);
        elevator = new ElevatorSystem(this.hardwareMap, telemetry);
        meMotor = new Claw2png(this, telemetry);
        lifter = new ParallelLiftSystem(this.hardwareMap, telemetry);
        this.driveSystem = new MecanumDriveSystem();
        this.driveSystem.init(this.hardwareMap);


        //Claw
        this.clawLoadPosition = new Button();
        this.clawLoadPosition.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.right_trigger>0.75 && !gamepad2.left_bumper;
                    }
                };
        this.clawLoadPosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.goToLoadPosition();
                    }
                };

        this.clawReleasePosition = new Button();
        this.clawReleasePosition.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.left_trigger>0.75 && !gamepad2.left_bumper;

                    }
                };
        this.clawReleasePosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.goToReleasePosition();
                    }
                };

        this.clawPinchPosition = new Button();
        this.clawPinchPosition.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.right_bumper;
                    }
                };
        this.clawPinchPosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.goToPinchPosition();
                    }
                };




        this.clawSetLoadPosition = new Button();
        this.clawSetLoadPosition.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.left_bumper && gamepad2.right_trigger > 0.5;
                    }
                };
        this.clawSetLoadPosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.setLoadPosition();
                    }
                };

        this.clawSetReleasePosition = new Button();
        this.clawSetReleasePosition.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.left_bumper && gamepad2.left_trigger > 0.5;
                    }
                };
        this.clawSetReleasePosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.setReleasePosition();
                    }
                };

        this.clawSetPinchPosition = new Button();
        this.clawSetPinchPosition.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.left_bumper && gamepad2.right_bumper;
                    }
                };
        this.clawSetReleasePosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.setPinchPosition();
                    }
                };



        this.clawIncrement = new Button();
        this.clawIncrement.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_left;
                    }
                };
        this.clawIncrement.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.incrementServo();
                    }
                };

        this.clawDecrement = new Button();
        this.clawDecrement.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_right;
                    }
                };
        this.clawDecrement.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.decrementServo();
                    }
                };

        this.clawBottom = new Button();
        this.clawBottom.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.a;
                    }
                };
        this.clawBottom.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        meMotor.goToBottom();
                    }
                };

        this.clawMiddle = new Button();
        this.clawMiddle.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.x;

                    }
                };
        this.clawMiddle.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        meMotor.goToMiddle();
                    }
                };

        this.clawTop = new Button();
        this.clawTop.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.y;
                    }
                };
        this.clawTop.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        meMotor.goToTop();
                    }
                };

        this.runMotor = new Button();
        this.runMotor.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.left_bumper;
                    }
                };
        this.runMotor.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        meMotor.runMotor();
                    }
                };
        this.runMotor.releasedHandler = new Handler() {
            @Override
            public void invoke() {
                meMotor.stop();
            }
        };

        this.runMotorBack = new Button();
        this.runMotorBack.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.right_bumper;
                    }
                };
        this.runMotorBack.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        meMotor.runMotorBack();
                    }
                };
        this.runMotorBack.releasedHandler = new Handler() {
            @Override
            public void invoke() {
                meMotor.stop();
            }
        };

        this.parallelBottom = new Button();
        this.parallelBottom.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.dpad_down;
                    }
                };
        this.parallelBottom.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.runMotorDown();
                    }
                };

        this.parallelMiddle = new Button();
        this.parallelMiddle.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.dpad_right;
                    }
                };
        this.parallelMiddle.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.goToMiddle();
                    }
                };
        this.parallelTop = new Button();
        this.parallelTop.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.dpad_up;
                    }
                };
        this.parallelTop.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.goToTop();
                    }
                };

        //ELEVATOR

        //Goes to zero
        this.elevatorLoadPosition1 = new Button();
        this.elevatorLoadPosition1.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.a;
                    }
                };
        this.elevatorLoadPosition1.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.runMotorDown();
                    }
                };

        //Goes to Block 2
        this.elevatorUnloadPosition1 = new Button();
        this.elevatorUnloadPosition1.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.b && !gamepad2.left_bumper ;
                    }
                };
        this.elevatorUnloadPosition1.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.goToUnloadBlock2();
                    }
                };

        this.elevatorUnloadPosition2 = new Button();
        this.elevatorUnloadPosition2.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.x && !gamepad2.left_bumper;
                    }
                };
        this.elevatorUnloadPosition2.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.goToUnloadBlock3();
                    }
                };

        this.elevatorUnloadPosition3 = new Button();
        this.elevatorUnloadPosition3.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.y;
                    }
                };
        this.elevatorUnloadPosition3.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.runMotorUp();
                    }
                };

        this.elevatorIncrementUp = new Button();
        this.elevatorIncrementUp.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_up;
                    }
                };
        this.elevatorIncrementUp.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.incrementUp();
                    }
                };

        this.elevatorIncrementDown = new Button();
        this.elevatorIncrementDown.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_down;
                    }
                };
        this.elevatorIncrementDown.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.incrementDown();
                    }
                };

        this.elevatorSetBlock2Pos = new Button();
        this.elevatorSetBlock2Pos.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.left_bumper && gamepad2.b;
                    }
                };
        this.elevatorSetBlock2Pos.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.setPositionBlock2();
                    }
                };

        this.elevatorSetBlock3Pos = new Button();
        this.elevatorSetBlock3Pos.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.left_bumper && gamepad2.x;
                    }
                };
        this.elevatorSetBlock3Pos.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.setPositionBlock3();
                    }
                };


        this.checkSlow = new Button();
        this.checkSlow.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.left_trigger > 0.50;
                    }
                };
        this.checkSlow.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        slowDrive = true;
                    }
                };

        this.checkSlow.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        slowDrive = false;
                    }
                };

        this.resetHedingButton = new Button();
        this.resetHedingButton.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad1.b;
            }
        };
        this.resetHedingButton.pressedHandler = new Handler() {
            @Override
            public void invoke() {
//                driveSystem.resetInitialHeading();
            }
        };
        this.resetHedingButton.releasedHandler = new Handler() {
            @Override
            public void invoke() {
                driveSystem.resetInitialHeading();
            }
        };


        /*this.checkPotentiometerPos = new Button();
        this.checkPotentiometerPos.isPressed=
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.a;
                    }
                };
        this.checkPotentiometerPos.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.goToBottom();
                    }
                }; */

    }
    ElapsedTime i = new ElapsedTime();

    public void logTime(String str) {
        Long time = i.nanoseconds();
        RobotLog.ee("Time " + str, time.toString());
    }

    @Override
    public void loop() {
        logTime("Loop Start");
        clawLoadPosition.testAndHandle();
        clawReleasePosition.testAndHandle();
        clawPinchPosition.testAndHandle();
        clawSetReleasePosition.testAndHandle();
        clawSetLoadPosition.testAndHandle();
        clawSetPinchPosition.testAndHandle();
        clawDecrement.testAndHandle();
        clawIncrement.testAndHandle();

        clawBottom.testAndHandle();
        clawMiddle.testAndHandle();
        clawTop.testAndHandle();
        runMotor.testAndHandle();
        runMotorBack.testAndHandle();
        logTime("meMotor Start");
        meMotor.loop();
        logTime("meMotor End");

        //checkPotentiometerPos.testAndHandle();

        parallelBottom.testAndHandle();
        parallelMiddle.testAndHandle();
        parallelTop.testAndHandle();
        lifter.checkForBottom();
        lifter.isPressed();
        elevator.checkForBottom(telemetry);
        elevator.checkForTop();
        elevatorLoadPosition1.testAndHandle();
        elevatorUnloadPosition1.testAndHandle();
        elevatorUnloadPosition2.testAndHandle();
        elevatorUnloadPosition3.testAndHandle();
        elevatorIncrementDown.testAndHandle();
        elevatorIncrementUp.testAndHandle();
        elevatorSetBlock2Pos.testAndHandle();
        elevatorSetBlock3Pos.testAndHandle();
        checkSlow.testAndHandle();
        resetHedingButton.testAndHandle();
        logTime("before mec");
        //lifter.loop();
        //checkPotentiometerPos.testAndHandle();
        if (true) {
            float coeff = slowDrive == true ? 0.5f : 1f;
            this.driveSystem.driveGodMode(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y, coeff);
        } else  {
            this.driveSystem.mecanumDrive(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y, slowDrive);
        }
        logTime("End");

    }


    @Override
    public void stop() {

    }


}