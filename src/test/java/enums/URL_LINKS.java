package enums;

public enum URL_LINKS {
    HOMEPAGE("https://test.hypnotes.net/"),
    LOGIN_URL("https://test.hypnotes.net/login");

    private final String getLink;

    URL_LINKS(String getLink) {
        this.getLink = getLink;
    }

    public String getLink() {

        return getLink;
    }

}
