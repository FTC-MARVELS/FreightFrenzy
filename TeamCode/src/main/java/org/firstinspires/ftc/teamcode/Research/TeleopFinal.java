package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeleopFinal")
@Disabled
public class TeleopFinal extends LinearOpMode {
    private DcMotor Frontright;
    private DcMotor Frontleft;
    private DcMotor Backright;
    private DcMotor Backleft;
    private DcMotor MiddleLeft;
    private DcMotor MiddleRight;

    public void runOpMode(){
        Frontright=hardwareMap.get(DcMotor.class,"Frontright");
        Frontleft=hardwareMap.get(DcMotor.class,"Frontleft");
        Backright=hardwareMap.get(DcMotor.class,"Backright");
        Backleft=hardwareMap.get(DcMotor.class,"Backleft");
        MiddleRight=hardwareMap.get(DcMotor.class,"Middleright");
        MiddleLeft=hardwareMap.get(DcMotor.class, "Middleleft");
        waitForStart();
        while(opModeIsActive()) {
            //The right motors are flipped so we have to reverse them
            Frontright.setDirection(DcMotorSimple.Direction.REVERSE);
            Backright.setDirection(DcMotorSimple.Direction.REVERSE);
            MiddleRight.setDirection(DcMotorSimple.Direction.REVERSE);


            double lefty = -gamepad1.left_stick_y;
            double leftx = gamepad1.left_stick_x;
            double righty = gamepad1.right_stick_y;
            double rightx = gamepad1.right_stick_x;

            Frontright.setPower(lefty - rightx - leftx);
            Frontleft.setPower(lefty + rightx + leftx);
            Backright.setPower(lefty - rightx + leftx);
            Backleft.setPower(lefty + rightx - leftx);
            MiddleRight.setPower(lefty - rightx);
            MiddleLeft.setPower(lefty + rightx);


            extend_collapse();
        }
    }

    public void  extend_collapse() {
        //COLLASPE AND EXPAND
        boolean dpadright = gamepad1.dpad_right;
        boolean dpadleft = gamepad1.dpad_left;

        if (dpadleft == true) {
            Frontleft.setPower(1);
            Backleft.setPower(-1);
            //Frontright.setPower(-1);
            //Backright.setPower(-1);



        }

        if (dpadright==true) {
            Frontleft.setPower(-1);
            Backleft.setPower(1);
        }

    }

}
