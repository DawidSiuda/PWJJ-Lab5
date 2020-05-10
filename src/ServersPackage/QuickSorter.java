package ServersPackage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import Interfaces.ServerInfo;
import Interfaces.SortClassInterface;
import javafx.scene.control.ListView;

public class QuickSorter extends UnicastRemoteObject implements SortClassInterface {

	private static final long serialVersionUID = 1L;

	public QuickSorter() throws RemoteException {
		super();
	}

	public QuickSorter(String serverName) throws RemoteException {
		super();
	}

	@Override
	public List<Integer> solve(List<Integer> inputList) throws RemoteException {

		List<Integer> list = null;
		try {
			if (inputList == null) {
				System.out.println("ERROR: List is nullptr");
				return null;
			}

			int size = inputList.size();
			list = new ArrayList<Integer>();

			Integer[] array = new Integer[inputList.size()];
			array = inputList.toArray(array);

			sort(array, 0, size - 1);

			list = Arrays.asList(array);

		} catch (Exception e) {
			System.out.println("ERROR: Solve throws exception.");
		}

		return list;
	}

	int partition(Integer arr[], int low, int high) {
		int pivot = arr[high];
		int i = (low - 1);
		for (int j = low; j < high; j++) {
			// If current element is smaller than the pivot
			if (arr[j] < pivot) {
				i++;

				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		int temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;

		return i + 1;
	}

	void sort(Integer arr[], int low, int high) {
		if (low < high) {

			int pi = partition(arr, low, high);

			sort(arr, low, pi - 1);
			sort(arr, pi + 1, high);
		}
	}

	ListView<String> listViewLogs;
}
