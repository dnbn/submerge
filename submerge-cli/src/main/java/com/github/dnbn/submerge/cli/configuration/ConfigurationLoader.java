package com.github.dnbn.submerge.cli.configuration;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.github.dnbn.submerge.cli.configuration.cli.CliConfiguration;
import com.github.dnbn.submerge.cli.configuration.user.UserConfiguration;
import com.github.dnbn.submerge.cli.exception.ConfigurationLoaderException;

public class ConfigurationLoader {

	/**
	 * Filename of the user configuration file
	 */
	private static final String CLI_CONFIG_XML = "cli-config.xml";

	/**
	 * Filename of the user application configuration file
	 */
	private static final String USER_CONFIG_XML = "user-config.xml";

	/**
	 * Private constructor
	 */
	private ConfigurationLoader() {

		throw new AssertionError();
	}

	/**
	 * Load the user configuration file
	 * 
	 * @return the Ouser configuration object
	 */
	public static UserConfiguration loadUserConfiguration() {

		ClassLoader classLoader = ConfigurationLoader.class.getClassLoader();

		try (InputStream configStream = classLoader.getResourceAsStream(USER_CONFIG_XML);) {

			JAXBContext jaxbContext = JAXBContext.newInstance(UserConfiguration.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (UserConfiguration) jaxbUnmarshaller.unmarshal(configStream);

		} catch (JAXBException | IOException e) {
			throw new ConfigurationLoaderException("Cannot parse the configuration file " + USER_CONFIG_XML, e);
		}
	}

	/**
	 * Load the application configuration file
	 * 
	 * @return the user configuration object
	 */
	public static CliConfiguration loadCliConfiguration() {

		ClassLoader classLoader = ConfigurationLoader.class.getClassLoader();

		try (InputStream configStream = classLoader.getResourceAsStream(CLI_CONFIG_XML);) {

			JAXBContext jaxbContext = JAXBContext.newInstance(CliConfiguration.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (CliConfiguration) jaxbUnmarshaller.unmarshal(configStream);

		} catch (JAXBException | IOException e) {
			throw new ConfigurationLoaderException("Cannot parse the configuration file " + CLI_CONFIG_XML, e);
		}
	}

}
