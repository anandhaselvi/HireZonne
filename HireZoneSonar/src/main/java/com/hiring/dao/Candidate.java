package com.hiring.dao;

import org.json.JSONObject;

public interface Candidate {

	


	JSONObject candidateMap(String userId);

	JSONObject insertApplicant(JSONObject json);

	JSONObject nameById(String candidateid);

	JSONObject updateCandidate(JSONObject jsonObject);

	JSONObject candidateList();
}
