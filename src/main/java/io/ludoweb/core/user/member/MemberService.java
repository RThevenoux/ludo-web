package io.ludoweb.core.user.member;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MemberService implements UserDetailsService {

	@Autowired
	MemberConverter converter;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	MemberRepository repo;

	// Implement UserDetailsService (Spring-Security)
	@Override
	public UserDetails loadUserByUsername(String username) {
		return repo.findByUsername(username)//
				.map(MemberPrincipal::new)//
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

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
			fill(entity, data);

			MemberEntity saved = repo.save(entity);
			return converter.apply(saved);
		}
	}

	public void delete(String externalId) {
		repo.deleteByExternalId(externalId);
	}

	private MemberEntity fill(MemberEntity entity, MemberInput input) {
		entity.setFirstName(input.getFirstName());
		entity.setLastName(input.getLastName());
		entity.setMail(input.getMail());
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

	public MemberStats getMemberStats(boolean subscriptionPaid) {
		List<MemberEntity> subscriptions = repo.findBySubscriptionPaid(subscriptionPaid);

		int totalMemberCount = 0;
		for (MemberEntity subscription : subscriptions) {
			totalMemberCount += (1 + subscription.getOtherMembers().size());
		}

		return new MemberStats(subscriptions.size(), totalMemberCount);
	}

	public List<MemberView> list() {
		return converter.convert(repo.findAll());
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
