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
public class ControllerOpMode extends OpMode {
    private ClawSystem claw;
    private ElevatorSystem elevator;
    private Button elevatorTopPos;
    private Button clawRestPosition;
    private Button clawLoadPosition;
    private Button clawReleasePosition;
    private Button elevatorLoadPosition1;
    private Button elevatorStackPosition;
    private Button elevatorLoadPosition2;
    private Button elevatorUnloadPosition1;
    private Button elevatorUnloadPosition2;
    private Button elevatorUnloadPosition3;
    MecanumDriveSystem driveSystem;


    public ControllerOpMode(){

    }

    @Override
    public void init(){
        claw = new ClawSystem(this.hardwareMap);
        elevator = new ElevatorSystem(this.hardwareMap, telemetry);

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
                        return gamepad1.right_trigger>0.75;
                    }
                };
        this.clawLoadPosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.setLoadPosition();
                    }
                };

        this.clawReleasePosition = new Button();
        this.clawReleasePosition.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.left_trigger>0.75;
                    }
                };
        this.clawReleasePosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.setReleasePosition();
                    }
                };

        this.clawRestPosition = new Button();
        this.clawRestPosition.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad1.dpad_down;
                    }
                };
        this.clawRestPosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        claw.setRestPosition();
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


        this.elevatorLoadPosition2 = new Button();
        this.elevatorLoadPosition2.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_down;
                    }
                };
        this.elevatorLoadPosition2.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.goToLoadPos2();
                    }
                };

        this.elevatorUnloadPosition1 = new Button();
        this.elevatorUnloadPosition1.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.b;
                    }
                };
        this.elevatorUnloadPosition1.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.goToUnloadPos1();
                    }
                };

        this.elevatorUnloadPosition2 = new Button();
        this.elevatorUnloadPosition2.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.x;
                    }
                };
        this.elevatorUnloadPosition2.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.goToUnloadPos2();
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
                        elevator.goToUnloadPos3();
                    }
                };

        this.elevatorStackPosition = new Button();
        this.elevatorStackPosition.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_up;
                    }
                };
        this.elevatorStackPosition.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        elevator.goToStackPos();
                    }
                };



    }
    @Override
    public void loop() {
        clawRestPosition.testAndHandle();
        clawLoadPosition.testAndHandle();
        clawReleasePosition.testAndHandle();
        elevator.checkForBottom(telemetry);
        elevator.checkForTop();
        elevatorLoadPosition1.testAndHandle();
        elevatorLoadPosition2.testAndHandle();
        elevatorUnloadPosition1.testAndHandle();
        elevatorUnloadPosition2.testAndHandle();
        elevatorUnloadPosition3.testAndHandle();
        elevatorStackPosition.testAndHandle();
        this.driveSystem.mecanumDrive(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y);

    }


    @Override
    public void stop() {

    }


}