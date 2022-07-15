package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous
public class touchSensorTest extends LinearOpMode {

    TouchSensor touch;
    public Servo servo;
    @Override
    public void runOpMode() throws InterruptedException {
        //touch = hardwareMap.get(TouchSensor.class, "Touch");
        servo = hardwareMap.get(Servo.class, "servo");
        touch = hardwareMap.get(TouchSensor.class, "Touch");
        waitForStart();
        //Testing for code revert
        while (opModeIsActive()) {
         if(touch.isPressed()) {
             servo.setPosition(0);
         } else {
             servo.setPosition(1);
         }
    }}}
