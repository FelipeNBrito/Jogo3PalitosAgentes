package Comportamentos.mediador;

import Agentes.AgenteMediador;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class ReceberQuantidadeDePalitosNaMaoBehavior extends Behaviour{

	private AgenteMediador agente;
	
	public ReceberQuantidadeDePalitosNaMaoBehavior(AgenteMediador agente) {
		this.agente = agente;
	}
	@Override
	public void action() {

		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("inform-num-palitos"), 
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
		ACLMessage mensagem = this.agente.receive(mt);
		
		if(mensagem != null){
			int quantidadeDePalitos = Integer.parseInt(mensagem.getContent());
			agente.addQuantidadeDePalitosNaMaoDoJogador(mensagem.getSender(), quantidadeDePalitos);
		}else{
			block();
		}
	}

	@Override
	public boolean done() {
		return this.agente.todosOsAgentesJaInformaramAQuantidadeDePalitosNaMao();
	}



}
