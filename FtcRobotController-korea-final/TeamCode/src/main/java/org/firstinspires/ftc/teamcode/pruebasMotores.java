package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class pruebasMotores extends OpMode {
    DriveTrain driveTrain;
    Shooter shooter;
    Intake intake;
    Climber climber;

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
        driveTrain.TeleOp(gamepad1, telemetry, false);
        if(gamepad1.dpad_left)
            shooter.shooterL.setPower(1);
        else
            shooter.shooterL.setPower(0);


        if(gamepad1.dpad_right)
            shooter.shooterR.setPower(1);
        else
            shooter.shooterR.setPower(0);


        if(gamepad1.right_trigger_pressed)
            shooter.transfer.setPower(1);
        else
            shooter.transfer.setPower(0);


        if(gamepad1.left_bumper)
            intake.intake.setPower(1);
        else
            intake.intake.setPower(0);


        if(gamepad1.right_bumper)
            climber.climber.setPower(1);
        else
            climber.climber.setPower(0);

        if(gamepad1.circle)
            shooter.block.setPosition(shooter.block.getPosition()<0.3 ? 1 : 0);

        if(gamepad1.triangle)
            intake.blockL.setPosition(intake.blockL.getPosition()<0.3 ? 1 : 0);

        if(gamepad1.cross)
            intake.blockR.setPosition(intake.blockR.getPosition()<0.3 ? 1 : 0);

        telemetry.addLine("ShooterL : dpad left");
        telemetry.addData("     velocity : ", shooter.shooterL.getVelocity());
        telemetry.addLine("ShooterR : dpad right");
        telemetry.addData("     velocity : ", shooter.shooterR.getVelocity());
        telemetry.addLine("Transfer : right trigger");
        telemetry.addLine("Intake : left bumper");
        telemetry.addLine("Climber : right bumper");
        telemetry.addLine("ShooterBlock : circle");
        telemetry.addLine("BlockL : triangle");
        telemetry.addLine("BlockR : cross");

        telemetry.update();
    }

}
