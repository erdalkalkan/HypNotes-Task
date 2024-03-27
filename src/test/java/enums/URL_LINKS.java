package enums;

public enum URL_LINKS {
    HOMEPAGE("https://test.urbanicfarm.com/"),
    LOGIN_URL("https://test.urbanicfarm.com/auth/login"),
    CONTACTUSPAGE_URL("https://test.urbanicfarm.com/api/public/contact"),
    BASEPAGELOGIN_URL("https://urbanicfarm.com/auth/login"),
    ADDADDRESSPAGE_URL("https://test.urbanicfarm.com/api/account/address/addAddress"),
    CANLILOGINAPIURL("https://urbanicfarm.com/api/public/login");

    private final String getLink;

    URL_LINKS(String getLink) {
        this.getLink = getLink;
    }

    public String getLink() {

        return getLink;
    }
}
