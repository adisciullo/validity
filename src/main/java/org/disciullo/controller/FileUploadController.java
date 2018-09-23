package org.disciullo.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.disciullo.model.ResultListHolder;
import org.disciullo.service.DuplicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller which presents page to choose a file, upload the file, the displays results
 * either in nicely formatted text/html or JSON.
 * @author Anne DiSciullo
 *
 */
@Controller
public class FileUploadController {

	@Autowired
	private DuplicateService duplicateService;

	private static final String tempDir = System.getProperty("java.io.tmpdir");

	/**
	 * Show the landing page. Allow a user to choose a file, then upload it.
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String showForm(Model model) {
		model.addAttribute("message", "Please choose a file to upload.");
		return "uploadForm";
	}


	/**
	 * Handle the file upload
	 * @param file the file to be uploaded
	 * @param outputFormat the format in which to diplay the output (html or json). If the output is json, will redirect to another method.
	 * @param model add attributes to be used on the html page
	 * @param redirectAttributes used to hold the results of parsing the file for duplicates
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("outputFormat") String outputFormat, Model model,
			RedirectAttributes redirectAttributes) throws IOException {
		ResultListHolder rlh = duplicateService.loadData(createTempFile(file));
		redirectAttributes.addFlashAttribute("resultListHolder", rlh);
		if (outputFormat.equals("json")) {
			//redirect
			return "redirect:/returnJson";
		}
		model.addAttribute("duplicates", rlh.getDupeList());
		model.addAttribute("uniques",  rlh.getUniqueList());
		printData(rlh.getDupeList(),  rlh.getUniqueList());

		return "results";
	}

	/**
	 * Method to display results as JSON in the browser.
	 * @param resultListHolder the results of parsing the file uploaded
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/returnJson", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<String> getJson(@ModelAttribute("resultListHolder") final ResultListHolder resultListHolder) throws IOException {
		List<String> results = new ArrayList<>();
		results.add("Duplicate entries:");
		results.addAll( resultListHolder.getDupeList());
		results.add("Unique entries:");
		results.addAll(resultListHolder.getUniqueList());
		return results;
	}

	/**
	 * Create a file in the system temporary space. The file will be deleted when the virtual machine terminates.
	 * @param multipartFile
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private File createTempFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		// create the file in the temporary system directory
		File file = new File(tempDir, multipartFile.getName());
		multipartFile.transferTo(file);
		file.deleteOnExit();
		return file;
	}

	/**
	 * Print the results of the duplicate and unique lists to system out.
	 * @param dupes
	 * @param unique
	 */
	private void printData(List<String> dupes, List<String> unique) {
		System.out.println("dupeList:");
		for (String x : dupes) {
			System.out.println(x);
		}
		System.out.println("uniqueList:");
		for (String x : unique) {
			System.out.println(x);
		}
	}

}
