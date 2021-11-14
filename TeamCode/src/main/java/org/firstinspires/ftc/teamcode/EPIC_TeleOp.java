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
    Mecanum_Wheels wheels;
    double lefty = 0.0;
    double leftx = 0.0;
    double righty = 0.0;
    double rightx = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        //Hardware Mapping
        wheels = new Mecanum_Wheels(hardwareMap);
        wheels.initialize();
        //wheels.rightErrorAdjustment = 0.93;//1;
        wheels.telemetry = telemetry;
        wheels.parent = this;
        //Spinner spinner = new Spinner(hardwareMap);
        //double wheelPower = 0.6;
        //wheels = new Mecanum_Wheels(hardwareMap);
        double carouselPower = 0.6;
        //Claw claw = new Claw(hardwareMap);
        //claw.parent = this;
        //claw.telemetry = this.telemetry;


        waitForStart();
        while (opModeIsActive()) {

            //telemetry.addData("Finger 1 position", claw.clawFinger1.getPosition());
            //telemetry.addData("Finger 2 position", claw.clawFinger2.getPosition());
            //telemetry.update();
            //mecanumWheels.initialize();

            lefty = -gamepad1.left_stick_y;
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

            wheels.move(lefty,righty,leftx,rightx);
//            wheels.leftMotorY(-lefty);
//            wheels.rightMotorY(-righty);
//            wheels.rightMotorX(rightx);
//            wheels.leftMotorX(leftx);





            telemetry.addData("lefty", "%.2f", lefty);
            telemetry.addData("leftx", "%.2f", leftx);

            telemetry.addData("rightx", "%.2f", rightx);
            telemetry.addData("righty", "%.2f", righty);

            telemetry.update();
        }
    }
}


