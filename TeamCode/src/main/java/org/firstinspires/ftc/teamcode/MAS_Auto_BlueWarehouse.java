package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Scanner;
import org.firstinspires.ftc.teamcode.tfrec.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;
/*

    Near Warehouse
    =============
    1. Scan
    2. Drop preload
    3. Spin (if we decide to do this)
    4. Go to Warehouse and Park

     */

@Autonomous(name="MAS_Auto_BlueWarehouse")
//@Disabled
public class MAS_Auto_BlueWarehouse extends LinearOpMode {
    //Configuration used: 6wheelConfig

    //Copy for all autonomous BEGIN
    private Detector tfDetector = null;
    private ElapsedTime runtime = new ElapsedTime();

    private static String MODEL_FILE_NAME = "bluewarehouse.tflite";
    private static String LABEL_FILE_NAME = "labels_bluewarehouse.txt";
    private static Classifier.Model MODEl_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;
    //Copy for all autonomous END

    @Override
    public void runOpMode() throws InterruptedException {
        double speed = 0.4;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Scanner scanner = new Scanner();

        Claw claw = new Claw(hardwareMap);
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();

        //Copy for all autonomous BEGIN
        try {
            tfDetector = new Detector(MODEl_TYPE, MODEL_FILE_NAME, LABEL_FILE_NAME, hardwareMap.appContext, telemetry);
            tfDetector.parent = this;
            tfDetector.activate();
        } catch (Exception ex) {
            telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
            sleep(3000);
            return;
        }
        //Copy for all autonomous END
        waitForStart();
        double spinnerDistance = 23;
        double spinnerRotate = 20;
        double shippingHubDistance = 40;
        double rotateNinety = 30;

        //Copy for all autonomous BEGIN
        int position = 9;
        try {
            position = scanner.scan(hardwareMap, tfDetector, telemetry);
            telemetry.addData("Found in class", position);
            telemetry.update();
            sleep(1000);

            if (position == 3) {
                position = scanner.scan(hardwareMap, tfDetector, telemetry);
                sleep(1000);
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

        //Copy for all autonomous END

        //move right a bit then forward a bit to drop
        mecanum.move_right_auto(speed, shippingHubDistance/1.05, 20.0);

        //Copy for all autonomous BEGIN
        if(position == 2 || position == 3 || position == 9) {
            mecanum.moveArm(2,0);
            sleep(3000);
            claw.moveBucket(0.0);
            sleep(2000);
        } else if(position == 1) {
            mecanum.moveArm(1,0);
            sleep(3000);
            claw.moveBucket(0.0);
            sleep(2000);
        } else if(position == 0) {
            mecanum.arm.setPower(0.5);
            sleep(500);
            claw.moveBucket(-0.5);
            mecanum.arm.setPower(0.0);
            sleep(600);
            claw.moveBucket(0.0);
        }
        //Copy for all autonomous END

        if(position != 0) {
            mecanum.move_forward_auto(speed, shippingHubDistance * 0.8, 20.0);
        } else {
            mecanum.move_forward_auto(speed, shippingHubDistance * 0.7, 20.0);
        }
        if(position == 0) {
            claw.hamza();
        } else {
            claw.dropObject();
        }

        sleep(1000);

        claw.stopGripper();

        mecanum.move_backward_auto(speed,shippingHubDistance/1.25, 20.0 );

        mecanum.rotate_clock_auto(speed, rotateNinety, 10.0);

        mecanum.move_right_auto(speed * 1.5, 15, 15.0);

        double ParkDistance = 75;//going forward into warehouse
        //Increase the speed if we are going over the obstacle
        mecanum.move_backward_auto(speed*2.5,ParkDistance * 1.3, 25.0 );
        //else will need logic to collapse and then move right to park

    }


}