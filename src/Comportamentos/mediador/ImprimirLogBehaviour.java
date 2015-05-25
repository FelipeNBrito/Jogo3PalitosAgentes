package Comportamentos.mediador;

import jade.core.behaviours.TickerBehaviour;
import Agentes.AgenteMediador;
import GUI.LogDoJogo;

public class ImprimirLogBehaviour extends TickerBehaviour{
	private LogDoJogo log;
	
	public ImprimirLogBehaviour(LogDoJogo log, long tempo, AgenteMediador agente) {
		super(agente,tempo);
		this.log = log;
	}
	
	@Override
	protected void onTick() {
		log.exibirProximaMensagem();
	}
	
}
