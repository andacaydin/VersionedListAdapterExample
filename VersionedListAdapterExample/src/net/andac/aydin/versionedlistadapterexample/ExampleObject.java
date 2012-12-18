package net.andac.aydin.versionedlistadapterexample;

import net.andac.aydin.versionedlistadapterexample.versionedadapter.AbstractVersionedObject;

public class ExampleObject extends AbstractVersionedObject {
	private String sometext;

	public ExampleObject(Long id, String string) {
		super(id);
		this.sometext = string;
	}

	public String getSometext() {
		return sometext;
	}

	public void setSometext(String sometext) {
		this.sometext = sometext;
	}
}
