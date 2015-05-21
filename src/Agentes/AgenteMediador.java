package Agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import Comportamentos.mediador.InformarJogoIniciadoBaheviour;
import Comportamentos.mediador.PegarChutes;
import Comportamentos.mediador.ReceberQuantidadeDePalitosNaMaoBehavior;
import Comportamentos.mediador.ReceberSolicitacaoDeJogoBehaviour;
import Comportamentos.mediador.RodadaBehaviour;
import Comportamentos.mediador.SolicitarQuantidadeDePalitosNaMaoBehaviour;

public class AgenteMediador extends Agent{
	
	private List<AID> agentesNoJogo;
	private Map<AID, Integer> quantidadeDePalitosTotal;
	private boolean jogoEmAndamento;
	private Queue<AID> ordemDosJogadores;
	private Map<AID,Integer> quantidadeDePalitosNaMaoDosJogadores;
	private Map<AID,Integer> chutes;
	
	
	protected void setup(){
		
		this.agentesNoJogo = new ArrayList<AID>();
		this.jogoEmAndamento = false;
		this.registrarNasPaginasAmarelas();
		this.chutes = new HashMap<AID, Integer>();
		this.quantidadeDePalitosTotal = new HashMap<AID, Integer>();
		
		addBehaviour(new ReceberSolicitacaoDeJogoBehaviour(this));
		addBehaviour(new OneShotBehaviour(this) {
			
			
			@Override
			public void action() {
				try {
					Thread.sleep(60000);
					((AgenteMediador) myAgent).iniciarPartida();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
	}
	
	public int getQuantidadeTotalDePalitosNaMaoDosJogadores(){
		int total = 0;
		for(int quantidadeDoAgente : quantidadeDePalitosNaMaoDosJogadores.values()){
			total += quantidadeDoAgente;
		}	
		return total;
	}
	
	public void decrementarPalitoDoJogador(AID agenteAID){
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
		if(this.chutes.size() >= this.ordemDosJogadores.size()){
			return false;
		}
		return true;
	}
	
	public AID jogadorDaVez() {
		return this.ordemDosJogadores.peek();
	}

	public void registrarChute(AID jogador, int chute) {
		
		AID jogadorDaVez = this.ordemDosJogadores.poll();
		
		this.ordemDosJogadores.offer(jogadorDaVez);
		
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
		
		this.quantidadeDePalitosNaMaoDosJogadores = new HashMap<AID,Integer>();
		this.chutes = new HashMap<AID, Integer>();
		addBehaviour(new SolicitarQuantidadeDePalitosNaMaoBehaviour(this));
		addBehaviour(new ReceberQuantidadeDePalitosNaMaoBehavior(this));
		addBehaviour(new PegarChutes(this));
		addBehaviour(new RodadaBehaviour(this));
		
		AID jogadorDaVez = ordemDosJogadores.poll();
		this.ordemDosJogadores.offer(jogadorDaVez);
	}
	
	public void iniciarPartida(){
		this.jogoEmAndamento = true;
		
		for(AID jogador:agentesNoJogo){
			this.ordemDosJogadores.add(jogador);
		}
		
		addBehaviour(new InformarJogoIniciadoBaheviour(this));
		
		this.iniciarRodada();
	}
}
