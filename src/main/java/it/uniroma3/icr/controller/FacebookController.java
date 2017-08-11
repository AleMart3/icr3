package it.uniroma3.icr.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.service.impl.StudentFacade;


@Controller

public class FacebookController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;
    @Autowired
	private StudentFacade userFacade;

    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
        
    }

    @RequestMapping(value="/vai", method=RequestMethod.GET)
    public String vai(){
    	return "/connect/facebookConnect";
    }
    
    @RequestMapping(value="/vai2", method=RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        String [] fields = {"name","email"};
        User user = facebook.fetchObject("me", User.class, fields);
        
        String email= user.getEmail();
        Student student= userFacade.findUser(email);
        if(student!=null){
        	model.addAttribute("student", student);
        	return "users/homeStudent";
        }
        else{
        
        
        String userprofile=user.getName();
        String[] temp;
		String delimiter = " ";
		temp = userprofile.split(delimiter);   
		String name = temp[0];   
		String surname = temp[1];
		model.addAttribute("nome", name);
		model.addAttribute("cognome", surname);
		model.addAttribute("email", email);
		model.addAttribute("student", new Student());
		
		Map<String,String> schoolGroups = new HashMap<String,String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);
       return "/registration";
        }
    }
}
