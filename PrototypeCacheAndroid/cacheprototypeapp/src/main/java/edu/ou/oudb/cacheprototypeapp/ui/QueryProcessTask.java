package edu.ou.oudb.cacheprototypeapp.ui;

import java.net.ConnectException;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import edu.ou.oudb.cacheprototypeapp.AndroidCachePrototypeApplication;
import edu.ou.oudb.cacheprototypeapp.R;
import edu.ou.oudb.cacheprototypelibrary.querycache.exception.ConstraintsNotRespectedException;
import edu.ou.oudb.cacheprototypelibrary.querycache.exception.DownloadDataException;
import edu.ou.oudb.cacheprototypelibrary.querycache.exception.JSONParserException;
import edu.ou.oudb.cacheprototypelibrary.querycache.query.Query;
import edu.ou.oudb.cacheprototypelibrary.utils.JSONLoader;

public class QueryProcessTask extends AsyncTask<Query, Void, List<List<String>>>
{
	
	private ProgressDialog mProgressDialog = null;
	private Context mContext = null;
	private Query mQuery = null;
	WakeLock wakeLock = null;
	Exception exception = null;
	
	public QueryProcessTask(Context context)
	{
		mContext = context;
	}
	
	

	@Override
	protected void onPreExecute() {
		mProgressDialog = ProgressDialog.show(mContext, mContext.getString(R.string.processing_query), mContext.getString(R.string.processing_query_message));
		
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				
				cancel(true);
				handleCancelled();
				
			}
		});
		
		PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
		wakeLock.acquire();
		
		super.onPreExecute();
	}
	
	@Override
	protected List<List<String>> doInBackground(Query... query) {
		mQuery = query[0];
		try {
			return ((AndroidCachePrototypeApplication) mContext.getApplicationContext()).getDataLoader().load(mQuery);
		} catch (ConnectException | ConstraintsNotRespectedException
				| DownloadDataException | JSONParserException e) {
			exception = e;
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(List<List<String>> result) {
		mProgressDialog.dismiss();
		
		wakeLock.release();
		
		if (result == null)
		{
			if(exception != null)
			{
				
				if (exception instanceof ConstraintsNotRespectedException)
				{
					launchErrorDialog(mContext.getString(R.string.query_processing_error), ((ConstraintsNotRespectedException) exception).getMessage());
				}
				else if (exception instanceof ConnectException)
				{
					launchErrorDialog(mContext.getString(R.string.no_connection_error), mContext.getString(R.string.no_connection_error_message));
				}
				else if (exception instanceof DownloadDataException)
				{
					Log.e("ERR_CONNECTION", mContext.getString(R.string.connection_error_message));
					launchErrorDialog(mContext.getString(R.string.connection_error), mContext.getString(R.string.connection_error_message));
				} 
				else if (exception instanceof JSONParserException)
				{
					Log.e("ERR_PARSER",mContext.getString(R.string.parsing_error_message));
					launchErrorDialog(mContext.getString(R.string.connection_error),mContext.getString(R.string.parsing_error_message));
				}
				else
				{
					launchErrorDialog(mContext.getString(R.string.query_processing_error), mContext.getString(R.string.query_processing_error_message));
				}
			}
			else
			{
				launchErrorDialog(mContext.getString(R.string.query_processing_error), mContext.getString(R.string.query_processing_error_message));
			}
			
		}
		else
		{
			
			lauchResultActivity(mQuery.getRelation(),result);
		}
		super.onPostExecute(result);
	}
	
	protected void handleCancelled() {
		wakeLock.release();
		
		JSONLoader.abort();
	}
	
	private void lauchResultActivity(String relation, List<List<String>> result) {
		
		Intent intent = new Intent(mContext, ResultListActivity.class);
		intent.putExtra(ResultListActivity.QUERY_RELATION, relation);
		((AndroidCachePrototypeApplication) mContext.getApplicationContext()).setCurrentQueryResult(result);
		
		mContext.startActivity(intent);
	}
	
	private void launchErrorDialog(String title, String message)
	{
		ErrorDialog errorDialog = ErrorDialog.newInstance(title, message);
		
	    try{
	    	final Activity activity = (Activity) mContext;
	    	errorDialog.show(activity.getFragmentManager(),"error_dialog");
    	} catch (ClassCastException e) {
    		Log.d("error_launch_error_dialog", "Can't get the fragment manager with this");
    	}
	}
}
