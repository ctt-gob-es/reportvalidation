package es.oaw.irapvalidator.controller;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.poi.sl.usermodel.ObjectMetaData.Application;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.oaw.irapvalidator.Constants;
import es.oaw.irapvalidator.IrapvalidatorApplication;
import es.oaw.irapvalidator.datatable.DatatablePage;
import es.oaw.irapvalidator.datatable.DatatablePagination;
import es.oaw.irapvalidator.dto.UserDTO;
import es.oaw.irapvalidator.model.Configuration;
import es.oaw.irapvalidator.model.User;
import es.oaw.irapvalidator.repository.ConfigurationRepository;
import es.oaw.irapvalidator.repository.RoleRepository;
import es.oaw.irapvalidator.repository.UserRepository;
import es.oaw.irapvalidator.service.UserService;

/**
 * The Class UsersController.
 */
@Controller
@RequestMapping(path = "/users")
public class UserController {

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/** The Constant SUCCESS_MESSAGE. */
	private static final String SUCCESS_MESSAGE = "success";

	/** The Constant ERROR_MESSAGE. */
	private static final String ERROR_MESSAGE = "error";

	/** The Constant REDIRECT_RECURSOS. */
	private static final String REDIRECT_USERS = "redirect:/users";

	/** The user service. */
	@Autowired
	private UserService userService;

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/** The role rpository. */
	@Autowired
	private RoleRepository roleRpository;

	/** The configuration repository. */
	@Autowired
	private ConfigurationRepository configurationRepository;

	/**
	 * Titular concesion.
	 *
	 * @param pagination the pagination
	 * @return the list
	 */
	@PostMapping(path = "/all")

	public @ResponseBody DatatablePage getAllUsers(@RequestBody DatatablePagination pagination) {
		return userService.findAll(pagination);
	}

	/**
	 * Registration.
	 *
	 * @param model the model
	 * @return the model and view
	 */
	@GetMapping
	public String list(Model model) {

		model.addAttribute("accessConfiguration", configurationRepository.findByKey("anonymous.access").get());
		model.addAttribute(Constants.MODEL_KEY_CONTENT, "../fragments/users/list");
		return Constants.TEMPLATE_LOGGED_IN;
	}

