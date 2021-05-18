package org.hotel.models;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface IModel {
    public HashMap<String, String> rules();
    public HashMap getErrors();
    public boolean hasErrors();
}

public abstract class Model implements IModel {
    private HashMap<String, String> errors = new HashMap<String, String>();

    public boolean validate() throws NoSuchFieldException, IllegalAccessException {
        HashMap rules = this.rules();
        for(Object i : rules.keySet()) {
            String[] types = ((String)rules.get(i)).split("|");
            for(int k=0; k < types.length; k++) {
                check(types[k],(String) i);
            }
        }
        return errors.size() == 0;
    }

    public HashMap getErrors() {
        return errors;
    }
    public boolean hasErrors() {
        return errors.size() != 0;
    }

    private Object getValueOf(String attr) throws NoSuchFieldException, IllegalAccessException {
        Field field = getClass().getField(attr);
        Class dataType = field.getType();

        if (dataType.toString().equals("double")) {
            return field.getDouble(this);
        } else if (dataType.toString().equals("int")) {
            return field.getInt(this);
        } else if (dataType.toString().equals("string")) {
            return field.toString();
        }
        return null;
    }

    private boolean check(String type, String attr) throws NoSuchFieldException, IllegalAccessException {
        Object attrValue = getValueOf(attr);

        switch (type) {
            case "required":
                if(attr == null || attr.length() == 0) {
                    errors.put(attr, "This field is required");
                    return  false;
                }
            case "string":
                return attr.matches("^[A-Za-z0-9]+$");
            case "number":
                return attr.matches("^[0-9]+$");
            case "email":
                Pattern EMAIL_REGEX =
                        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = EMAIL_REGEX.matcher(attr);
                return matcher.find();
            default:
                String[] parts;
                if(attr != null && attr.contains("min")) {
                    parts = attr.split(":");
                    if(parts.length != 2) {
                        errors.put(attr, "Min length error");  return false;
                    } else if (attr.length() < Integer.valueOf(parts[1])) {
                        errors.put(attr, "Minimum length should be " + parts[1]); return false;
                    }
                }

                if(attr != null && attr.contains("max")) {
                    parts = attr.split(":");
                    if(parts.length != 2) {
                        errors.put(attr, "Max length error");  return false;
                    } else if (attr.length() > Integer.valueOf(parts[1])) {
                        errors.put(attr, "Maximum length should be " + parts[1]); return false;
                    }
                }
                // All valid
                return true;
        }
    }
}
