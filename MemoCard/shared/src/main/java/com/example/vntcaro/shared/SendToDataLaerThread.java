package com.example.vntcaro.shared;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;


/**
 * Created by vntcaro on 02/10/2015.
 */
class SendToDataLayerThread extends Thread {
    String path;
    DataMap dataMap;
    GoogleApiClient mGoogleClient;
    static final String TAG="SendLayerThread";
    // Constructor for sending data objects to the data layer
    SendToDataLayerThread(String p, DataMap data,GoogleApiClient GoogleClient) {
        path = p;
        dataMap = data;
        mGoogleClient= GoogleClient;
    }

    public void run() {
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleClient).await();
        for (Node node : nodes.getNodes()) {

            // Construct a DataRequest and send over the data layer
            PutDataMapRequest putDMR = PutDataMapRequest.create(path);
            putDMR.getDataMap().putAll(dataMap);
            PutDataRequest request = putDMR.asPutDataRequest();
            DataApi.DataItemResult result = Wearable.DataApi.putDataItem(mGoogleClient,request).await();
            if (result.getStatus().isSuccess()) {
                Log.v(TAG, "DataMap: " + dataMap + " sent to: " + node.getDisplayName());
            } else {
                // Log an error
                Log.v(TAG, "ERROR: failed to send DataMap");
            }
        }
    }
}