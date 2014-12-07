package net.testcore.cellanalyzer;

import java.util.List;

import android.app.Activity;
import android.content.Context;
//import android.content.Intent;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "net.testcore.cellanalyzer.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		String net = tMgr.getNetworkOperator();
		System.out.println("Network operator: " + net);
		((TextView) findViewById(R.id.net_id_val)).setText(net);
		
		List<CellInfo> cells = tMgr.getAllCellInfo();
		TextView cell_val = (TextView) findViewById(R.id.cell_info_val);
		String crpt = null;
		if (cells == null || cells.isEmpty()) {
			crpt = "no cells found";
			System.out.println("[ALLCELLINFO]: " + crpt);
		}
		else {
			crpt = "We're ok... found " + cells.size() + " cells";
			System.out.println(crpt);

			for (CellInfo cell : cells) {
				System.out.println(cell.toString());
			}
		}
		cell_val.setText(crpt);
		
		List<NeighboringCellInfo> neighbors = tMgr.getNeighboringCellInfo();
		TextView neigh_val = (TextView) findViewById(R.id.neigh_info_val);
		String nrpt = null;
		if (neighbors == null || neighbors.isEmpty()) {
			nrpt = "no neighbors found";
			System.out.println(nrpt);
		}
		else {
			nrpt = "found some neighbors: " + neighbors.size();
			System.out.println(nrpt);

			for (NeighboringCellInfo cell : neighbors) {
				System.out.println("Neighbor cell string: " + cell.toString());
				System.out.println("Network type: " + cell.getNetworkType());
				System.out.println("Location: " + cell.getLac());
				System.out.println("Sig Strength: " + cell.getRssi());
			}

		}
		neigh_val.setText(nrpt);
		//tv.setText("this");
		//setContentView(tv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
