package org.firstinspires.ftc.teamcode.RobotObjects.EPIC;

import android.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    //Configuration used: 6wheelConfig
    public Servo clawFinger1;
    public Servo clawFinger2;
    public Servo arm;
    public DcMotorEx liftMotor;

    public double armInit = 0.0;
    public double finger1Init = 0.4;
    public double finger2Init = 0.4;
    public double armMin = 0.0;
    public double armMax = 0.5;
    public double finger1Min = 0.2;
    public double finger2Min = 0.2;
    public double finger1Max = 0.4;
    public double finger2Max = 0.4;
    public double liftPower = -0.6;
    public LinearOpMode parent;
    public Telemetry telemetry;

    public Claw(HardwareMap hardwareMap) {
        clawFinger1 = hardwareMap.get(Servo.class,"finger1");
        clawFinger2 = hardwareMap.get(Servo.class,"finger2");
        arm = hardwareMap.get(Servo.class,"arm");
        liftMotor = hardwareMap.get(DcMotorEx.class,"Lift");

    }

    public void initiateLift(){
        //int currentPosition = liftMotor.getCurrentPosition();
        //int targetPosition = 6
        telemetry.addData("Postion lift 2:%d", liftMotor.getCurrentPosition());
        telemetry.update();
        liftMotor.setPower(liftPower);
        parent.sleep(6000);
        liftMotor.setPower(0);
        telemetry.addData("Postion lift 2:%d", liftMotor.getCurrentPosition());
        telemetry.update();
        //arm.setPosition(0.5);
        liftMotor.setPower(-liftPower);
        parent.sleep(3500);
        liftMotor.setPower(0);
        telemetry.addData("Postion lift 2:%d", liftMotor.getCurrentPosition());
        telemetry.update();
    }

    public void initiateClaw() {

    }

    public void lift(double power)
    {

        liftMotor.setPower(power);
    }

    public void rotate(double power)
    {
        arm.setPosition(power);
    }

    public void rest()
    {
        liftMotor.setPower(-liftPower);
    }

    public void grab()
    {
        telemetry.addData("Postion Claw 1:%d", clawFinger1.getPosition());
        telemetry.addData("Postion Claw 2:%d", clawFinger2.getPosition());
        telemetry.update();
        //double fingerPosition = Range.clip()
        clawFinger1.setPosition(finger1Min);
        clawFinger2.setPosition(finger2Min);
        //while(parent.opModeIsActive()){
        telemetry.addData("Postion Claw 1:%d", clawFinger1.getPosition());
        telemetry.addData("Postion Claw 2:%d", clawFinger2.getPosition());
        telemetry.update();
        //}
    }

    public void release() {
        clawFinger1.setPosition(finger1Max);
        clawFinger2.setPosition(finger2Max);
        telemetry.addData("Postion Claw 1:%d", clawFinger1.getPosition());
        telemetry.addData("Postion Claw 2:%d", clawFinger2.getPosition());
        telemetry.update();
    }
}
