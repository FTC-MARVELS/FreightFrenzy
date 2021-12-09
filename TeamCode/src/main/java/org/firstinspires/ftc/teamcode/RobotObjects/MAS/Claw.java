package org.firstinspires.ftc.teamcode.RobotObjects.MAS;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    //Configuration used: 6wheelConfig
    public Servo leftclaw;
    public Servo rightclaw;
    public Servo rightwrist;
    public Servo leftwrist;
    public Servo arm;

    public double armInit = 0.0;
    public double leftClawInit = 1.0;
    public double rightWristInit = 0.0;
    public double rightClawInit = 0.0;
    public double leftWristInit = 0.0;
    public double armMin = 0.0;
    public double armMax = 0.5;
    public double finger1Min = 0.2;
    public double finger2Min = 0.2;
    public double finger3Min = 0.2;
    public double finger1Max = 0.4;
    public double finger2Max = 0.4;
    public double finger3Max = 0.4;

    public DcMotorEx rotatingGripper;

    public Claw(HardwareMap hardwareMap) {
      /*  rightwrist = hardwareMap.get(Servo.class,"rightwrist");
        leftwrist = hardwareMap.get(Servo.class,"leftwrist");
        rightclaw = hardwareMap.get(Servo.class,"rightclaw");
        leftclaw = hardwareMap.get(Servo.class,"leftclaw");*/
        rotatingGripper = hardwareMap.get(DcMotorEx.class, "rotatingGripper");
        //rightwrist.setPosition(rightWristInit);
        //leftwrist.setPosition(leftWristInit);
        //rightclaw.setPosition(rightClawInit);
        //leftclaw.setPosition(leftClawInit);
    }

    public void grabObject() {
        rotatingGripper.setPower(0.5);
        //rotatingGripper.setVelocity(2000);
    }

    public void dropObject() {
        rotatingGripper.setPower(-0.5);
        //rotatingGripper.setVelocity(-2000);
    }

    public void stopGripper() {
        rotatingGripper.setPower(0);
        //rotatingGripper.setVelocity(0);
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

