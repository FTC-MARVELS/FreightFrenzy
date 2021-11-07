/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode;

import android.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;


@TeleOp(name = "EPIC_TeleOp")
public class EPIC_TeleOp extends LinearOpMode {
    //Configuration used: 6wheelConfig
    double lefty = 0.0;
    double leftx = 0.0;
    double righty = 0.0;
    double rightx = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        //Hardware Mapping
        //Mecanum_Wheels mecanumWheels = new Mecanum_Wheels(hardwareMap);
        //mecanumWheels.rightErrorAdjustment = 0.93;//1;
        //Spinner spinner = new Spinner(hardwareMap);
        //double wheelPower = 0.6;
        double carouselPower = 0.6;
        Claw claw = new Claw(hardwareMap);
        claw.parent = this;
        claw.telemetry = this.telemetry;


        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("Finger 1 position", claw.clawFinger1.getPosition());
            telemetry.addData("Finger 2 position", claw.clawFinger2.getPosition());
            telemetry.update();
            //mecanumWheels.initialize();

            //lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;
            //lefty = gamepad2.left_stick_y;
            boolean dpad_left = gamepad1.dpad_left;
            boolean dpad_right = gamepad1.dpad_right;
            boolean b = gamepad1.b;
            boolean x = gamepad1.x;
            boolean y = gamepad1.y;
            boolean a = gamepad1.a;


//            if(!gamepad1.left_bumper)
//            {
//                if(lefty!=0  && rightx==0) {
//                    mecanumWheels.moveY(lefty);
//                }
//                else if(lefty==0 && rightx!=0) {
//                    mecanumWheels.moveX(rightx);
//                }
//                else if(dpad_left == true){
//                    mecanumWheels.Expand();
//                }
//                else if(dpad_right == true) {
//                    mecanumWheels.Collapse();
//                }
//                else if(x == true) {
//                    mecanumWheels.TurnLeft();
//                }
//                else if(b == true) {
//                    mecanumWheels.TurnRight();
//
//                }
//                else if(y == true){
//                    spinner.setPower(carouselPower);
//                }
//                else if(a == true) {
//                    spinner.setPower(-carouselPower);
//                }
//                else{
//                    spinner.setPower(0);
//                }
            if(gamepad1.dpad_left){
            //    if(gamepad1.y==true){
                    telemetry.addData("lefty", "%.2f", lefty);

                    telemetry.update();
                    lefty = claw.clawFinger1.getPosition() + 0.1;
                    if (lefty>1)
                        lefty = 1;
                    claw.clawFinger1.setPosition(lefty);
                    lefty = claw.clawFinger2.getPosition() + 0.1;
                    if (lefty>1)
                        lefty = 1;
                    claw.clawFinger2.setPosition(lefty);

                    telemetry.addData("lefty", "%.2f", lefty);

                    telemetry.update();
                    gamepad1.y = false;
                }
            //if(gamepad1.a==true){
            if(gamepad1.dpad_right){
                telemetry.addData("lefty", "%.2f", lefty);

                telemetry.update();
                lefty=lefty - 0.1;
                if (lefty<-0.4)
                    lefty = -0.4;
                claw.clawFinger1.setPosition(lefty);
                claw.clawFinger2.setPosition(lefty);

                telemetry.addData("lefty", "%.2f", lefty);

                telemetry.update();
                gamepad1.a = false;
            }
            //}


//            boolean modeTwo = false;
//            if(gamepad1.right_bumper) {
//                modeTwo = true;
//            } else{
//                modeTwo = false;
//            }
//            boolean modeThree = false;
//            if(gamepad1.left_bumper){
//                modeThree = true;
//            }
//            else{
//                modeThree=false;
//            }
//            if(modeTwo==true){
//               // move_side();
//                mecanumWheels.move_side(leftx, rightx);
//                //middle_forwardback();
//            }
//
//            if(modeThree==true) {
//                //slow_forwardback();
//                //slow_side();
//                mecanumWheels.move_forwardback_rotate(lefty/2,righty/2);
//                mecanumWheels.move_side(leftx/2, rightx/2);
//            }
//            mecanumWheels.move_forwardback_rotate(lefty, righty);
//          //  move_forwardback_rotate();


        //}




            //diagonal();

            //Displays power of each motor on the robot controller
            /*telemetry.addData("front left motor", "%.2f", Frontleft.getPower());
            telemetry.addData("front right motor", "%.2f", Frontright.getPower());
            telemetry.addData("back left motor", "%.2f", Backleft.getPower());
            telemetry.addData("back right motor", "%.2f", Backright.getPower());
            telemetry.addData("middle left motor", "%.2f", Middleleft.getPower());
            telemetry.addData("middle right motor", "%.2f", Middleright.getPower());*/

            telemetry.addData("lefty", "%.2f", lefty);
            telemetry.addData("leftx", "%.2f", leftx);

            telemetry.addData("rightx", "%.2f", rightx);
            telemetry.addData("righty", "%.2f", righty);

            telemetry.update();
        }
    }
}


