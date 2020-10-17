// autonomous program that drives bot forward a set distance, stops then
//  backs up to the starting point using encoders to measure the distance.
// Modified by team MARVELS to drive in a small rectangle (or square),
//  first clockwise then counter-clockwise.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Drive Encoder", group="Exercises")
//@Disabled
public class DriveRectangleWithEncoder extends LinearOpMode
{
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor middleMotor;

    @Override
    public void runOpMode() throws InterruptedException
    {
        leftMotor = hardwareMap.dcMotor.get("LeftDrive");
        rightMotor = hardwareMap.dcMotor.get("RightDrive");
        middleMotor = hardwareMap.dcMotor.get("MiddleDrive");
        
        // You will need to set this based on your robot's
        // gearing to get forward control input to result in
        // forward motion.
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

for (int i = 0; i < 3; i++)     // move forward then back, 3 times
{
        // reset encoder counts kept by motors.
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set motors to run forward for 5000 encoder counts.
        leftMotor.setTargetPosition(1500);
        rightMotor.setTargetPosition(1500);
        middleMotor.setTargetPosition(1500);
        
        
        // set motors to run to target encoder position and stop with brakes on.
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        telemetry.addData("Mode", "waiting");
        telemetry.update();

        // wait for start button.

        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

// Rectangle side 1

        // set both motors to 25% power. Movement will start. Sign of power is
        // ignored as sign of target encoder position controls direction when
        // running to position.

        leftMotor.setPower(0.25);
        rightMotor.setPower(0.25);

        // wait while opmode is active and left motor is busy running to position.

        while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", leftMotor.getCurrentPosition() + "  busy=" + leftMotor.isBusy());
            telemetry.addData("encoder-fwd-right", rightMotor.getCurrentPosition() + "  busy=" + rightMotor.isBusy());
            telemetry.addData("encoder-fwd-middle", middleMotor.getCurrentPosition() + "  busy=" + middleMotor.isBusy());
            telemetry.update();
            idle();
        }

        // set motor power to zero to turn off motors. The motors stop on their own but
        // power is still applied so we turn off the power.

        // Preexisting two commands to set power to zero are now commented out,
        //  because this prevented breaking action and allowed the robot to coast.
        // leftMotor.setPower(0.0);
        // rightMotor.setPower(0.0);

        // wait 5 sec so you can observe the final encoder position.

        resetStartTime();

        while (opModeIsActive() && getRuntime() < 5)
        {
            telemetry.addData("encoder-fwd-left-end", leftMotor.getCurrentPosition());
            telemetry.addData("encoder-fwd-right-end", rightMotor.getCurrentPosition());
            telemetry.addData("encoder-fwd-middle-end", middleMotor.getCurrentPosition());
            telemetry.update();
            idle();
        }

        middleMotor.setPower(0.25);
        
        while (opModeIsActive() && middleMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", leftMotor.getCurrentPosition() + "  busy=" + leftMotor.isBusy());
            telemetry.addData("encoder-fwd-right", rightMotor.getCurrentPosition() + "  busy=" + rightMotor.isBusy());
            telemetry.addData("encoder-fwd-middle", middleMotor.getCurrentPosition() + "  busy=" + middleMotor.isBusy());
            telemetry.update();
            idle();
        }

        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Power sign matters again as we are running without encoder
        leftMotor.setPower(0.25);
        rightMotor.setPower(0.25);

        while (opModeIsActive() && leftMotor.getCurrentPosition() > leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-back-left", leftMotor.getCurrentPosition());
            telemetry.addData("encoder-back-right", rightMotor.getCurrentPosition());
            telemetry.addData("encoder-back-middle", middleMotor.getCurrentPosition());
            telemetry.update();
            idle();
        }

    middleMotor.setTargetPosition(0);
    middleMotor.setPower(0.25);
        
       while (opModeIsActive() && middleMotor.getCurrentPosition() > middleMotor.getTargetPosition())
        {
            telemetry.addData("encoder-back-left", leftMotor.getCurrentPosition());
            telemetry.addData("encoder-back-right", rightMotor.getCurrentPosition());
            telemetry.addData("encoder-back-middle", middleMotor.getCurrentPosition());
            telemetry.update();
            idle();
        }
        // Preexisting two commands to set power to zero are now commented out,
        //  because this prevented breaking action and allowed the robot to coast.
        // leftMotor.setPower(0.0);
        // rightMotor.setPower(0.0);

        resetStartTime();

        while (opModeIsActive() && getRuntime() < 5)
        {
            telemetry.addData("encoder-back-left-end", leftMotor.getCurrentPosition());
            telemetry.addData("encoder-back-right-end", rightMotor.getCurrentPosition());
            telemetry.addData("encoder-back-middle-end", middleMotor.getCurrentPosition());
            telemetry.update();
            idle();
        }
    }
}
}
