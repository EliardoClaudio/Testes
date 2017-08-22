package package_tokengametest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.modcs.tools.parser.model.ExecutionRuntime;
import org.modcs.tools.parser.model.SPNModel;
import org.modcs.tools.parser.model.Script;
import org.modcs.tools.parser.model.spn.MarkingListener;
import org.modcs.tools.parser.model.spn.TokenGame;
import org.modcs.tools.parser.model.spn.TransitionListener;

import br.ufpe.cin.support.WriteFile;

public class EventsTestNew {
	
	public static void main(String[] args) {	
	}
		public static void EventTest(String arq) throws IOException {
	        ExecutionRuntime runtime = new ExecutionRuntime();
	        Script script = new Script(new File(arq));
	        runtime.evaluateScript(script);
	        
	
	        SPNModel model = (SPNModel) runtime.getModel("Model");
	
	        final TokenGame tg = new TokenGame(model, script);
	        
	        WriteFile.logger("\n ---> Test started on: " + new Date().toString(), "TokenGame.txt");
	        
	        tg.addTransitionListener("TI0", new TransitionListener() {          
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	   
					try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					} 
	               
	            }
	        });
	        
	        tg.addTransitionListener("TI1", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	
					try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
	                
	            }
	        });
	        
	        tg.addTransitionListener("TI2", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	
					try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
	                
	            }
	        });
	        
	        tg.addTransitionListener("TI2", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	
					try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
	                
	            }
	        });
	        
	        tg.addTransitionListener("TI3", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("failFront", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	
					try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
	                
	            }
	        });
	        
	        tg.addTransitionListener("failNode", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("failNode2", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("failNode3", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	
					try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
	                
	            }
	        });
	        
	        tg.addTransitionListener("failNode4", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	
					try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
	                
	            }
	        });
	        
	        tg.addTransitionListener("failVm", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("failVm2", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("failVm3", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("failVm4", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("repairFront", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("repairNode", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("repairNode2", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("repairNode3", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("repairNode4", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("repairVm", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("repairVm2", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }); 
	        
	        tg.addTransitionListener("repairVm3", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addTransitionListener("repairVm4", new TransitionListener() {           
	            @Override
	            public void transitionFired(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Transition: " + transition.getName() + " Fired! \n");
						leitor.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
	        
	        tg.addMarkingListener("#node4Off==1", new MarkingListener() {
	            @Override
	            public void markingReached(SPNModel.Transition transition) {
	            	try {
						BufferedWriter leitor = new BufferedWriter(new FileWriter ("TokenGame.txt",true));
						leitor.append("Reached Marking! \n");
						leitor.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					} 
	                
	            }
	        });
	        BufferedWriter leitor1 = new BufferedWriter(new FileWriter ("TokenGame.txt",true)); 
	        leitor1.append(tg.getMarking().toString());
	        leitor1.append("\n");
	        leitor1.close();
	        
	        int cont = 0;
	
	        while (true) {
	        	cont += 1;
	        	if(!tg.hasEnabledTransitions()){
	                break;
	            }
	            if (cont > 5){
	            	break;
	            }
	            
	            SPNModel.Transition t = tg.getRandomTransition();
	
	            tg.fireTransition(t);
	        }
	
	        BufferedWriter leitor2 = new BufferedWriter(new FileWriter ("TokenGame.txt",true)); 
	        leitor2.append(tg.getMarking().toString());
	        leitor2.append("\n");
	        leitor2.close();
	    }
	    
	}
