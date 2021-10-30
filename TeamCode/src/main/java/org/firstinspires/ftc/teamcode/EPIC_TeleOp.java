/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotObjects.Mecanum_Wheels;


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
        Mecanum_Wheels mecanumWheels = new Mecanum_Wheels(hardwareMap);

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
               // move_side();
                mecanumWheels.move_side(leftx, rightx);
                //middle_forwardback();
            }

            if(modeThree==true) {
                //slow_forwardback();
                //slow_side();
                mecanumWheels.move_forwardback_rotate(lefty/2,righty/2);
                mecanumWheels.move_side(leftx/2, rightx/2);
            }
            mecanumWheels.move_forwardback_rotate(lefty, righty);
          //  move_forwardback_rotate();


        }




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


