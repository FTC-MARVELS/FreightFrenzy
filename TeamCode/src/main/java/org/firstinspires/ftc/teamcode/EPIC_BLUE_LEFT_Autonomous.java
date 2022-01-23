package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;
import org.firstinspires.ftc.teamcode.tfrec.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;

import java.util.List;

@Autonomous(name="EPIC_Blue_LEFT_Autonomous", group="Robot19587")
public class EPIC_BLUE_LEFT_Autonomous extends LinearOpMode {
    //Configuration used: 6wheelConfig
    private Detector tfDetector = null;

    private static String MODEL_FILE_NAME = "EPIC_blue_left_model.tflite";
    private static String LABEL_FILE_NAME = "EPIC_blue_left_labels.txt";
    private static Classifier.Model MODEl_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        double distance = 0;
        double correctionFactor = 1;
        boolean tfdActivated = false;
        try {
            try {
                tfDetector = new Detector(MODEl_TYPE, MODEL_FILE_NAME, LABEL_FILE_NAME, hardwareMap.appContext, telemetry);
                tfDetector.parent = this;
                tfDetector.activate();
                tfdActivated = true;

            } catch (Exception ex) {
                telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
                sleep(3000);
                return;
            }
            double speed = 0.6;
            Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
            mecanum.leftErrorAdjustment = 0.991;
            Spinner spinner = new Spinner(hardwareMap);
            double spinnerPower = 0.58;
            double levelDistance = 11;
            double backDistance = 5;
            mecanum.parent = this;
            Claw claw = new Claw(hardwareMap);
            claw.parent = this;
            claw.telemetry = this.telemetry;
            //sleep(5000);
            mecanum.IsAutonomous = true;
            mecanum.velocity = 400;
            mecanum.telemetry = this.telemetry;
            mecanum.initialize();
            String id = "";
            runtime.reset();
            telemetry.addData("Opmode Active", "No");
            telemetry.update();
            waitForStart();
            // run until the end of the match (driver presses STOP)
            int i =0;
            while (opModeIsActive() && tfdActivated) {
                List<Classifier.Recognition> results = tfDetector.getLastResults();
                if (results == null || results.size() == 0) {
                    telemetry.addData("Info", "No results");
                } else {
                    for (Classifier.Recognition r : results) {
                        String item = String.format("%s: %.2f", r.getTitle(), r.getConfidence());
                        //confidence more than 60
                        if (r.getConfidence() > 0.6) {
                            id = r.getId();
                            telemetry.addData("id", "[" + id + "]");
                            telemetry.addData("Found", item);
                            telemetry.update();
                            //sleep(20000);
                            break;
                        }
                    }
                }
                i++;
                //detection takes time and is only available after the opMode is active. So if detection is not done withing 100 iterations then use
                //default level. If detection is completed then brake
                if(i==100 || !id.equals(""))
                {
                    break;
                }
                else
                    sleep(100);
                telemetry.addData("id", "[" + id + "]");

                telemetry.addData("Opmode Active", "Yes");
                telemetry.update();
            }

            int level = 3;
            distance = 27;
            //id is equivalent to the labels
            if(id.contains("0 blue_left_right")) {
                level = 1;
                levelDistance = 10;
                backDistance = 5.5;
            }
            else if(id.contains("1 blue_left_middle")) {
                level = 2;
                levelDistance = 12;
                backDistance = 7.5;
            }
            else if(id.contains("2 blue_left_left")) {
                level = 3;
                levelDistance = 14;
                backDistance = 8.25;
            }
            telemetry.addData("level", level);
            telemetry.update();
            sleep(500);
            correctionFactor = 1.444;//.4;
            distance = 15;
            distance = distance * correctionFactor;
            claw.lift(3);
            //Right
            mecanum.encoderDrive(0.6,distance,-distance,-distance,distance,2);
            distance = levelDistance * correctionFactor;
            distance = 17;
            //Forward
            mecanum.encoderDrive(0.6,distance,distance,distance,distance,2);
            claw.release();
            sleep(700);
            //Backward
            distance = 18;
            mecanum.encoderDrive(0.6,-distance,-distance,-distance,-distance,1);
            claw.lift(0);
            //Left turn
            distance = 17.3;
            mecanum.encoderDrive(0.6,-distance,-distance,distance,distance,2);
            //Left
            distance = 3;
            mecanum.encoderDrive(0.6,-distance,distance,distance,-distance,1);
            //Forward
            distance = 57;
            mecanum.encoderDrive(0.6,distance,distance,distance,distance,4);
           //Grab
            claw.grab();
//            //Backwards
//            distance = 65;
//            mecanum.encoderDrive(0.6,-distance,-distance,-distance,-distance,3);
//            //Turn Right
//            distance = 17.3;
//            mecanum.encoderDrive(0.6,distance,distance,-distance,-distance,2);
//            //Forward
//            distance = 17;
//            mecanum.encoderDrive(0.6,distance,distance,distance,distance,2);
//            //Release
//            claw.release();
//            //Backwards
//            distance = 17;
//            mecanum.encoderDrive(0.6,-distance,-distance,-distance,-distance,2);
//            //Turn Left
//            distance = 17.3;
//            mecanum.encoderDrive(0.6,-distance,-distance,distance,distance,2);
//            //Forward
//            distance = 55;
//            mecanum.encoderDrive(0.6,distance,distance,distance,distance,2);

        }
        catch (Exception ex){
            telemetry.addData("Init Error", ex.getMessage());
            telemetry.update();
        }
        finally {
            if (tfDetector != null){
                tfDetector.stopProcessing();
            }
        }

    }

}