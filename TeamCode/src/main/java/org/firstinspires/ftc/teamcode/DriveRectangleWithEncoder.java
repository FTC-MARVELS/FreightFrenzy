// original code from: https://stemrobotics.cs.pdx.edu/node/4746
//  autonomous program that drives bot forward a set distance, stops then
//   backs up to the starting point using encoders to measure the distance.
// Modified by team MARVELS to drive in a small rectangle (or square),
//  first clockwise then counter-clockwise.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.WorkerClasses.AutonomousWorkerMethods;

@Autonomous(name="Drive Rectangle", group="Templates")
//@Disabled
public class DriveRectangleWithEncoder extends LinearOpMode
{
    // declare our motors
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor middleMotor;

    // this OpMode will use the FtcDashboard;
    // FtcDashboard dashboard;

    // create PID variables for drive motors
    PIDCoefficients fwdbckPID = new PIDCoefficients(0,0,0);
    PIDCoefficients lftrtPID = new PIDCoefficients(0,0,0);
    ElapsedTime drivePIDTimer = new ElapsedTime();
    double drivePIDIntegral = 0;

    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.dcMotor.get("LeftDrive");
        rightMotor = hardwareMap.dcMotor.get("RightDrive");
        middleMotor = hardwareMap.dcMotor.get("MiddleDrive");

        // declare worker class(es)
        AutonomousWorkerMethods workers = new AutonomousWorkerMethods();

        // You will need to set this based on your robot's
        // gearing to get forward control input to result in
        // forward motion.
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        // set motors to run to target encoder position and stop with brakes on
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // initialize an instance of FtcDashboard
        // dashboard = FtcDashboard.getInstance();
        
        telemetry.addData("Mode", "waiting");
        telemetry.update();

        // wait for start button to be pressed
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        // reset encoder counts kept by motors.
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        boolean pauseateachcorner = true;   // set to false if pausing at each corner is not desired
        double motorpower = 0.25;           // range 0.0 - 1.0

    // Clockwise
    // Rectangle side cw1

        // send robot forward to specified encoder counts
        leftMotor.setTargetPosition(1500);
        rightMotor.setTargetPosition(1500);
        middleMotor.setTargetPosition(0);

        // Set motors to appropriate power levels, movement will start. Sign of power is
        //  ignored since sign of target encoder position controls direction when
        //  running to position.
        leftMotor.setPower(motorpower);
        rightMotor.setPower(motorpower);
        middleMotor.setPower(0.0);          // will setting this above zero help to immobilize left/right motion?

        // wait while opmode is active and motor is busy running to position
        while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            workers.telemeterEncoderPositions();
        }

        // set motor power to zero to turn off motors. The motors stop on their own but
        //  power is still applied so we turn off the power.

        // Commands to set power to zero are now commented out,
        //  because this prevented breaking action and allowed the robot to coast.
        // leftMotor.setPower(0.0);
        // rightMotor.setPower(0.0);

        // unless disabled, wait 5 sec so you can observe the final encoder position
        resetStartTime();
        while (pauseateachcorner && opModeIsActive() && getRuntime() < 5)
        {
            workers.telemeterEncoderPositions();
        }

    // Rectangle side cw2

        // send robot right to specified encoder counts
        leftMotor.setTargetPosition(1500);
        rightMotor.setTargetPosition(1500);
        middleMotor.setTargetPosition(1500);

        // Set motors to appropriate power levels, movement will start. Sign of power is
        //  ignored since sign of target encoder position controls direction when
        //  running to position.
        leftMotor.setPower(0.0);            // will setting these above zero help to immobilize forward/back motion?
        rightMotor.setPower(0.0);
        middleMotor.setPower(motorpower);

        // wait while opmode is active and motor is busy running to position.
        while (opModeIsActive() && middleMotor.isBusy())   //middleMotor.getCurrentPosition() < middleMotor.getTargetPosition())
        {
            workers.telemeterEncoderPositions();
        }

        // unless disabled, wait 5 sec so you can observe the final encoder position
        resetStartTime();
        while (pauseateachcorner && opModeIsActive() && getRuntime() < 5)
        {
            workers.telemeterEncoderPositions();
        }

    // Rectangle side cw3

        // send robot back to specified encoder counts
        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);
        middleMotor.setTargetPosition(1500);

        // Set motors to appropriate power levels, movement will start. Sign of power is
        //  ignored since sign of target encoder position controls direction when
        //  running to position.
        leftMotor.setPower(0.0);            // will setting these above zero help to immobilize forward/back motion?
        rightMotor.setPower(0.0);
        middleMotor.setPower(motorpower);

        // wait while opmode is active and motor is busy running to position
        while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            workers.telemeterEncoderPositions();
        }

        // unless disabled, wait 5 sec so you can observe the final encoder position
        resetStartTime();
        while (pauseateachcorner && opModeIsActive() && getRuntime() < 5)
        {
            workers.telemeterEncoderPositions();
        }

    // Rectangle side cw4

        // send robot left to specified encoder counts
        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);
        middleMotor.setTargetPosition(0);

        // Set motors to appropriate power levels, movement will start. Sign of power is
        //  ignored since sign of target encoder position controls direction when
        //  running to position.
        leftMotor.setPower(0.0);            // will setting these above zero help to immobilize forward/back motion?
        rightMotor.setPower(0.0);
        middleMotor.setPower(motorpower);

        // wait while opmode is active and motor is busy running to position.
        while (opModeIsActive() && middleMotor.isBusy())   //middleMotor.getCurrentPosition() < middleMotor.getTargetPosition())
        {
            workers.telemeterEncoderPositions();
        }

        // unless disabled, wait 5 sec so you can observe the final encoder position
        resetStartTime();
        while (pauseateachcorner && opModeIsActive() && getRuntime() < 5)
        {
            workers.telemeterEncoderPositions();
        }
    }
}
