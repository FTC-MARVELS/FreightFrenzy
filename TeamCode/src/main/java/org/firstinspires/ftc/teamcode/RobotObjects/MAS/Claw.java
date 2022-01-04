package org.firstinspires.ftc.teamcode.RobotObjects.MAS;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    //Configuration used: 6wheelConfig
    public Servo leftclaw;
    public Servo rightclaw;
    public Servo rightwrist;
    public Servo leftwrist;
    public CRServo swing;
    public Servo floor;
    public CRServo tail;
    public Servo finger;

    public double armInit = 0.0;

    public DcMotorEx intake;

    public Claw(HardwareMap hardwareMap) {
      /*  rightwrist = hardwareMap.get(Servo.class,"rightwrist");
        leftwrist = hardwareMap.get(Servo.class,"leftwrist");
        rightclaw = hardwareMap.get(Servo.class,"rightclaw");
        leftclaw = hardwareMap.get(Servo.class,"leftclaw");*/
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        swing = hardwareMap.get(CRServo.class,"swing");
        floor = hardwareMap.get(Servo.class,"floor");
        tail = hardwareMap.get(CRServo.class,"secondArm");
        finger = hardwareMap.get(Servo.class,"finger");
        swing.setPower(0);
        swing.setDirection(DcMotorSimple.Direction.FORWARD);
        tail.setPower(0);
        tail.setDirection(DcMotorSimple.Direction.FORWARD);
        //floor.setPosition(0.0);
        //floor.setDirection(DcMotorSimple.Direction.FORWARD);
        //rightwrist.setPosition(rightWristInit);
        //leftwrist.setPosition(leftWristInit);
        //rightclaw.setPosition(rightClawInit);
        //leftclaw.setPosition(leftClawInit);
    }

   /* public void holdBucket() {
        bucket.setPosition(0.5);
    }*/

    public void moveTail(double power) {
        tail.setPower(power);
    }

    public void moveSwing(double power) {
        swing.setPower(power);
    }

    public void moveFloor(double power) {
        floor.setPosition(power);
    }

    public void openFinger() {
        finger.setPosition(1);
    }

    public void closeFinger() {
        finger.setPosition(0);
    }

    public void grabObject() {
        intake.setPower(0.5);
        //rotatingGripper.setVelocity(2000);
    }

    public void dropObject() {
        intake.setPower(-0.38);
        //rotatingGripper.setVelocity(-2000);
    }

    public void hamza() {
        intake.setPower(-0.55);
    }

    public void stopIntake() {
        intake.setPower(0);
        //rotatingGripper.setVelocity(0);
    }

    public void startIntake(double power) {
        intake.setPower(-power);
    }

    public void reverseIntake(double power) {
        intake.setPower(power);
    }

    /*public void raiseWrist(double power)
    {
        rightwrist.setPosition(-power);
        leftwrist.setPosition(power);
    }

    public void restWrist()
    {
        rightwrist.setPosition(0);
        leftwrist.setPosition(0);
    }*/

    public void grab()
    {
        //double fingerPosition = Range.clip()
        //leftclaw.setPosition(finger1Min);
        //rightwrist.setPosition(finger2Min);
        //rightclaw.setPosition(finger3Min);
    }

    public void release() {
        //leftclaw.setPosition(finger1Max);
        //rightwrist.setPosition(finger2Max);
        //rightclaw.setPosition(finger3Max);
    }

    /*public void openClaws() {
        leftclaw.setPosition(0.6);
        rightclaw.setPosition(0.4);
    }

    public void closeClaws() {
        leftclaw.setPosition(0.85);
        rightclaw.setPosition(0.1);
    }*/
}

