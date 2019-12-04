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
    DcMotor Grab1, Grab2, Lift1, Lift2;

    //Servos
    CRServo Tail,Tail2;

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
        if(gamepad1.a){
            count++;
            //Float.compare(i,1.0f) == 0
            if(count==1){
                i=0.5f;
            }
            else if(count==2) {
                i = 1.0f;
                count=0;
            }
        }
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
        Tail=hardwareMap.crservo.get("Tail");
        Tail2=hardwareMap.crservo.get("Tail2");
        Lift1=hardwareMap.dcMotor.get("Lift1");
        Lift2=hardwareMap.dcMotor.get("Lift2");

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
            Tail.setPower(0.5);
            Tail2.setPower(-0.5);
        }
        else if(gamepad1.left_bumper) {
            Tail.setPower(-0.5);
            Tail2.setPower(0.5);
        }
        else if(!gamepad1.right_bumper && !gamepad1.left_bumper){
            Tail.setPower(0.0);
            Tail2.setPower(0.0);
        }

        /*if(Gravity) {
            Lift1.setPower(stop);
            Lift2.setPower(-stop);
        }*/

        if(gamepad1.y){//Chance first code
            Lift1.setPower(0.4);
            Lift2.setPower(-0.4);
        }
        else if(gamepad1.x){
            Lift1.setPower(-0.3);
            Lift2.setPower(0.3);
        }
        else if(!gamepad1.dpad_down&& !gamepad1.dpad_up){
            Lift1.setPower(0);
            Lift2.setPower(0);
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

        FL_power = i*FL_power;
        FR_power = i*FR_power;
        RL_power = i*RL_power;
        RR_power=i*RR_power;


    }

    public void setDriveChainPower()
    {
        frontLeft.setPower(FL_power);
        frontRight.setPower(FR_power);
        rearLeft.setPower(RL_power);
        rearRight.setPower(RR_power);
    }
}
