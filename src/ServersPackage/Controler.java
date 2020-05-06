package ServersPackage;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
	
	int port;
	MyRegistryInterfaces myRegistryInterfaces;
	
	public Controler() {
	}


	public void initialize() throws MalformedURLException, RemoteException, NotBoundException {
		
		try {
		System.out.println("FUNCTION: initialize");

		myRegistryInterfaces = (MyRegistryInterfaces) Naming.lookup("rmi://localhost:5097/AppRegister");
		
		listViewLogs.getItems().add("Connected to registry");
		
		port = myRegistryInterfaces.addServer("Server");

		listViewLogs.getItems().add("Received port: " + port);

		String name = "Server " + port;
		texLabelTitle.setText(name);
		quickSorter = new QuickSorter(name);
		}
		catch (Exception e) {
			System.out.println("ERROR at end()");
		}
	}

	public void buttonRegisterClicket() {
		System.out.println("FUNCTION: buttonRegisterClicket");
		return;
	}
	
	public void buttonUnegisterClicket() {
		System.out.println("FUNCTION: buttonUnegisterClicket");
		return;
	}
	
	public void end()
	{
		System.out.println("FUNCTION: end()");
		if(myRegistryInterfaces != null)
		{
			try {
				myRegistryInterfaces.removeServer(port);
			} catch (Exception e) {
				System.out.println("ERROR at end()");
			}
		}
	}
	
}