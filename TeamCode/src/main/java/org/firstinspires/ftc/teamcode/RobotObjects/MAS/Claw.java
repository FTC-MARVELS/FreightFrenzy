package org.firstinspires.ftc.teamcode.RobotObjects.MAS;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    //Configuration used: 6wheelConfig
    public Servo leftfinger;
    public Servo wrist;
    public Servo rightfinger;
    public Servo arm;

    public double armInit = 0.0;
    public double leftFingerInit = 0.4;
    public double wristInit = 0.4;
    public double rightFingerInit = 0.4;
    public double armMin = 0.0;
    public double armMax = 0.5;
    public double finger1Min = 0.2;
    public double finger2Min = 0.2;
    public double finger3Min = 0.2;
    public double finger1Max = 0.4;
    public double finger2Max = 0.4;
    public double finger3Max = 0.4;

    public Claw(HardwareMap hardwareMap) {
        leftfinger = hardwareMap.get(Servo.class,"leftfinger");
        wrist = hardwareMap.get(Servo.class,"wrist");
        rightfinger = hardwareMap.get(Servo.class,"rightfinger");
        leftfinger.setPosition(leftFingerInit);
        wrist.setPosition(wristInit);
        rightfinger.setPosition(rightFingerInit);
    }

    public void raiseWrist()
    {
        wrist.setPosition(1);
    }

    public void restWrist()
    {
        wrist.setPosition(0);
    }

    public void grab()
    {
        //double fingerPosition = Range.clip()
        leftfinger.setPosition(finger1Min);
        wrist.setPosition(finger2Min);
        rightfinger.setPosition(finger3Min);
    }

    public void release() {
        leftfinger.setPosition(finger1Max);
        wrist.setPosition(finger2Max);
        rightfinger.setPosition(finger3Max);
    }

    public void open() {
        leftfinger.setPosition(0.3);
        rightfinger.setPosition(1);
    }

    public void close() {
        leftfinger.setPosition(0.8);
        rightfinger.setPosition(0);
    }
}

