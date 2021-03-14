package io.ludoweb.config;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigService {

	@Autowired
	ServletContext context;
	@Autowired
	ConfigRepository repo;

	private ConfigView convert(ConfigEntity entity) {
		ConfigView view = new ConfigView();
		view.setMainLogo(entity.getMainLogo());
		view.setTitle(entity.getTitle());
		return view;
	}

	public ConfigView getConfig() {
		ConfigEntity entity = getConfigEntity();
		return convert(entity);
	}

	private ConfigEntity getConfigEntity() {
		Iterator<ConfigEntity> iterator = repo.findAll().iterator();

		if (iterator.hasNext()) {
			return iterator.next();
		} else {
			ConfigEntity config = new ConfigEntity();
			config.setMainLogo("http://parta-jeu.fr/wp-content/uploads/2019/10/LaLudotheque_512.png");
			config.setTitle("Parta'Jeu - Verfeil");
			return repo.save(config);
		}
	}

	@PostConstruct
	public void onInit() {
		ConfigEntity config = getConfigEntity();
		updateServletContext(config);
	}

	public ConfigView updateConfig(ConfigView input) {
		ConfigEntity config = getConfigEntity();
		config.setMainLogo(input.getMainLogo());
		config.setTitle(input.getTitle());
		updateServletContext(config);
		return convert(config);
	}

	private void updateServletContext(ConfigEntity config) {
		context.setAttribute("title", config.getTitle());
		context.setAttribute("main-logo", config.getMainLogo());
	}
}
