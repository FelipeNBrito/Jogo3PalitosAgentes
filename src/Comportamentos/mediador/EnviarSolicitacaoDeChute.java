package Comportamentos.mediador;

import java.io.IOException;
import java.io.Serializable;

import Agentes.AgenteMediador;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class EnviarSolicitacaoDeChute extends TickerBehaviour {

	private AgenteMediador mediador;
	
	public EnviarSolicitacaoDeChute(AgenteMediador agente, long tempo) {
		super(agente,tempo);
		this.mediador = agente;
	}
	
	@Override
	protected void onTick() {
		if(mediador.todosOsAgentesJaInformaramAQuantidadeDePalitosNaMao() && !mediador.todosOsJogadoresChutaram()){
			this.enviarRequisicaoDeChute(mediador.jogadorDaVez());
		}	
	}
	
	
	private void enviarRequisicaoDeChute(AID aidDestinatario) {
		
		if(!this.mediador.isJogoEmAndamento()){
			this.mediador.removeBehaviour(this);
		}
		
		ACLMessage mensagem = new ACLMessage(ACLMessage.REQUEST);
		
		mensagem.setOntology("request-chute");
		mensagem.setConversationId(String.valueOf(mediador.getNumeroDaRodada()));
		mensagem.addReceiver(aidDestinatario);
		
		mediador.addLog("Solicitacao de chute enviada ao jogador: "+ aidDestinatario.getLocalName());
		
		try {
			mensagem.setContentObject((Serializable) mediador.chutesDaRodada());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mediador.send(mensagem);
		
	}

}
