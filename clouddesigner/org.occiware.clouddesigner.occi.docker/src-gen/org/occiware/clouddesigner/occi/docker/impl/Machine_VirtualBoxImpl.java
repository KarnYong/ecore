/**
 */
package org.occiware.clouddesigner.occi.docker.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.ocl.examples.codegen.java.UnboxedExplicitNavigationProperty;

import org.eclipse.ocl.examples.domain.elements.DomainType;

import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;

import org.eclipse.ocl.examples.domain.ids.TypeId;

import org.eclipse.ocl.examples.domain.messages.EvaluatorMessages;

import org.eclipse.ocl.examples.domain.types.IdResolver;

import org.eclipse.ocl.examples.domain.utilities.DomainUtil;

import org.eclipse.ocl.examples.domain.values.OrderedSetValue;
import org.eclipse.ocl.examples.domain.values.RealValue;
import org.eclipse.ocl.examples.domain.values.SequenceValue;
import org.eclipse.ocl.examples.domain.values.TupleValue;

import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;

import org.eclipse.ocl.examples.domain.values.util.ValuesUtil;

import org.eclipse.ocl.examples.library.collection.CollectionSumOperation;

import org.eclipse.ocl.examples.library.numeric.NumericLessThanEqualOperation;

import org.eclipse.ocl.examples.library.oclany.OclAnyOclAsTypeOperation;
import org.eclipse.ocl.examples.library.oclany.OclAnyOclIsKindOfOperation;
import org.eclipse.ocl.examples.library.oclany.OclAnyToStringOperation;

import org.eclipse.ocl.examples.library.string.StringConcatOperation;

import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;

import org.occiware.clouddesigner.occi.Link;
import org.occiware.clouddesigner.occi.Resource;

import org.occiware.clouddesigner.occi.docker.DockerPackage;
import org.occiware.clouddesigner.occi.docker.DockerTables;
import org.occiware.clouddesigner.occi.docker.Machine_VirtualBox;

