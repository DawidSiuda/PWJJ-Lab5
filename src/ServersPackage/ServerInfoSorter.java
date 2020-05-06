package ServersPackage;
import Interfaces.ServerInfo;

public class ServerInfoSorter implements ServerInfo {

	private String serverName;
	private String algorithmDescription;
	
	public ServerInfoSorter(String serverName, String algorithmDescription) {
		this.algorithmDescription = algorithmDescription;
		this.serverName = serverName;
	}
	
	@Override
	public String getServerName() {
		return serverName;
	}

	@Override
	public String getAlgorithmDescription() {
		return algorithmDescription;
	}

}
