package com.doodeec.toby.views.shoppingitem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doodeec.toby.R;
import com.doodeec.toby.appstate.AppData;
import com.doodeec.toby.views.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * @author dusan.bartos
 */
public class ShoppingItemsListFragment extends Fragment implements DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    public final static String TAG = "ShoppingItemsList";

    private ShoppingItemAdapter adapter;
    private int i = 0;
    private TextView warningText;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ShoppingItemAdapter();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fab_layout, container, false);

        adapter.setData(AppData.getInstance().getShoppingListItems());

        warningText = (TextView) rootView.findViewById(R.id.warning);
        checkDataSize();

        FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.floatingButton);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = "my word";
                Asset asset = Asset.createFromBytes(word.getBytes());

                PutDataMapRequest dataMap = PutDataMapRequest.create("/dataMapPath");
                dataMap.getDataMap().putAsset("word", asset);
                PutDataRequest request = dataMap.asPutDataRequest();
                Log.d("TOBY", "Send word to wearable");
                Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                        .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                            @Override
                            public void onResult(DataApi.DataItemResult dataItemResult) {
                                Log.d("TOBY", "Sending word was successful: " + dataItemResult.getStatus()
                                        .isSuccess());
                            }
                        });

                /*AppData.getInstance().addShoppingListItem(new ShoppingListItem("Shopping item " + i++));
                adapter.notifyDataSetChanged();
                checkDataSize();*/
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d("TOBY", "Data changed " + dataEvents);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("TOBY", "Connection failed " + connectionResult);
        Wearable.DataApi.removeListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("TOBY", "Connected " + bundle);
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("TOBY", "Connection suspended " + i);
    }

    @Override
    public void onStop() {
        Wearable.DataApi.removeListener(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void checkDataSize() {
        if (AppData.getInstance().getShoppingListItems().size() > 0) {
            warningText.setVisibility(View.GONE);
        } else {
            warningText.setVisibility(View.VISIBLE);
        }
    }
}
