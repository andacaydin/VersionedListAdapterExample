package net.andac.aydin.versionedlistadapterexample.versionedadapter;

public abstract class AbstractVersionedObject {

	private long versionNumber;
	private long uniqueId;

	public AbstractVersionedObject(Long uniqueId) {
		this.uniqueId = uniqueId;

	}

	public long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(long uniqueId) {
		this.uniqueId = uniqueId;
	}

}
