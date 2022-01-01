/* This program makes it easier to control the robot by letting the left stick control
forward and backward movement and letting the right stick x control the rotating of
the robot just like an Rc Car.
*/
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


@TeleOp(name = "EPIC_TeleOp_Testwheels")
public class EPIC_TeleOp_TestWheels extends LinearOpMode {
    //Configuration used: EPIC4Wheel
    Mecanum_Wheels wheels;
    double lefty = 0.0;
    double lefty2 = 0.0;
    double leftx = 0.0;
    double righty = 0.0;
    double rightx = 0.0;
//    public DcMotorEx frontright;
//    public DcMotorEx frontleft;
//    public DcMotorEx backright;
//    public DcMotorEx backleft;


//    double liftPower = 0.0;
//    double rotateArm = 0.0;
//    double powerfactor = 0.6;

    private static void logGamepad(Telemetry telemetry, Gamepad gamepad, String prefix) {
        telemetry.addData(prefix + "Synthetic",
                gamepad.getGamepadId() == Gamepad.ID_UNASSOCIATED);
        for (Field field : gamepad.getClass().getFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;

            try {
                telemetry.addData(prefix + field.getName(), field.get(gamepad));
            } catch (IllegalAccessException e) {
                // ignore for now
            }
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        //Hardware Mapping

//        frontright = hardwareMap.get(DcMotorEx.class,"Frontright");
//        frontleft = hardwareMap.get(DcMotorEx.class,"Frontleft");
//        backright = hardwareMap.get(DcMotorEx.class,"Backright");
//        backleft = hardwareMap.get(DcMotorEx.class,"Backleft");
        wheels = new Mecanum_Wheels(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        wheels.initialize();
        //wheels.rightErrorAdjustment = 0.93;//1;
        Spinner spinner = new Spinner(hardwareMap);
        wheels.telemetry = telemetry;
        wheels.parent = this;
        wheels.leftErrorAdjustment = 0.5;
        wheels.rightErrorAdjustment = 0.5;
        double wheelPower = 0.6;
        double carouselPower = 0.58;
        claw.parent = this;
        claw.telemetry = this.telemetry;
        double clawPower = lefty/10;
        boolean lifted = false;
        //double needPos = clawPower+claw.arm.getPosition();
        claw.new_frontLeftTarget = 0;

        waitForStart();

        //claw.initiateLift();
        while (opModeIsActive()) {

//            //telemetry.addData("Finger 1 position", claw.clawFinger1.getPosition());
//            //telemetry.addData("Finger 2 position", claw.clawFinger2.getPosition());
//            //telemetry.update();
//            //mecanumWheels.initialize();
//
            lefty = gamepad1.left_stick_y;
            leftx = gamepad1.left_stick_x;
            righty = gamepad1.right_stick_y;
            rightx = -gamepad1.right_stick_x;


//            liftPower = gamepad2.right_stick_y;
//            rotateArm = gamepad2.left_stick_y;

            lefty2 = -gamepad2.left_stick_y;
//            boolean dpad_left = gamepad1.dpad_left;
//            boolean dpad_right = gamepad1.dpad_right;
            boolean b = gamepad1.b;
            boolean x = gamepad1.x;
            boolean y = gamepad1.y;
            boolean a = gamepad1.a;
//
            boolean a2 = gamepad2.a;
            boolean b2 = gamepad2.b;
            boolean y2 = gamepad2.y;
            boolean x2 = gamepad2.x;
            boolean dpad_down2 = gamepad2.dpad_down;
            boolean dpad_left2 = gamepad2.dpad_left;
            boolean dpad_up2 = gamepad2.dpad_up;
            boolean dpad_right2 = gamepad2.dpad_right;
//            //if(!dpad_left && !dpad_right)
//            //else
//            if(dpad_left) {
////                wheels.Collapse();
//
//            }
//            else if(dpad_right) {
////                wheels.Expand();
//                spinner.setPower(0);
//            }
            if(y2)
            {

                //claw.clawFinger1.setPosition(claw.clawFinger1.getPosition()+0.005);
                //claw.clawFinger2.setPosition(claw.clawFinger2.getPosition()+0.005);
                claw.release();

            }
            else if(x2)
            {

                //claw.clawFinger1.setPosition(claw.clawFinger1.getPosition()-0.005);
                //claw.clawFinger2.setPosition(claw.clawFinger2.getPosition()-0.005);
                claw.grab();

            }
            else if(a2)
            {
                claw.clawBucket1.setPosition(claw.clawBucket1.getPosition()+0.01);
                claw.clawBucket2.setPosition(claw.clawBucket2.getPosition()+0.01);
                //claw.grab();
            }
            //else if(x)
                //spinner.setPower(carouselPower);
            else if(b2) {
                //spinner.setPower(-carouselPower);
                claw.clawBucket1.setPosition(claw.clawBucket1.getPosition() - 0.01);
                claw.clawBucket2.setPosition(claw.clawBucket2.getPosition() - 0.01);
            }
            else if(dpad_down2){
                claw.lift(1);
            }
            else if(dpad_left2){
                claw.lift(2);
            }
            else if(dpad_up2){
                claw.lift(3);
            }
            else if(dpad_right2){
                claw.lift(0);
            }

            else if(lefty2!=0){
               claw.lift(3,(int)(lefty2*10),740);

                //spinner.setPower(0);
            }

            if(x)
                spinner.setPower(carouselPower);
            else if(b)
                spinner.setPower(-carouselPower);
            else {
                //wheels.move(lefty,righty,-leftx,rightx);
                //wheels.move(lefty,righty,leftx,rightx);
                wheels.frontright.setPower(0.5);

                spinner.setPower(0);
            }

//            boolean leftBumper = gamepad2.left_bumper;
//            boolean rightBumper = gamepad2.right_bumper;

//            claw.rotate(leftBumper, rightBumper);
//
//            if(y2) {
//                claw.grab();
//            }
//                else{
//                    claw.release();
//            }
//
////            wheels.leftMotorY(-lefty);
////            wheels.rightMotorY(-righty);
////            wheels.rightMotorX(rightx);
////            wheels.leftMotorX(leftx);




            

            telemetry.addData("lefty", "%.2f", lefty);
            telemetry.addData("lefty2", "%.2f", lefty2);
            telemetry.addData("leftx", "%.2f", leftx);

            telemetry.addData("rightx", "%.2f", gamepad1.right_stick_x);
            telemetry.addData("righty", "%.2f", gamepad1.right_stick_y);


            telemetry.addData("Finger1 current position", claw.clawFinger1.getPosition());
            telemetry.addData("Finger2 target position", claw.clawFinger2.getPosition());


            telemetry.addData("clawBucket1 current position", claw.clawBucket1.getPosition());
            telemetry.addData("clawBucket2 target position", claw.clawBucket2.getPosition());


            telemetry.addData("armLeft current position", claw.armLeft.getCurrentPosition());

            telemetry.addData("armRight current position", claw.armRight.getCurrentPosition());
            telemetry.addData("armLeft target position", claw.armLeft.getTargetPosition());
            telemetry.addData("armRight target position", claw.armRight.getTargetPosition());
            telemetry.addData("arm target position set", claw.new_frontLeftTarget);

            telemetry.addData("b2", b2);

            telemetry.update();



            //logGamepad(telemetry, gamepad1, "gamepad1");
            //logGamepad(telemetry, gamepad2, "gamepad2");
            telemetry.update();
        }
    }
}

