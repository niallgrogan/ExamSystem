import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MathClient {
    public static void main(String args[]) {
        try {
            String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry("localhost");
            ExamServer exam = (ExamServer) registry.lookup(name);
            System.out.println("here");

        }
        catch (Exception e) {
            System.err.println("MathClient exception");
            e.printStackTrace();
        }
    }
}
