package br.ufpe.cin.controller;

import java.io.IOException;
import java.util.Date;

import javax.swing.JOptionPane;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.enums.StateMachineEnum;
import br.ufpe.cin.support.MySshConnector;
import br.ufpe.cin.support.MyTimer;
import br.ufpe.cin.support.WriteFile;

public class CLCController {

    private StateMachineEnum state;
    private MyTimer timer;
    private MySshConnector sshConnection;
    private RandomVariateGenerator randF;
    private RandomVariateGenerator randR;

    public CLCController(MySshConnector sshConnection, RandomVariateGenerator failure, RandomVariateGenerator repair) {
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
     * Generates a Random Number (double) aCLCording to the distribution
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
     * Generates a Random Number (double) aCLCording to the distribution
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
     * @return true if the CLC is running, otherwise returns false
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
     * Shuts-down the CLC
     */
    public void stopCLC() {
	//FIXME - REIMPLEMENTAR considerando a dependencia com o hardware!
	this.getSshConnection().setCommand("service eucalyptus-cloud stop");
	this.getSshConnection().sshCommand();
    }

    /**
     * Starts the CLC
     * 
     */
    public void startCLC() {
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
    public void runCLCStateMachine() throws InterruptedException, IOException {
    	JOptionPane.showMessageDialog(null,"Front: Conexão realizada com sucesso!");
	    int i = 1;
	    while(i<=5){
		switch (this.getState()) {
		case RUNNING:
		    if (this.isAlive()) {
		    	int waitingTime = this.generateRandomFailureTime();
				//WriteFile.logger("SPN: frontOn", "Frontend_log.txt");
				this.setTimer(new MyTimer(waitingTime));
		    	WriteFile.logger("\n ---> The Front service was started!" , "Frontend_log.txt");
		    	WriteFile.logger("Status: Up" , "Frontend_log.txt");
		    	WriteFile.logger("Cloud Status: Active" , "Frontend_log.txt");
		    	WriteFile.logger("IP Number: " + this.getSshConnection(),"Frontend_log.txt");
		    	WriteFile.logger("Current status: fronOn=1 Time: " +waitingTime, "Frontend_log.txt");
				this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
				WriteFile.logger("Time: : " + new Date().toString(), "Frontend_log.txt");
				WriteFile.logger("service eucalyptus-cc start" , "Frontend_log.txt");
			    } else {
				// Sleep again until the CC starts
			    }
	
		case TIMER_INJECT_FAILURE:		
		    if (!this.getTimer().isExpired()) {
				this.stopCLC();
				int waitingTime = this.generateRandomFailureTime();
				this.setTimer(new MyTimer(waitingTime));
				WriteFile.logger("\n ---> The Front service stoped!" , "Frontend_log.txt");
				WriteFile.logger("Status: Down" , "Frontend_log.txt");
		    	WriteFile.logger("Cloud Status: Inactive" , "Frontend_log.txt");
		    	WriteFile.logger("IP Number: " + "192.168.0.151","Frontend_log.txt");
		    	WriteFile.logger("Current status: frontFail=0", "Frontend_log.txt");
		    	WriteFile.logger("SPN: FrontOff", "Frontend_log.txt");
				WriteFile.logger("Time: " + waitingTime, "Frontend_log.txt");
				WriteFile.logger("Date: " + new Date().toString(), "Frontend_log.txt");
				WriteFile.logger("service eucalyptus-cloud stop" , "Frontend_log.txt");
				//Thread.sleep(20000); // 30 seconds sleep for the CC to actually stops
				this.setState(StateMachineEnum.FAILED);
			    } else {
				// Sleep again until the timer expires
			    }
		    
		    
		    if (!this.getTimer().isExpired()) {
				this.stopCLC();
				int waitingTime = this.generateRandomFailureTime();
				this.setTimer(new MyTimer(waitingTime));
				WriteFile.logger("\n ---> The inhibitor was triggered!" , "Frontend_log.txt");
				this.setState(StateMachineEnum.FAILED);
			    } else {
			    }
			    break;
	
		case FAILED:
		    if (!this.isAlive()) {
				int waitingTime = this.generateRandomFailureTime();
				this.setTimer(new MyTimer(waitingTime));
				this.setState(StateMachineEnum.TIMER_REPAIR);
				WriteFile.logger("\n ---> The Front service failed!" , "Frontend_log.txt");
				WriteFile.logger("Status: Down" , "Frontend_log.txt");
		    	WriteFile.logger("Cloud Status: Inactive" , "Frontend_log.txt");
		    	WriteFile.logger("IP Number: " + "192.168.0.151","Frontend_log.txt");
		    	WriteFile.logger("Current status: VmOff", "Frontend_log.txt");
				WriteFile.logger("Time: " + waitingTime, "Frontend_log.txt");
				WriteFile.logger("Date: " + new Date().toString(), "Frontend_log.txt");
			    } else {
				// // Sleep again until the CC stops
			    }
			    break;
	
		case TIMER_REPAIR:
			 if (!this.getTimer().isExpired()) {
	 			this.startCLC();
	 			int waitingTimeR = this.generateRandomRepairTime();
	 			this.setTimer(new MyTimer(waitingTimeR));
	 			WriteFile.logger("\n ---> The inhibitor was triggered!" , "Frontend_log.txt");
	 			this.setState(StateMachineEnum.RUNNING);
	 		    } else {
	 		    }
			 
		    if (!this.getTimer().isExpired()) {
				this.startCLC();
				int waitingTimeR = this.generateRandomRepairTime();
				this.setTimer(new MyTimer(waitingTimeR));
				WriteFile.logger("\n ---> The Front service repaired!" , "Frontend_log.txt");
				WriteFile.logger("Status: Up" , "Frontend_log.txt");
		    	WriteFile.logger("Cloud Status: Active" , "Frontend_log.txt");
		    	WriteFile.logger("IP Number: " + "192.168.0.151","Frontend_log.txt");
		    	WriteFile.logger("Current status: frontOn=1", "Frontend_log.txt");
				WriteFile.logger("Time: " + waitingTimeR, "Frontend_log.txt");
				WriteFile.logger("Date: " + new Date().toString(), "Frontend_log.txt");
				WriteFile.logger("service eucalyptus-cloud start" , "Frontend_log.txt");
				this.setState(StateMachineEnum.RUNNING);
			    } else {
				// Wait for the timer to expire
			    }
		 
		    System.out.println("Contador : "+i);
		    
		    if(i == 1){
		    	i+=1;
		    	MySshConnector con1 = new MySshConnector("root", "clouds", "192.168.0.152");
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
				CCController cc1 = new CCController(con1, randF, randR);
				cc1.runNCStateMachine();
				
		    }
		    
		    if(i == 2){
		    	i+=1;
		    	MySshConnector con1 = new MySshConnector("root", "clouds", "192.168.0.153");
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
				VMController VM = new VMController(con1, randF, randR);
				VM.runVMStateMachine();
			
		    }
		    
		    if(i == 3){
		    	i+=1;
		    	MySshConnector con1 = new MySshConnector("root", "clouds", "192.168.0.155");
	
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
			
				CCController2 cc2 = new CCController2(con1, randF, randR);
				cc2.runNCStateMachine2();
				System.out.println("Entrou na MyClass3");
		    }
		    
		    if(i == 4){
		    	CCController4 myclass =  new CCController4(sshConnection, randF, randF);
		        myclass.runNCStateMachine4(); //Chama nova classe para percorrer a rede SPN
				System.out.println("Entrou na MyClass4");
		    }
		    
		    if(i == 5){
		    	CLCController myclass =  new CLCController(sshConnection, randF, randF);
				myclass.runCLCStateMachine();//Chama nova classe para percorrer a rede SPN			
				System.out.println("Entrou na MyClass5 #CLC");
		    }
			    break;
			    
		  }  		    
       }
	}
}