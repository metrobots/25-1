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

    @Override
    public void execute() {
        switch (algae.getCurrentState()) {
            case PICK_UP:
                pickUp();
                break;
            case SHOOT:
                shoot();
                break;
            default:
                break;
        }
    }

    private void pickUp() {
        algae.drivePivot(0.5);
        algae.driveIntake(0.5);
    }

    private void shoot() {
        algae.drivePivot(-0.5);
    }

    @Override
    public void end(boolean interrupted) {
        algae.stopIntake();
        algae.stopPivot();

        if (algae.getCurrentState() == AlgaeState.PICK_UP) {
            algae.setCurrentState(AlgaeState.SHOOT);
        } else if (algae.getCurrentState() == AlgaeState.SHOOT) {
            algae.setCurrentState(AlgaeState.PICK_UP);
        }
    }
}
