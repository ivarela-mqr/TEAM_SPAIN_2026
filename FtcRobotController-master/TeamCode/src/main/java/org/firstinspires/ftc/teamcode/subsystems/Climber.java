package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Climber {
    public DcMotor climber;
    public boolean hasStarted = false;


    public Climber(HardwareMap hardwareMap) {
        climber = hardwareMap.get(DcMotor.class, "climber");

        climber.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void ManualTeleOp(Gamepad gamepad1, Telemetry telemetry){
        if(gamepad1.cross){
            climber.setPower(1);
        }else {
            climber.setPower(0.2);
        }
    }

    public void TeleOp(Gamepad gamepad1, Telemetry telemetry){
        if(gamepad1.cross){
            climber.setPower(1);
            hasStarted = true;
        }else if (hasStarted){
            climber.setPower(0.1);
        } else{
            climber.setPower(0);
        }
    }

}