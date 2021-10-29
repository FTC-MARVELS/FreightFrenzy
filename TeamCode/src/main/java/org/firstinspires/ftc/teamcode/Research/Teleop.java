package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "op (Teleop)", group = "")
@Disabled
public class Teleop extends LinearOpMode {


    private DcMotor topright;
    private DcMotor topleft;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        topright = hardwareMap.get(DcMotor.class, "right");
        topleft = hardwareMap.get(DcMotor.class, "left");

        // Put initialization blocks here.
        waitForStart();
        double left = 0;
        double right = 0;
        topright.setPower(right);
        topleft.setPower(left);
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // bottomright.setPower(gamepad1.left_stick_y);
                left = gamepad1.left_stick_y;
                right = -gamepad1.right_stick_y;
                topleft.setPower(-right);

                // bottomleft.setPower(gamepad1.right_stick_y);
                topright.setPower(-left);
                telemetry.addData("left motor",  "%.2f", topleft.getPower());
                telemetry.addData("right motor", "%.2f", topright.getPower());

                telemetry.addData("left",  "%.2f", left);
                telemetry.addData("right", "%.2f", right);
                telemetry.update();


            }
        }
    }
}