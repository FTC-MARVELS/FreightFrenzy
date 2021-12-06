package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous (name = "EncoderDrive4")
@Disabled
public class Ecoder_Drive_3_omni extends LinearOpMode {
    private DcMotorEx rightmotor;
    private DcMotorEx leftmotor;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("ticks","start");
        //telemetry.update();
        //sleep(2000);
        rightmotor = hardwareMap.get(DcMotorEx.class,"right");
        leftmotor = hardwareMap.get(DcMotorEx.class,"left");
        telemetry.addData("ticks","motors defined");
        ///telemetry.update();
        //sleep(2000);

        rightmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("ticks","right motor direction reversed");
        //telemetry.update();
        //sleep(2000);


        rightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
        rightmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Runmode","Runmode with encoders");
        telemetry.update();
        //sleep(5000);


        rightmotor.setTargetPosition((int)Ticks);
        leftmotor.setTargetPosition((int)Ticks);
        telemetry.addData("Runmode","set target position");
        //telemetry.update();
        //sleep(5000);
        //rightmotor.setTargetPosition(1000);
        //leftmotor.setTargetPosition(1000);
        rightmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Runmode","rub to position");
        //telemetry.update();
        //sleep(5000);

        rightmotor.setVelocity(500);
        leftmotor.setVelocity(500);
        //rightmotor.setPower(0);
        //leftmotor.setPower(0);
        int i=0;
        while (rightmotor.isBusy() || leftmotor.isBusy() ) {

            telemetry.addData("Path","Driving 24 inches");
            telemetry.addData("i",Integer.toString(i));
            telemetry.addData("ticks",Double.toString(Ticks));

            telemetry.update();
            i++;





        }
}}