	/**
	 * Mapping para acceder al formulario de alta de titulares.
	 *
	 * @param user  the user
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/create")
	public String create(User user, Model model) {

		model.addAttribute("user", new UserDTO());
		model.addAttribute("roles", roleRpository.findAll());
		model.addAttribute(Constants.MODEL_KEY_CONTENT, "../fragments/users/new");

		return Constants.TEMPLATE_LOGGED_IN;
	}

	/**
	 * Adds the recurso.
	 *
	 * @param user               the User
	 * @param result             the result
	 * @param model              the model
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("User") UserDTO user, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {

		if (!StringUtils.isEmpty(user.getRepeatPassword()) && !StringUtils.isEmpty(user.getPassword())
				&& !user.getPassword().equals(user.getRepeatPassword())) {
			result.rejectValue("password", "error.User",
					messageSource.getMessage("field.password.not.match", null, LocaleContextHolder.getLocale()));
			result.rejectValue("passwordRepetir", "error.User",
					messageSource.getMessage("field.password.not.match", null, LocaleContextHolder.getLocale()));
		}

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("roles", roleRpository.findAll());
			model.addAttribute(Constants.MODEL_KEY_CONTENT, "../fragments/users/new");
			return Constants.TEMPLATE_LOGGED_IN;
		}

		User UserVO = new User();
		BeanUtils.copyProperties(user, UserVO);
		user.setActive(1);
		userService.saveUser(UserVO);
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
				messageSource.getMessage("messages.success.save.user", null, LocaleContextHolder.getLocale()));

		return REDIRECT_USERS;
	}

	/**
	 * Delete recurso.
	 *
	 * @param id                 the id User
	 * @param model              the model
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		try {
			User User = userRepository.findById(id).get();
			userRepository.delete(User);
		} catch (IllegalArgumentException | NoSuchElementException e) {
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
					messageSource.getMessage("messages.success.save.user", null, LocaleContextHolder.getLocale()));
			return REDIRECT_USERS;
		}

		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
				messageSource.getMessage("messages.success.delete.user", null, LocaleContextHolder.getLocale()));

		return REDIRECT_USERS;
	}

	/**
	 * Delete recurso.
	 *
	 * @param id                 the id User
	 * @param model              the model
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	@GetMapping("/deactivate/{id}")
	public String deactivate(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		try {
			User User = userRepository.findById(id).get();
			User.setActive(0);
			userRepository.save(User);
		} catch (IllegalArgumentException | NoSuchElementException e) {
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
					messageSource.getMessage("messages.error.deactivate.user", null, LocaleContextHolder.getLocale()));
			return REDIRECT_USERS;
		}

		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
				messageSource.getMessage("messages.success.deactivate.user", null, LocaleContextHolder.getLocale()));

		return REDIRECT_USERS;
	}

	/**
	 * Sactivar user.
	 *
	 * @param id                 the id user
	 * @param model              the model
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	@GetMapping("/activate/{id}")
	public String activate(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		try {
			User User = userRepository.findById(id).get();
			User.setActive(1);
			userRepository.save(User);
		} catch (IllegalArgumentException | NoSuchElementException e) {
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
					messageSource.getMessage("messages.error.activate.user", null, LocaleContextHolder.getLocale()));
			return REDIRECT_USERS;
		}

		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
				messageSource.getMessage("messages.success.activate.user", null, LocaleContextHolder.getLocale()));

		return REDIRECT_USERS;
	}

	/**
	 * Show update form.
	 *
	 * @param idUser             the id User
	 * @param model              the model
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int idUser, Model model, final RedirectAttributes redirectAttributes) {

		try {
			User User = userRepository.findById(idUser).get();
			UserDTO dto = new UserDTO();

			BeanUtils.copyProperties(User, dto);

			model.addAttribute("user", dto);
			model.addAttribute("roles", roleRpository.findAll());
			model.addAttribute("isactivo", User.getActive() == 1 ? true : false);

		} catch (NoSuchElementException | IllegalArgumentException e) {
			model.addAttribute("User", new User());
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
					messageSource.getMessage("messages.error.detail.User", null, LocaleContextHolder.getLocale()));
			return REDIRECT_USERS;
		}

		model.addAttribute(Constants.MODEL_KEY_CONTENT, "../fragments/users/edit");
		return Constants.TEMPLATE_LOGGED_IN;
	}

	/**
	 * Update recurso.
	 *
	 * @param idUser             the id User
	 * @param user               the User
	 * @param result             the result
	 * @param model              the model
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id") int idUser, @Valid @ModelAttribute("user") UserDTO user,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

		if (!StringUtils.isEmpty(user.getRepeatPassword()) && !StringUtils.isEmpty(user.getPassword())
				&& !user.getPassword().equals(user.getRepeatPassword())) {
			result.rejectValue("password", "error.User",
					messageSource.getMessage("field.password.not.match", null, LocaleContextHolder.getLocale()));
			result.rejectValue("passwordRepetir", "error.User",
					messageSource.getMessage("field.password.not.match", null, LocaleContextHolder.getLocale()));
		}

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("roles", roleRpository.findAll());
			model.addAttribute(Constants.MODEL_KEY_CONTENT, "../fragments/users/edit");
			return Constants.TEMPLATE_LOGGED_IN;
		}

		User UserVO = new User();
		BeanUtils.copyProperties(user, UserVO);
		userService.saveUser(UserVO);
		userRepository.save(UserVO);
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
				messageSource.getMessage("messages.success.save.user", null, LocaleContextHolder.getLocale()));
		return REDIRECT_USERS;
	}

	/**
	 * Creates the new user.
	 *
	 * @param user          the user
	 * @param bindingResult the binding result
	 * @return the model and view
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	/**
	 * Configuration.
	 *
	 * @param configuration      the configuration
	 * @param result             the result
	 * @param model              the model
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 * @throws InterruptedException the interrupted exception
	 */
	@PostMapping("/configuration")
	public String configuration(@Valid @ModelAttribute("Configuration") Configuration configuration,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes)
			throws InterruptedException {

		configurationRepository.save(configuration);

		//IrapvalidatorApplication.restart();

		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
				messageSource.getMessage("messages.success.save.user.config", null, LocaleContextHolder.getLocale()));

		return REDIRECT_USERS;
	}

}
