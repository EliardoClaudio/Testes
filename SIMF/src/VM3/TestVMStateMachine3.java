package VM3;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.support.MySshConnector;
import package_tokengametest.EventsTestNew;
import package_tokengametest.TokenGameTestNew;

public class TestVMStateMachine3 {
	
	public static void Chamada3(String login, String senha, String arq) throws InterruptedException, IOException {
		String newlogin = "192.168.0.151";
		try{
			System.out.println("Testando estado do Controlador VM3");
			if (arq.equals("")){ //Checa se o usuário selecionou um arquivo token.
				login = newlogin;

				//SSH credentials to the VM Machine
				MySshConnector con1 = new MySshConnector("root", senha, login);
	
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 3 and 20 minutes, mean value is 10 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(180000, 1200000, 600000);
				// randR - Random Repair Time between 3 and 20 minutes, mean value is 10 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(180000, 1200000, 600000);
	
				VMController3 VM3 = new VMController3(con1, randF, randR);
				
				int i = 0;
				while (true) {
				    VM3.runVMStateMachine();
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
				
				//SSH credentials to the CLC Machine
				MySshConnector con1 = new MySshConnector("root", senha, login);
	
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 3 and 20 minutes, mean value is 10 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(180000, 1200000, 600000);
				// randR - Random Repair Time between 3 and 20 minutes, mean value is 10 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(180000, 1200000, 600000);
	
				VMController3 VM3 = new VMController3(con1, randF, randR);
				
				int i = 0;
				while (true) {
				    VM3.runVMStateMachine();
				    Thread.sleep(50);
					if (i==0){
				    	JOptionPane.showMessageDialog(null,"Tentativa de Conexão realizada com sucesso!");
						i = i + 1;			 
				    }
				}
			}

		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}