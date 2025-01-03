package frc.robot.utils;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public final class Configs {
    public static final class MK4iSwerveModule {
        public static final SparkMaxConfig drivingConfig = new SparkMaxConfig();
        public static final SparkMaxConfig turningConfig = new SparkMaxConfig();

        static {
            // L3 MK4i Constants
            final double kDrivingMotorReduction = 6.12; // L3 drive reduction
            final double kWheelDiameterMeters = 0.10033;
            final double kDriveWheelFreeSpeedRps = 5676.0 / 60.0 / kDrivingMotorReduction; // NEO free speed / reduction

            // Calculate conversion factors
            double drivingFactor = kWheelDiameterMeters * Math.PI / kDrivingMotorReduction;
            double drivingVelocityFeedForward = 1.0 / (kDriveWheelFreeSpeedRps * 
                kWheelDiameterMeters * Math.PI);

            // Driving motor configuration
            drivingConfig
                    .idleMode(IdleMode.kBrake)
                    .smartCurrentLimit(40);
            
            drivingConfig.encoder
                    .positionConversionFactor(drivingFactor)
                    .velocityConversionFactor(drivingFactor / 60.0);
            
            drivingConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                    // Tuned for L3
                    .pid(0.1, 0.0, 0.0)
                    .velocityFF(drivingVelocityFeedForward)
                    .outputRange(-1, 1);

            // Turning motor configuration
            turningConfig
                    .idleMode(IdleMode.kBrake)
                    .smartCurrentLimit(20);
            
            turningConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                    .pid(1.0, 0.0, 0.0) // Increased P gain for better holding
                    .outputRange(-1, 1)
                    .positionWrappingEnabled(true)
                    .positionWrappingInputRange(0, 2 * Math.PI);
        }
    }
}