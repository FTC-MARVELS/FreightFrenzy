package org.firstinspires.ftc.teamcode.RobotObjects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Mecanum_Wheels  {
    //Configuration used: 6wheelConfig
    public DcMotorEx frontright;
    public DcMotorEx frontleft;
    public DcMotorEx backright;
    public DcMotorEx backleft;
    public DcMotorEx middleright;
    public DcMotorEx middleleft;

    public Mecanum_Wheels(HardwareMap hardwareMap) {
        frontright = hardwareMap.get(DcMotorEx.class,"Frontright");
        frontleft = hardwareMap.get(DcMotorEx.class,"Frontleft");
        backright = hardwareMap.get(DcMotorEx.class,"Backright");
        backleft = hardwareMap.get(DcMotorEx.class,"Backleft");
        middleright = hardwareMap.get(DcMotorEx.class,"Middleright");
        middleleft = hardwareMap.get(DcMotorEx.class,"Middleleft");
    }

    //initialize for TeleOp
    public void initialize() {
        double reset = 0;
        frontright.setPower(reset);
        //frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setPower(reset);
        backleft.setPower(reset);
        backright.setPower(reset);
        //backright.setDirection(DcMotorSimple.Direction.REVERSE);
        middleleft.setPower(reset);
        middleright.setPower(reset);
        //middleright.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void TestMechanumWheels(double power) {
        frontleft.setPower(power);
        backleft.setPower(power);
        frontright.setPower(-power);
        backright.setPower(-power);


    }
    public void TestOmniWheels(double power) {
        //middleright.setDirection(DcMotorSimple.Direction.REVERSE);
       middleleft.setPower(power + 0.4);
       middleright.setPower(-power);

    }
    public void TestOmniWheelsBackward(double power) {
        middleleft.setDirection(DcMotorSimple.Direction.REVERSE);
        middleleft.setPower(power);
        middleright.setPower(power - 0.4);

    }
    //moveForward for TeleOp
    //The left and right powers are controlled by the left and right y axes
    public void move_forwardback_rotate( double leftPower, double rightPower){
        frontleft.setPower(-leftPower);
        backleft.setPower(-leftPower);
        frontright.setPower(rightPower);
        backright.setPower(rightPower);
        middleleft.setPower(-leftPower);
        middleright.setPower(rightPower);
    }

    //moveSide for TeleOp
    //The left and right powers are controlled by the left and right x axes
    public void move_side( double leftPower, double rightPower){
        frontleft.setPower(-leftPower);
        backleft.setPower(leftPower);
        frontright.setPower(-rightPower);
        backright.setPower(rightPower);
    }

    public void moveForward(double power){
        frontleft.setPower(power);
        backleft.setPower(power);
        frontright.setPower(-power);
        backright.setPower(-power);
        middleleft.setPower(power);
        middleright.setPower(-power);
    }

    public void moveBackward(double power){
        frontleft.setPower(-power);
        backleft.setPower(-power);
        frontright.setPower(power);
        backright.setPower(power);
        middleleft.setPower(-power);
        middleright.setPower(power);
    }

    public void moveLeft(double power){
        frontleft.setPower(-power);
        backleft.setPower(power);
        frontright.setPower(power);
        backright.setPower(-power);
        //middleleft.setPower(-power);
        //middleright.setPower(-power);
    }

    public void moveRight(double power){
        frontleft.setPower(power);
        backleft.setPower(-power);
        frontright.setPower(-power);
        backright.setPower(power);
        //middleleft.setPower(power);
        //middleright.setPower(power);
    }

    public void moveForward() {
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontright.setTargetPosition(537);
        middleright.setTargetPosition(537);
        backright.setTargetPosition(537);
        frontleft.setTargetPosition(537);
        middleleft.setTargetPosition(537);
        backleft.setTargetPosition(537);


        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setVelocity(700);
        middleright.setVelocity(700);
        backright.setVelocity(700);
        frontleft.setVelocity(700);
        middleleft.setVelocity(700);
        backleft.setVelocity(700);
    }
    public void moveLeft() {
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition(537);
        middleright.setTargetPosition(537);
        backright.setTargetPosition(537);
        frontleft.setTargetPosition(-537);
        middleleft.setTargetPosition(-537);
        backleft.setTargetPosition(-537);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setVelocity(700);
        middleright.setVelocity(700);
        backright.setVelocity(700);
        frontleft.setVelocity(-700);
        middleleft.setVelocity(-700);
        backleft.setVelocity(-700);

    }
    public void moveRight() {

        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition(-537);
        middleright.setTargetPosition(-537);
        backright.setTargetPosition(-537);
        frontleft.setTargetPosition(537);
        middleleft.setTargetPosition(537);
        backleft.setTargetPosition(537);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setVelocity(-700);
        middleright.setVelocity(-700);
        backright.setVelocity(-700);
        frontleft.setVelocity(700);
        middleleft.setVelocity(700);
        backleft.setVelocity(700);
    }
    public void moveCollaspe() {
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setTargetPosition(537);
        middleright.setTargetPosition(0);
        backright.setTargetPosition(-537);
        frontleft.setTargetPosition(537);
        middleleft.setTargetPosition(0);
        backleft.setTargetPosition(-537);

        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontright.setVelocity(700);
        middleright.setVelocity(0);
        backright.setVelocity(-700);
        frontleft.setVelocity(700);
        middleleft.setVelocity(0);
        backleft.setVelocity(-700);

    }

}
