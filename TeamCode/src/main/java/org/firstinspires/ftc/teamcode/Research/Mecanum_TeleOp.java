/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;




@TeleOp(name = "Mecanum_TeleOp_test")
@Disabled
public class Mecanum_TeleOp extends LinearOpMode {

    //Declaring Motors
    private DcMotor Leftmotor1;
    private DcMotor Rightmotor1;
    private DcMotor Leftmotor2;
    private DcMotor Rightmotor2;

    double lefty = 0.0;
    double leftx = 0.0;
    double righty = 0.0;
    double rightx = 0.0;

    public void move_forwardback(){
        Leftmotor1.setPower(lefty);
        Leftmotor2.setPower(-lefty);
        Rightmotor1.setPower(-lefty);
        Rightmotor2.setPower(lefty);
    }

    public void move_side(){
        Leftmotor1.setPower(leftx);
        Leftmotor2.setPower(-leftx);
        Rightmotor1.setPower(-leftx);
        Rightmotor2.setPower(leftx);
    }

    //top right, bottom left diagonal directions
    public void diagonal(){
        if (rightx>0 && lefty>0) {
            Leftmotor1.setPower(lefty);
            Rightmotor2.setPower(-lefty);
        }
        else if (rightx<0 && lefty<0){
            Leftmotor1.setPower(-lefty);
            Rightmotor2.setPower(lefty);
        }

    }
    //top left, bottom right diagonal directions
    public void diagonal2(){
        if (rightx<0 && lefty>0) {
            Rightmotor1.setPower(-lefty);
            Leftmotor2.setPower(lefty);
        }
        else if (rightx>0 && lefty<0){
            Rightmotor1.setPower(lefty);
            Leftmotor2.setPower(-lefty);
        }

    }



    @Override
    public void runOpMode() throws InterruptedException {
        //Hardware Mapping
        Rightmotor1 = hardwareMap.get(DcMotor.class, "Topright");
        Leftmotor1 = hardwareMap.get(DcMotor.class, "Topleft");
        Rightmotor2 = hardwareMap.get(DcMotor.class, "Bottomright");
        Leftmotor2 = hardwareMap.get(DcMotor.class, "Bottomleft");

        waitForStart();
        while (opModeIsActive()) {
            double reset = 0;
            Rightmotor1.setPower(reset);
            Leftmotor1.setPower(reset);
            Leftmotor2.setPower(reset);
            Rightmotor2.setPower(reset);

            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = gamepad1.right_stick_x;

            move_forwardback();
            move_side();
            diagonal();
            diagonal2();

            //Displays power of each motor on the robot controller
            telemetry.addData("top left motor", "%.2f", Leftmotor1.getPower());
            telemetry.addData("top right motor", "%.2f", Rightmotor1.getPower());
            telemetry.addData("bottom left motor", "%.2f", Leftmotor2.getPower());
            telemetry.addData("bottom right motor", "%.2f", Rightmotor2.getPower());

            telemetry.addData("lefty", "%.2f", lefty);
            telemetry.addData("leftx", "%.2f", leftx);

            telemetry.addData("rightx", "%.2f", rightx);
            telemetry.addData("righty", "%.2f", righty);

            telemetry.update();


        }




    }


}