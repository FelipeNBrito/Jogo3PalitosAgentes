package Comportamentos.mediador;

import Agentes.AgenteMediador;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceberSolicitacaoDeChute extends Behaviour{

	private AgenteMediador mediador;
	private Behaviour enviarSolicitacaoDeChute;
	
	public ReceberSolicitacaoDeChute(AgenteMediador agente, Behaviour enviarSolicitacaoDeChute) {
		this.mediador = agente;
		this.enviarSolicitacaoDeChute = enviarSolicitacaoDeChute;
	}
	
	@Override
	public void action() {
		
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.and(MessageTemplate.MatchOntology("inform-chute"), 
				MessageTemplate.MatchPerformative(ACLMessage.INFORM)),MessageTemplate.MatchConversationId(String.valueOf(mediador.getNumeroDaRodada())));

		
		ACLMessage msg = this.mediador.receive(mt);
		
		if(msg != null && !mediador.jogadorJaChutouNaRodada(msg.getSender())){
			
			int chute = Integer.parseInt(msg.getContent());		
			mediador.addLog("O chute do agente "+msg.getSender().getLocalName()+" foi "+ chute);
			mediador.registrarChute(msg.getSender(), chute);
		
		} else{
			block();
		}
		
	}

	@Override
	public boolean done() {
		if(mediador.todosOsJogadoresChutaram()){
			mediador.addLog("Todos os Jogadores já chutaram!");
			mediador.removeBehaviour(this.enviarSolicitacaoDeChute);
			return true;
		}
		return false;
	}

}
