package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Climber {
    public DcMotor climber;
    public Servo elevator;


    public Climber(HardwareMap hardwareMap) {
        climber = hardwareMap.get(DcMotor.class, "Intake");
        elevator = hardwareMap.get(Servo.class, "Elevator");

        climber.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void ManualTeleOp(Gamepad gamepad1, Telemetry telemetry){
        if(gamepad1.triangle){
            climber.setPower(1);
        }else {
            climber.setPower(0);
        }

        if(gamepad1.dpad_up){
            extendClimber(0.05);
        } else if (gamepad1.dpad_down) {
            extendClimber(-0.05);
        }
    }

    private void extendClimber(double x){
        double newPos = elevator.getPosition() + x;
        elevator.setPosition(x);
    }
}