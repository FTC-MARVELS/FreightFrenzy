package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="Encoder_Drive_3_TEST")
@Disabled
public class Encoder extends LinearOpMode {
    private DcMotor frontright;
    private DcMotor frontleft;
    private DcMotor backright;
    private DcMotor backleft;
    private DcMotor middleright;
    private DcMotor middleleft;

    @Override
    public void runOpMode() throws InterruptedException {

        frontright = hardwareMap.get(DcMotor.class,"Frontright");
        frontleft = hardwareMap.get(DcMotor.class,"Frontleft");
        backright = hardwareMap.get(DcMotor.class,"Backright");
        backleft = hardwareMap.get(DcMotor.class,"Backleft");
        middleright = hardwareMap.get(DcMotor.class,"Middleright");
        middleleft = hardwareMap.get(DcMotor.class,"Middleleft");



        waitForStart();

        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double distance = 24; //Enter the amount of inches you want the robot to move
        double circumference = 3.141592653589793238 * 3.71;
        double rotations = (int)distance/circumference;
        double Ticks = (int) rotations * 537.6;

        frontright.setTargetPosition(1106);
        frontleft.setTargetPosition(1106);
        backright.setTargetPosition(1106);
        backleft.setTargetPosition(1106);
        middleright.setTargetPosition(1106);
        middleleft.setTargetPosition(1106);

        frontright.setPower(0.75);
        frontleft.setPower(0.75);
        backright.setPower(0.75);
        backleft.setPower(0.75);
        middleright.setPower(0.75);
        middleleft.setPower(0.75);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (frontright.isBusy() || frontleft.isBusy() || backright.isBusy() || backleft.isBusy() || middleright.isBusy() || middleleft.isBusy())
            telemetry.addData("Path","Driving 24 inches");
            telemetry.update();





        }
    }