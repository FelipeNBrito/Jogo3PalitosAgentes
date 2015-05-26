package Comportamentos.mediador;

import java.util.List;
import java.util.Map;

import Agentes.AgenteMediador;
import jade.core.AID;
import jade.core.behaviours.Behaviour;

public class RodadaBehaviour extends Behaviour{

	private AgenteMediador agente;
	private boolean flag = false;
	private AID vencedorAID;
	
	public RodadaBehaviour(AgenteMediador agente) {
		this.agente = agente;
	}
	
	@Override
	public void action() {
		if(!this.agente.isJogoEmAndamento()){
			this.agente.removeBehaviour(this);
		}
		
		if(agente.todosOsJogadoresChutaram()){
			agente.addLog("O mediador vai analisar os chutes e indicar o vencedor da rodada");
			Map<AID,Integer> jogadoresNoJogo = agente.chutesDaRodada();
			
			this.vencedorAID = null;
			
			for(AID jogadorAID : jogadoresNoJogo.keySet()){
				int chute = jogadoresNoJogo.get(jogadorAID);
				
				if(chute == agente.getQuantidadeTotalDePalitosNaMaoDosJogadores()){
					vencedorAID = jogadorAID;
					break;
				}
			}
			
			if(vencedorAID != null){
				this.agente.decrementaTotalDePalitosDoJogador(vencedorAID);
				agente.addBehaviour(new InformarVencedorDaRodada(this.agente, vencedorAID));
				if(this.agente.getTotalDePalitosDoJogador(vencedorAID) == 0){
					agente.fimDeJogo(vencedorAID);
				}
				
			} else{
				agente.addBehaviour(new InformarVencedorDaRodada(this.agente, null));
			}
			flag = true;
		}

	}

	@Override
	public boolean done() {
		if(flag){
			this.agente.iniciarRodada();
			return true;
		}
		return false;
	}

}
