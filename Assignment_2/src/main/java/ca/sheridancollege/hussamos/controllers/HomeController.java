package ca.sheridancollege.hussamos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.hussamos.beans.PasswordRecord;
import ca.sheridancollege.hussamos.repositories.PasswordRecordList;
import ca.sheridancollege.hussamos.utilities.RandomNumberGenerator;

@Controller
public class HomeController {
	@Autowired
	private PasswordRecordList pwList;
	private RandomNumberGenerator rng;
	
	public HomeController(PasswordRecordList pwList, RandomNumberGenerator rng) {
		super();
		this.pwList = pwList;
		this.rng = rng;
	}
	

	@GetMapping("/")
	public String index(Model model) {
		Long generatedId = rng.generateRandomNumber();
		model.addAttribute("generatedId", String.format("%09d", generatedId));
		return "index";
	}
		
	@PostMapping("/addRecord")
	public String addRecord(Model model, @ModelAttribute PasswordRecord record) {
		pwList.addRecord(record);
		Long generatedId = rng.generateRandomNumber();
		model.addAttribute("generatedId", String.format("%09d", generatedId));
		model.addAttribute("successful", "A record was added successfully!");
		return "index";
	}

	@GetMapping("/viewRecords")
	public String viewRecords(Model model) {
		List<PasswordRecord> records = pwList.getAllRecords();
		if(records.size() > 0)
			model.addAttribute("records", records);
		else
			model.addAttribute("noRecords","no records found");
		return "viewRecords";
	}
	
	@GetMapping("/findRecord")
	public String findRecord(Model model) {
		List<PasswordRecord> records = pwList.getAllRecords();
		model.addAttribute("records", records);
		return "/findRecord";
	}
	
	@PostMapping("/findRecord")
	public String findRecord(Model model, @RequestParam String searchId) {
		Long findId = Long.valueOf(searchId);
		PasswordRecord result = pwList.getRecord(findId);
		if(result != null)
			model.addAttribute("result", result);
		else
			model.addAttribute("recordNotFound", result==null);
		return "/findRecord";
	}
}
