package Comportamentos;

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
		
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("inform-chute"), 
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
		ACLMessage msg = this.mediador.receive(mt);
		
		
		if(jaMandouUmaVez == true && msg != null){
			
			if(msg.getSender().getName().equals(mediador.jogadorDaVez().getName())){
				
				mediador.registrarChute(msg.getSender(),Integer.parseInt(msg.getContent()));
				
				enviarRequisicaoDeChute();
				
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

}
