package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@Disabled
@TeleOp(name = "TankModesV2")

public class TankModesV2 extends LinearOpMode {
    private DcMotor Frontright;
    private DcMotor Frontleft;
    private DcMotor Backright;
    private DcMotor Backleft;
    private DcMotor MiddleLeft;
    private DcMotor MiddleRight;

    public void runOpMode(){
        waitForStart();
        Frontright=hardwareMap.get(DcMotor.class,"Frontright");
        Frontleft=hardwareMap.get(DcMotor.class,"Frontleft");
        Backright=hardwareMap.get(DcMotor.class,"Backright");
        Backleft=hardwareMap.get(DcMotor.class,"Backleft");
        MiddleRight=hardwareMap.get(DcMotor.class,"Middleright");
        MiddleLeft=hardwareMap.get(DcMotor.class, "Middleleft");


        //The right motors are flipped so we have to reverse them
        Frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        MiddleRight.setDirection(DcMotorSimple.Direction.REVERSE);
        Backright.setDirection(DcMotorSimple.Direction.REVERSE);

        double lefty = -gamepad1.left_stick_y;
        double rightx = gamepad1.right_stick_x;
        boolean b = gamepad1.b;
        boolean x = gamepad1.x;

        if (lefty > 0) {
            Frontright.setPower(lefty);
            MiddleRight.setPower(lefty);
            Backright.setPower(lefty);
            Frontleft.setPower(lefty);
            MiddleLeft.setPower(lefty);
            Backleft.setPower(lefty);
        }

        else if (lefty < 0) {
            Frontright.setPower(-lefty);
            MiddleRight.setPower(-lefty);
            Backright.setPower(-lefty);
            Frontleft.setPower(-lefty);
            MiddleLeft.setPower(-lefty);
            Backleft.setPower(-lefty);
        }

        if (rightx > 0) {
            Frontright.setPower(-lefty);
            Backright.setPower(lefty);
            Frontleft.setPower(lefty);
            Backleft.setPower(-lefty);
        }

        else if (rightx < 0) {
            Frontright.setPower(lefty);
            Backright.setPower(-lefty);
            Frontleft.setPower(-lefty);
            Backleft.setPower(lefty);
        }
        if(x == true) {
            Frontright.setPower(0.5);
            MiddleRight.setPower(0.5);
            Backright.setPower(0.5);
            Frontleft.setPower(-0.5);
            MiddleLeft.setPower(-0.5);
            Backleft.setPower(-0.5);
        }

        else if (b == true) {
            Frontright.setPower(-0.5);
            MiddleRight.setPower(-0.5);
            Backright.setPower(-0.5);
            Frontleft.setPower(0.5);
            MiddleLeft.setPower(0.5);
            Backleft.setPower(0.5);
        }




        extend_collapse();

    }

    public void  extend_collapse() {

        boolean dpadright = gamepad1.dpad_right;
        boolean dpadleft = gamepad1.dpad_left;

        if (dpadright == true) {
            Frontleft.setPower(1);
            Backleft.setPower(-1);



        }

        if (dpadleft==true) {
            Frontright.setPower(-1);
            Backright.setPower(1);
        }

    }



}
