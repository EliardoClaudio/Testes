package package_tokengametest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.modcs.tools.parser.model.ExecutionRuntime;
import org.modcs.tools.parser.model.SPNModel;
import org.modcs.tools.parser.model.Script;
import org.modcs.tools.parser.model.spn.TokenGame;

import br.ufpe.cin.support.WriteFile;

public class TokenGameTestNew {
	
    public static void main(String[] args) {
    }
	    public static void TokenGame(String arq) throws IOException {
	    	
	        ExecutionRuntime runtime = new ExecutionRuntime();
	        Script script = new Script(new File(arq));
	        runtime.evaluateScript(script);
	
	        SPNModel model = (SPNModel) runtime.getModel("Model");
	
	        TokenGame tg = new TokenGame(model, script);
	        
	        WriteFile.logger("\n ---> Test started on: " + new Date().toString(), "TokenGame.txt");
	        
	        BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
	        
	        leitor.append("Initial marking " + tg.getMarking());
	        
	        leitor.append("\n");
	        
	        leitor.close();
	        
	        int cont = 0;
	
	        while (true) {
	        	cont += 1;
	        	if(!tg.hasEnabledTransitions()){
	                break;
	            }
	        	
	        	if (cont > 5) {
	        		break;
	        	}
	            
	            SPNModel.Transition t = tg.getRandomTransition();
	
	            BufferedWriter leitor1 = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
	            leitor1.append("Fired: " + t.getName());
	            
	            leitor1.append("\n");
	            
	            leitor1.close();
	
	            tg.fireTransition(t);
	            
	            BufferedWriter leitor2 = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
	
	            leitor2.append("Current Marking: " + tg.getMarking());
	            
	            leitor2.append("\n");
	            
	            leitor2.close();
	        }
	
	        BufferedWriter leitor3 = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
	        
	        leitor3.append("\n");
	        
	        leitor3.append("Finishing Marking: " + tg.getMarking());
	        
	        leitor3.append("\n");
	        
	        leitor3.close();
	
	    }
	}
