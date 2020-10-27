package es.oaw.irapvalidator.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.oaw.irapvalidator.Constants;

/**
 * The Class IndexController.
 * 
 * General controller to common mappings.
 */
@Controller // This means that this class is a Controller
@RequestMapping(path = "/")
public class IndexController {

	/**
	 * Index.
	 *
	 * @param concession the concession
	 * @param model      the model
	 * @param session    the session
	 * @return the string
	 */
	@GetMapping
	public String index(Model model, HttpSession session) {

		model.addAttribute("content", "../fragments/index");

		return Constants.TEMPLATE_LOGGED_IN;

	}

	/**
	 * Login.
	 *
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

}
