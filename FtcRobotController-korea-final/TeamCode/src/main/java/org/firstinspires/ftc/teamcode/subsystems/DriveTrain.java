package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;



public class DriveTrain {
    private final DcMotor left, right;

    private double driveVelFactor;


    public DriveTrain(HardwareMap hardwareMap){
        //motors
        left = hardwareMap.get(DcMotor.class, "tankLeft");
        right = hardwareMap.get(DcMotor.class, "tankRight");

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void TeleOp(Gamepad gamepad1, Telemetry telemetry, boolean climbing){

        driveVelFactor = 1;
        if (gamepad1.left_stick_button)
            driveVelFactor = driveVelFactor *0.25;

        if(climbing)
            driveVelFactor = -driveVelFactor;




        double leftPower  = gamepad1.left_stick_y + (climbing ? gamepad1.right_stick_x : -gamepad1.right_stick_x);
        double rightPower = gamepad1.left_stick_y + (climbing ? -gamepad1.right_stick_x : gamepad1.right_stick_x);

        if (Math.abs(leftPower) >= 1 || Math.abs(rightPower) >= 1){
            normalize(leftPower, rightPower);
        }

        left.setPower(leftPower*driveVelFactor);
        right.setPower(rightPower*driveVelFactor);
    }

    void normalize (double a, double b){
        double x = Math.abs(Math.max(a, b));
        a = a/x;
        b = b/x;
    }

}