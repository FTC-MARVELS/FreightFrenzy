package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous (name = "EncoderDrive4")
public class Ecoder_Drive_3 extends LinearOpMode {
    private DcMotorEx rightmotor;
    private DcMotorEx leftmotor;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        rightmotor = hardwareMap.get(DcMotorEx.class,"right");
        leftmotor = hardwareMap.get(DcMotorEx.class,"left");

        rightmotor.setDirection(DcMotorSimple.Direction.REVERSE);


        rightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        double distance = 2; //Enter the amount of inches you want the robot to move
        double circumference = 3.141592653589793238 * 4;
        double rotations = distance/circumference;
        double Ticks = (int)rotations*1440;
        rightmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        rightmotor.setTargetPosition((int)Ticks);
        leftmotor.setTargetPosition((int)Ticks);
        //rightmotor.setTargetPosition(1000);
        //leftmotor.setTargetPosition(1000);
        rightmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightmotor.setVelocity(100);
        leftmotor.setVelocity(100);
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
