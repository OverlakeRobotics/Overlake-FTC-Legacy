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
    public Button rightBumper;
    public Button rightStickButton;

    public Controller(Gamepad gamepad) {
        this.gamepad = gamepad;
        a = new Button();
        b = new Button();
        x = new Button();
        y = new Button()
    }
}
