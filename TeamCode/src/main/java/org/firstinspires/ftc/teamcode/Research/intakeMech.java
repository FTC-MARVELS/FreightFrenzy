package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous

@Disabled
public class intakeMech extends LinearOpMode {
    public Servo finger1;
    public Servo finger2;

    @Override
    public void runOpMode() throws InterruptedException {
        finger1 = hardwareMap.get(Servo.class, "finger1");
        finger2 = hardwareMap.get(Servo.class, "finger2");

        waitForStart();



        telemetry.addData("Postion Claw 1:%d", finger1.getPosition());
        telemetry.addData("Postion Claw 2:%d", finger2.getPosition());
        telemetry.update();
        release();


    }
    public void release(){
        finger1.setPosition(0.6);
        finger2.setPosition(0.35);
    }

    public void grab(){
        finger1.setPosition(0.2);
        finger2.setPosition(0.75);

    }

}
