package ru.zaxar163.mh.core;

import static ru.zaxar163.mh.core.MHAccess.Constants.REF_getField;
import static ru.zaxar163.mh.core.MHAccess.Constants.REF_getStatic;
import static ru.zaxar163.mh.core.MHAccess.Constants.REF_invokeInterface;
import static ru.zaxar163.mh.core.MHAccess.Constants.REF_invokeSpecial;
import static ru.zaxar163.mh.core.MHAccess.Constants.REF_invokeStatic;
import static ru.zaxar163.mh.core.MHAccess.Constants.REF_invokeVirtual;
import static ru.zaxar163.mh.core.MHAccess.Constants.REF_newInvokeSpecial;
import static ru.zaxar163.mh.core.MHAccess.Constants.REF_putField;
import static ru.zaxar163.mh.core.MHAccess.Constants.REF_putStatic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class UncheckedLookup {
	static {
		MHAccess.init();
	}

	public static MethodHandle asType(final MethodHandle mh, final MethodType newType) {
		return MHAccess.asTypeMH(mh, newType);
	}

	public static MethodHandle findConstructor(final Class<?> refc, final MethodType type)
			throws NoSuchMethodException, IllegalAccessException {
		if (refc.isArray())
			throw new NoSuchMethodException("No constructor for array class: " + refc.getName());
		final String name = "<init>";
		final Object ctor = MHAccess.resolveOrFail(REF_newInvokeSpecial, refc, name, type);
		return MHAccess.getDirectConstructorCommon(refc, ctor);
	}

	public static MethodHandle findGetter(final Class<?> refc, final String name, final Class<?> type)
			throws NoSuchFieldException, IllegalAccessException {
		final Object field = MHAccess.resolveOrFail(REF_getField, refc, name, type);
		return MHAccess.getDirectFieldCommon(REF_getField, refc, field);
	}

	public static MethodHandle findSetter(final Class<?> refc, final String name, final Class<?> type)
			throws NoSuchFieldException, IllegalAccessException {
		final Object field = MHAccess.resolveOrFail(REF_putField, refc, name, type);
		return MHAccess.getDirectFieldCommon(REF_putField, refc, field);
	}

	public static MethodHandle findSpecial(final Class<?> refc, final String name, final MethodType type)
			throws NoSuchMethodException, IllegalAccessException {
		final Object method = MHAccess.resolveOrFail(REF_invokeSpecial, refc, name, type);
		return MHAccess.getDirectMethodCommon(REF_invokeSpecial, refc, method);
	}

	public static MethodHandle findStatic(final Class<?> refc, final String name, final MethodType type)
			throws NoSuchMethodException, IllegalAccessException {
		final byte refKind = REF_invokeStatic;
		final Object method = MHAccess.resolveOrFail(refKind, refc, name, type);
		return MHAccess.getDirectMethodCommon(refKind, refc, method);
	}

	public static MethodHandle findStaticGetter(final Class<?> refc, final String name, final Class<?> type)
			throws NoSuchFieldException, IllegalAccessException {
		final Object field = MHAccess.resolveOrFail(REF_getStatic, refc, name, type);
		return MHAccess.getDirectFieldCommon(REF_getStatic, refc, field);
	}

	public static MethodHandle findStaticSetter(final Class<?> refc, final String name, final Class<?> type)
			throws NoSuchFieldException, IllegalAccessException {
		final Object field = MHAccess.resolveOrFail(REF_putStatic, refc, name, type);
		return MHAccess.getDirectFieldCommon(REF_putStatic, refc, field);
	}

	public static MethodHandle findVirtual(final Class<?> refc, final String name, final MethodType type)
			throws NoSuchMethodException, IllegalAccessException {
		final byte refKind = refc.isInterface() ? REF_invokeInterface : REF_invokeVirtual;
		final Object method = MHAccess.resolveOrFail(refKind, refc, name, type);
		return MHAccess.getDirectMethodCommon(refKind, refc, method);
	}

	public static Object invokeWithArguments(final MethodHandle mh, final Object... arguments) throws Throwable {
		return MHAccess.invokeWithArgumentsMH(mh, arguments);
	}

	public static MethodType makeMT(final Class<?> rtype, final Class<?>... ptypes) {
		return MHAccess.makeMT(rtype, ptypes, true);
	}

	private UncheckedLookup() {
	}
}
