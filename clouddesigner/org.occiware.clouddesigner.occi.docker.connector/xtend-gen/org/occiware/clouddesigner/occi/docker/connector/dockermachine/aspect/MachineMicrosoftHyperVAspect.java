package org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod;
import org.occiware.clouddesigner.occi.docker.Machine_Microsoft_Hyper_V;
import org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineAspect;
import org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties;

@Aspect(className = Machine_Microsoft_Hyper_V.class)
@SuppressWarnings("all")
public class MachineMicrosoftHyperVAspect extends MachineAspect {
  @OverrideAspectMethod
  public static String createMachineCommand(final Machine_Microsoft_Hyper_V _self) {
    org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_ = org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectContext.getSelf(_self);
    Object result = null;
     if (_self instanceof org.occiware.clouddesigner.occi.docker.Machine_Microsoft_Hyper_V){
    result = org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspect._privk3_createMachineCommand(_self_, (org.occiware.clouddesigner.occi.docker.Machine_Microsoft_Hyper_V)_self);
    } else  if (_self instanceof org.occiware.clouddesigner.occi.docker.Machine){
    result = org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineAspect.createMachineCommand((org.occiware.clouddesigner.occi.docker.Machine)_self);
    } else  { throw new IllegalArgumentException("Unhandled parameter types: " + java.util.Arrays.<Object>asList(_self).toString()); };
    return (java.lang.String)result;
  }
  
  public static void machineStartAll(final Machine_Microsoft_Hyper_V _self) {
    org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_ = org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectContext.getSelf(_self);
    _privk3_machineStartAll(_self_, _self);
  }
  
  public static void machineStart(final Machine_Microsoft_Hyper_V _self) {
    org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_ = org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectContext.getSelf(_self);
    _privk3_machineStart(_self_, _self);
  }
  
  protected static boolean isDeployed(final Machine_Microsoft_Hyper_V _self) {
    org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_ = org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectContext.getSelf(_self);
    Object result = null;
    result =_privk3_isDeployed(_self_, _self);
    return (boolean)result;
  }
  
  protected static void isDeployed(final Machine_Microsoft_Hyper_V _self, final boolean isDeployed) {
    org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_ = org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectContext.getSelf(_self);
    _privk3_isDeployed(_self_, _self,isDeployed);
  }
  
  private static String super_createMachineCommand(final Machine_Microsoft_Hyper_V _self) {
    org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineAspectMachineAspectProperties _self_ = org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineAspectMachineAspectContext.getSelf(_self);
    return  org.occiware.clouddesigner.occi.docker.connector.dockermachine.aspect.MachineAspect._privk3_createMachineCommand(_self_, _self);
  }
  
  protected static String _privk3_createMachineCommand(final MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_, final Machine_Microsoft_Hyper_V _self) {
    return null;
  }
  
  protected static void _privk3_machineStartAll(final MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_, final Machine_Microsoft_Hyper_V _self) {
  }
  
  protected static void _privk3_machineStart(final MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_, final Machine_Microsoft_Hyper_V _self) {
  }
  
  protected static boolean _privk3_isDeployed(final MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_, final Machine_Microsoft_Hyper_V _self) {
     return _self_.isDeployed; 
  }
  
  protected static void _privk3_isDeployed(final MachineMicrosoftHyperVAspectMachine_Microsoft_Hyper_VAspectProperties _self_, final Machine_Microsoft_Hyper_V _self, final boolean isDeployed) {
    _self_.isDeployed = isDeployed; try {
    
    			for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    				if (m.getName().equals("set" + "IsDeployed")
    						&& m.getParameterTypes().length == 1) {
    					m.invoke(_self, isDeployed);
    
    				}
    			}
    		} catch (Exception e) {
    			// Chut !
    		} 
  }
}