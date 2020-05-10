package ServersPackage;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Interfaces.MyRegistryInterfaces;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Controler {
	//
	// FX variables
	//

	public Label texLabelTitle;
	public Button buttonRegister;
	public Button buttonUnregister;
	public ListView<String> listViewLogs;

	private QuickSorter quickSorter;

	private Registry regristry;
//	Registry regristry;

	String name;

	int port;
	MyRegistryInterfaces myRegistryInterfaces;

	public Controler() {
	}

	public void initialize() throws MalformedURLException, RemoteException, NotBoundException {

		if (createNewConnection() == false) {
			return;
		}

		registerServerClass();

		return;
	}

	public void buttonRegisterClicket() {
		System.out.println("FUNCTION: buttonRegisterClicket");
		
		end();

		if (createNewConnection() == false) {
			return;
		}

		registerServerClass();

		return;
	}

	private Boolean createNewConnection() {
		try {
			myRegistryInterfaces = (MyRegistryInterfaces) Naming.lookup("rmi://localhost:5097/AppRegister");

			listViewLogs.getItems().add("Connected to registry");

			port = myRegistryInterfaces.addServer("Server");

			listViewLogs.getItems().add("Received port: " + port);

			name = "Server " + port;

			texLabelTitle.setText(name);

		} catch (Exception e) {
			log("ERROT: Cannot establish new connection with AppRegister.");
			return false;
		}

		return true;
	}

	private Boolean registerServerClass() {
		log("Seting QuickSort class on port: " + port + "...");

		try {

			try {
				regristry = LocateRegistry.createRegistry(port);
			} catch (Exception e) {
				regristry = LocateRegistry.getRegistry(port);
			}

			log("Created registryu on port " + port);

			quickSorter = new QuickSorter(name);

			regristry.rebind("SortServer", quickSorter);

			log("QuickSort has been set.");

		} catch (Exception e) {
			log("ERROR: Cannot set QuickSort in registry.");
			return false;
		}

		return true;
	}

//	public void buttonUnegisterClicket() {
//		System.out.println("FUNCTION: buttonUnegisterClicket");
//		return;
//	}

	private void log(String str) {
		listViewLogs.getItems().add(str);
//		int size = listViewLogs.getItems().size();
//		if (size > 2)
//			listViewLogs.scrollTo(size - 1);
		System.out.println(str);
	}

	public void end() {
		System.out.println("FUNCTION: end()");
		if (myRegistryInterfaces != null) {
			try {
				myRegistryInterfaces.removeServer(port);
				regristry.unbind("SortServer");
				UnicastRemoteObject.unexportObject(regristry, true);
			} catch (Exception e) {
				System.out.println("ERROR: get warnings while removing server.");
				e.getMessage();
			}
		}
	}

}