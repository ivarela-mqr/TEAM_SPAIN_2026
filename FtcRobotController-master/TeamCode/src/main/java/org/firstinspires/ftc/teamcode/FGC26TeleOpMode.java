package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class FGC26TeleOpMode extends OpMode {
    DriveTrain driveTrain;
    Shooter shooter;
    Intake intake;

    @Override
    public void init() {
        driveTrain = new DriveTrain(hardwareMap);
        shooter = new Shooter(hardwareMap);
        intake = new Intake(hardwareMap);
    }

    @Override
    public void start(){
        gamepad1.rumble(20);
        gamepad2.rumble(20);
    }


    @Override
    public void loop() {
        driveTrain.TeleOp(gamepad1, gamepad2, telemetry);
        shooter.TeleOp(gamepad1,gamepad2,telemetry);
        intake.TeleOp(gamepad1,gamepad2,telemetry);

        telemetry.update();

    }

}
