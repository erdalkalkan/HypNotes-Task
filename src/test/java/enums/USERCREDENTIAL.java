package enums;

public enum USERCREDENTIAL {
    USER1("urbanicfarm2@gmail.com","Urbanicfarm2/"),
    USER2("urbanicfarm2@yopmail.com", "Urbanicfarm2/"),
    USER3("seller_urban@mailsac.com","VHt*zzt*wQNu6XS"),
    USER4("buyer_urban@mailsac.com","VHt*zzt*wQNu6XS"),
    USER5("seller_urban2@yopmail.com","Seller2/"),
    USERERDAL("erdal@yopmail.com","nPfXAzs656Jw6*w"),
    USERVEDAT("testuser@deneme.com","Testuser1/."),
    USERBUYER("peummonnemane-5141@yopmail.com","e*y7G2xhsTVAi5u"),
    PAYPALACCOUNT("sandbox@personal.example.com","NvR29+Ww"),
    USERBASEWEBSITE("test_seller@mailsac.com","e976S2rhtySNTdk");

    private final String getUsername;
    private final String getPassword;
    USERCREDENTIAL(String username, String password) {
        this.getUsername = username;
        this.getPassword = password;
    }
    public String getUsername(){
        return getUsername;
    }
    public String getPassword(){
        return getPassword;
    }
}
