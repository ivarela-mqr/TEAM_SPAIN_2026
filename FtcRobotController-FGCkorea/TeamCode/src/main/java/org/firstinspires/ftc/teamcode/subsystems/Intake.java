package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Debouncer;



public class Intake {
    public DcMotorEx intake;
    public boolean intaking = false;
    public Debouncer intakeDebouncer, outDebouncer;
    public Servo blockL, blockR;
    private final ElapsedTime unblockTimer = new ElapsedTime();
    private boolean waitingToBlock = false;


    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        blockL = hardwareMap.get(Servo.class,"blockLeft");
        blockR = hardwareMap.get(Servo.class,"blockRight");
        //timer = new ElapsedTime();

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeDebouncer = new Debouncer(200);
        outDebouncer = new Debouncer(200);
    }

    public void Start(){
        expand();
        if(isExpanded())
            intaking = true;
    }
    public void TeleOp(Gamepad gamepad1, Telemetry telemetry, boolean climbing, boolean unblocked){
        /*if(500<timer.milliseconds() && timer.milliseconds()<1500){
            expand();
        }
         */
        if(gamepad1.left_bumper && intakeDebouncer.isReady())
            intaking = !intaking;

        if(!climbing){
            if(gamepad1.triangle){
                intake.setPower(-1);
            } else if (intaking) {
                intake.setPower(1);
            }else{
                intake.setPower(0);
            }
        }else {
            intake.setPower(0);
        }

        if (unblocked && !waitingToBlock) {
            waitingToBlock = true;
            unblockTimer.reset();
        }

        // Después de 1 segundo
        if (waitingToBlock && unblockTimer.seconds() >= 4) {
            block();
            waitingToBlock = false;
        }

/*
        if(gamepad1.dpad_down){
            block();
        }
        if(gamepad1.dpad_up){
            expand();
        }*/
        
        telemetry.addData("Pos L",blockL.getPosition());
        telemetry.addData("Pos R",blockR.getPosition());
    }

    public void startExpand(){
        blockL.setPosition(0.5);
        blockR.setPosition(0.5);
    }
    public void expand(){ //old version: l=0.5 r=0.4
        blockL.setPosition(0);
        blockR.setPosition(1);
    }
    public  boolean isExpanded(){
        return (blockL.getPosition()<0.1);
    }
    public void block(){ //old version: l=1 r=0
        blockL.setPosition(1);
        blockR.setPosition(0);
    }


}