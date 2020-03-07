package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveTrain;
import frc.robot.Ingester;

import com.ctre.phoenix.sensors.PigeonIMU;

public class Auton {
  private static Timer m_timer = new Timer();
  private DriveTrain driveTrain;
  private Ingester ingester = new Ingester();

  private PigeonIMU m_gyro;

  private double stepTimeA1 = 4.0;
  private double stepTimeA2 = 1.0;
  private double stepTimeA3 = 3.0;

  private double stepTimeB1 = 1.5;
  private double stepTimeB3 = 1.5;
  private double stepTimeB5 = 2.5;
  private double stepTimeB6 = 1.0;
  private double stepTimeB7 = 4.0;

  private double stepTimeC1 = 2.0;

  private int step = 1;

  public Auton(DriveTrain driveTrain) {
    this.driveTrain = driveTrain;
    this.m_gyro = new PigeonIMU(driveTrain.m_leftRrMotor);
}

public void autonInit() {
    m_gyro.setFusedHeading(0);
    step = 1;
    m_timer.reset();
    m_timer.start();
    SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
  }

  // Autonomous mode: Robot positioned directly in front of the scoring goal
  // Move fwd to goal, score, get out of the way
  public void autonFrontGoal() {
    SmartDashboard.putString("Auton Mode", "autonFrontGoal");
    SmartDashboard.putNumber("Auton Step", step);
    SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
    SmartDashboard.putNumber("Timer", m_timer.get());
    if ((m_timer.get() < stepTimeA1) && (step == 1)) {
      driveTrain.driveArcade(0.6, 0.0);
    } else if ((m_timer.get() > stepTimeA1) && (step == 1)) {
      driveTrain.driveArcade(0, 0);
      step = 2; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeA2) && (step == 2)) {
      ingester.ingesterAuton(-1.0);
    } else if ((m_timer.get() > stepTimeA2) && (step == 2)) {
      ingester.ingesterAuton(0.0);
      step = 3; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeA3) && (step == 3)) {
      driveTrain.driveArcade(-0.6, 0.45);
    } else if ((m_timer.get() > stepTimeA3) && (step == 3)) {
      driveTrain.driveArcade(0, 0);
      step = 4; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }
  }

  // Autonomous mode: Robot positioned just left of the scoring goal
  // Move forward, turn right, move forward, move left, move fwd to goal, score,
  // get out of the way
  public void autonLeftGoal() {
    SmartDashboard.putString("Auton Mode", "autonLeftGoal");
    SmartDashboard.putNumber("Auton Step", step);
    SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading() % 360);
    SmartDashboard.putNumber("Timer", m_timer.get());
    if ((m_timer.get() < stepTimeB1) && (step == 1)) {
      driveTrain.driveArcade(0.4, 0.0);
    } else if ((m_timer.get() > stepTimeB1) && (step == 1)) {
      driveTrain.driveArcade(0, 0);
      step = 2; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if (step == 2) {
      driveTrain.driveArcade(0.3, 0.6);
    }

    if (((m_gyro.getFusedHeading() % 360) < -85) && (step == 2)) {
      driveTrain.driveArcade(0, 0);
      step = 3; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeB3) && (step == 3)) {
      driveTrain.driveArcade(0.4, 0.0);
    } else if ((m_timer.get() > stepTimeB3) && (step == 3)) {
      ingester.ingesterAuton(0.0);
      step = 4; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if (step == 4) {
      driveTrain.driveArcade(0.3, -0.6);
    }

    if (((m_gyro.getFusedHeading() % 360) > -5) && (step ==4)) {
      driveTrain.driveArcade(0, 0);
      step = 5; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeB5) && (step == 5)) {
      driveTrain.driveArcade(0.6, 0.3);
    } else if ((m_timer.get() > stepTimeB5) && (step == 5)) {
      driveTrain.driveArcade(0, 0);
      step = 6; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeB6) && (step == 6)) {
      ingester.ingesterAuton(-1.0);
    } else if ((m_timer.get() > stepTimeB6) && (step == 6)) {
      ingester.ingesterAuton(0.0);
      step = 7; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeB7) && (step == 7)) {
      driveTrain.driveArcade(-0.6, 0.3);
    } else if ((m_timer.get() > stepTimeB7) && (step == 7)) {
      driveTrain.driveArcade(0, 0);
      step = 8; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }
  }

  // Autonomous mode: Robot positioned just right of the scoring goal
  // Move forward, turn left, move forward, move right, move fwd to goal, score,
  // get out of the way
  public void autonRightGoal() {
    SmartDashboard.putString("Auton Mode", "autonRightGoal");
    SmartDashboard.putNumber("Auton Step", step);
    SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading() % 360);
    SmartDashboard.putNumber("Timer", m_timer.get());
    if ((m_timer.get() < stepTimeB1) && (step == 1)) {
      driveTrain.driveArcade(0.4, 0.0);
    } else if ((m_timer.get() > stepTimeB1) && (step == 1)) {
      driveTrain.driveArcade(0, 0);
      step = 2; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if (step == 2) {
      driveTrain.driveArcade(0.3, -0.6);
    }

    if (((m_gyro.getFusedHeading() % 360) < 85) && (step == 2)) {
      driveTrain.driveArcade(0, 0);
      step = 3; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeB3) && (step == 3)) {
      driveTrain.driveArcade(0.4, 0.0);
    } else if ((m_timer.get() > stepTimeB3) && (step == 3)) {
      ingester.ingesterAuton(0.0);
      step = 4; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if (step == 4) {
      driveTrain.driveArcade(0.3, 0.6);
    }

    if (((m_gyro.getFusedHeading() % 360) > 5) && (step ==4)) {
      driveTrain.driveArcade(0, 0);
      step = 5; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeB5) && (step == 5)) {
      driveTrain.driveArcade(0.6, 0.3);
    } else if ((m_timer.get() > stepTimeB5) && (step == 5)) {
      driveTrain.driveArcade(0, 0);
      step = 6; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeB6) && (step == 6)) {
      ingester.ingesterAuton(-1.0);
    } else if ((m_timer.get() > stepTimeB6) && (step == 6)) {
      ingester.ingesterAuton(0.0);
      step = 7; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }

    if ((m_timer.get() < stepTimeB7) && (step == 7)) {
      driveTrain.driveArcade(-0.6, 0.3);
    } else if ((m_timer.get() > stepTimeB7) && (step == 7)) {
      driveTrain.driveArcade(0, 0);
      step = 8; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }
  }

  public void autonMove2Sec() {
    SmartDashboard.putString("Auton Mode", "autonMove2Sec");
    SmartDashboard.putNumber("Auton Step", step);
    SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
    SmartDashboard.putNumber("Timer", m_timer.get());
    if ((m_timer.get() < stepTimeC1) && (step == 1)) {
      driveTrain.driveArcade(0.4, 0.0);
    } else if ((m_timer.get() > stepTimeC1) && (step == 1)) {
      driveTrain.driveArcade(0, 0);
      step = 2; // increment step counter, move to next step
      m_timer.reset();
      m_timer.start();
    }
  }
}