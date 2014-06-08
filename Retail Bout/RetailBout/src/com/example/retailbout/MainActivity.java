package com.example.retailbout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

   private static final int REQUEST_ENABLE_BT = 1;
   private Button onBtn;
   private Button offBtn;
   private Button listBtn;
   private Button findBtn;
   private TextView text;
   private BluetoothAdapter myBluetoothAdapter;
   private Set<BluetoothDevice> pairedDevices;
   private ListView myListView;
   private ArrayAdapter<String> BTArrayAdapter;
   String[] menutitles;
   TypedArray menuIcons;

   // nav drawer title
   private CharSequence mDrawerTitle;
   private CharSequence mTitle;

   private DrawerLayout mDrawerLayout;
   private ListView mDrawerList;
   private ActionBarDrawerToggle mDrawerToggle;

   private List<Slidermodel> rowItems;
   private SliderAdapter adapter;
   final Context context = this;

   @SuppressLint("NewApi")
   @Override
   protected void onCreate(Bundle savedInstanceState) {
	   
	 		
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      mTitle = mDrawerTitle = getTitle();

      menutitles = getResources().getStringArray(R.array.titles);
      menuIcons = getResources().obtainTypedArray(R.array.icons);

      mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
      mDrawerList = (ListView) findViewById(R.id.slider_list);

      rowItems = new ArrayList<Slidermodel>();

      for (int i = 0; i < menutitles.length; i++) {
       Slidermodel items = new Slidermodel(menutitles[i], menuIcons.getResourceId(
         i, -1));
       rowItems.add(items);
      }

      menuIcons.recycle();

      adapter = new SliderAdapter(getApplicationContext(), rowItems);

      mDrawerList.setAdapter(adapter);
      mDrawerList.setOnItemClickListener(new SlideitemListener());

      // enabling action bar app icon and behaving it as toggle button
      getActionBar().setDisplayHomeAsUpEnabled(true);
      getActionBar().setHomeButtonEnabled(true);

      mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
        R.drawable.payroll, R.string.app_name,R.string.app_name) {
           public void onDrawerClosed(View view) {
             getActionBar().setTitle(mTitle);
             // calling onPrepareOptionsMenu() to show action bar icons
             invalidateOptionsMenu();
           }

            public void onDrawerOpened(View drawerView) {
                  getActionBar().setTitle(mDrawerTitle);
                   // calling onPrepareOptionsMenu() to hide action bar icons
                  invalidateOptionsMenu();
             }
      };

      mDrawerLayout.setDrawerListener(mDrawerToggle);

      if (savedInstanceState == null) {
           // on first time display view for first nav item
           updateDisplay(4);
         }
      // take an instance of BluetoothAdapter - Bluetooth radio
      myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      if(myBluetoothAdapter == null) {
    	  onBtn.setEnabled(false);
    	  offBtn.setEnabled(false);
    	  listBtn.setEnabled(false);
    	  findBtn.setEnabled(false);
    	  text.setText("Status: not supported");
    	  
    	  Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",
         		 Toast.LENGTH_LONG).show();
      } else {
	      text = (TextView) findViewById(R.id.text);
//	      onBtn = (Button)findViewById(R.id.turnOn);
//	      
//	      onBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				on(v);
//			}
//	      });
	      
//	      offBtn = (Button)findViewById(R.id.turnOff);
//	      offBtn.setOnClickListener(new OnClickListener() {
//	  		
//	  		@Override
//	  		public void onClick(View v) {
//	  			// TODO Auto-generated method stub
//	  			off(v);
//	  		}
//	      });
	      
	      listBtn = (Button)findViewById(R.id.paired);
	      listBtn.setOnClickListener(new OnClickListener() {
	  		
	  		@Override
	  		public void onClick(View v) {
	  			// TODO Auto-generated method stub
	  			list(v);
	  		}
	      });
	      
	      findBtn = (Button)findViewById(R.id.search);
	      findBtn.setOnClickListener(new OnClickListener() {
	  		
	  		@Override
	  		public void onClick(View v) {
	  			// TODO Auto-generated method stub
	  			find(v);
	  		}
	      });
	    
	      myListView = (ListView)findViewById(R.id.listView1);
	
	      // create the arrayAdapter that contains the BTDevices, and set it to the ListView
	      BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
	      myListView.setAdapter(BTArrayAdapter);
      }
      
		   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				// set title
				alertDialogBuilder.setTitle("Turn ON your Bluetooth");

				// set dialog message
				alertDialogBuilder
					.setMessage("In order to use this app please turn ON the Bluetooth")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							if (!myBluetoothAdapter.isEnabled()) {
						         Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
						         startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

						         Toast.makeText(getApplicationContext(),"Bluetooth turned on" ,
						        		 Toast.LENGTH_LONG).show();
						      
							}else{
						         Toast.makeText(getApplicationContext(),"Bluetooth is already on",
						        		 Toast.LENGTH_LONG).show();
						      }
					
							dialog.dismiss();
