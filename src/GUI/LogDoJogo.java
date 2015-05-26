package GUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Agentes.AgenteMediador;


public class LogDoJogo extends JFrame{

	private static JTextArea textArea;
	private AgenteMediador agente;
	private List<String> mensagens;
	
	public LogDoJogo(AgenteMediador agente) {
		this.agente = agente;
		this.mensagens = new ArrayList<String>();
		initialize();
	}
	
	public void addLog(String log){
		this.mensagens.add(log);
	}
	
	public void exibirProximaMensagem(){
		if(this.mensagens.size() > 0){
			this.textArea.append(this.mensagens.get(0)+" \n");
			this.mensagens.remove(0);
		}
	}
	
	private void initialize(){
		
		this.getContentPane().setLayout(null);
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.textArea = new JTextArea(30, 65);
		this.textArea.setBounds(0, 0, 650, 450);
		this.textArea.setEditable(false);
		this.textArea.setLineWrap(true);
		
		JPanel jp = new JPanel();
		jp.setBounds(10, 10, 780, 580);
		add(jp);
		
		JScrollPane jsp = new JScrollPane(this.textArea);
		jp.add(jsp);
	
		JButton btnInicial = new JButton("Inciar Jogo");
		btnInicial.setBounds(350, 700, 100, 80);
		btnInicial.setBackground(Color.RED);
		btnInicial.setForeground(Color.WHITE);
		jp.add(btnInicial);
		
		btnInicial.addActionListener(new EventoBotao(this.agente, btnInicial));
		
		this.setVisible(true);
	}
	
	private class EventoBotao implements ActionListener{

		private AgenteMediador mediador;
		private JButton botao;
		
		public EventoBotao(AgenteMediador mediador, JButton botao) {
			this.mediador = mediador;
			this.botao = botao;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			this.mediador.iniciarPartida();
		}
		
	}
	
}
