package Agentes;

import java.util.Map;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import Comportamentos.BuscarAgenteMediadorBehaviour;
import Comportamentos.DarChute;
import Comportamentos.ReceberConfirmacaoDeSolicitacaoDeJogo;
import Comportamentos.SolicitarEntradaNoJogo;

public class AgenteJogador extends Agent {

	private int quantidadeDePalitosTotal;
	private AID agenteMediadorAID;
	private boolean jogando;
	private int quantidadeDePalitosNaMao;
	
	protected void setup(){
		this.jogando = false;
		

		addBehaviour(new BuscarAgenteMediadorBehaviour(this));
		addBehaviour(new SolicitarEntradaNoJogo(this));
		addBehaviour(new ReceberConfirmacaoDeSolicitacaoDeJogo(this));
		
		addBehaviour(new DarChute(this));
		
	}
	
	public void setJogando(boolean jogando){
		this.jogando = jogando;
	}
	
	public boolean isJogando(){
		return this.jogando;
	}
	
	private void iniciarNovaRodada(){
		this.quantidadeDePalitosTotal = 3;
	}
	
	public AID getAgenteMediadorAID(){
		return this.agenteMediadorAID;
	}
	
	public void setAgenteMediador(AID aid){
		this.agenteMediadorAID = aid;
	}
	
	public int getQuantidadeDePalitosNaMao(){
		return this.quantidadeDePalitosNaMao;
	}
	
	public void escolherNumeroDePalitos(){
		Random random = new Random(this.quantidadeDePalitosTotal);
		this.quantidadeDePalitosNaMao = random.nextInt();
	}

	public int gerarChute(Map<AID, Integer> chutes) {
		return 0;
	}
	
}
