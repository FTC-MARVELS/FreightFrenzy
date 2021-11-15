package org.firstinspires.ftc.teamcode.Research;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.R;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous
public class VuforiaStreamOpModeSample extends LinearOpMode {

    public static final String VUFORIA_LICENSE_KEY = "AV4DWg3/////AAABmQ6n5OzGwUYalTyB5uR46wxwDEO+PdJwv/KbeSDh+Frtn/FdN8pU2XERVvNAfgckS4NCo0L4YzGYhYUrTJfio23+zD2tl7J4NCF8IZ1hWtVmh1lx1p1+nv0cL/ZFOQb1k6O009NkKi/t+nLHTtZrswnCFC3Pasiw8IwoDPUjjnY08gU4IVRByn+DwgQL+1jrEo4/twIWe5UB65TztGdTXPOEcCzn5ZbjJqaCadFnYI0sMiWmMDoEfgFglWBoA55GOSuKrr1/fRuYXFuCqEqMBx7SzPYopF8vfM0qQ5h2EFEykKUSsre3OY3heH6ewwkpy7PRFE4MBaJoggv9dogm82m0dHu75KV2MbhRm75AkwB3";
    WebcamName webcamName = null;
    VuforiaLocalizer vuforia = null;

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        vuforiaParams.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
        vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        initVuforia();
        //VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaParams);
        //VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(vuforiaParams);
        // vuforia.loadTrackablesFromFile("F:\\A.jpg");
        FtcDashboard.getInstance().startCameraStream(vuforia, 0);
        waitForStart();

        // send telemetry to Driver Station using standard SDK interface
        telemetry.addData("Mode", "waiting for start");
        telemetry.update();

        while (opModeIsActive()) {

        }
    }


    private void initVuforia() {
        // Retrieve the camera we are to use
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters vparameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // VuforiaLocalizer.Parameters vparameters = new VuforiaLocalizer.Parameters();

        vparameters.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;

        // We also indicate which camera on the RC we wish to use
        vparameters.cameraName = webcamName;

        // Make sure extended tracking is disabled for this example.
        vparameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(vparameters);
    }
}