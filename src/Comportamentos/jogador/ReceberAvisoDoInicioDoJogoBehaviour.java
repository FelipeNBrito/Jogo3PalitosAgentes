package Comportamentos.jogador;

import java.util.List;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import Agentes.AgenteJogador;
import jade.core.AID;

public class ReceberAvisoDoInicioDoJogoBehaviour extends CyclicBehaviour{
	private AgenteJogador agente;
	
	public ReceberAvisoDoInicioDoJogoBehaviour(AgenteJogador agente) {
		super(agente);
		this.agente = agente;
	}
	
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("inform-inicio-jogo"), 
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
		ACLMessage msg = this.agente.receive(mt);
		
		if(msg != null){
			try {
				List<AID> agentesNoJogo = (List<AID>) msg.getContentObject();
				this.agente.setPartidaIniciada(true);
				this.agente.setQuantidadePalitosPorAgente(agentesNoJogo);
				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.block();
		}
	}
	
}