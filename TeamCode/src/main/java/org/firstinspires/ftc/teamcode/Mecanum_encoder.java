package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "Mecanum_Encoder")
public class Mecanum_encoder extends LinearOpMode {
    private DcMotorEx frontright;
    private DcMotorEx frontleft;
    private DcMotorEx backright;
    private DcMotorEx backleft;
    private DcMotorEx middleright;
    private DcMotorEx middleleft;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("ticks","start");
        //telemetry.update();
        //sleep(2000);
        frontright = hardwareMap.get(DcMotorEx.class,"Frontright");
        frontleft = hardwareMap.get(DcMotorEx.class,"Frontleft");
        backright = hardwareMap.get(DcMotorEx.class,"Backright");
        backleft = hardwareMap.get(DcMotorEx.class,"Backleft");
        middleright = hardwareMap.get(DcMotorEx.class,"Middleright");
        middleleft = hardwareMap.get(DcMotorEx.class,"Middleleft");
        telemetry.addData("ticks","motors defined");
        ///telemetry.update();
        //sleep(2000);
        waitForStart();

        frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        middleright.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("ticks","right motor direction reversed");
        //telemetry.update();
        //sleep(2000);


        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("ticks","Stop and reset encoder");
        //telemetry.update();
        //sleep(2000);

        waitForStart();
        telemetry.addData("ticks","wait for start");
        //sleep(2000);

        double distance = 24; //inches Enter the amount of inches you want the robot to move
        double circumference = 12.5;//inches 3.141592653589793238 * 4;
        double rotations = distance/circumference;
        double Ticks = (int)(rotations*537.6);
        telemetry.addData("ticks","ticks calculated");
        //telemetry.update();
        //sleep(5000);

        telemetry.addData("ticks",Double.toString(Ticks));
        //telemetry.update();
        //sleep(5000);
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        middleleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        middleright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Runmode","Runmode with encoders");
        telemetry.update();
        //sleep(5000);


        //rightmotor.setTargetPosition((int)Ticks);
        //leftmotor.setTargetPosition((int)Ticks);
        frontright.setTargetPosition(316);
        middleright.setTargetPosition(316);
        backright.setTargetPosition(316);
        frontleft.setTargetPosition(316);
        middleleft.setTargetPosition(316);
        backleft.setTargetPosition(316);

        telemetry.addData("Runmode","set target position");
        //telemetry.update();
        //sleep(5000);
        //rightmotor.setTargetPosition(1000);
        //leftmotor.setTargetPosition(1000);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Runmode","rub to position");
        //telemetry.update();
        //sleep(5000);

        frontright.setVelocity(500);
        middleright.setVelocity(500);
        backright.setVelocity(500);
        frontleft.setVelocity(500);
        middleleft.setVelocity(500);
        backleft.setVelocity(500);

        //rightmotor.setPower(0);
        //leftmotor.setPower(0);
        int i=0;
        while (frontright.isBusy() || middleright.isBusy() || backright.isBusy() || frontleft.isBusy() || middleleft.isBusy() || backleft.isBusy() ) {

            telemetry.addData("Path","Driving 24 inches");
            telemetry.addData("i",Integer.toString(i));
            telemetry.addData("ticks",Double.toString(Ticks));

            telemetry.update();
            i++;





        }
}}
