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
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("request-num-palitos"), 
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		ACLMessage msg = this.agente.receive(mt);
		
		if(msg != null){
			
			ACLMessage reply = msg.createReply();
			int quantidade = this.agente.getQuantidadeDePalitosNaMao();
			reply.setContent(quantidade+"");
			reply.setPerformative(ACLMessage.INFORM);
			reply.setConversationId("inform-num-palitos");
			this.agente.send(reply);

		}else{
			this.block();
		}
		
	}
	
}
