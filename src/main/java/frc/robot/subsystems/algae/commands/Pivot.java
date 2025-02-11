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

    @Override
    public void execute() {
        // TODO: Get clarification on what the purpose of pivot is.
        // /*
        // * I'm not sure if this will work, but implementing a 'switch case' might
        // emable
        // * users to freely
        // * move the pivot motor to any angle?
        // */

        /* Hope this is the right direction. */
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
