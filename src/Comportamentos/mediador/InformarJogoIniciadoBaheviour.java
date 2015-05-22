package Comportamentos.mediador;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import Agentes.AgenteMediador;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class InformarJogoIniciadoBaheviour extends Behaviour{

	private AgenteMediador agente;
	
	public InformarJogoIniciadoBaheviour(AgenteMediador agente) {
		this.agente = agente;
	}
	
	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.setConversationId("inform-inicio-jogo");
		
		List<AID> jogadoresNoJogo = agente.getJogadoresNoJogo();
		try {
			message.setContentObject((Serializable) jogadoresNoJogo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(AID jogadorAID : jogadoresNoJogo){
			message.addReceiver(jogadorAID);
		}
		
		agente.send(message);
		
		agente.addLog("Jogo iniciado!");
	}

	@Override
	public boolean done() {
		return true;
	}
}
