package Comportamentos;

import Agentes.AgenteJogador;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BuscarAgenteMediadorBehaviour extends Behaviour{

	
	@Override
	public void action() {
		
		
		DFAgentDescription dfd = new DFAgentDescription();
				
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Buscar Jogador");
		sd.setName("Mediador");
				
		dfd.addServices(sd);
		
		try {
			DFAgentDescription[] result = DFService.search(this.myAgent, dfd);
			
			if(result.length > 0){
				((AgenteJogador) this.myAgent).setAgenteMediador(result[0].getName());
			}
			
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean done() {
		
		if( ((AgenteJogador) this.myAgent).getAgenteMediadorAID() != null){
			return true;
		}
		return false;
	}

}
