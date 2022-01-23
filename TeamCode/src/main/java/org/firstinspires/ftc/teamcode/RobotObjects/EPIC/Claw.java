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
    public DcMotorEx armLeft;
    public DcMotorEx armRight;
    //public DcMotorEx liftMotor;

    public double armInit = 0.0;
    public double finger1Init = 0.3;
    public double finger2Init = 0.3;
    public double armMin = 0.0;
    public double armMax = 0.5;
    public double finger1Min = 0.2;
    public double finger2Min = 0.2;
    public double finger1Max = 0.7;
    public double finger2Max = -0.7;
    //0 position
    public double bucket1Min = 0.83;
    public double bucket2Min = 0.;
    //Level 1
    public double bucket1Level1 = 0.58;
    public double bucket2Level1 = 0.58;
    //Level 2
    public double bucket1Level2 = 0.26;
    public double bucket2Level2 = 0.26;
    //Level 3
    public double bucket1Level3 = 0.2;
    public double bucket2Level3 = 0.2;

    //public double bucket1Max = 0.7;
    //public double bucket2Max = -0.7;
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
        armLeft = hardwareMap.get(DcMotorEx.class,"armLeft");
        armRight = hardwareMap.get(DcMotorEx.class,"armRight");
        clawFinger2.setDirection(Servo.Direction.REVERSE);
        clawBucket2.setDirection(Servo.Direction.REVERSE);
        clawFinger1.setPosition(finger1Min);
        clawFinger2.setPosition(finger2Min);
        clawBucket1.setPosition(bucket1Min);
        clawBucket2.setPosition(bucket1Min);
        //arm = hardwareMap.get(DcMotorEx.class,"arm");
        //arm.setDirection(DcMotor.Direction.REVERSE);

        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armLeft.setTargetPosition(0);
        armRight.setTargetPosition(0);
        armLeft.setPower(0.2);
        armRight.setPower(0.2);
        //liftMotor = hardwareMap.get(DcMotorEx.class,"Lift");

    }
    public void lift(int level) {
        //double ticksPerInchMecanum = (537.7 / 1);
        //if (parent.opModeIsActive()) {
        //new_frontLeftTarget = arm.getCurrentPosition() + (int)(frontLeftInches * ticksPerInchMecanum);
        //while(arm.getCurrentPosition() < new_frontLeftTarget) {
        int position = 0;
        double bucket1pos = bucket1Min;
        double bucket2pos = bucket2Min;
        if(level == 1){
            position = 135;
            bucket1pos = bucket1Level1;
            bucket2pos = bucket2Level1;

        }
        else if(level == 2){
            position = 260;
            bucket1pos = bucket1Level2;
            bucket2pos = bucket2Level2;

        }
        else if(level == 3){
            position = 380;
            bucket1pos = bucket1Level3;
            bucket2pos = bucket2Level3;

        }

        new_frontLeftTarget = position;
        if(level!=0) {
            armLeft.setTargetPosition(new_frontLeftTarget);
            armRight.setTargetPosition(new_frontLeftTarget);
            parent.sleep(200);
            clawBucket1.setPosition(bucket1pos);
            clawBucket2.setPosition(bucket2pos);
        }
        else{

            clawBucket1.setPosition(bucket1pos);
            clawBucket2.setPosition(bucket2pos);
            parent.sleep(200);
            armLeft.setTargetPosition(new_frontLeftTarget);
            armRight.setTargetPosition(new_frontLeftTarget);
        }
        //arm.setTargetPosition(arm.getCurrentPosition() + position);

        if(position==0){
            armLeft.setPower(0.2);
            armRight.setPower(0.2);
            parent.sleep(200);
        }
        else
            armLeft.setPower(0.6);
            armRight.setPower(0.6);
        //}
        //else{
        //arm.setPower(0.0);
        //}
        telemetry.addData("Path1",  "Running to %7d ", new_frontLeftTarget);

        telemetry.addData("Path2",  "Running at %7d ",
                armLeft.getCurrentPosition());
                armRight.getCurrentPosition();
        telemetry.update();
        //}
    }


    public void lift(double speed, int direction,int limit) {
        //double ticksPerInchMecanum = (537.7 / 1);
        //if (parent.opModeIsActive()) {
            //new_frontLeftTarget = arm.getCurrentPosition() + (int)(frontLeftInches * ticksPerInchMecanum);
            //while(arm.getCurrentPosition() < new_frontLeftTarget) {
            int position=10;
            if(direction<0)
                position = -1*position;

        new_frontLeftTarget += position;
        if(new_frontLeftTarget>=0 && new_frontLeftTarget<limit){
            //arm.setTargetPosition(arm.getCurrentPosition() + position);
            armLeft.setTargetPosition(new_frontLeftTarget);
            armRight.setTargetPosition(new_frontLeftTarget);
            armLeft.setPower(speed);
            armRight.setPower(speed);
            parent.sleep(100);
        }
        else if(new_frontLeftTarget<0)
            new_frontLeftTarget =0;
        else if(new_frontLeftTarget > limit)
            new_frontLeftTarget = limit;
            //}
            //else{
                //arm.setPower(0.0);
        //}
            telemetry.addData("Path1",  "Running to %7d ", new_frontLeftTarget);

            telemetry.addData("Path2",  "Running at %7d ",
                    armLeft.getCurrentPosition());
                    armRight.getCurrentPosition();
            telemetry.update();
        //}
    }

    public void initiateLift(){
        //int currentPosition = liftMotor.getCurrentPosition();
        //int targetPosition = 6
        telemetry.addData("Postion lift 2:%d", armLeft.getCurrentPosition());
        telemetry.addData("Postion lift 2:%d", armRight.getCurrentPosition());
        telemetry.update();
        armLeft.setPower(liftPower);
        armRight.setPower(liftPower);
        parent.sleep(1000);
        armLeft.setPower(0);
        armRight.setPower(0);
        telemetry.addData("Postion lift 2:%d", armLeft.getCurrentPosition());
        telemetry.addData("Postion lift 2:%d", armRight.getCurrentPosition());
        telemetry.update();
        //arm.setPosition(0.5);
        armLeft.setPower(-liftPower);
        armRight.setPower(-liftPower);
        parent.sleep(1000);
        armLeft.setPower(0);
        armRight.setPower(0);
        telemetry.addData("Postion lift 2:%d", armLeft.getCurrentPosition());
        telemetry.addData("Postion lift 2:%d", armRight.getCurrentPosition());
        telemetry.update();
    }

    public void initiateClaw() {

    }

    public void lift(double power)
    {

        armLeft.setPower(power);
        armRight.setPower(power);
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
        armLeft.setPower(-liftPower);
        armRight.setPower(-liftPower);
    }

    public void pick()
    {

        clawFinger1.setPosition(finger1Max);
        clawFinger2.setPosition(finger2Max);

        clawBucket1.setPosition(bucket1Min);
        clawBucket2.setPosition(bucket2Min);
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
        //parent.sleep(5000);
        //}
    }

    public void release() {
        clawFinger1.setPosition(finger1Max);
        clawFinger2.setPosition(-finger2Max);

        //clawBucket1.setPosition(bucket1Max);
        //clawBucket2.setPosition(bucket2Max);
        telemetry.addData("Postion Claw 1:%d", clawFinger1.getPosition());
        telemetry.addData("Postion Claw 2:%d", clawFinger2.getPosition());
        telemetry.update();
        //parent.sleep(5000);
    }
}
