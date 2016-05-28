package com.odoo.addons.Risk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.odoo.App;
import com.odoo.R;
import com.odoo.addons.Risk.models.RiskAsessmentmodel;
import com.odoo.base.addons.ir.feature.OFileManager;
import com.odoo.core.orm.ODataRow;
import com.odoo.core.orm.OModel;
import com.odoo.core.orm.OValues;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.support.OdooCompatActivity;
import com.odoo.core.utils.IntentUtils;
import com.odoo.core.utils.OAlert;
import com.odoo.core.utils.OAppBarUtils;
import com.odoo.core.utils.OResource;
import com.odoo.core.utils.OStringColorUtil;
import com.odoo.widgets.parallax.ParallaxScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import odoo.controls.OField;
import odoo.controls.OForm;
import odoo.helper.OdooFields;
import odoo.helper.utils.gson.OdooResult;

public class RiskassesmentDetails extends OdooCompatActivity
        implements View.OnClickListener, OField.IOnFieldValueChangeListener {
    public static final String TAG = RiskassesmentDetails.class.getSimpleName();
    public static String KEY_PARTNER_TYPE = "partner_type";
    private final String KEY_MODE = "key_edit_mode";
    private final String KEY_NEW_IMAGE = "key_new_image";
    private ActionBar actionBar;
    private Bundle extras;
    private RiskAsessmentmodel RiskAsessmentmodel;
    private ODataRow record = null;
    private ParallaxScrollView parallaxScrollView;
    private ImageView userImage = null, captureImage = null;
    private TextView mTitleView = null;
    private OForm mForm;
    private App app;
    private Boolean mEditMode = false;
    private Menu mMenu;
    private OFileManager fileManager;
    private String newImage = null;
    private RiskAsessments.Type partnerType = RiskAsessments.Type.Riskassesment;

    public List<String> list_parent;
    public acilir_liste_modul expand_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view;
    public List<String> action_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riskassesment_detail);

       expandlist_view = (ExpandableListView)findViewById(R.id.expandableListView);

        Hazirla();


        expand_adapter = new acilir_liste_modul(getApplicationContext(), list_parent, list_child);
        expandlist_view.setAdapter(expand_adapter);
        expandlist_view.setClickable(true);

        expandlist_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String child_name = (String)expand_adapter.getChild(groupPosition, childPosition);
                AlertDialog.Builder builder = new AlertDialog.Builder(RiskassesmentDetails.this);
                builder.setMessage(child_name)
                        .setTitle("Deneme isg liste")
                        .setCancelable(false)
                        .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


                return false;
            }
        });




        OAppBarUtils.setAppBar(this, false);
        fileManager = new OFileManager(this);
        actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("");
        if (savedInstanceState != null) {
            mEditMode = savedInstanceState.getBoolean(KEY_MODE);
           // newImage = savedInstanceState.getString(KEY_NEW_IMAGE);
        }
        app = (App) getApplicationContext();
        parallaxScrollView = (ParallaxScrollView) findViewById(R.id.parallaxScrollView);
        parallaxScrollView.setActionBar(actionBar);
       // userImage = (ImageView) findViewById(android.R.id.icon);
        mTitleView = (TextView) findViewById(android.R.id.title);
        RiskAsessmentmodel = new RiskAsessmentmodel(this, null);
        extras = getIntent().getExtras();
        if (extras != null)
            partnerType = RiskAsessments.Type.valueOf(extras.getString(KEY_PARTNER_TYPE));
        if (!hasRecordInExtra())
            mEditMode = true;
        setupActionBar();
    }

    private boolean hasRecordInExtra() {
        return extras != null && extras.containsKey(OColumn.ROW_ID);
    }

    private void setMode(Boolean edit) {
        if (mMenu != null) {
            mMenu.findItem(R.id.menu_risk_detail_more).setVisible(!edit);
            mMenu.findItem(R.id.menu_risk_edit).setVisible(!edit);
            mMenu.findItem(R.id.menu_risk_save).setVisible(edit);
            mMenu.findItem(R.id.menu_risk_cancel).setVisible(edit);
        }
        int color = Color.DKGRAY;
        if (record != null) {
            color = OStringColorUtil.getStringColor(this, record.getString("name"));
        }
        if (edit) {
            if (hasRecordInExtra()) {
                actionBar.setTitle(R.string.label_edit);
            } else
                actionBar.setTitle(R.string.label_new);
            actionBar.setBackgroundDrawable(new ColorDrawable(color));
            mForm = (OForm) findViewById(R.id.riskFormEdit);
          //  captureImage = (ImageView) findViewById(R.id.captureImage);
           // captureImage.setOnClickListener(this);
          //  userImage = (ImageView) findViewById(android.R.id.icon1);
            findViewById(R.id.parallaxScrollView).setVisibility(View.GONE);
            findViewById(R.id.riskScrollViewEdit).setVisibility(View.VISIBLE);
            OField is_company = (OField) findViewById(R.id.is_company_edit);
            is_company.setOnValueChangeListener(this);
        } else {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_shade));
          //  userImage = (ImageView) findViewById(android.R.id.icon);
            mForm = (OForm) findViewById(R.id.riskForm);
            findViewById(R.id.riskScrollViewEdit).setVisibility(View.GONE);
            findViewById(R.id.parallaxScrollView).setVisibility(View.VISIBLE);
        }
        setColor(color);
    }

    private void setupActionBar() {
        if (!hasRecordInExtra()) {
            setMode(mEditMode);
            mForm.setEditable(mEditMode);
            mForm.initForm(null);
        } else {
            int rowId = extras.getInt(OColumn.ROW_ID);
            record = RiskAsessmentmodel.browse(rowId);

            checkControls();
            setMode(mEditMode);
            mForm.setEditable(mEditMode);
            mForm.initForm(record);
            mTitleView.setText(record.getString("name"));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subject:
                IntentUtils.redirectToMap(this, record.getString("subject"));
           /*     break;


            case R.id.website:
                IntentUtils.openURLInBrowser(this, record.getString("website"));
                break;
            case R.id.email:
                IntentUtils.requestMessage(this, record.getString("email"));
                break;
            case R.id.phone_number:
                IntentUtils.requestCall(this, record.getString("phone"));
                break;
            case R.id.mobile_number:
                IntentUtils.requestCall(this, record.getString("mobile"));
                break;
            case R.id.captureImage:
                fileManager.requestForFile(OFileManager.RequestType.IMAGE_OR_CAPTURE_IMAGE);
                break;
                */
        }
    }

    private void checkControls() {
        findViewById(R.id.subject).setOnClickListener(this);
      //  findViewById(R.id.full_address).setOnClickListener(this);
      //  findViewById(R.id.website).setOnClickListener(this);
     //   findViewById(R.id.email).setOnClickListener(this);
      //  findViewById(R.id.phone_number).setOnClickListener(this);
       // findViewById(R.id.mobile_number).setOnClickListener(this);
    }



    private void setColor(int color) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.parallax_view);
        frameLayout.setBackgroundColor(color);
        parallaxScrollView.setParallaxOverLayColor(color);
        parallaxScrollView.setBackgroundColor(color);
        mForm.setIconTintColor(color);
        findViewById(R.id.parallax_view).setBackgroundColor(color);
        findViewById(R.id.parallax_view_edit).setBackgroundColor(color);
        findViewById(R.id.riskScrollViewEdit).setBackgroundColor(color);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_risk_save:
                OValues values = mForm.getValues();
                if (values != null) {
                    switch (partnerType) {
                        case Supplier:

                            values.put("Riskassesment", "true");
                            break;
                        default:
                            values.put("Riskassesment", "true");
                            break;
                    }

                    if (record != null) {
                        RiskAsessmentmodel.update(record.getInt(OColumn.ROW_ID), values);
                        Toast.makeText(this, R.string.toast_information_saved, Toast.LENGTH_LONG).show();
                        mEditMode = !mEditMode;
                        setupActionBar();
                    } else {
                        final int row_id = RiskAsessmentmodel.insert(values);
                        if (row_id != OModel.INVALID_ROW_ID) {
                            finish();
                        }
                    }
                }
                break;
            case R.id.menu_risk_cancel:
                if (record == null) {
                    finish();
                    return true;
                }
            case R.id.menu_risk_edit:
                mEditMode = !mEditMode;
                setMode(mEditMode);
                mForm.setEditable(mEditMode);
                mForm.initForm(record);

                break;
            case R.id.Risks:
                Toast.makeText(RiskassesmentDetails.this, "Risklere Gidiyorsun", Toast.LENGTH_SHORT).show();
                break;


            case R.id.menu_risk_delete:
                OAlert.showConfirm(this, OResource.string(this,
                        R.string.confirm_are_you_sure_want_to_delete),
                        new OAlert.OnAlertConfirmListener() {
                            @Override
                            public void onConfirmChoiceSelect(OAlert.ConfirmType type) {
                                if (type == OAlert.ConfirmType.POSITIVE) {
                                    // Deleting record and finishing activity if success.
                                    if (RiskAsessmentmodel.delete(record.getInt(OColumn.ROW_ID))) {
                                        Toast.makeText(RiskassesmentDetails.this, R.string.toast_record_deleted,
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            }
                        });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_riskassetment_detail, menu);
        mMenu = menu;
        setMode(mEditMode);
        return true;
    }

    @Override
    public void onFieldValueChange(OField field, Object value) {
        if (field.getFieldName().equals("is_company")) {
            Boolean checked = Boolean.parseBoolean(value.toString());
            int view = (checked) ? View.GONE : View.VISIBLE;
            findViewById(R.id.parent_id).setVisibility(view);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_MODE, mEditMode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OValues values = fileManager.handleResult(requestCode, resultCode, data);
        if (values != null && !values.contains("size_limit_exceed")) {

        } else if (values != null) {
            Toast.makeText(this, R.string.toast_image_size_too_large, Toast.LENGTH_LONG).show();
        }
    }

    private class BigImageLoader extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {
            String image = null;
            try {
                Thread.sleep(300);
                OdooFields fields = new OdooFields();
                fields.addAll(new String[]{"image_medium"});
                OdooResult record = RiskAsessmentmodel.getServerDataHelper().read(null, params[0]);
                if (record != null && !record.getString("image_medium").equals("false")) {
                    image = record.getString("image_medium");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return image;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                if (!result.equals("false")) {
                    OValues values = new OValues();
                    values.put("large_image", result);
                    RiskAsessmentmodel.update(record.getInt(OColumn.ROW_ID), values);
                    record.put("large_image", result);

                }
            }
        }








    }







   public void Hazirla()
    {
        list_parent = new ArrayList<String>();
        list_child = new HashMap<String, List<String>>();

        list_parent.add("Action");

        action_list = new ArrayList<String>();

        action_list.add("Delete");
        action_list.add("Duplicate");

        list_child.put(list_parent.get(0),action_list);


    }



}
