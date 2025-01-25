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

    public double targetPos;
    
    public Descend () {}
    
    @Override
    public initialize () {
        double initialPos = Elevator.getPos();

        if (initialPos < posStage2) {
            targetPos = posStage2;
        } else if (initialPos < posStage1) {
            targetPos = posStage1;
        } else {
            targetPos = posStage0;
        }
    }

    @Override
    public void execute () {
        Elevator.moveToPos(targetPos);
    }


    @Override
    public void end () {
        Elevator.elevatorSparkMax1.stopMotor();
        Elevator.elevatorSparkMax2.stopMotor();
    }
    
    @Override
    public boolean isFinished () {
        return false;
    }
}
