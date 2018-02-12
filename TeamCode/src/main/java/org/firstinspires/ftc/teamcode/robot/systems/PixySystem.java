package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;

@Autonomous(name="PixySystem", group="Bot")
public class PixySystem extends System {
    private LinearOpMode opMode;
    private PixyCam pixyCam;
    private PixyCam.Block redBlock;
    private PixyCam.Block blueBlock;
    private PixyCam.Block otherThing;
    private Servo horizServo;
    public Servo vertServo;
    private DcMotor light;
    private double HORIZ_CENTER;
    private double VERT_BOTTOM;
    private double VERT_TOP;
    private boolean blueExists;
    private boolean redExists;
    private boolean otherExists;
    private boolean teamColorIsBlue;
    private int pause;

    // object constructor -- takes in the op mode ("this") and the zone variable found in the dead reckoning code
    public PixySystem (LinearOpMode linearOpMode, int zone) {
        super(linearOpMode, "PixySystem");
        this.opMode = linearOpMode;
        this.pause = 600;
        if (zone == 0 || zone == 1) { this.teamColorIsBlue = true; } else { this.teamColorIsBlue = false; }
    }

    // runs the series of events that the pixy does
    public void runPixySystem() {
        this.opMode.waitForStart();
        turnOnLight();
        initServos();
        getPixyValues();
        turnOffLight();
        moveServos();
    }

    // turns on the light
    public void turnOnLight() {
        this.light = this.opMode.hardwareMap.dcMotor.get("light");
        this.light.setPower(0.5 );
    }

    // turns off the light
    public void turnOffLight() {
        this.light.setPower(0);
    }

    // centers the horizontal servo and drops the vertical one
    public void initServos() {
        if (this.teamColorIsBlue) {
            this.horizServo = this.opMode.hardwareMap.servo.get("lefthorizservo");
            this.vertServo = this.opMode.hardwareMap.servo.get("leftvertservo");
            this.HORIZ_CENTER = 0.55;
            this.VERT_BOTTOM = 0.85;
            this.VERT_TOP = 0;
        } else {
            this.horizServo = this.opMode.hardwareMap.servo.get("righthorizservo");
            this.vertServo = this.opMode.hardwareMap.servo.get("rightvertservo");
            this.HORIZ_CENTER = 0.415;
            this.VERT_BOTTOM = 0.125;
            this.VERT_TOP = 1;
        }
        this.opMode.sleep(pause);
        this.vertServo.setPosition(VERT_BOTTOM);
        this.opMode.sleep(pause);
        this.horizServo.setPosition(HORIZ_CENTER);
        this.opMode.sleep(pause);
    }

    // gets all the values from the pixy
    public void getPixyValues() {
        if (teamColorIsBlue) { this.pixyCam = this.opMode.hardwareMap.get(PixyCam.class, "PixyCam1"); }
        else { this.pixyCam = this.opMode.hardwareMap.get(PixyCam.class, "PixyCam2"); }
        this.redBlock = this.pixyCam.GetBiggestBlock(1);
        this.blueBlock = this.pixyCam.GetBiggestBlock(2);
        this.otherThing = this.pixyCam.GetBiggestBlock(3);


        this.opMode.telemetry.addData("Red", this.redBlock.toString());
        this.opMode.telemetry.addData("Blue", this.blueBlock.toString());
        this.opMode.telemetry.addData("Other", this.otherThing.toString());
        this.opMode.telemetry.update();

        // determines if each color is actually seen by the camera or not
        if (this.blueBlock.x != 0) { this.blueExists = true; } else { this.blueExists = false; }
        if (this.redBlock.x != 0) { this.redExists = true; } else { this.redExists = false; }
        if (this.otherThing.x != 0) { this.otherExists = true; } else { this.otherExists = false; }
    }

    // uses two of three possible pieces of information and moves the horizontal servo accordingly
    // takes into account all 12 possible situations that could happen
    public void moveServos() {
        if (this.blueExists && this.redExists) {
            if (this.teamColorIsBlue) {
                if (this.redBlock.x < this.blueBlock.x) { this.horizServo.setPosition(this.HORIZ_CENTER + 0.2); }
                else { this.horizServo.setPosition(HORIZ_CENTER - 0.2); }
            } else {
                if (this.redBlock.x < this.blueBlock.x) { this.horizServo.setPosition(this.HORIZ_CENTER - 0.2); }
                else { this.horizServo.setPosition(HORIZ_CENTER + 0.2); }
            }
        } else if (this.blueExists && this.otherExists) {
            if (this.teamColorIsBlue) {
                if (this.otherThing.x < this.blueBlock.x) { this.horizServo.setPosition(this.HORIZ_CENTER + 0.2); }
                else { this.horizServo.setPosition(HORIZ_CENTER - 0.2); }
            } else {
                if (this.blueBlock.x < this.otherThing.x) { this.horizServo.setPosition(this.HORIZ_CENTER + 0.2); }
                else { this.horizServo.setPosition(HORIZ_CENTER - 0.2); }
            }
        } else if (this.otherExists && this.redExists) {
            if (this.teamColorIsBlue) {
                if (this.redBlock.x < this.otherThing.x) { this.horizServo.setPosition(this.HORIZ_CENTER + 0.2); }
                else { this.horizServo.setPosition(HORIZ_CENTER - 0.2); }
            } else {
                if (this.redBlock.x < this.otherThing.x) { this.horizServo.setPosition(this.HORIZ_CENTER - 0.2); }
                else { this.horizServo.setPosition(HORIZ_CENTER + 0.2); }
            }
        }

        // brings the servos back to the starting position
        this.opMode.sleep(pause);
        this.horizServo.setPosition(HORIZ_CENTER);
        this.opMode.sleep(pause);
        this.vertServo.setPosition(VERT_TOP);
        this.opMode.sleep(pause);
    }
}
