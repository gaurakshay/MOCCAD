package edu.ou.oudb.cacheprototypelibrary.estimationcache.process;

import java.net.ConnectException;

import edu.ou.oudb.cacheprototypelibrary.core.process.Process;
import edu.ou.oudb.cacheprototypelibrary.estimationcache.CloudEstimationComputationManager;
import edu.ou.oudb.cacheprototypelibrary.estimationcache.Estimation;
import edu.ou.oudb.cacheprototypelibrary.querycache.exception.DownloadDataException;
import edu.ou.oudb.cacheprototypelibrary.querycache.exception.JSONParserException;
import edu.ou.oudb.cacheprototypelibrary.querycache.query.Query;

public class CloudEstimationComputationProcess implements Process<Query, Estimation>{

	CloudEstimationComputationManager mCloudEstimationComputationManager = null;
	
	public CloudEstimationComputationProcess(CloudEstimationComputationManager clEstiCompManager)
	{
		mCloudEstimationComputationManager = clEstiCompManager;
	}
	
	@Override
	public Estimation run(Query query) throws ConnectException, JSONParserException, DownloadDataException {
		return mCloudEstimationComputationManager.estimate(query);
	}

}
