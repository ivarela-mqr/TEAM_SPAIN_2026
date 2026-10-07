package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Debouncer;


public class Climber {
    public DcMotor climber;
    public boolean hasStarted = false;
    private Debouncer climbDebounce;


    public Climber(HardwareMap hardwareMap) {
        climber = hardwareMap.get(DcMotor.class, "climber");

        climbDebounce = new Debouncer(300);

        climber.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        climber.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    public void ManualTeleOp(Gamepad gamepad1, Telemetry telemetry){
        if(gamepad1.cross){
            climber.setPower(1);
        }else {
            climber.setPower(0.2);
        }
    }

    public void TeleOp(Gamepad gamepad1, Telemetry telemetry){
        if (hasStarted) {
            if (gamepad1.cross) {
                climber.setPower(1);
                hasStarted = true;
            } else {
                climber.setPower(0.1);
            }
        }else{
            climber.setPower(0);
        }

        if (gamepad1.options && climbDebounce.isReady()){
            hasStarted = !hasStarted;
        }

    }

    public boolean climbing(){
        return (hasStarted);
    }

}