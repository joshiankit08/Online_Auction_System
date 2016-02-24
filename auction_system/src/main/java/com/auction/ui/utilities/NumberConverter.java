package com.auction.ui.utilities;

import java.text.NumberFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
/**
 * Converter used to convert String to Number object and vice versa.
 *
 */
@FacesConverter("numberConverter")
public class NumberConverter implements Converter 
{
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) 
	{
		if (submittedValue.trim().equals("")) 
		{
			return null;
		} 
		else 
		{
			try 
			{
				NumberFormat format = NumberFormat.getInstance();
				return format.parse(submittedValue);

			} 
			catch (Exception exception) 
			{
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Conversion Error",
						"Not a valid number"));
			}
		}

	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) 
	{
		if (value == null || value.equals("")) 
		{
			return "";
		} 
		else
		{
			return ((Number)value).toString();
		}
	}
}

