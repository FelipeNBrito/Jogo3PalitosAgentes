package Comportamentos.jogador;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import Agentes.AgenteJogador;

public class SolicitarEntradaNoJogo extends Behaviour{
	private AgenteJogador agente;
	private boolean jaEnviouSolicitacao;
	
	
	public SolicitarEntradaNoJogo(AgenteJogador agente){
		super(agente);
		this.agente = agente;
		this.jaEnviouSolicitacao = false;
	}
	
	@Override
	public void action() {
		AID mediadorAID = this.agente.getAgenteMediadorAID();
		
		if(this.agente.isJogando()){
			this.jaEnviouSolicitacao = true;
		}else if(mediadorAID != null){
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setOntology("request-participar-do-jogo");
			msg.addReceiver(mediadorAID);
			this.agente.send(msg);
			
			this.jaEnviouSolicitacao = true;
			
		}
	}

	@Override
	public boolean done() {
		return this.jaEnviouSolicitacao;
	}

}
