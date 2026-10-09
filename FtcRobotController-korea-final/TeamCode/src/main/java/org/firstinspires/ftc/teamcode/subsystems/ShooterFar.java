package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Debouncer;

import java.util.concurrent.TimeUnit;


public class ShooterFar {
    public  DcMotorEx shooterL, shooterR, transfer;
    public Servo block;
    private boolean shooting = false;
    private boolean shootBack = false;
    private boolean shootFar = false;
    Debouncer shootDebounce, limitDebounce, backDebounce, blockDebounce, farDebounce;

    double limitVel = 1150;
    double backVel = -800;
    double farVel = 1500;

    private ElapsedTime timer;


    //PIDFCoefficients coefficients = new PIDFCoefficients(22, 0, 1.7, 15);


    public ShooterFar(HardwareMap hardwareMap){
        shooterL = hardwareMap.get(DcMotorEx.class,"shootLeft");
        shooterR = hardwareMap.get(DcMotorEx.class,"shootRight");
        transfer = hardwareMap.get(DcMotorEx.class,"transfer");
        block =  hardwareMap.get(Servo.class,"blockShooter");

        timer = new ElapsedTime();

        shooterR.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);

        shooterL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //shooter0.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);
        //shooter1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);

        shootDebounce = new Debouncer(300);
        limitDebounce = new Debouncer(300);
        backDebounce = new Debouncer(300);
        farDebounce = new Debouncer(300);
        blockDebounce = new Debouncer(1000);
    }


    public void stop() {
        setPowerShooter(0);
        transfer.setPower(0);
    }

    private void setPowerShooter(double power){
        shooterL.setPower(power);
        shooterR.setPower(power);
    }
    private void setVelShooter(double vel){
        shooterL.setVelocity(vel);
        shooterR.setVelocity(vel);
    }


    public void Start(){
        shooting = true;
        shootBack = false;
        timer.reset();

    }
    public void TeleOp(Gamepad gamepad1, Telemetry telemetry, boolean climbing){
        if(gamepad1.right_bumper && blockDebounce.isReady()){
            //shoot modes
            if(!shooting){
                shooting = true;
                shootBack = false;
                shootFar = false;
                limitVel = 1150;
            }else{
                shooting = false;
            }
        }

        if(gamepad1.square && farDebounce.isReady()){
            if(!shootFar){
                shootFar = true;
                shootBack = false;
                shooting = false;
                limitVel = farVel - 200;
            }else{
                shootFar = false;
            }
        }

        if(gamepad1.circle && backDebounce.isReady()){
            if(!shootBack){
                shootBack = true;
                shooting = false;
                shootFar = false;
            }else{
                shootBack = false;
            }
        }

        if(shooting)
            unblock();






        if(gamepad1.dpad_up){
            farVel += 50;
        }else if(gamepad1.dpad_down){
            farVel -= 50;
        }


        if (gamepad1.right_bumper && blockDebounce.isReady()){
            if (block.getPosition() < 0.5){
                block.setPosition(1);
            }else {
                block.setPosition(0);
            }
        }

        if(climbing){
            if (timer.time(TimeUnit.SECONDS) > 145){
                shooterL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                shooterR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            setPowerShooter(0);
            transfer.setPower(0);

        }else{
            if (shootFar){
                setVelShooter(farVel);
            }else if (shooting){
                setPowerShooter(1);
            }else{
                setPowerShooter(0);
            }

            if((gamepad1.right_trigger_pressed && (isReady() || shootBack)) || gamepad1.left_trigger_pressed){
                telemetry.addLine("transfer on");
                transfer.setPower(1);
            }else {
                transfer.setPower(0);
            }
        }


        telemetry.addData("currVel", shooterL.getVelocity());
        telemetry.addData("limitVel", limitVel);
        if(shooting)
            telemetry.addData("shooting", true);
        if(shootFar)
            telemetry.addData("farshooting", true);
        telemetry.addData("Pos block",block.getPosition());
    }

    boolean isShooting(){
        return(shooting || shootBack);
    }
    private void unblock(){
        block.setPosition(0); //open
    }
    public boolean isUnblocked(){
        return shooting;
    }


    public boolean isReady(){
        return (shooterL.getVelocity() > limitVel) || (shooterR.getVelocity() > limitVel);
    }

    public boolean isReadyFar(){

        return (shooterL.getVelocity() > limitVel) || (shooterR.getVelocity() > limitVel);
    }
    public boolean isTransferOn(){
        return transfer.getPower()>0.5;
    }
}
