package ru.zaxar163.mh.core;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.invoke.MHMagicMHAcc;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

import ru.zaxar163.mh.Starter;

public class MHAccess {
	public static final class Constants {
		public static final byte REF_getField, REF_getStatic, REF_putField, REF_putStatic, REF_invokeVirtual,
				REF_invokeStatic, REF_invokeSpecial, REF_newInvokeSpecial, REF_invokeInterface;
		static {

			REF_getField = MHMagicMHAcc.REF_getField;
			REF_getStatic = MHMagicMHAcc.REF_getStatic;
			REF_putField = MHMagicMHAcc.REF_putField;
			REF_putStatic = MHMagicMHAcc.REF_putStatic;
			REF_invokeVirtual = MHMagicMHAcc.REF_invokeVirtual;
			REF_invokeStatic = MHMagicMHAcc.REF_invokeStatic;
			REF_invokeSpecial = MHMagicMHAcc.REF_invokeSpecial;
			REF_newInvokeSpecial = MHMagicMHAcc.REF_newInvokeSpecial;
			REF_invokeInterface = MHMagicMHAcc.REF_invokeInterface;
		}

		private static void init() {
		}

		private Constants() {
		}
	}

	private static class Loader {
		private static <T extends AccessibleObject> T a(final T f) {
			try {
				if (!f.isAccessible())
					f.setAccessible(true);
			} catch (final Throwable ignore) {
			}
			return f;
		}

		private static byte[] getBytes(final String string) throws Throwable {
			try (InputStream in = Starter.class.getClassLoader().getResourceAsStream(string);
					ByteArrayOutputStream output = new ByteArrayOutputStream()) {
				final byte[] buffer = new byte[4096];
				for (int length = in.read(buffer); length >= 0; length = in.read(buffer))
					output.write(buffer, 0, length);
				return output.toByteArray();
			}
		}

		private static void init() {
			try {
				final Class<?> u = Class.forName("sun.misc.Unsafe");
				final byte[] arr = getBytes("java/lang/invoke/MHMagicMHAcc.class");
				a(u.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class,
						ClassLoader.class, ProtectionDomain.class)).invoke(a(u.getDeclaredField("theUnsafe")).get(null),
								"java.lang.invoke.MHMagicMHAcc", arr, 0, arr.length, Class.class.getClassLoader(),
								null);
			} catch (final Throwable e) {
				throw new Error(e);
			}
		}
	}

	static {
		Loader.init();
		Constants.init();
	}

	public static MethodHandle asTypeMH(final MethodHandle mh, final MethodType newType) {
		return MHMagicMHAcc.asTypeMH(mh, newType);
	}

	public static MethodHandle getDirectConstructorCommon(final Class<?> refc, final Object ctor)
			throws IllegalAccessException {
		return MHMagicMHAcc.getDirectConstructorCommon(refc, ctor);
	}

	public static MethodHandle getDirectFieldCommon(final byte refKind, final Class<?> refc, final Object field)
			throws IllegalAccessException {
		return MHMagicMHAcc.getDirectFieldCommon(refKind, refc, field);
	}

	public static MethodHandle getDirectMethodCommon(final byte refKind, final Class<?> refc, final Object method)
			throws IllegalAccessException {
		return MHMagicMHAcc.getDirectMethodCommon(refKind, refc, method);
	}

	public static Lookup getImplLookup() {
		return MHMagicMHAcc.getImplLookup();
	}

	public static Object getImplNames() {
		return MHMagicMHAcc.getImplNames();
	}

	public static void init() {

	}

	public static Object invokeWithArgumentsMH(final MethodHandle mh, final Object... arguments) throws Throwable {
		return MHMagicMHAcc.invokeWithArgumentsMH(mh, arguments);
	}

	public static MethodType makeMT(final Class<?> rtype, final Class<?>[] ptypes, final boolean trusted) {
		return MHMagicMHAcc.makeMT(rtype, ptypes, trusted);
	}

	public static Object memberName() {
		return MHMagicMHAcc.memberName();
	}

	public static Object memberName(final byte refKind, final Class<?> defClass, final String name, final Object type) {
		return MHMagicMHAcc.memberName(refKind, defClass, name, type);
	}

	public static Object memberName(final Class<?> c) {
		return MHMagicMHAcc.memberName(c);
	}

	public static Object memberName(final Class<?> defClass, final String name, final Class<?> type,
			final byte refKind) {
		return MHMagicMHAcc.memberName(defClass, name, type, refKind);
	}

	public static Object memberName(final Class<?> defClass, final String name, final MethodType type,
			final byte refKind) {
		return MHMagicMHAcc.memberName(defClass, name, type, refKind);
	}

	public static Object memberName(final Constructor<?> ctor) {
		return MHMagicMHAcc.memberName(ctor);
	}

	public static Object memberName(final Field f, final boolean wantSetter) {
		return MHMagicMHAcc.memberName(f, wantSetter);
	}

	public static Object memberName(final Method m, final boolean wantSpecial) {
		return MHMagicMHAcc.memberName(m, wantSpecial);
	}

	public static Object resolveOrFail(final byte refKind, final Class<?> refc, final String name, final Class<?> type)
			throws NoSuchFieldException, IllegalAccessException {
		return MHMagicMHAcc.resolveOrFail(refKind, refc, name, type);
	}

	public static Object resolveOrFail(final byte refKind, final Class<?> refc, final String name,
			final MethodType type) throws NoSuchMethodException, IllegalAccessException {
		return MHMagicMHAcc.resolveOrFail(refKind, refc, name, type);
	}

	public static Object resolveOrFail(final byte refKind, final Object member) throws ReflectiveOperationException {
		return MHMagicMHAcc.resolveOrFail(refKind, member);
	}
}
