package gr.dit.project1.payload;

import java.util.List;

public class InsertRequest {
	private String sourceIp;
	private String timestamp;
	private String type;
	private String size;
	private String method;
	private String referer;
	private String resource;
	private String response;
	private String userAgent;
	private String userId;
	private List<String> destinationIp;
	private List<String> blockId;
	public String getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getDestinationIp() {
		return destinationIp;
	}
	public void setDestinationIp(List<String> destinationIp) {
		this.destinationIp = destinationIp;
	}
	public List<String> getBlockId() {
		return blockId;
	}
	public void setBlockId(List<String> blockId) {
		this.blockId = blockId;
	}
	
}
