package frc.robot.subsystems.elevator.commands;

import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.Elevator.ElevatorState;
import edu.wpi.first.wpilibj2.command.Command;

public class ElevatorCommands {
    public Command elevate(Elevator elevator, double targetPos) {
        return new Command() {
            @Override
            public void initialize () {}

            @Override
            public void execute () {
                elevator.requestElevatorState(ElevatorState.MOVING_TO_POS, targetPos);
            }
            
            @Override
            public void end (boolean interrupted) {
                elevator.requestElevatorState(ElevatorState.HOLD_POS, null);
            }

            @Override
            public boolean isFinished () {
                return false;
            }
        };
    }
    public Command ascend (Elevator elevator) {
        return new Command() {
            @Override
            public void initialize () {}

            @Override
            public void execute () {
                elevator.requestElevatorState(ElevatorState.MANUALLY_CONTROLLED, 1);
            }
            
            @Override
            public void end (boolean interrupted) {
                elevator.requestElevatorState(ElevatorState.HOLD_POS, null);
            }

            @Override
            public boolean isFinished () {
                return false;
            }
        };
    }
    public Command descend (Elevator elevator) {
        return new Command() {
            @Override
            public void initialize () {}

            @Override
            public void execute () {
                elevator.requestElevatorState(ElevatorState.MANUALLY_CONTROLLED, -1);
            }
            
            @Override
            public void end (boolean interrupted) {
                elevator.requestElevatorState(ElevatorState.HOLD_POS, null);
            }

            @Override
            public boolean isFinished () {
                return false;
            }
        };
    }
}
