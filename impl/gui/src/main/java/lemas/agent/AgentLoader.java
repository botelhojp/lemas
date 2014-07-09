package lemas.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.HashSet;
import java.util.Set;

import lemas.agent.behaviour.LoaderBehaviour;
import lemas.form.DialogResult;
import lemas.form.FrameMain;
import lemas.model.LemasLog;
import lemas.util.CommonsFrame;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveSimpleMessage;
import openjade.trust.ITrustModel;

public class AgentLoader extends OpenAgent {

	private static final long serialVersionUID = 1L;
	private Set<AID> wait = new HashSet<AID>();
	private double countTrue = 0;
	private double countFalse = 0;
	private DialogResult dialogResult;
	private double count = 0;
	private double round = 0;
	private LoaderBehaviour loader;
	
	@Override
	protected void setup() {
//		setCodec(new SLCodec());
		super.setup();
		loader = new LoaderBehaviour(this, getTrustModelClass());
		addBehaviour(loader);
	}
	
	@ReceiveSimpleMessage(conversationId = ConversationId.DO_DELETE)
	public void dead(ACLMessage message) {
		loader.removerCache(message.getSender());
	}	


	@ReceiveSimpleMessage(conversationId = ConversationId.LOADER)
	public void getMessage(ACLMessage msg) {
		if (wait.contains(msg.getSender())) {
			wait.remove(msg.getSender());
		} else {
			throw new OpenJadeException("Agente nao autorizado [" + msg.getSender().getLocalName() + "]");
		}
	}

	@ReceiveSimpleMessage(conversationId = ConversationId.TEST)
	public void getTestMessage(ACLMessage msg) {
		Boolean test = Boolean.parseBoolean(msg.getContent());
		if (test){
			countTrue++;
		}
		if (dialogResult == null) {
			dialogResult = new DialogResult();
			CommonsFrame.loadFrame(FrameMain.getInstance().getFrameResult(), dialogResult);
		} else {
			++count;
			if (count % 50 == 0) {
				dialogResult.addResult(0, round++, countTrue/count);
			}
		}
		updateScree();
	}

	private void updateScree() {
		double total = (countFalse + countTrue);
		LemasLog.info("total test:" + total + " ok " + countTrue + "  nok " + countFalse + " % " + (countTrue / (countFalse + countTrue)));
	}

	public void waiting(AID aid) {
		wait.add(aid);
	}

	public boolean nowait() {
		return wait.isEmpty();
	}

	@SuppressWarnings("unchecked")
	private Class<ITrustModel> getTrustModelClass() {
		try {
			if (getArguments().length != 2) {
				throw new RuntimeException("Modelo de Confiancao nao selecionado");
			}
			String clazz = getArguments()[0].toString();
			return (Class<ITrustModel>) Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Modelo de Confiancao nao selecionado", e);
		}
	}
}
