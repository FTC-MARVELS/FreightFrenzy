/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;


@TeleOp(name = "MAS_Final_TeleOp")
public class MAS_Final_TeleOp extends LinearOpMode {
    //Configuration used: 6wheelConfig
    double lefty = 0.0;
    double leftx = 0.0;
    double righty = 0.0;
    double rightx = 0.0;
    int StickButton = 0;
    int otherStickButton = 0;
    int finger = 0;
    int dPadButton = 0;
    int homePosition = 0;
    boolean tankDrive = true;
    private static double DEFAULT_ADJUSTMENT = 1.0;

    @Override
    public void runOpMode() throws InterruptedException {
        //Hardware Mapping
        Mecanum_Wheels mecanumWheels = new Mecanum_Wheels(hardwareMap);
        Spinner spinner = new Spinner(hardwareMap);
        Claw claw = new Claw(hardwareMap);


        waitForStart();
        while (opModeIsActive()) {
            mecanumWheels.initialize();

            resetAdjustments(mecanumWheels);

            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;

            if(gamepad1.dpad_down) { //mode button
                if(tankDrive == true) {
                    tankDrive = false;
                } else if(tankDrive == false) {
                    tankDrive = true;
                }
                telemetry.addData("Tank Drive Mode on DPAD Down" , tankDrive);
           }

            if(gamepad2.left_stick_button) {
                if(StickButton != 0) {
                    StickButton = 0;
                } else {
                    if(gamepad2.left_stick_x > 0.05) {
                        StickButton = 1;
                    }
                    if(gamepad2.left_stick_x < -0.05) {
                        StickButton = 2;
                    }
                }
            }


            telemetry.addData("Stick Button :", StickButton);

            telemetry.addData("Tank Drive Mode" , tankDrive);
            telemetry.addData("Left X" , leftx);
            telemetry.addData("Left Y" , lefty);
            telemetry.addData("Right X" , rightx);
            telemetry.addData("Right Y" , righty);

            if(tankDrive) {
                if(gamepad1.right_bumper) {
                    //applyAdjustments(mecanumWheels);
                    resetAdjustments(mecanumWheels);
                    mecanumWheels.move_side(leftx, rightx);
                } else if(gamepad1.left_bumper) {
                    resetAdjustments(mecanumWheels);
                    mecanumWheels.move_forwardback_rotate(lefty*0.5* 1,righty*0.5);
                    mecanumWheels.move_side(leftx*0.5, rightx*0.5);
                } else {
                    resetAdjustments(mecanumWheels);
                    mecanumWheels.move_forwardback_rotate(lefty , righty);
                }
            } else { /*//Not Tank Drive - New mode of driving
                //Right Joy Stick controls all forward, back, left, right motions
                //Left Joy Stick controls expand, collapse, rotate 90 right and rotate 90 left
                if(lefty > 0.01 || lefty < -0.01) {
                    resetAdjustments(mecanumWheels);
                    mecanumWheels.moveForward(lefty);
                    telemetry.addData("Left X" , leftx);
                }
                if(leftx > 0.1 || leftx < 0.1) {
                //if(leftx > 0.1 ) { // moveright
                //    applyAdjustmentsNonTankDrive(mecanumWheels);
                      resetAdjustments(mecanumWheels);
                      mecanumWheels.moveSide(leftx);
                    //mecanumWheels.move_right(leftx, 0.8, 1);
                //} else if (leftx < -0.1) {
                    //mecanumWheels.move_left(leftx, 1, 0.8);
                 //   mecanumWheels.moveSide(leftx, 1, 0.5);
                //
                }
                if(rightx > 0.01 || rightx < -0.01) {
                    resetAdjustments(mecanumWheels);
                    mecanumWheels.expandCollapse(rightx); //Need to test this function
                }
                if(righty > 0.01 || righty < -0.01) {
                    resetAdjustments(mecanumWheels);
                    mecanumWheels.rotateMode(righty);
                }*/
                resetAdjustments(mecanumWheels);
                mecanumWheels.moveAllDirections(lefty,righty,leftx,rightx);
            }

            if(gamepad1.a) {
                //spinner.setPower(1.0);
                spinner.setVelocity(3500);
            } else if(gamepad1.b) {
                //spinner.setPower(-1.0);
                spinner.setVelocity(-3500);
            } else if(gamepad1.x) {
                //spinner.setPower(0.52);
                spinner.setVelocity(1500);
            } else if(gamepad1.y) {
                //spinner.setPower(-0.52);
                spinner.setVelocity(-1500);
            } else {
                spinner.setPower(0);
                //spinner.setVelocity(0);
            }

            if(gamepad2.a) {
                claw.openFinger();
            } else {
                claw.closeFinger();
            }

            if(gamepad2.dpad_left) {
                claw.moveTail(0.4);
                dPadButton = 1;
            } else if(gamepad2.dpad_right) {
                claw.moveTail(-0.6);
                dPadButton = 2;
            } else if(gamepad2.dpad_down) {
                dPadButton = 0;
            } else {
                if(dPadButton == 0) {
                    claw.moveTail(0.0);
                } else if (dPadButton == 1) {
                    claw.moveTail(0.075);
                } else if(dPadButton == 2) {
                    claw.moveTail(-0.075);
                }
            }

            //spinner.setPower(gamepad2.right_stick_x*0.7);
            if(gamepad2.left_trigger > 0.05) {
                claw.startIntake(gamepad2.left_trigger);
            } else if(gamepad2.right_trigger > 0.05) {
                claw.reverseIntake(gamepad2.right_trigger);
            } else {
                claw.stopIntake();
            }

            /*if(gamepad2.left_stick_x > 0.1 || gamepad2.left_stick_x < -0.1) {
                mecanumWheels.liftArm(-gamepad2.left_stick_x*0.8 + 0.01);
            } else {
                mecanumWheels.arm.setPower(0.0);
                mecanumWheels.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }*/
/*
            if(gamepad2.left_stick_x > 0.05) {
                if(gamepad2.left_stick_button){
                    StickButton = 1;
                }
            } else if(gamepad2.left_stick_x < -0.05) {
                if(gamepad2.left_stick_button) {
                    StickButton = 2;
                }
            } else if (gamepad2.left_stick_x > -0.05 && gamepad2.left_stick_x < 0.05) {
                if(gamepad2.left_stick_button) {
                    StickButton = 0;
                }
            } */

            if(gamepad2.left_stick_button) {
                if(StickButton != 0) {
                    StickButton = 0;
                } else {
                    if(gamepad2.left_stick_x > 0.05) {
                        StickButton = 1;
                    }
                    if(gamepad2.left_stick_x < -0.05) {
                        StickButton = 2;
                    }
                }
            }

            if(StickButton == 0) {
                if(gamepad2.left_stick_x != 0) {
                    mecanumWheels.liftArm(-gamepad2.left_stick_x * 0.8);
                } else {
                    mecanumWheels.arm.setPower(0.0);
                    mecanumWheels.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                }
            } else if(StickButton == 1) {
                mecanumWheels.liftArm(-gamepad2.left_stick_x*0.8 - 0.075);
            } else if(StickButton == 2) {
                mecanumWheels.liftArm(-gamepad2.left_stick_x*0.8 + 0.075);
            }

            if(gamepad2.right_stick_button) {
                otherStickButton = 0;
            }

            claw.moveSwing(-gamepad2.right_stick_x*0.8);

            if(gamepad2.right_bumper) {
                claw.moveFloor(0.0);
            } else if(gamepad2.left_bumper) {
                claw.moveFloor(1.0);
            } else {
                claw.moveFloor(0.4);
            }
            /*if(gamepad2.b) {
                mecanumWheels.arm.setPower(0.6);
                mecanumWheels.arm.setTargetPosition(0);
                mecanumWheels.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                mecanumWheels.arm.setPower(0);
            }*
            /*if(gamepad2.left_bumper) {
                claw.moveSwing(0.8);
            } else if(gamepad2.right_bumper) {
                claw.moveSwing(-0.8);
            } else {
                claw.moveSwing(0);
            }*/

            telemetry.addData("Servo power" , claw.swing.getPower());

        /*    int currentLevel = 0;
            if(gamepad2.dpad_left) {
                mecanumWheels.positionForDrop(0, currentLevel);
                currentLevel = 0;
            } else if (gamepad2.dpad_up) {
                mecanumWheels.positionForDrop(1, currentLevel);
                currentLevel = 1;
            } else if (gamepad2.dpad_right) {
                mecanumWheels.positionForDrop(2, currentLevel);
                currentLevel = 2;
            } else if (gamepad2.dpad_down) {
                //Reset Original position
                //Pending code
                mecanumWheels.moveArm(0, currentLevel);
                currentLevel = 0;
            }
*/
            telemetry.update();

            /*int currentLevel = 0;
            int dPad = -1;

            if (gamepad2.dpad_up) {
                dPad = 2;
            } else if (gamepad2.dpad_right) {
                dPad = 1;
            } else if (gamepad2.dpad_down) {
                dPad = 0;
            }

            if (dPad == 2) {
                mecanumWheels.moveArm(2, currentLevel);
                currentLevel = 2;
                dPad = -1;
            } else if (dPad == 1) {
                mecanumWheels.moveArm(1, currentLevel);
                currentLevel = 1;
                dPad = 3;
            } else if (dPad == 0) {
                mecanumWheels.moveArm(0, currentLevel);
                currentLevel = 0;
                dPad = -4;
            }

            telemetry.addData("CurrentLevel", currentLevel);
            telemetry.addData("dPad", dPad);
            telemetry.update();*/
        }
    }

