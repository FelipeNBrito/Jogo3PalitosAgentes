package Comportamentos.jogador;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.Map;

import Agentes.AgenteJogador;

public class DarChute extends CyclicBehaviour{
	private AgenteJogador agente;
	
	public DarChute(AgenteJogador agente){
		super(agente);
		this.agente = agente;
	}
	
	@Override
	public void action() {
		
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
				MessageTemplate.MatchOntology("request-chute"));
		ACLMessage msg = myAgent.receive(mt);
		
		if(msg != null){
			
			ACLMessage chute = msg.createReply();
			try {
				Map<AID, Integer> chutes = (Map<AID, Integer>) msg.getContentObject();
				int valorChute = this.agente.gerarChute(chutes);
				
				chute.setContent(valorChute+"");
				chute.setOntology("inform-chute");
				chute.setConversationId(msg.getConversationId());
				chute.setPerformative(ACLMessage.INFORM);
				myAgent.send(chute);
				
			} catch (UnreadableException e){
				e.printStackTrace();
			}
			
		
		}else{
			this.block();
		}
	}

}
