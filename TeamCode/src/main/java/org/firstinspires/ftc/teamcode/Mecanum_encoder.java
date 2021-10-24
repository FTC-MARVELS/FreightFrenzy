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

        waitForStart();
        telemetry.addData("ticks","wait for start");


        double distance = 24; //inches Enter the amount of inches you want the robot to move
        double circumference = 12.5;//inches 3.141592653589793238 * 4;
        double rotations = distance/circumference;
        double Ticks = (int)(rotations*537.6);
        telemetry.addData("ticks","ticks calculated");


        telemetry.addData("ticks",Double.toString(Ticks));

        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        middleleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        middleright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Runmode","Runmode with encoders");
        telemetry.update();


//        frontright.setTargetPosition(537);
//        middleright.setTargetPosition(537);
//        backright.setTargetPosition(537);
//        frontleft.setTargetPosition(537);
//        middleleft.setTargetPosition(537);
//        backleft.setTargetPosition(537);

        telemetry.addData("Runmode","set target position");


//        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Runmode","rub to position");


//        frontright.setVelocity(1000);
//        middleright.setVelocity(1000);
//        backright.setVelocity(1000);
//        frontleft.setVelocity(1000);
//        middleleft.setVelocity(1000);
//        backleft.setVelocity(1000);


        moveForward();
        moveLeft();
        moveForward();
        moveRight();
        moveCollaspe();


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
    }
    private void moveForward() {
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
    private void moveLeft() {
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
    private void moveRight() {

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
    private void moveCollaspe() {
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

