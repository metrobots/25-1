package frc.robot.subsystems.algae.commands;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeSubsystem;

public class PickUpAlgae extends Command {

    /* Placeholder until real one is added. */
    private AlgaeSubsystem algaeSubsystem;

    @Override
    public void initialize() {
        algaeSubsystem.stopDrive();
    }

    @Override
    public void execute() {
        // TODO: implement pivot motor (I think?).
        algaeSubsystem.driveInward();
    }

    @Override
    public void end(boolean interrupted) {
        algaeSubsystem.stopDrive();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
