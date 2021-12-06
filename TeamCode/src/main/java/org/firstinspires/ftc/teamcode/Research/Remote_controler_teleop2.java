/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;




@TeleOp(name = "Remote_controler_teleop2")
@Disabled
public class Remote_controler_teleop2 extends LinearOpMode {

    //Declaring Motors
    private DcMotor Leftmotor;
    private DcMotor Rightmotor;

    @Override
    public void runOpMode() throws InterruptedException {
        //Hardware Mapping
        Rightmotor=hardwareMap.get(DcMotor.class,"Topright");
        Leftmotor=hardwareMap.get(DcMotor.class,"Topleft");

        waitForStart();
        while (opModeIsActive()) {
            double reset = 0;
            Rightmotor.setPower(reset);
            Leftmotor.setPower(reset);

            //Make variables for the sticks on the controller
            double lefty = gamepad1.left_stick_y;
            double rightx = gamepad1.right_stick_x;

            //lets the left stick control forward and backward movement of robot
            Rightmotor.setPower(lefty);
            Leftmotor.setPower(lefty);

            //Lets the right stick x make the robot rotate
            Rightmotor.setPower(-rightx);
            Leftmotor.setPower(rightx);

            //Displays power of each motor on the robot controller
            telemetry.addData("left motor", "%.2f", Leftmotor.getPower());
            telemetry.addData("right motor", "%.2f", Rightmotor.getPower());

            telemetry.addData("left", "%.2f", lefty);
            telemetry.addData("right", "%.2f", rightx);
            telemetry.update();


        }




    }


}
