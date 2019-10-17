/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;

public class MySpinner extends DefaultFormatter 
{

    public MySpinner(JSpinner spin) {
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMaximumFractionDigits(20);
        numberInstance.setMinimumFractionDigits(0);
        numberInstance.setRoundingMode(RoundingMode.DOWN);
        JFormattedTextField.AbstractFormatter format = new NumberFormatFormatter(numberInstance);
        DefaultFormatterFactory dff = new DefaultFormatterFactory(format);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spin, "0.0#####");
        JFormattedTextField textField = editor.getTextField();
        textField.setFormatterFactory(dff);
        textField.setColumns(10);
        spin.setEditor(editor);
    }
    
    public class NumberFormatFormatter extends DefaultFormatter {

        private NumberFormat format;

        public NumberFormatFormatter(NumberFormat format) {
            this.format = format;
        }

        @Override
        public Object stringToValue(String string) throws ParseException {
            return format.parse(string);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            String text = format.format(value);
            return text;
        }
    }
}