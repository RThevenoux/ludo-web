package io.ludoweb.core.config;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigService {

	@Autowired
	ServletContext context;
	@Autowired
	ConfigRepository repo;

	@Value("${io.ludoweb.default-config.mainLogo}")
	String defaultMainLogo;
	@Value("${io.ludoweb.default-config.title}")
	String defaultTitle;

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

			config.setMainLogo(defaultMainLogo);
			config.setTitle(defaultTitle);

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
