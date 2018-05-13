package gk.hangman;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;

public class LetterAdapter extends BaseAdapter {
	private String[] letters;
	private LayoutInflater letterInf;
	
	public LetterAdapter(Context c) {
		letters=new String[30];
		for (int a = 0; a < letters.length; a++) {
		  letters[a] = "" + (char)(a+'א');

		}
		letters[27] = "'";
		letters[28] = "/";
		letters[29] = "!";
		//specify the context in which we want to inflate the layout
		// will be passed from the main activity

		letterInf = LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		
		return letters.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//create a button for the letter at this position in the alphabet
		  Button letterBtn;
		  if (convertView == null) {
		    //inflate the button layout
		    letterBtn = (Button)letterInf.inflate(R.layout.letter, parent, false);
		  } else {
		    letterBtn = (Button) convertView;
		  }
		  //set the text to this letter

			int new_position = (position/6 + 1)*6 - (position%6 + 1 );
		  letterBtn.setText(letters[new_position]);
		  return letterBtn;
		  
	}

}
