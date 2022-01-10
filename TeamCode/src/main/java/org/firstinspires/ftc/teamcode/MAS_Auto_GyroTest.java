package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Scanner;
import org.firstinspires.ftc.teamcode.tfrec.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;

@Autonomous(name = "MAS_Auto_GyroTest")
@Disabled

public class MAS_Auto_GyroTest extends LinearOpMode {

    private Detector tfDetector = null;
    private ElapsedTime runtime = new ElapsedTime();

    private static String MODEL_FILE_NAME = "redwarehouseorangeconewithbase.tflite";
    private static String LABEL_FILE_NAME = "redwarehouseorangeconewithbase.txt";
    private static Classifier.Model MODEl_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;
    private CRServo secondArm;
    BNO055IMU               imu;
    Orientation lastAngles = new Orientation();
    double                  globalAngle, power = .30, correction;
    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        secondArm = hardwareMap.get(CRServo.class, "secondArm");
        double speed = 0.75;
        double rotationSpeed = 0.2;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Scanner scanner = new Scanner();
        Claw claw = new Claw(hardwareMap);
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        mecanum.rightErrorAdjustment = 0.5;//1;

        //telemetry.update("Servo Power", secondArm.getPower());
       /* try {
            tfDetector = new Detector(MODEl_TYPE, MODEL_FILE_NAME, LABEL_FILE_NAME, hardwareMap.appContext, telemetry);
            //tfDetector.parent = this;
            tfDetector.activate();
        } catch (Exception ex) {
            telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
            sleep(3000);
            return;
        }*/
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }

        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.addData("Angle " , getAngle());
        telemetry.update();

        waitForStart();

        /*secondArm.setDirection(DcMotorSimple.Direction.FORWARD);
        secondArm.setPower(0.8);
        sleep(1000);
        secondArm.setPower(0.5);*/

        correction = checkDirection();

        telemetry.addData("1 imu heading", lastAngles.firstAngle);
        telemetry.addData("2 global heading", globalAngle);
        telemetry.addData("3 correction", correction);
        telemetry.update();
        sleep(500);

        resetAngle();
        telemetry.addData("Angle after reset " , lastAngles.firstAngle + ": " + lastAngles.secondAngle + ": " + lastAngles.thirdAngle);
        telemetry.update();

        sleep(1000);

        mecanum.move_left_auto(0.6, 10, 10.0);
        rotate(45, 0.3, mecanum);
        resetAngle();
        telemetry.addData("Angle after reset " , lastAngles.firstAngle + ": " + lastAngles.secondAngle + ": " + lastAngles.thirdAngle);
        telemetry.update();

        sleep(1000);


    }

    /**
     * Resets the cumulative angle tracking to zero.
     */
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
        //telemetry.addData("Last Angles: ", lastAngles);
        telemetry.addData("Angle after reset " , lastAngles.firstAngle + ": " + lastAngles.secondAngle + ": " + lastAngles.thirdAngle);
        telemetry.addData("Global Angle in Reset Angle: " , globalAngle);
    }

    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
    private double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    /**
     * See if we are moving in a straight line and if not return a power correction value.
     * @return Power adjustment, + is adjust left - is adjust right.
     */
    private double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
    private void rotate(int degrees, double power, Mecanum_Wheels mecanumWheels)
    {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = power;
            rightPower = -power;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -power;
            rightPower = power;
        }
        else return;

        // set power to rotate.
        //leftMotor.setPower(leftPower);
        // rightMotor.setPower(rightPower);
        mecanumWheels.frontleft.setPower(leftPower);
        mecanumWheels.backleft.setPower(-leftPower);
        mecanumWheels.frontright.setPower(rightPower);
        mecanumWheels.backright.setPower(-rightPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {
                telemetry.addData("Angle is 0", getAngle());
            }

            while (opModeIsActive() && getAngle() > degrees) {
                telemetry.addData("Angle is > degrees" , getAngle() + ":" + degrees);
            }
        }
        else    // left turn.
            while (opModeIsActive() && getAngle() < degrees) {
                telemetry.addData("Angle is < degrees" , getAngle() + ":" + degrees);
            }

        // turn the motors off.
        mecanumWheels.frontleft.setPower(0);
        mecanumWheels.backleft.setPower(0);
        mecanumWheels.frontright.setPower(0);
        mecanumWheels.backright.setPower(0);
        telemetry.update();
        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }
}

