package java.lang.invoke;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MHMagicMHAcc {
	public static final byte REF_getField = MethodHandleNatives.Constants.REF_getField,
			REF_getStatic = MethodHandleNatives.Constants.REF_getStatic,
			REF_putField = MethodHandleNatives.Constants.REF_putField,
			REF_putStatic = MethodHandleNatives.Constants.REF_putStatic,
			REF_invokeVirtual = MethodHandleNatives.Constants.REF_invokeVirtual,
			REF_invokeStatic = MethodHandleNatives.Constants.REF_invokeStatic,
			REF_invokeSpecial = MethodHandleNatives.Constants.REF_invokeSpecial,
			REF_newInvokeSpecial = MethodHandleNatives.Constants.REF_newInvokeSpecial,
			REF_invokeInterface = MethodHandleNatives.Constants.REF_invokeInterface;
	private static final MemberName.Factory IMPL_NAMES = MemberName.getFactory();
	private static final Lookup IMPL_LOOKUP = Lookup.IMPL_LOOKUP;

	@ForceInline
	public static MethodHandle asTypeMH(final MethodHandle mh, final MethodType newType) {
		if (newType == mh.type())
			return mh;
		final MethodHandle atc = mh.asTypeCache;
		if (atc != null && newType == atc.type())
			return atc;
		return mh.asTypeCache = MethodHandleImpl.makePairwiseConvert(mh, newType, true);
	}

	@ForceInline
	private static void checkMember(final Object refc) {
		if (!MemberName.class.isAssignableFrom(refc.getClass()))
			throw new IllegalArgumentException("Not memberName!");
	}

	@ForceInline
	public static MethodHandle getDirectConstructorCommon(final Class<?> refc, final Object ctor)
			throws IllegalAccessException {
		checkMember(ctor);
		return DirectMethodHandle.make((MemberName) ctor).setVarargs((MemberName) ctor);
	}

	@ForceInline
	public static MethodHandle getDirectFieldCommon(final byte refKind, final Class<?> refc, final Object field)
			throws IllegalAccessException {
		checkMember(field);
		return DirectMethodHandle.make(refc, (MemberName) field);
	}

	@ForceInline
	public static MethodHandle getDirectMethodCommon(final byte refKind, final Class<?> refc, final Object method)
			throws IllegalAccessException {
		checkMember(method);
		return DirectMethodHandle.make(refKind, refc, (MemberName) method).setVarargs((MemberName) method);
	}

	@ForceInline
	public static Lookup getImplLookup() {
		return IMPL_LOOKUP;
	}

	@ForceInline
	public static Object getImplNames() {
		return IMPL_NAMES;
	}

	@ForceInline
	public static Object invokeWithArgumentsMH(final MethodHandle mh, final Object... arguments) throws Throwable {
		final MethodType invocationType = MethodType.genericMethodType(arguments == null ? 0 : arguments.length);
		return invocationType.invokers().spreadInvoker(0).invokeExact(asTypeMH(mh, invocationType), arguments);
	}

	@ForceInline
	public static MethodType makeMT(final Class<?> rtype, final Class<?>[] ptypes, final boolean trusted) {
		return MethodType.makeImpl(rtype, ptypes, trusted);
	}

	@ForceInline
	public static Object memberName() {
		return new MemberName();
	}

	@ForceInline
	public static Object memberName(final byte refKind, final Class<?> defClass, final String name, final Object type) {
		return new MemberName(refKind, defClass, name, type);
	}

	@ForceInline
	public static Object memberName(final Class<?> c) {
		return new MemberName(c);
	}

	@ForceInline
	public static Object memberName(final Class<?> defClass, final String name, final Class<?> type,
			final byte refKind) {
		return new MemberName(defClass, name, type, refKind);
	}

	@ForceInline
	public static Object memberName(final Class<?> defClass, final String name, final MethodType type,
			final byte refKind) {
		return new MemberName(defClass, name, type, refKind);
	}

	@ForceInline
	public static Object memberName(final Constructor<?> ctor) {
		return new MemberName(ctor);
	}

	@ForceInline
	public static Object memberName(final Field f, final boolean wantSetter) {
		return new MemberName(f, wantSetter);
	}

	@ForceInline
	public static Object memberName(final Method m, final boolean wantSpecial) {
		return new MemberName(m, wantSpecial);
	}

	@ForceInline
	public static Object resolveOrFail(final byte refKind, final Class<?> refc, final String name, final Class<?> type)
			throws NoSuchFieldException, IllegalAccessException {
		return IMPL_NAMES.resolveOrFail(refKind, new MemberName(refc, name, type, refKind), null,
				NoSuchFieldException.class);
	}

	@ForceInline
	public static Object resolveOrFail(final byte refKind, final Class<?> refc, final String name,
			final MethodType type) throws NoSuchMethodException, IllegalAccessException {
		return IMPL_NAMES.resolveOrFail(refKind, new MemberName(refc, name, type, refKind), null,
				NoSuchMethodException.class);
	}

	@ForceInline
	public static Object resolveOrFail(final byte refKind, final Object member) throws ReflectiveOperationException {
		checkMember(member);
		return IMPL_NAMES.resolveOrFail(refKind, (MemberName) member, null, ReflectiveOperationException.class);
	}

	private MHMagicMHAcc() {
	}
}
