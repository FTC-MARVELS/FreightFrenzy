// original code from: https://stemrobotics.cs.pdx.edu/node/7265
// Simple autonomous program that drives bot forward until end of period
// or touch sensor is hit. If touched, backs up a bit and turns 90 degrees
// right and keeps going. Demonstrates obstacle avoidance and use of the
// REV Hub's built in IMU in place of a gyro. Also uses gamepad1 buttons to
// simulate touch sensor press and supports left as well as right turn.
//
// Also uses IMU to drive in a straight line when not avoiding an obstacle.

// Modified by team MARVELS to demonstrate these abilities on their Ultimate Goal robot

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="Drive Imu", group="Exercises")
//@Disabled
public class DriveImu extends LinearOpMode
{
    DcMotor leftMotor, rightMotor;
    BNO055IMU imu;
    Orientation             lastAngles = new Orientation();
    double                  globalAngle, power = 0.20, correction;
    boolean                 aButton, bButton;

    // called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException
    {
        leftMotor = hardwareMap.dcMotor.get("LeftDrive");
        rightMotor = hardwareMap.dcMotor.get("RightDrive");

        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // define an instance of FtcDashboard;
        FtcDashboard dashboard;

        // initialize FtcDashboard
        dashboard = FtcDashboard.getInstance();

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

        TelemetryPacket imupacket = new TelemetryPacket();
        TelemetryPacket motionpacket = new TelemetryPacket();

        telemetry.addData("Mode", "imu calibrating...");
        telemetry.update();
        imupacket.put("Mode", "imu calibrating...");
        dashboard.sendTelemetryPacket(imupacket);

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }

        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        imupacket.put("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.addData("Mode", "waiting for start");
        motionpacket.put("Mode", "waiting for start");
        dashboard.sendTelemetryPacket(imupacket);
        dashboard.sendTelemetryPacket(motionpacket);

        // wait for start button.
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        sleep(1000);

        // drive until end of period.

        while (opModeIsActive())
        {
            // Use gyro to drive in a straight line.
            correction = checkDirection();

            telemetry.addData("1 imu heading", lastAngles.firstAngle);
            telemetry.addData("2 global heading", globalAngle);
            telemetry.addData("3 correction", correction);
            telemetry.update();
            imupacket.put("1 imu heading", lastAngles.firstAngle);
            imupacket.put("2 global heading", globalAngle);
            imupacket.put("3 correction", correction);
            dashboard.sendTelemetryPacket(imupacket);

            leftMotor.setPower(power - correction);
            rightMotor.setPower(power + correction);

            // We record the sensor values because we will test them in more than
            // one place with time passing between those places. See the lesson on
            // Timing Considerations to know why.

            aButton = gamepad1.a;
            bButton = gamepad1.b;

            if (aButton || bButton){
                // backup.
                telemetry.addData("motion", "backing up");
                telemetry.update();
                motionpacket.put("motion", "backing up");
                dashboard.sendTelemetryPacket(motionpacket);
                leftMotor.setPower(-power);
                rightMotor.setPower(-power);
                sleep(1000);

                // stop.
                telemetry.addData("motion", "stopping");
                telemetry.update();
                motionpacket.put("motion", "stopping");
                dashboard.sendTelemetryPacket(motionpacket);
                leftMotor.setPower(0);
                rightMotor.setPower(0);
                sleep(1500);

                // turn 90 degrees right.
                if (aButton){
                    telemetry.addData("motion", "rotating -90 degrees (cw)");
                    telemetry.update();
                    motionpacket.put("motion", "rotating -90 degrees (cw)");
                    dashboard.sendTelemetryPacket(motionpacket);
                    rotate(-90, power);
                }

                // turn 90 degrees left.
                if (bButton){
                    telemetry.addData("motion", "rotating +90 degrees (ccw)");
                    telemetry.update();
                    motionpacket.put("motion", "rotating +90 degrees (ccw)");
                    dashboard.sendTelemetryPacket(motionpacket);
                    rotate(90, power);
                }

                telemetry.addData("motion", "rotation complete");
                telemetry.update();
                motionpacket.put("motion", "rotation complete");
                dashboard.sendTelemetryPacket(motionpacket);
            }
        }

        // turn the motors off.
        rightMotor.setPower(0);
        leftMotor.setPower(0);
    }

    /**
     * Resets the cumulative angle tracking to zero.
     */
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
    private double getAngle()
    {
        // We (Marvels) experimentally determined that the X axis is the axis we want to use for
        //  heading angle, for our robot designed during the Ultimate Goal season.  Our REV hubs
        //  are mounted with the USB ports toward the ground and ceiling.
        //  We are therefore using AxesOrder.XYZ rather than AxesOrder.ZYX per the example code.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        //  returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        //  180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

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
    private void rotate(int degrees, double power)
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
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {}

            while (opModeIsActive() && getAngle() > degrees) {}
        }
        else    // left turn.
            while (opModeIsActive() && getAngle() < degrees) {}

        // turn the motors off.
        rightMotor.setPower(0);
        leftMotor.setPower(0);

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }
}
