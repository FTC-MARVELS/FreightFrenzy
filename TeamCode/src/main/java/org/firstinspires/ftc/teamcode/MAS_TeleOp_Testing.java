/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;


@TeleOp(name = "MAS_TeleOp_Testing")
public class MAS_TeleOp_Testing extends LinearOpMode {
    //Configuration used: 6wheelConfig
    double lefty = 0.0;
    double leftx = 0.0;
    double righty = 0.0;
    double rightx = 0.0;


    @Override
    public void runOpMode() throws InterruptedException {
        //Hardware Mapping
        Mecanum_Wheels mecanumWheels = new Mecanum_Wheels(hardwareMap);
        Spinner spinner = new Spinner(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        //mecanumWheels.rightErrorAdjustment = 0.93;//1;
        //claw.bucket1.setPosition(0.0);
        //claw.bucket2.setPosition(0.0);
        waitForStart();
        while (opModeIsActive()) {

            mecanumWheels.initialize();

            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;

            boolean modeTwo = false;
            if(gamepad1.right_bumper) {
                modeTwo = true;
            } else{
                modeTwo = false;
            }
            boolean modeThree = false;
            if(gamepad1.left_bumper){
                modeThree = true;
            }
            else{
                modeThree=false;
            }
            if(modeTwo==true){
                mecanumWheels.move_side(-leftx, -rightx);
            }

            if(modeThree==true) {
                mecanumWheels.move_forwardback_rotate(lefty*0.5,righty*0.5);
                mecanumWheels.move_side(leftx*0.5, rightx*0.5);
            }

            /*if (gamepad2.right_bumper) {
                claw.grabObject();
            } else if(gamepad2.left_bumper) {
                claw.dropObject();
            } else {
                claw.stopGripper();
            }*/

            claw.rotateGripper(gamepad2.right_stick_y);

            if(gamepad2.a) {
                spinner.setPower(0.67);
                //spinner.setVelocity(2000);
            } else if(gamepad2.b) {
                spinner.setPower(-0.67);
                //spinner.setVelocity(-2000);
            } else if(gamepad2.x) {
                spinner.setPower(0.55);
                //spinner.setVelocity(2000);
            } else if(gamepad2.y) {
                spinner.setPower(-0.55);
                //spinner.setVelocity(-2000);
            } else {
                spinner.setPower(0);
                //spinner.setVelocity(0);
            }

            //spinner.setPower(gamepad2.right_stick_x*0.7);

            mecanumWheels.liftArm(-gamepad2.left_stick_y);
            mecanumWheels.move_forwardback_rotate(lefty*0.85, righty);
            claw.moveBucket(-gamepad2.left_stick_y);
            if(gamepad2.right_bumper) {
                claw.moveBucket(0.8);
            } else if(gamepad2.left_bumper) {
                claw.moveBucket(-0.8);
            } else {
                claw.moveBucket(0);
            }

            telemetry.addData("Servo power" , claw.bucket1.getPower());
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
}

