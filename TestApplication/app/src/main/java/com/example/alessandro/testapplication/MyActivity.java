package com.example.alessandro.testapplication;

import android.app.Activity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class MyActivity extends Activity implements TabListener {

    public final static String EXTRA_MESSAGE = "com.example.alessandro.testApplication.MESSAGE";
    public final ImageView imageView=(ImageView)findViewById(R.id.foto);
    public final static int REQUEST_IMAGE_CAPTURE=1;
    public File photoFile;
    ArrayList <Fragment> fragList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*   setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/

        //creo l'actionbar e setto i navigation tabs
        TestDBOpenHelper testDBOpenHelper=new TestDBOpenHelper(this);
        testDBOpenHelper.getWritableDatabase();
         ActionBar bar=getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //setto i text per i diversi navigation tabs
        for(int i=1;i<=3;i++) {
            Tab tab=bar.newTab();
            if(i==1) {
                tab.setText("NUOVA PARTITA");
            }
            else
            {
                if(i==2)
                {
                    tab.setText("CLASSIFICA");
                }
                else
                {
                    tab.setText("CREDITS");
                }
            }
            tab.setTabListener(this);
            bar.addTab(tab);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

  /*  @Override
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
*/
    /**
     * A placeholder fragment containing a simple view.
     */
   /* public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            return rootView;
        }
    }*/

    public void sendNewMessage(View view)
    {

        Intent intent=new Intent(this,DisplayMessageActivity.class);
        EditText editText=(EditText) findViewById(R.id.insert_name); //dichiaro un edittext prendendo quello dell'activity
        String message= editText.getText().toString().concat(((EditText) findViewById(R.id.insert_surname)).getText().toString()); //cosi riesco a prendere il testo dall'edittext della prima acctivity
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }








       @Override
        public void onTabSelected(ActionBar.Tab tab,FragmentTransaction ft)
        {
            Fragment f=null;
            TabFragment tabFragment=null;

            if (fragList.size()> tab.getPosition())
                fragList.get(tab.getPosition());
            if (f==null)
            {
                tabFragment=new TabFragment();
                Bundle data=new Bundle();
                data.putInt("idx",tab.getPosition());
                tabFragment.setArguments(data);
                fragList.add(tabFragment);
            }
            else
            {
                tabFragment= (TabFragment) f;

            }
            ft.replace(android.R.id.content,tabFragment);
        }
    @Override
        public void onTabUnselected(ActionBar.Tab tab,FragmentTransaction ft)
        {
            if(fragList.size()>tab.getPosition())
            ft.remove(fragList.get(tab.getPosition()));
        }
    @Override
        public void onTabReselected(ActionBar.Tab tab,FragmentTransaction ft)
        {

        }

    public void openCamera(View view) {
        File photoStorage=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        photoFile=new File(photoStorage,(System.currentTimeMillis())+".jpg");

        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); //mi fa si che alla mia activity ritorni qualcosa in questo casol'immagine


    }

    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if((requestCode==REQUEST_IMAGE_CAPTURE)&&(resultCode==RESULT_OK))
        {
            Bitmap bitmap= null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(photoFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
           /* ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);*/
            this.getResizedBitmap(bitmap,20,20);
            ImageView imageView=(ImageView)findViewById(R.id.foto);
            imageView.setImageBitmap(bitmap);



        }
    }

    public Bitmap getResizedBitmap(Bitmap bitmap,int newHeight, int newWidth)
    {
         int width=bitmap.getWidth();
         int height=bitmap.getHeight();
         float scaleHeight=((float)newHeight)/height;
         float scaleWidth=((float)newWidth)/width;
         //creo una matrice cosi uso postscale e riduco la matrice e adatto il bitmap alla matrice
        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap resizedBitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,false);
        return resizedBitmap;



    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.getParcelable("bitmap");
    }
}

