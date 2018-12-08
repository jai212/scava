package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoClonerBase implements MdeTechnologyRepoEntriesConsumer, Task{
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoCloner:"+workflow.getName();
	}
	
	protected MdeTechnologyClonedRepoEntriesForAuthorCounter mdeTechnologyClonedRepoEntriesForAuthorCounter;
	
	protected void setMdeTechnologyClonedRepoEntriesForAuthorCounter(MdeTechnologyClonedRepoEntriesForAuthorCounter mdeTechnologyClonedRepoEntriesForAuthorCounter) {
		this.mdeTechnologyClonedRepoEntriesForAuthorCounter = mdeTechnologyClonedRepoEntriesForAuthorCounter;
	}
	
	private MdeTechnologyClonedRepoEntriesForAuthorCounter getMdeTechnologyClonedRepoEntriesForAuthorCounter() {
		return mdeTechnologyClonedRepoEntriesForAuthorCounter;
	}
	
	public void sendToMdeTechnologyClonedRepoEntriesForAuthorCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		getMdeTechnologyClonedRepoEntriesForAuthorCounter().send(extensionKeywordStargazersRemoteRepoUrlTuple, this.getClass().getName());
	}
	
	protected MdeTechnologyClonedRepoEntriesForFileCounter mdeTechnologyClonedRepoEntriesForFileCounter;
	
	protected void setMdeTechnologyClonedRepoEntriesForFileCounter(MdeTechnologyClonedRepoEntriesForFileCounter mdeTechnologyClonedRepoEntriesForFileCounter) {
		this.mdeTechnologyClonedRepoEntriesForFileCounter = mdeTechnologyClonedRepoEntriesForFileCounter;
	}
	
	private MdeTechnologyClonedRepoEntriesForFileCounter getMdeTechnologyClonedRepoEntriesForFileCounter() {
		return mdeTechnologyClonedRepoEntriesForFileCounter;
	}
	
	public void sendToMdeTechnologyClonedRepoEntriesForFileCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		getMdeTechnologyClonedRepoEntriesForFileCounter().send(extensionKeywordStargazersRemoteRepoUrlTuple, this.getClass().getName());
	}
	
	protected MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	
	protected void setMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter) {
		this.mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter = mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	}
	
	private MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter() {
		return mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	}
	
	public void sendToMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter().send(extensionKeywordStargazersRemoteRepoUrlTuple, this.getClass().getName());
	}
	
	
	
	protected ResultsBroadcaster resultsBroadcaster;
	
	protected void setResultsBroadcaster(ResultsBroadcaster resultsBroadcaster) {
		this.resultsBroadcaster = resultsBroadcaster;
	}
	
	private ResultsBroadcaster getResultsBroadcaster() {
		return resultsBroadcaster;
	}
	
	public void sendToResultsBroadcaster(Object[] row){
		getResultsBroadcaster().send(row);
	}
	
	
	
	@Override
	public void consumeMdeTechnologyRepoEntriesActual(ExtensionKeywordStargazersTuple extensionKeywordStargazersTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologyRepoEntries(extensionKeywordStargazersTuple);
		
		workflow.setTaskWaiting(this);
		
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