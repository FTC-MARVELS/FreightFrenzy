// original code from: https://stemrobotics.cs.pdx.edu/node/4746
//  autonomous program that drives bot forward a set distance, stops then
//   backs up to the starting point using encoders to measure the distance.
// Modified by team MARVELS to drive in a small rectangle (or square),
//  first clockwise then counter-clockwise.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.WorkerClasses.AutonomousWorkerMethods;

@Autonomous(name="Drive Rectangle", group="Templates")
//@Disabled
public class DriveRectangleWithEncoder extends LinearOpMode
{
    // declare our motors
    DcMotorEx leftMotor;
    DcMotorEx rightMotor;
    DcMotorEx middleMotor;

    // this OpMode will use the FtcDashboard;
    // FtcDashboard dashboard;

    // create PID variables for drive motors
    // PIDCoefficients fwdbckPID = new PIDCoefficients(0,0,0);     // TODO will one set of coefficients work for both fwd & bck motors, or will they each need individual coefficients?
    // PIDCoefficients lftrtPID = new PIDCoefficients(0,0,0);
    // ElapsedTime drivePIDTimer = new ElapsedTime();
    // double drivePIDIntegral = 0;

    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotorEx.class,"LeftDrive");
        rightMotor = hardwareMap.get(DcMotorEx.class,"RightDrive");
        middleMotor = hardwareMap.get(DcMotorEx.class,"MiddleDrive");

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

        // predefine some variables
        boolean pauseateachcorner = true;   // set to false if pausing at each corner is not desired
        // motor power is used for running without encoders, motor VELOCITY is used for running WITH encooders
        //  double motorpower = 0.25;       // range 0.0 - 1.0
        double motorVelocity = 125;         // units is ticks/second

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

    // Clockwise
    // Rectangle side cw1

        // send robot forward to specified encoder counts
        leftMotor.setTargetPosition(1500);
        rightMotor.setTargetPosition(1500);
        middleMotor.setTargetPosition(0);

        // Set motors to appropriate power levels, movement will start. Sign of power is
        //  ignored since sign of target encoder position controls direction when
        //  running to position.
        leftMotor.setVelocity(motorVelocity);
        rightMotor.setVelocity(motorVelocity);
        middleMotor.setVelocity(0.0);          // TODO will setting this above zero help to immobilize left/right motion?

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
        leftMotor.setVelocity(0.0);            // TODO will setting these above zero help to immobilize forward/back motion?
        rightMotor.setVelocity(0.0);
        middleMotor.setVelocity(motorVelocity);

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
        leftMotor.setVelocity(motorVelocity);
        rightMotor.setVelocity(motorVelocity);
        middleMotor.setVelocity(0.0);

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
        leftMotor.setVelocity(0.0);
        rightMotor.setVelocity(0.0);
        middleMotor.setVelocity(motorVelocity);

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
