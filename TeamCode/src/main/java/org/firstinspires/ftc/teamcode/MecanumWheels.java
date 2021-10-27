package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumWheels  {
    public DcMotorEx frontright;
    public DcMotorEx frontleft;
    public DcMotorEx backright;
    public DcMotorEx backleft;
    public DcMotorEx middleright;
    public DcMotorEx middleleft;

    public MecanumWheels(HardwareMap hardwareMap) {
        frontright = hardwareMap.get(DcMotorEx.class,"Frontright");
        frontleft = hardwareMap.get(DcMotorEx.class,"Frontleft");
        backright = hardwareMap.get(DcMotorEx.class,"Backright");
        backleft = hardwareMap.get(DcMotorEx.class,"Backleft");
        middleright = hardwareMap.get(DcMotorEx.class,"Middleright");
        middleleft = hardwareMap.get(DcMotorEx.class,"Middleleft");
    }

    public void moveForward(double distance,double speed ) {
        double circumference = 12;
        double rotations = distance/circumference;
        double TicksNeeded = rotations*distance;

        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition((int)TicksNeeded);
        middleright.setTargetPosition((int)TicksNeeded);
        backright.setTargetPosition((int)TicksNeeded);
        frontleft.setTargetPosition((int)TicksNeeded);
        middleleft.setTargetPosition((int)TicksNeeded);
        backleft.setTargetPosition((int)TicksNeeded);


        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setPower(speed);
        middleright.setPower(speed);
        backright.setPower(speed);
        frontleft.setPower(speed);
        middleleft.setPower(speed);
        backleft.setPower(speed);
    }
    public void moveLeft(double distance, double speed) {
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition((int)distance);
        middleright.setTargetPosition((int)distance);
        backright.setTargetPosition((int)distance);
        frontleft.setTargetPosition(-(int)distance);
        middleleft.setTargetPosition(-(int)distance);
        backleft.setTargetPosition(-(int)distance);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setPower(speed);
        middleright.setPower(speed);
        backright.setPower(speed);
        frontleft.setPower(-speed);
        middleleft.setPower(-speed);
        backleft.setPower(-speed);

    }
    public void moveRight(double distance, double speed) {

        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition(-(int)distance);
        middleright.setTargetPosition(-(int)distance);
        backright.setTargetPosition(-(int)distance);
        frontleft.setTargetPosition((int)distance);
        middleleft.setTargetPosition((int)distance);
        backleft.setTargetPosition((int)distance);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setPower(-speed);
        middleright.setPower(-speed);
        backright.setPower(-speed);
        frontleft.setPower(speed);
        middleleft.setPower(speed);
        backleft.setPower(speed);
    }
    public void moveCollaspe(double distance, double speed) {
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition((int)distance);
        middleright.setTargetPosition(0);
        backright.setTargetPosition(-(int)distance);
        frontleft.setTargetPosition((int)distance);
        middleleft.setTargetPosition(0);
        backleft.setTargetPosition(-(int)distance);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setPower(speed);
        middleright.setPower(0);
        backright.setPower(-speed);
        frontleft.setPower(speed);
        middleleft.setPower(0);
        backleft.setPower(-speed);


    }
}
