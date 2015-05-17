package Agentes;

import jade.core.AID;
import jade.core.Agent;
import Comportamentos.BuscarAgenteMediadorBehaviour;
import Comportamentos.DarChute;
import Comportamentos.ReceberConfirmacaoDeSolicitacaoDeJogo;
import Comportamentos.SolicitarEntradaNoJogo;

public class AgenteJogador extends Agent {

	private int quantidadeDePalitos;
	private AID agenteMediadorAID;
	private boolean jogando;
	
	protected void setup(){
		this.jogando = false;
		
<<<<<<< HEAD
		addBehaviour(new BuscarAgenteMediadorBehaviour(this));
=======
		addBehaviour(new BuscarAgenteMediadorBehaviour());
		addBehaviour(new SolicitarEntradaNoJogo(this));
		addBehaviour(new ReceberConfirmacaoDeSolicitacaoDeJogo(this));
		
		addBehaviour(new DarChute(this));
		
	}
	
	public void setJogando(boolean jogando){
		this.jogando = jogando;
	}
	
	public boolean isJogando(){
		return this.jogando;
>>>>>>> origin/master
	}
	
	private void iniciarNovaRodada(){
		this.quantidadeDePalitos = 3;
	}
	
	public AID getAgenteMediadorAID(){
		return this.agenteMediadorAID;
	}
	
	public void setAgenteMediador(AID aid){
		this.agenteMediadorAID = aid;
	}

	public int gerarChute() {
		return 0;
	}
}
