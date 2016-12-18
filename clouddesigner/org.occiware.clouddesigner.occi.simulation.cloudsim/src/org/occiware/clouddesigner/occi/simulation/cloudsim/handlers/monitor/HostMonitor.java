package org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.monitor;

public class HostMonitor {
	public String id;
	public long storage;
	public HostMonitor() {
		id = "";
		storage = 0;
	}
	public HostMonitor(String id, long storage) {
		this.id = id;
		this.storage = storage;
	}
	@Override
	public String toString() {
		return "HostMonitor [id=" + id + ", storage=" + storage + "]";
	}
}
