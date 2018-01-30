package org.firstinspires.ftc.teamcode.robot;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;
        import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;

@Autonomous(name="PixySystem", group="Bot")
public class PixySystem {
    private LinearOpMode linearOpMode;
    private PixyCam pixyCam;
    private PixyCam.Block redBlock;
    private PixyCam.Block blueBlock;
    private PixyCam.Block otherThing;
    private Servo horizServo;
    private Servo vertServo;
    private DcMotor light;
    private double HORIZ_CENTER;
    private double VERT_BOTTOM;
    private double VERT_TOP;
    private boolean blueExists;
    private boolean redExists;
    private boolean otherExists;
    private boolean teamColorIsBlue;

    // object constructor -- takes in the op mode ("this") and the zone variable found in the dead reckoning code
    public PixySystem (LinearOpMode linearOpMode, int zone) {
        this.linearOpMode = linearOpMode;
        if (zone == 0 || zone == 1) { this.teamColorIsBlue = true; } else { this.teamColorIsBlue = false; }
    }

    // runs the series of events that the pixy does
    public void runPixySystem() {
        linearOpMode.waitForStart();
        turnOnLight();
        initServos();
        getPixyValues();
        turnOffLight();
        moveServos();
    }

    // turns on the light
    public void turnOnLight() {
        this.light = linearOpMode.hardwareMap.dcMotor.get("light");
        this.light.setPower(0.5 );
    }

    // turns off the light
    public void turnOffLight() {
        this.light.setPower(0);
    }

    // centers the horizontal servo and drops the vertical one
    public void initServos() {
        if (this.teamColorIsBlue) {
            this.horizServo = linearOpMode.hardwareMap.servo.get("lefthorizservo");
            this.vertServo = linearOpMode.hardwareMap.servo.get("leftvertservo");
            this.HORIZ_CENTER = 0.55;
            this.VERT_BOTTOM = 0.85;
            this.VERT_TOP = 0;
        } else {
            this.horizServo = linearOpMode.hardwareMap.servo.get("righthorizservo");
            this.vertServo = linearOpMode.hardwareMap.servo.get("rightvertservo");
            this.HORIZ_CENTER = 0.415;
            this.VERT_BOTTOM = 0.125;
            this.VERT_TOP = 1;
        }
        linearOpMode.sleep(1000);
        this.vertServo.setPosition(VERT_BOTTOM);
        linearOpMode.sleep(1000);
        this.horizServo.setPosition(HORIZ_CENTER);
        linearOpMode.sleep(1000);
    }

    // gets all the values from the pixy
    public void getPixyValues() {
        if (teamColorIsBlue) { this.pixyCam = linearOpMode.hardwareMap.get(PixyCam.class, "PixyCam1"); }
        else { this.pixyCam = linearOpMode.hardwareMap.get(PixyCam.class, "PixyCam2"); }
        this.redBlock = this.pixyCam.GetBiggestBlock(1);
        this.blueBlock = this.pixyCam.GetBiggestBlock(2);
        this.otherThing = this.pixyCam.GetBiggestBlock(3);


        linearOpMode.telemetry.addData("Red", this.redBlock.toString());
        linearOpMode.telemetry.addData("Blue", this.blueBlock.toString());
        linearOpMode.telemetry.addData("Other", this.otherThing.toString());
        linearOpMode.telemetry.update();

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
        } else if (this.otherExists && this.blueExists) {
            if (this.teamColorIsBlue) {
                if (this.blueBlock.x < this.otherThing.x) { this.horizServo.setPosition(this.HORIZ_CENTER - 0.2); }
                else { this.horizServo.setPosition(HORIZ_CENTER + 0.2); }
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
        linearOpMode.sleep(1000);
        this.horizServo.setPosition(HORIZ_CENTER);
        linearOpMode.sleep(1000);
        this.vertServo.setPosition(VERT_TOP);
        linearOpMode.sleep(1000);
    }
}
