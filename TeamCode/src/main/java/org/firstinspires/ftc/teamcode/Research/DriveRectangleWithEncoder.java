// original code from: https://stemrobotics.cs.pdx.edu/node/4746
//  Autonomous program that drives bot forward a set distance, stops then
//   backs up to the starting point using encoders to measure the distance.
// Modified by team MARVELS to drive continuously in small rectangles, clockwise.
//  This is meant to act as a template for building autonomous linear OpModes -- just modify the
//   code between the comments "// Move in a clockwise rectangle" and
//   "// repeat the loop to do it all over again" with code to accomplish your desired routine.
//  Built-in abilities include:
//   * FTC Dashboard to aid with OpMode design, tuning, and troubleshooting
//   * PID motor control tuning
//   * Use of motor encoders to drive a set velocity and/or to a given position
//   * Use of IMU gyro to execute turns of any angle between 0-180 degrees
//   * Use of Vuforia to determine robot's position on field using navigation targets
//   * Use of Tensor Flow to discover objects (size of ring starter stack for Ultimate Goal season)

/**
 * To utilize the above listed "built-in abilities" do the following:
 *
 * ** FTC Dashboard **
 * 1. On any computer or tablet, connect WiFi to SSID: 14571-x-RC
 * 2. Open a web browser to address 192.168.49.1 (if RC phone on WiFi-Direct)
 *    or to 192.168.43.1 (if Control Hub)
 * *******************
 *
 * ** PID motor control tuning **
 * 1. Use FTC Dashboard to play with different Configuration PID variables until robot is well tuned
 * 2. To make these coefficients persistent, copy the values to the code lines starting with
 *     "public static PIDFCoefficients dashPID_Vleft = new PIDFCoefficients(0,0,0,0)"
 *     do the same for coefficients Vright, Pleft, and Pright
 * ******************************
 *
 * ** Motor encoders **
 * 1. Use setVelocity commands instead of setPower commands
 * 2. Use commands setMode(DcMotor.RunMode.RUN_TO_POSITION) or setMode(DcMotor.RunMode.RUN_USING_ENCODER)
 *     instead of setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER) or simply setPower alone
 * ********************
 *
 * ** IMU gyro **
 * 1. Call the rotate() method anytime you need to execute a turn
 * 2. Valid range for degrees is 0-180, use negative degrees for clockwise,
 *     or positive degrees for counter-clockwise rotations.
 * 3. Example: rotate(-90, 0.8); to execute a 90 degree clockwise turn (to the right),
 *     with a starting power level of 0.8.
 * **************
 *
 * ** Vuforia positioning **
 * 1. This OpMode is already polling Vuforia for the robot's current position and rotation,
 *     each iteration of the main loop.
 * 2. The position's origin is the center of the field.
 * 3. Call String.format("{X, Y, Z} = %.1f, %.1f, %.1f", translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch)
 *     to poll for the robot's current x,y,z position coordinates in inches.
 * 4. Call String.format("{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle)
 *     to poll for the robot's current roll,pitch,heading rotation in degrees.
 * *************************
 *
 * ** Tensor Flow **
 * 1. Poll the method updatedRecognitions.size() to determine if the camera sees a stack of
 *     rings or it does not see a stack of rings.
 * 2. If the camera does see a stack of rings, poll the method recognition.getLabel() to determine
 *     if the stack is a 'Single' or a 'Quad' stack (one ring or four rings).
 * 3. It may require multiple polls to gather enough data for accurate results.
 * *****************
 */

package org.firstinspires.ftc.teamcode.Research;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.ZYX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Config
@Autonomous(name="Drive Rectangle", group="Templates")
@Disabled
public class DriveRectangleWithEncoder extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

    }
