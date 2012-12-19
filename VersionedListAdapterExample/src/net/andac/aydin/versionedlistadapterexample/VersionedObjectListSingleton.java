package net.andac.aydin.versionedlistadapterexample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import net.andac.aydin.versionedlistadapterexample.versionedadapter.AbstractVersionedObject;

/**
 * @author Andac Aydin
 * 
 */
public class VersionedObjectListSingleton {

	private static final String TAG = "VersionedObjectListSingleton";
	private static VersionedObjectListSingleton instance;
	private LinkedHashMap<Long, AbstractVersionedObject> VersionedObjectList;
	private ArrayList<Long> indexpositions;

	public static synchronized VersionedObjectListSingleton getInstance() {
		if (instance == null) {
			instance = new VersionedObjectListSingleton();
		}
		return instance;
	}

	public void putVersionedObject(AbstractVersionedObject VersionedObject) {
		getVersionedObjectList().put(VersionedObject.getUniqueId(),
				VersionedObject);
		if (getIndexpositions().contains(VersionedObject.getUniqueId())) {
			getIndexpositions().remove(VersionedObject.getUniqueId());
		}
		getIndexpositions().add(VersionedObject.getUniqueId());
	}

	public AbstractVersionedObject getVersionedObject(long uniqueId) {
		return getVersionedObjectList().get(uniqueId);
	}

	public Collection<AbstractVersionedObject> values() {
		return getVersionedObjectList().values();
	}

	public int getIndexOfVersionedObject(long uniqueId) {
		return getIndexpositions().indexOf(uniqueId);
	}

	public int size() {
		return getVersionedObjectList().size();
	}

	public AbstractVersionedObject getVersionedObjectForIndex(int index) {
		return getVersionedObjectList().get(getIndexpositions().get(index));
	}

	private LinkedHashMap<Long, AbstractVersionedObject> getVersionedObjectList() {
		if (this.VersionedObjectList == null) {
			VersionedObjectList = new LinkedHashMap<Long, AbstractVersionedObject>();
		}
		return VersionedObjectList;
	}

	private ArrayList<Long> getIndexpositions() {
		if (this.indexpositions == null) {
			indexpositions = new ArrayList<Long>();
		}
		return indexpositions;
	}

	/**
	 * Updates given VersionedObject and increases Version Number.<br>
	 * Used for UI-updates e.g. ListViewAdapters and have to be called before
	 * notifyDataSetChanged() <br>
	 * 
	 * @param VersionedObjectId
	 * @return
	 */
	public boolean increaseVersion(AbstractVersionedObject VersionedObject) {
		long versionNumber = VersionedObject.getVersionNumber();
		VersionedObject.setVersionNumber(++versionNumber);
		putVersionedObject(VersionedObject);
		return true;
	}

	/**
	 * Just increases Version of given VersionedObject Number.<br>
	 * Used for UI-updates e.g. ListViewAdapters and have to be called before
	 * notifyDataSetChanged() <br>
	 * 
	 * @param VersionedObjectId
	 * @return
	 */
	public boolean increaseVersion(Long uniqueId) {
		AbstractVersionedObject VersionedObject = getVersionedObject(uniqueId);
		long versionNumber = VersionedObject.getVersionNumber();
		VersionedObject.setVersionNumber(++versionNumber);
		putVersionedObject(VersionedObject);
		return true;
	}
}
