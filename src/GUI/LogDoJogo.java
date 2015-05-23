package GUI;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class LogDoJogo extends JPanel{

	private static JTextArea textArea;
	
	public LogDoJogo() {
		
		this.textArea = new JTextArea(10, 40);
		this.textArea.setEditable(false);
		
		JScrollPane jsp = new JScrollPane(this.textArea);
		add(jsp);
	}
	
	public void addLog(String log){
		this.textArea.append(log + "\n");
	}
	
	public static LogDoJogo criarEMostrarGUI(){
		LogDoJogo log = new LogDoJogo();
		JFrame frame = new JFrame("Log do Jogo");
		frame.add(log);
		frame.pack();
		frame.setVisible(true);
		return log;
	}
}
