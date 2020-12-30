// original code from: https://stemrobotics.cs.pdx.edu/node/4746
//  autonomous program that drives bot forward a set distance, stops then
//   backs up to the starting point using encoders to measure the distance.
// Modified by team MARVELS to drive in a small rectangle (or square),
//  first clockwise then counter-clockwise.

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.WorkerClasses.AutonomousWorkerMethods;

@Config
@Autonomous(name="Drive Rectangle", group="Templates")
//@Disabled
public class DriveRectangleWithEncoder extends LinearOpMode
{
    // declare our motors
    DcMotorEx leftMotor;
    DcMotorEx rightMotor;
    DcMotorEx middleMotor;
    DcMotorEx shooter;
    DcMotorEx intake;
    DcMotorEx roller;

    // declare our servos
    Servo gate;
    Servo feeder;

    // define an instance of FtcDashboard;
    FtcDashboard dashboard;

    // predefine some variables for dashboard configuration
    public static boolean pauseateachcorner = true;   // set to false if pausing at each corner is not desired
    public static boolean useCustomPIDF = false;      // set to true to use custom PIDF control
    // motor POWER is used for running WITHOUT encoders, motor VELOCITY is used for running WITH encooders
    //  double motorpower = 0.25;       // range 0.0 - 1.0
    public static double motorVelocity = 125;         // units is ticks/second
    public static PIDFCoefficients dashPID_Vleft = new PIDFCoefficients(0,0,0,0);
    public static PIDFCoefficients dashPID_Vright = new PIDFCoefficients(0,0,0,0);
    public static PIDFCoefficients dashPID_Vmiddle = new PIDFCoefficients(0,0,0,0);
    public static PIDFCoefficients dashPID_Pleft = new PIDFCoefficients(0,0,0,0);
    public static PIDFCoefficients dashPID_Pright = new PIDFCoefficients(0,0,0,0);
    public static PIDFCoefficients dashPID_Pmiddle = new PIDFCoefficients(0,0,0,0);

    // called when init button is pressed
    @Override
    public void runOpMode() throws InterruptedException
    {
        // get references to hardware components
        leftMotor = hardwareMap.get(DcMotorEx.class,"LeftDrive");
        rightMotor = hardwareMap.get(DcMotorEx.class,"RightDrive");
        middleMotor = hardwareMap.get(DcMotorEx.class,"MiddleDrive");
        shooter = hardwareMap.get(DcMotorEx.class,"Shooter");
        intake = hardwareMap.get(DcMotorEx.class,"Intake");
        roller = hardwareMap.get(DcMotorEx.class,"Roller");
        gate = hardwareMap.get(Servo.class,"Gate");
        feeder = hardwareMap.get(Servo.class,"Feeder");

        // unless disabled, set PIDF coefficients for drive motors
        if (useCustomPIDF) {
            // these values were calculated using a maximum velocity value of XXXX, as measured on mm/dd/yyyy
            leftMotor.setVelocityPIDFCoefficients(dashPID_Vleft.p, dashPID_Vleft.i, dashPID_Vleft.d, dashPID_Vleft.f);
            rightMotor.setVelocityPIDFCoefficients(dashPID_Vright.p, dashPID_Vright.i, dashPID_Vright.d, dashPID_Vright.f);
            middleMotor.setVelocityPIDFCoefficients(dashPID_Vmiddle.p, dashPID_Vmiddle.i, dashPID_Vmiddle.d, dashPID_Vmiddle.f);
            leftMotor.setPositionPIDFCoefficients(dashPID_Pleft.p);
            rightMotor.setPositionPIDFCoefficients(dashPID_Pright.p);
            middleMotor.setPositionPIDFCoefficients(dashPID_Pmiddle.p);
        }

        // You will need to set this based on your robot's
        // gearing to get forward control input to result in
        // forward motion.
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        // set motors to run to target encoder position and stop with brakes on
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // declare worker class(es)
        AutonomousWorkerMethods workers = new AutonomousWorkerMethods();

        // initialize FtcDashboard
        dashboard = FtcDashboard.getInstance();

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
