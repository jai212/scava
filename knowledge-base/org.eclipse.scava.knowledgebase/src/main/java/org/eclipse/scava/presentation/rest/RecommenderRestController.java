/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.presentation.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationType;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Juri Di Rocco
 *
 */
@RestController
@RequestMapping("/api/recommendation")

public class RecommenderRestController {
	@Autowired
	@Qualifier("Dependency")
	private ISimilarityCalculator dependency;
	@Autowired
	@Qualifier("Readme")
	private ISimilarityCalculator readme;
	@Autowired
	private IRecommenderManager recommenderManager;
	private static final Logger logger = LoggerFactory.getLogger(RecommenderRestController.class);

	@RequestMapping(value = "cluster/{sim_method}", produces = "application/json", method = RequestMethod.GET)
	public List<Cluster> getClusters(
			@ApiParam(value = "String value which can be Compound, CrossSim, Dependency, Readme, RepoPalCompound or RepoPalCompoundV2.", required = true) @PathVariable("sim_method") String simFunction) {
		return recommenderManager.getClusters(simFunction);
	}

	@RequestMapping(value = "recommended_library", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Recommendation getRecommendedLibraries(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.RECOMMENDED_LIBRARY);
	}

	@RequestMapping(value = "recommended_API_call", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Recommendation getApiCallRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.API_CALL);
	}

	@RequestMapping(value = "recommended_API_documentation", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Recommendation getApiDocumentationRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		// TODO It has to be integrate from Riccardo
		return null;
	}

	@ApiOperation(value = "This resource is used to retrieve projects that are similar to a given project.")
	@RequestMapping(value = "similar/p/{id}/m/{sim_method}/n/{num}", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody List<Artifact> getSimilarProject(
			@ApiParam(value = "results are computed by using the similarity function specified as parameter.  "
					+ "String value which can be Compound, CrossSim, Dependency, Readme, RepoPalCompound or RepoPalCompoundV2.", required = true) @PathVariable("sim_method") String simFunction,
			@ApiParam(value = "This parameter specifies the string ID of a specific artifact.", required = true) @PathVariable("id") String id,
			@ApiParam(value = "Number of expected projects in the results", required = true) @PathVariable("num") int num) {

		return recommenderManager.getSimilarProjects(id, simFunction, num);
	}

	@RequestMapping(value = "query", method = RequestMethod.GET)
	public Query getQuery() {
		Query q = new Query();
		q.setCurrentMethodCode("public static KnowledgeBase readKnowledgeBase(List<RuleResource> resources) {\n"
				+ "	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();\n"
				+ "	for (RuleResource res: resources) {\n" + "		try {\n"
				+ "			kbuilder.add(ResourceFactory.newClassPathResource(res.getRuleResourceFile()), res.getResType());\n"
				+ "		} catch (Exception ex) {\n"
				+ "			kbuilder.add(ResourceFactory.newFileResource(res.getRuleResourceFile()), res.getResType());\n"
				+ "		}\n" + "	}\n" + "	KnowledgeBuilderErrors errors = kbuilder.getErrors();\n"
				+ "	if (errors.size() > 0) {\n" + "		for (KnowledgeBuilderError error: errors) {\n"
				+ "			System.err.println(error);\n" + "		}\n"
				+ "		throw new IllegalArgumentException(\"Could not parse knowledge.\");\n" + "	}\n"
				+ "	KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();\n"
				+ "	kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());\n" + "	return kbase;\n" + "}");
		return q;
	}

	@RequestMapping(value = "/pattern/{file_name:.+}", method = RequestMethod.GET)
	public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
		try {
			File file = new ClassPathResource("CLAMS_PATTERN/" + fileName).getFile();
			// get your file as InputStream
			InputStream is = new FileInputStream(file);
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {
			logger.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
			throw new RuntimeException("IOError writing file to output stream");
		}

	}
}