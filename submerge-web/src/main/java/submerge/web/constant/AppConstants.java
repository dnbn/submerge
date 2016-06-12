package submerge.web.constant;

public enum AppConstants {

	BUNDLE_RESSOURCE_BASENAME("submerge.i18n.text"),
	BUNDLE_RESSOURCE("msg"),
	SHA_256("SHA-256");
	
	final static String[] supportedLanguages = {"en", "fr", "zh"};

	private String constant;

	AppConstants(String constant) {
		this.constant = constant;
	}

	@Override
	public String toString() {
		return this.constant;
	}

}
