package gk.hangman;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
//import android.support.v4.R;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HangmanActivity extends Activity implements OnClickListener{
	private AlertDialog helpAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        Button playButton = (Button)findViewById(R.id.playBtn);
        playButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hangman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	switch (item.getItemId()) {
    	  case android.R.id.home:
    	    NavUtils.navigateUpFromSameTask(this);
    	    return true;
    	  case R.id.action_help:
    	    showHelp();
    	    return true;
    	  }
    	 
    	  return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.playBtn) {
			Intent playIntent = new Intent(this, GameActivity.class);
			this.startActivity(playIntent);
		}
		
	}
	
	public void showHelp() {
		  AlertDialog.Builder helpBuild = new AlertDialog.Builder(this);
		 
		  helpBuild.setTitle("עזרה");
		  helpBuild.setMessage("תשאלו את טל קינן הוא הבוס\n\n"
		      );
		  helpBuild.setPositiveButton("אוקיי",
		    new DialogInterface.OnClickListener() {
		      public void onClick(DialogInterface dialog, int id) {
		        helpAlert.dismiss();
		    }});
		  helpAlert = helpBuild.create();
		 
		  helpBuild.show();
		}
	
}
