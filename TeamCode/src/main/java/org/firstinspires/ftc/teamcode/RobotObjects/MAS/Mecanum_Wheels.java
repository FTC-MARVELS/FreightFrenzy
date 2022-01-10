package org.firstinspires.ftc.teamcode.RobotObjects.MAS;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mecanum_Wheels {
    //Configuration used: 4wheelConfig_MAS
    public DcMotorEx frontright;
    public DcMotorEx frontleft;
    public DcMotorEx backright;
    public DcMotorEx backleft;
    //public DcMotorEx middleright;
    //public DcMotorEx middleleft;
    public DcMotorEx arm;
    public Claw claw;
    public boolean IsMASAutonomous = false;

    public double leftErrorAdjustment = 1.0;
    public double rightErrorAdjustment = 1.0;
    public double frontleftErrorAdjustment = 1.0;
    public double frontrightErrorAdjustment = 1.0;
    public double backleftErrorAdjustment = 1.0;
    public double backrightErrorAdjustment = 1.0;

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
        //middleright = hardwareMap.get(DcMotorEx.class,"Middleright");
        //middleleft = hardwareMap.get(DcMotorEx.class,"Middleleft");
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        claw = new Claw(hardwareMap);
    }

    //initialize for TeleOp
    public void initialize() {
        double reset = 0;
        frontright.setPower(reset);
        frontleft.setPower(reset);
        backleft.setPower(reset);
        backright.setPower(reset);

        if(IsMASAutonomous) {
            {
//            frontright.setDirection(DcMotorSimple.Direction.REVERSE);
//            middleright.setDirection(DcMotorSimple.Direction.REVERSE);
//            backright.setDirection(DcMotorSimple.Direction.REVERSE);

                frontleft.setDirection(DcMotor.Direction.REVERSE);
                frontright.setDirection(DcMotor.Direction.FORWARD);
                //middleright.setDirection(DcMotor.Direction.FORWARD);
                //middleleft.setDirection(DcMotor.Direction.REVERSE);
                backright.setDirection(DcMotor.Direction.FORWARD);
                backleft.setDirection(DcMotor.Direction.REVERSE);

                frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                //middleleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                //middleright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


                frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //middleleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //middleright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }

    public void encoderDrive(double speed,
                             double frontLeftInches, double backLeftInches, double frontRightInches,
                             double backRightInches, double timeoutS) {
        int new_frontLeftTarget;
        int new_frontRightTarget;
        //int new_middleLeftTarget=0;
        //int new_middleRightTarget=0;
        int new_backLeftTarget;
        int new_backRightTarget;
        double ticksPerInchMecanum = (537.7 / mecanumWheelCircumference);
        double ticksPerInchOmni = (537.7 / omniWheelCircumference);
        // Ensure that the opmode is still active
        if (parent.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            new_frontLeftTarget = frontleft.getCurrentPosition() + (int)(frontLeftInches * ticksPerInchMecanum);
            new_frontRightTarget = frontright.getCurrentPosition() + (int)(frontRightInches * ticksPerInchMecanum);
            /*if(middleLeftInches!=0)
                new_middleLeftTarget = frontleft.getCurrentPosition() + (int)(middleLeftInches * ticksPerInchOmni);
            if(middleRightInches!=0)
                new_middleRightTarget = frontright.getCurrentPosition() + (int)(middleRightInches * ticksPerInchOmni);
            */
            new_backLeftTarget = backleft.getCurrentPosition() + (int)(backLeftInches * ticksPerInchMecanum);
            new_backRightTarget = backright.getCurrentPosition() + (int)(backRightInches * ticksPerInchMecanum);
            frontleft.setTargetPosition(new_frontLeftTarget);
            frontright.setTargetPosition(new_frontRightTarget);

            /*if(new_middleLeftTarget!=0)
                middleleft.setTargetPosition(new_middleLeftTarget);
            if(new_middleRightTarget!=0)
                middleright.setTargetPosition(new_middleRightTarget);
            */
            backleft.setTargetPosition(new_backLeftTarget);
            backright.setTargetPosition(new_backRightTarget);

            // Turn On RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            /*if(new_middleLeftTarget!=0)
                middleleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if(new_middleRightTarget!=0)
                middleright.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontleft.setPower(speed*1);
            frontright.setPower(speed);
            /*if(new_middleLeftTarget!=0)
                middleleft.setPower(speed);
            if(new_middleRightTarget!=0)
                middleright.setPower(speed);*/
            backleft.setPower(speed*1);
            backright.setPower(speed*1);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (parent.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    ( frontleft.isBusy() && frontright.isBusy()  && backleft.isBusy() && backright.isBusy()))

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d ", new_frontLeftTarget,  new_frontRightTarget, new_backLeftTarget, new_backRightTarget);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d ",
                    frontleft.getCurrentPosition(),
                    frontright.getCurrentPosition(),
                    //middleleft.getCurrentPosition(),
                    //middleright.getCurrentPosition(),
                    backleft.getCurrentPosition(),
                    backright.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        frontleft.setPower(0);
        frontright.setPower(0);
        //middleleft.setPower(0);
        //middleright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);

        // Turn off RUN_TO_POSITION
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //middleleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //middleright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move
    }

    //Autonomous mode methods BEGIN

    //frontleft backleft frontright backright
    public void move_right_auto(double speed, double distance, double timeOut) {
        encoderDrive(speed,distance,distance,-distance,-distance, timeOut);
    }

    public void move_left_auto(double speed, double distance, double timeOut) {
        encoderDrive(speed,-distance,-distance,distance,distance, timeOut);
    }

    public void rotate_clock_auto(double speed, double distance, double timeOut) {
        encoderDrive(speed,distance,-distance,-distance,distance, timeOut);
    }

    public void rotate_counter_clock_auto(double speed, double distance, double timeOut) {
        encoderDrive(speed,-distance,distance,distance,-distance, timeOut);
    }
    public void move_backward_auto(double speed, double distance, double timeOut){
        encoderDrive(speed,-distance,distance,-distance, distance, timeOut);
    }

    public void move_forward_auto(double speed, double distance, double timeOut){
        encoderDrive(speed,distance,-distance,distance, -distance, timeOut);
    }

    //Autonomous mode methods END

    /*public void middleForwardback(double leftPower, double rightPower){
        middleleft.setPower(leftPower*leftErrorAdjustment);
        middleright.setPower(rightPower*rightErrorAdjustment);
    }*/

    public double differenceInPower(double motorPower, double newPower) {
        double powerDiff = 0.0;
        powerDiff = Math.abs(newPower - motorPower);
        return powerDiff;
    }

    private double powerDiffLimit = 1.5;

    public boolean isNewPowerWithinLimits(double leftPower, double rightPower) {
        boolean newPoweWithinLimits = false;
        if(differenceInPower(frontleft.getPower(), leftPower) <= powerDiffLimit &&
                differenceInPower(backleft.getPower(), leftPower) <= powerDiffLimit &&
                differenceInPower(frontright.getPower(), rightPower) <= powerDiffLimit &&
                differenceInPower(backright.getPower(), rightPower) <= powerDiffLimit) {
            newPoweWithinLimits = true;
        }
        return newPoweWithinLimits;
    }

    public void setMotorPower(DcMotorEx wheel, double power) {
        double wheelPower = wheel.getPower();

        if(power == 0) {
            if(wheelPower < 0) {
                wheelPower = +0.2;
            } else if (wheelPower > 0){
                wheelPower = -0.2;
            }
        } else {
            wheelPower = power;
        }
        wheel.setPower(wheelPower);
    }

    //Tank Drive mode methods BEGIN
    //moveForward for TeleOp
    //The left and right powers are controlled by the left and right y axes
    public void move_forwardback_rotate( double leftPower, double rightPower){
    //    if(isNewPowerWithinLimits(leftPower, rightPower)) {
    /*        frontleft.setPower(leftPower*frontleftErrorAdjustment);
            backleft.setPower(-leftPower*backleftErrorAdjustment);
            frontright.setPower(-rightPower*frontrightErrorAdjustment);
            backright.setPower(rightPower*backrightErrorAdjustment);*/
        setMotorPower(frontleft, leftPower*frontleftErrorAdjustment);
        setMotorPower(backleft, -leftPower*backleftErrorAdjustment);
        setMotorPower(frontright, -rightPower*frontrightErrorAdjustment);
        setMotorPower(backright, rightPower*backrightErrorAdjustment);

        //    middleleft.setPower(-leftPower*leftErrorAdjustment);
        //    middleright.setPower(rightPower*rightErrorAdjustment);
    //    }
    }

    //moveSide for TeleOp
    //The left and right powers are controlled by the left and right x axes
    public void move_side( double leftPower, double rightPower){
        //if(isNewPowerWithinLimits(leftPower, rightPower)) {
          /*  frontleft.setPower(-leftPower * frontleftErrorAdjustment);
            backleft.setPower(-leftPower * backleftErrorAdjustment);
            frontright.setPower(-rightPower * frontrightErrorAdjustment);
            backright.setPower(-rightPower * backrightErrorAdjustment);*/
        setMotorPower(frontleft, -leftPower*frontleftErrorAdjustment);
        setMotorPower(backleft, -leftPower*backleftErrorAdjustment);
        setMotorPower(frontright, -rightPower*frontrightErrorAdjustment);
        setMotorPower(backright, -rightPower*backrightErrorAdjustment);

        //}
    }

    //Tank Drive mode methods END

    //Non Tank Drive mode methods BEGIN

    public void move_right(double power) {
        /*frontleft.setPower(-power*frontleftErrorAdjustment );
        backleft.setPower(-power*backleftErrorAdjustment);
        frontright.setPower(-power*frontrightErrorAdjustment);
        backright.setPower(-power*backrightErrorAdjustment);*/
        setMotorPower(frontleft, -power*frontleftErrorAdjustment);
        setMotorPower(backleft, -power*backleftErrorAdjustment);
        setMotorPower(frontright, -power*frontrightErrorAdjustment);
        setMotorPower(backright, -power*backrightErrorAdjustment);
    }

    public void move_left(double power) {
/*        frontleft.setPower(power*frontleftErrorAdjustment);
        backleft.setPower(power*backleftErrorAdjustment);
        frontright.setPower(power*frontrightErrorAdjustment);
        backright.setPower(power*backrightErrorAdjustment);*/
        setMotorPower(frontleft, power*frontleftErrorAdjustment);
        setMotorPower(backleft, power*backleftErrorAdjustment);
        setMotorPower(frontright, power*frontrightErrorAdjustment);
        setMotorPower(backright, power*backrightErrorAdjustment);
    }

    public void rotateMode(double power) {
/*        frontleft.setPower(-power* frontleftErrorAdjustment);
        backleft.setPower(power* backleftErrorAdjustment);
        frontright.setPower(-power*frontrightErrorAdjustment);
        backright.setPower(power*backrightErrorAdjustment);*/
        setMotorPower(frontleft, -power*frontleftErrorAdjustment);
        setMotorPower(backleft, power*backleftErrorAdjustment);
        setMotorPower(frontright, -power*frontrightErrorAdjustment);
        setMotorPower(backright, power*backrightErrorAdjustment);
    }

    public void expandCollapse(double power){
/*        frontleft.setPower(power* frontleftErrorAdjustment);
        backleft.setPower(power* backleftErrorAdjustment);
        frontright.setPower(-power*frontrightErrorAdjustment);
        backright.setPower(-power*backrightErrorAdjustment);*/
        setMotorPower(frontleft, power*frontleftErrorAdjustment);
        setMotorPower(backleft, power*backleftErrorAdjustment);
        setMotorPower(frontright, -power*frontrightErrorAdjustment);
        setMotorPower(backright, -power*backrightErrorAdjustment);
    }

    public void moveForward (double power) {
/*        frontleft.setPower(power*frontleftErrorAdjustment);
        backleft.setPower(-power*backleftErrorAdjustment);
        frontright.setPower(-power*frontrightErrorAdjustment);
        backright.setPower(power*backrightErrorAdjustment);*/
        setMotorPower(frontleft, power*frontleftErrorAdjustment);
        setMotorPower(backleft, -power*backleftErrorAdjustment);
        setMotorPower(frontright, -power*frontrightErrorAdjustment);
        setMotorPower(backright, power*backrightErrorAdjustment);
    }

    public void moveSide(double power) {
/*        frontleft.setPower(-power*frontleftErrorAdjustment);
        backleft.setPower(-power*backleftErrorAdjustment);
        frontright.setPower(-power*frontrightErrorAdjustment);
        backright.setPower(-power*backrightErrorAdjustment);*/
        setMotorPower(frontleft, -power*frontleftErrorAdjustment);
        setMotorPower(backleft, -power*backleftErrorAdjustment);
        setMotorPower(frontright, -power*frontrightErrorAdjustment);
        setMotorPower(backright, -power*backrightErrorAdjustment);
    }

    public void moveAllDirections(double lefty, double righty, double leftx, double rightx){
        /*frontleft.setPower(lefty + leftx + rightx);
        backleft.setPower(lefty + leftx - rightx);
        frontright.setPower(lefty + leftx + rightx);
        backright.setPower(lefty + leftx - rightx);*/
        setMotorPower(frontleft, (lefty - leftx - righty + rightx)*frontleftErrorAdjustment);
        setMotorPower(backleft, (-lefty - leftx + righty + rightx)*backleftErrorAdjustment);
        setMotorPower(frontright, (-lefty - leftx - righty - rightx)*frontrightErrorAdjustment);
        setMotorPower(backright, (lefty - leftx + righty - rightx)*backrightErrorAdjustment);
    }

    //Non Tank Drive mode methods END

    public void liftArm(double power) {
        arm.setPower(power);

    }

    public int moveArmSideways(int level, int currentLevel, String alliance) throws InterruptedException {
        //Red  LB 1.0 For Floor
        //Blue RB 0.0 For Floor
        int encoderPosition = 0;
        Double armPower = 0.5;
        int allianceSpecificMultiplier = 1;
        if(alliance!=null && "Red".equalsIgnoreCase(alliance)) {
            allianceSpecificMultiplier = -1;
        }
        if (level == 0) {
            if (currentLevel == 0) { //fixed
                if(alliance!=null && "Red".equalsIgnoreCase(alliance)) {
                    arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    arm.setTargetPosition(800 * allianceSpecificMultiplier);
                    //claw.moveSwing(0.0); Commenting so that I can move it to common method position for drop
                    arm.setPower(armPower);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    encoderPosition = 800 * allianceSpecificMultiplier;
                } else {
                    arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    arm.setTargetPosition(450 * allianceSpecificMultiplier);
                    //claw.moveSwing(0.0); Commenting so that I can move it to common method position for drop
                    arm.setPower(armPower);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    encoderPosition = 450 * allianceSpecificMultiplier;
                }
            }
        } else if (level == 1) {
            if (currentLevel == 0) { //fixed
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setTargetPosition(1100 * allianceSpecificMultiplier); //dropped at red 2nd level 1100
                //claw.moveSwing(0.0);
                arm.setPower(armPower);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                encoderPosition = 1100 * allianceSpecificMultiplier;
            }
        } else if (level == 2) {
            if (currentLevel == 0) { //fixed
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setTargetPosition(1650 * allianceSpecificMultiplier);// dropped at red top level 1700 /1650
                //claw.moveSwing(0.0);
                arm.setPower(armPower);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                encoderPosition = 1650 * allianceSpecificMultiplier;
            }
        }
        return encoderPosition;
    }

    public int positionForDropSidewaysAuto(int level, String alliance) throws InterruptedException {
        int encoderPosition = 0;
        if(level == 0) {
            encoderPosition = moveArmSideways(level, 0, alliance);
            //claw.moveSwing(0.0);
            //Thread.sleep(2000);
            if(alliance.equalsIgnoreCase("Red")) {
                claw.moveSwing(0.2);
                sleep(700);
                move_left_auto(0.8, 24, 10.0); //Red Level 0
                claw.moveFloor(1.0);
                sleep(1000);
           } else {
                claw.moveSwing(-0.2);
                sleep(700);
                move_right_auto(0.8, 22, 10.0); //Blue Level 0
                claw.moveFloor(0.0);
                sleep(1000);
            }
            //drop using intake just in case floor didn't drop
            claw.reverseIntake(0.15);
            sleep(1000);
            claw.stopIntake();
        } else if (level == 1) {
            encoderPosition = moveArmSideways(level, 0, alliance);
            sleep(2000);
            if (alliance.equalsIgnoreCase("Red")) {
                move_left_auto(0.8, 24, 10.0);//Red Level 1
                claw.moveFloor(1.0);
                sleep(1000);
            } else {
                move_right_auto(0.8, 21, 10.0);//Blue Level 1
                claw.moveFloor(1.0);
                sleep(1000);
            }
            //drop using intake just in case floor didn't drop
            claw.reverseIntake(0.3);
            sleep(1000);
            claw.stopIntake();
        } else if (level == 2) {
            encoderPosition = moveArmSideways(level, 0, alliance);
            sleep(2000);
            if (alliance.equalsIgnoreCase("Red")) {
                move_left_auto(0.8, 25, 10.0); //Red Level 2
                claw.moveFloor(1.0);
                sleep(1000);
            } else {
                move_right_auto(0.8, 24, 10.0); //Blue Level 2
                claw.moveFloor(1.0);
                sleep(1000);
            }
            //drop using intake just in case floor didn't drop
            claw.reverseIntake(0.3);
            sleep(1000);
            claw.stopIntake();
        }
        //move away after drop
        if(alliance.equalsIgnoreCase("Red")) {
            move_right_auto(0.3, 10, 10.0);
        } else {
            move_left_auto(0.3, 10, 10.0);
        }
        claw.moveFloor(0.5);
        return encoderPosition;
    }

    public void armToEncoderPosition(int encoderPosition) {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(-encoderPosition);
        arm.setPower(0.5);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //The below code was for R1 (2nd meet)
    public void moveArm(int level, int currentLevel) {
        if (level == 0) {
            if (currentLevel == 1) {
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setTargetPosition(-2100);
                claw.moveSwing(-0.3);
                arm.setPower(-0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            } else if (currentLevel == 2) {
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setTargetPosition(-3500);
                claw.moveSwing(-0.3);
                arm.setPower(-0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        } else if (level == 1) {
            if (currentLevel == 0) {
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setTargetPosition(2100);
                claw.moveSwing(0.5);
                arm.setPower(0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else if (currentLevel == 2) {
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setTargetPosition(-1400);
                claw.moveSwing(-0.3);
                arm.setPower(-0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        } else if (level == 2) {
            if (currentLevel == 1) {
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setTargetPosition(1400);
                claw.moveSwing(0.5);
                arm.setPower(0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else if (currentLevel == 0) {
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setTargetPosition(3500);// dropped at 2nd level
                claw.moveSwing(0.5);
                arm.setPower(0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
    }

    public void positionForDrop (int position, int currentLevel) throws InterruptedException {
        if(position == 2 || position == 3 || position == 9) {
            moveArm(2,currentLevel);
            sleep(3000);
            claw.moveSwing(0.0);
            sleep(2000);
        } else if(position == 1) {
            moveArm(1,currentLevel);
            sleep(3000);
            claw.moveSwing(0.0);
            sleep(2000);
        } else if(position == 0) {
            arm.setPower(0.5);
            sleep(500);
            claw.moveSwing(-0.5);
            arm.setPower(0.0);
            sleep(600);
            claw.moveSwing(0.0);
        }
    }
}