package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by Steven Abbott on 9/15/2017.
 */

@Autonomous(name="EducayshunOpMode", group="Bot")
public class EducayshunOpMode extends BaseOpMode {
    private int zone;
    private int inchesToReferenceBoxNonAudience;
    private int inchesToReferenceBoxAudience;
    private double powerSetting1;
    private double powerSetting2;
    private int cryptoApproachInches;
    private ConfigParser config;
    private int b;

    public EducayshunOpMode() {
        super("Autonomous");
        this.config = new ConfigParser("Autonomous.omc");
        this.inchesToReferenceBoxNonAudience = config.getInt("InchesToReferenceBoxNonAudience"); // the length of the first drive forward, ending at the refernce box
        this.inchesToReferenceBoxAudience = config.getInt("InchesToReferenceBoxAudience"); // the length of the first drive forward, ending at the refernce box
        this.zone = config.getInt("zone"); // sets which starting zone you are in
        this.powerSetting1 = config.getDouble("powerSetting1"); // the power setting for driving off the stone
        this.powerSetting2 = config.getDouble("powerSetting2"); // the power setting for everything other than getting off the stone
        this.cryptoApproachInches = config.getInt("cryptoApproachInches"); // the length of the drive forward to place the cube
        this.b = config.getInt("b");
    }

    public void runOpMode() {
        initSystems();
        elevator.goToZero(telemetry);
        double startHeading = imuSystem.getHeading();

        ////
        waitForStart();
        ////


        grabBlock();
        vuforiaCryptoBox(zone);


        ////
        stop();
        ////
    }