    public void applyAdjustments(Mecanum_Wheels mecanumWheels) {
        telemetry.addData("Left X and Right X values" ,leftx + ": " + rightx);

        if(gamepad1.left_stick_x > 0.05 && gamepad1.right_stick_x > 0.05) {
            mecanumWheels.frontleftErrorAdjustment = 0.7;
            mecanumWheels.backleftErrorAdjustment = 0.7;
            mecanumWheels.frontrightErrorAdjustment = 1.0;
            mecanumWheels.backrightErrorAdjustment = 1.0;
            telemetry.addData("Moving Right : Left Adjustments" , mecanumWheels.frontleftErrorAdjustment + " : " + mecanumWheels.backleftErrorAdjustment);

        } else if(gamepad1.left_stick_x < -0.05 && gamepad1.right_stick_x < -0.05) {
            mecanumWheels.frontleftErrorAdjustment = 1.0;
            mecanumWheels.backleftErrorAdjustment = 1.0;
            mecanumWheels.frontrightErrorAdjustment = 0.7;
            mecanumWheels.backrightErrorAdjustment = 0.7;
            telemetry.addData("Moving Left : Right Adjustments" , mecanumWheels.frontrightErrorAdjustment + " : " + mecanumWheels.backrightErrorAdjustment);

        }
        else {
            mecanumWheels.frontrightErrorAdjustment = DEFAULT_ADJUSTMENT;
            mecanumWheels.backrightErrorAdjustment = DEFAULT_ADJUSTMENT;
            mecanumWheels.frontleftErrorAdjustment = DEFAULT_ADJUSTMENT;
            mecanumWheels.backleftErrorAdjustment = DEFAULT_ADJUSTMENT;
        }
        telemetry.addData("Final Adjustment Values" ,
                mecanumWheels.frontrightErrorAdjustment + " : " + mecanumWheels.backrightErrorAdjustment +
                        mecanumWheels.frontleftErrorAdjustment + " : " + mecanumWheels.backleftErrorAdjustment );
        telemetry.update();
    }

