/**
 * Copyright (c) 2015 INRIA.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * - Philippe MERLE
 * 	- Fawaz PARAISO
 */
package org.occiware.clouddesigner.occi.docker.connector;

import com.github.dockerjava.api.model.Event;
import com.github.dockerjava.core.command.EventsResultCallback;
import com.google.common.base.Objects;
import java.util.ArrayList;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.occiware.clouddesigner.occi.Link;
import org.occiware.clouddesigner.occi.Resource;
import org.occiware.clouddesigner.occi.docker.Container;
import org.occiware.clouddesigner.occi.docker.Contains;
import org.occiware.clouddesigner.occi.docker.Machine;
import org.occiware.clouddesigner.occi.docker.connector.ExecutableContainer;
import org.occiware.clouddesigner.occi.docker.connector.ModelHandler;
import org.occiware.clouddesigner.occi.infrastructure.ComputeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public class EventCallBack extends EventsResultCallback {
  private static Logger LOGGER = LoggerFactory.getLogger(EventCallBack.class);
  
  private ExecutableContainer container;
  
  public EventCallBack(final ExecutableContainer container) {
    this.container = container;
  }
  
  public void modifyResourceSet(final Resource resource, final String state, final String containerId) {
    try {
      org.eclipse.emf.ecore.resource.Resource _eResource = resource.eResource();
      ResourceSet _resourceSet = _eResource.getResourceSet();
      TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(_resourceSet);
      Command cmd = new RecordingCommand(domain) {
        @Override
        protected void doExecute() {
          boolean _equalsIgnoreCase = state.equalsIgnoreCase("stop");
          if (_equalsIgnoreCase) {
            ((ExecutableContainer) resource).setState(ComputeStatus.INACTIVE);
          }
          boolean _equalsIgnoreCase_1 = state.equalsIgnoreCase("start");
          if (_equalsIgnoreCase_1) {
            ((ExecutableContainer) resource).setState(ComputeStatus.ACTIVE);
          }
          boolean _equalsIgnoreCase_2 = state.equalsIgnoreCase("create");
          if (_equalsIgnoreCase_2) {
            final ModelHandler instanceMH = new ModelHandler();
            EventCallBack.LOGGER.info("<=************************=>");
            Machine machine = EventCallBack.this.getCurrentMachine(((ExecutableContainer) resource));
            Container c = instanceMH.buildContainer(machine, containerId);
            instanceMH.linkContainerToMachine(c, machine);
          }
        }
      };
      try {
        CommandStack _commandStack = domain.getCommandStack();
        ((TransactionalCommandStack) _commandStack).execute(cmd, null);
      } catch (final Throwable _t) {
        if (_t instanceof RollbackException) {
          final RollbackException rbe = (RollbackException)_t;
          IStatus _status = rbe.getStatus();
          String _string = _status.toString();
          EventCallBack.LOGGER.error(_string);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public void onNext(final Event event) {
    EventCallBack.LOGGER.info("Received event #{}", event);
    Machine machine = this.container.getCurrentMachine();
    ComputeStatus _state = machine.getState();
    boolean _equals = Objects.equal(_state, ComputeStatus.ACTIVE);
    if (_equals) {
      EList<Link> _links = machine.getLinks();
      for (final Link l : _links) {
        {
          Contains contains = ((Contains) l);
          Resource _target = contains.getTarget();
          if ((_target instanceof Container)) {
            Resource _target_1 = l.getTarget();
            String _containerid = ((ExecutableContainer) _target_1).getContainerid();
            String _id = event.getId();
            boolean _equals_1 = Objects.equal(_containerid, _id);
            if (_equals_1) {
              String _status = event.getStatus();
              boolean _equalsIgnoreCase = _status.equalsIgnoreCase("stop");
              if (_equalsIgnoreCase) {
                Resource _target_2 = l.getTarget();
                String _status_1 = event.getStatus();
                String _id_1 = event.getId();
                this.modifyResourceSet(_target_2, _status_1, _id_1);
                EventCallBack.LOGGER.info("Apply stop notification to model");
              }
              String _status_2 = event.getStatus();
              boolean _equalsIgnoreCase_1 = _status_2.equalsIgnoreCase("start");
              if (_equalsIgnoreCase_1) {
                Resource _target_3 = l.getTarget();
                String _status_3 = event.getStatus();
                String _id_2 = event.getId();
                this.modifyResourceSet(_target_3, _status_3, _id_2);
                EventCallBack.LOGGER.info("Apply start notification to model");
              }
            } else {
              Resource _target_4 = l.getTarget();
              String _status_4 = event.getStatus();
              String _id_3 = event.getId();
              this.modifyResourceSet(_target_4, _status_4, _id_3);
              EventCallBack.LOGGER.info("Apply create notification to model");
            }
          }
        }
      }
    }
  }
  
  public Machine getCurrentMachine(final ExecutableContainer container) {
    EObject _eContainer = container.eContainer();
    EList<EObject> _eContents = _eContainer.eContents();
    for (final EObject eo : _eContents) {
      if ((eo instanceof Machine)) {
        final Machine machine = ((Machine) eo);
        EList<Link> _links = machine.getLinks();
        for (final Link l : _links) {
          {
            final Contains contains = ((Contains) l);
            Resource _target = contains.getTarget();
            if ((_target instanceof Container)) {
              Resource _target_1 = l.getTarget();
              String _id = ((ExecutableContainer) _target_1).getId();
              String _id_1 = container.getId();
              boolean _equals = Objects.equal(_id, _id_1);
              if (_equals) {
                return machine;
              }
            }
          }
        }
      }
    }
    return null;
  }
  
  public ArrayList<ExecutableContainer> listContainers(final Machine machine) {
    ArrayList<ExecutableContainer> containers = new ArrayList<ExecutableContainer>();
    EList<Link> _links = machine.getLinks();
    for (final Link l : _links) {
      {
        Contains contains = ((Contains) l);
        Resource _target = contains.getTarget();
        if ((_target instanceof Container)) {
          Resource _target_1 = contains.getTarget();
          containers.add(((ExecutableContainer) _target_1));
        }
      }
    }
    return containers;
  }
}