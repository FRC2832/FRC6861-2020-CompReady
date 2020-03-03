package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogGyro;
import frc.robot.DriveTrain;
import frc.robot.Ingester;
import frc.robot.Robot;

public class Auton{
   /* AutonPos1 autonPos1 = new AutonPos1();
    AutonPos2 autonPos2 = new AutonPos2();*/

     
    private final AnalogGyro m_gyro = new AnalogGyro(0);
    private final DriveTrain driveTrain = new DriveTrain();
    private final Ingester ingester = new Ingester();
    private final Robot robot = new Robot();
  
    //private final int count = 1;
  
    
    private final double stepTime1 = 4.0;
    private final double stepTime2 = 1.0;
    private final double stepTime3 = 4.0;
    private final double stepTime4 = 0.85;
    private final double stepTime5 = 4.0;
    private int count = 1;

    public void AutonPos1Run(){
        System.out.println("In AutonPos1Run-1: ");
      if ((Robot.m_timer.get() < stepTime1) && (count == 1)) {
        System.out.println("In AutonPos1Run-1--if: ");
        driveTrain.driveArcade(0.6, 0.0);
        //SmartDashboard.putNumber("Auton Stage", 1);
      } else if ((Robot.m_timer.get() > stepTime1) && (count == 1)) {
        driveTrain.driveArcade(0, 0);
        count = 2;
        Robot.m_timer.reset();
        Robot.m_timer.start();
      }
  
      if ((Robot.m_timer.get() < stepTime2) && (count == 2)) {
        ingester.ingesterAuton(-1.0);
        SmartDashboard.putNumber("Auton Stage", 2);
      } else if ((Robot.m_timer.get() > stepTime2) && (count == 2)) {
        ingester.ingesterAuton(0.0);
        count = 3;
        Robot.m_timer.reset();
        Robot.m_timer.start();
      }
  
      if ((Robot.m_timer.get() < stepTime3) && (count == 3)) {
        driveTrain.driveArcade(-0.6, 0.3);
        SmartDashboard.putNumber("Auton Stage", 3);
      } else if ((Robot.m_timer.get() > stepTime3) && (count == 3)) {
        driveTrain.driveArcade(0, 0);
        count = 4;
        Robot.m_timer.reset();
        Robot.m_timer.start();
      }
    }
      public void AutonPos2Run(){
      
     double stepTime1 = 4.0;
     double stepTime2 = 0.75;
     double stepTime3 = 4.5;
     double stepTime4 = 0.85;
     double stepTime5 = 4.0;
     int count = 1;
     System.out.println("In AutonPos1Run-2: ");
        if ((robot.m_timer.get() < stepTime3) && (count==1)) {
          driveTrain.driveArcade(0.6, -0.42);
          SmartDashboard.putNumber("Auton Stage", 3);
        } else if ((robot.m_timer.get() > stepTime3) && (count==1)) {
          driveTrain.driveArcade(0, 0);
          count = 2;
          robot.m_timer.reset();
          robot.m_timer.start();
        }
          
            if ((robot.m_timer.get() < stepTime4) && (count==2)) {
              driveTrain.driveArcade(.6, 0);
              SmartDashboard.putNumber("Auton Stage", 3);
            } else if ((robot.m_timer.get() > stepTime4) && (count==2)) {
              driveTrain.driveArcade(0, 0);
              count = 3;
              robot.m_timer.reset();
              robot.m_timer.start();
           }
          
        if ((robot.m_timer.get() < 3) && (count==3)) {
          ingester.ingesterAuton(-1.0);
        } else if ((robot.m_timer.get() > stepTime2) && (count==3)) {
          ingester.ingesterAuton(0.0);
          count = 4;
          robot.m_timer.reset();
          robot.m_timer.start();
        }
    
        if ((robot.m_timer.get() < stepTime4) && (count==4)) {
          driveTrain.driveArcade(-0.6, 0.3);
          SmartDashboard.putNumber("Auton Stage", 3);
        } else if ((robot.m_timer.get() > stepTime4) && (count==4)) {
          driveTrain.driveArcade(0, 0);
          count = 5;
          robot.m_timer.reset();
          robot.m_timer.start();
        }
    }
}