package frc.robot.subsystems.algae.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeSubsystem;

public class PickUpAlgae extends Command {

    /* Placeholder until real one is added. */
    private AlgaeSubsystem algaeSubsystem;

    @Override
    public void initialize() {
        algaeSubsystem.stopIntake();
    }

    @Override
    public void execute() {
        algaeSubsystem.driveIntake(1.0);
    }

    @Override
    public void end(boolean interrupted) {
        algaeSubsystem.stopIntake();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
