package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.android.inventoryapp.data.ItemsContract.StoreDataEntry;

/**
 * Created by hp on 01-Jun-17.
 */

public class EditionActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EXISTING_LOADER = 0;
    private Uri CurrentStoreUri;
    private EditText NameEditText;
    private EditText QuantityEditText;
    private EditText PriceEditText;
    private EditText RetailerEditText;
    private int nID = 0;
    private String ImageId = "";
    private String ProductName;
    private String RetailerName;
    private int quantity=0;
    private int productPrice;
    private ImageView mainImage;
    private boolean StoreHasChanged = false;
    private View.OnTouchListener TouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            StoreHasChanged = true;
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edition);
        Intent intent = getIntent();
        CurrentStoreUri = intent.getData();
        if (CurrentStoreUri == null) {
            setTitle(getString(R.string.edition_activity_title_page1));

        } else {
            setTitle(getString(R.string.edition_activity_title_page2));
            getLoaderManager().initLoader(EXISTING_LOADER, null, this);
        }
        invalidateOptionsMenu();
        NameEditText = (EditText) findViewById(R.id.text1);
        QuantityEditText = (EditText) findViewById(R.id.text2);
        PriceEditText = (EditText) findViewById(R.id.text3);
        RetailerEditText = (EditText) findViewById(R.id.text4);
        mainImage = (ImageView) findViewById(R.id.item_img);
        NameEditText.setOnTouchListener(TouchListener);
        QuantityEditText.setOnTouchListener(TouchListener);
        PriceEditText.setOnTouchListener(TouchListener);
        RetailerEditText.setOnTouchListener(TouchListener);
    }

    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Toast.makeText(this, "Uploading Image...", Toast.LENGTH_LONG).show();
            Uri selectedImageUri = data.getData();
            try {
                Log.v("Path:", selectedImageUri.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ImageView imageView = (ImageView) findViewById(R.id.item_img);
                imageView.setImageBitmap(bitmap);
                Log.v("TAG NOTE:", "Product before created, ID: " + nID);
                String filename = Long.toString(nID);
                Log.v("Image path: ", filename);
                saveToInternalStorage(bitmap, filename);
                nID++;
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToInternalStorage(Bitmap bmp, String filename) {
        ContextWrapper contextwrapper = new ContextWrapper(getApplicationContext());
        File appDirectory = contextwrapper.getFilesDir();
        File currentPath = new File(appDirectory, filename);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(currentPath);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            ImageId = filename;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Submit(View view) {

        String subject = "ITEMS OUT OF STOCK";
        String message = "ITEM: " + NameEditText.getText().toString() +
                "\nQuantity Needed: " + QuantityEditText.getText().toString();
        Log.v("Message:", message);
        String[] emails = {RetailerEditText.getText().toString() + "@sidshubita.co.in"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, emails);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_msg);
        builder.setPositiveButton(R.string.discard_changes, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edition_activity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (CurrentStoreUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_remove);
            menuItem.setVisible(false);
        }
        return true;
    }
    public void Increment(View view) {

        String quantityString=QuantityEditText.getText().toString().trim();
        if(TextUtils.isEmpty(quantityString))
        {
            Toast.makeText(getApplicationContext(), "Must enter an Integer value", Toast.LENGTH_LONG).show();
        }
        else {
            quantity=Integer.parseInt(quantityString);
            quantity++;
            show();
        }
    }
    public void Decrement(View view) {
        if(quantity>0)
            quantity--;
        show();
    }
    private void show() {
        QuantityEditText.setText(Integer.toString(quantity));
    }
    private void removeItem() {
        if (CurrentStoreUri != null) {
            int rowsDeleted = getContentResolver().delete(CurrentStoreUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.remove_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.remove_item_sucess),
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message);
        builder.setPositiveButton(R.string.Remove, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeItem();
            }
        });
        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveItem() {
        String nameString = NameEditText.getText().toString().trim();
        String quantityString = QuantityEditText.getText().toString().trim();
        String priceString = PriceEditText.getText().toString().trim();
        String retailerString = RetailerEditText.getText().toString().trim();
        if(TextUtils.isEmpty(nameString))
        {
            Toast.makeText(getApplicationContext(), "Enter Product Name", Toast.LENGTH_LONG).show();
        } else if(TextUtils.isEmpty(quantityString))
        {
            Toast.makeText(getApplicationContext(), "Enter Quantity", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(priceString))
        {
            Toast.makeText(getApplicationContext(), "Enter Price", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(retailerString))
        {
            Toast.makeText(getApplicationContext(), "Enter Retailer Name", Toast.LENGTH_LONG).show();
        }else if (mainImage.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Upload an image", Toast.LENGTH_LONG).show();
        }
        else {
            ContentValues values = new ContentValues();
            values.put(StoreDataEntry.COLUMN_ITEM_NAME, nameString);
            if (!TextUtils.isEmpty(quantityString)) {
                quantity = Integer.parseInt(quantityString);
            }
            values.put(StoreDataEntry.COLUMN_ITEM_QUANTITY, quantityString);
            if (!TextUtils.isEmpty(priceString)) {
                productPrice = Integer.parseInt(priceString);
            }
            values.put(StoreDataEntry.COLUMN_ITEM_PRICE, priceString);
            values.put(StoreDataEntry.COLUMN_ITEM_RETAILER, retailerString);
            values.put(StoreDataEntry.COLUMN_ITEM_IMAGE, ImageId);
            if (CurrentStoreUri == null) {
                Uri uri = getContentResolver().insert(StoreDataEntry.CONTENT_URI, values);
                if (uri == null) {
                    Toast.makeText(this, getString(R.string.insert_item_fail_msg),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.insert_item_sucessfull),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                int rowsAffected = getContentResolver().update(CurrentStoreUri, values, null, null);
                if (rowsAffected == 0) {
                    Toast.makeText(this, getString(R.string.update_item_failed_msg),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.update_item_sucess_msg),
                            Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveItem();
                return true;
            case R.id.action_remove:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!StoreHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditionActivity.this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditionActivity.this);
                            }
                        };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!StoreHasChanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                StoreDataEntry._ID,
                StoreDataEntry.COLUMN_ITEM_NAME,
                StoreDataEntry.COLUMN_ITEM_QUANTITY,
                StoreDataEntry.COLUMN_ITEM_PRICE,
                StoreDataEntry.COLUMN_ITEM_RETAILER,
                StoreDataEntry.COLUMN_ITEM_IMAGE
        };
        return new CursorLoader(this,
                CurrentStoreUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(StoreDataEntry.COLUMN_ITEM_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(StoreDataEntry.COLUMN_ITEM_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(StoreDataEntry.COLUMN_ITEM_PRICE);
            int retailerColumnIndex = cursor.getColumnIndex(StoreDataEntry.COLUMN_ITEM_RETAILER);
            int imageColumnIndex = cursor.getColumnIndex(StoreDataEntry.COLUMN_ITEM_IMAGE);
            ProductName = cursor.getString(nameColumnIndex);
            quantity = cursor.getInt(quantityColumnIndex);
            productPrice = cursor.getInt(priceColumnIndex);
            RetailerName = cursor.getString(retailerColumnIndex);
            ImageId = cursor.getString(imageColumnIndex);
            NameEditText.setText(ProductName);
            QuantityEditText.setText(Integer.toString(quantity));
            PriceEditText.setText(Integer.toString(productPrice));
            ContextWrapper cw = new ContextWrapper(this);
            File dir = cw.getFilesDir();
            String imageLocationDir = dir.toString();
            String imagePath = imageLocationDir + "/" + ImageId;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mainImage.setImageBitmap(bitmap);
            RetailerEditText.setText(RetailerName);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        NameEditText.setText("");
        QuantityEditText.setText("");
        PriceEditText.setText("");
        RetailerEditText.setText("");
    }
}

