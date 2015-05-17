package Agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import Comportamentos.mediador.ReceberSolicitacaoDeJogoBehaviour;
import Comportamentos.mediador.SolicitarQuantidadeDePalitosNaMaoBehaviour;

public class AgenteMediador extends Agent{
	
	private List<AID> agentesNoJogo;
	
	private boolean jogoEmAndamento;
	
	private Queue<AID> ordemDosJogadores;
	private Map<AID, Integer> quantidadeDePalitosNaMaoDosJogadores;
	
	private Map<AID,Integer> chutes;
	
	
	protected void setup(){
		
		this.agentesNoJogo = new ArrayList<AID>();
		this.jogoEmAndamento = false;
		this.registrarNasPaginasAmarelas();
		this.chutes = new HashMap<AID, Integer>();
		
		
		addBehaviour(new ReceberSolicitacaoDeJogoBehaviour(this));
		
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
	
	public void addAgenteAoJogo(AID agenteAID){
		if(!agentesNoJogo.contains(agenteAID)){
			this.agentesNoJogo.add(agenteAID);
		}
	}
	
	public void setJogoEmAndamento(boolean valor){
		this.jogoEmAndamento = valor;
	}
	
	public boolean isJogoEmAndamento(){
		return this.jogoEmAndamento;
	}
	
	public List<AID> getJogadoresNoJogo(){
		return this.agentesNoJogo;
	}

	public Map<AID,Integer> chutesDaRodada() {
		return chutes;
	}

	public AID jogadorDaVez() {
		
		AID jogadorDaVez = ordemDosJogadores.poll();
		
		ordemDosJogadores.offer(jogadorDaVez);
		
		return jogadorDaVez;
	}

	public void registrarChute(AID jogador, int chute) {
		
		chutes.put(jogador, chute);
		
	}
	
	
	public Map<AID,Integer> getQuantidadeDePalitosNaMaoDosJogadores(){
		return this.quantidadeDePalitosNaMaoDosJogadores;
	}
	
	public void addQuantidadeDePalitosNaMaoDoJogador(AID agenteAID, int quantidade){
		if(quantidadeDePalitosNaMaoDosJogadores.get(agenteAID) == null){
			quantidadeDePalitosNaMaoDosJogadores.put(agenteAID, quantidade);
		}
	}
	
	public boolean todosOsAgentesJaInformaramAQuantidadeDePalitosNaMao(){
		if(quantidadeDePalitosNaMaoDosJogadores.size() == agentesNoJogo.size()){
			return true;
		}
		return false;
	}
	public void iniciarRodada(){
		
		quantidadeDePalitosNaMaoDosJogadores = new HashMap<AID,Integer>();
		addBehaviour(new SolicitarQuantidadeDePalitosNaMaoBehaviour(this));
		
		AID jogadorDaVez = ordemDosJogadores.poll();
		ordemDosJogadores.offer(jogadorDaVez);
	}
	
	public void iniciarPartida(){
		
		for(AID jogador:agentesNoJogo){
			ordemDosJogadores.add(jogador);
		}
	}
}
