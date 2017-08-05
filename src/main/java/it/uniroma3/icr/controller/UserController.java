package it.uniroma3.icr.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.service.impl.AdminFacade;
import it.uniroma3.icr.service.impl.StudentFacade;
import it.uniroma3.icr.validator.studentValidator;

@Controller
public class UserController {

	@Autowired
	private StudentFacade userFacade;
	
	@Autowired
	private AdminFacade adminFacade;

	@Qualifier("userValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public String registrazione(@ModelAttribute Student student, Model model) {

		Map<String,String> schoolGroups = new HashMap<String,String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);

		return "registration";
	}

	@RequestMapping(value="/addUser", method = RequestMethod.POST)
	public String confirmUser(@ModelAttribute Student student, Model model, @Validated Student p, BindingResult bindingResult) {

		Map<String,String> schoolGroups = new HashMap<String,String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);
		
		Student u = userFacade.findUser(student.getUsername());
		
		Administrator a= adminFacade.findAdmin(student.getUsername());
	
		/*if(bindingResult.hasErrors() || student.getName().isEmpty() || student.getSurname().isEmpty()) {
			return "registration";
		}*/

		
		if(studentValidator.validate(student,model,u,a)){
			
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String passwordEncode = passwordEncoder.encode(student.getPassword());
			student.setPassword(passwordEncode);

			model.addAttribute("student", student);

			userFacade.retrieveUser(student);
			return "registrationRecap"; 
			} 
			else{
				return "registration";
			}

		}

	
	@RequestMapping(value="user/toChangeStudentPassword")
	public String toChangePassword(@ModelAttribute Student student, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		student = userFacade.findUser(username);
		model.addAttribute("student", student);
		return "users/changeStudentPassword";

	}

	@RequestMapping(value="user/changeStudentPassword", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute Student student) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordEncode = passwordEncoder.encode(student.getPassword());
		student.setPassword(passwordEncode);
		userFacade.retrieveUser(student);
		return "users/homeStudent";
	}

	@RequestMapping(value="/user/homeStudent")
	public String toHomeStudent(@ModelAttribute Student student, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		student = userFacade.findUser(username);
		model.addAttribute("student", student);
		return "users/homeStudent";
	}

}