package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
        double speed = 0.2;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        //Claw claw = new Claw(hardwareMap);
        Spinner spinner = new Spinner(hardwareMap);
       // Scanner scanner = new Scanner(hardwareMap);
        mecanum.IsAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        mecanum.rightErrorAdjustment = 0.93;//1;

        waitForStart();

        // move right to go to spinner - 5 inches for eg
       double spinnerDistance = 60;
        //sleep();

        mecanum.encoderDrive(speed,spinnerDistance,0,-spinnerDistance,-spinnerDistance,0,spinnerDistance, 10.0);

        // rotate anticlockwise
        double rotateDistance = 10;
        mecanum.encoderDrive(speed,-rotateDistance,0,-rotateDistance,rotateDistance,0,rotateDistance, 5.0);


        spinner.setPower(-0.58);
        sleep(1565);

        //come back to the regular position - turn left - 5 inches
     //   mecanum.encoderDrive(speed,spinnerDistance,0,-spinnerDistance,-spinnerDistance,0,spinnerDistance, 1.0);

        //Scan for position of the element



    }

}