    public void applyAdjustmentsNonTankDrive(Mecanum_Wheels mecanumWheels) {
        telemetry.addData("Left X and Right X values" ,leftx + ": " + rightx);

        if(gamepad1.left_stick_x > 0.05 ) {
            mecanumWheels.frontleftErrorAdjustment = 0.7;
            mecanumWheels.backleftErrorAdjustment = 0.7;
            mecanumWheels.frontrightErrorAdjustment = 1.0;
            mecanumWheels.backrightErrorAdjustment = 1.0;
            telemetry.addData("Moving Right : Left Adjustments" , mecanumWheels.frontleftErrorAdjustment + " : " + mecanumWheels.backleftErrorAdjustment);

        } else if(gamepad1.left_stick_x < -0.05) {
            mecanumWheels.frontleftErrorAdjustment = 1.0;
            mecanumWheels.backleftErrorAdjustment = 1.0;
            mecanumWheels.frontrightErrorAdjustment = 0.7;
            mecanumWheels.backrightErrorAdjustment = 0.7;
            telemetry.addData("Moving Left : Right Adjustments" , mecanumWheels.frontrightErrorAdjustment + " : " + mecanumWheels.backrightErrorAdjustment);
        } else {
            mecanumWheels.frontrightErrorAdjustment = DEFAULT_ADJUSTMENT;
            mecanumWheels.backrightErrorAdjustment = DEFAULT_ADJUSTMENT;
            mecanumWheels.frontleftErrorAdjustment = DEFAULT_ADJUSTMENT;
            mecanumWheels.backleftErrorAdjustment = DEFAULT_ADJUSTMENT;
        }
        telemetry.addData("Final Adjustment Values" ,
                mecanumWheels.frontrightErrorAdjustment + " : " + mecanumWheels.backrightErrorAdjustment +
                        mecanumWheels.frontleftErrorAdjustment + " : " + mecanumWheels.backleftErrorAdjustment );
        telemetry.update();
    }

    public void resetAdjustments(Mecanum_Wheels mecanumWheels) {
        mecanumWheels.frontrightErrorAdjustment = DEFAULT_ADJUSTMENT;
        mecanumWheels.backrightErrorAdjustment = DEFAULT_ADJUSTMENT;
        mecanumWheels.frontleftErrorAdjustment = DEFAULT_ADJUSTMENT;
        mecanumWheels.backleftErrorAdjustment = DEFAULT_ADJUSTMENT;

    }

}

