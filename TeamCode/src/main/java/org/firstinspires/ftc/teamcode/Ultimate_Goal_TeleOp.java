package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

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
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "LeftDrive");
        rightMotor = hardwareMap.get(DcMotorEx.class, "RightDrive");
        middleMotor = hardwareMap.get(DcMotorEx.class, "MiddleDrive");
        shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
        intake = hardwareMap.get(DcMotorEx.class, "Intake");
        roller = hardwareMap.get(DcMotorEx.class, "Roller");
        gate = hardwareMap.get(Servo.class, "Gate");
        feeder = hardwareMap.get(Servo.class, "Feeder");

        // The motor on one side must be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        // This eliminates the need to negate one of the setPower commands on left/right motor control
        leftMotor.setDirection(DcMotorEx.Direction.REVERSE);
        rightMotor.setDirection(DcMotorEx.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            rightMotor.setPower(gamepad1.right_stick_y);
            leftMotor.setPower(gamepad1.left_stick_y);
            middleMotor.setPower(gamepad1.left_stick_x);

            if (gamepad2.x) {
                if (shooter.getPower() == 0.0) {
                    shooter.setPower(1.0);
                } else {
                    shooter.setPower(0.0);
                }
            }

        }
    }
}
