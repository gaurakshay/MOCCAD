package edu.ou.oudb.cacheprototypeapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import edu.ou.oudb.cacheprototypeapp.R;
import edu.ou.oudb.cacheprototypelibrary.utils.StatisticsManager;

public class StatisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView statistics = (TextView) findViewById(R.id.statistics);
		
		statistics.setText("PARSER TO BE IMPLEMENTED");

	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch(id)
		{
		case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
		case R.id.action_save:
			StatisticsManager.close();
			return true;
		case R.id.action_delete:
			StatisticsManager.delete();
			finish();
			return true;
	    }
		return super.onOptionsItemSelected(item);
	}
}
