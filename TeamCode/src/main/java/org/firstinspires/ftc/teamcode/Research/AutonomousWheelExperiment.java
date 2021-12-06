package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@Disabled
@Autonomous(name = "AutonomousWheelExperiment")
public class AutonomousWheelExperiment extends LinearOpMode {
    private DcMotorEx frontright;
    private DcMotorEx frontleft;
    private DcMotorEx backright;
    private DcMotorEx backleft;
    private DcMotorEx middleright;
    private DcMotorEx middleleft;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("ticks", "start");

        frontright = hardwareMap.get(DcMotorEx.class, "Frontright");
        frontleft = hardwareMap.get(DcMotorEx.class, "Frontleft");
        backright = hardwareMap.get(DcMotorEx.class, "Backright");
        backleft = hardwareMap.get(DcMotorEx.class, "Backleft");
        middleright = hardwareMap.get(DcMotorEx.class, "Middleright");
        middleleft = hardwareMap.get(DcMotorEx.class, "Middleleft");
        telemetry.addData("ticks", "motors defined");

        waitForStart();

        frontleft.setPower(1);
        frontright.setPower(-1);

        sleep(200);
    }
}
