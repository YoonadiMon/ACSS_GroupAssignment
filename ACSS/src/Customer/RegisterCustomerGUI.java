package Customer;
import java.awt.*;

class RegisterAccount extends Frame {
    public RegisterAccount(int width, int height) {
        super("Registering Customer Account");

        // Content Alignment Variables
        int ContentH = 30;
        int GapX = 30;
        int titleWidth = 250;

        setSize(width, height);
        setLocation(100,100);
        setTitle("Registering Customer Account");
        setLayout(null);
        
        Label title = new Label("--- Register a new Customer Account ---");
        title.setBounds((width-titleWidth)/2, ContentH, titleWidth, 20);

        ContentH += GapX;
        Label username = new Label("Username");
        username.setBounds(50, ContentH, 80, 20);

        TextField usernameTF = new TextField();
        usernameTF.setBounds(150, ContentH, 150, 20);

        ContentH += GapX;
        Label email = new Label("Email");
        email.setBounds(50, ContentH, 80, 20);

        TextField emailTF = new TextField();
        emailTF.setBounds(150, ContentH, 150, 20);

        ContentH += GapX;
        Label password = new Label("Password");
        password.setBounds(50, ContentH, 80, 20);

        TextField passwordTF = new TextField();
        passwordTF.setBounds(150, ContentH, 150, 20);

        ContentH += GapX;
        Button sbmt = new Button("Submit");
        sbmt.setBounds(50, ContentH, 100, 30);

        Button reset = new Button("Reset");
        reset.setBounds(170,ContentH,100,30);
        
        add(title);
        add(username);
        add(email);
        add(password);
        add(usernameTF);
        add(emailTF);
        add(passwordTF);
        add(sbmt);
        add(reset);

        setVisible(true);
    }
}
