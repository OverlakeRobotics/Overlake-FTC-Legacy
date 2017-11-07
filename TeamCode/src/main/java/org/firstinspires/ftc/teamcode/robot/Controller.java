package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.util.Handler;

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
    private List<Button> buttons;
    private Map<String, Boolean> controllerButtonMap;

    public Controller() {
        buttons = new ArrayList<Button>();
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void handle() {
        for (Button b : buttons) {
            b.testAndHandle();
        }
    }
}
