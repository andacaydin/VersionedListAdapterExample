package net.andac.aydin.versionedlistadapterexample;

import net.andac.aydin.versionedlistadapterexample.versionedadapter.AbstractVersionedObjectListAdapter;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * @author Andac Aydin
 * 
 */
public class ExampleListAdapter extends AbstractVersionedObjectListAdapter {
	private static final String TAG = "VersionedObjectsListAdapter";

	public ExampleListAdapter(Context context, int viewid) {
		super(context, viewid);
	}

	/**
	 * This is the holder that will provide fast access to arbitrary objects and
	 * views. Use a subclass to adapt it for your needs.
	 */
	public static class VersionedObjectViewHolder extends ViewHolder {
		public TextView textView;

		public VersionedObjectViewHolder(TextView textView) {
			this.textView = textView;
		}
	}

	@Override
	protected void bindHolder(ViewHolder viewHolder) {
		// Binding the holder keeps our data up to date.
		// In contrast to createHolder this method is called for all items
		// So, be aware when doing a lot of heavy stuff here.
		// we simply transfer our object's data to the list item representatives
		VersionedObjectViewHolder versionedObjectViewHolder = (VersionedObjectViewHolder) viewHolder;
		ExampleObject exampleObject = (ExampleObject) viewHolder.data;
		versionedObjectViewHolder.textView.setText(exampleObject.getSometext());

	}

	@Override
	protected ViewHolder createHolder(View v) {
		TextView someText = (TextView) v.findViewById(R.id.exampleTextView);
		VersionedObjectViewHolder VersionedObjectHolder = new VersionedObjectViewHolder(
				someText);
		return VersionedObjectHolder;
	}

}
