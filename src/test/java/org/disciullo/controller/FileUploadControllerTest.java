package org.disciullo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.disciullo.service.DuplicateService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DuplicateService duplicateService;

	@Test
	public void testShowForm() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
		.andExpect(status().isOk())
		.andExpect(content().string(Matchers.containsString("Please choose a file to upload")));
	}

	@Test
	public void getJson() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/returnJson").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(Matchers.containsString("Duplicate entries")));
	}

	//TODO: finish tests

}