//							MainActivity.this.finish();
						}
					  });
			

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
					alertDialog.setCanceledOnTouchOutside(true);
   
   }


   public void on(View view){
      if (!myBluetoothAdapter.isEnabled()) {
         Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
         startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

         Toast.makeText(getApplicationContext(),"Bluetooth turned on" ,
        		 Toast.LENGTH_LONG).show();
      }
      else{
         Toast.makeText(getApplicationContext(),"Bluetooth is already on",
        		 Toast.LENGTH_LONG).show();
      }
   }
   
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   // TODO Auto-generated method stub
	   if(requestCode == REQUEST_ENABLE_BT){
		   if(myBluetoothAdapter.isEnabled()) {
			   text.setText("Status: Enabled");
		   } else {   
			   text.setText("Status: Disabled");
		   }
	   }
   }
   
   public void list(View view){
	  // get paired devices
      pairedDevices = myBluetoothAdapter.getBondedDevices();
      
      // put it's one to the adapter
      for(BluetoothDevice device : pairedDevices)
    	  BTArrayAdapter.add(device.getName()+ "\n" + device.getAddress());

      Toast.makeText(getApplicationContext(),"Show Paired Devices",
    		  Toast.LENGTH_SHORT).show();
      
   }
   
   final BroadcastReceiver bReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        // When discovery finds a device
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	             // Get the BluetoothDevice object from the Intent
	        	 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	        	 // add the name and the MAC address of the object to the arrayAdapter
	             BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	             BTArrayAdapter.notifyDataSetChanged();
	        }
	    }
	};
	
   public void find(View view) {
	   if (myBluetoothAdapter.isDiscovering()) {
		   // the button is pressed when it discovers, so cancel the discovery
		   myBluetoothAdapter.cancelDiscovery();
	   }
	   else {
			BTArrayAdapter.clear();
			myBluetoothAdapter.startDiscovery();
			
			registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));	
		}    
   }
   
   public void off(View view){
	  myBluetoothAdapter.disable();
	  text.setText("Status: Disconnected");
	  
      Toast.makeText(getApplicationContext(),"Bluetooth turned off",
    		  Toast.LENGTH_LONG).show();
   }
   
   @Override
   protected void onDestroy() {
	   // TODO Auto-generated method stub
	   super.onDestroy();
	   unregisterReceiver(bReceiver);
   }
   
   class SlideitemListener implements ListView.OnItemClickListener {
       @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                 updateDisplay(position);
            }

      }

 @TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") private void updateDisplay(int position) {
//	Fragment fragment = null;
         switch (position) {
//                 case 0:  Intent myIntent = new Intent(this, profile_activity.class);
//                 this.startActivity(myIntent); 
 
//               case 0:  fragment = new HOME_fragment();
//                        break;
//                  
//               case 1:  fragment = new PROFILE_fragment();
//                        break;
//                           
//               case 2:  fragment = new SEARCH_fragment();
//                        break;
//                          
//               case 3:  fragment = new FEEDBACK_fragment();
//                        break;
//                   
//              default:   break;
              
              case 0: Intent newActivity = new Intent(this, home_activity.class );     
                      startActivity(newActivity);
                      break;
        
              case 1: Intent newActivity1 = new Intent(this, profile_activity.class);     
                      startActivity(newActivity1);
                      break;
                      
              case 2: Intent newActivity2 = new Intent(this, search_activity.class);     
                      startActivity(newActivity2);
                      break;
                 
              case 3: Intent newActivity3 = new Intent(this, feedback_activity.class);     
                      startActivity(newActivity3);
                      break;
                      
              case 4: this.getApplicationContext();
                
              default:   break;
         }
                        
    }

//  if (fragment != null) {
//            FragmentManager fragmentManager = getFragmentManager();
//             fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
//            // update selected item and title, then close the drawer
//            setTitle(menutitles[position]);
//             mDrawerLayout.closeDrawer(mDrawerList);
//  } else {
//              // error in creating fragment
//              Log.e("MainActivity", "Error in creating fragment");
//            }
//
//  }

     @SuppressLint("NewApi") @Override
     public void setTitle(CharSequence title) {
         mTitle = title;
        getActionBar().setTitle(mTitle);
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
           getMenuInflater().inflate(R.menu.main, menu);
           return true;
     }

 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
  // toggle nav drawer on selecting action bar app icon/title
          if (mDrawerToggle.onOptionsItemSelected(item)) {
                     return true;
              }
             // Handle action bar actions click
             switch (item.getItemId()) {
                    case  R.id.action_settings:
                                  return true;
                    default :
                                 return super.onOptionsItemSelected(item);
                }
 }

 /***
  * Called when invalidateOptionsMenu() is triggered
  */
    @Override
     public boolean onPrepareOptionsMenu(Menu menu) {
               // if nav drawer is opened, hide the action items
                boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
                menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
                return super.onPrepareOptionsMenu(menu);
      }

 /**
  * When using the ActionBarDrawerToggle, you must call it during
  * onPostCreate() and onConfigurationChanged()...
  */

    @Override
     protected void onPostCreate(Bundle savedInstanceState) {
         super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
         mDrawerToggle.syncState();
   }

     @Override
     public void onConfigurationChanged(Configuration newConfig) {
         super.onConfigurationChanged(newConfig);
          // Pass any configuration change to the drawer toggles
           mDrawerToggle.onConfigurationChanged(newConfig);
    }

		
}