// original code from: https://docs.google.com/document/d/1tyWrXDfMidwYyP_5H4mZyVgaEswhOC35gvdmP-V-5hA/edit#
// Motor PIDF Tuning Guide
// This OpMode will determine and display the maximum velocities for drive motors to telemetry.
// See the above link for how to then use these maximum velocities to calculating PIDF Values.

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp
public class PidfMaxVelocityTest extends LinearOpMode {
    DcMotorEx leftMotor, leftMotor2, rightMotor, rightMotor2;
    double currentLeftVelocity;
    double currentLeft2Velocity;
    double currentRightVelocity;
    double currentRight2Velocity;
    double maxLeftVelocity = 0.0;
    double maxLeft2Velocity = 0.0;
    double maxRightVelocity = 0.0;
    double maxRight2Velocity = 0.0;

    // initialize an instance of FtcDashboard;
    FtcDashboard dashboard = FtcDashboard.getInstance();

    // predefine some variables for dashboard configuration
    public static double driveTimout = 2.1;   // don't allow robot to move too far !!!

    // called when init button is pressed
    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "LeftDrive");
        leftMotor2 = hardwareMap.get(DcMotorEx.class, "LeftDrive2");
        rightMotor = hardwareMap.get(DcMotorEx.class, "RightDrive");
        rightMotor2 = hardwareMap.get(DcMotorEx.class, "RightDrive2");

        // we won't be using encoders, but this will prevent motion on setPower commands, and instead wait for RunMode.RUN... commands
        leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // use maximum power
        leftMotor.setPower(1);
        leftMotor2.setPower(1);
        rightMotor.setPower(1);
        rightMotor2.setPower(1);

        // wait for start button to be pressed
        waitForStart();

        resetStartTime();
        while (opModeIsActive() && getRuntime() < driveTimout) {  // stop after enough time to achieve max velocity, BUT BEFORE HITTING WALL !!!

            // motion will start here
            leftMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            leftMotor2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            rightMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            rightMotor2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

            currentLeftVelocity = leftMotor.getVelocity();
            currentLeft2Velocity = leftMotor2.getVelocity();
            currentRightVelocity = rightMotor.getVelocity();
            currentRight2Velocity = rightMotor2.getVelocity();

            if (currentLeftVelocity > maxLeftVelocity) {
                maxLeftVelocity = currentLeftVelocity;
            }

            if (currentLeft2Velocity > maxLeft2Velocity) {
                maxLeft2Velocity = currentLeft2Velocity;
            }

            if (currentRightVelocity > maxRightVelocity) {
                maxRightVelocity = currentRightVelocity;
            }

            if (currentRight2Velocity > maxRight2Velocity) {
                maxRight2Velocity = currentRight2Velocity;
            }

            // continuously display updated current velocities
            telemetry.addData("current left velocity", currentLeftVelocity);
            telemetry.addData("current left2 velocity", currentLeft2Velocity);
            telemetry.addData("current right velocity", currentRightVelocity);
            telemetry.addData("current right2 velocity", currentRight2Velocity);
            telemetry.update();
        }

        // stop all motors
        leftMotor.setPower(0);
        leftMotor2.setPower(0);
        rightMotor.setPower(0);
        rightMotor2.setPower(0);
        sleep(1500);

        // display maximum velocities
        telemetry.log().add("maximum left velocity: " + maxLeftVelocity);
        telemetry.log().add("maximum left2 velocity: " + maxLeft2Velocity);
        telemetry.log().add("maximum right velocity: " + maxRightVelocity);
        telemetry.log().add("maximum right2 velocity: " + maxRight2Velocity);
        telemetry.update();
        sleep(15000);
    }
}