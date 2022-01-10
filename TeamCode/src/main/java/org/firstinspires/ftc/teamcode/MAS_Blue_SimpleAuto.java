package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
//import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;
//import org.firstinspires.ftc.teamcode.RobotObjects.Scanner;

/*
    Near Carousel
    =============
    1. Spin Carousel
    2. Scan
    3. Drop preload
    4. Go to Warehouse and Park

    //forward
    mecanum.encoderDrive(speed,12,12,12,12,12,12, 1.0);
    //backward
    mecanum.encoderDrive(speed,-12,-12,-12,-12,-12,-12, 1.0);
    //left
    mecanum.encoderDrive(speed,-12,0,12,12,0,0,12, 1.0);
    //left turn-12, 1.0);
    //right
    mecanum.encoderDrive(speed,12,0,-12,-12,
    mecanum.encoderDrive(speed,-12,0,-12,12,0,12, 1.0);
    //right turn
    mecanum.encoderDrive(speed,12,0,12,-12,0,-12, 1.0);
    //contract
    mecanum.encoderDrive(speed,3.15,0,-3.15,3.1,0,-3.1, 1.0);
    //expand
    mecanum.encoderDrive(speed,-3.15,0,3.15,-3.1,0,3.1, 1.0);

     */


@Autonomous(name = "MAS_Blue_SimpleAuto")
@Disabled

//@TeleOp(name="MAS_Blue_SimpleAuto")
public class MAS_Blue_SimpleAuto extends LinearOpMode {

    private Detector tfDetector = null;
    private ElapsedTime runtime = new ElapsedTime();

    private static String MODEL_FILE_NAME = "model_unquant_dec5.tflite";
    //private static String MODEL_FILE_NAME = "model.tflite";
    //private static String MODEL_FILE_NAME = "sample_model.tflite";
    private static String LABEL_FILE_NAME = "labels.txt";
    private static Classifier.Model MODEl_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;

    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {


        double speed = 0.75;
        double rotationSpeed = 0.2;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Scanner scanner = new Scanner();
        Claw claw = new Claw(hardwareMap);
        //Spinner spinner = new Spinner(hardwareMap);
        // Scanner scanner = new Scanner(hardwareMap);
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        mecanum.rightErrorAdjustment = 0.5;//1;

       /* try {
            tfDetector = new Detector(MODEl_TYPE, MODEL_FILE_NAME, LABEL_FILE_NAME, hardwareMap.appContext, telemetry);
            tfDetector.parent = this;
            tfDetector.activate();
        } catch (Exception ex) {
            telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
            sleep(3000);
            return;
        }
*/
        waitForStart();
       // runtime.reset();

       double forwardDistance = 38;
        double parkingDistance = 33;

  /*       int position = 9;
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
        sleep(4000);
       //
     */
       /* mecanum.moveArm(2,0);
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
        mecanum.move_forward_auto(speed,forwardDistance, 10.0 );
        mecanum.move_right_auto(speed*0.75, parkingDistance, 20.0);

    }


}
