package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import java.lang.Float;

@TeleOp(name="Base2.0", group="tele")
public class Holonomic extends OpMode {
    //Motors
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor rearRight;
    DcMotor rearLeft;
    DcMotor Grab1, Grab2, Lift, Slide;

    //Servos
    CRServo Block1,Block2;

    //Variables
    double stop=0.25;
    int count =0;
    float i=1.0f;
    boolean Gravity=true;
    float leftStickY;
    float leftStickX;
    float rightStickX;
    float FL_power_raw;
    float FR_power_raw;
    float RL_power_raw;
    float RR_power_raw;
    float FL_power;
    float FR_power;
    float RL_power;
    float RR_power;
    @Override
    public void init() {
        runInitSequence();
    }

    @Override
    public void loop() {
        controls();

    }
    public void runInitSequence()
    {
        //Define HW objects
        frontLeft = hardwareMap.dcMotor.get("FL");
        frontRight = hardwareMap.dcMotor.get("FR");
        rearLeft = hardwareMap.dcMotor.get("RL");
        rearRight = hardwareMap.dcMotor.get("RR");
        Grab1=hardwareMap.dcMotor.get("Grab1");
        Grab2=hardwareMap.dcMotor.get("Grab2");
        Block1=hardwareMap.crservo.get("Block");
        Block2=hardwareMap.crservo.get("Block2");
        Lift=hardwareMap.dcMotor.get("Lift");
        Slide=hardwareMap.dcMotor.get("Slide");


        frontRight.setDirection(DcMotor.Direction.REVERSE);
        rearRight.setDirection(DcMotor.Direction.REVERSE);

        //Run without internal PID
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void controls()
    {
        holonomicFormula();
        setDriveChainPower();

        if(gamepad1.right_trigger > 0) {
            Grab1.setPower(gamepad1.right_trigger);
            Grab2.setPower(-gamepad1.right_trigger);
        }
        else if(gamepad1.left_trigger > 0) {
            Grab1.setPower(-gamepad1.left_trigger);
            Grab2.setPower(gamepad1.left_trigger);
        }
        else if(gamepad1.right_trigger <= 0 && gamepad1.left_trigger <= 0) {
            Grab1.setPower(0);
            Grab2.setPower(0);
        }


        if(gamepad1.right_bumper) {
            Block1.setPower(0.5);
            Block2.setPower(-0.5);
        }
        else if(gamepad1.left_bumper) {
            Block1.setPower(-0.5);
            Block2.setPower(0.5);
        }
        else if(!gamepad1.right_bumper && !gamepad1.left_bumper){
         //   boolean control=true;
            Block1.setPower(0.02);
            Block2.setPower(-0.02);
        }

        /*if(Gravity) {
            Lift1.setPower(stop);
            Lift2.setPower(-stop);
        }*/


        if(gamepad1.y){//Chance first code
            Slide.setPower(-0.6);
        }
        else if(gamepad1.x){
            Slide.setPower(0.6);
        }
        else if(!gamepad1.x && !gamepad1.y){
            Slide.setPower(0);
        }


        if(gamepad1.a){//Chance first code
            Lift.setPower(0.8);
        }
        else if(gamepad1.b){
            Lift.setPower(-0.8);
        }
        else if(!gamepad1.a && !gamepad1.b){
            Lift.setPower(0);
        }

    }

    public void getJoyValues()
    {
        leftStickY = gamepad1.left_stick_y;
        leftStickX = gamepad1.left_stick_x;
        rightStickX = 0.5f*gamepad1.right_stick_x;
    }

    public void holonomicFormula()
    {
        getJoyValues();

        FL_power_raw = leftStickY - leftStickX - rightStickX;
        FR_power_raw = leftStickY + leftStickX + rightStickX;
        RL_power_raw = leftStickY + leftStickX - rightStickX;
        RR_power_raw = leftStickY - leftStickX + rightStickX;

        FL_power = Range.clip(FL_power_raw, -1, 1);
        FR_power = Range.clip(FR_power_raw, -1, 1);
        RL_power = Range.clip(RL_power_raw,-1 ,1);
        RR_power = Range.clip(RR_power_raw, -1, 1);

        FL_power = FL_power;
        FR_power = FR_power;
        RL_power = RL_power;
        RR_power=RR_power;


    }

    public void setDriveChainPower()
    {
        frontLeft.setPower(FL_power);
        frontRight.setPower(FR_power);
        rearLeft.setPower(RL_power);
        rearRight.setPower(RR_power);
    }
}
