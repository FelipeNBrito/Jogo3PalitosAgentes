package Comportamentos.mediador;

import java.io.IOException;
import java.io.Serializable;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Agentes.AgenteMediador;

public class PegarChutes extends CyclicBehaviour{
	
	private AgenteMediador mediador;
	
	private boolean jaMandouUmaVez;
	
	public PegarChutes(AgenteMediador mediador){
		super(mediador);
		this.mediador = mediador;
		this.jaMandouUmaVez = false;
		
	}

	@Override
	public void action() {
		//TODO Tratar se o chute foi válido
		
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("inform-chute"), 
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
		ACLMessage msg = this.mediador.receive(mt);
		
		
		if(jaMandouUmaVez == true && msg != null){
			
			if(msg.getSender().getName().equals(mediador.jogadorDaVez().getName())){
				
				tratarChute(msg.getSender(), Integer.parseInt(msg.getContent()));
				
			}else{
				this.block();
			}
		}else if(jaMandouUmaVez == false){
			
			enviarRequisicaoDeChute();
		}else{
			this.block();
		}
			
		
	}

	private void enviarRequisicaoDeChute() {
		
		ACLMessage mensagem = new ACLMessage(ACLMessage.REQUEST);
		
		mensagem.setConversationId("request-chute");
		
		try {
			mensagem.setContentObject((Serializable) mediador.chutesDaRodada());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mediador.send(mensagem);
	}
	
	private void tratarChute(AID jogador, int chute){
		if(mediador.chutesDaRodada().containsValue(chute)){
			enviarRecusaDeChute(jogador, chute);
		}else{
			enviarRequisicaoDeChute();
		}
	}

	private void enviarRecusaDeChute(AID jogador, int chute) {
		ACLMessage mensagem = new ACLMessage(ACLMessage.REFUSE);
		mensagem.addReceiver(jogador);
		mensagem.setConversationId("refuse-chute");
		mensagem.setContent(chute+"");
		mediador.send(mensagem);
	}

}
