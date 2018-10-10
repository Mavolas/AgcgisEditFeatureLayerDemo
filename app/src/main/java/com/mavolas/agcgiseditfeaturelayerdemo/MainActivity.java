package com.mavolas.agcgiseditfeaturelayerdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureSet;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.tasks.SpatialRelationship;
import com.esri.core.tasks.ags.query.Query;

public class MainActivity extends AppCompatActivity {


    // arcgis components
    private MapView mapView;
    private ArcGISFeatureLayer featureLayer;
    private ArcGISDynamicMapServiceLayer operationalLayer;
    private Point pointClicked;
    private Envelope initextent;


    private LayoutInflater inflator;
    private ListView listView;
    private View listLayout;

    public static final String TAG = "AttributeEditorSample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        mapView = new MapView(this);
        initextent = new Envelope(-10868502.895856911, 4470034.144641369,
                -10837928.084542884, 4492965.25312689);
        mapView.setExtent(initextent, 0);
        setContentView(mapView);


        // Tiled Layer Basemap
        ArcGISTiledMapServiceLayer basemap = new ArcGISTiledMapServiceLayer(getResources().getString(R.string.basemap));
        mapView.addLayer(basemap);

        // Operational Layer showing Kansas petroleum field production
        operationalLayer = new ArcGISDynamicMapServiceLayer(getResources().getString(R.string.operational_layer));
        mapView.addLayer(operationalLayer);

        // feature service representing the field production layer to query and highlight selections
        featureLayer = new ArcGISFeatureLayer(
                getResources().getString(R.string.feature_layer),
                ArcGISFeatureLayer.MODE.SELECTION);
        mapView.addLayer(featureLayer);


        // Show feature selected with outline symbol
        SimpleFillSymbol sfs = new SimpleFillSymbol( Color.TRANSPARENT);
        sfs.setOutline(new SimpleLineSymbol(Color.YELLOW, 5));
        featureLayer.setSelectionSymbol(sfs);


//        // Create a new AttributeListAdapter when the feature layer is initialized
//        if (featureLayer.isInitialized()) {
//            listAdapter = new AttributeListAdapter(this, featureLayer.getFields(), featureLayer.getTypes(),
//                    featureLayer.getTypeIdField());
//        } else {
//            featureLayer.setOnStatusChangedListener(new OnStatusChangedListener() {
//                private static final long serialVersionUID = 1L;
//
//                public void onStatusChanged(Object source, STATUS status) {
//                    if (status == STATUS.INITIALIZED) {
//                        listAdapter = new AttributeListAdapter(AttributeEditorActivity.this, featureLayer.getFields(), featureLayer
//                                .getTypes(), featureLayer.getTypeIdField());
//                    }
//                }
//            });
//        }

        // Set tap listener for MapView
        mapView.setOnSingleTapListener(new OnSingleTapListener() {
            private static final long serialVersionUID = 1L;

            public void onSingleTap(float x, float y) {
                // convert event into screen click
                pointClicked = mapView.toMapPoint(x, y);

                // build a query to select the clicked feature
                Query query = new Query();
                query.setOutFields(new String[]{"*"});
                query.setSpatialRelationship( SpatialRelationship.INTERSECTS);
                query.setGeometry(pointClicked);
                query.setInSpatialReference(mapView.getSpatialReference());

                // call the select features method and implement the callbacklistener
                featureLayer.selectFeatures(query, ArcGISFeatureLayer.SELECTION_METHOD.NEW, new CallbackListener<FeatureSet>() {

                    // handle any errors
                    public void onError(Throwable e) {
                        Log.d(TAG, "Select Features Error" + e.getLocalizedMessage());
                    }

                    public void onCallback(FeatureSet queryResults) {
                        if (queryResults.getGraphics().length > 0) {
//                            Log.d(
//                                    TAG,
//                                    "Feature found id="
//                                            + queryResults.getGraphics()[0].getAttributeValue(featureLayer.getObjectIdField()));
//
//                            // set new data and notify adapter that data has changed
//                            listAdapter.setFeatureSet(queryResults);
//                            listAdapter.notifyDataSetChanged();
//
//                            // This callback is not run in the main UI thread. All GUI
//                            // related events must run in the UI thread,
//                            // therefore use the Activity.runOnUiThread() method. See
//                            // http://developer.android.com/reference/android/app/Activity.html#runOnUiThread(java.lang.Runnable)
//                            // for more information.
//                            AttributeEditorActivity.this.runOnUiThread(new Runnable() {
//
//                                public void run() {
//
//                                    // show the editor dialog.
//                                    showDialog(ATTRIBUTE_EDITOR_DIALOG_ID);
//
//                                }
//                            });
                        }
                    }
                });
            }
        });

    }
}
