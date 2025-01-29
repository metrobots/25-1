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
    public Intake () {}

    @Override
    public void initialize () {}

    @Override 
    public void execute () {
        Coral.run(-1);
    }

    @Override
    public void end (boolean interrupted) {
        Coral.lCoral.stopMotor();
        Coral.rCoral.stopMotor();
    }

    @Override
    public boolean isFinished () {
        return false;
    }
}
