package Interface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import VM2.TestVMStateMachine2;
import VM3.TestVMStateMachine3;
import VM4.TestVMStateMachine4;
import VM5.TestVMStateMachine5;
import VM6.TestVMStateMachine6;
import br.ufpe.cin.testes.temp.TestCCController;
import br.ufpe.cin.testes.temp.TestCCController2;
import br.ufpe.cin.testes.temp.TestCCController3;
import br.ufpe.cin.testes.temp.TestCCController4;
import br.ufpe.cin.testes.temp.TestCLCController;
import br.ufpe.cin.testes.temp.TestHardwareController;
import br.ufpe.cin.testes.temp.TestVMStateMachine;

public class NewInjetor extends JFrame {
    /**
	 * @author Eliardo Cláudio - ecf@cin.ufpe.br
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<?> jComboBox1;
	private JTextField tfLogin;
	private JTextField tfTempo;
    private JLabel lbSenha; 
    private JLabel lbLogin;
    private JLabel lbTempo;
    private JTextField caminhoarq;
    private JButton btLogar; // Botão de Conectar
    private JButton plus;
    private JButton btCancelar;
    private JPasswordField pfSenha;
    private JLabel lbTime;
    private JComboBox<?> tfTime;
    private static NewInjetor frame;
    
    public NewInjetor() {
        inicializarComponentes();
        definirEventos();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void inicializarComponentes() {
    	this.setResizable(false);
        setTitle("SIMF Tool");
        setIconImage(Toolkit.getDefaultToolkit().getImage("icones/titulo.png"));
        setBounds(0,0,330,420); //Tamanho da Interface
        setLayout(null);
        jComboBox1 = new JComboBox(new String[]{"Auto", "Hardware"});
        tfTime = new JComboBox(new String[]{"1 Day", "2 Days", "3 Days", "8 Days", "12 Days", "24 Days", "Unlimited"});
        tfLogin = new JTextField(5);
        tfTempo = new JTextField(5);
        pfSenha = new JPasswordField(5);
        caminhoarq = new JTextField();
        lbSenha = new JLabel("Password:");
        lbLogin = new JLabel("IP Server:");
        lbTempo = new JLabel("Type Test:");
        btLogar = new JButton("Connect");
        plus = new JButton("+");
        btCancelar = new JButton("Cancel");
        lbTime = new JLabel("Test Duration:");
        tfLogin.setBounds(140, 40, 120, 25); //Largura da Label
        plus.setBounds(265, 40, 25, 24);
        lbLogin.setBounds(67, 40, 80, 25); //Espaçamento entre as Label
        jComboBox1.setBounds(140, 190, 120, 25);
        lbTempo.setBounds(63, 190, 80, 25);
        lbSenha.setBounds(60, 220, 80, 25);
        pfSenha.setBounds(140, 220, 120, 25);
        lbTime.setBounds(42, 250, 80, 25);
        tfTime.setBounds(140, 250, 120, 25);
        //caminhoarq.setBounds(103, 130, 120, 25);
        btLogar.setBounds(50, 345, 100, 25);
        btCancelar.setBounds(170, 345, 100, 25);

        add(plus);
        add(tfTime);
        add(lbTime);
        add(tfLogin);
        add(tfTempo);
        add(lbSenha);
        add(lbLogin);
        add(lbTempo);
        add(btLogar);
        add(btCancelar);
        add(pfSenha);
        add(jComboBox1);
        
    }

    public void definirEventos() {
    	plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JTextField transition1 = new JTextField(5);
		        transition1.setBounds(140, 70, 120, 25);
		        transition1.setText("Outro IP...");
		        //String aux = transition1.getText();
		        add(transition1);

		        JTextField transition2 = new JTextField(5);
		        transition2.setBounds(140, 100, 120, 25);
		        transition2.setText(" ... ");  
		        add(transition2); 

		        JTextField transition3 = new JTextField(5);
		        transition3.setBounds(140, 130, 120, 25);
		        transition3.setText(" ... ");  
		        add(transition3); 
      
		        JTextField transition4 = new JTextField(5);
		        transition4.setBounds(140, 160, 120, 25);
		        transition4.setText(" ... ");  
		        add(transition4); 
           }

        });
    	
        btLogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String senha = String.valueOf(pfSenha.getPassword()); // Conversão do array de caractere em string
                String opcao = String.valueOf(jComboBox1.getSelectedItem()); //Converte a opção relecionada do jComboBox em string
				@SuppressWarnings("unused")
				String opcaoTime = String.valueOf(tfTime.getSelectedItem());
                String arq = String.valueOf(caminhoarq.getText()); // Converte a opção relacionada da label tfarq em string
               /** 
                String transition1 = tfLogin.getText();
                String transition2 = tfLogin.getText();
                String transition3 = tfLogin.getText();
                String transition4 = tfLogin.getText();
                System.out.println(transition1+transition2+transition3+transition4);
               **/
                try {
                	String login = tfLogin.getText();
                	System.out.println("IP: "+ login);
                	System.out.println("Senha utilizada: "+"******");
                    opcao = opcao.toUpperCase();
                    frame.setVisible(false);
                    
                    switch(login)
                    {
	                    case "192.168.0.151":
	                    	System.out.println("Entrou no Front...");
	                	    TestCLCController.Chamada(login,senha,arq);
	                	    break;
                    
                    	case "192.168.0.152":
                    		System.out.println("Entrou no Node...");
                    		TestCCController.Chamada(login,senha,arq); //Faz a chamada do Metódo
                    	    break;
                    	    	
                    	case "192.168.0.153":
                    		System.out.println("Entrou no Node2...");
                    		TestCCController2.Chamada(login,senha,arq);
                    	    break;
                    	    
                    	case "192.168.0.155":
	                    	System.out.println("Entrou no Node3...");
	                    	TestCCController3.Chamada(login,senha,arq);
	                	    break;
	                	    
                    	case "192.168.0.156":
	                    	System.out.println("Entrou no Node4...");
	                    	TestCCController4.Chamada(login,senha,arq);
	                	    break;
                    	    
                    	case "192.168.0.170":
                    		System.out.println("Entrou na VM1");
                    		TestVMStateMachine.Chamada(login,senha,arq);
                    		break;

                		case "192.168.0.171":
                    		System.out.println("Entrou na VM2");
                    		TestVMStateMachine2.Chamada2(login,senha,arq);
                    		break;
                    	
                		case "192.168.0.172":
                    		System.out.println("Entrou na VM3");
                    		TestVMStateMachine3.Chamada3(login,senha,arq);
                    		break;
                    		
                		case "192.168.0.173":
                    		System.out.println("Entrou na VM4");
                    		TestVMStateMachine4.Chamada4(login,senha,arq);
                    		break;
                    		
                		case "192.168.0.174":
                    		System.out.println("Entrou na VM5");
                    		TestVMStateMachine5.Chamada5(login,senha,arq);
                    		break;
                    		
                		case "192.168.0.175":
                    		System.out.println("Entrou na VM6");
                    		TestVMStateMachine6.Chamada6(login,senha,arq);
                    	    break;
                    }
                    	switch(opcao)
                    	{
	                    	case "HARDWARE":
	                    		String aux = "192.168.0.151";
	                    		login = aux;
	                    		System.out.println("Entrou no Hardware: " + login);
	                    	    TestHardwareController.Chamada(login,senha,arq);
	                    	    break;  
                    	}
                    	Thread.sleep(20000);
                    	
					}
                    catch (InterruptedException e1) {
                    	System.out.println("Tratamento de erro!");
						e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("Tratamento de erro!");
						e1.printStackTrace();
					}               
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

		public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
		             frame = new NewInjetor();
		             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		             Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
		             frame.setLocation((tela.width - frame.getSize().width) / 2,
		                     (tela.height - frame.getSize().height) / 2);
		             frame.setVisible(true);
            	
            }
        });      
	}
}