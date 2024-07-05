import java.awt.EventQueue;
import controller.TelaController;

public class Application {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaController telaController = new TelaController();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
