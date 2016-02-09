import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class MathClient {
    public static void main(String args[]) {
        try {
            String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry("localhost");
            ExamServer exam = (ExamServer) registry.lookup(name);
            int token = exam.login(123456, "lala");
            List<String> l = exam.getAvailableSummary(token,123456);
            System.out.println(l);
        }
        catch (Exception e) {
            System.err.println("MathClient exception");
            e.printStackTrace();
        }
    }
}
