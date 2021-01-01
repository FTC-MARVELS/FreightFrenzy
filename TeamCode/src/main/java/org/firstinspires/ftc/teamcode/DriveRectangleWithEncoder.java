// original code from: https://stemrobotics.cs.pdx.edu/node/4746
//  autonomous program that drives bot forward a set distance, stops then
//   backs up to the starting point using encoders to measure the distance.
// Modified by team MARVELS to drive in a small rectangle (or square),
//  first clockwise then counter-clockwise.

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import org.firstinspires.ftc.teamcode.WorkerClasses.AutonomousWorkerMethods;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

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

    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AUbzCwP/////AAABmbf8lVGV50gKh7hv59eI3JQCXgBCGxkIDkp1CU2L7prKbjkbN08TcMn1OwKUNp2/2lSPSizaWIxYJlJ8iBcqvsibP5c+irEhGlqHtjYTR3TVLMVyOr4y1arszOyaPDztZUBUr5IMbfOXm4iC8MG2sDFvWQGv8vIIuthnJ19oKlXYRaVmt5dwBo2StzpMe0g9xEIh3SaB+xfb+b2W3yom6Z5jNd9NfVTToeP6vR1ubW/h9+OGu82ybfV0S0pNuRj81qwhYhhAvwaFIFoDR1vG3KVY0QGPKOjP+lyuXToCc1MFKA8SAhk+9BssevFLw5bwO9rspSdw/VtEFKMF70eiS7nYgH2dJyAcp9wQHdytf6E5";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;

    /**
     * This is the webcam we are to use. As with other hardware devices such as motors and
     * servos, this device is identified using the robot configuration tool in the FTC application.
     */
    WebcamName webcamName = null;

    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;

    // called when init button is pressed
    @Override
    public void runOpMode() throws InterruptedException
    {
        // get references to hardware components
        leftMotor = hardwareMap.get(DcMotorEx.class,"LeftDrive");
//d        rightMotor = hardwareMap.get(DcMotorEx.class,"RightDrive");
//d        middleMotor = hardwareMap.get(DcMotorEx.class,"MiddleDrive");
//d        shooter = hardwareMap.get(DcMotorEx.class,"Shooter");
//d        intake = hardwareMap.get(DcMotorEx.class,"Intake");
//d        roller = hardwareMap.get(DcMotorEx.class,"Roller");
//d        gate = hardwareMap.get(Servo.class,"Gate");
//d        feeder = hardwareMap.get(Servo.class,"Feeder");

        // unless disabled, set PIDF coefficients for drive motors
        if (useCustomPIDF) {
            // these values were calculated using a maximum velocity value of XXXX, as measured on mm/dd/yyyy
            leftMotor.setVelocityPIDFCoefficients(dashPID_Vleft.p, dashPID_Vleft.i, dashPID_Vleft.d, dashPID_Vleft.f);
//d            rightMotor.setVelocityPIDFCoefficients(dashPID_Vright.p, dashPID_Vright.i, dashPID_Vright.d, dashPID_Vright.f);
//d            middleMotor.setVelocityPIDFCoefficients(dashPID_Vmiddle.p, dashPID_Vmiddle.i, dashPID_Vmiddle.d, dashPID_Vmiddle.f);
//d            leftMotor.setPositionPIDFCoefficients(dashPID_Pleft.p);
//d            rightMotor.setPositionPIDFCoefficients(dashPID_Pright.p);
//d            middleMotor.setPositionPIDFCoefficients(dashPID_Pmiddle.p);
        }

        // You will need to set this based on your robot's
        // gearing to get forward control input to result in
        // forward motion.
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        // set all encoder counts to zero (target position must be set before RUN_TO_POSITION mode can be set)
        leftMotor.setTargetPosition(0);
//d        rightMotor.setTargetPosition(0);
//d        middleMotor.setTargetPosition(0);
        // set motors to run to target encoder position and stop with brakes on
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//d        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//d        middleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // declare worker class(es)
        AutonomousWorkerMethods workers = new AutonomousWorkerMethods();

        // initialize FtcDashboard
        dashboard = FtcDashboard.getInstance();

        // declare dashboard telelmetry
        TelemetryPacket packet = new TelemetryPacket();

        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

        // send telemetry to Driver Station using standard SDK interface
        telemetry.addData("Mode", "waiting");
        telemetry.update();
        // send same telemetry to dashboard using packet interface
        packet.put("Mode", "waiting");
        dashboard.sendTelemetryPacket(packet);

        // wait for start button to be pressed
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();
        // send same telemetry to dashboard using packet interface
        packet.put("Mode", "running");
        dashboard.sendTelemetryPacket(packet);

        // reset encoder counts kept by motors.
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//d        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//d        middleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    // Clockwise
    // Rectangle side cw1

        // send robot forward to specified encoder counts
        leftMotor.setTargetPosition(1500);
//d        rightMotor.setTargetPosition(1500);
//d        middleMotor.setTargetPosition(0);

        // Set motors to appropriate power levels, movement will start. Sign of power is
        //  ignored since sign of target encoder position controls direction when
        //  running to position.
        leftMotor.setVelocity(motorVelocity);
//d        rightMotor.setVelocity(motorVelocity);
//d        middleMotor.setVelocity(0.0);          // TODO will setting this above zero help to immobilize left/right motion?

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
/*d
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
        }*/
    }
}
