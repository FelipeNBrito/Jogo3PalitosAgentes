package Comportamentos.jogador;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Agentes.AgenteJogador;

public class ReceberConfirmacaoDeSolicitacaoDeJogo extends CyclicBehaviour{

	
	private AgenteJogador agente;
	
	
	public ReceberConfirmacaoDeSolicitacaoDeJogo(AgenteJogador agente){
		super(agente);
		this.agente = agente;
	}
	
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchOntology("accept-agente-entrou-no-jogo"), 
				MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
		ACLMessage msg = this.agente.receive(mt);
		
		if(msg != null){
			String conteudo = msg.getContent();
			AID agenteRemetenteAID = msg.getSender();
			if(conteudo.equalsIgnoreCase("voce esta participando") && agenteRemetenteAID == this.agente.getAgenteMediadorAID()){
				this.agente.setJogando(true);
				this.agente.removeBehaviour(this);
			}
		}else{
			this.block();
		}
	}
	
}
