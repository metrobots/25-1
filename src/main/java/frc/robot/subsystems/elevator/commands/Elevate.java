package frc.robot.subsystems.elevator.commands;

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

import frc.robot.subsystems.elevator.Elevator;

public class Elevate extends Command {
    // Declares Elevator object for Command
    private Elevator elevator = new Elevator();

    double targetPos;

    public Elevate (double targetPos) { // Object Constructor
        this.targetPos = targetPos; // Don't Worry About It
    }

    @Override
    public void initialize () {} // Called once at object creation

    @Override
    public void execute () { // Called as the Command is run.
        elevator.moveToPos(targetPos); // 
    }

    @Override
    public void end (boolean interrupted) {
        elevator.elevatorSparkMax1.stopMotor();
        elevator.elevatorSparkMax2.stopMotor();
    }

    @Override
    public boolean isFinished () {
        return false;
    }
}
