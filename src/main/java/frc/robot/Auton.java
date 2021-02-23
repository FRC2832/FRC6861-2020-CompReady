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
    private double stepTimeB3 = 2.0;
    private double stepTimeB5 = 2.5;
    private double stepTimeB6 = 1.0;
    private double stepTimeB7 = 3.0;

    private double stepTimeC1 = 2.0; // Default Move Time
    private double stepTimeC2 = 1.0;

    private static boolean move1SecDone = true;

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

    // Autonomous mode: Robot positioned anywhere on the line
    // Move forward for a set amount of time
    public void autonMove2Sec() {
        double timerValue = m_timer.get();
        SmartDashboard.putString("Auton Mode", "autonMove2Sec");
        SmartDashboard.putNumber("Auton Step", step);
        SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
        SmartDashboard.putNumber("Timer", timerValue);
        if ((timerValue < stepTimeC1) && (step == 1)) {
            driveTrain.driveArcade(0.6, 0.0);
        } else if ((timerValue > stepTimeC1) && (step == 1)) {
            driveTrain.driveArcade(0, 0);
            step = 2; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }
    }

    public void move1Sec() {
        double timerValue = m_timer.get();
        SmartDashboard.putString("Auton Mode", "move1Sec");
        SmartDashboard.putNumber("Auton Step", step);
        SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
        SmartDashboard.putNumber("Timer", timerValue);
        if ((timerValue < stepTimeC2) && (step == 1)) {
            driveTrain.driveArcade(0.6, 0.0);
        } else if ((timerValue > stepTimeC2) && (step == 1)) {
            driveTrain.driveArcade(0, 0);
            step = 2; // increment step counter, move to next step
            // Auton.setMove1SecDone(true); // TODO: is this right?
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
            Auton.setMove1SecDone(true);
        }
    }

    public static void setMove1SecDone(boolean done) {
        move1SecDone = done;
    }

    public static boolean getMove1SecDone() {
        return move1SecDone;
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
        } else {
            driveTrain.driveArcade(0, 0);
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
        } else {
            driveTrain.driveArcade(0, 0);
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
            driveTrain.driveArcade(0.6, 0.0);
        } else if ((m_timer.get() > stepTimeB1) && (step == 1)) {
            driveTrain.driveArcade(0, 0);
            step = 2; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if (step == 2) {
            driveTrain.driveArcade(0.3, 0.6);
        }

        if (((m_gyro.getFusedHeading() % 360) < -85) && (step == 2)) {
            driveTrain.driveArcade(0, 0);
            step = 3; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB3) && (step == 3)) {
            driveTrain.driveArcade(0.5, 0.0);
        } else if ((m_timer.get() > stepTimeB3) && (step == 3)) {
            ingester.ingesterAuton(0.0);
            step = 4; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        }

        if (step == 4) {
            driveTrain.driveArcade(0.3, -0.6);
        }

        if (((m_gyro.getFusedHeading() % 360) > -5) && (step == 4)) {
            driveTrain.driveArcade(0, 0);
            step = 5; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB5) && (step == 5)) {
            driveTrain.driveArcade(0.6, 0);
        } else if ((m_timer.get() > stepTimeB5) && (step == 5)) {
            driveTrain.driveArcade(0, 0);
            step = 6; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
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
        } else {
            driveTrain.driveArcade(0, 0);
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
            driveTrain.driveArcade(0.6, 0.0);
        } else if ((m_timer.get() > stepTimeB1) && (step == 1)) {
            driveTrain.driveArcade(0, 0);
            step = 2; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if (step == 2) {
            driveTrain.driveArcade(0.3, -0.6);
        }

        if (((m_gyro.getFusedHeading() % 360) > 85) && (step == 2)) {
            driveTrain.driveArcade(0, 0);
            step = 3; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB3) && (step == 3)) {
            driveTrain.driveArcade(0.5, 0.0);
        } else if ((m_timer.get() > stepTimeB3) && (step == 3)) {
            ingester.ingesterAuton(0.0);
            step = 4; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        }

        if (step == 4) {
            driveTrain.driveArcade(0.3, 0.6);
        }

        if (((m_gyro.getFusedHeading() % 360) < 5) && (step == 4)) {
            driveTrain.driveArcade(0, 0);
            step = 5; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB5) && (step == 5)) {
            driveTrain.driveArcade(0.6, 0);
        } else if ((m_timer.get() > stepTimeB5) && (step == 5)) {
            driveTrain.driveArcade(0, 0);
            step = 6; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
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
            driveTrain.driveArcade(-0.6, -0.3);
        } else if ((m_timer.get() > stepTimeB7) && (step == 7)) {
            driveTrain.driveArcade(0, 0);
            step = 8; // increment step counter, move to next step
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }
    }
    
    public void centerRobot() {
        System.out.println("centering robot");
        if (Pi.getMoveLeft()) {
            driveTrain.driveTank(-0.2, 0.2);
            // System.out.println("turning left");
        } else if (Pi.getMoveRight()) {
            driveTrain.driveTank(0.2, -0.2);
            // System.out.println("turning right");
        } else {
            driveTrain.driveTank(0, 0);
            // System.out.println("not turning");
        }
    }

    public void centerAndMove() {
        
    }

}