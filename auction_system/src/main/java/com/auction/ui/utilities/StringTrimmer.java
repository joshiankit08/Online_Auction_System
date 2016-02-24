package com.auction.ui.utilities;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * StringTrimmer Converter to trim the text fields .
 */
@FacesConverter("com.auction.ui.utilities.StringTrimmer")
public class StringTrimmer implements Converter{
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value != null ? value.trim() : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return (String) value;
    }
}
