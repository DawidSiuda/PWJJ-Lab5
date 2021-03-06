package ServersPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SortServer extends Application {
	
	FXMLLoader loader;
	Controler controler;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
			Parent root = loader.load();
	        primaryStage.setTitle("QuickSortServer");
			primaryStage.setScene(new Scene(root, 528, 434));
			controler = (Controler)loader.getController();
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop(){
		controler.end();
	}

	public static void main(String[] args) {
		launch(args);
	}
}


