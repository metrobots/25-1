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

public class Ascend extends Command {
    // Declares Elevator object for command
    public final Elevator elevator = new Elevator();
    
    // Positions for each elevator stage in inches
    public final double posStage0 = 0;
    public final double posStage1 = 1;
    public final double posStage2 = 2;
    public final double posStage3 = 3;

    // Declares Variable for the target position.
    public double targetPos;
    
    public Ascend () {} // Object Constructor
    
    @Override
    public void initialize () { // Called once at Object Creation
        double initialPos = elevator.getPos(); // See Elevator.java

        // Logic System to determine Target Position
        if (initialPos < posStage1) { // Checks if Position is below a fixed position (the first stage of the elevator)
            targetPos = posStage1; // Sets the target position to the point checked above
        } else if (initialPos < posStage2) {
            targetPos = posStage2;
        } else { // There is no stage 4, therefore anything above stage 2 should be going to stage 3
            targetPos = posStage3;
        }
    }

    @Override
    public void execute () { // Called as the Command is Run
        elevator.moveToPos(targetPos); // See Elevator.java
    }
    
    @Override
    public void end (boolean interrupted) { // Called when the Command is interrupted
        elevator.elevatorSparkMax1.stopMotor(); // Stops the Motor
        elevator.elevatorSparkMax2.stopMotor();
    }
    
    @Override
    public boolean isFinished () { // Somehow returns true after the Command is interrupted.
        return false;
    }
}
