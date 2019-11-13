package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Base2.0", group="tele")
public class Holonomic extends OpMode {
    //Motors
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor rearRight;
    DcMotor rearLeft;

    //Variables
    float i=1.0f;
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
    }

    public void getJoyValues()
    {
        leftStickY = gamepad1.left_stick_y;
        leftStickX = gamepad1.left_stick_x;
        rightStickX = gamepad1.right_stick_x;
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

        if(gamepad1.y){
            i=0.5f;
        }
        if(gamepad1.b){
            i=1.0f;
        }
    }

    public void setDriveChainPower()
    {
        frontLeft.setPower(FL_power);
        frontRight.setPower(FR_power);
        rearLeft.setPower(RL_power);
        rearRight.setPower(RR_power);
    }
}
