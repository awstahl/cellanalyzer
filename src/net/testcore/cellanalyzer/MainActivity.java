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
		TelephonyStrings tStr = new TelephonyStrings((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE));

		((TextView) findViewById(R.id.net_id_val)).setText(tStr.getNetworkOperator());		
		((TextView) findViewById(R.id.nname_info_val)).setText(tStr.getNetworkOperatorName());
		((TextView) findViewById(R.id.cell_info_val)).setText(tStr.getCellInfo());
		((TextView) findViewById(R.id.neigh_info_val)).setText(tStr.getNeighborInfo());
		((TextView) findViewById(R.id.ntype_id_val)).setText(tStr.getNetworkType());
		
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

// Wrapper class with logic to expose 
// TelephonyManager data as strings
class TelephonyStrings {
	private TelephonyManager tMgr;

	public TelephonyStrings(TelephonyManager tm) {
		tMgr = tm;
	}
	
	public String getNetworkOperator() {
		String net = tMgr.getNetworkOperator();
		System.out.println("Network operator: " + net);
		return net;
	}
	
	public String getNetworkOperatorName() {
		String nname = tMgr.getNetworkOperatorName();
		System.out.println("Network operator name: " + nname);
		return nname;
	}
	
	public String getCellInfo() {
		List<CellInfo> cells = tMgr.getAllCellInfo();
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
		return crpt;
	}

	public String getNeighborInfo() {
		List<NeighboringCellInfo> neighbors = tMgr.getNeighboringCellInfo();
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
		return nrpt;
	}

	// TODO: Translate the f*in ntype int to its string network type. Fuck java.
	public String getNetworkType() {
		int ntype =  tMgr.getNetworkType();
		String type = null;
		System.out.println("Reported network type: " + ntype);
		
		// java: the more verbose the better...
		switch(ntype) {
		case TelephonyManager.NETWORK_TYPE_CDMA:
			type = "CDMA";
			break;
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			type = "HSDPA";
			break;
		case TelephonyManager.NETWORK_TYPE_HSPA:
			type = "HSPA";
			break;
		case TelephonyManager.NETWORK_TYPE_LTE:
			type = "LTE";
			break;
		default:
			type = "unknown";
		}
		
		System.out.println("Found network type: " + type);
		return type;
	}
}
