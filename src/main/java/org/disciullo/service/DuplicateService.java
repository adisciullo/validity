package org.disciullo.service;

import java.io.File;
import java.io.IOException;

import org.disciullo.model.ResultListHolder;


/**
 * Service which does the work of parsing a file and finding the duplicates. Makes 2 lists, one for
 * duplicates and one for unique entries.
 * @author Anne DiSciullo
 *
 */
public interface DuplicateService {

	/**
	 * Parses the lines from the test file, finds the duplicates, create duplicate and unique lists.
	 * @param file
	 * @return the two lists held inside the {@link ResultListHolder}
	 * @throws IOException
	 */
	ResultListHolder loadData(File file) throws IOException;


}
