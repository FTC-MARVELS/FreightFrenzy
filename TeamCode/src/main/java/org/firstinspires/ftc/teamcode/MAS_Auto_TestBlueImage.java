package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Detector;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Scanner;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;

@Autonomous(name = "MAS_Auto_TestBlueImage")
public class MAS_Auto_TestBlueImage extends LinearOpMode {

    private Detector tfDetector = null;
    private ElapsedTime runtime = new ElapsedTime();

    private static String MODEL_FILE_NAME = "bluecarousel_0108_1.tflite";
            //""testmodeldec30.tflite";
    private static String LABEL_FILE_NAME = "bluecarousel_0108_1.txt";
    private static Classifier.Model MODEl_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;

    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        Scanner scanner = new Scanner();

        try {
            tfDetector = new Detector(MODEl_TYPE, MODEL_FILE_NAME, LABEL_FILE_NAME, hardwareMap.appContext, telemetry);
            //tfDetector.parent = this;
            tfDetector.activate();
        } catch (Exception ex) {
            telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
            sleep(3000);
            return;
        }

        waitForStart();


        int position = 9;
        try {
            position = scanner.scan(hardwareMap, tfDetector, telemetry);
            telemetry.addData("Found in class", position);
            telemetry.update();
            sleep(2000);

            if (position == 3) {
                position = scanner.scan(hardwareMap, tfDetector, telemetry);
                sleep(2000);
                telemetry.addData("Found again in class", position);
                telemetry.update();
            }
        } catch (Exception e) {
            e.printStackTrace();
            telemetry.addData("Error", String.format("Unable to scan image. %s", e.getMessage()));
            position = 2;
            telemetry.addData("Found in class Exception ", position);
            telemetry.update();
        } finally {
             tfDetector.stopProcessing();
        }

        telemetry.addData("FINAL POSITION", position);
        telemetry.update();
        sleep(2000);


    }

}

