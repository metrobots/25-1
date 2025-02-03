package frc.robot.subsystems.coral.commands;

import frc.robot.utils.Config;
import frc.robot.utils.Constants.DriveConstants;
import frc.robot.subsystems.coral.Coral;

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

public class Intake extends Command {
    // Declares Coral object for command
    private final Coral coral = new Coral();
    
    public Intake () {} // Object Constructor

    @Override
    public void initialize () {} // Called once at Object Creation

    @Override 
    public void execute () { // Called as the Command is Run
        coral.run(-1); // See Coral.java
    }

    @Override
    public void end (boolean interrupted) { // Called when the Command is interrupted
        coral.lCoral.stopMotor(); // Stops the Motor
        coral.rCoral.stopMotor();
    }

    @Override
    public boolean isFinished () { // Somehow returns true after the Command is Interrupted
        return false;
    }
}