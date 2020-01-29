package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.view.View;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@Autonomous(name="SkyStoneAutonomus", group="Autonomous")
public class Chanceauto extends LinearOpMode {
    DcMotor FleftDrive, BleftDrive, FrightDrive, BrightDrive, Slide, Lift, Grab1, Grab2;
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

    void BackTime(double speed, long time) {
        FleftDrive.setPower(speed);
        FrightDrive.setPower(-speed);
        BleftDrive.setPower(speed);
        sleep(time);
        Stop();

    }

    void LSlide(double speed, long time) {
        Lift.setPower(speed);
        sleep(time);
        Stop();
    }

    void RSlide(double speed, long time) {
        Lift.setPower(-speed);
        sleep(time);
        Stop();
    }

    void LowerArm(double speed, long time) {
        Lift.setPower(speed);
        sleep(time);
        Stop();
    }

    void Grab(double speed, long time) {
        FleftDrive.setPower(-speed);
        FrightDrive.setPower(speed);
        BleftDrive.setPower(-speed);
        BrightDrive.setPower(speed);
        Grab1.setPower(speed);
        Grab2.setPower(-speed);
        sleep(time);
        Stop();
    }

    void Ungrab(double speed, long time) {
        Grab1.setPower(-speed);
        Grab2.setPower(speed);
        sleep(time);
        Stop();
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


        long time2=1000;
        ForwardTime(time2,0.5);

        LowerArm(.6,100);

        LSlide(.6,100);

        ForwardTime(500,.5);

        Grab(1,500);

        BackTime(.5,1250);

        TurnLeft(.5);
        sleep(500);

        ForwardTime(1000,1);

        RSlide(1,50);

        ForwardTime(500,.5);

        Ungrab(1,100);

        BackTime(.5,500);

        LSlide(1,50);

        BackTime(.5,1000);

    }
}
