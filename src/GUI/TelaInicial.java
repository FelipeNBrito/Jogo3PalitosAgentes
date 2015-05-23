package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Agentes.AgenteMediador;

public class TelaInicial extends JFrame{
	private AgenteMediador mediador;
	
	public static void main(String[] args) {
		TelaInicial tela = new TelaInicial(null);
	}
	
	public TelaInicial(AgenteMediador mediador) {
		this.mediador = mediador;
		initialize();
	}
	
	private void initialize(){
		
		this.getContentPane().setLayout(null);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JButton btnInicial = new JButton("Inciar Jogo");
		btnInicial.setBounds(150, 150, 200, 100);
		btnInicial.setBackground(Color.RED);
		add(btnInicial);
		
		btnInicial.addActionListener(new EventoBotao(mediador));
		
		this.setVisible(true);
	}
	
	private class EventoBotao implements ActionListener{

		private AgenteMediador mediador;
		
		public EventoBotao(AgenteMediador mediador) {
			this.mediador = mediador;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			this.mediador.iniciarPartida();
		}
		
	}
}

	