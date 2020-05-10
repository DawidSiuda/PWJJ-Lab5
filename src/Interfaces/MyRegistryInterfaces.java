package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface MyRegistryInterfaces extends Remote{
	public int addClient(String name) throws RemoteException;
	public Boolean removeClient(int port) throws RemoteException;
	public int addServer(String name, String description) throws RemoteException;
	public Boolean removeServer(int port) throws RemoteException;
	public Map<Integer,String>getServerMap() throws RemoteException;
}
