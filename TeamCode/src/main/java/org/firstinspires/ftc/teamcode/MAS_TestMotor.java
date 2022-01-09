package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.hardware.motors.RevRoboticsUltraPlanetaryHdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;

//@Autonomous(name = "MAS_TestMotor")
@TeleOp(name = "MAS_TestMotor")
@Disabled

public class MAS_TestMotor extends LinearOpMode {
    public DcMotorEx frontright;
    public DcMotorEx frontleft;
    public DcMotorEx backright;
    public DcMotorEx backleft;
    public CRServo secondArm;
    public DcMotorEx testMotor1;
    public RevRoboticsUltraPlanetaryHdHexMotor testMotor ;
    public RevRoboticsCoreHexMotor temp1;
    public Claw claw;
    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum_Wheels mecanumWheels = new Mecanum_Wheels(hardwareMap);
        double lefty = 0.0;
        double leftx = 0.0;
        double righty = 0.0;
        double rightx = 0.0;
        double speed = 0.75;
        double rotationSpeed = 0.2;
        //Mecanum_Wheels mecanumWheels = new Mecanum_Wheels(hardwareMap);
        secondArm = hardwareMap.get(CRServo.class, "secondArm");
        //rotatingGripper = hardwareMap.get(DcMotorEx.class, "rotatingGripper");
        testMotor1 = hardwareMap.get(DcMotorEx.class,"arm");
        frontright = hardwareMap.get(DcMotorEx.class,"Frontright");
        frontleft = hardwareMap.get(DcMotorEx.class,"Frontleft");
        backright = hardwareMap.get(DcMotorEx.class,"Backright");
        backleft = hardwareMap.get(DcMotorEx.class,"Backleft");
        claw = new Claw(hardwareMap);
        waitForStart();
        // runtime.reset();
        //testMotor1.setPower(0.5);
       /* sleep(5000);
        testMotor1.setPower(-1.0);
        sleep(15000);
        testMotor1.setPower(0);
        sleep(5000);*/
        while (opModeIsActive()) {
            mecanumWheels.initialize();
            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;
           // mecanumWheels.moveAllDirections(lefty,righty,leftx,rightx);
            /*if(gamepad2.right_stick_x > 0.1 || gamepad2.right_stick_x < -0.1) {
                testMotor1.setPower(gamepad2.right_stick_x*0.8);
            } else {
                testMotor1.setPower(0.0);
                testMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }*/
           /* secondArm.setDirection(DcMotorSimple.Direction.FORWARD);
            if(gamepad1.a)
                secondArm.setPower(0.8);
            else if(gamepad1.b) {
                secondArm.setPower(-0.8);
            }*/
            if(gamepad1.a)
                claw.moveFloor(0.0);
            else if(gamepad1.b)
                claw.moveFloor(1.0);
           else
                claw.moveFloor(0.5);

        }

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

