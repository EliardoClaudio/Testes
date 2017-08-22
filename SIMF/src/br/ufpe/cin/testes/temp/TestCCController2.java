package br.ufpe.cin.testes.temp;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.controller.CCController2;
import br.ufpe.cin.support.MySshConnector;
import package_tokengametest.EventsTestNew;
import package_tokengametest.TokenGameTestNew;

public class TestCCController2 {
	
	public static void Chamada(String login, String senha, String arq) throws InterruptedException, IOException {
		
		try{
			System.out.println("Testing Machine Controller State...");
			if (arq.equals("")){ //Checa se o usuário selecionou um arquivo token.
				
				//SSH credentials to the Cluster Controller Machine
				MySshConnector con1 = new MySshConnector("root", senha, login);
			
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 2 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				// randR - Random Repair Time between 1 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
			
				CCController2 cc2 = new CCController2(con1, randF, randR);
				
				int i = 0;
				while (true) {
				    cc2.runNCStateMachine2();
					Thread.sleep(100);
					if (i==0){
						JOptionPane.showMessageDialog(null,"Tentativa de Conexão realizada com sucesso!");
						i = i + 1;		 
				    }
				}
			}	
			else{
				EventsTestNew.EventTest(arq);
				TokenGameTestNew.TokenGame(arq);
				
				//SSH credentials to the Cluster Controller Machine
				MySshConnector con1 = new MySshConnector("root", senha, login);
			
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 2 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				// randR - Random Repair Time between 1 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
			
				CCController2 cc2 = new CCController2(con1, randF, randR);
				
				int i = 0;
				while (true) {
				    cc2.runNCStateMachine2();
					Thread.sleep(100);
					if (i==0){
						JOptionPane.showMessageDialog(null,"Tentativa de Conexão realizada com sucesso!");
						i = i + 1;			 
				    }
				}
			}
			
		}catch (InterruptedException e2) {
			System.out.println("Tratamento de Erro!");
			e2.printStackTrace();
		}
	}
}