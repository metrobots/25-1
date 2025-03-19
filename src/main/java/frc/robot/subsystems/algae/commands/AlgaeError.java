package frc.robot.subsystems.algae.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeSubsystem;
import frc.robot.subsystems.algae.AlgaeSubsystem.AlgaeState;

/**
 * Sets algae state to ERROR. Does not reset to IDLE when the command is
 * finished running.
 */
public class AlgaeError extends Command {
    private final AlgaeSubsystem algae;

    public AlgaeError(AlgaeSubsystem algae) {
        this.algae = algae;
    }

    @Override
    public void initialize() {
        this.algae.setCurrentState(AlgaeState.ERROR);
    }

    @Override
    public void execute() {
        /* Do nothing, since algae state is already set in initialize(). */
    }

    @Override
    public void end(boolean interrupted) {
        /* Do nothing, since algae state is already set in initialize(). */
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
