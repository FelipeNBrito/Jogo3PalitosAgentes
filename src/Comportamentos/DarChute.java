package Comportamentos;

import Agentes.AgenteJogador;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.MessageTemplate.MatchExpression;

public class DarChute extends CyclicBehaviour{
	private AgenteJogador agente;
	
	public DarChute(AgenteJogador agente){
		super(agente);
		this.agente = agente;
	}
	
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchConversationId("request-chute"));
		ACLMessage msg = myAgent.receive(mt);
		
		if(msg != null && this.agente.isJogando()){
			ACLMessage chute = msg.createReply();
			int valorChute = this.agente.gerarChute();
			
			chute.setContent(valorChute+"");
			chute.setConversationId("inform-chute");
			chute.setPerformative(ACLMessage.INFORM);
			myAgent.send(chute);
		}else{
			this.block();
		}
	}

}
