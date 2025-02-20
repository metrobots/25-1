package frc.robot.subsystems.algae.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeSubsystem;
import frc.robot.subsystems.algae.AlgaeSubsystem.AlgaeState;

public class SetAlgaeState extends Command {
    private final AlgaeSubsystem algae = new AlgaeSubsystem();

    public SetAlgaeState(AlgaeState state) {
        algae.setCurrentState(state);
    }
}
