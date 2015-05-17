package Comportamentos.jogador;

import Agentes.AgenteJogador;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InformarQuantosPalitosJogueiBehaviour extends CyclicBehaviour{
	private AgenteJogador agente;
	
	
	public InformarQuantosPalitosJogueiBehaviour(AgenteJogador agente){
		super(agente);
		this.agente = agente;
	}
	
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("request-palitos-na-mao"), 
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		ACLMessage msg = this.agente.receive(mt);
		
		if(msg != null){
			
			ACLMessage reply = msg.createReply();
			reply.setContent(this.agente.getQuantidadeDePalitosNaMao()+"");
			reply.setPerformative(ACLMessage.INFORM);
			reply.setConversationId("inform-palitos-na-mao");
			this.agente.send(reply);

		}else{
			this.block();
		}
		
	}
	
}
