package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.utils.ControlMessage;

public abstract class EmptySinkBase implements ResultsPublisherConsumer{
	
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	
	protected EclipseResultPublisher eclipseResultPublisher;
	
	public void setEclipseResultPublisher(EclipseResultPublisher eclipseResultPublisher) {
		this.eclipseResultPublisher = eclipseResultPublisher;
	}
	
	public EclipseResultPublisher getEclipseResultPublisher() {
		return eclipseResultPublisher;
	}
	
	
	
	@Override
	public void consumeResultsPublisherActual(Result result) {

		workflow.setTaskInProgess(this);
		
		consumeResultsPublisher(result);
		
		workflow.setTaskWaiting(this);
		
	}
	
	//generated for each incoming queue we are subscribed to
	private static Set<String> incomingQueueIds = new HashSet<>();
	static {
		incomingQueueIds.add("org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.ResultsPublisher");
	}

	private Set<String> terminatedIncomingQueueIds = new HashSet<>();

	@Override
	public void processTerminationMessage(ControlMessage cm) {

		terminatedIncomingQueueIds.add(cm.getCallerId());

		if (terminatedIncomingQueueIds.equals(incomingQueueIds)) {
			// FIXME TERMINATE TASK
			//
			//
			System.err.println(
					this.getClass().getName() + ":" + workflow.getName() + " sent termination signal!");
			workflow.terminate();
			}
		
	}
	
	
	/**
	 * Call this within consumeXYZ() to denote task blocked due to some reason
	 * @param reason
	 */
	protected void taskBlocked(String reason) {
		
		workflow.setTaskBlocked(this,reason);
		
	}
	
	/**
	 * Call this within consumeXYZ() to denote task is now unblocked
	 * @param reason
	 */
	protected void taskUnblocked() {
		
		workflow.setTaskUnblocked(this);
		
	}
	
	
	
}