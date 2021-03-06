package org.occiware.clouddesigner.occi.simulation.cloudsim.handlers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JOptionPane;

import org.occiware.clouddesigner.occi.AttributeState;
import org.occiware.clouddesigner.occi.Configuration;
import org.occiware.clouddesigner.occi.Resource;
import org.occiware.clouddesigner.occi.simulation.cloudsim.Cloudlet;
import org.occiware.clouddesigner.occi.simulation.cloudsim.CloudletScheduler;
import org.occiware.clouddesigner.occi.simulation.cloudsim.Datacenter;
import org.occiware.clouddesigner.occi.simulation.cloudsim.DatacenterBroker;
import org.occiware.clouddesigner.occi.simulation.cloudsim.DatacenterBrokerOcci;
import org.occiware.clouddesigner.occi.simulation.cloudsim.DatacenterCharacteristics;
import org.occiware.clouddesigner.occi.simulation.cloudsim.HarddriveStorage;
import org.occiware.clouddesigner.occi.simulation.cloudsim.Host;
import org.occiware.clouddesigner.occi.simulation.cloudsim.Log;
import org.occiware.clouddesigner.occi.simulation.cloudsim.Pe;
import org.occiware.clouddesigner.occi.simulation.cloudsim.Storage;
import org.occiware.clouddesigner.occi.simulation.cloudsim.UtilizationModel;
import org.occiware.clouddesigner.occi.simulation.cloudsim.Vm;
import org.occiware.clouddesigner.occi.simulation.cloudsim.VmAllocationPolicyOcci;
import org.occiware.clouddesigner.occi.simulation.cloudsim.VmScheduler;
import org.occiware.clouddesigner.occi.simulation.cloudsim.VmSchedulerTimeShared;
import org.occiware.clouddesigner.occi.simulation.cloudsim.core.CloudSim;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.Parser.Cloudlet_Config;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.Parser.Dc_Config;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.Parser.Entity;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.Parser.HarddriveStorage_Config;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.Parser.Host_Config;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.Parser.VM_Config;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.monitor.DatacenterMonitor;
import org.occiware.clouddesigner.occi.simulation.cloudsim.handlers.monitor.HostMonitor;
import org.occiware.clouddesigner.occi.simulation.cloudsim.provisioners.BwProvisioner;
import org.occiware.clouddesigner.occi.simulation.cloudsim.provisioners.BwProvisionerSimple;
import org.occiware.clouddesigner.occi.simulation.cloudsim.provisioners.PeProvisioner;
import org.occiware.clouddesigner.occi.simulation.cloudsim.provisioners.RamProvisioner;
import org.occiware.clouddesigner.occi.simulation.cloudsim.provisioners.RamProvisionerSimple;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Simulation {

	public static List<Cloudlet> cloudletList;
	public static List<Vm> vmlist;
	public static Map<Vm, Host> vmToHost;
	Map<Entity, Set<Entity>> entities;
	public StringBuilder result;
	public List<String> att, carac;
	Map<String, ArrayList<String>> rsrc;
	public List<Datacenter> listDatacenter;

	public Simulation(Map<Entity, Set<Entity>> entities){
		this.entities = entities;
		att = new ArrayList<String>();
		rsrc = new HashMap<String, ArrayList<String>>();
		carac = new ArrayList<String>();
		vmToHost = new HashMap<Vm, Host>();
		listDatacenter = new ArrayList<Datacenter>();
		carac.add("Cloudlet_ID,STATUS,Datacenter_ID,VM,Time,Start_Time,Finish_Time");
	}

	public void runExtension(Configuration configuration){

		//store the cloudletID for each VM. Map VmID to a set of cloudletID
		Map<Integer, Set<Integer>> idVmToIdcloudLet = new HashMap<Integer, Set<Integer>>();

		int num_user = 1; // number of cloud users
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false; // mean trace events
		int brokerId=0;

		// Initialize the CloudSim library
		CloudSim.init(num_user, calendar, trace_flag);
		DatacenterBroker broker = null;

		cloudletList = null;
		vmlist = null;
//		int vmid = 0, cloudLetId = 0;
		int nameBroker = 1, nbrDatacenter = this.getDcList().size();
		List<DatacenterBroker> listBroker = new ArrayList<DatacenterBroker>();

		/*
		// karn 
		Iterator<Entity> it2 = entities.keySet().iterator();
		
		while(it2.hasNext()){
			Object obj = it2.next();
			if (obj instanceof Dc_Config) {

				Datacenter dc = createDatacenter((Dc_Config)obj);				
				List<Host> listHost = dc.getHostList();
				
				Dc_Config dc_obj = (Dc_Config)obj;					
				DatacenterMonitor datacenterMonitor = new DatacenterMonitor(dc_obj.id, dc_obj.storage_elasticity, dc_obj.storage_capacity);
				ArrayList<HostMonitor> listHostMonitor = new ArrayList<HostMonitor>();

				for (Host host: listHost) {
					listHostMonitor.add(new HostMonitor(String.valueOf(host.getId()), host.getStorage()));
				}
				datacenterMonitor.listHostMonitor = listHostMonitor;
//				listDatacenterMonitor = new ArrayList<DatacenterMonitor>();
//				listDatacenterMonitor.add(datacenterMonitor);
				
				String dataCenterID = datacenterMonitor.id;
				String dataCenter_storage_elasticity = datacenterMonitor.storage_elasticity;
				long dataCeneter_storage_capacity = datacenterMonitor.storage_capacity;
				long totalHostStorage = 0;
				for (HostMonitor host: listHostMonitor) {
					totalHostStorage += host.storage;
				}

				if (dataCenter_storage_elasticity.equals("V") && totalHostStorage > dataCeneter_storage_capacity) {
					boolean isFound = false;
					for(Resource resource : configuration.getResources()) {
						for(AttributeState as : resource.getAttributes()) {
							if (as.getName().equals("occi.core.id") && as.getValue().equals(dataCenterID)) {
								for(AttributeState as2 : resource.getAttributes()) {
									if (as2.getName().contains("storage_capacity")) {
										as2.setValue(String.valueOf(totalHostStorage));
										dc_obj.storage_capacity = totalHostStorage;
										isFound = true;
										String text = "=== Previous configuration ===\n";
										text += "DataCenter: " + dataCenterID + "\n";
										text += "  " + "storage_elasticity: " + dataCenter_storage_elasticity + "\n";
										text += "  " + "storage_capacity: " + dataCeneter_storage_capacity + "\n";
										text += "Hosts: total storage: " + totalHostStorage + "\n\n";
										text += "=== Current configuration ===\n";
										text += "DataCenter: " + dataCenterID + "\n";
										text += "  " + "storage_capacity: " + totalHostStorage + "\n";
										
										JOptionPane.showMessageDialog(null, text, "Alert", JOptionPane.INFORMATION_MESSAGE);
										break;
									}
								}
							}
							if (isFound) break;
						}
						if (isFound) break;
					}
				}
				
				System.out.println("***" + datacenterMonitor);
			}
		}
		*/
		
		Iterator<Entity> it = entities.keySet().iterator();
		while(it.hasNext()){
			Object obj = it.next();
			if (obj instanceof Dc_Config) {

				Datacenter dc = createDatacenter((Dc_Config)obj);
				listDatacenter.add(dc);
				List<Host> listHost = dc.getHostList();

				//broker for each user 
				if (num_user != 0) {
					idVmToIdcloudLet = new HashMap<Integer, Set<Integer>>();
					cloudletList = new ArrayList<Cloudlet>();
					vmlist = new ArrayList<Vm>();
					broker = createBroker(nameBroker);
					brokerId = broker.getId();
					nameBroker++;
				}
				Set<Entity> hostlinkedToDatacenter = entities.get(obj);
				Iterator<Entity> itHost = hostlinkedToDatacenter.iterator();
				while (itHost.hasNext()) {
					Host_Config host_config = (Host_Config) itHost.next();
					Host host = getHostFromList(listHost, host_config);

					Set<Entity> vmLinkedToHost = entities.get(host_config);
					Iterator<Entity> itVM = vmLinkedToHost.iterator();
					while (itVM.hasNext()) {
						VM_Config vm = (VM_Config)itVM.next();
						createVm(vm, brokerId, host, vm.id_vm);
						idVmToIdcloudLet.put(vm.id_vm, new HashSet<Integer>());
						Set<Entity> cloudletLinkedToVm = entities.get(vm);
						Iterator<Entity> itCloudlet = cloudletLinkedToVm.iterator();
						while (itCloudlet.hasNext()) {
							Cloudlet_Config cloudLet = (Cloudlet_Config)itCloudlet.next();
							createCloudLet(cloudLet, brokerId, cloudLet.cloudletId);
							idVmToIdcloudLet.get(vm.id_vm).add(cloudLet.cloudletId);
//							cloudLetId++;
						}
//						vmid++;
					}
				}
				nbrDatacenter--;
				num_user--;

				if ((num_user == 0 && nbrDatacenter == 0) || num_user != 0) {
					broker.submitVmList(vmlist);
					broker.submitCloudletList(cloudletList);
					for (Entry<Integer, Set<Integer>> entry : idVmToIdcloudLet.entrySet()) {
						Integer idVm = entry.getKey();
						Set<Integer> idCloudLet = entry.getValue();
						for (int idc : idCloudLet) {
							broker.bindCloudletToVm(idc, idVm);
						}
					}
					listBroker.add(broker);
				}

			}
		}

		CloudSim.startSimulation();

		Set<List<Cloudlet>> resultList = new HashSet<List<Cloudlet>>();
		for (DatacenterBroker dcBr : listBroker) {
			List<Cloudlet> newList = dcBr.getCloudletReceivedList();
			resultList.add(newList);
		}

		CloudSim.stopSimulation();

		for (List<Cloudlet> newList : resultList) {
			printCloudletList(newList);
		}

		/*************/
		for(Datacenter datacenter: listDatacenter){
			for(Host host: datacenter.getHostList()){
				RamProvisionerSimple ramProv = (RamProvisionerSimple)host.getRamProvisioner();
				BwProvisionerSimple bwsimple = (BwProvisionerSimple)host.getBwProvisioner();
				VmSchedulerTimeShared pesimple = (VmSchedulerTimeShared)host.getVmScheduler();
				Map<Integer, Long> storageConsumed = host.getStorageConsumed();

				Log.printLine(ramProv.getramConsumed());
				Log.printLine(bwsimple.getBwConsumed());
				Log.printLine(pesimple.getMipsConsumed());
				Log.printLine(host.getStorageConsumed());

				for(Vm vm_: vmToHost.keySet()){
					int vmId = vm_.getId();
					ArrayList<String> l = new ArrayList<String>();
					if(vmToHost.get(vm_).getId() == host.getId()){
						l.add(""+ramProv.getramConsumed().get(vmId)*datacenter.getCharacteristics().getCostPerMem());
						Log.printLine("vmId: "+vmId+", RamPrice: "+ramProv.getramConsumed().get(vmId)*datacenter.getCharacteristics().getCostPerMem());
						l.add(""+bwsimple.getBwConsumed().get(vmId)*datacenter.getCharacteristics().getCostPerBw());
						Log.printLine("vmId: "+vmId+", BwPrice: "+bwsimple.getBwConsumed().get(vmId)*datacenter.getCharacteristics().getCostPerBw());
						double sumMipsPeInVm =0.000;
						for(Double mipsPe: pesimple.getMipsConsumed().get(vmId)){
							sumMipsPeInVm+=mipsPe;
						}
						l.add(""+sumMipsPeInVm*datacenter.getCharacteristics().getCostPerMi());
						Log.printLine("vmId: "+vmId+", MipsPrice: "+sumMipsPeInVm*datacenter.getCharacteristics().getCostPerMi());
						l.add(""+storageConsumed.get(vmId)*datacenter.getCharacteristics().getCostPerStorage());
						Log.printLine("vmId: "+vmId+", storagePrice: "+storageConsumed.get(vmId)*datacenter.getCharacteristics().getCostPerStorage());
						rsrc.put(""+vmId, l);
					}
				}
			}
		}
		/*************/

		getResult();
		Log.printLine("Simulation finished!");
	}

	public String getResult(){


		String result ="";
		//karn
		
		WritableWorkbook workbook = null;
		try {
			
			/* On cr�� un nouveau worbook et on l'ouvre en �criture */
			workbook = Workbook.createWorkbook(new File("results.xls"));
			
			/* On cr�� une nouvelle feuille (test en position 0) et on l'ouvre en �criture */
			WritableSheet sheet = workbook.createSheet("result", 0); 
			
//			sheet.addCell(new Label(0, 0, "Cloudlet_ID"));
//			sheet.addCell(new Label(1, 0, "STATUS"));
//			sheet.addCell(new Label(2, 0, "Datacenter_ID"));
//			sheet.addCell(new Label(3, 0, "VM"));
//			sheet.addCell(new Label(4, 0, "Time"));
//			sheet.addCell(new Label(5, 0, "Start_Time"));
//			sheet.addCell(new Label(6, 0, "Finish_Time"));
			
			int rowNum = 0;
			for(String line: carac){
				String[] res = line.split(",");
				for(int i=0; i<res.length; i++){
					result =result+ "\t \t \t \t |" +res[i];
					sheet.addCell(new Label(i, rowNum, res[i]));
				}
				++rowNum;
				result+="\n";
			}
			for(String line: att){
				String[] res = line.split(",");
				for(int i=0; i<res.length; i++){
					sheet.addCell(new Label(i, rowNum, res[i]));
					
					if(i==0)
						result =result+ "\t \t \t \t \t \t \t \t \t \t  \t" +res[i];
					if(i==1)
						result =result+ "\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t  \t" +res[i];
					if(i==2)
						result =result+ "\t \t \t \t \t \t \t \t \t \t" +res[i];
					if(i==3)
						result =result+ "\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t  \t \t  \t" +res[i];
					if(i==4)
						result =result+ "\t \t \t \t \t \t \t \t " +res[i];
					if(i==5)
						result =result+ "\t \t \t \t \t \t \t \t " +res[i];
					if(i==6)
						result =result+ "\t \t \t \t \t \t \t \t \t \t \t \t \t \t" +res[i];
				}
				result+="\n";
				++rowNum;
			}

			++rowNum;
			sheet.addCell(new Label(0, rowNum, "vmId"));
			sheet.addCell(new Label(1, rowNum, "RamPrice"));
			sheet.addCell(new Label(2, rowNum, "BwPrice"));
			sheet.addCell(new Label(3, rowNum, "MipsPrice"));
			sheet.addCell(new Label(4, rowNum, "StoragePrice"));
			++rowNum;
			
//			for(String vmId: rsrc.keySet()){
				for(Vm vm: vmlist){
					String vmId = String.valueOf(vm.getId());
					if(vmId.equals(Integer.toString(vm.getId()))){
						double m = (Double.parseDouble(rsrc.get(vmId).get(2))/vm.getMips())*100;
						result+="VM: "+vmId+", % AvgCpu: "+m+"\n";
					}

					/* Creation d'un champ au format numerique */
					sheet.addCell(new Label(0, rowNum, vmId)); 
					sheet.addCell(new Number(1, rowNum, Double.parseDouble(rsrc.get(vmId).get(0)))); 
					sheet.addCell(new Number(2, rowNum, Double.parseDouble(rsrc.get(vmId).get(1)))); 
					sheet.addCell(new Number(3, rowNum, Double.parseDouble(rsrc.get(vmId).get(2)))); 
					sheet.addCell(new Number(4, rowNum, Double.parseDouble(rsrc.get(vmId).get(3)))); 
					
					result+="VM: "+vmId+", RamPrice: "+rsrc.get(vmId).get(0)+"\n";
					result+="VM: "+vmId+", BwPrice: "+rsrc.get(vmId).get(1)+"\n";
					result+="VM: "+vmId+", MipsPrice: "+rsrc.get(vmId).get(2)+"\n";
					result+="VM: "+vmId+", StoragePrice: "+rsrc.get(vmId).get(3)+"\n";
					
					++rowNum;
				}
//			}
			
			/* On ecrit le classeur */
			workbook.write(); 
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (RowsExceededException e) {
			e.printStackTrace();
		}
		catch (WriteException e) {
			e.printStackTrace();
		}
		finally {
			if(workbook!=null){
				/* On ferme le worbook pour lib�rer la m�moire */
				try {
					workbook.close();
				} 
				catch (WriteException e) {
					e.printStackTrace();
				} 
				catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		return result;
	}


	/*********************************************/
	/************ CREATE ENTITIES ****************/
	/*********************************************/
	public Datacenter createDatacenter(Dc_Config dc){
		List<Host> hostList = new ArrayList<Host>();
		List<Pe> peList = new ArrayList<Pe>();
		Set<Entity> linkedToDataCenter = entities.get(dc);
		Iterator<Entity> itForDataCenter = linkedToDataCenter.iterator();
		LinkedList<Storage> storageList = new LinkedList<Storage>();

		while (itForDataCenter.hasNext()) {
			Entity entity = itForDataCenter.next();
			if(entity instanceof Host_Config){
				Host_Config host = (Host_Config)entity;

				int hostId = host.id_host, ram = host.ram, numPe = host.core; 
				long storage = host.storage, bw = host.bw ; 
				double mips = host.mips ;
				String peProvisioner = host.peProvisioner;
				String ramProvisioner = host.ramProvisioner;
				String bwProvisioner = host.bwProvisioner;
				String vmScheduler = host.vmScheduler;

				try {
					Class<?> classPeProvi = Class.forName("org.occiware.clouddesigner.occi.simulation.cloudsim.provisioners."+peProvisioner);
					Constructor<?> consPeProvi = classPeProvi.getConstructor(Double.class);
					Object peProvis = consPeProvi.newInstance(mips);
					for (int p = 0; p < numPe; p++)
						peList.add(new Pe(0, (PeProvisioner) peProvis));

					Class<?> classRamProvi = Class.forName("org.occiware.clouddesigner.occi.simulation.cloudsim.provisioners."+ramProvisioner);
					Constructor<?> consRamProvi = classRamProvi.getConstructor(Integer.class);
					Object ramProvis = consRamProvi.newInstance(ram);

					Class<?> classBwProvi = Class.forName("org.occiware.clouddesigner.occi.simulation.cloudsim.provisioners."+bwProvisioner);
					Constructor<?> consBwProvi = classBwProvi.getConstructor(Long.class);
					Object bwProvis = consBwProvi.newInstance(bw);

					Class<?> classVmSched = Class.forName("org.occiware.clouddesigner.occi.simulation.cloudsim."+vmScheduler);
					Constructor<?> consVmSched = classVmSched.getConstructor(List.class);
					Object vmSched = consVmSched.newInstance(peList);

					Host h = new Host(hostId, (RamProvisioner) ramProvis, (BwProvisioner)bwProvis, 
							storage, peList, (VmScheduler) vmSched);

					hostList.add(h);
				} catch (Exception e) {
					System.err.println("Exception in createDatacenter");
					e.printStackTrace();
				}
			}else{
				try {
					HarddriveStorage_Config storage = (HarddriveStorage_Config)entity;
					HarddriveStorage hardstorage =  new HarddriveStorage("storage_"+storage.id, storage.size);
					storageList.add(hardstorage);
				} catch (Exception e) {
					System.err.println("create storage Exception");
					e.printStackTrace();
				}
				itForDataCenter.remove();
			}
		}

		String arch = dc.architecture, os = dc.os, vmm = dc.vmm;
		double time_zone = dc.time_zone, cost = dc.costPerSec, costPerMem = dc.costPerMem, 
				costPerStorage = dc.costPerStorage, costPerBw = dc.costPerBw;

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(arch, os, vmm, hostList, time_zone,
				cost, costPerMem, costPerStorage, costPerBw);

		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(dc.name+""+dc.id, characteristics, new VmAllocationPolicyOcci(hostList),
					storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datacenter;
	}


	public static DatacenterBroker createBroker(int id) {
		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBrokerOcci("Broker" + id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}


	public static void createVm(VM_Config vmResource, int brokerId, Host host, int vmid) {

		double mips = vmResource.mips; 
		int pesNumber = vmResource.numberOfPes, ram = vmResource.ram;
		long size = vmResource.size, bw = vmResource.bw;
		String vmm = vmResource.vmm, cloudletSchedul = vmResource.cloudletScheduler;

		try {
			Class<?> classCloudletSchedul = Class.forName("org.occiware.clouddesigner.occi.simulation.cloudsim."+cloudletSchedul);
			Constructor<?> consCloudletSchedul = classCloudletSchedul.getConstructor();
			Object cloudletSchedulObject = consCloudletSchedul.newInstance();

			// TODO: retreive Scheduler from Mixin
			Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, (CloudletScheduler) cloudletSchedulObject);

			//host.vmCreate(vm);
			vm.setHost(host);
			vmToHost.put(vm, host);
			vmlist.add(vm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createCloudLet(Cloudlet_Config cloudLet, int brokerId, int id) {

		int pesNumber = cloudLet.pesNumber;
		long length = cloudLet.cloudletLength, fileSize = cloudLet.cloudletFileSize, outputSize = cloudLet.cloudletOutputSize;
		String utilizationModelBw = cloudLet.utilizationModelBw;
		String utilizationModelCpu = cloudLet.utilizationModelCpu;
		String utilizationModelRam = cloudLet.utilizationModelRam;

		try {
			Class<?> classModelCPU = Class.forName("org.occiware.clouddesigner.occi.simulation.cloudsim."+utilizationModelCpu);
			Constructor<?> consModelCPU = classModelCPU.getConstructor();
			Object modelCPUObject = consModelCPU.newInstance();

			Class<?> classModelRam = Class.forName("org.occiware.clouddesigner.occi.simulation.cloudsim."+utilizationModelRam);
			Constructor<?> consModelRam = classModelRam.getConstructor();
			Object modelRamObject = consModelRam.newInstance();

			Class<?> classModelBw = Class.forName("org.occiware.clouddesigner.occi.simulation.cloudsim."+utilizationModelBw);
			Constructor<?> consModelBw = classModelBw.getConstructor();
			Object modelBwObject = consModelBw.newInstance();

			// TODO: mixin for utilizationModel

			Cloudlet cloudlet = new Cloudlet(id, length, pesNumber, fileSize, outputSize, (UtilizationModel) modelCPUObject,
					(UtilizationModel) modelRamObject, (UtilizationModel) modelBwObject);

			cloudlet.setUserId(brokerId);
			cloudletList.add(cloudlet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*******************************************/
	/*************** PRINT RESULT **************/
	/*******************************************/
	private void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;		

		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {

				att.add(cloudlet.getCloudletId()+",SUCCESS"+","+cloudlet.getResourceId()+
						","+cloudlet.getVmId()+","+cloudlet.getActualCPUTime()+","
						+cloudlet.getExecStartTime()+","+cloudlet.getFinishTime());
			}
		}
	}
	/************************************/
	/************ PRIVATE METHOD*********/
	/**********************************/

	private List<Dc_Config> getDcList(){
		List<Dc_Config> l = new ArrayList<Dc_Config>();
		for(Entity obj: entities.keySet()){
			if(obj instanceof Dc_Config){
				Dc_Config dc = (Dc_Config) obj;
				l.add(dc);
			}
		}
		return l;
	}

	private Host getHostFromList(List<Host> list, Host_Config hc){

		for(Host h : list){
			if(h.getId()==hc.id_host)
				return h;
		}
		return null;

	}

}
