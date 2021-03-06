package VM2;

import java.io.IOException;
import java.util.Date;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.enums.StateMachineEnum;
import br.ufpe.cin.support.MySshConnector;
import br.ufpe.cin.support.MyTimer;
import br.ufpe.cin.support.WriteFile;

public class VMController2 {

    private StateMachineEnum state;
    private MyTimer timer;
    private MySshConnector sshConnection;
    private RandomVariateGenerator randF;
    private RandomVariateGenerator randR;

    public VMController2(MySshConnector sshConnection, RandomVariateGenerator failure, RandomVariateGenerator repair) {
	this.state = StateMachineEnum.RUNNING;
	this.setSshConnection(sshConnection);
	this.randF = failure;
	this.randR = repair;
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
     * Checks if the Cloud Controller is dead or alive
     * 
     * @return true if the VM is running, otherwise returns false
     */
    public boolean isAlive() {
	this.getSshConnection().setCommand(
		"curl -v --silent http://192.168.0.151:8773/services/Heartbeat 2>&1 | grep eucalyptus | awk '{print $2}'");
	String aux = this.getSshConnection().sshCommand();
	if (aux != null) {
	    aux = aux.trim();
	} else {
	    return false;
	}
	if (aux.equals("enabled=true")) {
	    return true;
	} else
	    return false;
    }

    /**
     * Shuts-down the VM
     */
    public void stopVM() {
	//FIXME - REIMPLEMENTAR considerando a dependencia com o hardware!
	this.getSshConnection().setCommand("service eucalyptus-cloud stop");
	this.getSshConnection().sshCommand();
    }

    /**
     * Starts the CLC
     * 
     */
    public void startVM() {
	//FIXME - REIMPLEMENTAR considerando a dependencia com o hardware!
	this.getSshConnection().setCommand("service eucalyptus-cloud start");
	this.getSshConnection().sshCommand();
    }

    /**
     * State Machine
     * 
     * @throws InterruptedException
     * @throws IOException 
     */
    
    public void runVMStateMachine() throws InterruptedException, IOException {
	switch (this.getState()) {
	case RUNNING:
		WriteFile.logger("\n ---> Test started on: " + new Date().toString(), "Virtual_Machine 2_log.txt");
	    if (this.isAlive()) {
		    WriteFile.logger("\n ---> The service up!" , "Virtual_Machine 2_log.txt");
		    WriteFile.logger("Started. VM: " + "192.168.0.171","Virtual_Machine 2_log.txt");
		    WriteFile.logger(new Date().toString(), "Virtual_Machine 2_log.txt");
			int waitingTime = this.generateRandomFailureTime();
			this.setTimer(new MyTimer(waitingTime));
			WriteFile.logger("CLC_On=1 Time = "+ waitingTime, "Virtual_Machine 2_log.txt");
			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
			WriteFile.logger("Generated Failure Time: " + waitingTime, "Virtual_Machine 2_log.txt");
			WriteFile.logger(new Date().toString(), "Virtual_Machine 2_log.txt");
		    } else {
			// Sleep again until the VMs starts
		    }
		    break;

	case TIMER_INJECT_FAILURE:
	    if (this.getTimer().isExpired()) {
			this.stopVM();
			int waitingTime = this.generateRandomFailureTime();
			this.setTimer(new MyTimer(waitingTime));
			WriteFile.logger("\n ---> The service down!" , "Virtual_Machine 2_log.txt");
			WriteFile.logger("Failed. VM: " + "192.168.0.171", "Virtual_Machine 2_log.txt");
			WriteFile.logger(new Date().toString(), "Virtual_Machine 2_log.txt");
			WriteFile.logger("CLC_Off=0 Time = "+ waitingTime, "Virtual_Machine 2_log.txt");
			Thread.sleep(30000); // 30 seconds sleep for the VM to actually stops
			this.setState(StateMachineEnum.FAILED);
		    } else {
			// Sleep again until the timer expires
		    }
		    break;

	case FAILED:
		    if (!this.isAlive()) {
			int waitingTime = this.generateRandomRepairTime();
			this.setTimer(new MyTimer(waitingTime));
			this.setState(StateMachineEnum.TIMER_REPAIR);
			WriteFile.logger("\n ---> The service failed!" , "Virtual_Machine 2_log.txt");
			WriteFile.logger("Node_Off=0 Time = "+ waitingTime, "Virtual_Machine 2_log.txt");
			WriteFile.logger("Failed. VM: " + "192.168.0.171", "Virtual_Machine 2_log.txt");
			WriteFile.logger("Generated Failure Time: " + waitingTime, "Virtual_Machine 2_log.txt");
			WriteFile.logger(new Date().toString(), "Virtual_Machine 2_log.txt");
		    } else {
			// // Sleep again until the VM stops
		    }
		    break;

	case TIMER_REPAIR:
	    if (this.getTimer().isExpired()) {
			this.startVM();
			int waitingTime = this.generateRandomFailureTime();
			this.setTimer(new MyTimer(waitingTime));
			WriteFile.logger("\n ---> The service was repaired!" , "Virtual_Machine 2_log.txt");
			WriteFile.logger("Node_On=1 Time = "+ waitingTime, "Virtual_Machine 2_log.txt");
			WriteFile.logger("Repair. VM: " + "192.168.0.171", "Virtual_Machine 2_log.txt");
			WriteFile.logger(new Date().toString(), "Virtual_Machine 2_log.txt");
			Thread.sleep(30000); // 30 seconds sleep for the VM to actually starts
			this.setState(StateMachineEnum.RUNNING);
		    } else {
			// Wait for the timer to expire
		    }
		    break;

	}
  }
}
