package Agentes;

import java.util.List;

import Comportamentos.mediador.ReceberSolicitacaoDeJogoBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgenteMediador extends Agent{
	
	private List<AID> agentesNoJogo;
	private boolean jogoEmAndamento;
	
	protected void setup(){
		
		this.jogoEmAndamento = false;
		this.registrarNasPaginasAmarelas();
		
		addBehaviour(new ReceberSolicitacaoDeJogoBehaviour(this));
		
	}
	
	public void addAgenteAoJogo(AID agenteAID){
		if(!agentesNoJogo.contains(agenteAID)){
			this.agentesNoJogo.add(agenteAID);
		}
	}
	
	public void registrarNasPaginasAmarelas(){
	
		//Registrando o servico de jogo nas paginas Amarelas
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
				
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Buscar Jogador");
		sd.setName("Mediador");
				
		dfd.addServices(sd);
				
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setJogoEmAndamento(boolean valor){
		this.jogoEmAndamento = valor;
	}
	
	public boolean isJogoEmAndamento(){
		return this.jogoEmAndamento;
	}
}
