/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;


@TeleOp(name = "MAS_TeleOp")
@Disabled

public class MAS_TeleOp extends LinearOpMode {
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
        mecanumWheels.rightErrorAdjustment = 0.93;//1;
        waitForStart();
        while (opModeIsActive()) {

            mecanumWheels.initialize();

            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;

            boolean test = false;
            if(gamepad1.x) {
                test = true;
            }else {
                test = false;
            }

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
                //mecanumWheels.middleForwardback(lefty, righty);
                //middle_forwardback();
            }

            if(modeThree==true) {
                //slow_forwardback();
                //slow_side();
                mecanumWheels.move_forwardback_rotate(lefty*0.5,righty*0.5);
                mecanumWheels.move_side(leftx*0.5, rightx*0.5);
                //For testing only
                //spinner.setPower(-0.58);
                //sleep(1000);
                //spinner.setPower(0);

                //End testing only
            //} else {
             //   spinner.setPower(0);
            }

            if (gamepad2.right_bumper) {
             //   claw.openClaws();
            }
            else {
             //   claw.closeClaws();
            }

            //if(gamepad2.left_bumper) {
             //claw.raiseWrist(gamepad2.right_stick_y);
            //}
            //
            //
            /*else {
                claw.restWrist();
            }*/

            if(gamepad2.a) {
                spinner.setPower(0.55);
            } else if(gamepad2.b) {
                spinner.setPower(-0.55);
            } else {
                spinner.setPower(0);
            }

            //spinner.setPower(gamepad2.right_stick_x*0.7);

             mecanumWheels.liftArm(-gamepad2.left_stick_y *0.75 + 0.05);
            mecanumWheels.move_forwardback_rotate(lefty, righty);
          //  mecanumWheels.middleForwardback(lefty, righty); //adding omni wheel motion in regular mode

            }
    }
}

