package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Job;

public class MdeTechnologyClonedRepoEntriesForAuthorCounter {
	
	protected Map<String, Destination> destination;
	protected Map<String, Destination> pre;
	protected Map<String, Destination> post;
	protected Session session;
	protected Workflow workflow;
	
	public MdeTechnologyClonedRepoEntriesForAuthorCounter(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = new HashMap<String, Destination>();
		pre = new HashMap<String, Destination>();
		post = new HashMap<String, Destination>();
		
	}
	
	public void send(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple, String taskId) {
		try {
			Destination d = null;
			// if the sender is one of the targets of this channel, it has re-sent a message
			// so it should only be put in the relevant physical queue
			if ((d = pre.get(taskId)) != null) {
				MessageProducer producer = session.createProducer(d);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				ObjectMessage message = session.createObjectMessage();
				extensionKeywordStargazersRemoteRepoUrlTuple.setDestination("MdeTechnologyClonedRepoEntriesForAuthorCounter");
				message.setObject(extensionKeywordStargazersRemoteRepoUrlTuple);
				producer.send(message);
			} else
				// otherwise the sender must be the source of this channel so intends to
				// propagate its messages to all the physical queues
				for (Entry<String, Destination> e : pre.entrySet()) {			
					MessageProducer producer = session.createProducer(e.getValue());
					producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
					ObjectMessage message = session.createObjectMessage();
					extensionKeywordStargazersRemoteRepoUrlTuple.setDestination("MdeTechnologyClonedRepoEntriesForAuthorCounter");
					message.setObject(extensionKeywordStargazersRemoteRepoUrlTuple);
					producer.send(message);
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void addConsumer(MdeTechnologyClonedRepoEntriesForAuthorCounterConsumer consumer, String consumerId) throws Exception {

		// XXX use runtime class as ID of consumer as tasks are unique
	
		Destination preQueue = session.createQueue("MdeTechnologyClonedRepoEntriesForAuthorCounterPre-" + consumerId);
		pre.put(consumerId, preQueue);	
	
		Destination destQueue = session.createQueue("MdeTechnologyClonedRepoEntriesForAuthorCounterDestination-" + consumerId);
		destination.put(consumerId, destQueue);	
	
		Destination postQueue = session.createQueue("MdeTechnologyClonedRepoEntriesForAuthorCounterPost-" + consumerId);
		post.put(consumerId, postQueue);
	
		//
	
		if (workflow.isMaster()) {
			MessageConsumer preConsumer = session.createConsumer(preQueue);
			preConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						
						Job job = (Job) ((ObjectMessage) message).getObject();
						if (workflow.getCache().hasCachedOutputs(job)) {
							for (Job output : workflow.getCache().getCachedOutputs(job)) {
								if (output.getDestination().equals("MdeTechnologies")) {
									((MdeTechnologyExample) workflow).getMdeTechnologies().send((ExtensionKeywordTuple) output, this.getClass().getName());
								}
								if (output.getDestination().equals("MdeTechnologyRepoEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoEntries().send((ExtensionKeywordStargazersTuple) output, this.getClass().getName());
								}
								if (output.getDestination().equals("MdeTechnologyClonedRepoEntriesForAuthorCounter")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyClonedRepoEntriesForAuthorCounter().send((ExtensionKeywordStargazersRemoteRepoUrlTuple) output, this.getClass().getName());
								}
								if (output.getDestination().equals("MdeTechnologyClonedRepoEntriesForFileCounter")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyClonedRepoEntriesForFileCounter().send((ExtensionKeywordStargazersRemoteRepoUrlTuple) output, this.getClass().getName());
								}
								if (output.getDestination().equals("MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter().send((ExtensionKeywordStargazersRemoteRepoUrlTuple) output, this.getClass().getName());
								}
								if (output.getDestination().equals("MdeTechnologyRepoAuthorCountEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoAuthorCountEntries().send((ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) output, this.getClass().getName());
								}
								if (output.getDestination().equals("MdeTechnologyRepoFileCountEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoFileCountEntries().send((ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) output, this.getClass().getName());
								}
								if (output.getDestination().equals("MdeTechnologyRepoOwnerPopularityCountEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoOwnerPopularityCountEntries().send((ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) output, this.getClass().getName());
								}
							}
						}
						else {
							MessageProducer producer = session.createProducer(destQueue);
							producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
							producer.send(message);
						}
						
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
			});
			
			MessageConsumer destinationConsumer = session.createConsumer(destQueue);
			destinationConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						ObjectMessage objectMessage = (ObjectMessage) message;
						Job job = (Job) objectMessage.getObject();
						if (!job.isCached()) {
							workflow.getCache().cache(job);
						}
						MessageProducer producer = session.createProducer(postQueue);
						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						producer.send(message);
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
			});
		}
			
		//only connect if the consumer exists (for example it will not in a master_bare situation)
		if(consumer!=null) {
		
		MessageConsumer messageConsumer = session.createConsumer(postQueue);
		messageConsumer.setMessageListener(new MessageListener() {
	
			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple = (ExtensionKeywordStargazersRemoteRepoUrlTuple) objectMessage.getObject();
					consumer.consumeMdeTechnologyClonedRepoEntriesForAuthorCounterActual(extensionKeywordStargazersRemoteRepoUrlTuple);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}	
		});
	}
	
	}
	
}