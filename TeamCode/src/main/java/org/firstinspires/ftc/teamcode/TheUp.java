package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.view.View;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Bridge", group="Autonomous")
public class TheUp extends LinearOpMode {
    DcMotor FleftDrive, BleftDrive, FrightDrive, BrightDrive, George;
    double Value =0;
    double Value2=0;

    //com.qualcomm.robotcore.hardware.ColorSensor sensorColor;
    //ModernRoboticsI2cRangeSensor rangeSensor;
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

    void TurnRight(long time, double speed) {
        FleftDrive.setPower(speed);
        FrightDrive.setPower(speed);
        BleftDrive.setPower(speed);
        BrightDrive.setPower(speed);
        sleep(time);
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
        //sensorColor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        // rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

        //Drive:
        FleftDrive = hardwareMap.dcMotor.get("FL");
        FrightDrive = hardwareMap.dcMotor.get("FR");
        BleftDrive = hardwareMap.dcMotor.get("RL");
        BrightDrive = hardwareMap.dcMotor.get("RR");
        Tail=hardwareMap.crservo.get("SideTail");
        //George=hardwareMap.dcMotor.get("GrabberMotor");

        //float hsvValues[] = {0F, 0F, 0F};
        //final float values[] = hsvValues;
        //final double SCALE_FACTOR = 255;
        //int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        //final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);


        waitForStart();
        Tail.setDirection(CRServo.Direction.REVERSE);
        sleep(20000);
        Forward(0.3);
        sleep(2000);
        Stop();
        //while (opModeIsActive()) {
        //    RangeV=rangeSensor.rawUltrasonic();
        //    telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
        //    telemetry.addData("raw optical", rangeSensor.rawOptical());
        //}


    }
}