package Comportamentos.mediador;

import Agentes.AgenteMediador;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceberSolicitacaoDeJogoBehaviour extends Behaviour {

	private AgenteMediador agente;
	
	public ReceberSolicitacaoDeJogoBehaviour(AgenteMediador agente) {
		super(agente);
		this.agente = agente;
		
	}
	
	@Override
	public void action() {
		
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchOntology("request-participar-do-jogo"), 
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		ACLMessage msg = this.agente.receive(mt);
		
		
		if(msg != null){
			if(!this.agente.isJogoEmAndamento()){
				AID agenteSolicitanteAID = msg.getSender();
				agente.addAgenteAoJogo(agenteSolicitanteAID);
				
				ACLMessage resposta = msg.createReply();
				resposta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
				resposta.setOntology("accept-agente-entrou-no-jogo");
				resposta.setContent("voce esta participando");
				agente.send(resposta);
				
				agente.addLog("O Agente "+agenteSolicitanteAID.getLocalName()+" entrou no jogo!");
			}else{
				ACLMessage resposta = msg.createReply();
				resposta.setPerformative(ACLMessage.REFUSE);
				resposta.setConversationId("jogo-ja-em-andamento");
				agente.send(resposta);
			}
		} else{
			block();
		}
	}

	@Override
	public boolean done() {
		return false;
	}

}
