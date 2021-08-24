package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous
public class MoveAround extends LinearOpMode {
    DcMotorEx motorFrontLeft, motorBackLeft, motorFrontRight, motorBackRight;
    @Override
    public void runOpMode() throws InterruptedException
    {
        //HardwareMap hardwareMap = new HardwareMap(this);
        motorFrontLeft = hardwareMap.get(DcMotorEx.class, "top left");
        motorFrontRight = hardwareMap.get(DcMotorEx.class, "top right");
        motorBackLeft = hardwareMap.get(DcMotorEx.class, "bottom left");
        motorBackRight = hardwareMap.get(DcMotorEx.class, "bottom right");
    }

}
