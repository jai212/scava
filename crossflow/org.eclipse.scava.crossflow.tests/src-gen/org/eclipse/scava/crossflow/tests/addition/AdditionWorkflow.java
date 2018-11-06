package org.eclipse.scava.crossflow.tests.addition;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;



public class AdditionWorkflow extends Workflow {
	
	public static void main(String[] args) throws Exception {
		AdditionWorkflow app = new AdditionWorkflow();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected Additions additions;
	protected AdditionResults additionResults;
	protected EclipseResultPublisher eclipseResultPublisher;
	protected EclipseTaskStatusPublisher eclipseTaskStatusPublisher;
	
	private boolean createBroker = true;
	
	// tasks
	protected NumberPairSource numberPairSource;
	protected Adder adder;
	protected AdditionResultsSink additionResultsSink;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public AdditionWorkflow() {
		this.name = "AdditionWorkflow";
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
		
		additions = new Additions(this);
		additionResults = new AdditionResults(this);
		
		
	
				
		numberPairSource = new NumberPairSource();
		numberPairSource.setWorkflow(this);
		
		numberPairSource.setAdditions(additions);
		
				
		
		if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("Adder")) {
	
				
		adder = new Adder();
		adder.setWorkflow(this);
		
			additions.addConsumer(adder, Adder.class.getName());			
	
		adder.setAdditionResults(additionResults);
		}
		else if(isMaster()){
			additions.addConsumer(adder, Adder.class.getName());			
		}
		
				
		
	
		if (isMaster()) {
				
		additionResultsSink = new AdditionResultsSink();
		additionResultsSink.setWorkflow(this);
		}
		
			additionResults.addConsumer(additionResultsSink, AdditionResultsSink.class.getName());			
		if(adder!=null)		
			adder.setEclipseResultPublisher(eclipseResultPublisher);
	
		
				
		
		if (isMaster()){
			numberPairSource.produce();
		}
	}
	
	public Additions getAdditions() {
		return additions;
	}
	public AdditionResults getAdditionResults() {
		return additionResults;
	}
	
	public NumberPairSource getNumberPairSource() {
		return numberPairSource;
	}
	public Adder getAdder() {
		return adder;
	}
	public AdditionResultsSink getAdditionResultsSink() {
		return additionResultsSink;
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