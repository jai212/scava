package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;



public class MdeTechnologyExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		MdeTechnologyExample app = new MdeTechnologyExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected MdeTechnologies mdeTechnologies;
	protected MdeTechnologyRepoEntries mdeTechnologyRepoEntries;
	protected MdeTechnologyClonedRepoEntriesForAuthorCounter mdeTechnologyClonedRepoEntriesForAuthorCounter;
	protected MdeTechnologyClonedRepoEntriesForFileCounter mdeTechnologyClonedRepoEntriesForFileCounter;
	protected MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	protected MdeTechnologyRepoAuthorCountEntries mdeTechnologyRepoAuthorCountEntries;
	protected MdeTechnologyRepoFileCountEntries mdeTechnologyRepoFileCountEntries;
	protected MdeTechnologyRepoOwnerPopularityCountEntries mdeTechnologyRepoOwnerPopularityCountEntries;
	protected EclipseResultPublisher eclipseResultPublisher;
	protected EclipseTaskStatusPublisher eclipseTaskStatusPublisher;
	
	private boolean createBroker = true;
	
	// tasks
	protected MdeTechnologySource mdeTechnologySource;
	protected MdeTechnologyRepoFetcher mdeTechnologyRepoFetcher;
	protected MdeTechnologyRepoCloner mdeTechnologyRepoCloner;
	protected MdeTechnologyRepoAuthorCounter mdeTechnologyRepoAuthorCounter;
	protected MdeTechnologyRepoFileCounter mdeTechnologyRepoFileCounter;
	protected MdeTechnologyRepoOwnerPopularityCounter mdeTechnologyRepoOwnerPopularityCounter;
	protected MdeTechnologyRepoAuthorCountPrinter mdeTechnologyRepoAuthorCountPrinter;
	protected MdeTechnologyRepoFileCountPrinter mdeTechnologyRepoFileCountPrinter;
	protected MdeTechnologyRepoOwnerPopularityCountPrinter mdeTechnologyRepoOwnerPopularityCountPrinter;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public MdeTechnologyExample() {
		this.name = "MdeTechnologyExample";
	}
	
	public void createBroker(boolean createBroker) {
		this.createBroker = createBroker;
	}
	
	public void run() throws Exception {
	
		if (isMaster()) {
			cache = new Cache(this);
			if (createBroker) {
				brokerService = new BrokerService();
				brokerService.setUseJmx(true);
				brokerService.addConnector(getBroker());
				brokerService.start();
			}
		}

		eclipseResultPublisher = new EclipseResultPublisher(this);
		eclipseTaskStatusPublisher = new EclipseTaskStatusPublisher(this);
		
//TODO test of task status until it is integrated to ui
//		eclipseTaskStatusPublisher.addConsumer(new EclipseTaskStatusPublisherConsumer() {
//			@Override
//			public void consumeEclipseTaskStatusPublisher(TaskStatus status) {
//				System.err.println(status.getCaller()+" : "+status.getStatus()+" : "+status.getReason());
//			}
//		});
//
		
		mdeTechnologies = new MdeTechnologies(this);
		mdeTechnologyRepoEntries = new MdeTechnologyRepoEntries(this);
		mdeTechnologyClonedRepoEntriesForAuthorCounter = new MdeTechnologyClonedRepoEntriesForAuthorCounter(this);
		mdeTechnologyClonedRepoEntriesForFileCounter = new MdeTechnologyClonedRepoEntriesForFileCounter(this);
		mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter = new MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(this);
		mdeTechnologyRepoAuthorCountEntries = new MdeTechnologyRepoAuthorCountEntries(this);
		mdeTechnologyRepoFileCountEntries = new MdeTechnologyRepoFileCountEntries(this);
		mdeTechnologyRepoOwnerPopularityCountEntries = new MdeTechnologyRepoOwnerPopularityCountEntries(this);
		
		
	
				
		mdeTechnologySource = new MdeTechnologySource();
		mdeTechnologySource.setWorkflow(this);
		
		mdeTechnologySource.setMdeTechnologies(mdeTechnologies);
		
				
		
		if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoFetcher")) {
	
				
		mdeTechnologyRepoFetcher = new MdeTechnologyRepoFetcher();
		mdeTechnologyRepoFetcher.setWorkflow(this);
		
			mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher, MdeTechnologyRepoFetcher.class.getName());			
	
		mdeTechnologyRepoFetcher.setMdeTechnologyRepoEntries(mdeTechnologyRepoEntries);
		}
		else if(isMaster()){
			mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher, MdeTechnologyRepoFetcher.class.getName());			
		}
		
				
		
		if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoCloner")) {
	
				
		mdeTechnologyRepoCloner = new MdeTechnologyRepoCloner();
		mdeTechnologyRepoCloner.setWorkflow(this);
		
			mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoCloner, MdeTechnologyRepoCloner.class.getName());			
	
		mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForAuthorCounter(mdeTechnologyClonedRepoEntriesForAuthorCounter);
		mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForFileCounter(mdeTechnologyClonedRepoEntriesForFileCounter);
		mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter);
		}
		else if(isMaster()){
			mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoCloner, MdeTechnologyRepoCloner.class.getName());			
		}
		
				
		
		if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoAuthorCounter")) {
	
				
		mdeTechnologyRepoAuthorCounter = new MdeTechnologyRepoAuthorCounter();
		mdeTechnologyRepoAuthorCounter.setWorkflow(this);
		
			mdeTechnologyClonedRepoEntriesForAuthorCounter.addConsumer(mdeTechnologyRepoAuthorCounter, MdeTechnologyRepoAuthorCounter.class.getName());			
	
		mdeTechnologyRepoAuthorCounter.setMdeTechnologyRepoAuthorCountEntries(mdeTechnologyRepoAuthorCountEntries);
		}
		else if(isMaster()){
			mdeTechnologyClonedRepoEntriesForAuthorCounter.addConsumer(mdeTechnologyRepoAuthorCounter, MdeTechnologyRepoAuthorCounter.class.getName());			
		}
		
				
		
		if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoFileCounter")) {
	
				
		mdeTechnologyRepoFileCounter = new MdeTechnologyRepoFileCounter();
		mdeTechnologyRepoFileCounter.setWorkflow(this);
		
			mdeTechnologyClonedRepoEntriesForFileCounter.addConsumer(mdeTechnologyRepoFileCounter, MdeTechnologyRepoFileCounter.class.getName());			
	
		mdeTechnologyRepoFileCounter.setMdeTechnologyRepoFileCountEntries(mdeTechnologyRepoFileCountEntries);
		}
		else if(isMaster()){
			mdeTechnologyClonedRepoEntriesForFileCounter.addConsumer(mdeTechnologyRepoFileCounter, MdeTechnologyRepoFileCounter.class.getName());			
		}
		
				
		
		if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoOwnerPopularityCounter")) {
	
				
		mdeTechnologyRepoOwnerPopularityCounter = new MdeTechnologyRepoOwnerPopularityCounter();
		mdeTechnologyRepoOwnerPopularityCounter.setWorkflow(this);
		
			mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter.addConsumer(mdeTechnologyRepoOwnerPopularityCounter, MdeTechnologyRepoOwnerPopularityCounter.class.getName());			
	
		mdeTechnologyRepoOwnerPopularityCounter.setMdeTechnologyRepoOwnerPopularityCountEntries(mdeTechnologyRepoOwnerPopularityCountEntries);
		}
		else if(isMaster()){
			mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter.addConsumer(mdeTechnologyRepoOwnerPopularityCounter, MdeTechnologyRepoOwnerPopularityCounter.class.getName());			
		}
		
				
		
	
		if (isMaster()) {
				
		mdeTechnologyRepoAuthorCountPrinter = new MdeTechnologyRepoAuthorCountPrinter();
		mdeTechnologyRepoAuthorCountPrinter.setWorkflow(this);
		}
		
			mdeTechnologyRepoAuthorCountEntries.addConsumer(mdeTechnologyRepoAuthorCountPrinter, MdeTechnologyRepoAuthorCountPrinter.class.getName());			
		if(mdeTechnologyRepoAuthorCounter!=null)		
			mdeTechnologyRepoAuthorCounter.setEclipseResultPublisher(eclipseResultPublisher);
	
		
				
		
	
		if (isMaster()) {
				
		mdeTechnologyRepoFileCountPrinter = new MdeTechnologyRepoFileCountPrinter();
		mdeTechnologyRepoFileCountPrinter.setWorkflow(this);
		}
		
			mdeTechnologyRepoFileCountEntries.addConsumer(mdeTechnologyRepoFileCountPrinter, MdeTechnologyRepoFileCountPrinter.class.getName());			
		if(mdeTechnologyRepoFileCounter!=null)		
			mdeTechnologyRepoFileCounter.setEclipseResultPublisher(eclipseResultPublisher);
	
		
				
		
	
		if (isMaster()) {
				
		mdeTechnologyRepoOwnerPopularityCountPrinter = new MdeTechnologyRepoOwnerPopularityCountPrinter();
		mdeTechnologyRepoOwnerPopularityCountPrinter.setWorkflow(this);
		}
		
			mdeTechnologyRepoOwnerPopularityCountEntries.addConsumer(mdeTechnologyRepoOwnerPopularityCountPrinter, MdeTechnologyRepoOwnerPopularityCountPrinter.class.getName());			
		if(mdeTechnologyRepoOwnerPopularityCounter!=null)		
			mdeTechnologyRepoOwnerPopularityCounter.setEclipseResultPublisher(eclipseResultPublisher);
	
		
				
		
		if (isMaster()){
			mdeTechnologySource.produce();
		}
	}
	
	public MdeTechnologies getMdeTechnologies() {
		return mdeTechnologies;
	}
	public MdeTechnologyRepoEntries getMdeTechnologyRepoEntries() {
		return mdeTechnologyRepoEntries;
	}
	public MdeTechnologyClonedRepoEntriesForAuthorCounter getMdeTechnologyClonedRepoEntriesForAuthorCounter() {
		return mdeTechnologyClonedRepoEntriesForAuthorCounter;
	}
	public MdeTechnologyClonedRepoEntriesForFileCounter getMdeTechnologyClonedRepoEntriesForFileCounter() {
		return mdeTechnologyClonedRepoEntriesForFileCounter;
	}
	public MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter() {
		return mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	}
	public MdeTechnologyRepoAuthorCountEntries getMdeTechnologyRepoAuthorCountEntries() {
		return mdeTechnologyRepoAuthorCountEntries;
	}
	public MdeTechnologyRepoFileCountEntries getMdeTechnologyRepoFileCountEntries() {
		return mdeTechnologyRepoFileCountEntries;
	}
	public MdeTechnologyRepoOwnerPopularityCountEntries getMdeTechnologyRepoOwnerPopularityCountEntries() {
		return mdeTechnologyRepoOwnerPopularityCountEntries;
	}
	
	public MdeTechnologySource getMdeTechnologySource() {
		return mdeTechnologySource;
	}
	public MdeTechnologyRepoFetcher getMdeTechnologyRepoFetcher() {
		return mdeTechnologyRepoFetcher;
	}
	public MdeTechnologyRepoCloner getMdeTechnologyRepoCloner() {
		return mdeTechnologyRepoCloner;
	}
	public MdeTechnologyRepoAuthorCounter getMdeTechnologyRepoAuthorCounter() {
		return mdeTechnologyRepoAuthorCounter;
	}
	public MdeTechnologyRepoFileCounter getMdeTechnologyRepoFileCounter() {
		return mdeTechnologyRepoFileCounter;
	}
	public MdeTechnologyRepoOwnerPopularityCounter getMdeTechnologyRepoOwnerPopularityCounter() {
		return mdeTechnologyRepoOwnerPopularityCounter;
	}
	public MdeTechnologyRepoAuthorCountPrinter getMdeTechnologyRepoAuthorCountPrinter() {
		return mdeTechnologyRepoAuthorCountPrinter;
	}
	public MdeTechnologyRepoFileCountPrinter getMdeTechnologyRepoFileCountPrinter() {
		return mdeTechnologyRepoFileCountPrinter;
	}
	public MdeTechnologyRepoOwnerPopularityCountPrinter getMdeTechnologyRepoOwnerPopularityCountPrinter() {
		return mdeTechnologyRepoOwnerPopularityCountPrinter;
	}
	
	public void setTaskInProgess(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getClass().getName(), ""));
	}

	public void setTaskWaiting(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.WAITING, caller.getClass().getName(), ""));
	}

	public void setTaskBlocked(Object caller, String reason) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.BLOCKED, caller.getClass().getName(), reason));
	}

	public void setTaskUnblocked(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getClass().getName(), ""));
	}

}