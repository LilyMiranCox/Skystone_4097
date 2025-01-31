package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@Autonomous(name="SkyStoneAutonomus", group="Autonomous")
public class LeftAutnomus extends LinearOpMode {
    DcMotor FleftDrive, BleftDrive, FrightDrive, BrightDrive, George;
    double Value =0;
    double Value2=0;

    ColorSensor sensorColor, BottomSensor;
    ModernRoboticsI2cRangeSensor rangeSensor;
    DistanceSensor sensorDistance;
    int RangeV;
    CRServo Tail,Tail2;
    //Functions:

    void mySleep(long interval, String msg) {
        ElapsedTime myTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        while(myTimer.milliseconds() < interval && opModeIsActive()) {
            telemetry.addData("Elapsed Time (msec)", "%.00f", myTimer.milliseconds());
            telemetry.addData("mySleep", msg);
            telemetry.update();
            this.sleep(250);
        }
    }

    void Forward(double speed) {
        FleftDrive.setPower(-speed);
        FrightDrive.setPower(speed);
        BleftDrive.setPower(-speed);
        BrightDrive.setPower(speed);
    }


    void Stop() {
        FleftDrive.setPower(0);
        FrightDrive.setPower(0);
        BleftDrive.setPower(0);
        BrightDrive.setPower(0);
    }


    void StrafeLeft(double speed) {
        FleftDrive.setPower(speed);
        FrightDrive.setPower(speed);
        BleftDrive.setPower(-speed);
        BrightDrive.setPower(-speed);
        //sleep(time);
    }

    void StrafeRight(double speed) {
        FleftDrive.setPower(-speed);
        FrightDrive.setPower(-speed);
        BleftDrive.setPower(speed);
        BrightDrive.setPower(speed);

    }

    void TurnLeft(double speed) {
        FleftDrive.setPower(-speed);
        FrightDrive.setPower(-speed);
        BleftDrive.setPower(-speed);
        BrightDrive.setPower(-speed);
        //sleep(time);
    }

    void TurnRight(double speed) {
        FleftDrive.setPower(speed);
        FrightDrive.setPower(speed);
        BleftDrive.setPower(speed);
        BrightDrive.setPower(speed);
    }


    void ForwardTime(long time, double speed) {
        FleftDrive.setPower(-speed);
        FrightDrive.setPower(speed);
        BleftDrive.setPower(-speed);
        BrightDrive.setPower(speed);
        sleep(time);
        Stop();
    }

    void BackTime(double speed) {
        FleftDrive.setPower(speed);
        FrightDrive.setPower(-speed);
        BleftDrive.setPower(speed);
        BrightDrive.setPower(-speed);
    }
    void LowerSlide(double speed, long time) {
        George.setPower(speed);
        //sleep(time);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //Sensors:
        BottomSensor=hardwareMap.get(ColorSensor.class, "BSensor");
        sensorColor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "ColorSensor");
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

        //Drive:
        FleftDrive = hardwareMap.dcMotor.get("FL");
        FrightDrive = hardwareMap.dcMotor.get("FR");
        BleftDrive = hardwareMap.dcMotor.get("RL");
        BrightDrive = hardwareMap.dcMotor.get("RR");
        Tail=hardwareMap.crservo.get("SideTail");
        //George=hardwareMap.dcMotor.get("GrabberMotor");

        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);


        waitForStart();
        Tail.setDirection(CRServo.Direction.REVERSE);


        while (opModeIsActive()) {
            RangeV=rangeSensor.rawUltrasonic();
            telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
            telemetry.addData("raw optical", rangeSensor.rawOptical());

            telemetry.addData("Distance (cm)",
                    String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
            //telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
            //telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
            //telemetry.update();
       // while(Value >= 2) {

            //Forward(0.3);
            //sleep(500);
            //Stop();
            //Color Sensor Part:
            /*if (sensorColor.blue() > sensorColor.red()){
                stop();
            }*/

            Value=(sensorColor.red()*sensorColor.green())/Math.pow(sensorColor.blue(), 2);
            if(Value<2 && RangeV<13){
                telemetry.addData("Found:", Value);
                telemetry.update();
            }
            else{
                telemetry.addData("Not:", Value);
                telemetry.update();
            }

            StrafeLeft(0.5);
            if(RangeV<40) {
                BackTime(0.25);
                if(RangeV<10){
                    StrafeRight(0.4);
                }
                if(Value<2){
                    break;
                }
            }

        }
        while(opModeIsActive()){
            Value2=(sensorColor.red()*sensorColor.green())/Math.pow(sensorColor.blue(), 2);
            Forward(0.25);
            if(Value2>2){
                Stop();
                Tail.setPower(-0.9);
                sleep(4000);
                //Tail.setPower(0);
                break;
            }
        }
        /*
        StrafeRight(0.5);
        sleep(50);
        Stop();
        TurnRight(0.5);
        sleep(500);
        Stop();
        while (opModeIsActive()){
            StrafeRight(1);
            if(BottomSensor.blue()>BottomSensor.red()){
                sleep(500);
                Stop();
            }
        }*/

    }
}
