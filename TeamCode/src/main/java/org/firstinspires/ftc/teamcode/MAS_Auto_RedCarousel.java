package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;
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


@Autonomous(name="MAS_Auto_RedCarousel")
public class MAS_Auto_RedCarousel extends LinearOpMode {
    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        double speed = 0.35;
        double rotationSpeed = 0.2;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        //Claw claw = new Claw(hardwareMap);
        Spinner spinner = new Spinner(hardwareMap);
       // Scanner scanner = new Scanner(hardwareMap);
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
       mecanum.rightErrorAdjustment = 0.5;//1;

        waitForStart();
        double spinnerDistance = 22;
        double spinnerRotate = 20;
        double shippingHubDistance = 13.3;
        double rotateNinety = 21;

        //SCAN CODE- EISHA AND HAMZA
        mecanum.move_forward_auto(speed,6, 15.0 );
        // rotate to bring spinner to position
        mecanum.rotate_clock_auto(rotationSpeed, spinnerRotate, 10.0);
        //backward to go to carousel
        mecanum.move_backward_auto(speed,spinnerDistance , 10.0);
        //Spin
        spinner.setPower(-0.56);
        sleep(2500);
        spinner.setPower(0);
        //back to position + rotate

        //come back to original position
        mecanum.move_forward_auto(speed,spinnerDistance , 10.0);
        //rotate to position the camera to scan
        mecanum.rotate_anti_clock_auto(rotationSpeed, rotateNinety, 10.0);
        //move back a bit to scan
        mecanum.move_backward_auto(speed,4, 15.0 );
        //Scan for position of the element- eisha and hamza!


      /*  mecanum.liftXrail(0.4);
        sleep(1400);
        mecanum.liftXrail(0);
        claw.open();*/
        sleep(1000);
        //move right a bit then forward a bit to drop
        mecanum.move_right_auto(speed, shippingHubDistance/1.05, 10.0);
        mecanum.move_forward_auto(speed, 18, 15.0);

        //mecanum.move_forward_auto(speed,shippingHubDistance, 15.0 );

        //tes mecanum.move_backward_auto(speed,shippingHubDistance/2, 15.0 );

        //tes      mecanum.rotate_anti_clock_auto(speed, rotateNinety, 10.0);

       double rotateParkDistance = 11.5;
        //rotating 90deg to go to warehouse & park
       // mecanum.rotate_clock_auto(speed, rotateParkDistance, 10.0);

        double ParkDistance = 40;//going forward into warehouse
        //tes     mecanum.move_backward_auto(speed, ParkDistance, 20.0);
    //tes    mecanum.move_left_auto(speed,shippingHubDistance/1.25, 15.0 );

    }

}