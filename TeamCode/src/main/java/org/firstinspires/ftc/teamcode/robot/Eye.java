package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Steven Abbott on 9/27/2017.
 */

public class Eye {

    HardwareMap hwMap;
    OpenGLMatrix lastLocation;
    VuforiaLocalizer vuforia;
    int cameraMonitorViewId;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;
    float mmPerInch;
    //List<VuforiaTrackable> allTrackables;
    public VectorF trans;
    public Orientation rot;
    VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

    public void init(HardwareMap hwMap) {
        this.hwMap = hwMap;
        this.lastLocation = null;
        this.cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        this.parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        this.parameters.vuforiaLicenseKey = "AfIW5rj/////AAAAGaDrYjvjtkibrSYzQTjEFjJb+NGdODG1LJE2IVqxl0wdLW+9JZ3nIyQF2Hef7GlSLQxR/6SQ3pkFudWmzU48zdcBEYJ+HCwOH3vKFK8gJjuzrcc7nis7JrU+IMTONPctq+JTavtRk+LBhM5bxiFJhEO7CFnDqDDEFc5f720179XJOvZZA0nuCvIqwSslb+ybEVo/G8BDwH1FjGOaH/CxWaXGxVmGd4zISFBsMyrwopDI2T0pHdqvRBQ795QCuJFQjGQUtk9UU3hw/E8Z+oSC36CSWZPdpH3XkKtvSb9teM5xgomeEJ17MdV+XwTYL0iB/aRXZiXRczAtjrcederMUrNqqS0o7XvYS3eW1ViHfynl";
        this.parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        this.relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        this.mmPerInch = 25.4f;
        this.trans = null;
        this.rot = null;
        this.relicTemplate = relicTrackables.get(0);
        this.relicTemplate.setName("relicVuMarkTemplate");
        this.relicTrackables.activate();

        //this.allTrackables = new ArrayList<VuforiaTrackable>();
        //this.allTrackables.addAll(relicTrackables);
    }

    /*public void look() {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
            if (pose != null) {
                this.trans = pose.getTranslation();
                this.rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            }
        }
    }*/

    public int look() {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
            if (pose != null) {
                this.trans = pose.getTranslation();
                this.rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            }
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                return 0;
                //return "left";
            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                return 2;
                //return "right";
            } else {
                return 1;
                //return "center";
            }
        }
        return -1;
        //return "undetermined";
    }

    public int find() {
        int times = 0;
        this.rot = null;
        this.trans = null;
        while (this.rot == null) {
            this.look();
            times++;
        }
        return times;
    }

    public int find(int times) {
        int cycles = 0;
        this.rot = null;
        this.trans = null;
        while (cycles < times) {
            this.look();
            cycles++;
        }
        return cycles;
    }

    public void telemetry(Telemetry telemetry) {
        if (rot != null) {
            double tX = trans.get(0);
            telemetry.addLine("X trans:" + this.trans.get(0));
            double tY = trans.get(1);
            telemetry.addLine("Y trans:" + this.trans.get(1));
            double tZ = trans.get(2);
            telemetry.addLine("Z trans:" + this.trans.get(2));

            // Extract the rotational components of the target relative to the robot
            double rX = rot.firstAngle;
            telemetry.addLine("X rotation:" + this.rot.firstAngle);
            double rY = rot.secondAngle;
            telemetry.addLine("Y rotation:" + this.rot.secondAngle);
            double rZ = rot.thirdAngle;
            telemetry.addLine("Z rotation:" + this.rot.thirdAngle);
        } else {
            telemetry.addLine("Not visible");
        }
        telemetry.update();
    }
}
