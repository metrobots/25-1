package main.java.frc.robot.subsystems.pivot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class SetPivotState extends CommandsBase {
    PivotSubsystem pivot = new PivotSubsystem();

    public SetPivotState(SetPivotState state) {
        pivot.getCurrentState(state);
    }
}

