package org.disciullo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Object to hold the two lists being created from the file being parsed.
 * @author Anne
 *
 */
public class ResultListHolder {

	List<String> dupeList = new ArrayList<>();
	List<String> uniqueList = new ArrayList<>();

	public List<String> getDupeList() {
		return dupeList;
	}

	public void setDupeList(List<String> dupeList) {
		this.dupeList = dupeList;
	}

	public List<String> getUniqueList() {
		return uniqueList;
	}

	public void setUniqueList(List<String> uniqueList) {
		this.uniqueList = uniqueList;
	}

}
