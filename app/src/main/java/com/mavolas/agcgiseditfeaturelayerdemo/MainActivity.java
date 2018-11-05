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

    ArcGISDynamicMapServiceLayer dynamicLayer = null;

    public static final String TAG = "AttributeEditorSample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );


        setContentView( R.layout.activity_acgis_map);

        mapView = findViewById(R.id.mapView);

//        initextent = new Envelope(-10868502.895856911, 4470034.144641369,
//                -10837928.084542884, 4492965.25312689);
//        mapView.setExtent(initextent, 0);

//        dynamicLayer = new ArcGISDynamicMapServiceLayer("http://172.18.0.244:6080/arcgis/rest/services/Community/Community/MapServer");
//
//        mapView.addLayer(dynamicLayer);

        // Create feature layers
        ArcGISFeatureLayer fl1 = new ArcGISFeatureLayer(
                "http://172.18.0.244:6080/arcgis/rest/services/Community/Community/FeatureServer/0",
                ArcGISFeatureLayer.MODE.ONDEMAND);

        mapView.addLayer(fl1);

        // Tiled Layer Basemap
//        ArcGISTiledMapServiceLayer basemap = new ArcGISTiledMapServiceLayer(getResources().getString(R.string.basemap));
//        mapView.addLayer(basemap);
//
//        // Operational Layer showing Kansas petroleum field production
//        operationalLayer = new ArcGISDynamicMapServiceLayer(getResources().getString(R.string.operational_layer));
//        mapView.addLayer(operationalLayer);
//
//        // feature service representing the field production layer to query and highlight selections
//        featureLayer = new ArcGISFeatureLayer(
//                getResources().getString(R.string.feature_layer),
//                ArcGISFeatureLayer.MODE.SELECTION);
//        mapView.addLayer(featureLayer);
//
//
//        // Show feature selected with outline symbol
//        SimpleFillSymbol sfs = new SimpleFillSymbol( Color.TRANSPARENT);
//        sfs.setOutline(new SimpleLineSymbol(Color.YELLOW, 5));
//        featureLayer.setSelectionSymbol(sfs);


    }
}
