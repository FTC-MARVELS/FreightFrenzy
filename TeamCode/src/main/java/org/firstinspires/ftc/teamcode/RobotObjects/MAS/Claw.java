package org.firstinspires.ftc.teamcode.RobotObjects.MAS;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Claw {
    //Configuration used: 6wheelConfig
    public Servo clawFinger1;
    public Servo clawFinger2;
    public Servo arm;

    public double armInit = 0.0;
    public double finger1Init = 0.4;
    public double finger2Init = 0.4;
    public double armMin = 0.0;
    public double armMax = 0.5;
    public double finger1Min = 0.2;
    public double finger2Min = 0.2;
    public double finger1Max = 0.4;
    public double finger2Max = 0.4;

    public Claw(HardwareMap hardwareMap) {
        clawFinger1 = hardwareMap.get(Servo.class,"finger1");
        clawFinger2 = hardwareMap.get(Servo.class,"finger2");
        clawFinger1.setPosition(finger1Init);
        clawFinger2.setPosition(finger2Init);
    }

    public void lift()
    {

    }

    public void rest()
    {

    }

    public void grab()
    {
        //double fingerPosition = Range.clip()
        clawFinger1.setPosition(finger1Min);
        clawFinger2.setPosition(finger2Min);
    }

    public void release() {
        clawFinger1.setPosition(finger1Max);
        clawFinger2.setPosition(finger2Max);
    }
}

