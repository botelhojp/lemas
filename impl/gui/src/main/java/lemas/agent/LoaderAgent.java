package lemas.agent;

import lemas.agent.behaviour.LoadeBehaviour;
import openjade.core.OpenAgent;

public class LoaderAgent extends OpenAgent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		super.setup();
		moveContainer(OpenAgent.MAIN_CONTAINER);
		for (int index = 0; index < getArguments().length; index++) {
			System.out.println((String) getArguments()[index]);
		}
		addBehaviour(new LoadeBehaviour(this));
	}

}