import org.occiware.clouddesigner.occi.docker.util.DockerValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Machine Virtual Box</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.occiware.clouddesigner.occi.docker.impl.Machine_VirtualBoxImpl#getBoot2docker_url <em>Boot2docker url</em>}</li>
 *   <li>{@link org.occiware.clouddesigner.occi.docker.impl.Machine_VirtualBoxImpl#getDisk_size <em>Disk size</em>}</li>
 * </ul>
 *
 * @generated
 */
public class Machine_VirtualBoxImpl extends MachineImpl implements Machine_VirtualBox {
	/**
	 * The default value of the '{@link #getBoot2docker_url() <em>Boot2docker url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBoot2docker_url()
	 * @generated
	 * @ordered
	 */
	protected static final String BOOT2DOCKER_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBoot2docker_url() <em>Boot2docker url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBoot2docker_url()
	 * @generated
	 * @ordered
	 */
	protected String boot2docker_url = BOOT2DOCKER_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisk_size() <em>Disk size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisk_size()
	 * @generated
	 * @ordered
	 */
	protected static final int DISK_SIZE_EDEFAULT = 20000;

	/**
	 * The cached value of the '{@link #getDisk_size() <em>Disk size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisk_size()
	 * @generated
	 * @ordered
	 */
	protected int disk_size = DISK_SIZE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Machine_VirtualBoxImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DockerPackage.Literals.MACHINE_VIRTUAL_BOX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBoot2docker_url() {
		return boot2docker_url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBoot2docker_url(String newBoot2docker_url) {
		String oldBoot2docker_url = boot2docker_url;
		boot2docker_url = newBoot2docker_url;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.MACHINE_VIRTUAL_BOX__BOOT2DOCKER_URL, oldBoot2docker_url, boot2docker_url));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDisk_size() {
		return disk_size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisk_size(int newDisk_size) {
		int oldDisk_size = disk_size;
		disk_size = newDisk_size;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.MACHINE_VIRTUAL_BOX__DISK_SIZE, oldDisk_size, disk_size));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean ContainersUsedTooMemory(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		/**
		 * 
		 * inv ContainersUsedTooMemory: Tuple {
		 * 	message : String = 'Containers consume ' + (links->select(oclIsKindOf(docker::Contains)).target.oclAsType(Container).memory->sum()).toString() + ' when memory is equals to ' + memory.toString(),
		 * 	status : Boolean = 
		 * 			(links->select(oclIsKindOf(docker::Contains)).target.oclAsType(Container).memory->sum()) <= memory
		 * }.status
		 */
		final /*@NonNull*/ /*@NonInvalid*/ DomainEvaluator evaluator = PivotUtil.getEvaluator(this);
		final /*@NonNull*/ /*@NonInvalid*/ UnboxedExplicitNavigationProperty IMPPROPid_memory_2 = new UnboxedExplicitNavigationProperty(DockerTables.PROPid_memory);
		final /*@NonNull*/ /*@NonInvalid*/ IdResolver idResolver = evaluator.getIdResolver();
		final /*@NonNull*/ /*@Thrown*/ List<Link> links_0 = this.getLinks();
		final /*@Nullable*/ /*@Thrown*/ Number memory_2 = (Number)IMPPROPid_memory_2.evaluate(evaluator, TypeId.REAL, this);
		final /*@NonNull*/ /*@Thrown*/ OrderedSetValue BOXED_links_0 = idResolver.createOrderedSetOfAll(DockerTables.ORD_CLSSid_Link, links_0);
		final /*@Nullable*/ /*@Thrown*/ RealValue BOXED_memory_2 = memory_2 == null ? null : ValuesUtil.realValueOf(memory_2);
		/*@NonNull*/ /*@Thrown*/ OrderedSetValue.Accumulator accumulator = ValuesUtil.createOrderedSetAccumulatorValue(DockerTables.ORD_CLSSid_Link);
		/*@Nullable*/ Iterator<?> ITERATOR__1_3 = BOXED_links_0.iterator();
		/*@NonNull*/ /*@Thrown*/ OrderedSetValue select_0;
		while (true) {
		    if (!ITERATOR__1_3.hasNext()) {
		        select_0 = accumulator;
		        break;
		    }
		    /*@Nullable*/ /*@NonInvalid*/ Link _1_3 = (Link)ITERATOR__1_3.next();
		    /**
		     * oclIsKindOf(Contains)
		     */
		    final /*@NonNull*/ /*@NonInvalid*/ DomainType TYP_docker_c_c_Contains_1 = idResolver.getType(DockerTables.CLSSid_Contains, null);
		    final /*@Thrown*/ boolean oclIsKindOf_0 = DomainUtil.nonNullState(OclAnyOclIsKindOfOperation.INSTANCE.evaluate(evaluator, _1_3, TYP_docker_c_c_Contains_1).booleanValue());
		    //
		    if (oclIsKindOf_0 == ValuesUtil.TRUE_VALUE) {
		        accumulator.add(_1_3);
		    }
		}
		/*@NonNull*/ /*@Thrown*/ SequenceValue.Accumulator accumulator_0 = ValuesUtil.createSequenceAccumulatorValue(DockerTables.SEQ_CLSSid_Resource);
		/*@Nullable*/ Iterator<?> ITERATOR__1_4 = select_0.iterator();
		/*@NonNull*/ /*@Thrown*/ SequenceValue collect_4;
		while (true) {
		    if (!ITERATOR__1_4.hasNext()) {
		        collect_4 = accumulator_0;
		        break;
		    }
		    /*@Nullable*/ /*@NonInvalid*/ Link _1_4 = (Link)ITERATOR__1_4.next();
		    /**
		     * target
		     */
		    if (_1_4 == null) {
		        throw new InvalidValueException("Null source for \'occi::Link::target\'");
		    }
		    final /*@NonNull*/ /*@Thrown*/ Resource target_0 = _1_4.getTarget();
		    //
		    accumulator_0.add(target_0);
		}
		/*@NonNull*/ /*@Thrown*/ SequenceValue.Accumulator accumulator_1 = ValuesUtil.createSequenceAccumulatorValue(DockerTables.SEQ_CLSSid_Container);
		/*@Nullable*/ Iterator<?> ITERATOR__1_5 = collect_4.iterator();
		/*@NonNull*/ /*@Thrown*/ SequenceValue collect_3;
		while (true) {
		    if (!ITERATOR__1_5.hasNext()) {
		        collect_3 = accumulator_1;
		        break;
		    }
		    /*@Nullable*/ /*@NonInvalid*/ Resource _1_5 = (Resource)ITERATOR__1_5.next();
		    /**
		     * oclAsType(Container)
		     */
		    final /*@NonNull*/ /*@NonInvalid*/ DomainType TYP_docker_c_c_Container_1 = idResolver.getType(DockerTables.CLSSid_Container, null);
		    final /*@Nullable*/ /*@Thrown*/ org.occiware.clouddesigner.occi.docker.Container oclAsType_0 = (org.occiware.clouddesigner.occi.docker.Container)OclAnyOclAsTypeOperation.INSTANCE.evaluate(evaluator, _1_5, TYP_docker_c_c_Container_1);
		    //
		    accumulator_1.add(oclAsType_0);
		}
		/*@NonNull*/ /*@Thrown*/ SequenceValue.Accumulator accumulator_2 = ValuesUtil.createSequenceAccumulatorValue(DockerTables.SEQ_PRIMid_Real);
		/*@Nullable*/ Iterator<?> ITERATOR__1_6 = collect_3.iterator();
		/*@NonNull*/ /*@Thrown*/ SequenceValue collect_2;
		while (true) {
		    if (!ITERATOR__1_6.hasNext()) {
		        collect_2 = accumulator_2;
		        break;
		    }
		    /*@Nullable*/ /*@NonInvalid*/ org.occiware.clouddesigner.occi.docker.Container _1_6 = (org.occiware.clouddesigner.occi.docker.Container)ITERATOR__1_6.next();
		    /**
		     * memory
		     */
		    if (_1_6 == null) {
		        throw new InvalidValueException("Null source for \'infrastructure::Compute::memory\'");
		    }
		    final /*@Nullable*/ /*@Thrown*/ Number memory_1 = (Number)IMPPROPid_memory_2.evaluate(evaluator, TypeId.REAL, _1_6);
		    final /*@Nullable*/ /*@Thrown*/ RealValue BOXED_memory_1 = memory_1 == null ? null : ValuesUtil.realValueOf(memory_1);
		    //
		    accumulator_2.add(BOXED_memory_1);
		}
		final /*@NonNull*/ /*@Thrown*/ RealValue sum_3 = DomainUtil.nonNullState((RealValue)CollectionSumOperation.INSTANCE.evaluate(evaluator, TypeId.REAL, collect_2));
		final /*@NonNull*/ /*@Thrown*/ String toString = DomainUtil.nonNullState(OclAnyToStringOperation.INSTANCE.evaluate(sum_3));
		final /*@NonNull*/ /*@Thrown*/ String sum_0 = DomainUtil.nonNullState(StringConcatOperation.INSTANCE.evaluate(DockerTables.STR_Containers_32_consume_32, toString));
		final /*@NonNull*/ /*@Thrown*/ String sum_1 = DomainUtil.nonNullState(StringConcatOperation.INSTANCE.evaluate(sum_0, DockerTables.STR__32_when_32_memory_32_is_32_equa));
		final /*@NonNull*/ /*@Thrown*/ String toString_0 = DomainUtil.nonNullState(OclAnyToStringOperation.INSTANCE.evaluate(BOXED_memory_2));
		final /*@NonNull*/ /*@Thrown*/ String sum_2 = DomainUtil.nonNullState(StringConcatOperation.INSTANCE.evaluate(sum_1, toString_0));
		final /*@Thrown*/ boolean le = DomainUtil.nonNullState(NumericLessThanEqualOperation.INSTANCE.evaluate(sum_3, BOXED_memory_2).booleanValue());
		final /*@NonNull*/ /*@Thrown*/ TupleValue symbol_0 = ValuesUtil.createTupleOfEach(DockerTables.TUPLid_, sum_2, le);
		final /*@NonInvalid*/ boolean status = (Boolean)symbol_0.getValue(1/*status*/);
		if (status == ValuesUtil.TRUE_VALUE) {
		    return true;
		}
		if (diagnostics != null) {
		    int severity = Diagnostic.WARNING;
		    String message = DomainUtil.bind(EvaluatorMessages.ValidationConstraintIsNotSatisfied_ERROR_, new Object[]{"Machine_VirtualBox", "ContainersUsedTooMemory", EObjectValidator.getObjectLabel(this, context)});
		    diagnostics.add(new BasicDiagnostic(severity, DockerValidator.DIAGNOSTIC_SOURCE, DockerValidator.MACHINE_VIRTUAL_BOX__CONTAINERS_USED_TOO_MEMORY, message, new Object [] { this }));
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DockerPackage.MACHINE_VIRTUAL_BOX__BOOT2DOCKER_URL:
				return getBoot2docker_url();
			case DockerPackage.MACHINE_VIRTUAL_BOX__DISK_SIZE:
				return getDisk_size();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DockerPackage.MACHINE_VIRTUAL_BOX__BOOT2DOCKER_URL:
				setBoot2docker_url((String)newValue);
				return;
			case DockerPackage.MACHINE_VIRTUAL_BOX__DISK_SIZE:
				setDisk_size((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DockerPackage.MACHINE_VIRTUAL_BOX__BOOT2DOCKER_URL:
				setBoot2docker_url(BOOT2DOCKER_URL_EDEFAULT);
				return;
			case DockerPackage.MACHINE_VIRTUAL_BOX__DISK_SIZE:
				setDisk_size(DISK_SIZE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DockerPackage.MACHINE_VIRTUAL_BOX__BOOT2DOCKER_URL:
				return BOOT2DOCKER_URL_EDEFAULT == null ? boot2docker_url != null : !BOOT2DOCKER_URL_EDEFAULT.equals(boot2docker_url);
			case DockerPackage.MACHINE_VIRTUAL_BOX__DISK_SIZE:
				return disk_size != DISK_SIZE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case DockerPackage.MACHINE_VIRTUAL_BOX___CONTAINERS_USED_TOO_MEMORY__DIAGNOSTICCHAIN_MAP:
				return ContainersUsedTooMemory((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (boot2docker_url: ");
		result.append(boot2docker_url);
		result.append(", disk_size: ");
		result.append(disk_size);
		result.append(')');
		return result.toString();
	}

} //Machine_VirtualBoxImpl