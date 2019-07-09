package com.websystique.trainer.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.websystique.trainer.model.Trainer;
import com.websystique.trainer.service.TrainerService;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	TrainerService service;
	
	@Autowired
	MessageSource messageSource;

	/*
	 * This method will list all existing trainers.
	 */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String listTrainers(ModelMap model) {

		List<Trainer> trainers = service.findAllTrainers();
		model.addAttribute("trainers", trainers);
		return "alltrainers";
	}

	/*
	 * This method will provide the medium to add a new trainer.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newTrainer(ModelMap model) {
		Trainer trainer = new Trainer();
		model.addAttribute("trainer", trainer);
		model.addAttribute("edit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving trainer in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveTrainer(@Valid Trainer trainer, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation 
		 * and applying it on field [ssn] of Model class [Trainer].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
		if(!service.isTrainerSsnUnique(trainer.getId(), trainer.getSsn())){
			FieldError ssnError =new FieldError("trainer","ssn",messageSource.getMessage("non.unique.ssn", new String[]{trainer.getSsn()}, Locale.getDefault()));
		    result.addError(ssnError);
			return "registration";
		}
		
		service.saveTrainer(trainer);

		model.addAttribute("success", "Trainer " + trainer.getName() + " registered successfully");
		return "success";
	}


	/*
	 * This method will provide the medium to update an existing trainer.
	 */
	@RequestMapping(value = { "/edit-{ssn}-trainer" }, method = RequestMethod.GET)
	public String editTrainer(@PathVariable String ssn, ModelMap model) {
		Trainer trainer = service.findTrainerBySsn(ssn);
		model.addAttribute("trainer", trainer);
		model.addAttribute("edit", true);
		return "registration";
	}
	
	/*
	 * This method will be called on form submission, handling POST request for
	 * updating trainer in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{ssn}-trainer" }, method = RequestMethod.POST)
	public String updateTrainer(@Valid Trainer trainer, BindingResult result,
			ModelMap model, @PathVariable String ssn) {

		if (result.hasErrors()) {
			return "registration";
		}

		if(!service.isTrainerSsnUnique(trainer.getId(), trainer.getSsn())){
			FieldError ssnError =new FieldError("trainer","ssn",messageSource.getMessage("non.unique.ssn", new String[]{trainer.getSsn()}, Locale.getDefault()));
		    result.addError(ssnError);
			return "registration";
		}

		service.updateTrainer(trainer);

		model.addAttribute("success", "Trainer " + trainer.getName()	+ " updated successfully");
		return "success";
	}

	
	/*
	 * This method will delete an trainer by it's SSN value.
	 */
	@RequestMapping(value = { "/delete-{ssn}-trainer" }, method = RequestMethod.GET)
	public String deleteTrainer(@PathVariable String ssn) {
		service.deleteTrainerBySsn(ssn);
		return "redirect:/list";
	}

}
