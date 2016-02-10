//import java.awt.*;
//import java.awt.event.*;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import javax.swing.*;
//
//public class MathGUIClient extends JFrame {
//    private JTextField username;
//    private JPasswordField password;
//    private JButton loginButton;
//    private String studentid;
//    private String passcode;
//    private static ExamServer exam;
//
//    public MathGUIClient() {
//        super("Math GUI Client");
//
//        Container container = getContentPane();
//        container.setLayout(new FlowLayout());
//
//        username =
//                new JTextField("Username");
//        container.add(username);
//
//        password =
//                new JPasswordField("Password");
//        container.add(password);
//
//        loginButton =
//                new JButton("Login");
//        container.add(loginButton);
//
//        LoginButtonHandler loginHandler = new LoginButtonHandler();
//
//        TextFieldHandler textHandler = new TextFieldHandler();
//
//        setSize(325,100);
//        setVisible(true);
//    }
//
//    private class LoginButtonHandler implements ActionListener{
//        public void actionPerformed(ActionEvent event) {
//            int token = exam.login(123456, "lala");
//        }
//    }
//
//    private class TextFieldHandler implements ActionListener {
//        public void actionPerformed(ActionEvent event) {
//            if(event.getSource() == username) {
//                studentid = event.getActionCommand();
//            }
//            else if(event.getSource() == password) {
//                passcode = event.getActionCommand();
//            }
//        }
//    }
//
//    public static void main(String args[])
//    {
//        try {
//            String name = "ExamServer";
//            Registry registry = LocateRegistry.getRegistry("localhost");
//            exam = (ExamServer) registry.lookup(name);
//            MathGUIClient app = new MathGUIClient();
//            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        }
//        catch (Exception e) {
//            System.err.println("MathClient exception");
//            e.printStackTrace();
//        }
//    }
//}