    public void vuforiaCryptoBox(int zone) {
        double inchesPerBox = b;
        int boxNumber = eye.look(); // 0 = left   1 = center   2 = right
        telemetry.addLine("DETERMINED THE PICTURE!!!! YAY. Its picture number " + boxNumber);
        telemetry.update();
        sleep(50);

        // zone 0 is blue non-audience      zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0) { // blue non-audience
            driveSystem.driveToPositionInches(-42, powerSetting1);
            returnToHeading(-90, powerSetting2);
            driveSystem.driveToPositionInches(-(inchesPerBox * boxNumber + inchesToReferenceBoxNonAudience), powerSetting2); // 25
            driveSystem.turn(60, powerSetting2);
            driveSystem.driveToPositionInches(-cryptoApproachInches, powerSetting2);
            placeBlock();
            driveSystem.driveToPositionInches(5, powerSetting2);
        } else if (zone == 1) { // blue audience
            driveSystem.driveToPositionInches(-(inchesPerBox * boxNumber + inchesToReferenceBoxAudience), powerSetting1);
            returnToHeading(60, powerSetting2);
            driveSystem.driveToPositionInches(-cryptoApproachInches, powerSetting2);
            placeBlock();
            driveSystem.driveToPositionInches(5, powerSetting2);
        } else if (zone == 2) { // red non-audience
            driveSystem.driveToPositionInches(-40, powerSetting1);
            returnToHeading(90, powerSetting2);
            driveSystem.driveToPositionInches(-((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxNonAudience), powerSetting2); // 25
            driveSystem.turn(-60, powerSetting2);
            driveSystem.driveToPositionInches(-cryptoApproachInches, powerSetting2);
            placeBlock();
            driveSystem.driveToPositionInches(5, powerSetting2);
        } else if (zone == 3) { // red audience
            driveSystem.driveToPositionInches(-((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxAudience), powerSetting1);
            returnToHeading(-60, 1);
            driveSystem.driveToPositionInches(-cryptoApproachInches, powerSetting2);
            placeBlock();
            driveSystem.driveToPositionInches(5, powerSetting2);
        } else if (zone == 4) { //this is a test zone to test inches per box
            driveSystem.driveToPositionInches(-inchesPerBox, powerSetting2);
            sleep(2000);
            driveSystem.driveToPositionInches(-inchesPerBox, powerSetting2);
        }
    }

    public enum StartPositions {
        BLUE_NON_AUDIENCE,
        BLUE_AUDIENCE,
        RED_NON_AUDIENCE,
        RED_AUDIENCE,
    }


    public void returnToHeading(double heading, double power) {
        double correctionNeeded = heading - imuSystem.getHeading();
        telemetry.addLine("returning to " + heading + " by adjusting by " + correctionNeeded + "currently at " + imuSystem.getHeading());
        telemetry.update();
        driveSystem.turn(correctionNeeded, power);
    }

    public void placeBlock() {
        elevator.goToZero(telemetry);
        claw.goToReleasePosition();
        sleep(1000);
    }

    public void grabBlock() {
        claw.goToLoadPosition();
        sleep(500);
        elevator.goToUnloadBlock3();
        sleep(1000);
    }

    /*public void correctBoxLeftApproach(int boxNumber) {
        if (boxNumber == 0) {
            turn(116 , 1);
        } else if (boxNumber == 1) {
            turn(90, 1);
        } else {
            turn(63, 1);
        }
    }

    public void correctBoxRightApproach(int boxNumber) {
        if (boxNumber == 0) {
            turn(-65 , 1);
        } else if (boxNumber == 1) {
            turn(-90, 1);
        } else {
            turn(-117, 1);
        }
    }

    /*
    driveToPositionInches(-58, 0.6);
            //telemetry.addLine("We're at the first cryptobox!");
            //telemetry.update();
            //sleep(2000);
            //correctBoxLeftApproach(picNumber);

            if (picNumber == 0) {
                turn(117 , 1);
            } else if (picNumber == 1) {
                turn(90, 1);
            } else {
                turn(65, 1);
            }
            //turn(90, 1);
            //sleep(2000);
            driveToPositionInches(-14, 1);

            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(1000);
            driveToPositionInches(5, 1);
     */

    /*public void correctBoxLeftApproach(int boxNumber) {
        int inchesPerBox = 15;
        telemetry.addLine("driving to box: " + boxNumber + " or inches: " + (-inchesPerBox * boxNumber));
        telemetry.update();
        driveToPositionInches((-inchesPerBox * boxNumber), 0.75);
    }

    public void correctBoxRightApproach(int boxNumber) {
        int inchesPerBox = 11;
        driveToPositionInches((33 - (boxNumber * -inchesPerBox)), 1);
    }*/


    public void cryptoBox(int zone) {
        // pic 0 is left     pic 1 is right      pic 2 is center
        // zone 0 is blue non-audience     zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0) {
            driveSystem.driveToPositionInches(-40, 1);
            driveSystem.turn(-90, 1);
            driveSystem.driveToPositionInches(-18, 1);
            driveSystem.turn(90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(1000);
            driveSystem.driveToPositionInches(-20, 1);
        } else if (zone == 1) {
            driveSystem.driveToPositionInches(-50, 1);
            driveSystem.turn(90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(2000);
            driveSystem.driveToPositionInches(-20, 1);
        } else if (zone == 2) {
            driveSystem.driveToPositionInches(40, 1);
            driveSystem.turn(-90, 1);
            driveSystem.driveToPositionInches(-18, 1);
            driveSystem.turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(1000);
            driveSystem.driveToPositionInches(-20, 1);
        } else {
            driveSystem.driveToPositionInches(-50, 1);
            driveSystem.turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(2000);
            driveSystem.driveToPositionInches(-20, 1);
        }
        claw.goToReleasePosition();
        driveSystem.driveToPositionInches(5, 1);
    }

    public void vuDrive(int x, int y) {
        eye.find();
    }

    public void fancyDrive(int x, int y, double power) {
        int error = 50;
        int rotError = 3;
        eye.look();
        double xp = 0.003; // Init values for xp, yp, rp, xDiff, and Ydiff are arbitrary but are meant to help with debugging
        double yp = 0.004; // while being low enough to not power a malfunctioning robot.
        double rp = 0.005;
        double xDiff = eye.trans.get(1) - x;
        double yDiff = eye.trans.get(2) - y;
        double rotDiff = eye.rot.secondAngle;
        while (xDiff > x + error || xDiff < x - error || yDiff > y + error || yDiff < y - error || rp > rotError || rp < rotError) {
            eye.look();
            rotDiff = eye.rot.secondAngle;
            xDiff = eye.trans.get(1) - x;
            yDiff = eye.trans.get(2) - y;
            if ((rotDiff * (error / rotError)) > xDiff || (rotDiff * (error / rotError)) > yDiff) {
                rp = power;
                xp = power / (rotDiff * (error / rotError) * xDiff);
                yp = power / (rotDiff * (error / rotError) * yDiff);
            } else if (xDiff > yDiff) {
                xp = power;
                yp = power / xDiff * yDiff;
                rp = power / xDiff * (rotDiff * (error / rotError));
            } else {
                yp = power;
                xp = power / yDiff * xDiff;
                rp = power / yDiff * (rotDiff * (error / rotError));
            }
            driveSystem.mecanumDrive((float)xp, (float)yp, (float)rp, 0, false);
            sleep(50);
        }
    }


    /*public void testServo() {
        //armLeft.setPosition(position);
        //armRight.setPosition(position);


    }
    public void driveBox() {
        int echsdee = 0;
        while (echsdee < 4){
            driveToPositionRevs(3, DRIVE_POWER);
            turn(90, DRIVE_POWER);
            echsdee++;
        }

    }

    public void driveBox2() {
        driveSystem.mecanumDriveXY(0, 2);
        sleep(2000);
        driveSystem.mecanumDriveXY(2, 0);
        sleep(2000);
        driveSystem.mecanumDriveXY(0, -2);
        sleep(2000);
        driveSystem.mecanumDriveXY(-2, 0);
        sleep(2000);
        driveSystem.mecanumDriveXY(0, 0);
    }*/

    public void learnIMU() {
        while (true){
            telemetry.addLine("Heading:               " + imuSystem.getHeading());
            /*telemetry.addLine("X plane Slope:         " + imuSystem.getAngleOnPlaneX());
            telemetry.addLine("Y plane slope:         " + imuSystem.getAngleOnPlaneY());
            telemetry.addLine("Linear X acceleration: " + imuSystem.getLinearAccelerationX());
            telemetry.addLine("Linear Y acceleration: " + imuSystem.getLinearAccelerationY());
            telemetry.addLine("Linear Z acceleration: " + imuSystem.getLinearAceelerationZ());
            telemetry.addLine("x distance traveled:   " + imuSystem.getPosition().x / 1000);
            telemetry.addLine("y distance traveled:   " + imuSystem.getPosition().y / 1000);
            telemetry.addLine("z distance traveled:   " + imuSystem.getPosition().z / 1000);*/
            telemetry.update();
            sleep(1);
        }
    }

    /*public void balance() {
        double power  = 0.35;
        double BALANCED_X_PLANE = 159.9375;
        double BALANCED_Y_PLANE = 0.0625;
        double MARGIN_OF_ERROR = 3;
        int CORRECTION = 150;
        while (CORRECTION == 150) {
            if (imuSystem.getAngleOnPlaneX() > BALANCED_X_PLANE + MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(power, 0);
            } else if (imuSystem.getAngleOnPlaneX() < BALANCED_X_PLANE - MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(-power, 0);
            } else {
                driveSystem.mecanumDriveXY(0, 0);
            }
            sleep(CORRECTION);
            driveSystem.mecanumDriveXY(0, 0);
            if (imuSystem.getAngleOnPlaneY() < BALANCED_Y_PLANE - MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(0, power);
            } else if (imuSystem.getAngleOnPlaneY() > BALANCED_Y_PLANE + MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(0, -power);
            } else {
                driveSystem.mecanumDriveXY(0, 0);
            }
            sleep(CORRECTION);
            driveSystem.mecanumDriveXY(0, 0);
        }
    }

    void driveToPositionAccel(double revolutions, double maxPower)
    {
        double minPower = 0.1;

        this.driveSystem.setTargetPositionRevs(revolutions);
            double targetInches = driveSystem.ticksToInches(driveSystem.revolutionsToTicks(revolutions));
            telemetry.addLine("Target position " + revolutions + " revolutions or " + targetInches + " inches.");
            telemetry.update();
        /*
            Create a Ramp that will map a distance in revolutions between 0.01 and 1.0
            onto power values between minPower and maxPower.
            When the robot is greater than 1.0 revolution from the target the power
            will be set to maxPower, but when it gets within 1.0 revolutions, the power
            will be ramped down to minPower*/




    /*Position position1 = imuSystem.getPosition();
        Ramp ramp = new ExponentialRamp(driveSystem.revolutionsToTicks(0.01), minPower,
                driveSystem.revolutionsToTicks(1.0), maxPower);

        // Wait until they are done
        driveSystem.setPower(maxPower);
        while (this.driveSystem.anyMotorsBusy())
        {
            telemetry.update();

            this.idle();

            this.driveSystem.adjustPower(ramp);
        }

        // Now that we've arrived, kill the motors so they don't just sit there buzzing
        driveSystem.setPower(0);
        //Position position2 = imuSystem.getPosition();
        double imuDistanceReached = Math.hypot(position1.x, position1.y);
        telemetry.addLine("x distance traveled: ");

        // Always leave the screen looking pretty
        telemetry.update();
    }

    public VectorF anglesFromTarget(VuforiaTrackableDefaultListener image) {
        float[] data = image.getRawPose().getData();
        float[][] rotation = {
                { data[0], data[1]},
                { data[4], data[5], data[6] },
                { data[8], data[9], data[10] }
        };
        double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
        double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
        double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);
        return new VectorF((float)thetaX, (float)thetaY, (float)thetaZ);
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    public void driveXY(int x, int y, MecanumDriveSystem driveSystem, double power){
        int error = 100;
        eye.look();
        while (eye.trans.get(1) > x + error || eye.trans.get(1) < x - error || eye.trans.get(2) > y + error || eye.trans.get(2) < y - error) {
            eye.look();
            if (eye.trans.get(1) > x + error) {//z toward and away from the picture
                //driveToPositionMM(eye.trans.get(2) - x, 0, power);
                telemetry.addLine("Too far right");
                telemetry.update();
                driveSystem.mecanumDriveXY(-1, 0);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else if (eye.trans.get(1) < x - error) {
                //driveToPositionMM(x - eye.trans.get(2), 0, power);
                telemetry.addLine("Too far left");
                telemetry.update();
                driveSystem.mecanumDriveXY(1, 0);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else {
                telemetry.addLine("Good on left right");
                telemetry.update();
                driveSystem.mecanumDriveXY(0, 0);
            }
            if (eye.trans.get(2) > y + error) {
                //driveToPositionMM(0, eye.trans.get(1) - y, power);
                telemetry.addLine("Too far forward");
                telemetry.update();
                driveSystem.mecanumDriveXY(0, 1);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else if (eye.trans.get(2) < y - error) {
                //driveToPositionMM(0, y - eye.trans.get(1), power);
                telemetry.addLine("Too far back");
                telemetry.update();
                driveSystem.mecanumDriveXY(0, -1);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else {
                telemetry.addLine("Good on forward-back");
                telemetry.update();
                driveSystem.mecanumDriveXY(0,0);
            }
        }
        telemetry.addLine("YAAAAAAYYYYY!!!!!");
        telemetry.update();
        return;
    }*/
}
