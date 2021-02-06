package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Config
@TeleOp(name = "Ultimate Goal TeleOp")

public class Ultimate_Goal_TeleOp extends LinearOpMode{

    // declare our motors
    DcMotorEx leftMotor;
    DcMotorEx rightMotor;
    DcMotorEx shooter;
    DcMotorEx intake;
    DcMotorEx roller;
    DcMotorEx arm;

    // declare our servos
    Servo gate;
    Servo feeder;
    Servo grabber;

    // initialize instance of FtcDashboard
    FtcDashboard dashboard = FtcDashboard.getInstance();

    // predefine some variables for dashboard configuration
    public static boolean useVuforia = false;      // set to true to enable Vuforia & trackables

    // initializeVuforia
    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'vparameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
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
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "LeftDrive");
        rightMotor = hardwareMap.get(DcMotorEx.class, "RightDrive");
        shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
        intake = hardwareMap.get(DcMotorEx.class, "Intake");
        roller = hardwareMap.get(DcMotorEx.class, "Roller");
        arm = hardwareMap.get(DcMotorEx.class, "Arm");
        gate = hardwareMap.get(Servo.class, "Gate");
        feeder = hardwareMap.get(Servo.class, "Feeder");
        grabber = hardwareMap.get(Servo.class, "Grabber");

        // The motor on one side must be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        // This eliminates the need to negate one of the setPower commands on left/right motor control
        leftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightMotor.setDirection(DcMotorEx.Direction.REVERSE);

        // declare dashboard telelmetry
        TelemetryPacket modepacket = new TelemetryPacket();
        TelemetryPacket imupacket = new TelemetryPacket();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

         // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            leftMotor.setPower(-gamepad1.left_stick_y);
            rightMotor.setPower(-gamepad1.right_stick_y);

            if (gamepad2.x) {
                if (shooter.getPower() == 0.0) {
                    shooter.setPower(0.86);
                } else {
                    shooter.setPower(0.0);
                }
            }

            arm.setPower(0.5 * gamepad2.left_stick_y);
            intake.setPower(gamepad2.right_stick_y);
            roller.setPower(gamepad2.left_stick_x);

            if (gamepad2.right_bumper) {
                feeder.setPosition(0.45);
            } else {
                feeder.setPosition(0.73);
            }

            if (gamepad2.left_bumper) {
                grabber.setPosition(0);
            } else {
                grabber.setPosition(0.3);
            }
        }
    }
}
