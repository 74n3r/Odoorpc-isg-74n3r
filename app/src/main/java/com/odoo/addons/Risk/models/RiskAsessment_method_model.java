package com.odoo.addons.Risk.models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.odoo.core.orm.OModel;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.OSelection;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.core.support.OUser;

public class RiskAsessment_method_model extends OModel {
    public static final String TAG = RiskAsessment_method_model.class.getSimpleName();

    public static final String AUTHORITY = "com.odoo.addons.Risk.isg_risk_assesment_method";
    OColumn name = new OColumn("name", OVarchar.class).setName("name");
   // OColumn partner_id = new OColumn("partner_id", ResPartner.class, OColumn.RelationType.ManyToOne);
    //OColumn assesment_method = new OColumn("assesment_method", RiskAsessment_method_model.class, OColumn.RelationType.ManyToOne);
    OColumn type=new OColumn("type", OSelection.class)
    .addSelection("lmatris","L Matris")
    .addSelection("fine_kinney","Fine Kinney");



    public RiskAsessment_method_model(Context context, OUser user) {
        super(context, "isg.risk_assesment.method", user);
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
