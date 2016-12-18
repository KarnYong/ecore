package org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.monitor;

import java.util.ArrayList;

public class DatacenterMonitor {
	public String id;
	public String storage_elasticity;
	public long storage_capacity;
	public ArrayList<HostMonitor> listHostMonitor = new ArrayList<HostMonitor>();
	public DatacenterMonitor() {
		this.id = "";
		this.storage_elasticity = "";
		this.storage_capacity = 0;
	}
	public DatacenterMonitor(String id, String storage_elasticity, long storage_capacity) {
		this.id = id;
		this.storage_elasticity = storage_elasticity;
		this.storage_capacity = storage_capacity;
	}
	@Override
	public String toString() {
		return "DatacenterMonitor [id=" + id + ", storage_elasticity=" + storage_elasticity + ", storage_capacity="
				+ storage_capacity + ", listHostMonitor=" + listHostMonitor + "]";
	}

}
