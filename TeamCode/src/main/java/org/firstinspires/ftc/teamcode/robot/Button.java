package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.util.Handler;

public class Button
{
    public Func<Boolean> isPressed;
    public Handler pressedHandler;
    public Handler releasedHandler;

    private boolean wasPressed;
    public boolean justPressed;
    public boolean justReleased;

    public Button()
    {
    }

    public Button(Func<Boolean> isPressed)
    {
        this.isPressed = isPressed;
    }

    public Button(Func<Boolean> isPressed, Handler pressedHandler)
    {
        this.isPressed = isPressed;
        this.pressedHandler = pressedHandler;
    }

    public Button(Func<Boolean> isPressed, Handler pressedHandler, Handler releasedHandler)
    {
        this.isPressed = isPressed;
        this.pressedHandler = pressedHandler;
        this.releasedHandler = releasedHandler;
    }

    public void testAndHandle() throws Exception {
        Boolean pressed = this.isPressed.value();

        this.justPressed = (pressed && !this.wasPressed);
        this.justReleased = (!pressed && this.wasPressed);
        this.wasPressed = pressed;

        if (this.justPressed && this.pressedHandler != null)
            this.pressedHandler.invoke();

        if (this.justReleased && this.releasedHandler != null)
            this.releasedHandler.invoke();
    }
}
