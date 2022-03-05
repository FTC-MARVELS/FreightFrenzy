/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode;

import android.util.Range;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
@TeleOp(name = "EPIC_FINAL_TELEOP_NEW_ROBOT_RED")
public class EPIC_FINAL_TELEOP_NEW_ROBOT_RED extends LinearOpMode {
    //Configuration used: EPIC4Wheel
    Mecanum_Wheels wheels;
    double lefty = 0.0;
    double lefty2 = 0.0;
    double leftx = 0.0;
    double righty = 0.0;
    double rightx = 0.0;


    @Override
    public void runOpMode() throws InterruptedException {

        wheels = new Mecanum_Wheels(hardwareMap);
        wheels.initialize();
        //wheels.rightErrorAdjustment = 0.93;//1;
        wheels.telemetry = telemetry;
        wheels.parent = this;
        wheels.leftErrorAdjustment = 1;
        wheels.rightErrorAdjustment = 0.9;
        double correctionFactor = 1.444;

        waitForStart();

        while (opModeIsActive()) {

            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = -gamepad1.right_stick_x;

            lefty2 = -gamepad2.left_stick_y;

            boolean y = gamepad1.y;
            boolean a = gamepad1.a;

            if(y) {

                wheels.leftErrorAdjustment = 1;
                wheels.rightErrorAdjustment = 0.92;
            }
            else if(a) {
                wheels.leftErrorAdjustment = 0.5;//wheels.leftErrorAdjustment - 0.05;
                wheels.rightErrorAdjustment = 0.45;//wheels.rightErrorAdjustment - 0.045;
            }

            else {
                wheels.move(lefty,righty,leftx,rightx);
            }

            telemetry.addData("lefty", "%.2f", lefty);
            telemetry.addData("lefty2", "%.2f", lefty2);
            telemetry.addData("leftx", "%.2f", leftx);

            telemetry.addData("rightx", "%.2f", gamepad1.right_stick_x);
            telemetry.addData("righty", "%.2f", gamepad1.right_stick_y);

            telemetry.update();

            telemetry.update();
        }
    }
}