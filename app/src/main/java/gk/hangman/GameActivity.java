package gk.hangman;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;



public class GameActivity extends Activity {
	private String[] words;
	private ArrayList<String> new_words;
	private Random rand;
	private String currWord;
	private LinearLayout wordLayout;
	private TextView[] charViews;
	private GridView letters;
	private LetterAdapter ltrAdapt;
	//body part images
	private ImageView[] bodyParts;
	//number of body parts
	private int numParts=6;
	//current part - will increment when wrong answers are chosen
	private int currPart;
	//number of characters in current word
	private int numChars;
	//number correctly guessed
	private int numCorr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_game);
	  getActionBar().setDisplayHomeAsUpEnabled(true);
	  Resources res = getResources();
	  // sama words kuin mika lisattiin xml arrays

		words = res.getStringArray(R.array.words);

		ArrayList<String> new_words = new ArrayList<String>();
	  for(int i=0; i < words.length; i++)
	  {
		  String new_word = "";
		  for (int j = words[i].length()-1; j>=0; j--) {
			  new_word += words[i].charAt(j);
		  }

		  new_words.add(new_word);
	  }

		for(int i=0; i < words.length; i++)
		{
			words[i] = new_words.get(i);

		}
	  rand = new Random();
	  currWord = "";
	  wordLayout = (LinearLayout)findViewById(R.id.word);
	  letters = (GridView)findViewById(R.id.letters);
	  
	  bodyParts = new ImageView[numParts];
	  bodyParts[0] = (ImageView)findViewById(R.id.head);
	  bodyParts[1] = (ImageView)findViewById(R.id.body);
	  bodyParts[2] = (ImageView)findViewById(R.id.arm1);
	  bodyParts[3] = (ImageView)findViewById(R.id.arm2);
	  bodyParts[4] = (ImageView)findViewById(R.id.leg1);
	  bodyParts[5] = (ImageView)findViewById(R.id.leg2);
	  
	  playGame();
	}
	
	private void playGame() {
		// valitaan random sana meidan arraysta arvattavaksi
		String newWord = words[rand.nextInt(words.length)];
		// ei samaa heti uusiksi
		while (newWord.equals(currWord))  {
			newWord = words[rand.nextInt(words.length)];
		}
		currWord = newWord;
		
		charViews = new TextView[currWord.length()];
		// poista vanhat sanat
		wordLayout.removeAllViews();
		
		for (int c = 0; c < currWord.length(); c++) {
			  charViews[c] = new TextView(this);
			  charViews[c].setText(""+currWord.charAt(c));
			  
			  charViews[c].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			  charViews[c].setGravity(Gravity.CENTER);
			  charViews[c].setTextColor(Color.WHITE);
			  charViews[c].setBackgroundResource(R.drawable.letter_bg);
			  //add to layout
			  wordLayout.addView(charViews[c]);
			  
			}	
		ltrAdapt=new LetterAdapter(this);
		letters.setAdapter(ltrAdapt);
		
		currPart=0;
		numChars=currWord.length();
		numCorr=0;
		
		for(int p = 0; p < numParts; p++) {
			  bodyParts[p].setVisibility(View.INVISIBLE);
			}
	}
	
	public void letterPressed(View view) {
		  //user has pressed a letter to guess
		String ltr=((TextView)view).getText().toString();
		char letterChar = ltr.charAt(0);

		view.setEnabled(false);
		view.setBackgroundResource(R.drawable.letter_down);
		
		// tarkistetaan onko arvattavassa sanassa
		boolean correct = false;
		for(int k = 0; k < currWord.length(); k++) {
			if (currWord.charAt(k)==letterChar){
				correct = true;
				numCorr++;
				charViews[k].setTextColor(Color.BLACK);
			}
		}
		
		if (correct) {
			  //correct guess
			if (numCorr == numChars) {
				  //user has won
				// Disable Buttons
				disableBtns();
				 
				  // Display Alert Dialog
				AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
				winBuild.setTitle("מצוין !");

				winBuild.setMessage("ניצחת במשחק \n\n");
				winBuild.setPositiveButton("שחק שוב",
						new DialogInterface.OnClickListener() {
						  public void onClick(DialogInterface dialog, int id) {
							GameActivity.this.playGame();
				    }});
				 
				  winBuild.setNegativeButton("יציאה",
				    new DialogInterface.OnClickListener() {
				      public void onClick(DialogInterface dialog, int id) {
				        GameActivity.this.finish();
				    }});
				 
				  winBuild.show();
				}
		} else if (currPart < numParts) {
			//some guesses left
			bodyParts[currPart].setVisibility(View.VISIBLE);
			currPart++;
		} else {
			//user has lost
			disableBtns();
			 
			  // Display Alert Dialog
			AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
			loseBuild.setTitle("אופס !");
			currWord = reverseWord(currWord);
			loseBuild.setMessage("הפסדת!\n\nהתשובה הנכונה הייתה : "+currWord);
			loseBuild.setPositiveButton("שחק שוב",
			  new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int id) {
			        GameActivity.this.playGame();
			    }});
			 
			  loseBuild.setNegativeButton("יציאה",
			    new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int id) {
			        GameActivity.this.finish();
			    }});
			 
			  loseBuild.show();
		
		}
		}

	public void disableBtns() {
		  int numLetters = letters.getChildCount();
		  for (int l = 0; l < numLetters; l++) {
		    letters.getChildAt(l).setEnabled(false);
		  }
		}
	private String reverseWord(String word)
	{
		String new_word = "";
		for (int j = word.length()-1; j>=0; j--) {
			new_word += word.charAt(j);
		}

		return  new_word;


	}
}
