package AppRegistryPackage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import Interfaces.MyRegistryInterfaces;
import javafx.scene.control.ListView;

public class Controler extends UnicastRemoteObject implements MyRegistryInterfaces {
	//
	// FX variables
	//

	private static final long serialVersionUID = 1L;
	public ListView<String> listViewServerList;
	public Map<Integer, String> serverMap = new HashMap<Integer, String>();

	public ListView<String> listViewClientsList;
	public Map<Integer, String> clientsMap = new HashMap<Integer, String>();

	public ListView<String> listViewLogs;

	private Registry regristry;
	private int portOfRegistry = 5097;
	private int appCounter = 0;

	public Controler() throws RemoteException {
		super();
	}

	public void initialize() throws RemoteException {
		System.out.println("FUNCTION: initialize");

		try {
			regristry = LocateRegistry.createRegistry(portOfRegistry);
			log("Creates registry on port: " + portOfRegistry);
		} catch (Exception e) {
			regristry = LocateRegistry.getRegistry(portOfRegistry);
			log("Got registry on port: " + portOfRegistry);
		}

		regristry.rebind("AppRegister", this);
		log("Rebinded registry.");

	}

	@Override
	public int addClient(String name) {
		log("Registering new client...");

		appCounter++;
		int clientPort = portOfRegistry - appCounter;

		clientsMap.put(clientPort, name);
		reloadClientsList();

		log("Added client with port: " + clientPort);
		return portOfRegistry - appCounter;
	}

	@Override
	public Boolean removeClient(int port) {
		try {
			for (Integer val : clientsMap.keySet()) {
				if (val == port) {
					clientsMap.remove(val);
					log("Removed client at port: " + port);
					reloadClientsList();
					return true;
				}
			}
		} catch (Exception e) {
			log("ERROR at remowing client.");
		}

		log("Cannot find client at port: " + port);

		return false;
	}

	@Override
	public int addServer(String name, String description) {

		try {
			log("Registering new server...");

			appCounter++;
			int serverPort = portOfRegistry - appCounter;

			log("Chose port " + serverPort + " for asking server.");

			if (name == null)
				name = "SortServer";
			if (description == null)
				description = "";

			serverMap.put(serverPort, name + " "+ description );
			reloadServerList();

			log("Added server with port: " + serverPort);
		} catch (Exception e) {
			log("ERROR: Problems during adding new server.");
			return 0;
		}
		return portOfRegistry - appCounter;
	}

	@Override
	public Boolean removeServer(int port) {

		try {
			for (Integer val : serverMap.keySet()) {
				if (val == port) {
					serverMap.remove(val);
					log("Removed server at port: " + port);
					reloadServerList();
					return true;
				}
			}
		} catch (Exception e) {
			log("ERROR at remowing server");
		}

		log("Cannot find server at port: " + port);

		return false;
	}

	private void reloadServerList() {
		try {
		listViewServerList.getItems().clear();

		for (Integer val : serverMap.keySet()) {
			listViewServerList.getItems().add(serverMap.get(val) + " port: " + val);
		}
		}catch (Exception e) {
		}
	}

	private void reloadClientsList() {
		listViewClientsList.getItems().clear();

		for (Integer val : clientsMap.keySet()) {
			listViewClientsList.getItems().add(clientsMap.get(val) + " port: " + val);
		}
	}

	public void shutdown() {
		System.out.println("EndProgram");
	}

	private void log(String str) {
			listViewLogs.getItems().add(str);
			System.out.println(str);
	}

	@Override
	public Map<Integer, String> getServerMap() throws RemoteException {
		return serverMap;
	}
}