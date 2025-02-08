package frc.robot.subsystems.algae.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeSubsystem;

public class ShootAlgae extends Command {

    private AlgaeSubsystem algaeSubsystem;

    public ShootAlgae() {}

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
