package io.ludoweb.core.user.member;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import io.ludoweb.core.user.SecurityTool;
import io.ludoweb.core.user.member.email.EmailResult;
import io.ludoweb.core.user.member.email.EmailResultFactory;
import io.ludoweb.core.user.member.email.EmailValidatorService;

@Service
@Transactional
public class MemberService implements UserDetailsService {

	private static final String CACHE_MEMBER_PLANS = "member.plans";
	private static final String CACHE_MEMBER_TYPES = "member.types";

	public static final String ROLE_MEMBER = "MEMBER";

	@Autowired
	MemberConverter converter;
	@Autowired
	EmailResultFactory emailResultFactory;
	@Autowired
	EmailValidatorService emailValidator;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	PredicateBuilder predicateBuilder;
	@Autowired
	MemberRepository repo;

	private MemberUserDetails buildDetails(MemberEntity entity) {
		MemberUserDetails details = new MemberUserDetails();
		details.setId(entity.getId());
		details.setPassword(entity.getPassword());
		details.setUsername(entity.getUsername());
		details.setAuthorities(SecurityTool.roleAuthorityAsSet(entity.getRole()));
		return details;
	}

	@CacheEvict(value = { CACHE_MEMBER_PLANS, CACHE_MEMBER_TYPES }, allEntries = true)
	public MemberView createOrUpdate(MemberInput data) {
		Optional<MemberEntity> optUser = findEntityByExternalId(data.getExternalId());

		if (optUser.isPresent()) {
			MemberEntity entity = optUser.get();
			fill(entity, data);
			return converter.apply(entity);
		} else {
			MemberEntity entity = new MemberEntity();
			entity.setPassword(null);
			entity.setExternalId(data.getExternalId());
			entity.setRole(ROLE_MEMBER);
			fill(entity, data);

			MemberEntity saved = repo.save(entity);
			return converter.apply(saved);
		}
	}

	@CacheEvict(value = { CACHE_MEMBER_PLANS, CACHE_MEMBER_TYPES }, allEntries = true)
	public void delete(String externalId) {
		repo.deleteByExternalId(externalId);
	}

	private MemberEntity fill(MemberEntity entity, MemberInput input) {
		entity.setEmail(input.getEmail());
		entity.setEmailValid(emailValidator.isValid(input.getEmail()));
		entity.setFirstName(input.getFirstName());
		entity.setLastName(input.getLastName());
		entity.setOtherMembers(input.getOtherMembers());
		entity.setPhone(input.getPhone());
		entity.setPlan(input.getPlan());
		entity.setType(input.getType());
		entity.setUsername(input.getUsername());
		return entity;
	}

	public Optional<MemberView> findByExternalId(String externalId) {
		return findEntityByExternalId(externalId).map(converter);
	}

	public Optional<MemberView> findById(long id) {
		return repo.findById(id).map(converter);
	}

	public Optional<MemberEntity> findEntityByExternalId(String externalId) {
		return repo.findByExternalId(externalId);
	}

	public EmailResult getEmails(MemberRequest request) {
		Predicate predicate = predicateBuilder.buildPredicate(request);
		return emailResultFactory.build(repo.findAll(predicate));
	}

	public MemberStats getMemberStats(MemberRequest request) {
		Predicate predicate = predicateBuilder.buildPredicate(request);

		Iterable<MemberEntity> subscriptions = repo.findAll(predicate);

		int totalMemberCount = 0;
		int subscriptionCount = 0;

		for (MemberEntity subscription : subscriptions) {
			totalMemberCount += (1 + subscription.getOtherMembers().size());
			subscriptionCount++;
		}

		return new MemberStats(subscriptionCount, totalMemberCount);
	}

	public List<MemberView> list() {
		return converter.convert(repo.findAll());
	}

	@Cacheable(CACHE_MEMBER_PLANS)
	public List<String> listPlanNames() {
		return repo.listPlanNames();
	}

	@Cacheable(CACHE_MEMBER_TYPES)
	public List<String> listTypes() {
		return repo.listTypes();
	}

	// Implement UserDetailsService (Spring-Security)
	@Override
	public UserDetails loadUserByUsername(String username) {
		return repo.findByUsername(username)//
				.map(this::buildDetails)//
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

	public List<MemberView> search(MemberRequest request) {
		Predicate predicate = predicateBuilder.buildPredicate(request);
		return converter.convert(repo.findAll(predicate));
	}

	public boolean updateMemberPassword(String externalId, String password) {
		return findEntityByExternalId(externalId)//
				.map(entity -> {
					String encoded = passwordEncoder.encode(password);
					entity.setPassword(encoded);
					return true;
				})//
				.orElse(false);
	}
}
