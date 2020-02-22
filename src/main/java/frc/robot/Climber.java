/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Relay;


public class Climber {
    private static final int sparkID = 3;
    public XboxController m_driverController1 = new XboxController(0);
    public CANSparkMax m_motor = new CANSparkMax(sparkID, MotorType.kBrushless);
    public Relay m_latch = new Relay(0);
    public CANEncoder m_encoder;

    // private double delay = 0.200;
    // private double delayFlag = 0;
    // private double startTime = 0.0;

    public void climberInit() {
        m_motor.setIdleMode(IdleMode.kBrake);
        m_encoder = m_motor.getEncoder();
        m_encoder.setPosition(0.0);
        // SmartDashboard.putNumber("Light Saber Init", 0);

    }

    public void climber() throws InterruptedException {
        SmartDashboard.putNumber("Light Saber climber", 0);
        if (m_driverController1.getTriggerAxis(Hand.kRight) > 0.5 & m_encoder.getPosition() < 31) {
            // m_latch.set(Relay.Value.kOn);
            // Thread.sleep(400);
            m_motor.set(-0.33);
        }

        if (m_driverController1.getTriggerAxis(Hand.kRight) > 0.5 && m_encoder.getPosition() > 30
                && m_encoder.getPosition() < 171) {
            // m_latch.set(Relay.Value.kOn);
            // Thread.sleep(400);
            m_motor.set(-0.8);
        }

        if (m_driverController1.getTriggerAxis(Hand.kRight) > 0.5 && m_encoder.getPosition() > 170) {
            // m_latch.set(Relay.Value.kOn);
            m_motor.set(-0.33);
        }

        if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 && m_encoder.getPosition() < 31) {
            // m_latch.set(Relay.Value.kOn);
            // Thread.sleep(400);
            m_motor.set(0.33);
        }

