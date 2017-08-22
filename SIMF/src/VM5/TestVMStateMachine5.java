package VM5;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.support.MySshConnector;
import package_tokengametest.EventsTestNew;
import package_tokengametest.TokenGameTestNew;

public class TestVMStateMachine5 {
	public static void Chamada5(String login, String senha, String arq) throws InterruptedException, IOException {
		String newlogin = "192.168.0.154";
		try{
			System.out.println("Testando estado do Controlador VM5");
			if (arq.equals("")){ //Checa se o usuário selecionou um arquivo token.
				login = newlogin;
				
				//SSH credentials to the VM
				MySshConnector con1 = new MySshConnector("root", senha, login);
			
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 2 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				// randR - Random Repair Time between 1 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60, 600, 300);
			
				VMController5 VM5 = new VMController5(con1, randF, randR);
				
				int i = 0;
				while (true) {
				    VM5.runHardwareStateMachine();
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
				
				//SSH credentials to the VM
				MySshConnector con1 = new MySshConnector("root", senha, login);
			
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 2 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				// randR - Random Repair Time between 1 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60, 600, 300);
			
				VMController5 VM5 = new VMController5(con1, randF, randR);
				
				int i = 0;
				while (true) {
				    VM5.runHardwareStateMachine();
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
