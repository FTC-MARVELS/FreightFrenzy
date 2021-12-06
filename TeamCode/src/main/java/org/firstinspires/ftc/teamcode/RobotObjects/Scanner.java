package org.firstinspires.ftc.teamcode.RobotObjects;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.R;

public class Scanner {
    //Configuration used: 6wheelConfig
    public static final String VUFORIA_LICENSE_KEY = "AV4DWg3/////AAABmQ6n5OzGwUYalTyB5uR46wxwDEO+PdJwv/KbeSDh+Frtn/FdN8pU2XERVvNAfgckS4NCo0L4YzGYhYUrTJfio23+zD2tl7J4NCF8IZ1hWtVmh1lx1p1+nv0cL/ZFOQb1k6O009NkKi/t+nLHTtZrswnCFC3Pasiw8IwoDPUjjnY08gU4IVRByn+DwgQL+1jrEo4/twIWe5UB65TztGdTXPOEcCzn5ZbjJqaCadFnYI0sMiWmMDoEfgFglWBoA55GOSuKrr1/fRuYXFuCqEqMBx7SzPYopF8vfM0qQ5h2EFEykKUSsre3OY3heH6ewwkpy7PRFE4MBaJoggv9dogm82m0dHu75KV2MbhRm75AkwB3";
    WebcamName webcamName = null;
    VuforiaLocalizer vuforia = null;

    public Scanner(HardwareMap hardwareMap) {
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters vparameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        vparameters.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;

        // We also indicate which camera on the RC we wish to use
        vparameters.cameraName = webcamName;

        // Make sure extended tracking is disabled for this example.
        vparameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(vparameters);
    }

    public void initialize() {
        VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        vuforiaParams.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
        vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

    }


}
