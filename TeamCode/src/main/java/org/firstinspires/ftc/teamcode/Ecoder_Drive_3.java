package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Ecoder_Drive_3 extends LinearOpMode {
    private DcMotor rightmotor;
    private DcMotor leftmotor;

    @Override
    public void runOpMode() throws InterruptedException {
        rightmotor.setDirection(DcMotorSimple.Direction.REVERSE);

        rightmotor = hardwareMap.get(DcMotor.class,"Right");
        leftmotor = hardwareMap.get(DcMotor.class,"Left");

        waitForStart();

        rightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double distance = 24; //Enter the amount of inches you want the robot to move
        double circumference = 3.141592653589793238 * 3.71;
        double rotations = (int)distance/circumference;
        double Ticks = rotations*1440;

        rightmotor.setTargetPosition((int)Ticks);
        leftmotor.setTargetPosition((int)Ticks);

        rightmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightmotor.setPower(0);
        leftmotor.setPower(0);

        while (rightmotor.isBusy() || leftmotor.isBusy() ) {
            telemetry.addData("Path","Driving 24 inches");
            telemetry.update();





        }
}}
