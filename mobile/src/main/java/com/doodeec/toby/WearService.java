package com.doodeec.toby;

import android.util.Log;

import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * @author dusan.bartos
 */
public class WearService extends WearableListenerService {

    private static final String TAG = "TOBY";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "WEAR create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "WEAR destroy");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        Log.d(TAG, "WEAR Data changed " + dataEvents);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.d(TAG, "WEAR Message " + messageEvent);
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
        Log.d(TAG, "WEAR Connected " + peer);

        //TODO sync data
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
        Log.d(TAG, "WEAR Disconnected " + peer);
    }
}
