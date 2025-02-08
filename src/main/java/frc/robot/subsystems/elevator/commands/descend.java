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
    /// Declares Elevator object for command
    public final Elevator elevator = new Elevator();

    // Declares Variable for the target position.
    public double adjust = 1;
    
    public Descend () {} // Object Constructor
    
    @Override
    public void initialize () {} // Called once at Object Creation

    @Override
    public void execute () { // Called as the Command is Run
        double currentPos = elevator.getPos(); // See Elevator.java
        elevator.moveToPos(currentPos + adjust); // See Elevator.java
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
