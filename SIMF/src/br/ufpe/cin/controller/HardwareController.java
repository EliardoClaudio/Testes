package br.ufpe.cin.controller;

import java.io.IOException;
import java.util.Date;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.enums.StateMachineEnum;
import br.ufpe.cin.support.MySshConnector;
import br.ufpe.cin.support.MyTimer;
import br.ufpe.cin.support.SubnetCheck;
import br.ufpe.cin.support.WriteFile;

/**
 * This class implements a Hardware controller
 * 
 * @author Vandi Alves - valn@cin.ufpe.br
 *
 */
public class HardwareController {

    private StateMachineEnum state;
    private MyTimer timer;
    private MySshConnector sshConnection;
    private RandomVariateGenerator randF;
    private RandomVariateGenerator randR;
    private SubnetCheck machine;

    public HardwareController(MySshConnector sshConnection, RandomVariateGenerator failure,
	    RandomVariateGenerator repair) {
	this.state = StateMachineEnum.RUNNING;
	this.setSshConnection(sshConnection);
	this.randF = failure;
	this.randR = repair;
	this.machine = new SubnetCheck(sshConnection.getHost());
    }

    public StateMachineEnum getState() {
	return state;
    }

    public void setState(StateMachineEnum state) {
	this.state = state;
    }

    public MyTimer getTimer() {
	return timer;
    }

    public void setTimer(MyTimer timer) {
	this.timer = timer;
    }

    public MySshConnector getSshConnection() {
	return sshConnection;
    }

    public void setSshConnection(MySshConnector sshConnection) {
	this.sshConnection = sshConnection;
    }

    public RandomVariateGenerator getRandF() {
	return randF;
    }

    public void setRandF(RandomVariateGenerator randF) {
	this.randF = randF;
    }

    public RandomVariateGenerator getRandR() {
	return randR;
    }

    public void setRandR(RandomVariateGenerator randR) {
	this.randR = randR;
    }

    /**
     * Generates a Random Number (double) according to the distribution
     * provided.
     * 
     * @return the truncated number to be used as a random failure time.
     */
    public int generateRandomFailureTime() {
	double b = this.randF.generateRandomNumber();
	int c = (int) b;
	return c;
    }

    /**
     * Generates a Random Number (double) according to the distribution
     * provided.
     * 
     * @return the truncated number to be used as a random repair time.
     */
    public int generateRandomRepairTime() {
	double b = this.randR.generateRandomNumber();
	int c = (int) b;
	return c;
    }

    /**
     * Checks if the Hardware is dead or alive
     * 
     * @return true if the machine is running, otherwise returns false
     */
    public boolean isAlive() {
	try {
	    return machine.isAlive();
	} catch (IOException e) {
	    System.err.println("Error in isAlive method.");
	    e.printStackTrace();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	return false;
    }

    /**
     * Shuts-down the Hardware
     */
    public void stopHardware(int seconds) {
	//FIXME - REIMPLEMENTAR considerando a dependencia!!
	this.getSshConnection().setCommand("rtcwake -m disk -s " + seconds);
	this.getSshConnection().sshCommand();
    }

    /**
     * Starts the hardware
     * 
     */
    public void startHardware() {
	//No need for a start method because the hardware will wake automatically - RTCWAKE
    }

    /**
     * State Machine
     * 
     * @throws InterruptedException
     */
    @SuppressWarnings("incomplete-switch")
    public void runHardwareStateMachine() throws InterruptedException {
	switch (this.getState()) {
	case RUNNING:
	    if (this.isAlive()) {
	    	int waitingTime = this.generateRandomFailureTime();
			this.setTimer(new MyTimer(waitingTime));
	    	WriteFile.logger("\n ---> The service up!" , "Hardware_log.txt");
			WriteFile.logger("Started. Hardware: " + this.getSshConnection().getHost(),"Hardware_log.txt");
			WriteFile.logger("Cycle IP: = 192.168.0.151 ", "Hardware_log.txt");
			WriteFile.logger("Front_On=1 Time = "+ waitingTime, "Hardware_log.txt");
			WriteFile.logger("Generated Failure Time: " + waitingTime, "Hardware_log.txt");
			WriteFile.logger(new Date().toString(), "Hardware_log.txt");
			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
		    } else {
			// Sleep again until the Hardware starts
		    }
		    break;

	case TIMER_INJECT_FAILURE:
	    if (this.getTimer().isExpired()) {
			int waitingTimeR = this.generateRandomRepairTime();
			this.stopHardware(waitingTimeR);
			WriteFile.logger("\n ---> The service down!" , "Hardware_log.txt");
			WriteFile.logger("Failed. Hardware: " + this.getSshConnection().getHost(),"Hardware_log.txt");
			WriteFile.logger("Cycle IP: = 192.168.0.151 ", "Hardware_log.txt");
			WriteFile.logger("Front_Off=0 Time = "+ waitingTimeR + 3, "Hardware_log.txt");
			WriteFile.logger("Generated Failure Time: " + waitingTimeR, "Hardware_log.txt");
			WriteFile.logger(new Date().toString(), "Hardware_log.txt");
			Thread.sleep(30000); //30 seconds
			this.setState(StateMachineEnum.FAILED);
		    } else {
			// Sleep again until the timer expires
		    }
		    break;

	case FAILED:
	    if (this.isAlive()) {
	    	int waitingTimeR = this.generateRandomRepairTime();
			WriteFile.logger("\n ---> The service failed!" , "Node_log.txt");
			WriteFile.logger("Failed. Hardware: " + this.getSshConnection().getHost(), "Node_log.txt");
			WriteFile.logger("Cycle IP: = 192.168.0.152 ", "Hardware_log.txt");
			WriteFile.logger("Node_On=1 Time = "+ waitingTimeR, "Hardware_log.txt");
			WriteFile.logger("Generated Failure Time: " + waitingTimeR, "Hardware_log.txt");
			WriteFile.logger(new Date().toString(), "Hardware_log.txt");
			this.setState(StateMachineEnum.RUNNING);
		    } else {
			// // Sleep again until the Hardware starts
		    }
		    break;

	}
  }
}
