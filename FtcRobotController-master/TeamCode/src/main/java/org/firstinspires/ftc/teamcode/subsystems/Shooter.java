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
    public  DcMotorEx shooter0, shooter1, transfer;
    public Servo block;
    private boolean shooting = false;
    private boolean shootBack = false;
    Debouncer shootDebounce, limitDebounce, backDebounce, blockDebounce;

    double limitVel = 1200;
    double maxVel = 2100;
    double backVel = -700;

    double targetVel = maxVel;

    //PIDFCoefficients coefficients = new PIDFCoefficients(22, 0, 1.7, 15);


    public Shooter (HardwareMap hardwareMap){
        shooter0 = hardwareMap.get(DcMotorEx.class,"Shoot_left");
        shooter1 = hardwareMap.get(DcMotorEx.class,"Shoot_right");
        transfer = hardwareMap.get(DcMotorEx.class,"Transfer");
        block =  hardwareMap.get(Servo.class,"BlockShooter");

        shooter1.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);

        shooter0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //shooter0.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);
        //shooter1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);

        shootDebounce = new Debouncer(300);
        limitDebounce = new Debouncer(300);
        backDebounce = new Debouncer(300);
        blockDebounce = new Debouncer(300);
    }


    public void stop() {
        setPowerShooter(0);
        transfer.setPower(0);
    }

    private void setPowerShooter(double power){
        shooter0.setPower(power);
        shooter1.setPower(power);
    }
    private void setVelShooter(double vel){
        shooter0.setVelocity(vel);
        shooter1.setVelocity(vel);
    }


    public void TeleOp(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry){
        if(gamepad1.circle && shootDebounce.isReady()){
            if(!shooting){
                shooting = true;
                shootBack = false;
            }else{
                shooting = false;
            }
        }
        if(gamepad1.square && backDebounce.isReady()){
            if(!shootBack){
                shootBack = true;
                shooting = false;
            }else{
                shootBack = false;
            }
        }

        if(gamepad1.right_bumper && blockDebounce.isReady()){
            if(block.getPosition()<0.5){
                block.setPosition(1);
            }else {
                block.setPosition(0);
            }
        }

        if (shootBack){
            setVelShooter(backVel);
        }else if (shooting){
            setPowerShooter(1);
        }else{
            setPowerShooter(0);

        }

        if((gamepad1.right_trigger_pressed && isReady()) || shootBack){
            transfer.setPower(1);
        }else {
            transfer.setPower(0);
        }


        telemetry.addData("currVel", shooter0.getVelocity());
        telemetry.addData("limitVel", limitVel);
        if(shooting)
            telemetry.addData("shooting",shooting);
        if(shootBack)
            telemetry.addData("backshooting",shootBack);
    }

    boolean isShooting(){
        return(shooting || shootBack);
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

        telemetry.addData("currVel", shooter0.getVelocity());
        telemetry.addData("limitVel", limitVel);
        telemetry.addData("shootBack", shootBack);
        telemetry.addData("shooting", shooting);
    }

    public boolean isReady(){
        return (Math.min(shooter0.getVelocity(),shooter1.getVelocity()) > 1200);
    }
}
