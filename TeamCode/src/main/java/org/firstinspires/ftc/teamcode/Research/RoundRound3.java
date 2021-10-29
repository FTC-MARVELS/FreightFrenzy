package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous
@Disabled
public class RoundRound3 extends LinearOpMode {
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
        DcMotor motorBackRight, motorFrontRight, motorBackLeft, motorFrontLeft;
        static final double     FORWARD_SPEED = 0.6;
        private ElapsedTime runtime = new ElapsedTime();
        @Override
        public void runOpMode() throws InterruptedException
        {
            waitForStart();
            //motorFrontLeft = hardwareMap.get(DcMotorEx.class, "motorFrontLeft");
            //motorFrontRight = hardwareMap.get(DcMotorEx.class, "motorFrontRight");
            motorBackLeft = hardwareMap.get(DcMotorEx.class, "left");
            motorBackRight = hardwareMap.get(DcMotorEx.class, "right");

            //motorFrontLeft.setPower(FORWARD_SPEED);
            //motorFrontRight.setPower(FORWARD_SPEED);
            motorBackLeft.setPower(FORWARD_SPEED);
            motorBackRight.setPower(FORWARD_SPEED);
            motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            runtime.reset();
            runtime.reset();
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() <1.0)) {
                telemetry.addData("Done", runtime.seconds());
                telemetry.update();
            }
            motorFrontLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackLeft.setPower(0);
            motorBackRight.setPower(0);
    }

}
