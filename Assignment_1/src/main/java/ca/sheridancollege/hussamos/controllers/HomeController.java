package ca.sheridancollege.hussamos.controllers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
		model.addAttribute("generatedId", generatedId);
		return "index";
	}
		
	@PostMapping("/addRecord")
	public String addRecord(Model model, @ModelAttribute PasswordRecord record) {
		pwList.addRecord(record);
		Long generatedId = rng.generateRandomNumber();
		model.addAttribute("generatedId", generatedId);

		/*PasswordRecord record = new PasswordRecord();
		record.setId(rng.generateRandomNumber());
		pwList.addRecord(record);
		*/return "index";
	}

	@GetMapping("/viewRecords")
	public String viewRecords(Model model) {
		List<PasswordRecord> records = pwList.getAllRecords();
		model.addAttribute("records", records);
		return "viewRecords";
	}
	
	@GetMapping("/findRecord")
	public String findRecord(Model model) {
		List<PasswordRecord> records = pwList.getAllRecords();
		model.addAttribute("records", records);
		return "/findRecord";
	}
	
	@PostMapping("/findRecord")
	public String findRecord(Model model, @RequestParam Long searchId) {
		List<PasswordRecord> records = pwList.getAllRecords();
		PasswordRecord result = null;
		model.addAttribute("records", records);
		for(PasswordRecord record : records) {
			if(record.getId().equals(searchId)) {
				result = record;
				break;
			}
		}
		
		model.addAttribute("result", result);
		model.addAttribute("recordNotFound", result == null);
		
		return "/result";
	}
}
