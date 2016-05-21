/**
 * Odoo, Open Source Management Solution
 * Copyright (C) 2012-today Odoo SA (<http:www.odoo.com>)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details
 * <p/>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http:www.gnu.org/licenses/>
 * <p/>
 * Created on 31/12/14 11:29 AM
 */
package com.odoo.core.orm.fields.types;

import com.odoo.core.utils.ODateUtils;

public class ODate extends OTypeHelper {
    public static final String TAG = ODate.class.getSimpleName();

    @Override
    public String getFieldType() {
        return "VARCHAR";
    }

    @Override
    public String getDataFormat() {
        return ODateUtils.DEFAULT_DATE_FORMAT;
    }


}
