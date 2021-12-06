package org.firstinspires.ftc.teamcode.RobotObjects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Spinner {
    //Configuration used: 6wheelConfig
    private DcMotor CarouselWheel;
    //private double power = 0;
    private int time = 0;


    public Spinner(HardwareMap hardwareMap){
        //As per 6wheelConfig
        CarouselWheel = hardwareMap.get(DcMotorEx .class, "carouselMotor");

    }
    public void setPower(double power){
        this.CarouselWheel.setPower(power);
    }
    public double getPower(){
        return this.CarouselWheel.getPower();
    }
    public void setTime(int time){
        this.time = time;
    }
    public double getTime(){
        return this.time;
    }


}
