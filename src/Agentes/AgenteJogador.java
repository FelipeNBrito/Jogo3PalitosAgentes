package Agentes;

import Comportamentos.BuscarAgenteMediadorBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgenteJogador extends Agent {

	private int quantidadeDePalitos;
	private AID agenteMediadorAID;
	
	protected void setup(){
		
		addBehaviour(new BuscarAgenteMediadorBehaviour(this));
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
	
}
