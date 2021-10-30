package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;
//import com.qualcomm.robotcore.hardware.HardwareMap;

//Using 6wheelConfig
@Autonomous(name = "RoundRound2")
@Disabled
public class RoundRound2 extends LinearOpMode {
//    DcMotorEx motorFrontLeft, motorBackLeft, motorFrontRight, motorBackRight;
//    @Override
//    public void runOpMode() throws InterruptedException
//    {
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//        //HardwareMap hardwareMap = new HardwareMap(this);
//        waitForStart();
//        motorFrontLeft = hardwareMap.get(DcMotorEx.class, "left");
//        motorFrontRight = hardwareMap.get(DcMotorEx.class, "right");
//        //motorFrontLeft = hardwareMap.get(DcMotorEx.class, "top left");
//        //motorFrontRight = hardwareMap.get(DcMotorEx.class, "top right");
//        //motorBackLeft = hardwareMap.get(DcMotorEx.class, "bottom left");
//        //motorBackRight = hardwareMap.get(DcMotorEx.class, "bottom right");
//        motorFrontLeft.setDirection(DcMotor.Direction.FORWARD);
//        motorFrontRight.setDirection(DcMotor.Direction.FORWARD);
//        double leftPower = 0.0;
//        double rightPower = 0.0;
//        motorFrontLeft.setPower(0.5);
//        motorFrontRight.setPower(0.5);
//        sleep(1000);
//        motorFrontLeft.setPower(0.0);
//        motorFrontRight.setPower(0.0);
//        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
//
//        telemetry.update();
//        sleep(5000);
        //
//  DcMotor CarouselWheel;
        static final double     FORWARD_SPEED = -0.4;
        private ElapsedTime runtime = new ElapsedTime();
        @Override
        public void runOpMode() throws InterruptedException
        {
            waitForStart();
            Spinner spinner = new Spinner(hardwareMap);
            spinner.setPower(FORWARD_SPEED);
            spinner.setTime(100);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < spinner.getTime())) {
                telemetry.addData("Done", runtime.seconds());
                telemetry.addData("power", spinner.getPower());
                telemetry.update();
            }
            spinner.setPower(0);
    }

}
