package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.util.Handler;

public abstract class Button implements ButtonCallback
{
    private boolean wasPressed;
    public boolean justPressed;
    public boolean justReleased;

    public void testAndHandle()
    {
        Boolean pressed = this.isPressed();

        this.justPressed = (pressed && !this.wasPressed);
        this.justReleased = (!pressed && this.wasPressed);
        this.wasPressed = pressed;

        this.pressedHandler();
        this.releasedHandler();
    }
}
