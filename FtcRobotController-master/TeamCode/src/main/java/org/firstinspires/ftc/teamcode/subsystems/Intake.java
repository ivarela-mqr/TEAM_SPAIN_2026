package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Intake {
    public DcMotor intake;
    public DcMotorEx extension;


    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotor.class, "Intake");
        extension = hardwareMap.get(DcMotorEx.class, "Extension");


        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extension.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
    public void TeleOp(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        if(gamepad1.left_trigger_pressed){
            intake.setPower(gamepad1.left_trigger);
        }else {
            intake.setPower(0);
        }
    }
}