package net.testcore.cellanalyzer;

import java.util.List;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "net.testcore.cellanalyzer.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.writeViews();
}
	
	private void writeViews() {
		TelephonyStrings tStr = new TelephonyStrings((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE), getResources());
		((TextView) findViewById(R.id.net_id_val)).setText(tStr.getNetworkOperator());		
		((TextView) findViewById(R.id.nname_info_val)).setText(tStr.getNetworkOperatorName());
		((TextView) findViewById(R.id.cell_info_val)).setText(tStr.getCellInfo());
		((TextView) findViewById(R.id.neigh_info_val)).setText(tStr.getNeighborInfo());
		((TextView) findViewById(R.id.ntype_id_val)).setText(tStr.getNetworkType());
		((TextView) findViewById(R.id.data_state_val)).setText(tStr.getDataState());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch(item.getItemId()) {
		case R.id.action_refresh:
			writeViews();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

// Proxy class with logic to expose 
// TelephonyManager data as strings
class TelephonyStrings {
	private TelephonyManager tMgr;
	private Resources res;

	public TelephonyStrings(TelephonyManager tm, Resources r) {
		tMgr = tm;
		res = r;
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

	public String getNetworkType() {
		String type = null;

		SparseArray<String> types = new SparseArray<String>();
		types.append(TelephonyManager.NETWORK_TYPE_CDMA, "CDMA");
		types.append(TelephonyManager.NETWORK_TYPE_HSDPA, "HSDPA");
		types.append(TelephonyManager.NETWORK_TYPE_HSPA, "HSPA");
		types.append(TelephonyManager.NETWORK_TYPE_LTE, "LTE");

		type = types.get(tMgr.getNetworkType());
		System.out.println("Found network type: " + type);
		return type;
	}

	public String getDataState() {
		String state = null;
	
		SparseArray<String> states = new SparseArray<String>();
		states.append(TelephonyManager.DATA_DISCONNECTED, res.getString(R.string.dstate_discon));
		states.append(TelephonyManager.DATA_CONNECTED, res.getString(R.string.dstate_conn));
		states.append(TelephonyManager.DATA_CONNECTING, res.getString(R.string.dstate_conning));
		states.append(TelephonyManager.DATA_SUSPENDED, res.getString(R.string.dstate_susp));

		state = states.get(tMgr.getDataState());
		System.out.println("Found network state: " + state);
		return state;
	}
}
