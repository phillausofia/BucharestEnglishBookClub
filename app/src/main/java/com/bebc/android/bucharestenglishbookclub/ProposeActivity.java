/*
 * *
 *  * Created by Iulian Pralea on 7/19/19 3:46 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 7/10/19 2:02 PM
 *
 */

package com.bebc.android.bucharestenglishbookclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProposeActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We set the layout propose_activity.xml on the content of activity
        setContentView(R.layout.propose_activity);
    }

    /**
     * This method will gather the user input and sent an intent
     * to an e-mail app so the user can submit its proposal
     * @param view
     */
    public void submitProposal(View view) {
        //Get the author's name
        EditText authorField = (EditText) findViewById(R.id.userInput_author);
        Editable authorEditable = authorField.getText();
        String authorName = authorEditable.toString();

        //Get the title of the short story
        EditText titleField = (EditText) findViewById(R.id.userInput_title);
        Editable titleEditable = titleField.getText();
        String title = titleEditable.toString();

        //Get the URL for the short story
        EditText urlField = (EditText) findViewById(R.id.userInput_url);
        Editable urlEditable = urlField.getText();
        String url = urlEditable.toString();

        //Next we'll check is the user has entered valid input; the author and title field must
        //contain input; while the URL must be valid only if the field contains any input
        if (TextUtils.isEmpty(authorName))
            //We'll send a toast message is something is wrong with the input
            Toast.makeText(this, "Author field can't be empty", Toast.LENGTH_LONG).show();
        else {
            if (TextUtils.isEmpty(title))
                Toast.makeText(this, "Title field can't be empty", Toast.LENGTH_LONG).show();
            else {
                if (!TextUtils.isEmpty(url) && !URLUtil.isValidUrl(url))
                    Toast.makeText(this, "Please enter a valid URL", Toast.LENGTH_LONG).show();
                else {
                    //Create the proposal summary that will be displayed in the e-mail body message
                    String summary = createProposalSummary(authorName, title, url);

                    //We'll use an intent to launch an e-mail app
                    //The summary will be in the e-mail body
                    Intent emailItent = new Intent(Intent.ACTION_SENDTO);
                    emailItent.setData(Uri.parse("mailto:"));
                    emailItent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.proposal_email_subject));
                    emailItent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.proposal_email_address)});
                    emailItent.putExtra(Intent.EXTRA_TEXT, summary);
                    if (emailItent.resolveActivity(getPackageManager()) != null)
                        startActivity(emailItent);
                }
            }
        }
    }

    /**
     * This method will return the summary of the proposal
     */
    private String createProposalSummary(String author, String title, String url) {
        String summary = getResources().getString(R.string.author) + ": " + author;
        summary += "\n" +getResources().getString(R.string.title) + ": " + title;

        //If the URL field was left blank, we'll need to specify that in the summary
        if (TextUtils.isEmpty(url))
            summary += "\n" + getResources().getString(R.string.url) + ": [Blank]";
        else
            summary += "\n"  + getResources().getString(R.string.url) + ": " + url;
        return summary;

    }
}
