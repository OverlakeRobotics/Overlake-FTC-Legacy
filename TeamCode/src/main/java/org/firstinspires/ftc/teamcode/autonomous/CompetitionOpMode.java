package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.systems.physical.ParallelLiftSystem;
import org.firstinspires.ftc.teamcode.robot.systems.physical.PixySystem;

/**
 * Created by lexis on 23-Oct-17.
 */

@Autonomous(name = "CompetitionOpMode", group = "Bot")
public class CompetitionOpMode extends BaseOpMode
{
    private int zone;
    private int inchesToReferenceBoxNonAudience;
    private int inchesToReferenceBoxAudience;
    private double powerSetting1;
    private double powerSetting2;
    private int cryptoApproachInches;
    private boolean isBlue;
    private boolean isAudienceSide;
    private int b;
    PixySystem pixySystem;
    private double startHeading;
    private int doi;

    public CompetitionOpMode()
    {
        super("Autonomous");
    }

    private void init2()
    {
        this.doi = config.getInt("doi");
        this.inchesToReferenceBoxNonAudience = config.getInt("InchesToReferenceBoxNonAudience"); // the length of the first drive forward, ending at the refernce box
        this.inchesToReferenceBoxAudience = config.getInt("InchesToReferenceBoxAudience"); // the length of the first drive forward, ending at the refernce box
        this.zone = config.getInt("zone"); // sets which starting zone you are in
        this.powerSetting1 = config.getDouble("powerSetting1"); // the power setting for driving off the stone
        this.powerSetting2 = config.getDouble("powerSetting2"); // the power setting for everything other than getting off the stone
        this.cryptoApproachInches = config.getInt("cryptoApproachInches"); // the length of the drive forward to place the cube
        this.b = config.getInt("b");
        this.isBlue = config.getBoolean("isBlue");
        this.isAudienceSide = config.getBoolean("isAudienceSide");
        syncConfigZoneBooleansAndInts(this.zone, this.isBlue, this.isAudienceSide);
        this.pixySystem = new PixySystem(this, zone);
    }

    @Override
    public void runOpMode()
    {

        this.initSystems();
        init2();
        /*parallelLiftSystem.initFromPark();
        elevator.initFromAutoInit();
        this.startHeading = imuSystem.getHeading();

        ////
        waitForStart();
        ////


        pixySystem.runPixySystem();
        vuforiaCryptoBox(zone);*/

        waitForStart();

        vuforiaDrive(800, 800, 1);


        stop();
    }

