package frc.robot.subsystems.algae.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeSubsystem;

public class ShootAlgae extends Command {

    /* Placeholder until real one is added. */
    private AlgaeSubsystem algaeSubsystem;

    public ShootAlgae(AlgaeSubsystem subsystem) {
        this.algaeSubsystem = subsystem;
        addRequirements(this.algaeSubsystem);
    }

    @Override
    public void initialize() {
        algaeSubsystem.stopDrive();
    }

    @Override
    public void execute() {
        algaeSubsystem.driveOutward();
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
