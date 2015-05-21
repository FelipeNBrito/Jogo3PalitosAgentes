package Comportamentos.jogador;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Agentes.AgenteJogador;

public class ReceberVencedorDaPartidaBehaviour extends CyclicBehaviour{
	private AgenteJogador agente;
	
	public ReceberVencedorDaPartidaBehaviour(AgenteJogador agente) {
		this.agente = agente;
	}
	
	
	@Override
	public void action() {
		MessageTemplate mt 	= MessageTemplate.and(MessageTemplate.MatchConversationId("inform-vencedor-partida"),
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
		ACLMessage msg = this.agente.receive(mt);
		
		if(msg != null){
			
			
			this.agente.setJogando(false);
		}else{
			this.block();
		}
	}

}
