/**
 */
package hypervisor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>OS</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see hypervisor.HypervisorPackage#getOS()
 * @model
 * @generated
 */
public enum OS implements Enumerator {
	/**
	 * The '<em><b>Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	TYPE(0, "type", "type"),

	/**
	 * The '<em><b>Boot</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOOT_VALUE
	 * @generated
	 * @ordered
	 */
	BOOT(0, "boot", "boot"),

	/**
	 * The '<em><b>Kernel</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #KERNEL_VALUE
	 * @generated
	 * @ordered
	 */
	KERNEL(0, "kernel", "kernel"),

	/**
	 * The '<em><b>Ramdisk</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RAMDISK_VALUE
	 * @generated
	 * @ordered
	 */
	RAMDISK(0, "ramdisk", "ramdisk"),

	/**
	 * The '<em><b>Cmdline</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CMDLINE_VALUE
	 * @generated
	 * @ordered
	 */
	CMDLINE(0, "cmdline", "cmdline");

	/**
	 * The '<em><b>Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TYPE
	 * @model name="type"
	 * @generated
	 * @ordered
	 */
	public static final int TYPE_VALUE = 0;

	/**
	 * The '<em><b>Boot</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Boot</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BOOT
	 * @model name="boot"
	 * @generated
	 * @ordered
	 */
	public static final int BOOT_VALUE = 0;

	/**
	 * The '<em><b>Kernel</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Kernel</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #KERNEL
	 * @model name="kernel"
	 * @generated
	 * @ordered
	 */
	public static final int KERNEL_VALUE = 0;

	/**
	 * The '<em><b>Ramdisk</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Ramdisk</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RAMDISK
	 * @model name="ramdisk"
	 * @generated
	 * @ordered
	 */
	public static final int RAMDISK_VALUE = 0;

	/**
	 * The '<em><b>Cmdline</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cmdline</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CMDLINE
	 * @model name="cmdline"
	 * @generated
	 * @ordered
	 */
	public static final int CMDLINE_VALUE = 0;

	/**
	 * An array of all the '<em><b>OS</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final OS[] VALUES_ARRAY =
		new OS[] {
			TYPE,
			BOOT,
			KERNEL,
			RAMDISK,
			CMDLINE,
		};

	/**
	 * A public read-only list of all the '<em><b>OS</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<OS> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>OS</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OS get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OS result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>OS</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OS getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OS result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>OS</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OS get(int value) {
		switch (value) {
			case TYPE_VALUE: return TYPE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private OS(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //OS