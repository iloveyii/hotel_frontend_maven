package org.hotel.models;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
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
        for(Object field : rules.keySet()) {
            String[] arrRules = ((String)rules.get(field)).split("\\|");
            for(int k=0; k < arrRules.length; k++) {
                if(check(arrRules[k],(String) field) == false) {
                    break;
                };
            }
        }
        return errors.size() == 0;
    }

    public HashMap getErrors() {
        return errors;
    }
    public String getStringErrors() {
        String strErrors = "";
        for(String i: errors.keySet()) {
            strErrors += String.format("%s : %s\n", i.toUpperCase(Locale.ROOT), errors.get(i));
        }
        return strErrors;
    }
    public boolean hasErrors() {
        return errors.size() != 0;
    }

    private String getValueOf(String attr) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends Model> c = this.getClass();
        Field f1 = c.getDeclaredField(attr);
        f1.setAccessible(true);
        String t = f1.getType().toString();
        if(f1.getType().toString().equals("double")) {
            return String.valueOf(f1.getDouble(this));
        }
        return (String) f1.get(this);
    }

    private boolean check(String rule, String attr) throws NoSuchFieldException, IllegalAccessException {
        Object attrValue = getValueOf(attr);
        String strAttrValue = (String) attrValue;

        switch (rule) {
            case "required":
                if(strAttrValue == null || strAttrValue.length() == 0) {
                    errors.put(attr, "This field is required");
                    return  false;
                }
                break;
            case "string":
                if(strAttrValue.matches("^[A-Za-z0-9]+$") == false) {
                    errors.put(attr, "This field should contain only string values."); return  false;
                };
                break;
            case "number":
                if(strAttrValue.matches("^[0-9.]+$") == false) {
                    errors.put(attr, "This field should contain only numeric values."); return  false;
                }
                break;
            case "email":
                Pattern EMAIL_REGEX =
                        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = EMAIL_REGEX.matcher(strAttrValue);
                if(matcher.find()==false){
                    errors.put(attr, "This field should be a valid email."); return  false;
                }
                break;
            default:
                String[] parts;
                if(strAttrValue != null && rule.contains("min")) {
                    parts = rule.split(":");
                    if(parts.length != 2) {
                        errors.put(attr, "Min length error");  return false;
                    } else if (strAttrValue.length() < Integer.valueOf(parts[1])) {
                        errors.put(attr, "Minimum length should be " + parts[1]); return false;
                    }
                }

                if(strAttrValue != null && rule.contains("max")) {
                    parts = rule.split(":");
                    if(parts.length != 2) {
                        errors.put(strAttrValue, "Max length error");  return false;
                    } else if (strAttrValue.length() > Integer.valueOf(parts[1])) {
                        errors.put(attr, "Maximum length should be " + parts[1]); return false;
                    }
                }
                // All valid
                return true;
        }
        return true;
    }
}
