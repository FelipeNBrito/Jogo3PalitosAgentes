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
		
		if(agente.todosOsJogadoresChutaram()){
			System.out.println("O mediador vai analisar os chutes e indicar o vencedor");
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
				this.agente.decrementarPalitoDoJogador(vencedorAID);
				agente.addBehaviour(new InformarVencedorDaRodada(this.agente, vencedorAID));
			} else{
				agente.addBehaviour(new InformarVencedorDaRodada(this.agente, null));
			}
			flag = true;
		}

	}

	@Override
	public boolean done() {
		return flag;
	}

}
