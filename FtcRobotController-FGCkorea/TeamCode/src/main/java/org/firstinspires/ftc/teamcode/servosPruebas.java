package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class servosPruebas extends OpMode {
    DriveTrain driveTrain;
    Shooter shooter;
    Intake intake;
    Climber climber;
    boolean climbing= false;

    @Override
    public void init() {
        driveTrain = new DriveTrain(hardwareMap);
        shooter = new Shooter(hardwareMap);
        intake = new Intake(hardwareMap);
        climber = new Climber(hardwareMap);
    }

    @Override
    public void start(){
        gamepad1.rumble(20);
        gamepad2.rumble(20);


    }

    @Override
    public void loop() {
        if (gamepad1.cross){
            if(intake.blockL.getPosition()>0.5)
                intake.blockL.setPosition(0);
            else
                intake.blockL.setPosition(1);

            if(intake.blockR.getPosition()>0.5)
                intake.blockR.setPosition(0);
            else
                intake.blockR.setPosition(1);

            if(shooter.block.getPosition()>0.5)
                shooter.block.setPosition(0);
            else
                shooter.block.setPosition(1);
        }

        telemetry.addData("pos block l", intake.blockL.getPosition());
        telemetry.addData("pos block r", intake.blockR.getPosition());
        telemetry.addData("pos block shooter", shooter.block.getPosition());


        telemetry.update();
    }

}
