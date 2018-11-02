package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

public interface MdeTechnologyRepoAuthorCountEntriesConsumer {

	public void consumeMdeTechnologyRepoAuthorCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
	
	/**
	 * wraps consumeMdeTechnologyRepoAuthorCountEntries() to provide task status information
	 */
	public void consumeMdeTechnologyRepoAuthorCountEntriesActual(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);

}