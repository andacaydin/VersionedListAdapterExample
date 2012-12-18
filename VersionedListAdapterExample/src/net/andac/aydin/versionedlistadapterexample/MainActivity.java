package net.andac.aydin.versionedlistadapterexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// we fill up our singleton
		for (int i = 0; i < 10; i++) {
			VersionedObjectListSingleton.getInstance().putVersionedObject(
					new ExampleObject(Integer.valueOf(i).longValue(),
							"generic text " + i));
			Log.v(TAG, "adding" + i);
		}

		// we get our listview and set adapter
		ListView listview = (ListView) this
				.findViewById(R.id.versionedListView);
		ExampleListAdapter exampleListAdapter = new ExampleListAdapter(this,
				R.layout.list_item);
		listview.setAdapter(exampleListAdapter);

		// Set to the middle
		listview.setSelection(exampleListAdapter.getMiddle());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
