package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;

@Autonomous(name="PixySystem", group="Bot")
public class PixySystem {
    private LinearOpMode linearOpMode;
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
        this.linearOpMode = linearOpMode;
        if (!isRedSide) {
            this.teamColorIsBlue = true;
        } else {
            this.teamColorIsBlue = false;
        }
    }

    public void initPixyStuff() {
        if (teamColorIsBlue) {
            this.pixyCam = linearOpMode.hardwareMap.get(PixyCam.class, "PixyCam1");
            this.redBlock = pixyCam.GetBiggestBlock(1);
            this.blueBlock = pixyCam.GetBiggestBlock(2);
            this.horizServo = linearOpMode.hardwareMap.servo.get("lefthorizservo");
            this.vertServo = linearOpMode.hardwareMap.servo.get("leftvertservo");
            this.HORIZ_CENTER = 0.55;
            this.VERT_BOTTOM = 0.85;
            this.VERT_TOP = 1.0;
        } else {
            this.pixyCam = linearOpMode.hardwareMap.get(PixyCam.class, "PixyCam2");
            this.redBlock = pixyCam.GetBiggestBlock(1);
            this.blueBlock = pixyCam.GetBiggestBlock(2);
            this.horizServo = linearOpMode.hardwareMap.servo.get("righthorizservo");
            this.vertServo = linearOpMode.hardwareMap.servo.get("rightvertservo");
            this.HORIZ_CENTER = 0.415;
            this.VERT_BOTTOM = 0.125;
            this.VERT_TOP = 0.8;
        }
        linearOpMode.telemetry.addData("Red", this.redBlock.toString());
        linearOpMode.telemetry.addData("Blue", this.blueBlock.toString());
        linearOpMode.telemetry.update();
    }

    public void doServoStuff() {
        linearOpMode.sleep(1000);
        if (this.redBlock.x == 0 || this.blueBlock.x == 0) {
        } else if (teamColorIsBlue) {
            this.vertServo.setPosition(VERT_BOTTOM);
            linearOpMode.sleep(1000);
            this.horizServo.setPosition(HORIZ_CENTER);
            linearOpMode.sleep(1000);

            if (this.redBlock.x > this.blueBlock.x) {
                this.horizServo.setPosition(HORIZ_CENTER - 0.2);
            } else {
                this.horizServo.setPosition(HORIZ_CENTER + 0.2);
            }

            linearOpMode.sleep(1000);
            this.horizServo.setPosition(HORIZ_CENTER);
            linearOpMode.sleep(1000);
            this.vertServo.setPosition(0);
            linearOpMode.sleep(1000);
        } else {
            this.vertServo.setPosition(VERT_BOTTOM);
            linearOpMode.sleep(1000);
            this.horizServo.setPosition(HORIZ_CENTER);
            linearOpMode.sleep(1000);

            if (this.redBlock.x > this.blueBlock.x) {
                this.horizServo.setPosition(HORIZ_CENTER + 0.2);
            } else {
                this.horizServo.setPosition(HORIZ_CENTER - 0.2);
            }

            linearOpMode.sleep(1000);
            this.horizServo.setPosition(HORIZ_CENTER);
            linearOpMode.sleep(1000);
            this.vertServo.setPosition(1);
            linearOpMode.sleep(1000);
        }
    }
}
