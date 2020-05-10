package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SortClassInterface  extends Remote{
	public List<Integer> solve(List<Integer> arg) throws RemoteException;
}
