package Agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Comportamentos.mediador.EnviarSolicitacaoDeChute;
import Comportamentos.mediador.ImprimirLogBehaviour;
import Comportamentos.mediador.InformarJogoIniciadoBehaviour;
import Comportamentos.mediador.ReceberQuantidadeDePalitosNaMaoBehavior;
import Comportamentos.mediador.ReceberSolicitacaoDeChute;
import Comportamentos.mediador.ReceberSolicitacaoDeJogoBehaviour;
import Comportamentos.mediador.RodadaBehaviour;
import Comportamentos.mediador.SolicitarQuantidadeDePalitosNaMaoBehaviour;
import GUI.LogDoJogo;

public class AgenteMediador extends Agent{
	
	private List<AID> agentesNoJogo;
	private Map<AID, Integer> quantidadeDePalitosTotal;
	private boolean jogoEmAndamento;
	private List<AID> ordemDosJogadores;
	private Map<AID,Integer> quantidadeDePalitosNaMaoDosJogadores;
	private Map<AID,Integer> chutes;
	private LogDoJogo log;
	private int numeroDaRodada;
	
	
	protected void setup(){
		
		this.agentesNoJogo = new ArrayList<AID>();
		this.ordemDosJogadores = new ArrayList<AID>();
		this.jogoEmAndamento = false;
		this.registrarNasPaginasAmarelas();
		this.chutes = new HashMap<AID, Integer>();
		this.quantidadeDePalitosTotal = new HashMap<AID, Integer>();
		this.log = new LogDoJogo(this);
		this.numeroDaRodada = 0;
		
		addBehaviour(new ImprimirLogBehaviour(log, 1500, this));
		addBehaviour(new ReceberSolicitacaoDeJogoBehaviour(this));
		
		
	}
	
	public int getNumeroDaRodada(){
		return numeroDaRodada;
	}
	
	public void addLog(String log){
		this.log.addLog(log);
	}
	
	public void decrementaTotalDePalitosDoJogador(AID agenteAID){
		quantidadeDePalitosTotal.put(agenteAID, quantidadeDePalitosTotal.get(agenteAID) - 1);
	}
	
	public int getTotalDePalitosDoJogador(AID agenteAID){
		return this.quantidadeDePalitosTotal.get(agenteAID);
	}
	
	public void fimDeJogo(AID vencedor){
		this.jogoEmAndamento = false;
		this.log.addLog("O agente vencedor foi: " + vencedor.getLocalName());
		this.jogoEmAndamento = false;
	}
	
	public int getQuantidadeTotalDePalitosNaMaoDosJogadores(){
		int total = 0;
		for(int quantidadeDoAgente : quantidadeDePalitosNaMaoDosJogadores.values()){
			total += quantidadeDoAgente;
		}	
		return total;
	}
	
	public boolean jogadorJaChutouNaRodada(AID agenteAid){
		return this.chutes.containsKey(agenteAid);
		
		
	}
	
	public void decrementarPalitosNaMaoDoJogador(AID agenteAID){
		quantidadeDePalitosNaMaoDosJogadores.put(agenteAID, quantidadeDePalitosTotal.get(agenteAID) - 1);
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
			this.quantidadeDePalitosTotal.put(agenteAID, 3);
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

	public boolean todosOsJogadoresChutaram(){
		if(this.chutes.size() >= this.agentesNoJogo.size()){
			return true;
		}
		return false;
	}
	
	public AID jogadorDaVez() {
		if(this.ordemDosJogadores.size() > 0)
			return this.ordemDosJogadores.get(0);
		return null;
	}

	public void registrarChute(AID jogador, int chute) {
		if(this.ordemDosJogadores.size() > 0){
			AID jogadorDaVez = this.ordemDosJogadores.remove(0);
			this.ordemDosJogadores.add(jogadorDaVez);
		}
		this.chutes.put(jogador, chute);
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
		if(this.jogoEmAndamento){
			this.numeroDaRodada++;
			this.quantidadeDePalitosNaMaoDosJogadores = new HashMap<AID,Integer>();
			this.chutes = new HashMap<AID, Integer>();
			if(this.ordemDosJogadores.size() > 0){
				AID jogadorDaVez = this.ordemDosJogadores.remove(0);
				this.ordemDosJogadores.add(jogadorDaVez);
			}
		
			addBehaviour(new SolicitarQuantidadeDePalitosNaMaoBehaviour(this));
			addBehaviour(new ReceberQuantidadeDePalitosNaMaoBehavior(this));
			TickerBehaviour solicitarChute = new EnviarSolicitacaoDeChute(this, 10000);
			addBehaviour(solicitarChute);
			addBehaviour(new ReceberSolicitacaoDeChute(this,solicitarChute));
			addBehaviour(new RodadaBehaviour(this));
		}
	}
	
	public void iniciarPartida(){
		
		if(!this.jogoEmAndamento && this.agentesNoJogo.size() > 1){
			this.jogoEmAndamento = true;
			
			for(AID jogador:agentesNoJogo){
				this.ordemDosJogadores.add(jogador);
			}
			
			addBehaviour(new InformarJogoIniciadoBehaviour(this));
			
			this.iniciarRodada();
		}else if(this.jogoEmAndamento){
			this.log.addLog("O jogo já está em andamento!");
		}else{
			this.log.addLog("Não há jogadores suficiente!");
		}
	}
}
