package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Debouncer;


public class Shooter {
    public  DcMotorEx shooter0, shooter1, transfer;
    private boolean shooting = false;
    Debouncer shootDebounce, limitDebounce;

    double limitVel = 1200;
    double maxVel = 2100;

    //PIDFCoefficients coefficients = new PIDFCoefficients(22, 0, 1.7, 15);


    public Shooter (HardwareMap hardwareMap){
        shooter0 = hardwareMap.get(DcMotorEx.class,"Shoot_left");
        shooter1 = hardwareMap.get(DcMotorEx.class,"Shoot_right");
        transfer = hardwareMap.get(DcMotorEx.class,"Transfer");

        shooter1.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);

        shooter0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //shooter0.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);
        //shooter1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);

        shootDebounce = new Debouncer(300);
        limitDebounce = new Debouncer(300);
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
            shooting = !shooting;
        }

        if(shooting && !isReady()){
            setPowerShooter(1);
        }else if (shooting){
            setVelShooter(maxVel);
        }else{
            setPowerShooter(0);

        }

        if(gamepad1.right_trigger_pressed && isReady()){
            transfer.setPower(1);
        }else {
            transfer.setPower(0);
        }
        telemetry.addData("currVel", shooter0.getVelocity());
        telemetry.addData("limitVel", limitVel);
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
    }

    public boolean isReady(){
        return (Math.min(shooter0.getVelocity(),shooter1.getVelocity()) > 1200);
    }
}
