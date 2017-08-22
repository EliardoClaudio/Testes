package Interface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import package_tokengametest.EventsTestNew;
import package_tokengametest.TokenGameTestNew;

public class Injetor extends JFrame {
    /**
	 * @author Eliardo Cláudio - ecf@cin.ufpe.br
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<?> jComboBox1;
	private JTextField tfLogin;
    private JLabel lbSenha; //Área da senha
    private JLabel lbLogin; //Área do IP
    private JLabel lbTempo;
    private JTextField caminhoarq;
    private JLabel btn1;
    private JButton btCancelar;
    private JButton btn;
    private JPasswordField pfSenha;
    private String max;
    private static Injetor frame;
    
    public Injetor() {
        inicializarComponentes();
        definirEventos();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void inicializarComponentes() {
    	this.setResizable(false);
        setTitle("SIMF Tool");
        setIconImage(Toolkit.getDefaultToolkit().getImage("icones/titulo.png"));
        setBounds(0,0,330,280); //Tamanho da Interface
        setLayout(null);
        jComboBox1 = new JComboBox(new String[]{});
        tfLogin = new JTextField(5);
        pfSenha = new JPasswordField(5);
        caminhoarq = new JTextField();
        lbSenha = new JLabel("Password:");
        lbLogin = new JLabel("IP Server:");
        lbTempo = new JLabel("Type Test:");
        btn1 = new JLabel("File SPN:");
        btCancelar = new JButton("Cancel");
        btn = new JButton("Select");
        tfLogin.setBounds(140, 30, 120, 25); //Largura da Label
        lbLogin.setBounds(67, 30, 80, 25); //Espaçamento entre as Label
        lbTempo.setBounds(63, 60, 80, 25);
        lbSenha.setBounds(60, 90, 80, 25);
        pfSenha.setBounds(140, 90, 120, 25);
        btCancelar.setBounds(170, 195, 100, 25);
        jComboBox1.setBounds(140, 60, 120, 25);
        btn.setBounds(135, 110, 120, 25);
        btn1.setBounds(72, 110, 120, 25);
        add(btn);
        add(btn1);
        
    }

    public void definirEventos() {
    	btn.addActionListener(
    	   new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    	        JFileChooser fc = new JFileChooser();
    	        int res = fc.showOpenDialog(null);
    	                 
    	        if(res == JFileChooser.APPROVE_OPTION){
    	            File arquivo = fc.getSelectedFile(); 
    	            String arq = arquivo.toString();
    	            JOptionPane.showMessageDialog(null, "You have selected the file: " + arquivo.getName());
    	            caminhoarq.setText(arq); //// Setando o valor da Label automaticamente, esse valor será o valor escolhido na busca do arquivo 

    	            try{
                        int cont = 1;
                        BufferedReader leitor = new BufferedReader(new FileReader ("TokenGame.txt"));   //Lendo Arquivo Transformado
                        max = leitor.readLine();
                        while (max != null){
                            cont +=1;
                            max = leitor.readLine(); 
                        }
                        ArrayList<String> lista = new ArrayList<String>(cont);
                        leitor.close();
                          
                        BufferedReader leitor2 = new BufferedReader(new FileReader ("TokenGame.txt"));   //Lendo Arquivo Transformado
                        max = leitor2.readLine();
                        lista.add(max);
                        while (max != null){    
                            max = leitor2.readLine(); 
                            lista.add(max);
                       }
                        System.out.println("Tradução do Script realizada com sucesso!");
                        System.out.println("Os dados podem sem melhor visualizados dentro do Monitor...");
                        leitor2.close();
                       
                        for(int i=0;i<lista.size();i++){
                    	    System.out.println(lista.get(i)); //Imprime a lista de forma organizada.
	                    }
                        
                        //Recebendo arquivo SPN e chamando a outra interface.
                        String arq2 = String.valueOf(caminhoarq.getText()); // Converte a opção relacionada da label tfarq em string
                        System.out.println("Caminho do Arquivo SPN: "+arq2);
                        frame.setVisible(false);
                      
                        try {
        					EventsTestNew.EventTest(arq2);
        					TokenGameTestNew.TokenGame(arq2);
        				} catch (IOException e1) {
        					e1.printStackTrace();
        				}
                    				
                        try {
                        	Thread.sleep(2500);
                		} catch (InterruptedException e2) {	
                			e2.printStackTrace();
                		}
                        NewInjetor.main(null);
                        
                    }
                    catch (IOException e1) {
                        System.out.println("Tratamento de erro!");
                        e1.printStackTrace();
                    }               

    	        }   	                     
    	        else
    	            JOptionPane.showMessageDialog(null, "You have not selected any files."); 
    	        }   	
    	    }   
    	);	

        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
	             frame = new Injetor();
	             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	             Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
	             frame.setLocation((tela.width - frame.getSize().width) / 2,
	                     (tela.height - frame.getSize().height) / 2);
	             frame.setVisible(true);
            	
            }
        });      
	}
}
