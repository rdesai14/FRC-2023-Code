package frc.robot.commands.DriveTypes;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class ArcadeDrive extends CommandBase {
  private final Drivetrain drive;
  private final DoubleSupplier translationalXSupplier;
  private final DoubleSupplier turn;
  private final BooleanSupplier turbo;
  private final SlewRateLimiter slewRate;
  private double x;
  private double y;
  private double rotation;
  private double[] rollingInputX  = new double[DriveConstants.INPUT_ROLLING_AVERAGE_SAMPLE_SIZE];
  private int index = 0;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param left      The control input for driving backwards
   * @param right     The control input for turning
   * @param turbo     The button input for toggling the robot speed
   */
  public ArcadeDrive(
      Drivetrain subsystem, DoubleSupplier translationalXSupplier, DoubleSupplier turn, BooleanSupplier turbo) {
        super();

    this.drive = subsystem;
    this.translationalXSupplier = translationalXSupplier;
    this.turn = turn;
    this.turbo = turbo;
    slewRate = new SlewRateLimiter(12);

    addRequirements(drive);
  }

  @Override
  public void execute() {
    x = translationalXSupplier.getAsDouble();
    rotation = turn.getAsDouble();

    rollingInputX[index] = x;
    index++;
    if (index == DriveConstants.INPUT_ROLLING_AVERAGE_SAMPLE_SIZE) {
      index = 0;
    }

    if(turbo.getAsBoolean() == true) {
      drive.arcadeDrive(getAverage(rollingInputX) * 0.7, rotation);
    }




    // if (turbo.getAsBoolean() == true){
    //   double scalar = turbo.getAsBoolean() ? 1: 1;
    //   drive.arcadeDrive(slewRate.calculate(forward.getAsDouble() * -scalar), turn.getAsDouble() * -scalar);
    // } else{
    //   double scalar = turbo.getAsBoolean() ? .5 : .7;
    //   drive.arcadeDrive(slewRate.calculate(forward.getAsDouble() * -scalar), turn.getAsDouble() * -scalar);
    // }



  }

  private double getAverage(double[] arr) {
    double rtn = 0;
    for(double i : arr) {
      rtn+=i;
    }
    return rtn / arr.length;
  }

}