    public void vuforiaCryptoBox(int zone)
    {
        double inchesPerBox = b;
        int boxNumber = eye.look(); // 0 = left   1 = center   2 = right
        telemetry.addLine("DETERMINED THE PICTURE!!!! YAY. Its picture number " + boxNumber);
        telemetry.update();
        sleep(50);

        grabBlock();

        // zone 0 is blue non-audience      zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0)
        { // blue non-audience
            driveSystem.driveToPositionInchez(1, 1, this);
            driveSystem.driveToPositionInchez(doi, powerSetting1, this);//42
            returnToHeading(-90, powerSetting2);
            if ((inchesPerBox * boxNumber + inchesToReferenceBoxNonAudience) >= 0)
            {
                driveSystem.driveToPositionInchez((inchesPerBox * boxNumber + inchesToReferenceBoxNonAudience), powerSetting2, this); // 25
            }
            else
            {
                driveSystem.driveToPositionInchezBackwordz((inchesPerBox * boxNumber + inchesToReferenceBoxNonAudience), powerSetting2, this); // 25
            }
            returnToHeading(-30, powerSetting2);//60
            driveSystem.driveToPositionInchez(cryptoApproachInches, powerSetting2, this);
            placeBlock();
            driveSystem.driveToPositionInchezBackwordz(8, powerSetting2, this);
            claw.runMotorBackUpALotProbablyDeleteLater();
        }
        else if (zone == 1)
        { // blue audience
            driveSystem.driveToPositionInchez(1, 1, this);
            if (((inchesPerBox * boxNumber) + inchesToReferenceBoxAudience) >= 0)
            {
                driveSystem.driveToPositionInchez((inchesPerBox * boxNumber + inchesToReferenceBoxAudience), powerSetting2, this); // 25
            }
            else
            {
                driveSystem.driveToPositionInchezBackwordz((inchesPerBox * boxNumber + inchesToReferenceBoxAudience), powerSetting2, this); // 25
            }
            returnToHeading(60, powerSetting2);
            driveSystem.driveToPositionInchez(cryptoApproachInches, powerSetting2, this);
            placeBlock();
            driveSystem.driveToPositionInchezBackwordz(8, powerSetting2, this);
            claw.runMotorBackUpALotProbablyDeleteLater();
        }
        else if (zone == 2)
        { // red non-audience
            driveSystem.driveToPositionInchez(1, 1, this);
            driveSystem.driveToPositionInchez(doi, powerSetting1, this);
            returnToHeading(90, powerSetting2);
            if (((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxNonAudience) >= 0)
            {
                driveSystem.driveToPositionInchez(((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxNonAudience), powerSetting2, this); // 25
            }
            else
            {
                driveSystem.driveToPositionInchezBackwordz(((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxNonAudience), powerSetting2, this); // 25
            }
            returnToHeading(30, powerSetting2);//-60
            driveSystem.driveToPositionInchez(cryptoApproachInches, powerSetting2, this);
            placeBlock();
            driveSystem.driveToPositionInchezBackwordz(8, powerSetting2, this);
            claw.runMotorBackUpALotProbablyDeleteLater();
        }
        else if (zone == 3)
        { // red audience
            driveSystem.driveToPositionInchez(1, 1, this);
            if (((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxAudience) >= 0)
            {
                driveSystem.driveToPositionInchez(((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxAudience), powerSetting2, this); // 25
            }
            else
            {
                driveSystem.driveToPositionInchezBackwordz(((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxAudience), powerSetting2, this); // 25
            }
            returnToHeading(-60, 1);
            driveSystem.driveToPositionInchez(cryptoApproachInches, powerSetting2, this);
            placeBlock();
            driveSystem.driveToPositionInchezBackwordz(8, powerSetting2, this);
            claw.runMotorBackUpALotProbablyDeleteLater();
        }
        else if (zone == 4)
        { //this is a test zone to test inches per box
            driveSystem.driveToPositionInchez(-inchesPerBox, powerSetting2, this);
            sleep(2000);
            driveSystem.driveToPositionInchez(-inchesPerBox, powerSetting2, this);
        }
    }

    public void returnToHeading(double heading, double power)
    {
        double correctionNeeded = heading - imuSystem.getHeading();
        telemetry.addLine("returning to " + heading + " by adjusting by " + correctionNeeded + "currently at " + imuSystem.getHeading());
        telemetry.update();
        driveSystem.turn(correctionNeeded, power, this);
    }

    public void vuforiaDrive(int x, int y, double power)
    {
        int tollerance = 100;

        double travelX = eye.getX() - x;
        double travelY = eye.getY() - y;

        driveSystem.turn(-(eye.getRotX()), 1, this);

        double px = 0;
        double py = 0;

        while (x > tollerance || y > tollerance)
        {
            if (eye.getRotX() > 10)
            {
                driveSystem.turn(-(eye.getRotX()), 1, this);
            }

            travelX = eye.getX() - x;
            travelY = eye.getY() - y;

            if (travelX > travelY)
            {
                px = power;
                py = ((power / travelX) * travelY);
            }
            else
            {
                py = power;
                py = ((power / travelX) * travelX);
            }

            driveSystem.mecanumDrive((float) px, (float) px, (float) py, (float) py, false);
            sleep(50);
            eye.telemetry(telemetry);
        }
    }

    public void calibrate()
    {
        elevator.goToTopSynch(0.4);
        parallelLiftSystem.goToBottomSync();
        parallelLiftSystem.goToIndexSync(ParallelLiftSystem.middleIndex);
        elevator.goToIndexSynch(elevator.MIDDLE_INDEX);
        parallelLiftSystem.goToPostitionSync(parallelLiftSystem.park);
        claw.goToTop();
    }

    public void placeBlock()
    {
        elevator.goToIndexSynch(0);
        this.claw.runMotorBackUpALotProbablyDeleteLater();
        sleep(1000);
    }

    public void grabBlock()
    {
        this.claw.openSynch(this);
        sleep(500);
        elevator.goToIndexSynch(2);
    }

    public void correctBoxLeftApproach(int boxNumber)
    {
        int inchesPerBox = 15;
        telemetry.addLine("driving to box: " + boxNumber + " or inches: " + (-inchesPerBox * boxNumber));
        telemetry.update();
//        driveToPositionInches((-inchesPerBox * boxNumber), 0.75);
    }

    public void correctBoxRightApproach(int boxNumber)
    {
        int inchesPerBox = 11;
        this.driveSystem.driveToPositionInches(((inchesPerBox * 2) - (boxNumber * inchesPerBox)), 1);
    }

    // zone 0 is blue non-audience      zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
    public void syncConfigZoneBooleansAndInts(int zone, boolean isBlue, boolean isAudienceSide)
    {
        if (zone > 4)
        {
            if (isBlue && isAudienceSide)
            {
                this.zone = 0;
            }
            else if (isBlue && !isAudienceSide)
            {
                this.zone = 1;
            }
            else if (!isBlue && isAudienceSide)
            {
                this.zone = 2;
            }
            else if (!isBlue && !isAudienceSide)
            {
                this.zone = 3;
            }
        }
        else
        {
            if (zone == 0)
            {
                this.isBlue = true;
                this.isAudienceSide = false;
            }
            else if (zone == 1)
            {
                this.isBlue = true;
                this.isAudienceSide = true;
            }
            else if (zone == 2)
            {


                this.isBlue = false;
                this.isAudienceSide = false;
            }
            else if (zone == 3)
            {
                this.isBlue = false;
                this.isAudienceSide = true;
            }
        }
    }

    /*public void cryptoBox(int zone) {
        claw.goToBottom();
        sleep(2000);
        ////////////////////////////////////////////////////elevator.goToUnloadBlock2();
        // pic 0 is left     pic 1 is right      pic 2 is center
        // zone 0 is blue non-audience     zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0) {
            driveSystem.driveToPositionInches(inchesToReferenceBoxAudience, powerSetting1);
            driveSystem.turn(-90, 1);
            driveSystem.driveToPositionInches(-18, powerSetting2);
            driveSystem.turn(90, 1);
            /////////////////////////////////////////elevator.goToZero(telemetry);
            claw.goToTop();
            sleep(1000);
            driveSystem.driveToPositionInches(-20, powerSetting2);
        } else if (zone == 1) {
            driveSystem.driveToPositionInches(-50, powerSetting1);
            driveSystem.turn(90, powerSetting2);
            ////////////////////////////////////////////////elevator.goToZero(telemetry);
            claw.goToTop();
            sleep(2000);
            driveSystem.driveToPositionInches(-13, 1);
        } else if (zone == 2) {
            driveSystem.driveToPositionInches(40, 1);
            driveSystem.turn(-90, 1);
            driveSystem.driveToPositionInches(-18, 1);
            driveSystem.turn(-90, 1);
            ///////////////////////////////////elevator.goToZero(telemetry);
            claw.goToTop();
            sleep(1000);
            driveSystem.driveToPositionInches(-18, 1);
        } else {
            driveSystem.driveToPositionInches(-50, 1);
            driveSystem.turn(-90, 1);
            /////////////////////////////////elevator.goToZero(telemetry);
            claw.goToTop();
            sleep(2000);
            driveSystem.driveToPositionInches(-18, 1);
        }
        claw.goToTop();
    }

    /*public void initPixy() {
        pixyCam = hardwareMap.get(PixyCam.class, "pixycam");
        //pixyCam2 = hardwareMap.get(PixyCam.class, "pixycam2");

        //this.rightVertServo = hardwareMap.servo.get("rightvertservo");
        this.leftVertServo = hardwareMap.servo.get("leftvertservo");
        //this.rightHorizServo = hardwareMap.servo.get("righthorizservo");
        this.leftHorizServo = hardwareMap.servo.get("lefthorizservo");
        //this.rightHorizServoCenter = 0.4;
        //this.rightVertServoTop = 1.0;
        //this.rightVertServoBottom = 0.0;
        this.leftRedBlock = pixyCam.GetBiggestBlock(1);
        this.leftBlueBlock = pixyCam.GetBiggestBlock(2);
        telemetry.addData("Red: ", leftRedBlock.toString());
        telemetry.addData("Blue: ", leftBlueBlock.toString());
        telemetry.update();
        sleep(1000);
        leftVertServo.setPosition(1);
        sleep(1000);
        waitForStart();

        if (teamColorIsBlue == true) {
            if (this.leftRedBlock.x < this.leftBlueBlock.x) {  // if red is further left than blue
                leftHorizServo.setPosition(0.8); // then move the servo left
                sleep(1000);
                leftHorizServo.setPosition(0.5);
            } else {
                leftHorizServo.setPosition(0.2);
                sleep(1000);
                leftHorizServo.setPosition(0.5); // move the servo right -- also, if no values are found for x, it will go right
            }
            sleep(1000);
            leftVertServo.setPosition(0);
            sleep(1000);
        } else {
            if (leftRedBlock.x < leftBlueBlock.x) {  // if red is further left than blue
                leftHorizServo.setPosition(0.2); // then move the servo left
                sleep(1000);
                leftHorizServo.setPosition(0.5);
            } else {
                leftHorizServo.setPosition(0.8);
                sleep(1000);
                leftHorizServo.setPosition(0.5); // move the servo right -- also, if no values are found for x, it will go right
            }
            sleep(1000);
            leftVertServo.setPosition(0);
            sleep(1000);









            /*
            this.rightRedBlock = pixyCam2.GetBiggestBlock(1);
            this.rightBlueBlock = pixyCam2.GetBiggestBlock(2);
            telemetry.addData("Red :", rightRedBlock.toString());
            telemetry.addData("Blue :", rightBlueBlock.toString());
            telemetry.update();
            sleep(1000);
            rightVertServo.setPosition(rightVertServoBottom);
            sleep(1000);
            if (rightRedBlock.x < rightBlueBlock.x) {
                rightHorizServo.setPosition(rightHorizServoCenter + 0.2);
                sleep(1000);
                rightHorizServo.setPosition(rightHorizServoCenter);
            } else {
                rightHorizServo.setPosition(rightHorizServoCenter - 0.2);
                sleep(1000);
                rightHorizServo.setPosition(rightHorizServoCenter);
            }
            sleep(1000);
            rightVertServo.setPosition(rightVertServoTop);
            sleep(1000);
        }
    }*/

}
