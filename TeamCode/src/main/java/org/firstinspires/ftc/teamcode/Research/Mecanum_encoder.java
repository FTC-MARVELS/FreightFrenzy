package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous (name = "Mecanum_Encoder")
@Disabled
public class Mecanum_encoder extends LinearOpMode {
    private DcMotorEx frontright;
    private DcMotorEx frontleft;
    private DcMotorEx backright;
    private DcMotorEx backleft;
    private DcMotorEx middleright;
    private DcMotorEx middleleft;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("ticks","start");

        frontright = hardwareMap.get(DcMotorEx.class,"Frontright");
        frontleft = hardwareMap.get(DcMotorEx.class,"Frontleft");
        backright = hardwareMap.get(DcMotorEx.class,"Backright");
        backleft = hardwareMap.get(DcMotorEx.class,"Backleft");
        middleright = hardwareMap.get(DcMotorEx.class,"Middleright");
        middleleft = hardwareMap.get(DcMotorEx.class,"Middleleft");
        telemetry.addData("ticks","motors defined");

        waitForStart();
        frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        middleright.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("ticks","right motor direction reversed");


        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("ticks","Stop and reset encoder");


        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        middleleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        middleright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Runmode","Runmode with encoders");
        telemetry.update();

        telemetry.addData("Runmode","set target position");

        telemetry.addData("Runmode","run to position");


        moveForward();
        moveLeft();
        moveForward();
        moveRight();
        moveCollaspe();

        int i=4;
        while (i < 5) {
            moveForward();
            moveRight();
            moveCollaspe();
            i++;
        }
        frontright.setPower(0);
        middleright.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        middleleft.setPower(0);
        backleft.setPower(0);
    }
    private void moveForward() {
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition(200);
        middleright.setTargetPosition(200);
        backright.setTargetPosition(200);
        frontleft.setTargetPosition(200);
        middleleft.setTargetPosition(200);
        backleft.setTargetPosition(200);


        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setVelocity(400);
        middleright.setVelocity(400);
        backright.setVelocity(400);
        frontleft.setVelocity(400);
        middleleft.setVelocity(400);
        backleft.setVelocity(400);
    }
    private void moveLeft() {
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition(200);
        middleright.setTargetPosition(200);
        backright.setTargetPosition(200);
        frontleft.setTargetPosition(-200);
        middleleft.setTargetPosition(-200);
        backleft.setTargetPosition(-200);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setVelocity(400);
        middleright.setVelocity(400);
        backright.setVelocity(400);
        frontleft.setVelocity(-400);
        middleleft.setVelocity(-400);
        backleft.setVelocity(-400);

    }
    private void moveRight() {

        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition(-200);
        middleright.setTargetPosition(-200);
        backright.setTargetPosition(-200);
        frontleft.setTargetPosition(200);
        middleleft.setTargetPosition(200);
        backleft.setTargetPosition(200);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setVelocity(-400);
        middleright.setVelocity(-400);
        backright.setVelocity(-400);
        frontleft.setVelocity(400);
        middleleft.setVelocity(400);
        backleft.setVelocity(400);
    }
    private void moveCollaspe() {
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition(200);
        middleright.setTargetPosition(0);
        backright.setTargetPosition(-200);
        frontleft.setTargetPosition(200);
        middleleft.setTargetPosition(0);
        backleft.setTargetPosition(-200);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setVelocity(400);
        middleright.setVelocity(0);
        backright.setVelocity(-400);
        frontleft.setVelocity(400);
        middleleft.setVelocity(0);
        backleft.setVelocity(-400);

    }
}

