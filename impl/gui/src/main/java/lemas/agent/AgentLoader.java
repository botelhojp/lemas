package lemas.agent;

import java.util.List;

import jade.content.ContentElement;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import lemas.Lemas;
import lemas.agent.behaviour.LoaderBehaviour;
import lemas.form.DialogResult;
import lemas.form.FrameMain;
import lemas.form.FrameProject;
import lemas.model.LemasLog;
import lemas.util.CommonsFrame;
import openjade.core.OpenAgent;
import openjade.core.annotation.ReceiveMatchMessage;
import openjade.core.annotation.ReceiveSimpleMessage;
import openjade.ontology.RequestRating;
import openjade.ontology.WitnessResponse;
import openjade.trust.ITrustModel;
import openjade.trust.WitnessUtil;

public class AgentLoader extends OpenAgent {

	private static AgentLoader instance;
	private static final long serialVersionUID = 1L;
	private int wait = 0;;
	private double countFalse = 0;
	private static int executions = -1;
	private DialogResult dialogResult;
	private double count = 0;
	private double round = 0;
	private LoaderBehaviour loader;
	
	public static AgentLoader getInstance(){
		return instance;
	}

	@Override
	public void setup() {
		super.setup();
		wait = 0;
		countFalse = 0;
		count = 0;
		round = 0;
		executions++;
		
		loader = new LoaderBehaviour(this, getTrustModelClass());
		addBehaviour(loader);
		instance = this;
	}

	/**
	 * Recebe confirmacao de destruicao do agente
	 * 
	 * @param message
	 */
	@ReceiveSimpleMessage(conversationId = ConversationId.DO_DELETE)
	public void dead(ACLMessage message) {
		loader.removerCache(message.getSender());
	}

	/**
	 * 
	 * @param msg
	 */
	@ReceiveSimpleMessage(conversationId = ConversationId.TEST)
	public void getTestMessage(ACLMessage msg) {
		++count;
		Boolean test = Boolean.parseBoolean(msg.getContent());
		if (!test) {
			countFalse++;
		}
		addResult(executions, round++, countFalse);
		LemasLog.info("total = " + count + " acertos = " + countFalse);
		wait--;
	}
	
	/**
	 * Recebe um pedido para informar testemunhas de um agente
	 * @param message
	 */
	@ReceiveMatchMessage(performative = ACLMessage.REQUEST, conversationId = ConversationId.GET_INDIRECT, action = RequestRating.class)
	public void requestWitness(ACLMessage message, ContentElement ce) {
		RequestRating sr = (RequestRating) ce;
		List<AID> list = WitnessUtil.getWitness(sr.getAid());
		WitnessResponse wr = new WitnessResponse();
		wr.setServer(sr.getAid());
		for (AID aid : list) {
			wr.addWitnesses(aid);	
		}
		sendMessage(message.getSender(), ACLMessage.INFORM, ConversationId.GET_INDIRECT, wr);
	}
	
	public void addResult(int executions, double round, double value){
		if (dialogResult == null) {
			dialogResult = new DialogResult();
			CommonsFrame.loadFrame(FrameMain.getInstance().getFrameResult(), dialogResult);
		}
		dialogResult.addResult(executions, round, value);
	}

	public void waiting() {
		wait++;
	}

	public boolean nowait() {
		return (wait==0);
	}

	@SuppressWarnings("unchecked")
	private Class<ITrustModel> getTrustModelClass() {
		try {
			if (getArguments().length != 2) {
				throw new RuntimeException("Modelo de Confiancao nao selecionado");
			}
			return (Class<ITrustModel>) Class.forName(FrameProject.getInstance().getCurrentProject().getClazz());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Modelo de Confiancao nao selecionado", e);
		}
	}

	public void stop() {
		loader.stop();
		Lemas.cleanFiles();
	}
}
