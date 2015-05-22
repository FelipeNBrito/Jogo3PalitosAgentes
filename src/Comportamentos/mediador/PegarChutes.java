package Comportamentos.mediador;

import java.io.IOException;
import java.io.Serializable;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.introspection.RemovedBehaviour;
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
		
		if(mediador.todosOsAgentesJaInformaramAQuantidadeDePalitosNaMao()){
			this.enviarRequisicaoDeChute(mediador.jogadorDaVez());
		}
		
		
		
		if(mediador.todosOsAgentesJaInformaramAQuantidadeDePalitosNaMao() && !this.mediador.todosOsJogadoresChutaram()){
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("inform-chute"), 
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			
			ACLMessage msg = this.mediador.receive(mt);
			
			if(jaMandouUmaVez == true && msg != null){
		
				if(msg.getSender().getName().equals(mediador.jogadorDaVez().getName())){
					tratarChute(msg.getSender(), Integer.parseInt(msg.getContent()));
					mediador.addLog("O chute do agente "+msg.getSender().getLocalName()+" foi "+Integer.parseInt(msg.getContent()));
				}else{
					this.block();
				}
			}else if(jaMandouUmaVez == false){
				enviarRequisicaoDeChute(mediador.jogadorDaVez());
			}else{
				this.block();
			}
		}else{
			this.mediador.removeBehaviour(this);
		}
	}

	private void enviarRequisicaoDeChute(AID aidDestinatario) {
		
		ACLMessage mensagem = new ACLMessage(ACLMessage.REQUEST);
		
		mensagem.setConversationId("request-chute");
		mensagem.addReceiver(aidDestinatario);
		
		mediador.addLog("Solicitacao de chute enviada ao jogador: "+ aidDestinatario.getLocalName());
		
		try {
			mensagem.setContentObject((Serializable) mediador.chutesDaRodada());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.jaMandouUmaVez = true;
		mediador.send(mensagem);
	}
	
	private void tratarChute(AID jogador, int chute){
		if(mediador.chutesDaRodada().containsValue(chute)){
			enviarRecusaDeChute(jogador, chute);
		}else{
			mediador.registrarChute(jogador, chute);
			enviarRequisicaoDeChute(mediador.jogadorDaVez());
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
