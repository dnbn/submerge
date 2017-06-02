package com.github.dnbn.submerge.cli.configuration.user;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.github.dnbn.submerge.api.subtitle.config.Font;

/**
 * This object contains factory methods for each Java content interface and Java element
 * interface generated in the com.github.dnbn.submerge.cli.configuration.user package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java
 * representation for XML content. The Java representation of XML content can consist of
 * schema derived interfaces and classes representing the binding of schema type
 * definitions, element declarations and model groups. Factory methods for each of these
 * are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _UserConfiguration_QNAME = new QName("", "userConfiguration");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of schema
	 * derived classes for package: com.github.dnbn.submerge.cli.configuration.user
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link UserConfiguration }
	 * 
	 */
	public UserConfiguration createUserConfiguration() {
		return new UserConfiguration();
	}

	/**
	 * Create an instance of {@link FontConfig }
	 * 
	 */
	public Font createFontConfig() {
		return new Font();
	}

	/**
	 * Create an instance of {@link SubtitleConfig }
	 * 
	 */
	public SubtitleConfig createSubtitleConfig() {
		return new SubtitleConfig();
	}

	/**
	 * Create an instance of {@link DualAssConfig }
	 * 
	 */
	public DualAssConfig createDualAssConfig() {
		return new DualAssConfig();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link UserConfiguration }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "userConfiguration")
	public JAXBElement<UserConfiguration> createUserConfiguration(UserConfiguration value) {
		return new JAXBElement<>(_UserConfiguration_QNAME, UserConfiguration.class, null, value);
	}

}
