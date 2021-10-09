package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import static java.lang.Math.abs;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous

public class BasicAutonomousRotateAS   extends LinearOpMode{

        DcMotor leftMotor;
        DcMotor rightMotor;
        DcMotor leftMotor2;
        DcMotor rightMotor2;
    public static double turnpower = 0.9;       // range 0.0 - 1.0
    public static double minturnpower = 0.8;
    public static double turngain = 0.05;
        public static boolean isSecondaryRobot = false;     // set to true when using secondary, which has less hardware
    // declare IMU and its angle object & variables
    BNO055IMU imu;
    Orientation             lastAngles = new Orientation();
    double                  globalAngle, correction;
    // define an instance of FtcDashboard;
    FtcDashboard dashboard;
        @Override
        public void runOpMode() throws InterruptedException {
            leftMotor = hardwareMap.get(DcMotor.class, "0");
            rightMotor = hardwareMap.get(DcMotor.class, "1");
            leftMotor2 = hardwareMap.get(DcMotor.class, "2");
            rightMotor2 = hardwareMap.get(DcMotor.class, "3");

            waitForStart();

            leftMotor.setPower(0.5);
            leftMotor2.setPower(0.5);
            rightMotor.setPower(-0.5);
            rightMotor2.setPower(-0.5);

            sleep(200);

            leftMotor.setPower(-0.5);
            leftMotor2.setPower(-0.5);
            rightMotor.setPower(0.5);
            rightMotor2.setPower(0.5);

            sleep(1000);


//doing the 1st turns
            leftMotor.setPower(0.75);  //turns right
            leftMotor2.setPower(0.75);

            sleep(200);

            leftMotor.setPower(0.5);
            leftMotor2.setPower(0.5);
            rightMotor.setPower(-0.5);
            rightMotor2.setPower(-0.5);

            sleep(200);

            rightMotor.setPower(-0.75);
            rightMotor2.setPower(-0.75);
            leftMotor.setPower(0.25);
            leftMotor2.setPower(0.25);

            sleep(500);
//doing the opposite action
            rightMotor.setPower(0.75);  //turns left
            rightMotor2.setPower(0.75);
            sleep(200);

            rightMotor.setPower(0.5);
            rightMotor2.setPower(0.5);
            leftMotor.setPower(-0.5);
            leftMotor2.setPower(-0.5);

            sleep(200);

            leftMotor.setPower(-0.75);
            leftMotor2.setPower(-0.75);
            rightMotor.setPower(0.25);
            rightMotor2.setPower(0.25);

            sleep(500);

            rotate(50, 0.5);

        }

    // reset the cumulative IMU angle tracking to zero
    private void resetImuAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }

    // get current cumulative IMU angle rotation from last reset
    // @return Angle in degrees. + = left, - = right
    private double getAngle()
    {
        // We (Marvels) experimentally determined that the Z axis is the axis we want to use for
        //  IMU heading angle, when using our robot designed for the Ultimate Goal season.  Our
        //  REV hubs are mounted with the USB ports toward the ground and ceiling.
        //  Logic suggests that the X axis would be correct (AxesOrder XYZ), but experiments say
        //  otherwise.  We still need to reconcile why.
        // We have to process the angle because the imu works in euler angles so the axis is
        //  returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        //  180 degrees. We detect this transition and track the total cumulative angle of rotation.

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

    // See if we are moving in a straight line and if not return a power correction value.
    //  @return Power adjustment, + is adjust left - is adjust right
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


    // rotate left or right the number of degrees. Does not support turning more than 180 degrees
    //  @param degrees Degrees to turn, + for left (ccw), - for right (cw)
    private void rotate(int degrees, double power)
    {
        double  leftPower, rightPower;
        TelemetryPacket turnpacket = new TelemetryPacket();

        // ensure setPower command does not begin motion, wait for RunMode.RUN... instead
        leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        if (!isSecondaryRobot) {
            leftMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            rightMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        }

        // restart imu movement tracking.
        resetImuAngle();

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

        // set power to rotate, turning motion will start here
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
        if (!isSecondaryRobot) {
            leftMotor2.setPower(leftPower);
            rightMotor2.setPower(rightPower);
        }

        telemetry.addData("", "executing turn");
        telemetry.update();
        turnpacket.put("", "executing turn");
        dashboard.sendTelemetryPacket(turnpacket);
        // set motors to run while ignoring their encoders
        // movement will start here
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (!isSecondaryRobot) {
            leftMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // rotate until turn is completed.
        if (degrees < 0) {  // right turn
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {}

            while (opModeIsActive() && getAngle() > degrees) {
                leftMotor.setPower(leftPower * abs((getAngle() - degrees)) * turngain);     // decrease power as IMU angle approaches desired angle
                rightMotor.setPower(rightPower * abs((getAngle() - degrees)) * turngain);
                if (!isSecondaryRobot) {
                    leftMotor2.setPower(leftPower * abs((getAngle() - degrees)) * turngain);
                    rightMotor2.setPower(rightPower * abs((getAngle() - degrees)) * turngain);
                }
                if (abs(leftMotor.getPower()) < minturnpower) {                             // but don't go below minimum turn power
                    leftMotor.setPower(minturnpower);
                    rightMotor.setPower(-minturnpower);
                    if (!isSecondaryRobot) {
                        leftMotor2.setPower(minturnpower);
                        rightMotor2.setPower(-minturnpower);
                    }
                }
            }
        }
        else {              // left turn.
            while (opModeIsActive() && getAngle() < degrees) {
                leftMotor.setPower(leftPower * abs((getAngle() - degrees)) * turngain);     // decrease power as IMU angle approaches desired angle
                rightMotor.setPower(rightPower * abs((getAngle() - degrees)) * turngain);
                if (!isSecondaryRobot) {
                    leftMotor2.setPower(leftPower * abs((getAngle() - degrees)) * turngain);     // decrease power as IMU angle approaches desired angle
                    rightMotor2.setPower(rightPower * abs((getAngle() - degrees)) * turngain);
                }
                if (abs(leftMotor.getPower()) < minturnpower) {                             // but don't go below minimum turn power
                    leftMotor.setPower(-minturnpower);
                    rightMotor.setPower(minturnpower);
                    if (!isSecondaryRobot) {
                        leftMotor2.setPower(-minturnpower);
                        rightMotor2.setPower(minturnpower);
                    }
                }
            }
        }
        // turn the motors off.
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        if (!isSecondaryRobot) {
            leftMotor2.setPower(0);
            rightMotor2.setPower(0);
        }
        telemetry.addData("turn power", "zero");
        telemetry.update();
        turnpacket.put("turn power", "zero");
        dashboard.sendTelemetryPacket(turnpacket);

        // wait for rotation to stop.
        sleep(1000);

        // reset imu angle tracking on new heading.
        resetImuAngle();
    }    // todo: write your code here
}