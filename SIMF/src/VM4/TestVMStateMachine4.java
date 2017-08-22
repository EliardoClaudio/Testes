package VM4;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.support.MySshConnector;
import package_tokengametest.EventsTestNew;
import package_tokengametest.TokenGameTestNew;

public class TestVMStateMachine4 {
	
	public static void Chamada4(String login, String senha, String arq) throws InterruptedException, IOException {
		String newlogin = "192.168.0.152";
		try{
			System.out.println("Testando o estado do Controlador VM2");
			if (arq.equals("")){ //Checa se o usuário selecionou um arquivo token.
				login = newlogin;
				
				//SSH credentials to the Cluster Controller Machine
				MySshConnector con1 = new MySshConnector("root", senha, login);
			
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 2 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				// randR - Random Repair Time between 1 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
			
				VMController4 VM4 = new VMController4(con1, randF, randR);
				
				int i = 0;
				while (true) {
				    VM4.runVMStateMachine();
					Thread.sleep(50);
					if (i==0){
				    	JOptionPane.showMessageDialog(null,"Tentativa de Conexão realizada com sucesso!");
						i = i + 1;		 
				    }
				}
			}	
			else{
				EventsTestNew.EventTest(arq);
				TokenGameTestNew.TokenGame(arq);
				
				login = newlogin;
				
				//SSH credentials to the Cluster Controller Machine
				MySshConnector con1 = new MySshConnector("root", senha, login);
			
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 2 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				// randR - Random Repair Time between 1 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
			
				VMController4 VM4 = new VMController4(con1, randF, randR);
				
				int i = 0;
				while (true) {
				    VM4.runVMStateMachine();
					Thread.sleep(50);
					if (i==0){
				    	JOptionPane.showMessageDialog(null,"Tentativa de Conexão realizada com sucesso!");
						i = i + 1;			 
				    }
				}
			}
			
		}catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}