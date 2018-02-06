package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;
import org.firstinspires.ftc.teamcode.robot.systems.System;

@Autonomous(name="PixySystem", group="Bot")
public class PixySystem extends System {
    private LinearOpMode opMode;
    private PixyCam pixyCam;

    private PixyCam.Block redBlock;
    private PixyCam.Block blueBlock;

    private Servo horizServo;
    private Servo vertServo;
    private double HORIZ_CENTER;
    private double VERT_BOTTOM;
    private double VERT_TOP;

    private boolean teamColorIsBlue;

    public PixySystem (LinearOpMode linearOpMode, boolean isRedSide) {
        super(linearOpMode, "PixySystem");
        this.opMode = linearOpMode;
        if (!isRedSide) {
            this.teamColorIsBlue = true;
        } else {
            this.teamColorIsBlue = false;
        }
    }

    public void initPixyStuff() {
        if (teamColorIsBlue) {
            this.pixyCam = map.get(PixyCam.class, "PixyCam1");
            this.redBlock = pixyCam.GetBiggestBlock(1);
            this.blueBlock = pixyCam.GetBiggestBlock(2);
            this.horizServo = map.servo.get("lefthorizservo");
            this.vertServo = map.servo.get("leftvertservo");
            this.HORIZ_CENTER = 0.55;
            this.VERT_BOTTOM = 0.85;
            this.VERT_TOP = 1.0;
        } else {
            this.pixyCam = map.get(PixyCam.class, "PixyCam2");
            this.redBlock = pixyCam.GetBiggestBlock(1);
            this.blueBlock = pixyCam.GetBiggestBlock(2);
            this.horizServo = map.servo.get("righthorizservo");
            this.vertServo = map.servo.get("rightvertservo");
            this.HORIZ_CENTER = 0.415;
            this.VERT_BOTTOM = 0.125;
            this.VERT_TOP = 0.8;
        }
        telemetry.addData("Red", this.redBlock.toString());
        telemetry.addData("Blue", this.blueBlock.toString());
        telemetry.update();
    }

    public void doServoStuff() {
        opMode.sleep(1000);
        if (this.redBlock.x == 0 || this.blueBlock.x == 0) {
        } else if (teamColorIsBlue) {
            this.vertServo.setPosition(VERT_BOTTOM);
            opMode.sleep(1000);
            this.horizServo.setPosition(HORIZ_CENTER);
            opMode.sleep(1000);

            if (this.redBlock.x > this.blueBlock.x) {
                this.horizServo.setPosition(HORIZ_CENTER - 0.2);
            } else {
                this.horizServo.setPosition(HORIZ_CENTER + 0.2);
            }

            opMode.sleep(1000);
            this.horizServo.setPosition(HORIZ_CENTER);
            opMode.sleep(1000);
            this.vertServo.setPosition(0);
            opMode.sleep(1000);
        } else {
            this.vertServo.setPosition(VERT_BOTTOM);
            opMode.sleep(1000);
            this.horizServo.setPosition(HORIZ_CENTER);
            opMode.sleep(1000);

            if (this.redBlock.x > this.blueBlock.x) {
                this.horizServo.setPosition(HORIZ_CENTER + 0.2);
            } else {
                this.horizServo.setPosition(HORIZ_CENTER - 0.2);
            }

            opMode.sleep(1000);
            this.horizServo.setPosition(HORIZ_CENTER);
            opMode.sleep(1000);
            this.vertServo.setPosition(1);
            opMode.sleep(1000);
        }
    }
}
