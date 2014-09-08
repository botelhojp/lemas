package lemas.agent;

import jade.lang.acl.ACLMessage;
import lemas.Lemas;
import lemas.agent.behaviour.LoaderBehaviour;
import lemas.form.DialogResult;
import lemas.form.FrameMain;
import lemas.form.FrameProject;
import lemas.model.LemasLog;
import lemas.util.CommonsFrame;
import openjade.core.OpenAgent;
import openjade.core.annotation.ReceiveSimpleMessage;
import openjade.trust.ITrustModel;

public class AgentLoader extends OpenAgent {

	private static AgentLoader instance;
	private static final long serialVersionUID = 1L;
	private int wait = 0;;
	private double countTrue = 0;
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
		countTrue = 0;
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
	 * Recebe confirmacao de que o agente foi iniciado
	 * 
	 * @param msg
	 */
	@ReceiveSimpleMessage(conversationId = ConversationId.LOADER)
	public void getMessage(ACLMessage msg) {
//		if (wait.contains(msg.getSender())) {
//			wait.remove(msg.getSender());
//		} else {
//			throw new OpenJadeException("Agente nao autorizado [" + msg.getSender().getLocalName() + "]");
//		}
	}

	/**
	 * 
	 * @param msg
	 */
	@ReceiveSimpleMessage(conversationId = ConversationId.TEST)
	public void getTestMessage(ACLMessage msg) {
		++count;
		Boolean test = Boolean.parseBoolean(msg.getContent());
		if (test) {
			countTrue++;
		}
		if (dialogResult == null) {
			dialogResult = new DialogResult();
			CommonsFrame.loadFrame(FrameMain.getInstance().getFrameResult(), dialogResult);
		}
		dialogResult.addResult(executions, round++, countTrue / count);
		LemasLog.info("total = " + count + " acertos = " + countTrue);
		updateScree();
		wait--;
	}

	private void updateScree() {
		double total = (countFalse + countTrue);
		LemasLog.info("total test:" + total + " ok " + countTrue + "  nok " + countFalse + " % " + (countTrue / (countFalse + countTrue)));
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
