package org.firstinspires.ftc.teamcode.RobotObjects;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Spinner {
    //Configuration used: 6wheelConfig
    private DcMotorEx CarouselWheel;
    LinearOpMode parent;
    //private double power = 0;
    private int time = 0;


    public Spinner(HardwareMap hardwareMap){
        //As per 6wheelConfig
        CarouselWheel = hardwareMap.get(DcMotorEx .class, "carouselMotor");

    }
    public void spinDuck(){
        CarouselWheel.setPower(.7);
        parent.sleep(1100);
        CarouselWheel.setPower(1);
        parent.sleep(400);

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

    public void setVelocity(double velocity) { this.CarouselWheel.setVelocity(velocity);}

}
