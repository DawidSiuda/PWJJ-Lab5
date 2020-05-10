package ClientPackage;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Interfaces.MyRegistryInterfaces;
import Interfaces.SortClassInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controler {

	public Label labelTitle;
	public Label labelTitle2;

	public TextField textFieldHowManyData;

	public Button buttonGenerate;
	public Button buttonRefreshServerList;
	public Button buttonSort;

	public TableView tableViewServerMap;
	public TableColumn<Integer, ServerRepresentation> tableColumnPort;
	public TableColumn<String, ServerRepresentation> tableColumnDescription;

	public ListView<Integer> listViewGeneratedData;
	public List<Integer> generatedData = new ArrayList<Integer>();

	public ListView<Integer> listViewReceivedData;
	public List<Integer> receivedData = new ArrayList<Integer>();

	public ListView<String> listViewLogs;

	MyRegistryInterfaces myRegistryInterfaces;
	int port;

	private Map<Integer, String> serverMap = new HashMap<Integer, String>();

	ObservableList<ServerRepresentation> observableListServers = FXCollections.observableArrayList();

	public void initialize() throws RemoteException {
		System.out.println("FUNCTION: initialize");

		textFieldHowManyData.setText("15");

		//
		// Connect to register.
		//

		try {
			myRegistryInterfaces = (MyRegistryInterfaces) Naming.lookup("rmi://localhost:5097/AppRegister");

			log("Connected to registry");

			port = myRegistryInterfaces.addClient("Client");

			log("Received port: " + port);

			String name = "Client " + port;
			labelTitle.setText(name);
		} catch (Exception e) {
			log("Error during connection.");
		}

		//
		// Generate table.
		//

		tableColumnPort = new TableColumn<Integer, ServerRepresentation>("Port");
		tableColumnPort.setCellValueFactory(new PropertyValueFactory<>("port"));

		tableColumnDescription = new TableColumn<String, ServerRepresentation>("Description");
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

		tableViewServerMap.getColumns().add(tableColumnPort);
		tableViewServerMap.getColumns().add(tableColumnDescription);

		buttonRefreshServerListClicked();
	}

	public void buttonGenerateClicked() {
		System.out.println("FUNCTION: buttonGenerateClicked");

		Random random = new Random();
		int value;

		try {
			value = Integer.parseInt(textFieldHowManyData.getText());
		} catch (Exception e) {
			MyMessage.show("Wrong number");
			return;
		}

		generatedData.clear();
		for (int i = 0; i < value; i++) {
			generatedData.add(random.nextInt(1000000));
		}

		reloadlistViewGeneratedData();

		log("Data has been generated.");
	}

	public void buttonRefreshServerListClicked() {
		log("FUNCTION: buttonRefreshServerListClicked");

		try {
			serverMap = myRegistryInterfaces.getServerMap();
		} catch (RemoteException e) {
			log("Error at function buttonRefreshServerListClicked.");
		}

		tableViewServerMap.getItems().clear();

		for (Integer val : serverMap.keySet()) {
			log(String.valueOf(val));
			tableViewServerMap.getItems().add(new ServerRepresentation(val, serverMap.get(val)));
		}
	}

	public void buttonSortClicked() {
		log("FUNCTION: buttonSortClicked");

		ServerRepresentation ser = (ServerRepresentation) tableViewServerMap.getSelectionModel().getSelectedItem();

		if (ser == null) {
			log("Cannot choose server, check if you selected correct server.");
			MyMessage.show("Error, Check if you selected server.");
			return;
		}

		log("Going to use server on port: " + ser.getPort());

		//
		// Send data here
		//

		try {
			String serverAddress = "rmi://localhost:" + ser.getPort() + "/SortServer";

			log("Address: " + serverAddress);
			
			SortClassInterface sortClassInterface = (SortClassInterface) Naming.lookup(serverAddress);

			log("Connected succesfull.");

			receivedData = sortClassInterface.solve(generatedData);

			reloadlistViewReceivedData();

		} catch (Exception e) {
			log("Cannot connect to hosted class.");
			MyMessage.show("Connection error.");
			return;
		}
	}

	private void reloadlistViewGeneratedData() {
		log("FUNCTION: reloadlistViewGeneratedData");

		listViewGeneratedData.getItems().clear();

		for (Integer var : generatedData) {
			listViewGeneratedData.getItems().add(var);
		}
	}

	private void reloadlistViewReceivedData() {
		log("FUNCTION: reloadlistViewReceivedData");

		listViewReceivedData.getItems().clear();

		for (Integer var : receivedData) {
			listViewReceivedData.getItems().add(var);
		}
	}

	private void log(String str) {
		listViewLogs.getItems().add(str);
		System.out.println(str);
	}

	public void end() {
		log("FUNCTION: end().");
		try {
			myRegistryInterfaces.removeClient(port);
		} catch (RemoteException e) {
			log("Error during disconnected.");
		}
	}
}