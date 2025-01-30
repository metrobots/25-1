package frc.robot.subsystems.elevator.commands;

import frc.robot.utils.Config;
import frc.robot.utils.Constants.DriveConstants;
import frc.robot.subsystems.elevator.Elevator;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;

import edu.wpi.first.wpilibj2.command.Command;

public class Descend extends Command {
    // Positions for each elevator stage.
    public final double posStage0 = 0;
    public final double posStage1 = 1;
    public final double posStage2 = 2;
    public final double posStage3 = 3;

    // Declares Variable for the target position.
    public double targetPos;
    
    public Descend () {} // Object Constructor

    @Override
    public initialize () { // Called once at Object Creation
        double initialPos = Elevator.getPos(); // See Elevator.java

        // Logic System to determine Target Position
        if (initialPos > posStage2) { // Checks if Position is above a fixed position (the second stage of the elevator)
            targetPos = posStage2; // Sets the target position to the point checked above
        } else if (initialPos > posStage1) {
            targetPos = posStage1;
        } else { // There is no stage -1, therefore anything below stage 1 should be going to stage 0
            targetPos = posStage0;
        }
    }

    @Override
    public void execute () { // Called as the Command is Run
        Elevator.moveToPos(targetPos); // See Elevator.java
    }


    @Override
    public void end (boolean interrupted) { // Called when the Command is Interrupted
        Elevator.elevatorSparkMax1.stopMotor(); // Stops the Motor
        Elevator.elevatorSparkMax2.stopMotor();
    }
    
    @Override
    public boolean isFinished () { // Somehow returns true after the Command is interrupted.
        return false;
    }
}
