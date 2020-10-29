package es.oaw.irapvalidator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import es.oaw.irapvalidator.Constants;
import es.oaw.irapvalidator.datatable.DatatablePage;
import es.oaw.irapvalidator.datatable.DatatablePagination;
import es.oaw.irapvalidator.filter.Filter;
import es.oaw.irapvalidator.model.User;
import es.oaw.irapvalidator.repository.RoleRepository;
import es.oaw.irapvalidator.repository.UserRepository;

/**
 * The Class UserService.
 */
@Service("userService")
public class UserService {

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/** The b crypt password encoder. */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Instantiates a new user service.
	 *
	 * @param userRepository        the user repository
	 * @param roleRepository        the role repository
	 * @param bCryptPasswordEncoder the b crypt password encoder
	 */
	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * Save user.
	 *
	 * @param user the user
	 */
	public void saveUser(final User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		userRepository.save(user);
	}

	/**
	 * Find all.
	 *
	 * @param pagination the pagination
	 * @return the datatable page
	 */
	public DatatablePage findAll(DatatablePagination pagination) {

		Page<User> page;

		// Order configuration
		String orderDirection = pagination.getOrder().get(0).getDir();
		String orderColumn = pagination.getColumns().get(pagination.getOrder().get(0).getColumn()).getData();
		int pageNumber = pagination.getStart() / pagination.getLength();

		// By default order by ID of entity
		PageRequest pageRequest = PageRequest.of(pageNumber, pagination.getLength(),
				(Constants.ASC.equalsIgnoreCase(orderDirection) ? Direction.ASC : Direction.DESC),
				StringUtils.isEmpty(orderColumn) ? "idUsuario" : orderColumn);

		page = userRepository.findAll(pageRequest);

		DatatablePage datatablePage = new DatatablePage();
		datatablePage.setContent(page.getContent());
		datatablePage.setRecordsFiltered(page.getTotalElements());
		datatablePage.setRecordsTotal(page.getTotalElements());

		return datatablePage;
	}

}