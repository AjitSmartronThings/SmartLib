package com.things.smartcam.validator;

import android.os.Build;
import android.util.Patterns;

import java.util.regex.Pattern;

public class IpAddressValidator extends PatternValidator {
    public IpAddressValidator(String _customErrorMessage) {
        super(_customErrorMessage, Build.VERSION.SDK_INT >= 8 ? Patterns.IP_ADDRESS : Pattern.compile(
                "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                        + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                        + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                        + "|[1-9][0-9]|[0-9]))"));
    }

    public static Boolean isIP(String input) {
        if (input!= null && !input.isEmpty()) {
            //Regex
            String regex = "^((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)(\\.((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)){3}$";
            //check if input matches the regex
            if (input.matches(regex)) {
                // valid ip
                return true;
            } else {
                // invalid ip
                return false;
            }
        }
        // invalid input
        return false;
    }
}


