package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

@TeleOp(name = "Ultimate Goal TeleOp")

public class Ultimate_Goal_TeleOp extends LinearOpMode{

    // declare our motors
    DcMotorEx leftMotor;
    DcMotorEx rightMotor;
    DcMotorEx middleMotor;
    DcMotorEx shooter;
    DcMotorEx intake;
    DcMotorEx roller;

    // declare our servos
    Servo gate;
    Servo feeder;
    // called when init button is pressed
    @Override
    public void runOpMode() throws InterruptedException
    {
        leftMotor = hardwareMap.get(DcMotorEx.class, "LeftDrive");
        rightMotor = hardwareMap.get(DcMotorEx.class, "RightDrive");
        middleMotor = hardwareMap.get(DcMotorEx.class, "MiddleDrive");
        shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
        intake = hardwareMap.get(DcMotorEx.class, "Intake");
        roller = hardwareMap.get(DcMotorEx.class, "Roller");
        gate = hardwareMap.get(Servo.class, "Gate");
        feeder = hardwareMap.get(Servo.class, "Feeder");
    }

}
