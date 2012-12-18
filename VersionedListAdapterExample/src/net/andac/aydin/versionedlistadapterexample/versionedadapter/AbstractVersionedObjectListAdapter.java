package net.andac.aydin.versionedlistadapterexample.versionedadapter;

import net.andac.aydin.versionedlistadapterexample.VersionedObjectListSingleton;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbstractVersionedObjectListAdapter extends BaseAdapter {
	private static final String TAG = "BaseVersionedObjectListAdapter";
	private final LayoutInflater inflater;
	private final int viewId;
	private Integer middle;
	private static final int HALF_MAX_VALUE = Integer.MAX_VALUE / 2;

	/**
	 * This is the holder that will provide fast access to arbitrary objects and
	 * views. Use a subclass to adapt it for your needs.
	 */
	public static class ViewHolder {
		// back reference to our list object
		public AbstractVersionedObject data;
	}

	/**
	 * The constructor.
	 * 
	 * @param context
	 *            is the current context
	 * @param viewid
	 *            is the resource id of your list view item
	 * @param dataObjects
	 *            is the list data objects, or null, if you require to indicate
	 *            an empty list
	 */
	public AbstractVersionedObjectListAdapter(Context context, int viewid) {
		this.inflater = LayoutInflater.from(context);
		this.viewId = viewid;
		this.middle = HALF_MAX_VALUE - HALF_MAX_VALUE
				% VersionedObjectListSingleton.getInstance().size();
	}

	/*
	 * for circular list! List starts from beginning when reaching the end
	 */
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	/**
	 * Get the data object.
	 * 
	 * @param position
	 *            (index) to retrieve
	 * 
	 * @return Return the object at indicated position. Note,
	 *         &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; the holder
	 *         object uses a back reference to its related data
	 *         &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; object. So, the
	 *         user usually should use {@link ViewHolder#data}
	 *         &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; for faster
	 *         access.
	 */
	@Override
	public AbstractVersionedObject getItem(int position) {
		return VersionedObjectListSingleton.getInstance()
				.getVersionedObjectForIndex(
						Integer.valueOf(position
								% VersionedObjectListSingleton.getInstance()
										.values().size()));
	}

	/**
	 * Position equals id.
	 * 
	 * @return The id of the object
	 */
	@Override
	public long getItemId(int position) {
		return getItem(position).getUniqueId();
	}

	/**
	 * Make a view to hold each row. This method is instantiated for each list
	 * data object. Using the Holder Pattern, avoids the unnecessary
	 * findViewById(...) calls.
	 * 
	 * @param position
	 *            (index) to retrieve
	 * @param convertView
	 *            is the view
	 * @param parent
	 *            is the associated ViewGroup
	 * 
	 * @return The view associated with this row
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid unnecessary
		// calls to findViewById(...) on each row.
		ViewHolder holder;
		// When the view is not null, we can reuse it directly, there is no need
		// to re-inflate it. We only inflate a new View when the view supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = this.inflater.inflate(this.viewId, null);
			// call the user's implementation
			holder = createHolder(convertView);
			holder.data = getItem(position);
			// we set the holder as tag
			convertView.setTag(holder);
			// call the user's implementation
			bindHolder(holder);
		} else {
			// get holder back...much faster than inflate
			holder = (ViewHolder) convertView.getTag();
			if (holder.data.getVersionNumber() < VersionedObjectListSingleton
					.getInstance()
					.getVersionedObject(holder.data.getUniqueId())
					.getVersionNumber()) {
				holder.data = VersionedObjectListSingleton.getInstance()
						.getVersionedObject(holder.data.getUniqueId());
				bindHolder(holder);
			}
		}

		return convertView;
	}

	// XXX we tell adapter we have n types, each a VersionedObject!
	// Since Adapter needs this value to be fix through its lifetime, we use 20
	// more then size .... just in case. Edit this value if you feel like user
	// might add more then 20 items per use.
	@Override
	public int getViewTypeCount() {
		return VersionedObjectListSingleton.getInstance().size() + 20;
	}

	@Override
	public int getItemViewType(int position) {
		return Integer.valueOf(position
				% VersionedObjectListSingleton.getInstance().size());

	}

	/**
	 * Creates your custom holder that carries a reference for your particular
	 * view.
	 * 
	 * @param v
	 *            is the view for the new holder object
	 * 
	 * @return The newly created ViewHolder
	 * 
	 */
	protected abstract ViewHolder createHolder(View v);

	/**
	 * Binds the data from user's object (typically an entity) to the holder.
	 * 
	 * @param h
	 *            is the holder that represents the data object
	 */
	protected abstract void bindHolder(ViewHolder h);

	@Override
	public boolean hasStableIds() {
		return true;
	}

	public Integer getMiddle() {
		return this.middle;
	}
}
