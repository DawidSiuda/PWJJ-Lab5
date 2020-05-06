package AppRegistryPackage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppServerRegistry extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
			Parent root = loader.load();
	        primaryStage.setTitle("AppServerRegister");

			primaryStage.setScene(new Scene(root, 529, 612));
			// Control controller = loader.getController();
			//primaryStage.setOnHidden(e -> controller.shutdown());
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}