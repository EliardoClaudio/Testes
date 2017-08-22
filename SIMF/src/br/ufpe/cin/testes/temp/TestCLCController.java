package br.ufpe.cin.testes.temp;

import java.io.IOException;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.controller.CLCController;
import br.ufpe.cin.support.MySshConnector;
import package_tokengametest.EventsTestNew;
import package_tokengametest.TokenGameTestNew;

public class TestCLCController {
	
	public static void Chamada(String login, String senha, String arq) throws InterruptedException, IOException {
		//String newlogin = "192.168.0.152";
		try{
			System.out.println("Testing status of the Cloud Controller Front...");
			if (arq.equals("")){ //Checa se o usuário selecionou um arquivo token.
				//login = newlogin;
				//SSH credentials to the CLC Machine
				MySshConnector con1 = new MySshConnector("root", senha, login);
	
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 3 and 20 minutes, mean value is 10 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				// randR - Random Repair Time between 1 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
	
				CLCController clc1 = new CLCController(con1, randF, randR);
				clc1.runCLCStateMachine();
					
			}
			else{
				EventsTestNew.EventTest(arq);
				TokenGameTestNew.TokenGame(arq);
				
				//SSH credentials to the CLC Machine
				MySshConnector con1 = new MySshConnector("root", senha, login);
	
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 3 and 20 minutes, mean value is 10 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				// randR - Random Repair Time between 1 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
	
				CLCController clc1 = new CLCController(con1, randF, randR);
				clc1.runCLCStateMachine();
				
			}

		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}