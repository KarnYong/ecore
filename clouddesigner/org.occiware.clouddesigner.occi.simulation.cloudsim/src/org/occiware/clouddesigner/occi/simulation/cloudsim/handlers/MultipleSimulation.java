package org.occiware.clouddesigner.occi.simulation.cloudsim.handlers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.occiware.clouddesigner.occi.AttributeState;
import org.occiware.clouddesigner.occi.Configuration;
import org.occiware.clouddesigner.occi.Resource;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.Parser.Entity;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.Parser.Host_Config;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.Parser.VM_Config;;

public class MultipleSimulation {
	Map<Entity, Set<Entity>> entities;

	public MultipleSimulation(Map<Entity, Set<Entity>> entities) {
		this.entities = entities;
	}
	
	public void runExtensions(Configuration config) {
		Iterator<Entity> it = entities.keySet().iterator();
		while(it.hasNext()){
			Object obj = it.next();
			if (obj instanceof Host_Config) {
				Host_Config hostConfig = (Host_Config)obj;
				for (int i = 0; i < 10; i++) {
					Set<Entity> ents = entities.get(hostConfig);
					entities.remove(hostConfig);
//					vmConfig.size = hostConfig.storage;
					long totalSize = 0;
					for (Entity ent: ents) {
						VM_Config vmConfig = (VM_Config)ent;
						ents.remove(ent);
						vmConfig.size += i * 100;
						totalSize += vmConfig.size;
						ents.add(ent);
					}
					hostConfig.storage = totalSize;
					entities.put(hostConfig, ents);
					Simulation sim = new Simulation(this.entities);
					sim.runExtension(config);
					
					JOptionPane.showMessageDialog(null, sim.getResult(),
					"Simulation Result",
					JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
}
