package frc.robot.subsystems.algae;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Pivot extends Commands {
    public Pivot() {
        
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        
        /*
        I'm not sure if this will work, but implementing a 'switch case' might emable users to freely 
        move the pivot motor to any angle?
        */

        switch {

            /*
            Zero is just a placeholder for a future input value. Also, one of these will rotate the 
            motor forwards or backwards
            */
            case 0:
                AlgaeSubsystem.pivotMotor(insertSpeedHere);
                break;

            /*
            One is just a placeholder for a future input value. Also, one of these will rotate the 
            motor forwards or backwards
            */
            case 1:
                AlgaeSubsystem.pivotMotor(-insertSpeedHere);
                break;
        }
    }

    @Override
    public void end(boolean interrupted) {
        AlgaeSubsystem.pivotMotor.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
