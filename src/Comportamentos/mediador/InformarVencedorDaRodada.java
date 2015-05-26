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
		if(!this.agente.isJogoEmAndamento()){
			this.agente.removeBehaviour(this);
		}
		
		ACLMessage mensagem = new ACLMessage(ACLMessage.INFORM);
		mensagem.setOntology("inform-jogador-vencedor");
		mensagem.setConversationId(String.valueOf(agente.getNumeroDaRodada()));
		
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
		
		if(this.vencedorAID == null){
			agente.addLog("Niguem ganhou a rodada!");
		}else{
			agente.addLog("O vencedor da rodada foi o agente "+this.vencedorAID.getLocalName());
		}
	
	}

}
