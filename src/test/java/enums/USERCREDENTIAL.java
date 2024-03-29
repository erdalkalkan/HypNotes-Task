package enums;

public enum USERCREDENTIAL {
    TR1("hypnotes2022tr1@gmail.com","Aydinlik123/"),
    TR2("hypnotes2022tr2@gmail.com", "Aydinlik123/")
    ;

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
