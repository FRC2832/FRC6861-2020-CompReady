/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.XboxController;


public class ColorWheel {
    private WPI_TalonSRX m_colorWheel = new WPI_TalonSRX(5);
    public XboxController m_driverController2 = new XboxController(1);
    
    
public void colorWheelSpin() {
      if(m_driverController2.getXButtonPressed() & m_colorWheel.getSelectedSensorPosition()<10) //Auto mode
      {
        m_colorWheel.set(.8);
      }
      if(m_colorWheel.getSelectedSensorPosition()>=3400) //3400 is an approximation for number of counts for 3.5 turns 
      {
        m_colorWheel.set(0);
        m_colorWheel.setSelectedSensorPosition(0);
      }
      if(m_driverController2.getYButtonPressed()) //Manual mode
      {
        m_colorWheel.set(0.3);
      }
      if(m_driverController2.getYButtonReleased())
      {
        m_colorWheel.set(0.0);
      }
            
    }

}
