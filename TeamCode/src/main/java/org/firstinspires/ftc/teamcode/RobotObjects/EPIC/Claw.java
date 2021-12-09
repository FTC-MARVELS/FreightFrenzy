package org.firstinspires.ftc.teamcode.RobotObjects.EPIC;

import android.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    //Configuration used: 6wheelConfig
    public Servo clawFinger1;
    public Servo clawFinger2;
    public Servo clawBucket1;
    public Servo clawBucket2;
    public DcMotorEx arm;
    //public DcMotorEx liftMotor;

    public double armInit = 0.0;
    public double finger1Init = 0.4;
    public double finger2Init = 0.4;
    public double armMin = 0.0;
    public double armMax = 0.5;
    public double finger1Min = 0.0;
    public double finger2Min = 0.0;
    public double finger1Max = 0.7;
    public double finger2Max = 0.7;
    public double liftPower = -0.2;
    public LinearOpMode parent;
    public Telemetry telemetry;
    public double pos = 0.0;

    public int new_frontLeftTarget = 0;

    public Claw(HardwareMap hardwareMap) {
        clawFinger1 = hardwareMap.get(Servo.class,"finger1");
        clawFinger2 = hardwareMap.get(Servo.class,"finger2");
        clawBucket1 = hardwareMap.get(Servo.class,"bucket1");
        clawBucket2 = hardwareMap.get(Servo.class,"bucket2");
        arm = hardwareMap.get(DcMotorEx.class,"arm");
        arm.setDirection(DcMotor.Direction.REVERSE);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        arm.setTargetPosition(0);
        arm.setPower(0.2);
        //liftMotor = hardwareMap.get(DcMotorEx.class,"Lift");

    }

    public void lift(double speed, int direction,double timeoutS) {
        //double ticksPerInchMecanum = (537.7 / 1);
        //if (parent.opModeIsActive()) {
            //new_frontLeftTarget = arm.getCurrentPosition() + (int)(frontLeftInches * ticksPerInchMecanum);
            //while(arm.getCurrentPosition() < new_frontLeftTarget) {
            int position=-10;
            if(direction<0)
                position = -1*position;

        new_frontLeftTarget += position;
        if(new_frontLeftTarget>=0 && new_frontLeftTarget<130){
            //arm.setTargetPosition(arm.getCurrentPosition() + position);
            arm.setTargetPosition(new_frontLeftTarget);
            arm.setPower(speed);
            parent.sleep(100);
        }
        else if(new_frontLeftTarget<0)
            new_frontLeftTarget =0;
        else if(new_frontLeftTarget > 100)
            new_frontLeftTarget = 100;
            //}
            //else{
                //arm.setPower(0.0);
        //}
            telemetry.addData("Path1",  "Running to %7d ", new_frontLeftTarget);

            telemetry.addData("Path2",  "Running at %7d ",
                    arm.getCurrentPosition());
            telemetry.update();
        //}
    }

    public void initiateLift(){
        //int currentPosition = liftMotor.getCurrentPosition();
        //int targetPosition = 6
        telemetry.addData("Postion lift 2:%d", arm.getCurrentPosition());
        telemetry.update();
        arm.setPower(liftPower);
        parent.sleep(1000);
        arm.setPower(0);
        telemetry.addData("Postion lift 2:%d", arm.getCurrentPosition());
        telemetry.update();
        //arm.setPosition(0.5);
        arm.setPower(-liftPower);
        parent.sleep(1000);
        arm.setPower(0);
        telemetry.addData("Postion lift 2:%d", arm.getCurrentPosition());
        telemetry.update();
    }

    public void initiateClaw() {

    }

    public void lift(double power)
    {

        arm.setPower(power);
    }

//    public void swing(double position){
//        pos = position;
//        arm.setPosition(pos);
//        parent.sleep(750);
//    }

//    public void rotate(double power)
//    {
//        //arm.setPosition(power);
//        if(power<0) {
//            pos = pos - 0.1;
//            if (pos < -1.5)
//                pos = -1.5;
//        }
//        else if (power > 0){
//        pos = pos + 0.1;
//        if (pos >1.5)
//            pos = 1.5;
//        }
//        arm.setPosition(pos);
//
//    }

    public void rest()
    {
        arm.setPower(-liftPower);
    }

    public void grab()
    {
        telemetry.addData("Postion Claw 1:%d", clawFinger1.getPosition());
        telemetry.addData("Postion Claw 2:%d", clawFinger2.getPosition());
        telemetry.update();
        //double fingerPosition = Range.clip()
        clawFinger1.setPosition(finger1Min);
        clawFinger2.setPosition(finger2Min);
        //while(parent.opModeIsActive()){
        telemetry.addData("Postion Claw 1:%d", clawFinger1.getPosition());
        telemetry.addData("Postion Claw 2:%d", clawFinger2.getPosition());
        telemetry.update();
        //}
    }

    public void release() {
        clawFinger1.setPosition(finger1Max);
        clawFinger2.setPosition(finger2Max);
        telemetry.addData("Postion Claw 1:%d", clawFinger1.getPosition());
        telemetry.addData("Postion Claw 2:%d", clawFinger2.getPosition());
        telemetry.update();
    }
}
