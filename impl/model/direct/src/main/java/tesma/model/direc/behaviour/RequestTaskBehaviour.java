package tesma.model.direc.behaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.List;

import openjade.core.behaviours.CyclicTimerBehaviour;
import openjade.trust.model.Pair;

import org.apache.log4j.Logger;

import tesma.model.direc.agent.TaskAgent;
import tesma.model.direc.ontology.SendTask;
import tesma.model.direc.ontology.Task;
import tesma.model.direc.ontology.TaskOntology;
/**
 * Comportamento para remover as tarefas no estado delegate
 */
public class RequestTaskBehaviour extends CyclicTimerBehaviour {

	private static final long serialVersionUID = 1L;

	protected static Logger log = Logger.getLogger(RequestTaskBehaviour.class);

	private TaskAgent myAgent;

	public RequestTaskBehaviour(Agent agent) {
		super(agent, 100, 100);
		myAgent = (TaskAgent) agent;
	}

	@Override
	public void run() {
		List<Task> tasks = myAgent.getTasks().get(TaskAgent.TASK_TO_DELEGATE);
		try {
			if (!tasks.isEmpty()) {
				AID receive = null;				
				String[] terms = {"completed", "points"};
				List<Pair> pairs = myAgent.getTrustModel().getPairs(terms);
				if (pairs == null || pairs.isEmpty()){
					receive = getRandomAgent();
					log.debug("random:" + receive.getLocalName());
				}else{
					receive = pairs.get(pairs.size()-1).getAid();
					log.debug("best:" + receive.getLocalName());
				}
				if (receive != null) {
					Task task = tasks.remove(0);
					SendTask action = new SendTask();
					action.setTask(task);
					ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
					msg.setSender(myAgent.getAID());
					msg.addReceiver(receive);

					myAgent.fillContent(msg, action, myAgent.getCodec(), TaskOntology.getInstance());
					((TaskAgent)myAgent).sendMessage(msg);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Retorna um agente aleatório
	 * @return
	 */
	private AID getRandomAgent() {
		List<AID>receives = myAgent.getAIDByService(TaskAgent.SERVICE_WORKER);
		return (!receives.isEmpty()) ? receives.get((int) (Math.random() * receives.size())) : null;
	}
}
