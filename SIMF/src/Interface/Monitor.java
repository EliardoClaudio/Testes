package Interface;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import resultadosfrontend.ResultDownFront;
import resultadosfrontend.ResultUpFront;
import resultadoshardware.ResultDownHardware;
import resultadoshardware.ResultUpHardware;
import resultadosnode.ResultDownNode;
import resultadosnode.ResultUpNode;
import resultadosvm.ResultDownVM;
import resultadosvm.ResultUpVM;

public class Monitor extends JFrame {	
	/**
	 * Eliardo Cláudio
	 */
	private static final long serialVersionUID = 1L;
		JPanel middlePanel;
		JTextArea display;
		JTextArea display2;
		JScrollPane scroll;
		JScrollPane scroll2;
		JComboBox<?> JComboBox1;
		String max;
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Monitor() {
			setIconImage(Toolkit.getDefaultToolkit().getImage("icones/titulo.png"));
			JComboBox1 = new JComboBox(new String[]{"Node", "Frontend", "Virtual Machine", "Hardware", "TokenGame"});
			middlePanel = new JPanel ();
		    middlePanel.add(JComboBox1);
		    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Monitor" ));
		    display = new JTextArea ( 30, 35 );
		    display2 = new JTextArea ( 30, 35 );
		    middlePanel.add(display2);
		    display.setEditable ( false );
		    display2.setEditable ( false );
		    scroll = new JScrollPane ( display );
		    scroll2 = new JScrollPane ( display2 );
		    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		    scroll2.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
		    middlePanel.add ( scroll );
		    middlePanel.add ( scroll2 );
		 
		    JFrame frame = new JFrame ();
		    frame.add ( middlePanel );
		    frame.pack ();
		    frame.setLocationRelativeTo ( null );
		    frame.setVisible ( true );
			
		}
		public static void main(String[] args) {   
	    	Monitor n = new Monitor();
		    while(true) {
		       n.codigo();
		       temporizador();
		    }
		} 
		    public void codigo() {
			    try {
			    	 String opcao = String.valueOf(JComboBox1.getSelectedItem());
			    	 opcao = opcao.toUpperCase();
		            	
		             switch(opcao)
		             {
		             	case "NODE":
		             		PrintWriter writer = new PrintWriter("Results_log.txt");
			 				writer.print("");
			 				writer.close();
			 				ResultUpNode.Up();
			 				ResultDownNode.Down();
			 				//ResultFailNode.Fail();
			 				//ResultRepairNode.Repair();
		             		display2.setText("");
		             		BufferedReader leitor12 = new BufferedReader(new FileReader ("Results_log.txt"));   
		             		max = leitor12.readLine();
		             		display2.append("Informações sobre o "+opcao+" estão sendo analisadas...");
		             		display2.append("\n"+"\n");
		             		while (max != null){
		             			display2.append(max);
		             			max = leitor12.readLine(); 
		             			display2.append("\n");
		             			display2.setCaretPosition(display2.getText().length());
		             		}
		             		leitor12.close();
		             		
		             		display.setText("");
		             		BufferedReader leitor = new BufferedReader(new FileReader ("Node_log.txt"));   
		             		max = leitor.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max); 
		             			max = leitor.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		             	 }
		             		leitor.close();
		             	    	break;
		             	    
		             	case "FRONTEND":
		             		PrintWriter writer2 = new PrintWriter("Results_log.txt");
			 				writer2.print("");
			 				writer2.close();
			 				ResultUpFront.Up();
			 				ResultDownFront.Down();
			 				//ResultFailFront.Fail();
			 				//ResultRepairFront.Repair();
		             		display2.setText("");
		             		BufferedReader leitor15 = new BufferedReader(new FileReader ("Results_log.txt"));   
		             		max = leitor15.readLine();
		             		display2.append("Informações sobre o "+opcao+" estão sendo analisadas...");
		             		display2.append("\n"+"\n");
		             		while (max != null){
		             			display2.append(max);
		             			max = leitor15.readLine(); 
		             			display2.append("\n");
		             			display2.setCaretPosition(display2.getText().length());
		             		}
		             		leitor15.close();
		             		
		             		display.setText(""); 
		             		BufferedReader leitor2 = new BufferedReader(new FileReader ("Frontend_log.txt"));   
		             		max = leitor2.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max); 
		             			max = leitor2.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		       	         }
		             		leitor2.close();
		             	        break;
		             	    
		             	case "VIRTUAL MACHINE":
		             		PrintWriter writer3 = new PrintWriter("Results_log.txt");
			 				writer3.print("");
			 				writer3.close();
			 				ResultUpVM.Up();
			 				ResultDownVM.Down();
			 				//ResultFailVM.Fail();
			 				//ResultRepairVM.Repair();
		             		display2.setText("");
		             		BufferedReader leitor14 = new BufferedReader(new FileReader ("Results_log.txt"));   
		             		max = leitor14.readLine();
		             		display2.append("Informações sobre o "+opcao+" estão sendo analisadas...");
		             		display2.append("\n"+"\n");
		             		while (max != null){
		             			display2.append(max);
		             			max = leitor14.readLine(); 
		             			display2.append("\n");
		             			display2.setCaretPosition(display2.getText().length());
		             		}
		             		leitor14.close();
		             		
		             		display.setText(""); 
		             		BufferedReader leitor3 = new BufferedReader(new FileReader ("VMController_log.txt"));   
		             		max = leitor3.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max); 
		             			max = leitor3.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		       	         }
		             		leitor3.close();
		             	        break;
		             	        
		             	case "VIRTUAL MACHINE 2":
		             		display.setText(""); 
		             		BufferedReader leitor6 = new BufferedReader(new FileReader ("Virtual Machine 2.txt"));   
		             		max = leitor6.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max); 
		             			max = leitor6.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		       	         }
		             		leitor6.close();
		             	        break;
		             	
		             	case "VIRTUAL MACHINE 3":
		             		display.setText(""); 
		             		BufferedReader leitor7 = new BufferedReader(new FileReader ("Virtual Machine 3.txt"));   
		             		max = leitor7.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max); 
		             			max = leitor7.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		       	         }
		             		leitor7.close();
		             	        break;
		             	        
		             	case "VIRTUAL MACHINE 4":
		             		display.setText(""); 
		             		BufferedReader leitor10 = new BufferedReader(new FileReader ("Virtual Machine 4.txt"));   
		             		max = leitor10.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max); 
		             			max = leitor10.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		       	         }
		             		leitor10.close();
		             	        break;
		             	        
		             	case "VIRTUAL MACHINE 5":
		             		display.setText(""); 
		             		BufferedReader leitor11 = new BufferedReader(new FileReader ("Virtual Machine 5.txt"));   
		             		max = leitor11.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max); 
		             			max = leitor11.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		       	         }
		             		leitor11.close();
		             	        break; 
		             	        
		             	case "VIRTUAL MACHINE 6":
		             		display.setText(""); 
		             		BufferedReader leitor9 = new BufferedReader(new FileReader ("Virtual Machine 6.txt"));   
		             		max = leitor9.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max); 
		             			max = leitor9.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		       	         }
		             		leitor9.close();
		             	        break;       
		             	      
		             	case "HARDWARE":
		             		PrintWriter writer4 = new PrintWriter("Results_log.txt");
			 				writer4.print("");
			 				writer4.close();
			 				ResultUpHardware.Up();
			 				ResultDownHardware.Down();
			 				//ResultFailHardware.Fail();
			 				//ResultRepairHardware.Repair();
		             		display2.setText("");
		             		BufferedReader leitor13 = new BufferedReader(new FileReader ("Results_log.txt"));   
		             		max = leitor13.readLine();
		             		display2.append("Informações sobre o "+opcao+" estão sendo analisadas...");
		             		display2.append("\n"+"\n");
		             		while (max != null){
		             			display2.append(max);
		             			max = leitor13.readLine(); 
		             			display2.append("\n");
		             			display2.setCaretPosition(display2.getText().length());
		             		}
		             		leitor13.close();
		             		
		             		display.setText(""); 
		             		BufferedReader leitor4 = new BufferedReader(new FileReader ("Hardware_log.txt"));   
		             		max = leitor4.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max);
		             			max = leitor4.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		       	         }
		             		leitor4.close();
		             	    	break;
		             	    	
		             	case "TOKENGAME":
		             		display.setText(""); 
		             		BufferedReader leitor5 = new BufferedReader(new FileReader ("TokenGame.txt"));   
		             		max = leitor5.readLine();
		             		display.append("O teste "+opcao+" está sendo Monitorado...");
		             		display.append("\n"+"\n");
		             		while (max != null){
		             			display.append(max);
		             			max = leitor5.readLine(); 
		             			display.append("\n");
		             			display.setCaretPosition(display.getText().length());
		       	         }
		             		leitor5.close();
		             	    	break;
		            }
		             
			        } catch (IOException e) {
			    }
		    }
		        	        
			public static void temporizador() {
	          try {
	             Thread.sleep(8000);
	          } catch(InterruptedException e){
	    }
	} 
}