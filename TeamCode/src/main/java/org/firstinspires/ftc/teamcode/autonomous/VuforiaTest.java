package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

/**
 * Created by EvanCoulson on 12/1/16.
 */

@Autonomous(name="VuforiaTest", group="Bot")
public class VuforiaTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AfIW5rj/////AAAAGaDrYjvjtkibrSYzQTjEFjJb+NGdODG1LJE2IVqxl0wdLW+9JZ3nIyQF2Hef7GlSLQxR/6SQ3pkFudWmzU48zdcBEYJ+HCwOH3vKFK8gJjuzrcc7nis7JrU+IMTONPctq+JTavtRk+LBhM5bxiFJhEO7CFnDqDDEFc5f720179XJOvZZA0nuCvIqwSslb+ybEVo/G8BDwH1FjGOaH/CxWaXGxVmGd4zISFBsMyrwopDI2T0pHdqvRBQ795QCuJFQjGQUtk9UU3hw/E8Z+oSC36CSWZPdpH3XkKtvSb9teM5xgomeEJ17MdV+XwTYL0iB/aRXZiXRczAtjrcederMUrNqqS0o7XvYS3eW1ViHfynl";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        waitForStart();

        beacons.activate();

        while(opModeIsActive()) {
            for (VuforiaTrackable beacon : beacons) {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacon.getListener()).getPose();

                if (pose != null) {
                    VectorF translation = pose.getTranslation();

                    telemetry.addData(beacon.getName() + " - Translation ", translation);

                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));

                    telemetry.addData(beacon.getName() + " - Degrees ", degreesToTurn);
                }
            }
            telemetry.update();
        }
    }
}
