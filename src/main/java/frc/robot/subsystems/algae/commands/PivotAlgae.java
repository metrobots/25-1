package frc.robot.subsystems.algae.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeSubsystem;
import frc.robot.subsystems.algae.AlgaeSubsystem.AlgaeState;

public class PivotAlgae extends Command {
    private final AlgaeSubsystem algae;

    public PivotAlgae(AlgaeSubsystem algae) {
        this.algae = algae;
    }

    @Override
    public void initialize() {
        algae.stopIntake();
        algae.stopPivot();
    }

    /**
     * If the current state is PICK_UP, then will run pickUp() method.
     * If the current state is PREP_SHOOT, the pivot will continuously move
     * up, but it will not shoot.
     */
    @Override
    public void execute() {
        switch (algae.getCurrentState()) {
            case PICK_UP:
                pickUp();
                break;
            case PREP_SHOOT:
                shoot();
                break;
            default:
                break;
        }
    }

    /**
     * Moves the pivot at a constant speed to pick up algae while
     * continually driving intake.
     */
    private void pickUp() {
        algae.drivePivot(0.5);
        algae.driveIntake(0.5);
    }

    private void shoot() {
        algae.drivePivot(-0.5);
    }

    /**
     * After this command is exectued, the state will swap.
     */
    @Override
    public void end(boolean interrupted) {
        algae.stopIntake();
        algae.stopPivot();

        if (algae.getCurrentState() == AlgaeState.PICK_UP) {
            algae.setCurrentState(AlgaeState.PREP_SHOOT);
        } else if (algae.getCurrentState() == AlgaeState.PREP_SHOOT) {
            algae.setCurrentState(AlgaeState.PICK_UP);
        }
    }
}
