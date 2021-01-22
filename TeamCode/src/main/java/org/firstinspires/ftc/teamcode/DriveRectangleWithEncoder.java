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

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Config
@Autonomous(name="Drive Rectangle", group="Templates")
//@Disabled
public class DriveRectangleWithEncoder extends LinearOpMode
{
    // declare our motors
    DcMotorEx leftMotor;
    DcMotorEx rightMotor;
    DcMotorEx shooter;
    DcMotorEx intake;
    DcMotorEx roller;

    // declare our servos
    Servo gate;
    Servo feeder;

    // define an instance of FtcDashboard;
    FtcDashboard dashboard;

    // predefine some variables for dashboard configuration
    public static boolean pauseAtEachCorner = true;   // set to false if pausing at each corner is not desired
    public static boolean useCustomPIDF = false;      // set to true to use custom PIDF control
    // motor POWER is used for running WITHOUT encoders, motor VELOCITY is used for running WITH encooders
    double motorpower = 0.25;       // range 0.0 - 1.0
//d    public static double motorVelocity = 125.0;         // units is ticks/second
    public static PIDFCoefficients dashPID_Vleft = new PIDFCoefficients(0,0,0,0);
    public static PIDFCoefficients dashPID_Vright = new PIDFCoefficients(0,0,0,0);
    public static PIDFCoefficients dashPID_Pleft = new PIDFCoefficients(0,0,0,0);
    public static PIDFCoefficients dashPID_Pright = new PIDFCoefficients(0,0,0,0);

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
        rightMotor = hardwareMap.get(DcMotorEx.class,"RightDrive");
//dhw        shooter = hardwareMap.get(DcMotorEx.class,"Shooter");
//dhw        intake = hardwareMap.get(DcMotorEx.class,"Intake");
//dhw        roller = hardwareMap.get(DcMotorEx.class,"Roller");
//dhw        gate = hardwareMap.get(Servo.class,"Gate");
//dhw        feeder = hardwareMap.get(Servo.class,"Feeder");

        // initialize FtcDashboard
        dashboard = FtcDashboard.getInstance();

        // declare dashboard telelmetry
        TelemetryPacket pidfpacket = new TelemetryPacket();
        TelemetryPacket modepacket = new TelemetryPacket();

