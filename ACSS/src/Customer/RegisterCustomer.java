package Customer;
import java.awt.Frame;
import java.awt.Label;

class RegisterAccount extends Frame {
    public RegisterAccount() {
        super("Registering Customer Account");
        setSize(400,300);
        setLocation(100,100);
        setTitle("Registering Customer Account");
        setLayout(null);
        
        Label title = new Label("--- Register a Customer Account---");
        title.setBounds(130, 30, 200, 20);
        
        add(title);
        
        setVisible(true);
    }
}
