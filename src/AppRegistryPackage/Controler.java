package AppRegistryPackage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import Interfaces.MyRegistryInterfaces;
import javafx.scene.control.ListView;

public class Controler extends UnicastRemoteObject implements MyRegistryInterfaces{
	//
	// FX variables
	//

	private static final long serialVersionUID = 1L;
	public ListView<String> listViewServerList;
	public Map<Integer,String> serverMap = new HashMap<Integer,String>();

	public ListView<String> listViewClientsList;
	public Map<Integer,String> clientsMap = new HashMap<Integer,String>();
	
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
		} catch (Exception e) {
			regristry = LocateRegistry.getRegistry(portOfRegistry);
		}
		
		regristry.rebind("AppRegister", this);

	}

	@Override
	public int addClient(String name) {
		appCounter++;
		int clientPort = portOfRegistry-appCounter;
			
		clientsMap.put(clientPort, name);
		reloadClientsList();
		
		log("Added client with port: " + clientPort);
		return portOfRegistry-appCounter;
	}
	
	public void removeClient(int port) {	
		try {
			for(Integer val : clientsMap.keySet()) {
				if(val == port) {
					clientsMap.remove(val); 
					log("Removed client at port: " + port);
					reloadClientsList();
					return;
				}
			}
		}
		catch (Exception e) {
			log("ERROR at remowing client.");
		}

		log("Cannot find client at port: " + port);

	}
	
	public int addServer(String ServerDescription) {
		
		appCounter++;
		int serverPort = portOfRegistry-appCounter;
			
		serverMap.put(serverPort, ServerDescription);
		reloadServerList();
		
		log("Added server with port: " + serverPort );
		return portOfRegistry-appCounter;
	}
	
	public void removeServer(int port) {
		
		try {
			for(Integer val : serverMap.keySet()) {
				if(val == port) {
					serverMap.remove(val);
					log("Removed server at port: " + port);
					reloadServerList();
					return;
				}
			}
		}
		catch (Exception e) {
			log("ERROR at remowing server");
		}

		log("Cannot find server at port: " + port);

	}
	
	private void reloadServerList() {
		listViewServerList.getItems().clear();

		for(Integer val : serverMap.keySet()) {
			listViewServerList.getItems().add(serverMap.get(val) + " port: " + val);
		}
	}
	
	private void reloadClientsList() {
		listViewClientsList.getItems().clear();

		for(Integer val : clientsMap.keySet()) {
			listViewClientsList.getItems().add(clientsMap.get(val) + " port: " + val);
		}
	}
	
    public void shutdown() {
        // cleanup code here...
        System.out.println("EndProgram");
        //regristry.stop();
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