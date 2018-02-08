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
    //private ClawSystem claw;
    private ElevatorSystem elevator;
    private ParallelLiftSystem lifter;
    private ConfigParser config;
    private Claw2png meMotor;


    private Button parallelBottom;
    private Button parallelTop;

    private Button clawBottom;
    private Button clawMiddle;
    private Button clawTop;
    private Button runMotor;
    private Button runMotorBack;


    private Button elevatorLoadPosition1;
    private Button elevatorUnloadPosition3;
    private Button elevatorIncrementUp;
    private Button elevatorIncrementDown;
    private Button elevatorSetBlock2Pos;
    private Button elevatorSetBlock3Pos;

    private Button checkSlow;

    private Button resetHedingButton;

    MecanumDriveSystem driveSystem;
    boolean slowDrive = false;
    private Button lifterIncrementUp;

    private Button lifterIncrementDown;
    private Button setLifter;
    private Button elevatorGoToZed;
    private Button lifterDown;


    public ControllerOpMode(){
        this.config = new ConfigParser("TeleOpMecanum.omc");
        this.msStuckDetectInit = 20000;
    }

    @Override
    public void init() {

        //
        // claw = new ClawSystem(this.hardwareMap);
        elevator = new ElevatorSystem(this);
        meMotor = new Claw2png(this, telemetry);
        lifter = new ParallelLiftSystem(this);
        this.driveSystem = new MecanumDriveSystem();
        this.driveSystem.init(this.hardwareMap);

        ///////////////////////////////////////////////////////////////////////////

        this.clawBottom = new Button();
        this.clawBottom.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.left_trigger > 0.3;
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
                        return gamepad2.left_bumper;

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
                        return gamepad2.right_trigger > 0.3;
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
                //nothing here right
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
                //nothing here right now
            }
        };

        //////////////////PARALLEL LIFTER////////////////////////////////////

        this.parallelBottom = new Button();
        this.parallelBottom.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_down;
                    }
                };
        this.parallelBottom.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.positionDown();
                    }
                };

        this.parallelTop = new Button();
        this.parallelTop.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_up;
                    }
                };
        this.parallelTop.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.positionUp();
                    }
                };

        this.lifterIncrementUp = new Button();
        this.lifterIncrementUp.isPressed = new Func<Boolean>()
        {
            @Override
            public Boolean value()
            {
                return gamepad1.dpad_up ;
            }
        };
        this.lifterIncrementUp.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.incrementUp();
                    }
                };

        this.lifterIncrementDown = new Button();
        this.lifterIncrementDown.isPressed = new Func<Boolean>()
        {
            @Override
            public Boolean value()
            {
                return gamepad1.dpad_down;
            }
        };
        this.lifterIncrementDown.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.incrementDown();
                    }
                };

        this.setLifter = new Button();
        this.setLifter.isPressed = new Func<Boolean>()
        {
            @Override
            public Boolean value()
            {
                return gamepad2.dpad_down && gamepad2.left_bumper;
            }
        };
        this.setLifter.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.setPosition();
                    }
                };

        this.lifterDown = new Button();
        this.lifterDown.isPressed = new Func<Boolean>()
        {
            @Override
            public Boolean value()
            {
                return gamepad2.b;
            }
        };
        this.lifterDown.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.runMotorDown();
                    }
                };

        /////////////////ELEVATOR///////////////////////////////////////////

        this.elevatorLoadPosition1 = new Button();
        this.elevatorLoadPosition1.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.a && !gamepad2.left_bumper;
                    }
                };
        this.elevatorLoadPosition1.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.positionDown();
                    }
                };

        this.elevatorUnloadPosition3 = new Button();
        this.elevatorUnloadPosition3.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.y && !gamepad2.left_bumper;
                    }
                };
        this.elevatorUnloadPosition3.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.positionUp();
                    }
                };

        this.elevatorIncrementUp = new Button();
        this.elevatorIncrementUp.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.y;
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
                        return gamepad1.a;
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
                        return gamepad2.left_bumper && gamepad2.a;
                    }
                };
        this.elevatorSetBlock2Pos.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.setPosition();
                    }
                };

        this.elevatorSetBlock3Pos = new Button();
        this.elevatorSetBlock3Pos.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.left_bumper && gamepad2.y;
                    }
                };
        this.elevatorSetBlock3Pos.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.setPosition();
                    }
                };

        this.elevatorGoToZed = new Button();
        this.elevatorGoToZed.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.x;
                    }
                };
        this.elevatorGoToZed.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.runMotorDown();
                    }
                };

        //////////AUXILIARY COMMANDS/////////////////////////////

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

    }
    ElapsedTime i = new ElapsedTime();

    public void logTime(String str) {
        Long time = i.nanoseconds();
        RobotLog.ee("Time " + str, time.toString());
    }

    @Override
    public void loop() {
        logTime("Loop Start");

        setLifter.testAndHandle();
        lifterIncrementUp.testAndHandle();
        lifterIncrementDown.testAndHandle();
        clawBottom.testAndHandle();
        clawMiddle.testAndHandle();
        clawTop.testAndHandle();
        runMotor.testAndHandle();
        runMotorBack.testAndHandle();
        logTime("meMotor Start");
        meMotor.loop();
        logTime("meMotor End");

        //checkPotentiometerPos.testAndHandle();
        lifter.parallelLiftLoop();

        parallelBottom.testAndHandle();
        parallelTop.testAndHandle();
        lifter.checkForBottom();
        lifter.isPressed();
        elevator.checkForBottom(telemetry);
        elevator.checkForTop();
        elevator.elevatorLoop();
        elevator.loop();
        lifterDown.testAndHandle();
        elevatorGoToZed.testAndHandle();
        elevatorLoadPosition1.testAndHandle();
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
        /*if (true) {
            float coeff = slowDrive == true ? 0.5f : 1f;
            this.driveSystem.driveGodMode(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y, coeff);
        } else  {*/

        this.driveSystem.mecanumDrive(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y, slowDrive);

        logTime("End");

    }


    @Override
    public void stop() {

    }


}