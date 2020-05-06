package MyRMIApp;

import Interfaces.ServerInfo;

public class OS implements ServerInfo {
	
	public OS(String name, String description) {
		algorithmDescription = description;
		serverName = name;
	}
	private String algorithmDescription;
	private String serverName;
	
	
	@Override
	public String getAlgorithmDescription() {
		return algorithmDescription;
	}

	@Override
	public String getServerName() {
		return serverName;
	}

}
