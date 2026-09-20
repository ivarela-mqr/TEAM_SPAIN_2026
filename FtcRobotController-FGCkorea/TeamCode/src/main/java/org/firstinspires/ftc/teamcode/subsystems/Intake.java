package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Debouncer;


public class Intake {
    public DcMotorEx intake;
    public boolean intaking = false;
    public Debouncer intakeDebouncer, outDebouncer;
    public Servo blockL, blockR;


    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        blockL = hardwareMap.get(Servo.class,"blockLeft");
        blockR = hardwareMap.get(Servo.class,"blockRight");

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeDebouncer = new Debouncer(200);
        outDebouncer = new Debouncer(200);
    }

    public void Start(){
        expand();
        intaking = true;
    }
    public void TeleOp(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        if(gamepad1.left_bumper && intakeDebouncer.isReady())
            intaking = !intaking;

        if(gamepad1.triangle){
            intake.setPower(-1);
        } else if (intaking) {
            intake.setPower(1);
        }else{
            intake.setPower(0);
        }



        if(gamepad1.dpad_down){
            block();
        }
        if(gamepad1.dpad_up){
            expand();
        }
    }

    public void expand(){
        blockL.setPosition(0.5);
        blockR.setPosition(0.4);
    }
    public void block(){
        blockL.setPosition(1);
        blockR.setPosition(0);
    }


}