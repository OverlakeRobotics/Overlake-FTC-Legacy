package org.firstinspires.ftc.teamcode.robot;

/**
 * Created by EvanCoulson on 11/3/17.
 */

public interface ButtonCallback {
    boolean isPressed();
    void pressedHandler();
    void releasedHandler();
}
