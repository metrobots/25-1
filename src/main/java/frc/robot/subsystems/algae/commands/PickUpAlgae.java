package frc.robot.subsystems.algae.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeSubsystem;
import frc.robot.subsystems.algae.AlgaeSubsystem.AlgaeState;

/**
 * Sets state of algae subsystem to PICK_UP when initialized. When the command
 * ends, the state will be set to IDLE.
 */
public class PickUpAlgae extends Command {
    private final AlgaeSubsystem algae;

    public PickUpAlgae(AlgaeSubsystem algae) {
        this.algae = algae;
    }

    @Override
    public void initialize() {
        this.algae.setCurrentState(AlgaeState.PICK_UP);
    }

    @Override
    public void execute() {
        /* Do nothing, since algae state is already set in initialize(). */
    }

    @Override
    public void end(boolean interrupted) {
        this.algae.setCurrentState(AlgaeState.IDLE);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
