package frc.robot.utils;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.time.Duration;
import java.time.Instant;

/**
 * ControllerKit Xbox controller with advanced features for FRC robotics.
 * Extends CommandXboxController with additional functionality for precise control and advanced input detection.
 * 
 * Features include:
 * - Customizable deadbands and sensitivity
 * - Advanced stick position detection
 * - Button hold detection
 * - Rumble control patterns
 * - Diagonal and combination inputs
 */
public class ControllerKit extends CommandXboxController {
    private static final double DEFAULT_DEADBAND = 0.1;
    private double deadband = DEFAULT_DEADBAND;
    private Duration buttonHoldThreshold = Duration.ofMillis(500);
    private Instant buttonPressStart = null;
    private int rumbleIntensity = 0;
    private double stickSensitivity = 1.0;

    /**
     * Creates a new Xbox Controller on the specified port.
     * 
     * @param port The port number of the controller (typically 0 for first controller)
     * 
     * Example:
     * ControllerKit controller = new ControllerKit(0);
     */
    public ControllerKit(int port) {
        super(port);
    }

    /**
     * Sets the deadband for all stick inputs.
     * Inputs smaller than this value will be treated as 0.
     * 
     * @param deadband Value between 0 and 1 (typical values: 0.05 - 0.15)
     * 
     * Example:
     * controller.setDeadband(0.12); // Ignores small stick movements below 12%
     */
    public void setDeadband(double deadband) {
        this.deadband = deadband;
    }

    /**
     * Gets the current deadband value.
     * 
     * @return Current deadband value between 0 and 1
     * 
     * Example:
     * double currentDeadband = controller.getDeadband(); // Get current deadband
     */
    public double getDeadband() {
        return deadband;
    }

    /**
     * Sets the sensitivity multiplier for stick inputs.
     * Values > 1 increase sensitivity, values < 1 decrease sensitivity.
     * 
     * @param sensitivity Multiplier for stick values (typical range: 0.5 - 2.0)
     * 
     * Example:
     * controller.setStickSensitivity(1.5); // Make sticks 50% more sensitive
     */
    public void setStickSensitivity(double sensitivity) {
        this.stickSensitivity = sensitivity;
    }

    /**
     * Gets the left stick X-axis value with deadband and sensitivity applied.
     * 
     * @return Processed stick value between -1 and 1
     * 
     * Example:
     * double xValue = controller.getLeftX(); // Get processed left stick X value
     * drive.arcade(controller.getLeftY(), controller.getLeftX()); // Common drive use
     */
    @Override
    public double getLeftX() {
        return applyDeadband(super.getLeftX()) * stickSensitivity;
    }

    /**
     * Gets the left stick Y-axis value with deadband and sensitivity applied.
     * Note: Y axis is inverted by default (negative is up) to match joystick convention.
     * 
     * @return Processed stick value between -1 and 1
     * 
     * Example:
     * double forwardSpeed = -controller.getLeftY(); // Get forward/backward value
     */
    @Override
    public double getLeftY() {
        return applyDeadband(super.getLeftY()) * stickSensitivity;
    }

    /**
     * Checks if the left stick is being moved diagonally.
     * Useful for detecting 45-degree inputs or menu navigation.
     * 
     * @return true if stick is moved diagonally beyond deadband
     * 
     * Example:
     * if (controller.isDiagonalLeft()) {
     *     // Handle diagonal input
     *     robot.strafeDiagonally();
     * }
     */
    public boolean isDiagonalLeft() {
        double x = getLeftX();
        double y = getLeftY();
        return Math.abs(x) > deadband && Math.abs(y) > deadband;
    }

    /**
     * Checks if a stick is in the corner (maximum diagonal position).
     * Useful for detecting full diagonal inputs.
     * 
     * @return true if stick is near maximum in both X and Y
     * 
     * Example:
     * if (controller.isLeftStickInCorner()) {
     *     // Handle corner input
     *     robot.maximumStrafe();
     * }
     */
    public boolean isLeftStickInCorner() {
        return Math.abs(getLeftX()) > 0.8 && Math.abs(getLeftY()) > 0.8;
    }

    /**
     * Sets how long a button must be held to trigger a "held" state.
     * 
     * @param threshold Duration that button must be held (default: 500ms)
     * 
     * Example:
     * controller.setButtonHoldThreshold(Duration.ofSeconds(1)); // 1 second hold
     */
    public void setButtonHoldThreshold(Duration threshold) {
        this.buttonHoldThreshold = threshold;
    }

    /**
     * Checks if a specific button has been held for the threshold duration.
     * 
     * @param button Button ID to check
     * @return true if button has been held long enough
     * 
     * Example:
     * if (controller.isButtonHeld(XboxController.Button.kA.value)) {
     *     // Handle long press of A button
     *     robot.enableTurboMode();
     * }
     */
    public boolean isButtonHeld(int button) {
        if (getHID().getRawButton(button)) {
            if (buttonPressStart == null) {
                buttonPressStart = Instant.now();
            }
            return Duration.between(buttonPressStart, Instant.now()).compareTo(buttonHoldThreshold) >= 0;
        } else {
            buttonPressStart = null;
            return false;
        }
    }

    /**
     * Sets the rumble intensity for both motors.
     * 
     * @param intensity Value from 0-100 representing rumble strength
     * 
     * Example:
     * controller.setRumble(50); // 50% rumble intensity
     */
    public void setRumble(int intensity) {
        this.rumbleIntensity = Math.max(0, Math.min(100, intensity));
        double rumbleValue = this.rumbleIntensity / 100.0;
        getHID().setRumble(XboxController.RumbleType.kLeftRumble, rumbleValue);
        getHID().setRumble(XboxController.RumbleType.kRightRumble, rumbleValue);
    }

    /**
     * Gets the angle of the left stick in degrees.
     * 0° is right, 90° is up, 180° is left, 270° is down.
     * 
     * @return Angle in degrees (0-360)
     * 
     * Example:
     * double angle = controller.getLeftStickAngle();
     * if (angle > 45 && angle < 135) {
     *     // Handle upward movement
     * }
     */
    public double getLeftStickAngle() {
        return Math.toDegrees(Math.atan2(getLeftY(), getLeftX()));
    }

    /**
     * Gets the magnitude (distance from center) of the left stick.
     * Useful for determining how far the stick is pushed.
     * 
     * @return Magnitude between 0 and 1
     * 
     * Example:
     * double speed = controller.getLeftStickMagnitude();
     * drivetrain.drive(speed, angle); // Polar drive control
     */
    public double getLeftStickMagnitude() {
        double x = getLeftX();
        double y = getLeftY();
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Creates a Trigger that activates when A and B are pressed together.
     * 
     * @return Trigger for A+B combination
     * 
     * Example:
     * new InstantCommand(robot::specialMove).onTrue(controller.abButtonsPressed());
     */
    public Trigger abButtonsPressed() {
        return a().and(b());
    }

    /**
     * Checks if the D-pad is being held diagonally.
     * Useful for menu navigation or special moves.
     * 
     * @return true if D-pad is at 45°, 135°, 225°, or 315°
     * 
     * Example:
     * if (controller.isDPadDiagonal()) {
     *     // Handle diagonal D-pad input
     *     menu.diagonalSelect();
     * }
     */
    public boolean isDPadDiagonal() {
        return getHID().getPOV() % 90 != 0 && getHID().getPOV() != -1;
    }

    // Private utility methods

    /**
     * Applies deadband processing to an axis value.
     * Values within deadband are set to 0.
     */
    private double applyDeadband(double value) {
        if (Math.abs(value) < deadband) {
            return 0.0;
        }
        return value;
    }
}