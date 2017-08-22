package VM5;

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
public class VMController5 {

    private StateMachineEnum state;
    private MyTimer timer;
    private MySshConnector sshConnection;
    private RandomVariateGenerator randF;
    private RandomVariateGenerator randR;
    private SubnetCheck machine;

    public VMController5(MySshConnector sshConnection, RandomVariateGenerator failure,
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
    	this.getSshConnection().setCommand("service eucalyptus-cloud stop" + seconds);
    	this.getSshConnection().sshCommand();
    }

    /**
     * Starts the hardware
     * 
     */
    public void startHardware() {
    	this.getSshConnection().setCommand("service eucalyptus-cloud start");
    	this.getSshConnection().sshCommand();
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
	    	WriteFile.logger("\n ---> O Serviço foi iniciado!" , "VM5Controller_log.txt");
			WriteFile.logger("Started. VM: " + "192.168.0.174","VM5Controller_log.txt");
			WriteFile.logger(new Date().toString(), "VM5Controller_log.txt");
			int waitingTime = this.generateRandomFailureTime();
			this.setTimer(new MyTimer(waitingTime));
			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
			WriteFile.logger("Tempo de falha gerado: " + waitingTime, "VM5Controller_log.txt");
			WriteFile.logger(new Date().toString(), "VM5Controller_log.txt");
		    } else {
			// Sleep again until the VM starts
		    }
		    break;

	case TIMER_INJECT_FAILURE:
	    if (this.getTimer().isExpired()) {
			int waitingTimeR = this.generateRandomRepairTime();
			this.stopHardware(waitingTimeR);
			WriteFile.logger("\n ---> O Serviço parou!" , "VM5Controller_log.txt");
			WriteFile.logger("Failed. VM: " + "192.168.0.174","VM5Controller_log.txt");
			WriteFile.logger(new Date().toString(), "VM5Controller_log.txt");
			WriteFile.logger("Tempo de reparo Gerado: " + waitingTimeR, "VM5Controller_log.txt");
			WriteFile.logger(new Date().toString(), "VM5Controller_log.txt");
			Thread.sleep(30000); //30 seconds
			this.setState(StateMachineEnum.FAILED);
		    } else {
			// Sleep again until the timer expires
		    }
		    break;

	case FAILED:
	    if (this.isAlive()) {
			this.setState(StateMachineEnum.RUNNING);
		    } else {
			// // Sleep again until the VM starts
		    }
		    break;

	}
  }
}
