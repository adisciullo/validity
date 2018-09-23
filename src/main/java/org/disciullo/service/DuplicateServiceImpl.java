package org.disciullo.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.disciullo.model.Headers;
import org.disciullo.model.ResultListHolder;
import org.springframework.stereotype.Service;

@Service("duplicateService")
public class DuplicateServiceImpl implements DuplicateService {

	private static final int THRESHOLD = 5;


	@Override
	public ResultListHolder loadData(File file) throws IOException {
		Reader in = null;
		try {
			in = new FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(Headers.class).parse(in);
			return readRecords(records);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Iterate through the records, find the duplicates and place them in the proper list (duplicate or unique).
	 * @param records
	 */
	private ResultListHolder readRecords(Iterable<CSVRecord> records) {
		//This method only works if the duplicates are consecutive (as they are in the sample files given).
		//It will find duplicates if there are 3 (or more) in a row
		ResultListHolder resultListHolder = new ResultListHolder();
		List<String> dupeList = new ArrayList<>();
		List<String> uniqueList = new ArrayList<>();
		String prev = null;
		String curr = null;
		String prevLine = null;
		String currLine = null;
		boolean prevIsDupe = false;
		LevenshteinDistance distance = new LevenshteinDistance();
		int count = 0;
		for (CSVRecord record : records) {
			count++;
			if (count == 1) {
				//skip the header entirely
				continue;
			}
			if (count == 2) {
				// the first real row, nothing to compare yet
				curr = createShortRecord(record);
				currLine = recreateLine(record);
				continue;
			}
			prev = curr;
			prevLine = currLine;
			curr = createShortRecord(record);
			currLine = recreateLine(record);
			//using just first name, last name and email to compare
			//in the sample files, these are all unique and identical where duplicate
			//but if there were small differences, we could just select a threshold to denote duplicates.
			Integer currEqualsPrev = distance.apply(curr, prev);
			// What number is a good threshold?
			if (currEqualsPrev <= THRESHOLD) {
				dupeList.add(prevLine);
				prevIsDupe = true;
			} else {
				if (prevIsDupe) {
					dupeList.add(prevLine);
					prevIsDupe = false;
				} else {
					uniqueList.add(prevLine);
				}
			}
		}
		//test the last line and add to correct list
		if (distance.apply(curr, prev) <= THRESHOLD) {
			dupeList.add(currLine);
		} else {
			uniqueList.add(currLine);
		}
		resultListHolder.setDupeList(dupeList);
		resultListHolder.setUniqueList(uniqueList);
		return resultListHolder;
	}

	/**
	 * Create a short string with just first name, last name and email to be used for comparison.
	 * @param record
	 * @return
	 */
	private String createShortRecord(CSVRecord record) {
		String firstName = record.get(Headers.first_name);
		String lastName = record.get(Headers.last_name);
		String email = record.get(Headers.email);
		return firstName.concat(lastName).concat(email);
	}

	/**
	 * Re-create the original line from the file.
	 * @param record
	 * @return
	 */
	private String recreateLine(CSVRecord record) {
		//	The annoying thing about using the Commons library is that there is no access to the full line, which
		//	is needed for this exercise. Recreating it here.
		List<String> values = new ArrayList<>(record.size());
		for (Headers h : Headers.values()) {
			values.add(record.get(h));
		}
		return String.join(",", values);
	}


}
