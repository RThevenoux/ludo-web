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

	@PostConstruct
	public void onInit() {
		ConfigEntity config = getConfig();
		updateServletContext(config);
	}

	public ConfigEntity getConfig() {
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

	public void updateConfig(ConfigInput input) {
		ConfigEntity config = getConfig();
		config.setMainLogo(input.getMainLogo());
		config.setTitle(input.getTitle());
		updateServletContext(config);
	}

	private void updateServletContext(ConfigEntity config) {
		context.setAttribute("title", config.getTitle());
		context.setAttribute("main-logo", config.getMainLogo());
	}
}
