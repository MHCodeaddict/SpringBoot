package ca.sheridancollege.hussamos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.hussamos.beans.PasswordRecord;
import ca.sheridancollege.hussamos.repositories.PasswordRecordList;
import ca.sheridancollege.hussamos.utilities.RandomNumberGenerator;
import ca.sheridancollege.hussamos.database.DatabaseAccess;

@Controller
public class HomeController {
	private RandomNumberGenerator rng;
	@Autowired
	private DatabaseAccess da;
	
	public HomeController(PasswordRecordList pwList, RandomNumberGenerator rng) {
		super();
		this.rng = rng;
	}
	

	@GetMapping("/")
	public String index(Model model) {
		Long generatedId = rng.generateRandomNumber();
		model.addAttribute("generatedId", String.format("%09d", generatedId));
		model.addAttribute("pwList",new PasswordRecord());
		return "index";
	}
		
	@PostMapping("/insertRecord")
	public String insertRecord(Model model, @ModelAttribute PasswordRecord record) {
		da.insertRecord(record);
		Long generatedId = rng.generateRandomNumber();
		model.addAttribute("generatedId", String.format("%09d", generatedId));
		model.addAttribute("successful", "A record was added successfully!");
		return "index";
	}

	@GetMapping("/viewRecords")
	public String viewRecords(Model model) {
		if(da != null)
			model.addAttribute("records", da.getRecordList());
		else
			model.addAttribute("noRecords", "Database is empty.");
		return "viewRecords";
	}
	
	@GetMapping("/findRecord")
	public String findRecord(Model model) {
		List<PasswordRecord> records = da.getRecordList();
		model.addAttribute("records", records);
		return "/findRecord";
	}
	
	@PostMapping("/findRecord")
	public String findRecord(Model model, @RequestParam String searchId) {
		List<PasswordRecord> result = da.getRecordListById(Long.valueOf(searchId));
		if(result.size()>0)
			model.addAttribute("result", result.get(0));
		else
			model.addAttribute("recordNotFound", "no records found");
		return "/findRecord";
	}
	
	@GetMapping("/deleteRecordById/{id}")
	public String deleteRecordById(Model model, @PathVariable Long id) {
		da.deleteRecordById(id);
		model.addAttribute("records", da.getRecordList());
		return "/viewRecords";
	}
	
	@GetMapping("/editRecordById/{id}")
	public String editRecordById(Model model, @PathVariable Long id) {
		PasswordRecord record = da.getRecordListById(id).get(0);
		model.addAttribute("editRecord","edit this record");
		model.addAttribute("record", record);
		da.deleteRecordById(id);
		model.addAttribute("records", da.getRecordList());
		return "/viewRecords";
	}
}
