package org.disciullo.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.disciullo.model.ResultListHolder;
import org.junit.Test;

public class DuplicateServiceTest {

	//some of these test files have entries where there are slight differences in names and emails
	DuplicateService duplicateService = new DuplicateServiceImpl();

	@Test
	public void testFindDuplicatesAtStart() throws IOException {
		File file = new File("testFiles/test1.csv");
		ResultListHolder rlh = duplicateService.loadData(file);
		List<String> dupeList = rlh.getDupeList();
		List<String> uniqueList = rlh.getUniqueList();

		assertEquals(2, dupeList.size());
		assertEquals("0", dupeList.get(0).substring(0, 1));
		assertEquals("0", dupeList.get(1).substring(0, 1));

		assertEquals(2, uniqueList.size());
		assertEquals("1", uniqueList.get(0).substring(0, 1));
		assertEquals("2", uniqueList.get(1).substring(0, 1));

	}

	@Test
	public void testFindDuplicatesAtEnd() throws IOException {
		File file = new File("testFiles/test2.csv");
		ResultListHolder rlh = duplicateService.loadData(file);
		List<String> dupeList = rlh.getDupeList();
		List<String> uniqueList = rlh.getUniqueList();
		assertEquals(2, dupeList.size());
		assertEquals("3", dupeList.get(0).substring(0, 1));
		assertEquals("3", dupeList.get(1).substring(0, 1));
		assertEquals("Anne", dupeList.get(0).substring(12, 16));
		assertEquals("Ann", dupeList.get(1).substring(12, 15));
		assertEquals(2, uniqueList.size());
		assertEquals("1", uniqueList.get(0).substring(0, 1));
		assertEquals("2", uniqueList.get(1).substring(0, 1));
	}

	@Test
	public void testFindDuplicatesThreeDuplicates() throws IOException {
		File file = new File("testFiles/test3.csv");
		ResultListHolder rlh = duplicateService.loadData(file);
		List<String> dupeList = rlh.getDupeList();
		List<String> uniqueList = rlh.getUniqueList();
		assertEquals(3, dupeList.size());
		assertEquals("3", dupeList.get(0).substring(0, 1));
		assertEquals("3", dupeList.get(1).substring(0, 1));
		assertEquals("3", dupeList.get(2).substring(0, 1));
		assertEquals(3, uniqueList.size());
		assertEquals("1", uniqueList.get(0).substring(0, 1));
		assertEquals("2", uniqueList.get(1).substring(0, 1));
	}

	@Test
	public void testFindDuplicatesTwoSetsOfDuplicates() throws IOException {
		File file = new File("testFiles/test4.csv");
		ResultListHolder rlh = duplicateService.loadData(file);
		List<String> dupeList = rlh.getDupeList();
		List<String> uniqueList = rlh.getUniqueList();
		assertEquals(4, dupeList.size());
		assertEquals("3", dupeList.get(0).substring(0, 1));
		assertEquals("3", dupeList.get(1).substring(0, 1));
		assertEquals("4", dupeList.get(2).substring(0, 1));
		assertEquals("4", dupeList.get(3).substring(0, 1));
		assertEquals(3, uniqueList.size());
		assertEquals("1", uniqueList.get(0).substring(0, 1));
		assertEquals("2", uniqueList.get(1).substring(0, 1));
	}

	@Test
	public void testFindDuplicatesNormalFile() throws IOException {
		File file = new File("testFiles/normal.csv");
		ResultListHolder rlh = duplicateService.loadData(file);
		List<String> dupeList = rlh.getDupeList();
		List<String> uniqueList = rlh.getUniqueList();
		assertEquals(16, dupeList.size());
		assertEquals(90, uniqueList.size());
	}

	@Test
	public void testFindDuplicatesAdvFile() throws IOException {
		File file = new File("testFiles/advanced.csv");
		ResultListHolder rlh = duplicateService.loadData(file);
		List<String> dupeList = rlh.getDupeList();
		List<String> uniqueList = rlh.getUniqueList();
		assertEquals(20, dupeList.size());
		assertEquals(89, uniqueList.size());
	}

}
