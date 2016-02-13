package edu.ou.oudb.cacheprototypeapp.ui;

import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.ou.oudb.cacheprototypeapp.R;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	Context mContext;
	
	public SectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		mContext = context;
	}
	

	@Override
	public Fragment getItem(int position) {
		
		switch (position) {
		case 0:
			return new ProcessedQueriesFragment();
		case 1:
			return new QueryCacheFragment();
		case 2:
			return new MobileEstimationCacheFragment();
		case 3:
			return new CloudEstimationCacheFragment();
			
		}

		return PlaceholderFragment.newInstance(position + 1);
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return mContext.getString(R.string.processed_queries_section).toUpperCase(l);
		case 1:
			return mContext.getString(R.string.query_cache_section).toUpperCase(l);
		case 2:
			return mContext.getString(R.string.mobile_estimation_cache_section).toUpperCase(l);
		case 3:
			return mContext.getString(R.string.cloud_estimation_cache_section).toUpperCase(l);
		}
		return null;
	}

	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			TextView sectionTitle = (TextView) rootView.findViewById(R.id.section_label);
			
			sectionTitle.setText("Section " + getArguments().getInt(ARG_SECTION_NUMBER));
			
			return rootView;
		}
	}
	
}
