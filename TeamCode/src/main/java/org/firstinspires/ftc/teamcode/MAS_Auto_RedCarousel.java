package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
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
        Claw claw = new Claw(hardwareMap);
        Spinner spinner = new Spinner(hardwareMap);
       // Scanner scanner = new Scanner(hardwareMap);
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        mecanum.rightErrorAdjustment = 0.5;//1;

        waitForStart();
        double spinnerDistance = 22.6;
        double spinnerRotate = 21;
        double shippingHubDistance = 14;
        double rotateNinety = 21;

        //SCAN CODE- EISHA AND HAMZA
        mecanum.move_forward_auto(speed,6.5, 10.0 );
        // rotate to bring spinner to position
        mecanum.rotate_clock_auto(rotationSpeed, spinnerRotate, 20.0);
        //backward to go to carousel
        mecanum.move_backward_auto(speed,spinnerDistance , 20.0);
        //Spin
        spinner.setPower(-0.58);
        sleep(2600);
        spinner.setPower(0);

        //back to position + rotate
        //come back to original position
        mecanum.move_forward_auto(speed,spinnerDistance , 20.0);
        //rotate to position the camera to scan
        mecanum.rotate_anti_clock_auto(rotationSpeed, rotateNinety, 20.0);
        //move back a bit to scan
        mecanum.move_backward_auto(speed,4, 10.0 );
        //Scan for position of the element- eisha and hamza!

        //move right a bit then forward a bit to drop
        mecanum.move_right_auto(speed, shippingHubDistance/1.05, 20.0);
        mecanum.move_forward_auto(speed, shippingHubDistance*1.41,20.0);

        //Raise Arm and wrist to drop
        mecanum.liftArm(-0.4);
        sleep(4000);
        claw.raiseWrist(0.5);
        claw.openClaws();
        sleep(1000);
        mecanum.liftArm(0.3);
        claw.restWrist();

        mecanum.move_backward_auto(speed,shippingHubDistance/0.95, 20.0 );

        mecanum.rotate_clock_auto(speed, rotateNinety, 10.0);

        double ParkDistance = 34;//going forward into warehouse
        //Increase the speed if we are going over the obstacle
        mecanum.move_backward_auto(speed*2,ParkDistance*1.05, 25.0 );
        //else will need logic to collapse and then move right to park
        mecanum.move_left_auto(speed, shippingHubDistance*1.1, 20.0);

    }

}