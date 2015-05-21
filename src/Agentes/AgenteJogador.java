package Agentes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import Comportamentos.jogador.BuscarAgenteMediadorBehaviour;
import Comportamentos.jogador.DarChute;
import Comportamentos.jogador.InformarQuantosPalitosJogueiBehaviour;
import Comportamentos.jogador.ReceberAvisoDoInicioDoJogoBehaviour;
import Comportamentos.jogador.ReceberConfirmacaoDeSolicitacaoDeJogo;
import Comportamentos.jogador.ReceberVencedorDaPartidaBehaviour;
import Comportamentos.jogador.ReceberVencedorDaRodadaBehaviour;
import Comportamentos.jogador.SolicitarEntradaNoJogo;

public class AgenteJogador extends Agent {

	private int quantidadeDePalitosTotal;
	private AID agenteMediadorAID;
	private boolean jogando;
	private int quantidadeDePalitosNaMao;
	private boolean partidaIniciada;
	private Map<AID, Integer> agentesNaPartida;
	
	protected void setup(){
		this.jogando = false;
		this.partidaIniciada = false;
		this.agentesNaPartida = new HashMap<AID, Integer>();
		this.quantidadeDePalitosTotal = 3;

		addBehaviour(new BuscarAgenteMediadorBehaviour(this));
		addBehaviour(new SolicitarEntradaNoJogo(this));
		addBehaviour(new ReceberConfirmacaoDeSolicitacaoDeJogo(this));
		addBehaviour(new ReceberAvisoDoInicioDoJogoBehaviour(this));
		addBehaviour(new InformarQuantosPalitosJogueiBehaviour(this));
		addBehaviour(new DarChute(this));
		addBehaviour(new ReceberVencedorDaRodadaBehaviour(this));
		addBehaviour(new ReceberVencedorDaPartidaBehaviour(this));
	}
	
	public void setJogando(boolean jogando){
		this.jogando = jogando;
	}
	
	public boolean isJogando(){
		return this.jogando;
	}
	
	public AID getAgenteMediadorAID(){
		return this.agenteMediadorAID;
	}
	
	public void setAgenteMediador(AID aid){
		this.agenteMediadorAID = aid;
	}
	
	public int getQuantidadeDePalitosNaMao(){
		return this.quantidadeDePalitosNaMao;
	}
	
	public void escolherNumeroDePalitos(){
		Random random = new Random(this.quantidadeDePalitosTotal);
		this.quantidadeDePalitosNaMao = random.nextInt();
	}

	public int gerarChute(Map<AID, Integer> chutes) {
		return 0;
	}
	
	public void setPartidaIniciada(boolean partidaIniciada){
		this.partidaIniciada = partidaIniciada;
	}
	
	public boolean isPartidaIniciada(){
		return this.partidaIniciada;
	}
	
	public void setQuantidadePalitosPorAgente(List<AID> agentes){
		for (AID agente : agentes) {
			this.agentesNaPartida.put(agente, 3);
		}
	}
	
	public void diminuirQuantidadeDePalitosDoVencedorDaRodada(AID agente){
		if(agente.getName().equals(this.getName())){
			this.quantidadeDePalitosTotal--;
		}
		Integer quantidadeDePalitos = this.agentesNaPartida.get(agente);
		if(quantidadeDePalitos != null){
			this.agentesNaPartida.put(agente, quantidadeDePalitos--);
		}
	}
	
}
