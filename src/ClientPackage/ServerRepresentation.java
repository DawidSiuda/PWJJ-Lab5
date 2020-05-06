package ClientPackage;

public class ServerRepresentation {
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	ServerRepresentation(int port, String description){
		this.port = port;
		this.description = description;
	}
	public Integer port;
	public String description;
}
