package br.ufpe.cin.controller;

import java.io.IOException;
import java.util.Date;

import javax.swing.JOptionPane;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.enums.StateMachineEnum;
import br.ufpe.cin.support.MySshConnector;
import br.ufpe.cin.support.MyTimer;
import br.ufpe.cin.support.WriteFile;

/**
 * This class implements a CC controller
 * 
 * @author Eliardo Cláudio
 *
 */
public class CCController {

    private StateMachineEnum state;
    private MyTimer timer;
    private MySshConnector sshConnection;
    private RandomVariateGenerator randF;
    private RandomVariateGenerator randR;

    public CCController(MySshConnector sshConnection, RandomVariateGenerator failure, RandomVariateGenerator repair) {
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
     * Checks if the Node Controller is dead or alive
     * 
     * @return true if the CC is running, otherwise returns false
     */
    public boolean isAlive() {
	this.getSshConnection().setCommand("service eucalyptus-nc status | awk '{print $3}'");
	String aux = this.getSshConnection().sshCommand().trim();
	//System.out.println("entrou no metodo isAlive do CC e retornou: " + aux);
	if (aux.equals("running")) {
	    return true;
	} else
	    return false;
    }

    /**
     * Shuts-down the CC
     */
    public void stopCC() {
	//FIXME - REIMPLEMENTAR vendo a dependencia!!
	this.getSshConnection().setCommand("service eucalyptus-nc stop");
	//WriteFile.logger("service eucalyptus-cc stop" , "Node_log.txt");
	this.getSshConnection().sshCommand();
    }

    /**
     * Starts the CC
     * 
     */
    public void startCC() {
	//FIXME - REIMPLEMENTAR vendo a dependencia!!
	this.getSshConnection().setCommand("service eucalyptus-nc start");
	//WriteFile.logger("service eucalyptus-cc start" , "Node_log.txt");
	this.getSshConnection().sshCommand();
    }

    /**
     * State Machine
     * 
     * @throws InterruptedException
     */
    public void runNCStateMachine() throws InterruptedException {
    	JOptionPane.showMessageDialog(null,"Node: Conexão realizada com sucesso!");
    	int i = 0;
    	while(i<=5){
    	switch (this.getState()) {
    	case RUNNING:
    	    if (this.isAlive()) {
    	    	int waitingTime = this.generateRandomFailureTime();
    			//WriteFile.logger("SPN: frontOn", "Node_log.txt");
    			this.setTimer(new MyTimer(waitingTime));
    	    	WriteFile.logger("\n ---> The Front service was started!" , "Node_log.txt");
    	    	WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.151","Node_log.txt");
    	    	WriteFile.logger("Current status: fronOn=1 Time: " +waitingTime, "Node_log.txt");
    			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-cc start" , "Node_log.txt");
    		    } else {
    			// Sleep again until the CC starts
    		    }
    	    
    	    if (this.isAlive()) {
    	    	int waitingTime = this.generateRandomFailureTime();
    			//WriteFile.logger("SPN: NodeOn", "Node_log.txt");
    			this.setTimer(new MyTimer(waitingTime));
    	    	WriteFile.logger("\n ---> The Node service was started!" , "Node_log.txt");
    	    	WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.152","Node_log.txt");
    	    	WriteFile.logger("Current status: NodeOn=1 Time: " +waitingTime, "Node_log.txt");
    			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc start" , "Node_log.txt");
    		    } else {
    		    }
    	    
    	    if (this.isAlive()) {
    	    	int waitingTime = this.generateRandomFailureTime();
    			//WriteFile.logger("SPN: Node2On", "Node_log.txt");
    			this.setTimer(new MyTimer(waitingTime));
    	    	WriteFile.logger("\n ---> The Node2 service was started!" , "Node_log.txt");
    	    	WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.153","Node_log.txt");
    	    	WriteFile.logger("Current status: node2On=1 Time: " +waitingTime, "Node_log.txt");
    			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc start" , "Node_log.txt");
    		    } else {
    		    }
    	    
    	    if (this.isAlive()) {
    	    	int waitingTime = this.generateRandomFailureTime();
    			//WriteFile.logger("SPN: Node3On", "Node_log.txt");
    			this.setTimer(new MyTimer(waitingTime));
    	    	WriteFile.logger("\n ---> The Node3 service was started!" , "Node_log.txt");
    	    	WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.155","Node_log.txt");
    	    	WriteFile.logger("Current status: node3On=1 Time: " +waitingTime, "Node_log.txt");
    			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc start" , "Node_log.txt");
    		    } else {
    		    }
    	    
    	    if (this.isAlive()) {
    	    	int waitingTime = this.generateRandomFailureTime();
    			//WriteFile.logger("SPN: Node4On", "Node_log.txt");
    			this.setTimer(new MyTimer(waitingTime));
    	    	WriteFile.logger("\n ---> The Node4 service was started!" , "Node_log.txt");
    	    	WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.156","Node_log.txt");
    	    	WriteFile.logger("Current status: node4On=1 Time: " +waitingTime, "Node_log.txt");
    			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc start" , "Node_log.txt");
    		    } else {
    		    }
    	    
    	    if (this.isAlive()) {
    	    	int waitingTime = this.generateRandomFailureTime();
    			//WriteFile.logger("SPN: VMOn", "Node_log.txt");
    			this.setTimer(new MyTimer(waitingTime));
    	    	WriteFile.logger("\n ---> The VM service was started!" , "Node_log.txt");
    	    	WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("ID VM: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: vmOn=1 Time: " +waitingTime, "Node_log.txt");
    			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-run-instances -k teste -t m1.small emi-D89C3AA2 | awk '{print $2}' | grep i-" , "Node_log.txt");
    		    } else {
    		    }
    	    
    	    if (this.isAlive()) {
    	    	WriteFile.logger("\n ---> The VM2 service was started!" , "Node_log.txt");
    	    	WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("ID VM2: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: vm2On=1", "Node_log.txt");
    			int waitingTime = this.generateRandomFailureTime();
    			WriteFile.logger("SPN: VM2On", "Node_log.txt");
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-run-instances -k teste -t m1.small emi-D89C3AA2 | awk '{print $2}' | grep i-" , "Node_log.txt");
    		    } else {
    		    }
    	    
    	    if (this.isAlive()) {
    	    	WriteFile.logger("\n ---> The VM3 service was started!" , "Node_log.txt");
    	    	WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("ID VM3: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: vm3On=1", "Node_log.txt");
    			int waitingTime = this.generateRandomFailureTime();
    			WriteFile.logger("SPN: VM3On", "Node_log.txt");
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-run-instances -k teste -t m1.small emi-D89C3AA2 | awk '{print $2}' | grep i-" , "Node_log.txt");
    		    } else {
    		    }
    	    
    	    if (this.isAlive()) {
    	    	WriteFile.logger("\n ---> The VM4 service was started!" , "Node_log.txt");
    	    	WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("ID VM4: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: node4On=1", "Node_log.txt");
    			int waitingTime = this.generateRandomFailureTime();
    			WriteFile.logger("SPN: VM4On", "Node_log.txt");
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-run-instances -k teste -t m1.small emi-D89C3AA2 | awk '{print $2}' | grep i-" , "Node_log.txt");
    		    } else {
    		    }

    	case TIMER_INJECT_FAILURE:
    		if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The inhibitor was triggered!" , "Node_log.txt");
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    		    }
    		
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The Front service stoped!" , "Node_log.txt");
    			WriteFile.logger("Status: Down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.151","Node_log.txt");
    	    	WriteFile.logger("Current status: frontFail=0", "Node_log.txt");
    	    	WriteFile.logger("SPN: FrontOff", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-cloud stop" , "Node_log.txt");
    			//Thread.sleep(20000); // 30 seconds sleep for the CC to actually stops
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    			// Sleep again until the timer expires
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The Node service stoped!" , "Node_log.txt");
    			WriteFile.logger("Status: Down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.152","Node_log.txt");
    	    	WriteFile.logger("Current status: nodeFail=0", "Node_log.txt");
    	    	WriteFile.logger("SPN: NodeOff", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Time: : " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc stop" , "Node_log.txt");
    			//Thread.sleep(20000); 
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    			// Sleep again until the timer expires
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The Node2 service stoped!" , "Node_log.txt");
    			WriteFile.logger("Status: Down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.153","Node_log.txt");
    	    	WriteFile.logger("Current status: node2Fail=0", "Node_log.txt");
    	    	WriteFile.logger("SPN: Node2Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc stop" , "Node_log.txt");
    			//Thread.sleep(20000); 
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    			// Sleep again until the timer expires
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The Node3 service stoped!" , "Node_log.txt");
    			WriteFile.logger("Status: Down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.155","Node_log.txt");
    	    	WriteFile.logger("Current status: node3Fail=0", "Node_log.txt");
    	    	WriteFile.logger("SPN: Node3Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc stop" , "Node_log.txt");
    			//Thread.sleep(20000); 
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    			// Sleep again until the timer expires
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The Node4 service stoped!" , "Node_log.txt");
    			WriteFile.logger("Status: Down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.156","Node_log.txt");
    	    	WriteFile.logger("Current status: node2Fail=0", "Node_log.txt");
    	    	WriteFile.logger("SPN: Node4Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc stop" , "Node_log.txt");
    			//Thread.sleep(20000); 
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    			// Sleep again until the timer expires
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The VM service stoped!" , "Node_log.txt");
    			WriteFile.logger("Status: Down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("ID VM: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: vmFail", "Node_log.txt");
    	    	WriteFile.logger("SPN: VMOff", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-terminate-instances" , "Node_log.txt");
    			//Thread.sleep(20000); 
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    			// Sleep again until the timer expires
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The VM2 service stoped!" , "Node_log.txt");
    			WriteFile.logger("Status: Down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("ID VM2: " + "emi-D89C3AA","Node_log.txt");
    	    	WriteFile.logger("Current status: vm2Fail=0", "Node_log.txt");
    	    	WriteFile.logger("SPN: VM2Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-terminate-instances" , "Node_log.txt");
    			//Thread.sleep(20000); // 30 seconds sleep for the CC to actually stops
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    			// Sleep again until the timer expires
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The inhibitor was triggered!" , "Node_log.txt");
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The VM3 service stoped!" , "Node_log.txt");
    			WriteFile.logger("Status: Down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("ID VM3: " + "emi-D89C3AA","Node_log.txt");
    	    	WriteFile.logger("Current status: Node3On=1", "Node_log.txt");
    	    	WriteFile.logger("SPN: VM3Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-terminate-instances" , "Node_log.txt");
    			//Thread.sleep(20000); // 30 seconds sleep for the CC to actually stops
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    			// Sleep again until the timer expires
    		    }
    	    
    	    if (this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The VM4 service stoped!" , "Node_log.txt");
    			WriteFile.logger("Status: Down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("ID VM4: " + "emi-D89C3AA","Node_log.txt");
    	    	WriteFile.logger("Current status: Node4Off=0", "Node_log.txt");
    	    	WriteFile.logger("SPN: VM4Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-terminate-instances" , "Node_log.txt");
    			Thread.sleep(10000); // 30 seconds sleep for the CC to actually stops
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    			// Sleep again until the timer expires
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The inhibitor was triggered!" , "Node_log.txt");
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    		    }
    		    break;

    	case FAILED:
    	    if (!this.isAlive()) {
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_REPAIR);
    			WriteFile.logger("\n ---> The Front service failed!" , "Node_log.txt");
    			WriteFile.logger("Status: down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.151","Node_log.txt");
    	    	WriteFile.logger("Current status: VmOff", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    		    } else {
    			// // Sleep again until the CC stops
    		    }
    	    
    	    if (!this.isAlive()) {
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_REPAIR);
    			WriteFile.logger("\n ---> The Node service failed!" , "Node_log.txt");
    			WriteFile.logger("Status: down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.152","Node_log.txt");
    	    	WriteFile.logger("Current status: Vm2Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    		    } else {
    			// // Sleep again until the CC stops
    		    }
    	    
    	    if (!this.isAlive()) {
    			this.stopCC();
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			WriteFile.logger("\n ---> The inhibitor was triggered!" , "Node_log.txt");
    			this.setState(StateMachineEnum.FAILED);
    		    } else {
    		    }
    	    
    	    
    	    if (!this.isAlive()) {
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_REPAIR);
    			WriteFile.logger("\n ---> The Node2 service failed!" , "Node_log.txt");
    			WriteFile.logger("Status: down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.153","Node_log.txt");
    	    	WriteFile.logger("Current status: Vm3Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    		    } else {
    			// // Sleep again until the CC stops
    		    }
    	    
    	    if (!this.isAlive()) {
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_REPAIR);
    			WriteFile.logger("\n ---> The Node3 service failed!" , "Node_log.txt");
    			WriteFile.logger("Status: down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.155","Node_log.txt");
    	    	WriteFile.logger("Current status: Vm3Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    		    } else {
    			// // Sleep again until the CC stops
    		    }
    	    
    	    if (!this.isAlive()) {
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_REPAIR);
    			WriteFile.logger("\n ---> The Node4 service failed!" , "Node_log.txt");
    			WriteFile.logger("Status: down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.156","Node_log.txt");
    	    	WriteFile.logger("Current status: Vm4Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    		    } else {
    			// // Sleep again until the CC stops
    		    }
    	    
    	    if (!this.isAlive()) {
    			int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_REPAIR);
    			WriteFile.logger("\n ---> The VM service failed!" , "Node_log.txt");
    			WriteFile.logger("Status: down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("ID VM: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: Vm4Off", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    		    } else {
    			// // Sleep again until the CC stops
    		    }
    	    
    	    if (!this.isAlive()) {
    	    	int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_REPAIR);
    			WriteFile.logger("\n ---> The VM2 service failed!" , "Node_log.txt");
    			WriteFile.logger("Status: down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("ID VM2: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: Node3On=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    		    } else {
    			// // Sleep again until the CC stops
    		    }
    	    
    	    if (!this.isAlive()) {
    	    	int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_REPAIR);
    			WriteFile.logger("\n ---> The VM3 service failed!" , "Node_log.txt");
    			WriteFile.logger("Status: down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("ID VM3: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: Node3On=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    		    } else {
    			// // Sleep again until the CC stops
    		    }
    	    
    	    if (!this.isAlive()) {
    	    	int waitingTime = this.generateRandomFailureTime();
    			this.setTimer(new MyTimer(waitingTime));
    			this.setState(StateMachineEnum.TIMER_REPAIR);
    			WriteFile.logger("\n ---> The VM4 service failed!" , "Node_log.txt");
    			WriteFile.logger("Status: down" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Inactive" , "Node_log.txt");
    	    	WriteFile.logger("ID VM4: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: Node3Off=0", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTime, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    		    } else {
    			// // Sleep again until the CC stops
    		    }
    		    break;

    	case TIMER_REPAIR:
    		 if (!this.getTimer().isExpired()) {
     			this.startCC();
     			int waitingTimeR = this.generateRandomRepairTime();
     			this.setTimer(new MyTimer(waitingTimeR));
     			WriteFile.logger("\n ---> The inhibitor was triggered!" , "Node_log.txt");
     			this.setState(StateMachineEnum.RUNNING);
     		    } else {
     		    }
    		 
    	    if (!this.getTimer().isExpired()) {
    			this.startCC();
    			int waitingTimeR = this.generateRandomRepairTime();
    			this.setTimer(new MyTimer(waitingTimeR));
    			WriteFile.logger("\n ---> The Front service repaired!" , "Node_log.txt");
    			WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.151","Node_log.txt");
    	    	WriteFile.logger("Current status: frontOn=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTimeR, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-cloud start" , "Node_log.txt");
    			this.setState(StateMachineEnum.RUNNING);
    		    } else {
    			// Wait for the timer to expire
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.startCC();
    			int waitingTimeR = this.generateRandomRepairTime();
    			this.setTimer(new MyTimer(waitingTimeR));
    			WriteFile.logger("\n ---> The Node service repaired!" , "Node_log.txt");
    			WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.152","Node_log.txt");
    	    	WriteFile.logger("Current status: nodeOn=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTimeR, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc start" , "Node_log.txt");
    			this.setState(StateMachineEnum.RUNNING);
    		    } else {
    			// Wait for the timer to expire
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.startCC();
    			int waitingTimeR = this.generateRandomRepairTime();
    			this.setTimer(new MyTimer(waitingTimeR));
    			WriteFile.logger("\n ---> The Node2 service repaired!" , "Node_log.txt");
    			WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.153","Node_log.txt");
    	    	WriteFile.logger("Current status: node2On=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTimeR, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc start" , "Node_log.txt");
    			this.setState(StateMachineEnum.RUNNING);
    		    } else {
    			// Wait for the timer to expire
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.startCC();
    			int waitingTimeR = this.generateRandomRepairTime();
    			this.setTimer(new MyTimer(waitingTimeR));
    			WriteFile.logger("\n ---> The Node3 service repaired!" , "Node_log.txt");
    			WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.155","Node_log.txt");
    	    	WriteFile.logger("Current status: node3On=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTimeR, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc start" , "Node_log.txt");
    			this.setState(StateMachineEnum.RUNNING);
    		    } else {
    			// Wait for the timer to expire
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.startCC();
    			int waitingTimeR = this.generateRandomRepairTime();
    			this.setTimer(new MyTimer(waitingTimeR));
    			WriteFile.logger("\n ---> The Node4 service repaired!" , "Node_log.txt");
    			WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("IP Number: " + "192.168.0.156","Node_log.txt");
    	    	WriteFile.logger("Current status: node4On=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTimeR, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("service eucalyptus-nc start" , "Node_log.txt");
    			this.setState(StateMachineEnum.RUNNING);
    		    } else {
    			// Wait for the timer to expire
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.startCC();
    			int waitingTimeR = this.generateRandomRepairTime();
    			this.setTimer(new MyTimer(waitingTimeR));
    			WriteFile.logger("\n ---> The VM service repaired!" , "Node_log.txt");
    			WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("ID VM: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: vmOn=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTimeR, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-describe-instances" , "Node_log.txt");
    			this.setState(StateMachineEnum.RUNNING);
    		    } else {
    			// Wait for the timer to expire
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.startCC();
    			int waitingTimeR = this.generateRandomRepairTime();
    			this.setTimer(new MyTimer(waitingTimeR));
    			WriteFile.logger("\n ---> The VM2 service repaired!" , "Node_log.txt");
    			WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("ID VM2: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: vm2On=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTimeR, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-describe-instances" , "Node_log.txt");
    			this.setState(StateMachineEnum.RUNNING);
    		    } else {
    			// Wait for the timer to expire
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.startCC();
    			int waitingTimeR = this.generateRandomRepairTime();
    			this.setTimer(new MyTimer(waitingTimeR));
    			WriteFile.logger("\n ---> The VM3 service repaired!" , "Node_log.txt");
    			WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("ID VM3: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: vm3On=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTimeR, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-describe-instances" , "Node_log.txt");
    			this.setState(StateMachineEnum.RUNNING);
    		    } else {
    			// Wait for the timer to expire
    		    }   
    	    
    	    if (!this.getTimer().isExpired()) {
    			this.startCC();
    			int waitingTimeR = this.generateRandomRepairTime();
    			this.setTimer(new MyTimer(waitingTimeR));
    			WriteFile.logger("\n ---> The VM4 service repaired!" , "Node_log.txt");
    			WriteFile.logger("Status: Up" , "Node_log.txt");
    	    	WriteFile.logger("Cloud Status: Active" , "Node_log.txt");
    	    	WriteFile.logger("ID VM4: " + "emi-D89C3AA2","Node_log.txt");
    	    	WriteFile.logger("Current status: Vm4On=1", "Node_log.txt");
    			WriteFile.logger("Time: " + waitingTimeR, "Node_log.txt");
    			WriteFile.logger("Date: " + new Date().toString(), "Node_log.txt");
    			WriteFile.logger("euca-describe-instances" , "Node_log.txt");
    			Thread.sleep(10000); // 30 seconds sleep for the CLC to actually starts
    			this.setState(StateMachineEnum.RUNNING);
    		    } else {
    		    }
    	    
    	    if (!this.getTimer().isExpired()) {
     			this.startCC();
     			int waitingTimeR = this.generateRandomRepairTime();
     			this.setTimer(new MyTimer(waitingTimeR));
     			WriteFile.logger("\n ---> The inhibitor was triggered!" , "Node_log.txt");
     			this.setState(StateMachineEnum.RUNNING);
     		    } else {
     		    }
    	    i+=1;
    	    System.out.println("Contador é: "+i);
    	    
    	    if(i == 1){
    	    	VMController myclass =  new VMController(sshConnection, randF, randF);
    	         try {
					myclass.runVMStateMachine(); //Chama nova classe para percorrer a rede SPN
					System.out.println("Entrou na MyClass");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    }
    	    
    	    if(i == 2){
    	    	CCController myclass2 =  new CCController(sshConnection, randF, randF);
    	        myclass2.runNCStateMachine(); //Chama nova classe para percorrer a rede SPN
				System.out.println("Entrou na MyClass2");
    	    }
    	    
    	    if(i == 3){
    	    	CCController2 myclass3 =  new CCController2(sshConnection, randF, randF);
    	        myclass3.runNCStateMachine2(); //Chama nova classe para percorrer a rede SPN
				System.out.println("Entrou na MyClass3");
    	    }
    	    
    	    if(i == 4){
    	    	CCController4 myclass4 =  new CCController4(sshConnection, randF, randF);
    	        myclass4.runNCStateMachine4(); //Chama nova classe para percorrer a rede SPN
				System.out.println("Entrou na MyClass4");
    	    }
    	    
    	    if(i == 5){
    	    	CLCController myclass5 =  new CLCController(sshConnection, randF, randF);
    	        try {
					myclass5.runCLCStateMachine();//Chama nova classe para percorrer a rede SPN
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				System.out.println("Entrou na MyClass5 #CLC");
    	    }
    		    break;
    		    
    	}  		    
      }
    }
 }