package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Scanner;

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
    mecanum.encoderDrive(speed,-12,0,12,12,0,-12, 1.0);
    //right
    mecanum.encoderDrive(speed,12,0,-12,-12,0,12, 1.0);
    //left turn
    mecanum.encoderDrive(speed,-12,0,-12,12,0,12, 1.0);
    //right turn
    mecanum.encoderDrive(speed,12,0,12,-12,0,-12, 1.0);
    //contract
    mecanum.encoderDrive(speed,3.15,0,-3.15,3.1,0,-3.1, 1.0);
    //expand
    mecanum.encoderDrive(speed,-3.15,0,3.15,-3.1,0,3.1, 1.0);

     */




@Autonomous(name="MAS_Auto_BlueCarousel_old")
@Disabled
public class MAS_Auto_BlueCarousel_old extends LinearOpMode {

    private Detector tfDetector = null;
    private ElapsedTime runtime = new ElapsedTime();

    private static String MODEL_FILE_NAME = "bcr.tflite";
    private static String LABEL_FILE_NAME = "labels_bcr.txt";
    private static Classifier.Model MODEl_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;

    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        double speed = 0.6;
        double rotationSpeed = 0.2;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Spinner spinner = new Spinner(hardwareMap);
        Scanner scanner = new Scanner();
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        mecanum.rightErrorAdjustment = 0.5;//1;
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
        double spinnerDistance = 21.5;
        double spinnerRotate = 10;
        double shippingHubDistance = 40;
        double rotateNinety = 21;
        //SCAN CODE- EISHA AND HAMZA

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
        } finally {
            tfDetector.stopProcessing();
        }

        telemetry.addData("FINAL POSITION", position);
        telemetry.update();

        if(position == 3 || position == 9) {
            position = 2;
        }

        mecanum.move_left_auto(speed, shippingHubDistance * 1.1, 7.0);

       /* if(position == 2 || position == 3 || position == 9) {
            mecanum.moveArm(2,0);
            sleep(3000);
            claw.moveBucket(0.0);
            //sleep(2000);
        } else if(position == 1) {
            mecanum.moveArm(1,0);
            sleep(3000);
            claw.moveBucket(0.0);
            //sleep(2000);
        } else if(position == 0) {
            claw.moveBucket(-0.5);
            sleep(800);
            claw.moveBucket(0.0);
        }*/
        //mecanum.positionForDrop(position,0);
        mecanum.positionForDropSidewaysAuto(position, "Blue"); //this code moves closer to the hub, drops and then moves back slightly


        mecanum.move_forward_auto(speed,shippingHubDistance*0.8, 7.0 );
        claw.dropObject();
        sleep(800);
        claw.stopIntake();
        mecanum.move_backward_auto(speed,4, 2.0);
        // rotate to bring spinner to position
        mecanum.rotate_counter_clock_auto(rotationSpeed, spinnerRotate * 2.5, 10.0);
        //backward to go to carousel
        mecanum.move_backward_auto(speed, spinnerDistance*2.5, 20.5);
        mecanum.rotate_counter_clock_auto(rotationSpeed, 18, 2.0);
        mecanum.move_backward_auto(speed, 25, 5.5);
        mecanum.rotate_clock_auto(rotationSpeed, 8, 2.0);
        mecanum.move_left_auto(speed * 0.8, 5, 2.0);

        sleep(100);
        //mecanum.move_backward_auto(0.03, 0.5, 1.0);
        //Spin
        spinner.setPower(0.58);
        sleep(2600);
        spinner.setPower(0);


        //back to position + rotate
        //come back to original position

        if (position == 1 || position == 2) {
            claw.moveSwing(0.7);
        } else {
            claw.moveSwing(-0.4);
        }
        sleep(200);
        mecanum.moveArm(0, position);
        claw.moveSwing(0.0);
        mecanum.move_right_auto(speed/2, 31, 2.5);
        claw.moveSwing(0.0);
        mecanum.move_backward_auto(speed/2, 10, 2.0);
        //going forward into warehouse
        //Increase the speed if we are going over the obstacle
        //mecanum.move_backward_auto(speed*2,ParkDistance*1.145, 25.0 );
        //else will need logic to collapse and then move right to park
        //mecanum.move_left_auto(speed, shippingHubDistance*1.75, 20.0);
        //mecanum.move_backward_auto(speed * 0.75,  5.0, 20.5);

    }

}