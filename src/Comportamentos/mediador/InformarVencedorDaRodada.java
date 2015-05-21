package Comportamentos.mediador;

import java.io.IOException;
import java.util.List;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import Agentes.AgenteMediador;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class InformarVencedorDaRodada extends OneShotBehaviour{

	private AgenteMediador agente;
	private AID vencedorAID;
	
	public InformarVencedorDaRodada(AgenteMediador agente, AID vencedorAID) {
		this.agente = agente;
		this.vencedorAID = vencedorAID;
	}
	@Override
	public void action() {
		
		ACLMessage mensagem = new ACLMessage(ACLMessage.INFORM);
		mensagem.setConversationId("inform-jogador-vencedor");
		
		try {
			mensagem.setContentObject(this.vencedorAID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<AID> jogadoresNoJogo = agente.getJogadoresNoJogo();
		
		for(AID agenteAID : jogadoresNoJogo){
			mensagem.addReceiver(agenteAID);
		}
		agente.send(mensagem);
		
		System.out.println("O vencedor da rodada foi o agente "+this.vencedorAID.getLocalName());
	}

}
