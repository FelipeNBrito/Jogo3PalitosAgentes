package Comportamentos.mediador;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;

import Agentes.AgenteMediador;


public class SolicitarQuantidadeDePalitosNaMaoBehaviour extends OneShotBehaviour{

	private AgenteMediador agente;
	
	public SolicitarQuantidadeDePalitosNaMaoBehaviour(AgenteMediador agente) {
		this.agente = agente;
	}
	
	@Override
	public void action() {
		if(!this.agente.isJogoEmAndamento()){
			this.agente.removeBehaviour(this);
		}
		
		ACLMessage mensagem = new ACLMessage(ACLMessage.REQUEST);
		mensagem.setOntology("request-num-palitos");
		mensagem.setConversationId(String.valueOf(agente.getNumeroDaRodada()));
		
		List<AID> jogadoresNoJogo = agente.getJogadoresNoJogo();
		
		for(AID agenteAID : jogadoresNoJogo){
			mensagem.addReceiver(agenteAID);
			agente.addLog("O Agente Mediador solicitou a quantidade de palitos na mão do: "+ agenteAID.getLocalName());
		}
		
		agente.send(mensagem);
	}

}
