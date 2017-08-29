/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
class ProgramController {

	private static final String VIEWS_PROGRAMS_CREATE_OR_UPDATE_FORM = "programs/createOrUpdateProgramsForm";
	private final ProgramRepository programs;

	@Autowired
	public ProgramController(ProgramRepository clinicService) {
		this.programs = clinicService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/programs/new", method = RequestMethod.GET)
	public String initCreationForm(Map<String, Object> model) {
		Program program = new Program();
		model.put("program", program);
		return VIEWS_PROGRAMS_CREATE_OR_UPDATE_FORM;
	}

	@RequestMapping(value = "/programs/new", method = RequestMethod.POST)
	public String processCreationForm(@Valid Program program, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_PROGRAMS_CREATE_OR_UPDATE_FORM;
		} else {
			this.programs.save(program);
			return "redirect:/programs/" + program.getId();
		}
	}

	@RequestMapping(value = "/programs/find", method = RequestMethod.GET)
	public String initFindForm(Map<String, Object> model) {
		model.put("program", new Program());
		return "programs/findPrograms";
	}

	@RequestMapping(value = "/programs", method = RequestMethod.GET)
	public String processFindForm(Program program, BindingResult result, Map<String, Object> model) {

		if (program.getProgramName() == null) {
			program.setProgramName(""); // empty string signifies broadest possible search
		}
		System.out.println(">>>>" + program.getProgramName());
		Collection<Program> results = this.programs.findByLastName(program.getProgramName());

		System.out.println(">>>>" + results.size());
		if (results.isEmpty()) {
			result.rejectValue("programName", "notFound", "not found");
			return "programs/findPrograms";
		} else if (results.size() == 1) {
			program = results.iterator().next();
			return "redirect:/programs/" + program.getId();
		} else {
			model.put("selections", results);
			return "programs/programsList";
		}
	}

	@RequestMapping(value = "/programs/{programId}/edit", method = RequestMethod.GET)
	public String initUpdateProgramsForm(@PathVariable("programId") int programId, Model model) {
		Program program = this.programs.findById(programId);
		model.addAttribute(program);
		return VIEWS_PROGRAMS_CREATE_OR_UPDATE_FORM;
	}
	
	@RequestMapping(value = "/programs/{programId}/delete", method = RequestMethod.GET)
	public String deleteProgram(@PathVariable("programId") int programId, Model model) {
		this.programs.deleteById(programId);
		model.addAttribute(new Program());
		return "programs/findPrograms";
	}

	@RequestMapping(value = "/programs/{programId}/edit", method = RequestMethod.POST)
	public String processUpdateProgramsForm(@Valid Program program, BindingResult result,
			@PathVariable("programId") int programId) {
		if (result.hasErrors()) {
			return VIEWS_PROGRAMS_CREATE_OR_UPDATE_FORM;
		} else {
			program.setId(programId);
			this.programs.save(program);
			return "redirect:/programs/{programId}";
		}
	}

	/**
	 * Custom handler for displaying an program.
	 *
	 * @param programId
	 *            the ID of the program to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping("/programs/{programId}")
	public ModelAndView showPrograms(@PathVariable("programId") int programId) {
		ModelAndView mav = new ModelAndView("programs/programsDetails");
		System.out.println("program id>> " + programId);
		mav.addObject(this.programs.findById(programId));
		return mav;
	}

}
