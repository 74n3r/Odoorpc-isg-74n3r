package com.odoo.addons.Risk.models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.odoo.base.addons.res.ResPartner;
import com.odoo.core.orm.OModel;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.core.support.OUser;

public class RiskAsessment_procedure_model extends OModel {
    public static final String TAG = RiskAsessment_procedure_model.class.getSimpleName();

    public static final String AUTHORITY = "com.odoo.addons.Risk.isg_risk_assesment_procedure";
    OColumn name = new OColumn("name", OVarchar.class).setName("name");
    OColumn partner_id = new OColumn("partner_id", ResPartner.class, OColumn.RelationType.ManyToOne);


    public RiskAsessment_procedure_model(Context context, OUser user) {
        super(context, "isg.risk_assesment.procedure", user);
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
