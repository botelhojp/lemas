package lesma.model.direct.behaviour;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.List;

import lesma.model.direct.agent.TaskAgent;
import lesma.model.direct.ontology.SendTask;
import lesma.model.direct.ontology.Task;
import lesma.model.direct.ontology.TaskOntology;

import openjade.core.behaviours.CyclicTimerBehaviour;

import org.apache.log4j.Logger;


public class ResponseTaskBehaviour extends CyclicTimerBehaviour {

	private static final long serialVersionUID = 1L;
	
	protected static Logger log = Logger.getLogger(ResponseTaskBehaviour.class);

	private TaskAgent myAgent;

	public ResponseTaskBehaviour(Agent agent) {
		super(agent, 100);
		myAgent = (TaskAgent) agent;
	}

	@Override
	public void run() {
		List<Task> tasks = myAgent.getTasks().get(TaskAgent.TASK_TO_COMPLETED);
		if (!tasks.isEmpty()){
			Task task = tasks.remove(0);
			
			SendTask action = new SendTask();
			action.setTask(task);
			
			ACLMessage msg = new ACLMessage(ACLMessage.CONFIRM);
			msg.setSender(myAgent.getAID());
			msg.addReceiver(task.getTaskSender());
			
			myAgent.fillContent(msg, action, myAgent.getCodec(), TaskOntology.getInstance());
			((TaskAgent) myAgent).sendMessage(msg);
		}
	}
}
