package lesma.model.direct.ontology;


import jade.content.AgentAction;

/**
* Protege name: SendTask
* @author ontology bean generator
* @version 2013/03/31, 15:37:40
*/
@SuppressWarnings("all")
public class SendTask implements AgentAction {

   /**
* Protege name: task
   */
   private Task task;
   public void setTask(Task value) { 
    this.task=value;
   }
   public Task getTask() {
     return this.task;
   }

}
