package org.firstinspires.ftc.teamcode.RobotObjects;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mecanum_Wheels  {
    //Configuration used: 6wheelConfig
    public DcMotorEx frontright;
    public DcMotorEx frontleft;
    public DcMotorEx backright;
    public DcMotorEx backleft;
    public DcMotorEx middleright;
    public DcMotorEx middleleft;
    public DcMotorEx arm;
    public Servo rightFinger;
    public Servo leftFinger;

    public boolean IsAutonomous = false;
    public boolean IsMASAutonomous = false;

    public double leftErrorAdjustment = 1.0;
    public double rightErrorAdjustment = 1.0;

    public double mecanumWheelCircumference = 12; //inches
    public double omniWheelCircumference = 12; //inches



    public LinearOpMode parent;

    public int velocity = 200;

    private ElapsedTime runtime = new ElapsedTime();

    public Telemetry telemetry;

    public Mecanum_Wheels(HardwareMap hardwareMap) {
        frontright = hardwareMap.get(DcMotorEx.class,"Frontright");
        frontleft = hardwareMap.get(DcMotorEx.class,"Frontleft");
        backright = hardwareMap.get(DcMotorEx.class,"Backright");
        backleft = hardwareMap.get(DcMotorEx.class,"Backleft");
        middleright = hardwareMap.get(DcMotorEx.class,"Middleright");
        middleleft = hardwareMap.get(DcMotorEx.class,"Middleleft");
        arm = hardwareMap.get(DcMotorEx.class, "xRail");
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

        if(IsAutonomous)
        {
//            frontright.setDirection(DcMotorSimple.Direction.REVERSE);
//            middleright.setDirection(DcMotorSimple.Direction.REVERSE);
//            backright.setDirection(DcMotorSimple.Direction.REVERSE);

            frontleft.setDirection(DcMotor.Direction.FORWARD);
            frontright.setDirection(DcMotor.Direction.REVERSE);
            middleright.setDirection(DcMotor.Direction.REVERSE);
            middleleft.setDirection(DcMotor.Direction.FORWARD);
            backright.setDirection(DcMotor.Direction.REVERSE);
            backleft.setDirection(DcMotor.Direction.FORWARD);

            frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            middleleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            middleright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        if(IsMASAutonomous) {
            {
//            frontright.setDirection(DcMotorSimple.Direction.REVERSE);
//            middleright.setDirection(DcMotorSimple.Direction.REVERSE);
//            backright.setDirection(DcMotorSimple.Direction.REVERSE);

                frontleft.setDirection(DcMotor.Direction.FORWARD);
                frontright.setDirection(DcMotor.Direction.REVERSE);
                middleright.setDirection(DcMotor.Direction.FORWARD);
                middleleft.setDirection(DcMotor.Direction.REVERSE);
                backright.setDirection(DcMotor.Direction.FORWARD);
                backleft.setDirection(DcMotor.Direction.FORWARD);

                frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


                frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                middleleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                middleright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }
   public void liftArm(double power) {
            arm.setPower(power);

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

    public void encoderDrive(double speed,
                             double frontLeftInches, double middleLeftInches, double backLeftInches, double frontRightInches,double middleRightInches,
                             double backRightInches, double timeoutS) {
        int new_frontLeftTarget;
        int new_frontRightTarget;
        int new_middleLeftTarget=0;
        int new_middleRightTarget=0;
        int new_backLeftTarget;
        int new_backRightTarget;
        double ticksPerInchMecanum = (537.7 / mecanumWheelCircumference);
        double ticksPerInchOmni = (537.7 / omniWheelCircumference);
        // Ensure that the opmode is still active
        if (parent.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            new_frontLeftTarget = frontleft.getCurrentPosition() + (int)(frontLeftInches * ticksPerInchMecanum);
            new_frontRightTarget = frontright.getCurrentPosition() + (int)(frontRightInches * ticksPerInchMecanum);
            if(middleLeftInches!=0)
                new_middleLeftTarget = frontleft.getCurrentPosition() + (int)(middleLeftInches * ticksPerInchOmni);
            if(middleRightInches!=0)
                new_middleRightTarget = frontright.getCurrentPosition() + (int)(middleRightInches * ticksPerInchOmni);

            new_backLeftTarget = backleft.getCurrentPosition() + (int)(backLeftInches * ticksPerInchMecanum);
            new_backRightTarget = backright.getCurrentPosition() + (int)(backRightInches * ticksPerInchMecanum);
            frontleft.setTargetPosition(new_frontLeftTarget);
            frontright.setTargetPosition(new_frontRightTarget);

            if(new_middleLeftTarget!=0)
                middleleft.setTargetPosition(new_middleLeftTarget);
            if(new_middleRightTarget!=0)
                middleright.setTargetPosition(new_middleRightTarget);

            backleft.setTargetPosition(new_backLeftTarget);
            backright.setTargetPosition(new_backRightTarget);

            // Turn On RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if(new_middleLeftTarget!=0)
                middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if(new_middleRightTarget!=0)
                middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontleft.setPower(speed);
            frontright.setPower(speed);
            if(new_middleLeftTarget!=0)
                middleleft.setPower(speed);
            if(new_middleRightTarget!=0)
                middleright.setPower(speed);
            backleft.setPower(speed);
            backright.setPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (parent.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    ( frontleft.isBusy() && frontright.isBusy()  && backleft.isBusy() && backright.isBusy()))

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d :%7d :%7d", new_frontLeftTarget,  new_frontRightTarget, new_middleLeftTarget, new_middleRightTarget , new_backLeftTarget, new_backRightTarget);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d :%7d :%7d",
                    frontleft.getCurrentPosition(),
                    frontright.getCurrentPosition(),
                    middleleft.getCurrentPosition(),
                    middleright.getCurrentPosition(),
                    backleft.getCurrentPosition(),
                    backright.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        frontleft.setPower(0);
        frontright.setPower(0);
        middleleft.setPower(0);
        middleright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);

        // Turn off RUN_TO_POSITION
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        middleleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        middleright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move
    }

    public void moveY(int distance){
        double circumference = mecanumWheelCircumference; //inches
        double rotations = (double) distance/circumference;
        double Ticks = (int)(rotations * 537.6);
        int direction = 1;
        if (distance<0)
            direction = -1;
        frontleft.setTargetPosition((int)Ticks*direction);
        middleleft.setTargetPosition((int)Ticks*direction);
        backleft.setTargetPosition((int)Ticks*direction);
        frontright.setTargetPosition(-(int)Ticks*direction);
        middleright.setTargetPosition(-(int)Ticks*direction);
        backright.setTargetPosition(-(int)Ticks*direction);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontleft.setVelocity(velocity*direction);
        middleleft.setVelocity(velocity*direction);
        backleft.setVelocity(velocity*direction);
        frontright.setVelocity(-velocity*direction);
        middleright.setVelocity(-velocity*direction);
        backright.setVelocity(-velocity*direction);
    }



    public void TestOmniWheelsBackward(double power) {
        middleleft.setDirection(DcMotorSimple.Direction.REVERSE);
        middleleft.setPower(power);
        middleright.setPower(power - 0.4);

    }
    public void TurnLeft() {
        frontleft.setPower(-0.6*leftErrorAdjustment);
        backleft.setPower(-0.6*leftErrorAdjustment);
        frontright.setPower(-0.6*rightErrorAdjustment);
        backright.setPower(-0.6*rightErrorAdjustment);
    }
    public void TurnRight() {
        frontleft.setPower(0.6*leftErrorAdjustment);
        backleft.setPower(0.6*leftErrorAdjustment);
        frontright.setPower(0.6*rightErrorAdjustment);
        backright.setPower(0.6*rightErrorAdjustment);
    }

    public void Expand() {
        //frontleft.setPower(-0.6);
        //backleft.setPower(0.6);
        frontright.setPower(0.6);
        backright.setPower(-0.6);

    }
    public void move(double lefty, double righty, double leftx, double rightx){
//        frontright.setPower(lefty  -rightx - leftx);
//        frontleft.setPower(-lefty + rightx + leftx);
//        backright.setPower(lefty - rightx + leftx);
//        backleft.setPower(-lefty + rightx - leftx);
//        middleright.setPower(lefty - rightx);
//        middleleft.setPower(-lefty + rightx);
        frontright.setPower(-lefty  +rightx + leftx);
        frontleft.setPower(lefty + rightx + leftx);
        backright.setPower(-lefty + rightx - leftx);
        backleft.setPower(lefty + rightx - leftx);
        middleright.setPower(-lefty + rightx);
        middleleft.setPower(lefty + rightx);

    }

    public void Collapse() {
        //frontleft.setPower(0.6);
        //backleft.setPower(-0.6);
        frontright.setPower(-0.6);
        backright.setPower(0.6);

    }

    //moveForward for TeleOp
    //The left and right powers are controlled by the left and right y axes
    public void move_forwardback_rotate( double leftPower, double rightPower){
        frontleft.setPower(leftPower*leftErrorAdjustment);
        backleft.setPower(leftPower*leftErrorAdjustment);
        frontright.setPower(-rightPower*rightErrorAdjustment);
        backright.setPower(rightPower*rightErrorAdjustment);
        middleleft.setPower(-leftPower*leftErrorAdjustment);
        middleright.setPower(rightPower*rightErrorAdjustment);
    }

    //moveSide for TeleOp
    //The left and right powers are controlled by the left and right x axes
    public void move_side( double leftPower, double rightPower){
        frontleft.setPower(-leftPower*leftErrorAdjustment);
        backleft.setPower(leftPower*leftErrorAdjustment);
        frontright.setPower(-rightPower*rightErrorAdjustment);
        backright.setPower(-rightPower*rightErrorAdjustment);
    }

    public void move_right_auto(double speed, double distance, double timeOut) {
        encoderDrive(speed,-distance,0,distance,distance,0,-distance, timeOut);
    }

    public void move_left_auto(double speed, double distance, double timeOut) {
        encoderDrive(speed,distance,0,-distance,-distance,0,distance, timeOut);
    }

    public void rotate_clock_auto(double speed, double distance, double timeOut) {
        encoderDrive(speed,-distance,0,-distance,distance,0,distance, timeOut);
    }

    public void rotate_anti_clock_auto(double speed, double distance, double timeOut) {
        encoderDrive(speed,distance,0,distance,-distance,0,-distance, timeOut);
    }
    public void move_backward_auto(double speed, double distance, double timeOut){
        encoderDrive(speed,distance,distance,distance,distance,distance, distance, timeOut);
    }

    public void move_forward_auto(double speed, double distance, double timeOut){
        encoderDrive(speed,-distance,-distance,-distance,-distance,-distance, -distance, timeOut);
    }

//    public void moveForward(double power){
//        frontleft.setPower(power);
//        backleft.setPower(power);
//        frontright.setPower(-power);
//        backright.setPower(-power);
//        middleleft.setPower(power);
//        middleright.setPower(-power);
//    }
//
//    public void moveBackward(double power){
//        frontleft.setPower(-power);
//        backleft.setPower(-power);
//        frontright.setPower(power);
//        backright.setPower(power);
//        middleleft.setPower(-power);
//        middleright.setPower(power);
//    }

    public void moveY(double power){
        frontleft.setPower(-power*leftErrorAdjustment);
        backleft.setPower(-power*leftErrorAdjustment);
        frontright.setPower(power*rightErrorAdjustment);
        backright.setPower(power*rightErrorAdjustment);
        middleleft.setPower(-power*leftErrorAdjustment);
        middleright.setPower(power*rightErrorAdjustment);
    }
    public void moveX(double power){
        frontleft.setPower(power*leftErrorAdjustment);
        backleft.setPower(-power*leftErrorAdjustment);
        frontright.setPower(power*rightErrorAdjustment);
        backright.setPower(-power*rightErrorAdjustment);
        middleleft.setPower(-power*leftErrorAdjustment);
        middleright.setPower(-power*rightErrorAdjustment);
    }
//    public void moveLeft(double power){
//        frontleft.setPower(-power);
//        backleft.setPower(power);
//        frontright.setPower(-power);
//        backright.setPower(power);
//        //middleleft.setPower(-power);
//        //middleright.setPower(-power);
//    }
//
//    public void moveRight(double power){
//        frontleft.setPower(power);
//        backleft.setPower(-power);
//        frontright.setPower(power);
//        backright.setPower(-power);
//        //middleleft.setPower(power);
//        //middleright.setPower(power);
//    }
    public void middleForwardback(double leftPower, double rightPower){
        middleleft.setPower(leftPower*leftErrorAdjustment);
        middleright.setPower(rightPower*rightErrorAdjustment);
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
