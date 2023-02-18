package frc.robot.commands.SimpleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
public class ArmExtendCommand extends CommandBase{

    private final Arm arm;


    public ArmExtendCommand(Arm arm){
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute(){
        arm.extend();
    }

    public void end(boolean interrupted){}

    @Override
    public boolean isFinished(){
        return true;
    }
}
