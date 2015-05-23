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
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchOntology("request-num-palitos"), 
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		ACLMessage msg = this.agente.receive(mt);
		
		if(msg != null){
			
			ACLMessage reply = msg.createReply();
			this.agente.escolherNumeroDePalitos();
			int quantidade = this.agente.getQuantidadeDePalitosNaMao();
			reply.setContent(quantidade+"");
			reply.setPerformative(ACLMessage.INFORM);
			reply.setOntology("inform-num-palitos");
			this.agente.send(reply);
			System.out.println("A agente " + this.agente.getLocalName() +
					" informou que tem " + quantidade + "palitos na mão" );

		}else{
			this.block();
		}
		
	}
	
}
