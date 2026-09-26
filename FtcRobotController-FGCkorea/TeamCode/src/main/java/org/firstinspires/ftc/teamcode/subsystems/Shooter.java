package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Debouncer;


public class Shooter {
    public  DcMotorEx shooterL, shooterR, transfer;
    public Servo block;
    private boolean shooting = false;
    private boolean shootBack = false;
    Debouncer shootDebounce, limitDebounce, backDebounce, blockDebounce;

    double limitVel = 1150;
    double backVel = -700;


    //PIDFCoefficients coefficients = new PIDFCoefficients(22, 0, 1.7, 15);


    public Shooter (HardwareMap hardwareMap){
        shooterL = hardwareMap.get(DcMotorEx.class,"shootLeft");
        shooterR = hardwareMap.get(DcMotorEx.class,"shootRight");
        transfer = hardwareMap.get(DcMotorEx.class,"transfer");
        block =  hardwareMap.get(Servo.class,"blockShooter");

        shooterR.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);

        shooterL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //shooter0.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);
        //shooter1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);

        shootDebounce = new Debouncer(300);
        limitDebounce = new Debouncer(300);
        backDebounce = new Debouncer(300);
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
        shooting = false;
        shootBack = true;


    }
    public void TeleOp(Gamepad gamepad1, Telemetry telemetry, boolean climbing){
        if(gamepad1.right_bumper && blockDebounce.isReady()){
            //shoot modes
            if(!shooting){
                shooting = true;
                shootBack = false;
            }else{
                shooting = false;
            }
        }
        if(shooting)
            unblock();
        if(gamepad1.square && backDebounce.isReady()){
            if(!shootBack){
                shootBack = true;
                shooting = false;
            }else{
                shootBack = false;
            }
        }


        if(!climbing){
            if (shootBack){
                setVelShooter(backVel);
            }else if (shooting){
                setPowerShooter(1);
            }else{
                setPowerShooter(0);
            }

            if(gamepad1.right_trigger_pressed && (isReady() || shootBack)){
                transfer.setPower(1);
            }else {
                transfer.setPower(0);
            }
        }else{
            setPowerShooter(0);
            transfer.setPower(0);
        }



        telemetry.addData("currVel", shooterL.getVelocity());
        telemetry.addData("limitVel", limitVel);
        if(shooting)
            telemetry.addData("shooting", true);
        if(shootBack)
            telemetry.addData("backshooting", true);
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


    public void TeleOpPrueba(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        if(gamepad1.circle && shootDebounce.isReady()){
            shooting = !shooting;
        }

        if(shooting){
            setVelShooter(limitVel);
        }else{
            setVelShooter(0);
        }

        if(gamepad1.right_trigger_pressed){
            transfer.setPower(1);
        }else {
            transfer.setPower(0);
        }


        if (gamepad1.dpad_up && limitDebounce.isReady()){
            limitVel+=50;
        }
        if (gamepad1.dpad_down && limitDebounce.isReady()){
            limitVel-=50;
        }

        telemetry.addData("currVel", shooterL.getVelocity());
        telemetry.addData("limitVel", limitVel);
        telemetry.addData("shootBack", shootBack);
        telemetry.addData("shooting", shooting);
    }

    public boolean isReady(){
        return (Math.min(shooterL.getVelocity(), shooterR.getVelocity()) > limitVel);
    }
}
