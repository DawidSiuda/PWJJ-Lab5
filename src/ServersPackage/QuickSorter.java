package ServersPackage;

import java.util.ArrayList;
import java.util.List;

import Interfaces.ServerInfo;

public class QuickSorter {
	
	public QuickSorter(String serverName)
	{
		serverInfo = new ServerInfoSorter(serverName, "QuickSort");
	}
	public List<Integer> solve(List<Integer> inputList)	{
		
		List<Integer> outputList = new ArrayList<Integer>();
		for (Integer num : inputList) {
			num = 0;
			outputList.add(num);
		}
		return outputList;
	}
	
	private ServerInfo serverInfo;

	public ServerInfo getServerInfo() {
		return serverInfo;
	} 
}
