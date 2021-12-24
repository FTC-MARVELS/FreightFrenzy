package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.hardware.motors.RevRoboticsUltraPlanetaryHdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Scanner;
import org.firstinspires.ftc.teamcode.tfrec.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;

@Autonomous(name = "MAS_TestMotor_Auto")
public class MAS_TestMotor_Auto extends LinearOpMode {

    public CRServo servo1;
    public DcMotorEx testMotor1;
    public RevRoboticsUltraPlanetaryHdHexMotor testMotor ;
    public RevRoboticsCoreHexMotor temp1;
    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {

        double speed = 0.75;
        double rotationSpeed = 0.2;
        //Mecanum_Wheels mecanumWheels = new Mecanum_Wheels(hardwareMap);
        //servo1 = hardwareMap.get(CRServo.class, "servo1");
        //rotatingGripper = hardwareMap.get(DcMotorEx.class, "rotatingGripper");
        testMotor1 = hardwareMap.get(DcMotorEx.class,"Frontright");
        waitForStart();
        // runtime.reset();
        //testMotor1.setPower(0.5);
        sleep(5000);
        testMotor1.setPower(-1.0);
        sleep(15000);
        testMotor1.setPower(0);
        sleep(5000);

        /*mecanumWheels.frontright.setPower(1);
        sleep(5000);
        mecanumWheels.frontright.setPower(-1);

        sleep(5000);
        mecanumWheels.frontright.setPower(0);*/

       // mecanumWheels.backleft.setPower(0.5);
       // sleep(5000);
       // mecanumWheels.backleft.setPower(0.0);
        /*sleep(5000);
        servo1.setDirection(DcMotorSimple.Direction.FORWARD);
        servo1.setPower(0.3);
        sleep(2000);
        telemetry.addData("Servo Power again in class", servo1.getPower());
        telemetry.update();}*/
        //rotatingGripper.setPower(1);
        //sleep(4000);
    }
}

