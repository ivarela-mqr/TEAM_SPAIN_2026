package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Debouncer;


public class Intake {
    public DcMotorEx extension,intake;
    public boolean intakeando = false;
    public Debouncer intakeDebouncer, outDebouncer;
    public Servo bloql,bloqr;


    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "Intake");
        extension = hardwareMap.get(DcMotorEx.class, "Extension");
        bloql = hardwareMap.get(Servo.class,"Bloql");
        bloqr = hardwareMap.get(Servo.class,"Bloqr");

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extension.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intakeDebouncer = new Debouncer(200);
        outDebouncer = new Debouncer(200);
        bloql.setPosition(1);
        bloqr.setPosition(0);
    }
    public void TeleOp(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        if(gamepad1.left_bumper && intakeDebouncer.isReady())
            intakeando = !intakeando;

        if(gamepad1.triangle){
            intake.setPower(-1);
        } else if (intakeando) {
            intake.setPower(1);
        }else{
            intake.setPower(0);
        }

        if(gamepad1.dpad_down){
            bloql.setPosition(1);
            bloqr.setPosition(0);
        }
        if(gamepad1.dpad_up){
            bloql.setPosition(0.5);
            bloqr.setPosition(0.4);
        }
    }
}