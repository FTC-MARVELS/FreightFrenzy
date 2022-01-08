package org.firstinspires.ftc.teamcode.RobotObjects.MAS;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;

import java.util.List;

public class Scanner {

    // Declare OpMode members.
    private Detector tfDetector = null;
    private ElapsedTime runtime = new ElapsedTime();
    private static int MAX_POSITION = 2;
    private Telemetry telemetry;
    //private static String MODEL_FILE_NAME = "model_unquant_dec5.tflite";
    //private static String LABEL_FILE_NAME = "labels.txt";
    //private static Classifier.Model MODEl_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;

    /*
    Not Needed
     public void initialize(HardwareMap hardwareMap, LinearOpMode parent , Telemetry inputTelemetry) throws Exception {

        tfDetector = new Detector(MODEl_TYPE, MODEL_FILE_NAME, LABEL_FILE_NAME, hardwareMap.appContext, inputTelemetry);
        tfDetector.parent = parent;
        tfDetector.activate();
        telemetry = inputTelemetry;
    }*/

    public int scan(HardwareMap hardwareMap, Detector detector, Telemetry inputTelemetry) throws InterruptedException, Exception {

        String positionStr = "";
        int position = MAX_POSITION;
        telemetry = inputTelemetry;
        Float previousConfidence = 0.0f;
        List<Classifier.Recognition> results = detector.getLastResults();
        /*telemetry.addData("TEST STATEMENT", detector);
        telemetry.update();
        sleep(1000);
        telemetry.addData("TEST STATEMENT",results);
        telemetry.update();
        sleep(1000);*/
        if (results == null || results.size() == 0){
            telemetry.addData("Info", "No results");
            telemetry.update();
            //sleep(500);
            positionStr = "NA";
            position = 3; // Added to test no results
        }
        else {
            for (Classifier.Recognition r : results) {
                String item = String.format("%s: %.2f", r.getTitle(), r.getConfidence());
                telemetry.addData("Found", item);
                telemetry.update();
                sleep(200);
                /*telemetry.addData("Found confidence level", r.getConfidence());
                telemetry.update();
                sleep(1000);*/
                if(r.getConfidence()>0.5) {
                    if(r.getConfidence() > previousConfidence) {
                        positionStr = r.getTitle();
                        position = Integer.parseInt(positionStr.substring(0,1));
                    }
                    /*telemetry.addData("Found position before break", positionStr);
                    telemetry.update();
                    sleep(1000);*/
                    //break;
                } /*else {
                    positionStr = "NA";
                    position = MAX_POSITION;
                    /*telemetry.addData("DID NOT Find position before break", positionStr);
                    telemetry.update();
                    sleep(1000);
                }*/
                previousConfidence = r.getConfidence();

            }
        }

        return position;
    }

}
