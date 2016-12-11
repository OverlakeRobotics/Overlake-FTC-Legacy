/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.robot.*;
import org.firstinspires.ftc.teamcode.util.Handler;


/**
 * TeleOpMecanum Mode
 * <p>
 * Enables control of the robot via the gamepad
 */

@TeleOp(name="TeleOpMecanum", group="TeleOp")
public class TeleOpMecanum extends OpMode {

	//region Motors
	MecanumDriveSystem driveSystem;

    private Button flickerButton;
    private Button flickerShootPositionButton;
    private Button flickerLoadPositionButton;
    private Button flickerIncrementAngle;
    private Button flickerDecrementAngle;
    private Button flickerSaveShootPos;
    private Button flickerSaveLoadPos;

    private Button ballLiftFowardButton;
    private Button ballLiftReverseButton;
    private Button ballFlailFowardButton;
    private Button ballFlailReverseButton;
    private FlickerSystem flicker;
    private BallLiftSystem ballLift;

	public TeleOpMecanum() {

	}

	/*
	 * Code to run when the op mode is initialized goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
	 */
	@Override
	public void init()
	{
        driveSystem = new MecanumDriveSystem();
        this.driveSystem.init(this.hardwareMap);
        flicker = new FlickerSystem(this.hardwareMap);
        ballLift = new BallLiftSystem(this.hardwareMap);

        this.ballLiftFowardButton = new Button();

        this.ballLiftFowardButton.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.right_bumper;
                    }
                };
        this.ballLiftFowardButton.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        if (/*!flicker.isBallLoaded()*/true) {
                            ballLift.runLift(true);
                        }
                        ballLift.runBelt(true);
                    }
                };
        this.ballLiftFowardButton.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        ballLift.stopBelt();
                        ballLift.stopLift();
                    }
                };

        this.ballLiftReverseButton = new Button();

        this.ballLiftReverseButton.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.left_bumper;
                    }
                };
        this.ballLiftReverseButton.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        ballLift.runLift(false);
                        ballLift.runBelt(false);
                    }
                };
        this.ballLiftReverseButton.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        ballLift.stopBelt();
                        ballLift.stopLift();
                    }
                };


        this.ballFlailFowardButton = new Button();

        this.ballFlailFowardButton.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.a;
                    }
                };
        this.ballFlailFowardButton.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        if (/*!flicker.isBallLoaded()*/true) {
                            ballLift.runFlail(true);
                        }
                    }
                };
        this.ballFlailFowardButton.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        ballLift.stopFlail();
                    }
                };

        this.ballFlailReverseButton = new Button();


        this.ballFlailReverseButton.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.b;
                    }
                };
        this.ballFlailReverseButton.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        ballLift.runFlail(false);
                    }
                };
        this.ballFlailReverseButton.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        ballLift.stopFlail();
                    }
                };

        this.flickerButton = new Button();
        this.flickerButton.isPressed =
            new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.right_trigger > 0.75;
                    }
                };
        this.flickerButton.pressedHandler =
            new Handler()
            {
                @Override
                public void invoke()
                {
                    flicker.start();
                }
            };
        this.flickerButton.releasedHandler =
            new Handler()
            {
                @Override
                public void invoke()
                {
                    flicker.stop();
                }
            };

        this.flickerShootPositionButton = new Button();
        this.flickerShootPositionButton.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_up;
                    }
                };
        this.flickerShootPositionButton.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        flicker.setShootPosition();
                    }
                };
        this.flickerShootPositionButton.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        //
                    }
                };

        this.flickerLoadPositionButton = new Button();
        this.flickerLoadPositionButton.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.dpad_down;
                    }
                };
        this.flickerLoadPositionButton.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        flicker.setLoadPosition();
                    }
                };
        this.flickerLoadPositionButton.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        //
                    }
                };

        this.flickerIncrementAngle = new Button();
        this.flickerIncrementAngle.isPressed = new Func<Boolean>()
        {
            @Override
            public Boolean value()
            {
                return gamepad2.dpad_right;
            }
        };
        this.flickerIncrementAngle.pressedHandler = new Handler()
        {
            @Override
            public void invoke()
            {
                flicker.incrementLoad();
            }
        };
        this.flickerIncrementAngle.releasedHandler = new Handler()
        {
            @Override
            public void invoke()
            {
                //
            }
        };

        this.flickerDecrementAngle = new Button();
        this.flickerDecrementAngle.isPressed = new Func<Boolean>()
        {
            @Override
            public Boolean value()
            {
                return gamepad2.dpad_left;
            }
        };
        this.flickerDecrementAngle.pressedHandler = new Handler()
        {
            @Override
            public void invoke()
            {
                flicker.decrementLoad();
            }
        };
        this.flickerDecrementAngle.releasedHandler = new Handler()
        {
            @Override
            public void invoke()
            {
                //
            }
        };

        this.flickerSaveLoadPos = new Button();
        this.flickerSaveLoadPos.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value()
            {
                return gamepad2.x;
            }
        };
        this.flickerSaveLoadPos.pressedHandler = new Handler()
        {
            @Override
            public void invoke()
            {
                flicker.saveLoadPosition();
            }
        };
        this.flickerSaveLoadPos.releasedHandler = new Handler()
        {
            @Override
            public void invoke()
            {
                //
            }
        };

        this.flickerSaveShootPos = new Button();
        this.flickerSaveShootPos.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value()
            {
                return gamepad2.y;
            }
        };
        this.flickerSaveShootPos.pressedHandler = new Handler() {
            @Override
            public void invoke() {
                flicker.saveShootPosition();
            }
        };
        this.flickerSaveShootPos.releasedHandler = new Handler()
        {
            @Override
            public void invoke()
            {
                //
            }
        };


	}

	/*
	 * This method will be called repeatedly in a loop
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
	 */
	@Override
	public void loop()
	{
		// scale the joystick value to make it easier to control
		// the robot more precisely at slower speeds.
        this.driveSystem.mecanumDrive(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y);

        flickerButton.testAndHandle();
        flickerShootPositionButton.testAndHandle();
        flickerLoadPositionButton.testAndHandle();
        ballLiftFowardButton.testAndHandle();
        ballLiftReverseButton.testAndHandle();
        ballFlailFowardButton.testAndHandle();
        ballFlailReverseButton.testAndHandle();
        flickerDecrementAngle.testAndHandle();
        flickerIncrementAngle.testAndHandle();
        flickerSaveShootPos.testAndHandle();
        flickerSaveLoadPos.testAndHandle();

		telemetry.addData("Text", gamepad1.right_stick_x + ", " + gamepad1.right_stick_y + ", " + gamepad1.left_stick_x + ", " + gamepad1.left_stick_y);
        telemetry.addData("Angle", flicker.getServoAngle());
	}

	/*
	 * Code to run when the op mode is first disabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
	 */
	@Override
	public void stop()
	{

	}
}
