package Comportamentos.mediador;

import java.util.List;

import Agentes.AgenteMediador;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class SolicitarQuantidadeDePalitosNaMaoBehaviour extends OneShotBehaviour{

	private AgenteMediador agente;
	
	public SolicitarQuantidadeDePalitosNaMaoBehaviour(AgenteMediador agente) {
		this.agente = agente;
	}
	
	@Override
	public void action() {
	
		ACLMessage mensagem = new ACLMessage(jade.lang.acl.ACLMessage.REQUEST);
		mensagem.setConversationId("request-num-palitos");
		
		List<AID> jogadoresNoJogo = agente.getJogadoresNoJogo();
		
		for(AID agenteAID : jogadoresNoJogo){
			mensagem.addReceiver(agenteAID);
		}
		
		agente.send(mensagem);
	}

}