        // discover current (default) PIDF coefficients
        PIDFCoefficients readPidfVleft = leftMotor.getPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION);
        telemetry.addData("default PIDF (Vleft)", readPidfVleft);
        pidfpacket.put("default PIDF (Vleft)", readPidfVleft);
        dashboard.sendTelemetryPacket(pidfpacket);

        // unless disabled, set PIDF coefficients for drive motors
        if (useCustomPIDF) {
            // these values were calculated using a maximum velocity value of XXXX, as measured on mm/dd/yyyy
            leftMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION, dashPID_Vleft);
            rightMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION, dashPID_Vright);
        }

        // You will need to set this based on your robot's
        // gearing to get forward control input to result in
        // forward motion.
        leftMotor.setDirection(DcMotorEx.Direction.REVERSE);

        // declare worker class(es)
        org.firstinspires.ftc.teamcode.AutonomousWorkerMethods workers = new org.firstinspires.ftc.teamcode.AutonomousWorkerMethods();

        // Retrieve the camera we are to use
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        // We also indicate which camera on the RC we wish to use
        parameters.cameraName = webcamName;

        // Make sure extended tracking is disabled for this example.
        parameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        //  sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsUltimateGoal = this.vuforia.loadTrackablesFromAsset("UltimateGoal");
        VuforiaTrackable blueTowerGoalTarget = targetsUltimateGoal.get(0);
        blueTowerGoalTarget.setName("Blue Tower Goal Target");
        VuforiaTrackable redTowerGoalTarget = targetsUltimateGoal.get(1);
        redTowerGoalTarget.setName("Red Tower Goal Target");
        VuforiaTrackable redAllianceTarget = targetsUltimateGoal.get(2);
        redAllianceTarget.setName("Red Alliance Target");
        VuforiaTrackable blueAllianceTarget = targetsUltimateGoal.get(3);
        blueAllianceTarget.setName("Blue Alliance Target");
        VuforiaTrackable frontWallTarget = targetsUltimateGoal.get(4);
        frontWallTarget.setName("Front Wall Target");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsUltimateGoal);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        //Set the position of the perimeter targets with relation to origin (center of field)
        redAllianceTarget.setLocation(OpenGLMatrix
                .translation(0, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        blueAllianceTarget.setLocation(OpenGLMatrix
                .translation(0, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
        frontWallTarget.setLocation(OpenGLMatrix
                .translation(-halfField, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        // The tower goal targets are located a quarter field length from the ends of the back perimeter wall
        blueTowerGoalTarget.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));
        redTowerGoalTarget.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        //  Let all the trackable listeners know where the camera is
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

        // send telemetry to Driver Station using standard SDK interface
        telemetry.addData("Mode", "waiting");
        telemetry.update();
        // send same telemetry to dashboard using packet interface
        modepacket.put("Mode", "waiting");
        dashboard.sendTelemetryPacket(modepacket);

        // wait for start button to be pressed
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();
        // send same telemetry to dashboard using packet interface
        modepacket.put("Mode", "running");
        dashboard.sendTelemetryPacket(modepacket);

        targetsUltimateGoal.activate();
        while (!isStopRequested()) {

            // reset encoder counts kept by motors.
            leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Move in a clockwise rectangle

            // Rectangle side cw1

            // send robot forward to specified encoder counts
            leftMotor.setTargetPosition(1500);
        rightMotor.setTargetPosition(1500);


            // Set motors to appropriate power levels
            leftMotor.setPower(motorpower);
        rightMotor.setPower(motorpower);

            // set motors to run to target encoder position and stop with brakes on
            // movement will start here
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // wait while opmode is active and motor is busy running to position
            while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
            {
                trackAndTelemeter(allTrackables,"forward motion");
            }

            // unless disabled, wait 5 sec so you can observe the final encoder position
            resetStartTime();
            while (opModeIsActive() && getRuntime() < 5)
            {
                trackAndTelemeter(allTrackables,"forward complete");
            }

            // Rectangle side cw2

            // send robot right to specified encoder counts
            leftMotor.setTargetPosition(1500);
        rightMotor.setTargetPosition(1500);

            // Set motors to appropriate power levels
            leftMotor.setPower(motorpower);
            rightMotor.setPower(motorpower);

            // set motors to run to target encoder position and stop with brakes on
            // movement will start here
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // wait while opmode is active and motor is busy running to position
            while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
            {
                trackAndTelemeter(allTrackables,"right motion");
            }

            // unless disabled, wait 5 sec so you can observe the final encoder position
            resetStartTime();
            while (opModeIsActive() && getRuntime() < 5)
            {
                trackAndTelemeter(allTrackables,"right complete");
            }

            // Rectangle side cw3

            // send robot back to specified encoder counts
            leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);

            // Set motors to appropriate power levels
            leftMotor.setPower(motorpower);
            rightMotor.setPower(motorpower);

            // set motors to run to target encoder position and stop with brakes on
            // movement will start here
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // wait while opmode is active and motor is busy running to position
            while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
            {
                trackAndTelemeter(allTrackables,"back motion");
            }

            // unless disabled, wait 5 sec so you can observe the final encoder position
            resetStartTime();
            while (opModeIsActive() && getRuntime() < 5)
            {
                trackAndTelemeter(allTrackables,"back complete");
            }

            // Rectangle side cw4

            // send robot left to specified encoder counts
            leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);

            // Set motors to appropriate power levels
            leftMotor.setPower(motorpower);
            rightMotor.setPower(motorpower);

            // set motors to run to target encoder position and stop with brakes on
            // movement will start here
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // wait while opmode is active and motor is busy running to position
            while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
            {
                trackAndTelemeter(allTrackables,"left motion");
            }

            // unless disabled, wait 5 sec so you can observe the final encoder position
            resetStartTime();
            while (opModeIsActive() && getRuntime() < 5)
            {
                trackAndTelemeter(allTrackables,"left complete");
            }

        }

        // Disable Tracking when OpMode is complete;
        targetsUltimateGoal.deactivate();
    }

// internal methods below here

    public void trackAndTelemeter(List<VuforiaTrackable> trackables, String direction) {
        TelemetryPacket eppacket = new TelemetryPacket();
        TelemetryPacket vuforiapacket = new TelemetryPacket();

        // Display to SDK telemetry all drive encoder positions and busy statuses
        telemetry.addData("direction", direction);
        telemetry.addData("encoder-left", leftMotor.getCurrentPosition() + ", busy=" + leftMotor.isBusy());
        telemetry.addData("encoder-right", rightMotor.getCurrentPosition() + ", busy=" + rightMotor.isBusy());
        telemetry.update();
        // Also display same to dashboard telemetry
        eppacket.put("direction", direction);
        eppacket.put("encoder-left", leftMotor.getCurrentPosition() + ", busy=" + leftMotor.isBusy());
        eppacket.put("encoder-right", rightMotor.getCurrentPosition() + ", busy=" + rightMotor.isBusy());
        dashboard.sendTelemetryPacket(eppacket);

        // check all the trackable targets to see which one (if any) is visible
        targetVisible = false;
        for (VuforiaTrackable trackable : trackables) {
            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                telemetry.addData("Visible Target", trackable.getName());
                vuforiapacket.put("Visible Target", trackable.getName());
                targetVisible = true;

                // getUpdatedRobotLocation() will return null if no new information is available since
                //  the last time that call was made, or if the trackable is not currently visible.
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                break;
            }
        }

        // provide feedback as to where the robot is located (if we know)
        if (targetVisible) {
            // express position (translation) of robot in inches
            VectorF translation = lastLocation.getTranslation();
            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);
            vuforiapacket.put("Pos (in)", String.format("{X, Y, Z} = %.1f, %.1f, %.1f", translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch));

            // express the rotation of the robot in degrees
            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            vuforiapacket.put("Rot (deg)", String.format("{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle));
        }
        else {
            telemetry.addData("Visible Target", "none");
            vuforiapacket.put("Visible Target", "none");
        }
        telemetry.update();
        dashboard.sendTelemetryPacket(vuforiapacket);
        idle();
    }
}
