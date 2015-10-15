package lemas.agent;

import java.lang.reflect.Method;

import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import openjade.core.annotation.ReceiveMatchMessage;
import openjade.core.annotation.ReceiveSignerMessage;
import openjade.core.annotation.ReceiveSimpleMessage;
import openjade.core.behaviours.BehaviourException;
import openjade.ontology.OpenJadeOntology;

public class AgentOO extends LemasAgent {
	
	private static final long serialVersionUID = 1L;
	
	private String localName;
	private AID myAID;

	public AgentOO(String localName, Object[] param) {
		setArguments(param);
		myAID = new AID("" + localName, false);
		this.localName = localName;
		super.setup();
	}
	
	@Override
	public void sendMessage(ACLMessage msg) {
		countMessages++;
		AID to = (AID) msg.getAllReceiver().next();
		if (to.getLocalName().equals("lemas_loader")){
			AgentLoader.getInstance().getTestMessage(msg);	
		}else{
			AgentCache.get(to.getLocalName()).message(msg);
//			super.sendMessage(msg);
		}
	}
	
	@Override
	public ACLMessage sendMessage(AID to, int performative, String conversationId, AgentAction action) {
		ACLMessage message = new ACLMessage(performative);
		message.setSender(_getAID());
		message.addReceiver(to);
		message.setConversationId(conversationId);
		fillContent(message, action, getCodec(), OpenJadeOntology.getInstance());
		AgentCache.get(to.getLocalName()).message(message);
		return message;
	}
	
	@Override
	public void test(AID aid){
		ACLMessage msg = makeMessage(new AID("lemas_loader", false), ConversationId.TEST, "" + this.getTrustModel().test(aid));
		AgentLoader.getInstance().getTestMessage(msg);
	}	
	
	@Override
	public String _getLocalName(){
		return localName;
	}
	
	@Override
	public AID _getAID(){
		return myAID;
	}

	public void message(ACLMessage message) {
		if (message != null) {
			try {
				Method[] methods = this.getClass().getMethods();
				for (Method method : methods) {
					method.setAccessible(true);
					if (method.isAnnotationPresent(ReceiveSignerMessage.class)) {
						method.invoke(this, message);
						return;
					}
					if (method.isAnnotationPresent(ReceiveSimpleMessage.class)) {
						ReceiveSimpleMessage messageMatch = method.getAnnotation(ReceiveSimpleMessage.class);
						String conversationId = messageMatch.conversationId();
						int[] performatives = messageMatch.performative();
						for (int performative : performatives) {
							MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(performative), MessageTemplate.MatchConversationId(conversationId));
							if (messageTemplate.match(message)) {
								method.invoke(this, message);
								return;
							}
						}						
					}					
					if (method.isAnnotationPresent(ReceiveMatchMessage.class)) {
						ReceiveMatchMessage messageMatch = method.getAnnotation(ReceiveMatchMessage.class);
						@SuppressWarnings("unchecked")
						Method getInstance = messageMatch.ontology().getMethod("getInstance");
						getInstance.setAccessible(true);
						Ontology ontology = (Ontology) getInstance.invoke(null);
						Codec codec = (Codec) messageMatch.codec().newInstance();
						String[] conversationsId = messageMatch.conversationId();
						int[] performatives = messageMatch.performative();
						for (String conversationId : conversationsId) {
							for (int performative : performatives) {								
								MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchLanguage(codec.getName()), MessageTemplate.MatchOntology(ontology.getName()));
								MessageTemplate mt2 = MessageTemplate.and(MessageTemplate.MatchPerformative(performative), MessageTemplate.MatchConversationId(conversationId));
								if (MessageTemplate.and(mt1, mt2).match(message)) {
									ContentElement ce = this.extractContent(message, codec, ontology);
									method.invoke(this, message, ce);
									return;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				throw new BehaviourException(e.getMessage(), e);
			}
		}
		
	}
	
	private ACLMessage makeMessage(AID receiver, String conversation, String content){
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setSender(this._getAID());
		msg.setConversationId(conversation);
		msg.addReceiver(receiver);
		msg.setContent(content);		
		return msg;
	}
	
}