        if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 && m_encoder.getPosition() > 30
                && m_encoder.getPosition() < 171) {
            // m_latch.set(Relay.Value.kOn);
            // Thread.sleep(400);
            m_motor.set(0.8);
        }

        if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 && m_encoder.getPosition() > 170
                && m_encoder.getPosition() < 191) {
            // m_latch.set(Relay.Value.kOn);
            // Thread.sleep(400);
            m_motor.set(0.33);
        }

        if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 && m_encoder.getPosition() > 190) {
            // m_latch.set(Relay.Value.kOff);
            m_motor.set(0.0);
        }

        if (m_driverController1.getTriggerAxis(Hand.kRight) > 0.5
                && m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5) {
            //m_latch.set(Relay.Value.kOn);
            //Thread.sleep(400);
            m_motor.set(-0.33);
        }

        if (m_driverController1.getTriggerAxis(Hand.kRight) < 0.5
                && m_driverController1.getTriggerAxis(Hand.kLeft) < 0.5) {
            m_motor.set(0.0);
            // Thread.sleep(100);
            // m_latch.set(Relay.Value.kOff); }

            SmartDashboard.putNumber("Light Saber Current", m_motor.getOutputCurrent());
            SmartDashboard.putNumber("Light Saber Position", m_encoder.getPosition());

        }

        /*
         * public void climber() { SmartDashboard.putNumber("Light Saber climber", 1);
         * //m_encoder.getPosition()); //Negative (-)speed value shall raise the
         * climber; //Positive (+)speed value shall lower the climber if
         * ((m_driverController1.getTriggerAxis(Hand.kRight) > 0.5) &&
         * (m_encoder.getPosition() < 31) && (delayFlag == 0)) {
         * m_latch.set(Relay.Value.kOn); startTime = Timer.getFPGATimestamp() + delay;
         * delayFlag =1; SmartDashboard.putNumber("Light Saber climber", 2); } if
         * ((m_driverController1.getTriggerAxis(Hand.kRight) > 0.5) &&
         * (m_encoder.getPosition() < 31) && (delayFlag == 1)) {
         * m_latch.set(Relay.Value.kOn); if (Timer.getFPGATimestamp() >= startTime) {
         * m_motor.set(-0.3); } }
         * 
         * if (m_driverController1.getTriggerAxis(Hand.kRight) > 0.5 &&
         * m_encoder.getPosition() > 30 && m_encoder.getPosition() < 171 && delayFlag ==
         * 0) { m_latch.set(Relay.Value.kOn); startTime = Timer.getFPGATimestamp() +
         * delay; delayFlag =1; } if (m_driverController1.getTriggerAxis(Hand.kRight) >
         * 0.5 && m_encoder.getPosition() > 30 && m_encoder.getPosition() < 171 &&
         * delayFlag == 1) { m_latch.set(Relay.Value.kOn); if (Timer.getFPGATimestamp()
         * >= startTime) { m_motor.set(-0.7); } }
         * 
         * if (m_driverController1.getTriggerAxis(Hand.kRight) > 0.5 &&
         * m_encoder.getPosition() > 170 & delayFlag == 0) {
         * m_latch.set(Relay.Value.kOn); startTime = Timer.getFPGATimestamp() + delay;
         * delayFlag =1; } if (m_driverController1.getTriggerAxis(Hand.kRight) > 0.5 &&
         * m_encoder.getPosition() > 170 && delayFlag == 1) {
         * m_latch.set(Relay.Value.kOn); if (Timer.getFPGATimestamp() >= startTime) {
         * m_motor.set(-0.3); } }
         * 
         * if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 &&
         * m_encoder.getPosition() < 31 && delayFlag == 0) {
         * m_latch.set(Relay.Value.kOn); startTime = Timer.getFPGATimestamp() + delay;
         * delayFlag =1; } if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 &&
         * m_encoder.getPosition() < 31 && delayFlag == 1) {
         * m_latch.set(Relay.Value.kOn); if (Timer.getFPGATimestamp() >= startTime) {
         * m_motor.set(-0.3); } }
         * 
         * if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 &&
         * m_encoder.getPosition() > 30 && m_encoder.getPosition() < 171 && delay == 0)
         * { m_latch.set(Relay.Value.kOn); startTime = Timer.getFPGATimestamp() + delay;
         * delayFlag =1; } if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 &&
         * m_encoder.getPosition() > 30 && m_encoder.getPosition() < 171 && delay == 1)
         * { m_latch.set(Relay.Value.kOn); if (Timer.getFPGATimestamp() >= startTime) {
         * m_motor.set(-0.7); } }
         * 
         * if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 &&
         * m_encoder.getPosition() > 170 && m_encoder.getPosition() < 191 && delay == 0)
         * { m_latch.set(Relay.Value.kOn); startTime = Timer.getFPGATimestamp() + delay;
         * delayFlag =1; } if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 &&
         * m_encoder.getPosition() > 170 && m_encoder.getPosition() < 191 && delay == 1)
         * { m_latch.set(Relay.Value.kOn); if (Timer.getFPGATimestamp() >= startTime) {
         * m_motor.set(-0.3); } }
         * 
         * if (m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5 &&
         * m_encoder.getPosition() > 195) { m_motor.set(0.0);
         * m_latch.set(Relay.Value.kOff); }
         * 
         * if (m_driverController1.getTriggerAxis(Hand.kRight) < 0.5 &&
         * m_driverController1.getTriggerAxis(Hand.kLeft) < 0.5) { m_motor.set(0.0);
         * m_latch.set(Relay.Value.kOff); delayFlag = 0; startTime = 0.0; }
         */
        // Override Mode = Press and Hold both triggers
        // if (m_driverController1.getTriggerAxis(Hand.kRight) > 0.5 &&
        // m_driverController1.getTriggerAxis(Hand.kLeft) > 0.5) {
        // m_latch.set(Relay.Value.kOn);
        // m_motor.set(-0.30);
        // }

        // SmartDashboard.putNumber("Light Saber Current", m_motor.getOutputCurrent());
        // SmartDashboard.putNumber("Light Saber Position", m_encoder.getPosition());

        // }
    }
}