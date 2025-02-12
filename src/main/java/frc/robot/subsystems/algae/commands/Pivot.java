package frc.robot.subsystems.algae.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeSubsystem;

public class Pivot extends Command {
    private final AlgaeSubsystem algae;

    public Pivot(AlgaeSubsystem subsystem) {
        this.algae = subsystem;
    }

    @Override
    public void initialize() {
        algae.stopPivot();
    }

    /**
     * @brief Currently moves the arm forward at a constant speed. Doesn't ever come
     *        back up.
     */
    @Override
    public void execute() {
        this.algae.drivePivot(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        algae.stopPivot();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
