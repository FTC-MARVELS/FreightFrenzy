package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Scanner;

/*
    Near Carousel
    =============
    1. Spin Carousel
    2. Scan
    3. Drop preload
    4. Go to Warehouse and Park

    //forward
    mecanum.encoderDrive(speed,12,12,12,12,12,12, 1.0);
    //backward
    mecanum.encoderDrive(speed,-12,-12,-12,-12,-12,-12, 1.0);
    //left
    mecanum.encoderDrive(speed,-12,0,12,12,0,-12, 1.0);
    //right
    mecanum.encoderDrive(speed,12,0,-12,-12,0,12, 1.0);
    //left turn
    mecanum.encoderDrive(speed,-12,0,-12,12,0,12, 1.0);
    //right turn
    mecanum.encoderDrive(speed,12,0,12,-12,0,-12, 1.0);
    //contract
    mecanum.encoderDrive(speed,3.15,0,-3.15,3.1,0,-3.1, 1.0);
    //expand
    mecanum.encoderDrive(speed,-3.15,0,3.15,-3.1,0,3.1, 1.0);

     */




@Autonomous(name="MAS_Auto_RedCarousel")
public class MAS_Auto_RedCarousel extends LinearOpMode {

    private Detector tfDetector = null;
    private ElapsedTime runtime = new ElapsedTime();

    private static String MODEL_FILE_NAME = "testmodeldec30.tflite";
    private static String LABEL_FILE_NAME = "testmodeldec30.txt";
    private static Classifier.Model MODEl_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double                  globalAngle, power = .30, correction;
    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        double speed = 0.8;
        double rotationSpeed = 0.5;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Spinner spinner = new Spinner(hardwareMap);
        Scanner scanner = new Scanner();
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        //mecanum.rightErrorAdjustment = 0.5;//1;
        try {
            tfDetector = new Detector(MODEl_TYPE, MODEL_FILE_NAME, LABEL_FILE_NAME, hardwareMap.appContext, telemetry);
        //    tfDetector.parent = this;
            tfDetector.activate();
        } catch (Exception ex) {
            telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
            sleep(3000);
            return;
        }
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
        telemetry.update();

        waitForStart();
        double spinnerDistance = 21.5;
        double spinnerRotate = 10;
        double shippingHubDistance = 40;
        double rotateNinety = 21;
        //SCAN CODE- EISHA AND HAMZA

        int position = 9;
        try {
            position = scanner.scan(hardwareMap, tfDetector, telemetry);
            telemetry.addData("Found in class", position);
            telemetry.update();
            sleep(500);

            if (position == 3) {
                sleep(300);
                position = scanner.scan(hardwareMap, tfDetector, telemetry);
                telemetry.addData("Found again in class", position);
                telemetry.update();
            }
        } catch (Exception e) {
            e.printStackTrace();
            telemetry.addData("Error", String.format("Unable to scan image. %s", e.getMessage()));
            position = 2;
            telemetry.addData("Found in class Exception ", position);
            telemetry.update();
        }

        telemetry.addData("FINAL POSITION", position);
        telemetry.update();
        if(position == 3 || position == 9) {
            position = 2;
        }
        //Testing single position  only
        //position = 2;
        // restart imu movement tracking.
        resetAngle();
        telemetry.addData("Angle after reset " , lastAngles.firstAngle + ": " + lastAngles.secondAngle + ": " + lastAngles.thirdAngle);
        telemetry.update();

        sleep(1000);
        double wareHouseDistance = 55;
        //mecanum.positionForDrop(position,0);
        if(position==1) {
            mecanum.move_forward_auto(speed, 15, 10.0);//position 1 and 2
            mecanum.move_left_auto(speed, 4, 10.0);
        } else if(position==2) {
            mecanum.move_forward_auto(speed, 16, 10.0);//position 1 and 2
            mecanum.move_left_auto(speed, 4, 10.0);
        } else {
            mecanum.move_forward_auto(speed, 12, 10.0);//position 1 and 2
        }
        int encoderPosition = mecanum.positionForDropSidewaysAuto(position, "Red"); //this code moves closer to the hub, drops and then moves back slightly
        telemetry.addData("Encoder Position", encoderPosition);
        telemetry.update();
        //return arm to base position
        mecanum.armToEncoderPosition(encoderPosition);
        sleep(1000);
        claw.moveTail(-0.6); //tail up
        sleep(800);

        //mecanum.move_backward_auto(0.03, 0.5, 1.0);
        //rotate(-8, rotationSpeed, mecanum);

        mecanum.move_backward_auto(speed, 25, 20.0);

        //mecanum.rotate_counter_clock_auto(rotationSpeed, spinnerRotate, 10.0);

        rotate(40, 0.3, mecanum);
        mecanum.move_left_auto(0.5, 12, 20.0);
        mecanum.move_backward_auto(0.5, 9, 20.0);
        sleep(100);
        mecanum.move_backward_auto(0.3, 3, 20.0);
        sleep(100);
        mecanum.move_backward_auto(0.1, 3, 1.0);
        //Spin
        spinner.setVelocity(1500);
        sleep(2600);
        spinner.setPower(0);


        mecanum.move_forward_auto(speed, 10, 10.0);
        rotate(-40, 0.3, mecanum);
        //claw.moveTail(0.6);
        //sleep(800);
        mecanum.move_left_auto(speed, 20, 20.0);
        mecanum.move_backward_auto(speed, 7, 20.0);

  /*      spinner.setPower(-0.58);
        spinner.setPower(0);
*/


    }
    /**
     * Resets the cumulative angle tracking to zero.
     */
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
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
        // gazala commented code resetAngle();

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

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }

}