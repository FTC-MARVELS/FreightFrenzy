package org.firstinspires.ftc.teamcode.Research;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Ultimate Goal game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "ImageRecCarl", group = "Concept")

@Disabled
public class Image_Rec_Carl extends LinearOpMode {
//    private static final String TFOD_MODEL_FILE = "C:\\Users\\aasiy\\StudioProjects\\FreightFrenzy\\TeamCode\\src\\main\\res\\raw\\red_carousel_model.tflite";
//    private static final String TFOD_MODEL_LABELS = "C:\\Users\\aasiy\\StudioProjects\\FreightFrenzy\\TeamCode\\src\\main\\res\\raw\\locationLabels.txt";
    private static final String TFOD_MODEL_ASSET = "red_carousel_model.tflite";
    //private static final String TFOD_MODEL_LABELS = "locationLabels.txt";
    private String[] labels = {
            "left",
            "middle",
            "right"
    };
    WebcamName webcamName = null;

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AV4DWg3/////AAABmQ6n5OzGwUYalTyB5uR46wxwDEO+PdJwv/KbeSDh+Frtn/FdN8pU2XERVvNAfgckS4NCo0L4YzGYhYUrTJfio23+zD2tl7J4NCF8IZ1hWtVmh1lx1p1+nv0cL/ZFOQb1k6O009NkKi/t+nLHTtZrswnCFC3Pasiw8IwoDPUjjnY08gU4IVRByn+DwgQL+1jrEo4/twIWe5UB65TztGdTXPOEcCzn5ZbjJqaCadFnYI0sMiWmMDoEfgFglWBoA55GOSuKrr1/fRuYXFuCqEqMBx7SzPYopF8vfM0qQ5h2EFEykKUSsre3OY3heH6ewwkpy7PRFE4MBaJoggv9dogm82m0dHu75KV2MbhRm75AkwB3";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // read the label map text files.
        //readLabels();

        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    tfod.activate();
                    // The TensorFlow software will scale the input images from the camera to a lower resolution.
                    // This can result in lower detection accuracy at longer distances (> 55cm or 22").
                    // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
                    // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
                    // should be set to the value of the images used to create the TensorFlow Object Detection model
                    // (typically 16/9).
                    //tfod.setZoom(2.5, 16.0/9.0);
                    telemetry.addData(">", "Activated");
                    sleep(2000);
                    telemetry.update();

                }

                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());

                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        String objectPosition = "0";
                        for (Recognition recognition : updatedRecognitions) {
//                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
//                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
//                                    recognition.getLeft(), recognition.getTop());
//                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
//                                    recognition.getRight(), recognition.getBottom());
//                        }
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            i++;

                            // check label to see if the camera now sees a Duck
                            if (recognition.getLabel().equals("left")) {
                                objectPosition = "1";
                                telemetry.addData("Object Detected", "Left");
                            } else if (recognition.getLabel().equals("middle")) {
                                objectPosition = "2";
                                telemetry.addData("Object Detected", "Middle");
                            } else {
                                objectPosition = "3";
                            }
                        }
                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    /*private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
      /*  VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }*/

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

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        telemetry.addData(">", "Loading TFOD");
        telemetry.update();
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.1f;
        //tfodParameters.isModelTensorFlow2 = true;
        //tfodParameters.inputSize = 320;
        telemetry.addData(">", "Setting TFOD Params");
        telemetry.update();
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        telemetry.addData(">", "TFOD Initialized %s" , tfod);
        telemetry.update();
        //if(labels != null) {
            //tfod.loadModelFromFile(TFOD_MODEL_FILE, labels);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, labels);
            telemetry.addData(">", "Loaded assets from model");
            telemetry.update();
            sleep(1000);

        //}
        telemetry.addData(">", "After loading assets %s", tfod.getUpdatedRecognitions());
        telemetry.update();
        sleep(2000);

    }

    /**
     * Read the labels for the object detection model from a file.
     */
    /*private void readLabels() {
        ArrayList<String> labelList = new ArrayList<>();

        // try to read in the the labels.
        try (BufferedReader br = new BufferedReader(new FileReader(TFOD_MODEL_LABELS))) {
            int index = 0;
            while (br.ready()) {
                // skip the first row of the labelmap.txt file.
                // if you look at the TFOD Android example project (https://github.com/tensorflow/examples/tree/master/lite/examples/object_detection/android)
                // you will see that the labels for the inference model are actually extracted (as metadata) from the .tflite model file
                // instead of from the labelmap.txt file. if you build and run that example project, you'll see that
                // the label list begins with the label "person" and does not include the first line of the labelmap.txt file ("???").
                // i suspect that the first line of the labelmap.txt file might be reserved for some future metadata schema
                // (or that the generated label
                 map file is incorrect).
                // for now, skip the first line of the label map text file so that your label list is in sync with the embedded label list in the .tflite model.
                if(index == 0) {
                    // skip first line.
                    br.readLine();
                } else {
                    labelList.add(br.readLine());
                }
                index++;
            }
        } catch (Exception e) {
            telemetry.addData("Exception", e.getLocalizedMessage());
            telemetry.update();
        }

        if (labelList.size() > 0) {
            labels = getStringArray(labelList);
            RobotLog.vv("readLabels()", "%d labels read.", labels.length);
            for (String label : labels) {
                RobotLog.vv("readLabels()", " " + label);
            }
        } else {
            RobotLog.vv("readLabels()", "No labels read!");
        }
    }*/

    // Function to convert ArrayList<String> to String[]
    private String[] getStringArray(ArrayList<String> arr)
    {
        // declaration and initialize String Array
        String str[] = new String[arr.size()];

        // Convert ArrayList to object array
        Object[] objArr = arr.toArray();

        // Iterating and converting to String
        int i = 0;
        for (Object obj : objArr) {
            str[i++] = (String)obj;
        }

        return str;
    }
}
