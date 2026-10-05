package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class FGC26TeleOpMode extends OpMode {
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

        intake.Start();
        shooter.Start();

    }

    @Override
    public void loop() {
        driveTrain.TeleOp(gamepad1, telemetry);
        shooter.TeleOp(gamepad1,telemetry, climbing);
        intake.TeleOp(gamepad1,telemetry, climbing, shooter.isTransferOn());
        climbing = climber.TeleOp(gamepad1,telemetry);

        telemetry.update();
    }

}
