package com.odoo.addons.Risk.models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.odoo.base.addons.res.ResPartner;
import com.odoo.core.orm.OModel;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.ODate;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.core.support.OUser;

public class RiskAsessmentmodel extends OModel {
    public static final String TAG = RiskAsessmentmodel.class.getSimpleName();

    public static final String AUTHORITY = "com.odoo.addons.Risk.isg_risk_assesment";
    OColumn name = new OColumn("name", OVarchar.class).setName("name");
    OColumn partner_id = new OColumn("partner_id", ResPartner.class, OColumn.RelationType.ManyToOne);
    OColumn assesment_method = new OColumn("assesment_method ", RiskAsessmentmodel.class, OColumn.RelationType.ManyToOne);
   // OColumn procedure_id = new OColumn("procedure_id", RiskAsessmentmodel.class, OColumn.RelationType.ManyToOne);
    OColumn subject = new OColumn("subject", OVarchar.class).setName("subject");
    OColumn date_performed = new OColumn("date_performed", ODate.class);
    OColumn date_valid_before = new OColumn("date_valid_before", ODate.class);
    OColumn date_updated = new OColumn("date_updated", ODate.class);

    public RiskAsessmentmodel(Context context, OUser user) {
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
