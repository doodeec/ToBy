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

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TOBY", "WEAR create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TOBY", "WEAR destroy");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        Log.d("TOBY", "WEAR Data changed " );
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.d("TOBY", "WEAR Message " + messageEvent.getPath());
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
        Log.d("TOBY", "WEAR Connected ");

        //TODO sync data
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
        Log.d("TOBY", "WEAR Disconnected");
    }
}