//    // declare our motors
//    DcMotorEx leftMotor, rightMotor, leftMotor2, rightMotor2;
//    DcMotorEx shooter;
//    DcMotorEx intake;
//    DcMotorEx roller;
//    DcMotorEx arm;
//
//    // declare our servos
//    Servo gate;
//    Servo feeder;
//    Servo grabber;
//
//    // declare IMU and its angle object & variables
//    BNO055IMU imu;
//    Orientation             lastAngles = new Orientation();
//    double                  globalAngle, correction;
//
//    // define an instance of FtcDashboard;
//    FtcDashboard dashboard;
//
//    // predefine variables for dashboard configuration
//    public static boolean isSecondaryRobot = false;     // set to true when using secondary, which has less hardware
//    public static boolean pauseAtEachCorner = true;   // set to false if pausing at each corner is not desired
//    public static boolean useCustomPIDF = false;      // set to true to use custom PIDF control
//    // motor POWER is used for running WITHOUT encoders, motor VELOCITY is used for running WITH encoders
////d    public static double driveVelocity = 800;        // units is ticks/second
////d    public static double turnVelocity = 1400;        // units is ticks/second
////d    public static double minTurnVelocity = 600;      // units is ticks/second
//    public static double drivepower = 0.4;      // range 0.0 - 1.0
//    public static double turnpower = 0.7;       // range 0.0 - 1.0
//    public static double minturnpower = 0.3;
//    public static double turngain = 0.01;
//    public static PIDFCoefficients dashPID_Vleft = new PIDFCoefficients(0,0,0,0);
//    public static PIDFCoefficients dashPID_Vright = new PIDFCoefficients(0,0,0,0);
//    public static PIDFCoefficients dashPID_Pleft = new PIDFCoefficients(0,0,0,0);
//    public static PIDFCoefficients dashPID_Pright = new PIDFCoefficients(0,0,0,0);
//
//    // configureVuforia
//
//    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
//    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
//    private static final boolean PHONE_IS_PORTRAIT = false  ;
//
//    /*
//     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
//     * 'vparameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
//     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
//     * web site at https://developer.vuforia.com/license-manager.
//     *
//     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
//     * random data. As an example, here is a example of a fragment of a valid key:
//     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
//     * Once you've obtained a license key, copy the string from the Vuforia web site
//     * and paste it in to your code on the next line, between the double quotes.
//     */
//    private static final String VUFORIA_KEY =
//            "AUbzCwP/////AAABmbf8lVGV50gKh7hv59eI3JQCXgBCGxkIDkp1CU2L7prKbjkbN08TcMn1OwKUNp2/2lSPSizaWIxYJlJ8iBcqvsibP5c+irEhGlqHtjYTR3TVLMVyOr4y1arszOyaPDztZUBUr5IMbfOXm4iC8MG2sDFvWQGv8vIIuthnJ19oKlXYRaVmt5dwBo2StzpMe0g9xEIh3SaB+xfb+b2W3yom6Z5jNd9NfVTToeP6vR1ubW/h9+OGu82ybfV0S0pNuRj81qwhYhhAvwaFIFoDR1vG3KVY0QGPKOjP+lyuXToCc1MFKA8SAhk+9BssevFLw5bwO9rspSdw/VtEFKMF70eiS7nYgH2dJyAcp9wQHdytf6E5";
//
//    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
//    // We will define some constants and conversions here
//    private static final float mmPerInch        = 25.4f;
//    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor
//
//    // Constants for perimeter targets
//    private static final float halfField = 72 * mmPerInch;
//    private static final float quadField  = 36 * mmPerInch;
//
//    // Class Members
//    private OpenGLMatrix lastLocation = null;
//    private VuforiaLocalizer vuforia = null;
//
//    /**
//     * This is the webcam we are to use. As with other hardware devices such as motors and
//     * servos, this device is identified using the robot configuration tool in the FTC application.
//     */
//    WebcamName webcamName = null;
//
//    private boolean targetVisible = false;
//    private float phoneXRotate    = 0;
//    private float phoneYRotate    = 0;
//    private float phoneZRotate    = 0;
//
//    /*
//    TENSOR FLOW
//     */
//    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
//    private static final String LABEL_FIRST_ELEMENT = "Quad";
//    private static final String LABEL_SECOND_ELEMENT = "Single";
//    int wobbleZone = -1;
//    int tfodSize = -1;
//    String tfodLabel = "";
//
//    /**
//     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
//     * Detection engine.
//     */
//    private TFObjectDetector tfod;
//
//    // called when init button is pressed
//    @Override
//    public void runOpMode() throws InterruptedException
//    {
//        // get references to hardware components
//        leftMotor = hardwareMap.get(DcMotorEx.class,"LeftDrive");
//        rightMotor = hardwareMap.get(DcMotorEx.class,"RightDrive");
//        shooter = hardwareMap.get(DcMotorEx.class,"Shooter");
//        intake = hardwareMap.get(DcMotorEx.class,"Intake");
//        roller = hardwareMap.get(DcMotorEx.class,"Roller");
//        arm = hardwareMap.get(DcMotorEx.class, "Arm");
//        gate = hardwareMap.get(Servo.class,"Gate");
//        feeder = hardwareMap.get(Servo.class,"Feeder");
//        grabber = hardwareMap.get(Servo.class, "Grabber");
//        if (!isSecondaryRobot) {
//            leftMotor2 = hardwareMap.get(DcMotorEx.class,"LeftDrive2");
//            rightMotor2 = hardwareMap.get(DcMotorEx.class,"RightDrive2");
//        }
//
//        // You will need to set this based on your robot's
//        // gearing to get forward control input to result in
//        // forward motion.
//        rightMotor.setDirection(DcMotorEx.Direction.REVERSE);
//        if (!isSecondaryRobot) {
//            rightMotor2.setDirection(DcMotorEx.Direction.REVERSE);
//        }
//
//        // initialize FtcDashboard
//        dashboard = FtcDashboard.getInstance();
//
//        // declare dashboard telelmetry
//        TelemetryPacket modepacket = new TelemetryPacket();
//        TelemetryPacket pidfpacket = new TelemetryPacket();
//        TelemetryPacket motionpacket = new TelemetryPacket();
//        TelemetryPacket imupacket = new TelemetryPacket();
//        TelemetryPacket tfodpacket = new TelemetryPacket();
//
//        // discover and show current (default) PIDF coefficients
//        PIDFCoefficients readPidfVleft = leftMotor.getPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION);
//        telemetry.addData("default PIDF (Vleft)", readPidfVleft);
//        pidfpacket.put("default PIDF (Vleft)", readPidfVleft);
//        dashboard.sendTelemetryPacket(pidfpacket);
//
//        // unless disabled, set PIDF coefficients for drive motors
//        if (useCustomPIDF) {
//            // these values were calculated using a maximum velocity value of XXXX, as measured on mm/dd/yyyy
//            leftMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION, dashPID_Vleft);
//            rightMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION, dashPID_Vright);
//        }
//
//        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        if (!isSecondaryRobot) {
//            leftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//            rightMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        }
//
//        BNO055IMU.Parameters imuparameters = new BNO055IMU.Parameters();
//
//        imuparameters.mode                = BNO055IMU.SensorMode.IMU;
//        imuparameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
//        imuparameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        imuparameters.loggingEnabled      = false;
//
//        // Retrieve and initialize the IMU. The IMU is internally attached to I2C port 0 on REV
//        //  hubs (both Control and Expansion), and named "imu".
//        imu = hardwareMap.get(BNO055IMU.class, "imu");
//
//        imu.initialize(imuparameters);
//
//        telemetry.addData("IMU Status", "calibrating...");
//        telemetry.update();
//        imupacket.put("IMU Status", "calibrating...");
//        dashboard.sendTelemetryPacket(imupacket);
//
//        // make sure the imu gyro is calibrated before continuing.
//        while (!isStopRequested() && !imu.isGyroCalibrated())
//        {
//            sleep(50);
//            idle();
//        }
//
//        // declare worker class(es)
//        org.firstinspires.ftc.teamcode.Research.AutonomousWorkerMethods workers = new org.firstinspires.ftc.teamcode.Research.AutonomousWorkerMethods();
//
//        // Initialize Vuforia engine
//        initVuforia();
//
//        // Initialize Vuforia Trackables
//        // Load the data sets for the trackable objects. These particular data
//        //  sets are stored in the 'assets' part of our application.
//        VuforiaTrackables targetsUltimateGoal = this.vuforia.loadTrackablesFromAsset("UltimateGoal");
//        VuforiaTrackable blueTowerGoalTarget = targetsUltimateGoal.get(0);
//        blueTowerGoalTarget.setName("Blue Tower Goal Target");
//        VuforiaTrackable redTowerGoalTarget = targetsUltimateGoal.get(1);
//        redTowerGoalTarget.setName("Red Tower Goal Target");
//        VuforiaTrackable redAllianceTarget = targetsUltimateGoal.get(2);
//        redAllianceTarget.setName("Red Alliance Target");
//        VuforiaTrackable blueAllianceTarget = targetsUltimateGoal.get(3);
//        blueAllianceTarget.setName("Blue Alliance Target");
//        VuforiaTrackable frontWallTarget = targetsUltimateGoal.get(4);
//        frontWallTarget.setName("Front Wall Target");
//
//        // For convenience, gather together all the trackable objects in one easily-iterable collection */
//        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
//        allTrackables.addAll(targetsUltimateGoal);
//
//        /**
//         * In order for localization to work, we need to tell the system where each target is on the field, and
//         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
//         * Transformation matrices are a central, important concept in the math here involved in localization.
//         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
//         * for detailed information. Commonly, you'll encounter transformation matrices as instances
//         * of the {@link OpenGLMatrix} class.
//         *
//         * If you are standing in the Red Alliance Station looking towards the center of the field,
//         *     - The X axis runs from your left to the right. (positive from the center to the right)
//         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
//         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
//         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
//         *
//         * Before being transformed, each target image is conceptually located at the origin of the field's
//         *  coordinate system (the center of the field), facing up.
//         */
//
//        //Set the position of the perimeter targets with relation to origin (center of field)
//        redAllianceTarget.setLocation(OpenGLMatrix
//                .translation(0, -halfField, mmTargetHeight)
//                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));
//
//        blueAllianceTarget.setLocation(OpenGLMatrix
//                .translation(0, halfField, mmTargetHeight)
//                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
//        frontWallTarget.setLocation(OpenGLMatrix
//                .translation(-halfField, 0, mmTargetHeight)
//                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));
//
//        // The tower goal targets are located a quarter field length from the ends of the back perimeter wall
//        blueTowerGoalTarget.setLocation(OpenGLMatrix
//                .translation(halfField, quadField, mmTargetHeight)
//                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));
//        redTowerGoalTarget.setLocation(OpenGLMatrix
//                .translation(halfField, -quadField, mmTargetHeight)
//                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
//
//        //
//        // Create a transformation matrix describing where the phone is on the robot.
//        //
//        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
//        // Lock it into Portrait for these numbers to work.
//        //
//        // Info:  The coordinate frame for the robot looks the same as the field.
//        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
//        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
//        //
//        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
//        // pointing to the LEFT side of the Robot.
//        // The two examples below assume that the camera is facing forward out the front of the robot.
//
//        // We need to rotate the camera around it's long axis to bring the correct camera forward.
//        if (CAMERA_CHOICE == BACK) {
//            phoneYRotate = -90;
//        } else {
//            phoneYRotate = 90;
//        }
//
//        // Rotate the phone vertical about the X axis if it's in portrait mode
//        if (PHONE_IS_PORTRAIT) {
//            phoneXRotate = 90 ;
//        }
//
//        // Next, translate the camera lens to where it is on the robot.
//        // The following 3 values were measured on Marvels Primary Robot, for Ultimate Goal season, on 20-Feb-2021:
//        final float CAMERA_FORWARD_DISPLACEMENT  = 6.75f * mmPerInch;   // amount camera lens is in front of robot-center
//        final float CAMERA_VERTICAL_DISPLACEMENT = 6.625f * mmPerInch;   // amount camera lens is above ground
//        final float CAMERA_LEFT_DISPLACEMENT     = 8.50f * mmPerInch;     // amount camera lens is left of the robot's center line
//
////d        OpenGLMatrix robotFromCamera = OpenGLMatrix
////d                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
////d                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));
//           // change the above code as provided by the example, to the new code below as described at
//           // https://ftcforum.firstinspires.org/forum/first-tech-challenge-community-forum-this-is-an-open-forum/teams-helping-teams-programming/76847-question-on-vuforia-navigation?p=80899#post80899
//
//        OpenGLMatrix robotFromCamera = OpenGLMatrix
//                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
//                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, ZYX, DEGREES, 90,90, 0));
//
//        //  Let all the trackable listeners know where the camera is
//        for (VuforiaTrackable trackable : allTrackables) {
////d            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, vparameters.cameraDirection);
//            ((VuforiaTrackableDefaultListener) trackable.getListener()).setCameraLocationOnRobot(webcamName, robotFromCamera);
//        }
//
//        // Initialize TensorFlow
//        initTfod();
//
//        /**
//         * Activate TensorFlow Object Detection before we wait for the start command.
//         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
//         **/
//        if (tfod != null) {
//            tfod.activate();
//
//            // The TensorFlow software will scale the input images from the camera to a lower resolution.
//            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
//            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
//            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
//            // should be set to the value of the images used to create the TensorFlow Object Detection model
//            // (typically 1.78 or 16/9).
//
//            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
//            //tfod.setZoom(2.5, 1.78);
//        }
//
//        FtcDashboard.getInstance().startCameraStream(vuforia, 0);
//
//        // send telemetry to Driver Station using standard SDK interface
//        telemetry.addData("Mode", "waiting for start");
//        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
//        telemetry.update();
//        // send same telemetry to dashboard using packet interface
//        imupacket.put("imu calib status", imu.getCalibrationStatus().toString());
//        dashboard.sendTelemetryPacket(imupacket);
//        modepacket.put("Mode", "waiting for start");
//        dashboard.sendTelemetryPacket(modepacket);
//
//        // make sure the imu gyro is calibrated before continuing.
//        while (!isStopRequested() && !imu.isGyroCalibrated())
//        {
//            sleep(50);
//            idle();
//        }
//        // wait for start button to be pressed
//        waitForStart();
//
//        telemetry.addData("Mode", "running");
//        telemetry.update();
//        // send same telemetry to dashboard using packet interface
//        modepacket.put("Mode", "running");
//        dashboard.sendTelemetryPacket(modepacket);
//
//        targetsUltimateGoal.activate();
//
//        // main loop
//        while (opModeIsActive()) {
//
//            if (tfod != null) {
//                for (int j=0; j<50; j++) {
//                    // getUpdatedRecognitions() will return null if no new information is available since
//                    // the last time that call was made.
//                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
//                    if (updatedRecognitions != null) {
//                        tfodSize = updatedRecognitions.size();
//                        telemetry.addData("# Objects Detected", tfodSize);
//                        tfodpacket.put("# Objects Detected", tfodSize);
//
//                        // step through the list of recognitions and display boundary info.
//                        int i = 0;
//                        for (Recognition recognition : updatedRecognitions) {
//                            String tfodLabel = recognition.getLabel();
//                            Float tfodLeft = recognition.getLeft();
//                            Float tfodTop = recognition.getTop();
//                            Float tfodRight = recognition.getRight();
//                            Float tfodBottom = recognition.getBottom();
//                            telemetry.addData(String.format("label (%d)", i), tfodLabel);
//                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
//                                    tfodLeft, tfodTop);
//                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
//                                    tfodRight, tfodBottom);
//                            tfodpacket.put(String.format("label (%d)", i), tfodLabel);
//                            tfodpacket.put("-  left, top", String.format("(%d), %.03f, %.03f", i, tfodLeft, tfodTop));
//                            tfodpacket.put("-  right, bottom", String.format("(%d), %.03f, %.03f", i, tfodRight, tfodBottom));
//                        }
//                        telemetry.update();
//                        dashboard.sendTelemetryPacket(tfodpacket);
//
//                        telemetry.addData("# Objects Detected is", tfodSize);
//                        tfodpacket.put("# Objects Detected is", tfodSize);
//                        if (tfodSize == 0) {wobbleZone = 0; telemetry.addData("ring stack is none", "");}
//                        else {
//                            if (tfodLabel.equals("Single")) {wobbleZone = 1;}
//                            if (tfodLabel.equals("Quad")) {wobbleZone = 2;}
//                            telemetry.addData("ring stack is ", tfodLabel);
//                        }
//                        telemetry.update();
//                        dashboard.sendTelemetryPacket(tfodpacket);
//                    }
//                    idle();
//                    sleep(50);
//                }
//            }
//
//            // show TFOD ring count results
//            telemetry.addData("# Objects Detected", tfodSize);
//            tfodpacket.put("# Objects Detected", tfodSize);
//            telemetry.addData("ring stack", tfodLabel);
//            tfodpacket.put("ring stack", tfodLabel);
//            telemetry.addData("wobbleZone", wobbleZone);
//            tfodpacket.put("wobbleZone", wobbleZone);
//            telemetry.update();
//            dashboard.sendTelemetryPacket(tfodpacket);
//            // pause to read TFOD telemetry
//            sleep(10000);
//
//            // Move in a clockwise rectangle
//
//            // Rectangle side cw1
//
//            // reset encoder counts kept by motors
//            leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            if (!isSecondaryRobot) {
//                leftMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//                rightMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            }
//
//            // send robot forward to specified encoder counts
//            leftMotor.setTargetPosition(1500);
//            rightMotor.setTargetPosition(1500);
//            if (!isSecondaryRobot) {
//                leftMotor2.setTargetPosition(1500);
//                rightMotor2.setTargetPosition(1500);
//            }
//
//            // Set motors to appropriate power levels
//            leftMotor.setPower(drivepower);
//            rightMotor.setPower(drivepower);
//            if (!isSecondaryRobot) {
//                leftMotor2.setPower(drivepower);
//                rightMotor2.setPower(drivepower);
//            }
//
//            // set motors to run to target encoder position and stop with brakes on
//            // movement will start here
//            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            if (!isSecondaryRobot) {
//                leftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                rightMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//
//            // wait while opmode is active and motor is busy running to position
//            while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
//            {
//                trackAndTelemeter(allTrackables,"forward motion");
//            }
//
//            // unless disabled, wait so you can observe the final encoder position
//            resetStartTime();
//            do {
//                trackAndTelemeter(allTrackables,"forward complete");
//            } while (pauseAtEachCorner && opModeIsActive() && getRuntime() < 2);
//
//            // We are in a corner of the rectangle, turn cw 90 degrees
//            telemetry.addData("motion", "rotating -90 degrees (cw)");
//            telemetry.update();
//            motionpacket.put("motion", "rotating -90 degrees (cw)");
//            dashboard.sendTelemetryPacket(motionpacket);
//            rotate(-90, turnpower);
//
//            // Rectangle side cw2
//
//            // reset encoder counts kept by motors
//            leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            if (!isSecondaryRobot) {
//                leftMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//                rightMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            }
//
//            // send robot right to specified encoder counts
//            leftMotor.setTargetPosition(1000);
//            rightMotor.setTargetPosition(1000);
//            if (!isSecondaryRobot) {
//                leftMotor2.setTargetPosition(1000);
//                rightMotor2.setTargetPosition(1000);
//            }
//
//            // Set motors to appropriate power levels
//            leftMotor.setPower(drivepower);
//            rightMotor.setPower(drivepower);
//            if (!isSecondaryRobot) {
//                leftMotor2.setPower(drivepower);
//                rightMotor2.setPower(drivepower);
//            }
//
//            // set motors to run to target encoder position and stop with brakes on
//            // movement will start here
//            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            if (!isSecondaryRobot) {
//                leftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                rightMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//
//            // wait while opmode is active and motor is busy running to position
//            while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
//            {
//                trackAndTelemeter(allTrackables,"right motion");
//            }
//
//            // unless disabled, wait so you can observe the final encoder position
//            resetStartTime();
//            do {
//                trackAndTelemeter(allTrackables,"right complete");
//            } while (pauseAtEachCorner && opModeIsActive() && getRuntime() < 2);
//
//            // We are in a corner of the rectangle, turn cw 90 degrees
//            telemetry.addData("motion", "rotating -90 degrees (cw)");
//            telemetry.update();
//            motionpacket.put("motion", "rotating -90 degrees (cw)");
//            dashboard.sendTelemetryPacket(motionpacket);
//            rotate(-90, turnpower);
//
//            // Rectangle side cw3
//
//            // reset encoder counts kept by motors
//            leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            if (!isSecondaryRobot) {
//                leftMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//                rightMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            }
//
//            // send robot back to specified encoder counts
//            leftMotor.setTargetPosition(1500);
//            rightMotor.setTargetPosition(1500);
//            if (!isSecondaryRobot) {
//                leftMotor2.setTargetPosition(1500);
//                rightMotor2.setTargetPosition(1500);
//            }
//
//            // Set motors to appropriate power levels
//            leftMotor.setPower(drivepower);
//            rightMotor.setPower(drivepower);
//            if (!isSecondaryRobot) {
//                leftMotor2.setPower(drivepower);
//                rightMotor2.setPower(drivepower);
//            }
//
//            // set motors to run to target encoder position and stop with brakes on
//            // movement will start here
//            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            if (!isSecondaryRobot) {
//                leftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                rightMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//
//            // wait while opmode is active and motor is busy running to position
//            while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
//            {
//                trackAndTelemeter(allTrackables,"back motion");
//            }
//
//            // unless disabled, wait so you can observe the final encoder position
//            resetStartTime();
//            do {
//                trackAndTelemeter(allTrackables,"back complete");
//            } while (pauseAtEachCorner && opModeIsActive() && getRuntime() < 2);
//
//            // We are in a corner of the rectangle, turn cw 90 degrees
//            telemetry.addData("motion", "rotating -90 degrees (cw)");
//            telemetry.update();
//            motionpacket.put("motion", "rotating -90 degrees (cw)");
//            dashboard.sendTelemetryPacket(motionpacket);
//            rotate(-90, turnpower);
//
//            // Rectangle side cw4
//
//            // reset encoder counts kept by motors
//            leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            if (!isSecondaryRobot) {
//                leftMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//                rightMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            }
//
//            // send robot left to specified encoder counts
//            leftMotor.setTargetPosition(1000);
//            rightMotor.setTargetPosition(1000);
//            if (!isSecondaryRobot) {
//                leftMotor2.setTargetPosition(1000);
//                rightMotor2.setTargetPosition(1000);
//            }
//
//            // Set motors to appropriate power levels
//            leftMotor.setPower(drivepower);
//            rightMotor.setPower(drivepower);
//            if (!isSecondaryRobot) {
//                leftMotor2.setPower(drivepower);
//                rightMotor2.setPower(drivepower);
//            }
//
//            // set motors to run to target encoder position and stop with brakes on
//            // movement will start here
//            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            if (!isSecondaryRobot) {
//                leftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                rightMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//
//            // wait while opmode is active and motor is busy running to position
//            while (opModeIsActive() && leftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
//            {
//                trackAndTelemeter(allTrackables,"left motion");
//            }
//
//            // unless disabled, wait so you can observe the final encoder position
//            resetStartTime();
//            do {
//                trackAndTelemeter(allTrackables,"left complete");
//            } while (pauseAtEachCorner && opModeIsActive() && getRuntime() < 2);
//
//            // We are in the starting (lower left) corner of the rectangle, turn 180 degrees completely around
//            telemetry.addData("motion", "rotating +180 degrees");
//            telemetry.update();
//            motionpacket.put("motion", "rotating +180 degrees");
//            dashboard.sendTelemetryPacket(motionpacket);
//            rotate(180, turnpower);
//            // We are in the starting corner of the rectangle, but facing right, turn ccw +90 degrees
//            telemetry.addData("motion", "rotating +90 degrees (ccw)");
//            telemetry.update();
//            motionpacket.put("motion", "rotating +90 degrees (ccw)");
//            dashboard.sendTelemetryPacket(motionpacket);
//            rotate(90, turnpower);
//        // repeat the loop to do it all over again (continue cw rectangles)
//        }
//
//        // Disable Vuforia Tracking and TFOD when OpMode is complete
//        targetsUltimateGoal.deactivate();
//
//        if (tfod != null) {
//            tfod.shutdown();
//        }
//
//    }
//
//// internal methods below here
//
//    // display vuforia trackables and drive motor status via telemetry
//    public void trackAndTelemeter(List<VuforiaTrackable> trackables, String direction) {
//        TelemetryPacket eppacket = new TelemetryPacket();
//        TelemetryPacket vuforiapacket = new TelemetryPacket();
//
//        // Display to SDK telemetry all drive encoder positions and busy statuses
//        telemetry.addData("direction", direction);
//        telemetry.addData("encoder-left", leftMotor.getCurrentPosition() + ", busy=" + leftMotor.isBusy());
//        telemetry.addData("encoder-right", rightMotor.getCurrentPosition() + ", busy=" + rightMotor.isBusy());
//        telemetry.update();
//        // Also display same to dashboard telemetry
//        eppacket.put("direction", direction);
//        eppacket.put("encoder-left", leftMotor.getCurrentPosition() + ", busy=" + leftMotor.isBusy());
//        eppacket.put("encoder-right", rightMotor.getCurrentPosition() + ", busy=" + rightMotor.isBusy());
//        dashboard.sendTelemetryPacket(eppacket);
//
//        // check all the trackable targets to see which one (if any) is visible
//        targetVisible = false;
//        for (VuforiaTrackable trackable : trackables) {
//            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
//                telemetry.addData("Visible Target", trackable.getName());
//                vuforiapacket.put("Visible Target", trackable.getName());
//                targetVisible = true;
//
//                // getUpdatedRobotLocation() will return null if no new information is available since
//                //  the last time that call was made, or if the trackable is not currently visible.
//                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
//                if (robotLocationTransform != null) {
//                    lastLocation = robotLocationTransform;
//                }
//                break;
//            }
//        }
//
//        // provide feedback as to where the robot is located (if we know)
//        if (targetVisible) {
//            // express position (translation) of robot in inches
//            VectorF translation = lastLocation.getTranslation();
//            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
//                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);
//            vuforiapacket.put("Pos (in)", String.format("{X, Y, Z} = %.1f, %.1f, %.1f", translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch));
//
//            // express the rotation of the robot in degrees
//            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
//            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
//            vuforiapacket.put("Rot (deg)", String.format("{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle));
//        }
//        else {
//            telemetry.addData("Visible Target", "none");
//            vuforiapacket.put("Visible Target", "none");
//        }
//        telemetry.update();
//        dashboard.sendTelemetryPacket(vuforiapacket);
//        idle();
//    }
//
//    // reset the cumulative IMU angle tracking to zero
//    private void resetImuAngle()
//    {
//        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//        globalAngle = 0;
//    }
//
//    // get current cumulative IMU angle rotation from last reset
//    // @return Angle in degrees. + = left, - = right
//    private double getAngle()
//    {
//        // We (Marvels) experimentally determined that the Z axis is the axis we want to use for
//        //  IMU heading angle, when using our robot designed for the Ultimate Goal season.  Our
//        //  REV hubs are mounted with the USB ports toward the ground and ceiling.
//        //  Logic suggests that the X axis would be correct (AxesOrder XYZ), but experiments say
//        //  otherwise.  We still need to reconcile why.
//        // We have to process the angle because the imu works in euler angles so the axis is
//        //  returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
//        //  180 degrees. We detect this transition and track the total cumulative angle of rotation.
//
//        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//
//        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;
//
//        if (deltaAngle < -180)
//            deltaAngle += 360;
//        else if (deltaAngle > 180)
//            deltaAngle -= 360;
//
//        globalAngle += deltaAngle;
//        lastAngles = angles;
//        return globalAngle;
//    }
//
//     // See if we are moving in a straight line and if not return a power correction value.
//     //  @return Power adjustment, + is adjust left - is adjust right
//    private double checkDirection()
//    {
//        // The gain value determines how sensitive the correction is to direction changes.
//        // You will have to experiment with your robot to get small smooth direction changes
//        // to stay on a straight line.
//        double correction, angle, gain = .10;
//        angle = getAngle();
//        if (angle == 0)
//            correction = 0;             // no adjustment.
//        else
//            correction = -angle;        // reverse sign of angle for correction.
//        correction = correction * gain;
//        return correction;
//    }
//
//     // rotate left or right the number of degrees. Does not support turning more than 180 degrees
//     //  @param degrees Degrees to turn, + for left (ccw), - for right (cw)
//    private void rotate(int degrees, double power)
//    {
//        double  leftPower, rightPower;
//        TelemetryPacket turnpacket = new TelemetryPacket();
//
//        // ensure setPower command does not begin motion, wait for RunMode.RUN... instead
//        leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//        rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//        if (!isSecondaryRobot) {
//            leftMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//            rightMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//        }
//
//        // restart imu movement tracking.
//        resetImuAngle();
//
//        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
//        // clockwise (right).
//
//        if (degrees < 0)
//        {   // turn right.
//            leftPower = power;
//            rightPower = -power;
//        }
//        else if (degrees > 0)
//        {   // turn left.
//            leftPower = -power;
//            rightPower = power;
//        }
//        else return;
//
//        // set power to rotate, turning motion will start here
//        leftMotor.setPower(leftPower);
//        rightMotor.setPower(rightPower);
//        if (!isSecondaryRobot) {
//            leftMotor2.setPower(leftPower);
//            rightMotor2.setPower(rightPower);
//        }
//
//        telemetry.addData("", "executing turn");
//        telemetry.update();
//        turnpacket.put("", "executing turn");
//        dashboard.sendTelemetryPacket(turnpacket);
//        // set motors to run while ignoring their encoders
//        // movement will start here
//        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        if (!isSecondaryRobot) {
//            leftMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//            rightMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        }
//
//        // rotate until turn is completed.
//        if (degrees < 0) {  // right turn
//            // On right turn we have to get off zero first.
//            while (opModeIsActive() && getAngle() == 0) {}
//
//            while (opModeIsActive() && getAngle() > degrees) {
//                leftMotor.setPower(leftPower * abs((getAngle() - degrees)) * turngain);     // decrease power as IMU angle approaches desired angle
//                rightMotor.setPower(rightPower * abs((getAngle() - degrees)) * turngain);
//                if (!isSecondaryRobot) {
//                    leftMotor2.setPower(leftPower * abs((getAngle() - degrees)) * turngain);
//                    rightMotor2.setPower(rightPower * abs((getAngle() - degrees)) * turngain);
//                }
//                if (abs(leftMotor.getPower()) < minturnpower) {                             // but don't go below minimum turn power
//                    leftMotor.setPower(minturnpower);
//                    rightMotor.setPower(-minturnpower);
//                    if (!isSecondaryRobot) {
//                        leftMotor2.setPower(minturnpower);
//                        rightMotor2.setPower(-minturnpower);
//                    }
//                }
//            }
//        }
//        else {              // left turn.
//            while (opModeIsActive() && getAngle() < degrees) {
//                leftMotor.setPower(leftPower * abs((getAngle() - degrees)) * turngain);     // decrease power as IMU angle approaches desired angle
//                rightMotor.setPower(rightPower * abs((getAngle() - degrees)) * turngain);
//                if (!isSecondaryRobot) {
//                    leftMotor2.setPower(leftPower * abs((getAngle() - degrees)) * turngain);     // decrease power as IMU angle approaches desired angle
//                    rightMotor2.setPower(rightPower * abs((getAngle() - degrees)) * turngain);
//                }
//                if (abs(leftMotor.getPower()) < minturnpower) {                             // but don't go below minimum turn power
//                    leftMotor.setPower(-minturnpower);
//                    rightMotor.setPower(minturnpower);
//                    if (!isSecondaryRobot) {
//                        leftMotor2.setPower(-minturnpower);
//                        rightMotor2.setPower(minturnpower);
//                    }
//                }
//            }
//        }
//        // turn the motors off.
//        leftMotor.setPower(0);
//        rightMotor.setPower(0);
//        if (!isSecondaryRobot) {
//            leftMotor2.setPower(0);
//            rightMotor2.setPower(0);
//        }
//        telemetry.addData("turn power", "zero");
//        telemetry.update();
//        turnpacket.put("turn power", "zero");
//        dashboard.sendTelemetryPacket(turnpacket);
//
//        // wait for rotation to stop.
//        sleep(1000);
//
//        // reset imu angle tracking on new heading.
//        resetImuAngle();
//    }
//
//    // display telemetry to both SDK and dashboard
//    public class MultiTelemetry
//    {
//        // THIS IS STILL A WORK IN PROGRESS.
//        // The idea is to have one method that will send to both SDK AND Dashboard Telemetry with
//        // a single method call.
//        public void init() {
//            telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//
//            // ...
//        }
//
//        // ...
//    }
//
//    /**
//     * Initialize the Vuforia localization engine.
//     */
//    private void initVuforia() {
//        // Retrieve the camera we are to use
//        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
//
//        /*
//         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
//         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
//         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
//         */
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        VuforiaLocalizer.Parameters vparameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
//        // VuforiaLocalizer.Parameters vparameters = new VuforiaLocalizer.Parameters();
//
//        vparameters.vuforiaLicenseKey = VUFORIA_KEY;
//
//        // We also indicate which camera on the RC we wish to use
//        vparameters.cameraName = webcamName;
//
//        // Make sure extended tracking is disabled for this example.
//        vparameters.useExtendedTracking = false;
//
//        //  Instantiate the Vuforia engine
//        vuforia = ClassFactory.getInstance().createVuforia(vparameters);
//    }
//
//    /**
//     * Initialize the TensorFlow Object Detection engine.
//     */
//    private void initTfod() {
//        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        tfodParameters.minResultConfidence = 0.8f;
//        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
//    }
//
}
