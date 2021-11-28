/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode;

import android.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;


@TeleOp(name = "EPIC_TeleOp")
public class EPIC_TeleOp extends LinearOpMode {
    //Configuration used: 6wheelConfig
    Mecanum_Wheels wheels;
    double lefty = 0.0;
    double leftx = 0.0;
    double righty = 0.0;
    double rightx = 0.0;
    double liftPower = 0.0;
    double rotateArm = 0.0;
    double powerfactor = 0.6;

    @Override
    public void runOpMode() throws InterruptedException {
        //Hardware Mapping
        wheels = new Mecanum_Wheels(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        wheels.initialize();
        //wheels.rightErrorAdjustment = 0.93;//1;
        Spinner spinner = new Spinner(hardwareMap);
        wheels.telemetry = telemetry;
        wheels.parent = this;
        wheels.leftErrorAdjustment = 0.81;
        wheels.rightErrorAdjustment = 1.2;
        //double wheelPower = 0.6;
        double carouselPower = 0.58;
        claw.parent = this;
        claw.telemetry = this.telemetry;
        double clawPower = lefty/10;
        double needPos = clawPower+claw.arm.getPosition();


        waitForStart();
        //claw.initiateLift();
        while (opModeIsActive()) {

//            //telemetry.addData("Finger 1 position", claw.clawFinger1.getPosition());
//            //telemetry.addData("Finger 2 position", claw.clawFinger2.getPosition());
//            //telemetry.update();
//            //mecanumWheels.initialize();
//
            lefty = -gamepad1.left_stick_y*powerfactor;
            leftx = gamepad1.left_stick_x*powerfactor;
            righty = gamepad1.right_stick_y*powerfactor;
            rightx = gamepad1.right_stick_x*powerfactor;


            liftPower = gamepad2.right_stick_y;
            rotateArm = gamepad2.left_stick_y;

            //lefty = gamepad2.left_stick_y;
            boolean dpad_left = gamepad1.dpad_left;
            boolean dpad_right = gamepad1.dpad_right;
            boolean b = gamepad1.b;
            boolean x = gamepad1.x;
            boolean y = gamepad1.y;
            boolean a = gamepad1.a;

            boolean a2 = gamepad2.a;
            boolean y2 = gamepad2.y;
//            //if(!dpad_left && !dpad_right)
//            //else
            if(dpad_left) {
                wheels.Collapse();
                spinner.setPower(0);
            }
            else if(dpad_right) {
                wheels.Expand();
                spinner.setPower(0);
            }

            else if(x)
                spinner.setPower(carouselPower);
            else if(b)
                spinner.setPower(-carouselPower);
            else{
                wheels.move(lefty,righty,leftx,rightx);
                spinner.setPower(0);
            }

            boolean leftBumper = gamepad2.left_bumper;
            boolean rightBumper = gamepad2.right_bumper;

            claw.rotate(leftBumper, rightBumper);

            if(y2) {
                claw.grab();
            }
                else{
                    claw.release();
            }
//
////            wheels.leftMotorY(-lefty);
////            wheels.rightMotorY(-righty);
////            wheels.rightMotorX(rightx);
////            wheels.leftMotorX(leftx);




            

            telemetry.addData("lefty", "%.2f", lefty);
            telemetry.addData("leftx", "%.2f", leftx);

            telemetry.addData("rightx", "%.2f", rightx);
            telemetry.addData("righty", "%.2f", righty);

            telemetry.update();
        }
    }
}

