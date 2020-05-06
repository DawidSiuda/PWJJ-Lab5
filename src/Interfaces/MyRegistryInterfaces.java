package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface MyRegistryInterfaces extends Remote{
	//public int getNumberOfInstance() throws RemoteException;
	public int addClient(String name) throws RemoteException;
	public void removeClient(int port) throws RemoteException;
	public int addServer(String name) throws RemoteException;
	public void removeServer(int port) throws RemoteException;
	public Map<Integer,String>getServerMap() throws RemoteException;
}
