package Comportamentos.jogador;

import Agentes.AgenteJogador;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceberVencedorDaRodadaBehaviour extends CyclicBehaviour{
	private AgenteJogador agente;
	
	public ReceberVencedorDaRodadaBehaviour(AgenteJogador agente){
		super(agente);
		this.agente = agente;
	}
	
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchOntology("inform-jogador-vencedor"), 
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage msg = this.agente.receive(mt);
		
		if(msg != null){
			try {
				AID vencedor = (AID) msg.getContentObject();
				if(vencedor != null){
					this.agente.diminuirQuantidadeDePalitosDoVencedorDaRodada(vencedor);
					System.out.println("A quantidade de palitos do jogador " + vencedor.getLocalName() + " foi decrementda");
				}
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.block();
		}
	}

}
