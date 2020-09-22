package com.berkd.graphy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {


    private final Handler mHandler = new Handler();
    private Runnable mTimer;
    private double graphLastXValue = 5d;
    private LineGraphSeries<DataPoint> mSeries;
    private int myDataPoints = 50; // used to set the precision of the amplitude accuracy.

    private TextView graphType; // used to store the amplitude OR frequency type.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        initGraph(graph);

    }


    @Override
    public void onResume() {
        runGraph();
        super.onResume();
    }

    @Override
    public void onPause() {
        pauseGraph();
        super.onPause();
    }

    public void initGraph(GraphView graph) {
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);

        graph.getGridLabelRenderer().setLabelVerticalWidth(100);

        // first mSeries is a line
        mSeries = new LineGraphSeries<>();
        mSeries.setDrawDataPoints(true);
        mSeries.setDrawBackground(true);
        graph.addSeries(mSeries);
    }

    /*******************************************
     * Run Graph
     *  Run the graph. With a 50ms delay.
     */
    public void runGraph() {
        mTimer = new Runnable() {
            @Override
            public void run() {
                graphLastXValue += 0.25d;
                mSeries.appendData(new DataPoint(graphLastXValue, Math.random()*20), true, myDataPoints);
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(mTimer, 50);
    }

    public void pauseGraph() {
        mHandler.removeCallbacks(mTimer); // pause the graph
    }

}