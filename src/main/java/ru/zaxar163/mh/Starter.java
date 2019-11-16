package ru.zaxar163.mh;

import java.io.PrintStream;

import ru.zaxar163.mh.core.UncheckedLookup;

public class Starter {
	public static void main(final String[] args) throws Throwable {
		UncheckedLookup.invokeWithArguments(UncheckedLookup.findVirtual(PrintStream.class, "println",
				UncheckedLookup.makeMT(void.class, String.class)), System.out, "Yeah, reflection");
	}
}
