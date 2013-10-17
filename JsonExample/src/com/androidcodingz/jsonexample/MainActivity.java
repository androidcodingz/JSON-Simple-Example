package com.androidcodingz.jsonexample;

import java.util.ArrayList;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView lv;
	private HashMap<String, String> map1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.listView1);

		new look2()
				.execute("http://api.openweathermap.org/data/2.5/weather?q=London,uk");

	}

	class look2 extends AsyncTask<String, String, String> {
		private int tt;
		private JSONArray lookfor;
		private ProgressDialog pd;
		String srchtxt, url;

		private JSONObject e;
		private ArrayList<HashMap<String, String>> mylist;
		private HashMap<String, String> map;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(MainActivity.this);
			pd.setMessage("Loading Please wait...");
			pd.setCancelable(false);
			pd.show();
			System.out.println("1111");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				String[] ss = params;

				StrictMode.ThreadPolicy policy1 = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy1);
				mylist = new ArrayList<HashMap<String, String>>();
				System.out.println("In Background.....");

				url = ss[0];
				// url.replace("", "%20");
				System.out.println(url);
				JSONObject js = JSONfunctions.getJSONfromURL(url);
				System.out.println("....."+js);
				//JSONObject res = js.getJSONObject("sys");
				//System.out.println("....."+res);
				

				// JSONObject res1=res.getJSONObject("success");
				if (js.has("weather")) {
					lookfor = js.getJSONArray("weather");
					if (lookfor != null)

						for (int i = 0; i < lookfor.length(); i++) {
							map = new HashMap<String, String>();
							e = lookfor.getJSONObject(i);
							map.put("id", e.getString("id"));
							map.put("main", e.getString("main"));
							map.put("description", e.getString("description"));
							//map.put("icon", e.getString("icon"));
							

							mylist.add(map);

							System.out.println("insert" + mylist);
						}
					else {

					}

				}

			} catch (JSONException e) {
				// TODO: handle exception
				System.out.println(e);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}

			return null;
		}

		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,
					mylist, R.layout.row, new String[] {"description","id","main" },
					new int[] { R.id.textView1,R.id.textView2, R.id.textView3,});

			lv.setAdapter(adapter);

			pd.dismiss();

			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					map1 = new HashMap<String, String>();
					map1 = mylist.get(position);
					Toast.makeText(getApplicationContext(),
							map1.get("main"), 1000).show();

				}
			});

			
		}

	}

}
