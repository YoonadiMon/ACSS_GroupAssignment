package Manager;

public class Manager {
    String username;
    String password;
    String securityphrase;

    public Manager(String username, String password, String securityphrase) {
        this.username = username;
        this.password = password;
        this.securityphrase = securityphrase;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityPhrase(){
        return securityphrase;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    @Override
    public String toString() {
        return username + "," + password + "," + securityphrase;
    }

    public static Manager fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            return new Manager(parts[0], parts[1], parts[2]);
        } else {
            throw new IllegalArgumentException("Invalid manager data: " + line);
        }
    }
}