package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.util.Handler;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by EvanCoulson on 11/3/17.
 */

public class Controller {
    public Gamepad gamepad;

    private ConfigParser parser;

    public Button a;
    public Button b;
    public Button x;
    public Button y;
    public Button back;
    public Button start;
    public Button dPadDown;
    public Button dPadUp;
    public Button dPadLeft;
    public Button dPadRight;
    public Button leftBumper;
    public Button leftStickButton;
    public Button leftTrigger;
    public Button rightBumper;
    public Button rightStickButton;
    public Button rightTrigger;

    public Controller(final Gamepad gamepad) {
        this.gamepad = gamepad;
        a = new Button();
        b = new Button();
        x = new Button();
        y = new Button();
        back = new Button();
        start = new Button();
        dPadDown = new Button();
        dPadUp = new Button();
        dPadLeft = new Button();
        dPadRight = new Button();
        leftBumper = new Button();
        leftStickButton = new Button();
        leftTrigger = new Button();
        rightBumper = new Button();
        rightStickButton = new Button();
        rightTrigger = new Button();

        a.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.a;
            }
        };

        b.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.b;
            }
        };

        x.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.x;
            }
        };

        y.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.y;
            }
        };

        back.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.back;
            }
        };

        start.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.start;
            }
        };

        dPadDown.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.dpad_down;
            }
        };

        dPadLeft.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.dpad_left;
            }
        };

        dPadRight.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.dpad_right;
            }
        };

        dPadDown.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.dpad_down;
            }
        };

        leftBumper.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.left_bumper;
            }
        };

        leftStickButton.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.left_stick_button;
            }
        };

        rightBumper.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.right_bumper;
            }
        };

        rightStickButton.isPressed = new Func<Boolean>() {
            @Override
            public Boolean value() {
                return gamepad.right_stick_button;
            }
        };
    }

    public void handle() {

    }
}
