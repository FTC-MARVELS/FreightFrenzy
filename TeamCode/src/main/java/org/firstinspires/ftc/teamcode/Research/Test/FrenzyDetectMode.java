package org.firstinspires.ftc.teamcode.Research.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Research.Test.FrenzyDetector;

@TeleOp(name = "Frenzy Rec Thread", group = "Robot19564")
public class FrenzyDetectMode extends LinearOpMode {
    WebcamName webcamName = null;
    private static final String VUFORIA_KEY =
            "AV4DWg3/////AAABmQ6n5OzGwUYalTyB5uR46wxwDEO+PdJwv/KbeSDh+Frtn/FdN8pU2XERVvNAfgckS4NCo0L4YzGYhYUrTJfio23+zD2tl7J4NCF8IZ1hWtVmh1lx1p1+nv0cL/ZFOQb1k6O009NkKi/t+nLHTtZrswnCFC3Pasiw8IwoDPUjjnY08gU4IVRByn+DwgQL+1jrEo4/twIWe5UB65TztGdTXPOEcCzn5ZbjJqaCadFnYI0sMiWmMDoEfgFglWBoA55GOSuKrr1/fRuYXFuCqEqMBx7SzPYopF8vfM0qQ5h2EFEykKUSsre3OY3heH6ewwkpy7PRFE4MBaJoggv9dogm82m0dHu75KV2MbhRm75AkwB3";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;
    private FrenzyDetector frenzyDetector = null;
    @Override
    public void runOpMode() {
        try {
            try {
               // initVuforia();

                //FtcDashboard.getInstance().startCameraStream(vuforia, 0);
                frenzyDetector = new FrenzyDetector(this.hardwareMap, "Red", this, telemetry);
                Thread detectThread = new Thread(frenzyDetector);
                detectThread.start();
                telemetry.update();
            } catch (Exception ex) {
                telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
                telemetry.update();
                sleep(5000);
                return;
            }

            // Wait for the game to start (driver presses PLAY)
            telemetry.update();
            waitForStart();

            String zone = null;

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
                zone = frenzyDetector.returnZone();
                telemetry.addData("Detected Zone", zone);
            }
            frenzyDetector.stopDetection();
        } catch (Exception ex) {
            telemetry.addData("Init Error", ex.getMessage());
            telemetry.update();
        } finally {
            if (frenzyDetector != null) {
                frenzyDetector.stopDetection();
            }
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

        vparameters.vuforiaLicenseKey = VUFORIA_KEY;

        // We also indicate which camera on the RC we wish to use
        vparameters.cameraName = webcamName;

        // Make sure extended tracking is disabled for this example.
        vparameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(vparameters);

        telemetry.addData(">", "Vuforia %s", vuforia);
        telemetry.update();
        sleep(2000);

    }


}
