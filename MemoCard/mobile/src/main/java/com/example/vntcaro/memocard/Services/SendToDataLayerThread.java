package com.example.vntcaro.memocard.Services;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;




/**
 * Created by vntcaro on 02/10/2015.
 */
public class SendToDataLayerThread extends Thread {

        static final String TAG = "SendDataLayer";
        String path;
        DataMap dataMap;
        GoogleApiClient mGoogleApiClient;

        //Class Constructor
        public SendToDataLayerThread(String p, DataMap data) {
            path = p;
            dataMap = data;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(
                    mGoogleApiClient).await();
            for (Node node : nodes.getNodes()) {

                //Construct a DataRequest and send it to DataLayer

                PutDataMapRequest putDMR = PutDataMapRequest.create(path);
                putDMR.getDataMap().putAll(dataMap);
                PutDataRequest request = putDMR.asPutDataRequest();
                DataApi.DataItemResult result = Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                        .await();
                if ( result.getStatus().isSuccess()) {
                    Log.d(TAG, "DataMap: #" + dataMap + "# sent to: " + node.getDisplayName());
                } else {
                    //ERROR
                    Log.d(TAG,"ERROR: failed to send DataMap");
                }

            }
        }





}
