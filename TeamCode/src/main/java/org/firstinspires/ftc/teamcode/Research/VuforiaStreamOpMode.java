package org.firstinspires.ftc.teamcode.Research;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.teamcode.R;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.RobotObjects.Mecanum_Wheels;

@Autonomous
public class VuforiaStreamOpMode extends LinearOpMode {

    public static final String VUFORIA_LICENSE_KEY = "AUbzCwP/////AAABmbf8lVGV50gKh7hv59eI3JQCXgBCGxkIDkp1CU2L7prKbjkbN08TcMn1OwKUNp2/2lSPSizaWIxYJlJ8iBcqvsibP5c+irEhGlqHtjYTR3TVLMVyOr4y1arszOyaPDztZUBUr5IMbfOXm4iC8MG2sDFvWQGv8vIIuthnJ19oKlXYRaVmt5dwBo2StzpMe0g9xEIh3SaB+xfb+b2W3yom6Z5jNd9NfVTToeP6vR1ubW/h9+OGu82ybfV0S0pNuRj81qwhYhhAvwaFIFoDR1vG3KVY0QGPKOjP+lyuXToCc1MFKA8SAhk+9BssevFLw5bwO9rspSdw/VtEFKMF70eiS7nYgH2dJyAcp9wQHdytf6E5";

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        vuforiaParams.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
        vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaParams);

        FtcDashboard.getInstance().startCameraStream(vuforia,0);
        waitForStart();

        while (opModeIsActive()) {
            Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
            mecanum.moveForward();
        }
    }

}
