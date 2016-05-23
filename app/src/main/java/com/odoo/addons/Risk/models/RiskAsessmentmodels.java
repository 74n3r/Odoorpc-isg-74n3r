package com.odoo.addons.Risk.models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.odoo.core.orm.OModel;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.core.support.OUser;

public class RiskAsessmentmodels extends OModel {
    public static final String TAG = RiskAsessmentmodels.class.getSimpleName();

    

    public static final String AUTHORITY = "com.odoo.addons.Risk.isg_risk_assesment";

    OColumn name = new OColumn("name", OVarchar.class).setName("name");
    OColumn assesment_method = new OColumn("assesment_method", OVarchar.class).setName("assesment_method");

    public RiskAsessmentmodels(Context context, OUser user) {

        //burayi degistirdim
        super(context, "isg.risk_assesment", user);
        setHasMailChatter(true);
    }


    @Override
    public Uri uri() {
        return buildURI(AUTHORITY);
    }


    @Override
    public void onSyncStarted() {
        super.onSyncStarted();
        Log.i(TAG, "onSyncStarted");
    }

    @Override
    public void onSyncFinished() {
        super.onSyncFinished();
        Log.i(TAG, "onSyncFinished");
    }


}
