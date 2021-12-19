package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Scanner;
import org.firstinspires.ftc.teamcode.tfrec.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;

import java.util.List;

@Autonomous(name = "MAS_Auto_TestImage")
public class MAS_Auto_TestImage extends LinearOpMode {

    private Detector tfDetector = null;
    private ElapsedTime runtime = new ElapsedTime();

    private static String MODEL_FILE_NAME = "bcr.tflite";
    private static String LABEL_FILE_NAME = "labels_bcr.txt";
    private static Classifier.Model MODEl_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;

    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {

        double speed = 0.75;
        double rotationSpeed = 0.2;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Scanner scanner = new Scanner();
        Claw claw = new Claw(hardwareMap);
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        mecanum.rightErrorAdjustment = 0.5;//1;

        try {
            tfDetector = new Detector(MODEl_TYPE, MODEL_FILE_NAME, LABEL_FILE_NAME, hardwareMap.appContext, telemetry);
            tfDetector.parent = this;
            tfDetector.activate();
        } catch (Exception ex) {
            telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
            sleep(3000);
            return;
        }

        waitForStart();
        // runtime.reset();

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
        }

        telemetry.addData("FINAL POSITION", position);
        telemetry.update();
        sleep(2000);
    //labels are reversed here
      /*  if(position == 0 || position == 3 || position == 9) {
            mecanum.moveArm(2,0);
            sleep(3000);
            claw.moveBucket(0.0);
            sleep(2000);
        } else if(position == 1) {
            mecanum.moveArm(1,0);
            sleep(3000);
            claw.moveBucket(0.0);
            sleep(2000);
        } else if(position == 2) {
            claw.moveBucket(0.2);
            sleep(200);
        }*/

        /*mecanum.moveArm(2,0);
        sleep(3000);
        claw.moveBucket(0.0);
        sleep(2000);

        mecanum.moveArm(0,2);
        sleep(3000);
        claw.moveBucket(0.0);
        sleep(2000);

        mecanum.moveArm(1,0);
        sleep(3000);
        claw.moveBucket(0.0);
        sleep(2000);


        mecanum.moveArm(0,1);
        sleep(3000);
        claw.moveBucket(0.0);
        sleep(2000);
*/

    }


}

