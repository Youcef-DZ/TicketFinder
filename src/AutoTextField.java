import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.ItemEvent;
import static java.awt.event.ItemEvent.SELECTED;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.List;

/*
This file was produced by MathiasLoewe
https://github.com/MathiasLoewe/Awesomemap/blob/master/src/gui/AutoTextField.java

I have edited it to suit my needs on this App
*/

class AutoTextField extends JTextField {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<Object> dataList;
    private boolean isCaseSensitive;
    private boolean isStrict;
    private AutoComboBox autoComboBox;

    AutoTextField(List<Object> airportsList, AutoComboBox b) {
        isCaseSensitive = false;
        isStrict = true;
        autoComboBox = null;
        if (airportsList == null) {
            throw new IllegalArgumentException("values can not be null");
        } else {
            dataList = airportsList;
            autoComboBox = b;
            init();
        }
    }

    private void init() {
        setDocument(new AutoDocument());

    }

    private String getMatch(String s) {
        for (Object aDataList : dataList) {
            String s1 = aDataList.toString();
            if (s1 != null) {
                if (!isCaseSensitive && s1.toLowerCase().startsWith(s.toLowerCase()))
                    return s1;
                if (isCaseSensitive && s1.startsWith(s))
                    return s1;
            }
        }

        return null;
    }

    @Override
    public void replaceSelection(String s) {
        AutoDocument _lb = (AutoDocument) getDocument();
        if (_lb != null)
            try {
                int i = min(getCaret().getDot(), getCaret().getMark());
                int j = max(getCaret().getDot(), getCaret().getMark());
                _lb.replace(i, j - i, s, null);
            } catch (BadLocationException ignored) {
            }
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void setCaseSensitive(boolean flag) {
        isCaseSensitive = flag;
    }

    public boolean isStrict() {
        return isStrict;
    }

    public void setStrict(boolean flag) {
        isStrict = flag;
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> list) {
        if (list == null) {
            throw new IllegalArgumentException("values can not be null");
        } else {
            dataList = list;
        }
    }

    class AutoDocument extends PlainDocument {

        private static final long serialVersionUID = 1L;

        @Override
        public void replace(int i, int j, String s, AttributeSet attributeset) throws BadLocationException {
            super.remove(i, j);
            insertString(i, s, attributeset);
        }

        @Override
        public void insertString(int i, String s, AttributeSet attributeset) throws BadLocationException {
            if (s == null || "".equals(s))
                return;
            String s1 = getText(0, i);
            String s2 = getMatch(s1 + s);
            int j = (i + s.length()) - 1;
            if (isStrict && s2 == null) {
                s2 = getMatch(s1);
                j--;
            } else if (!isStrict && s2 == null) {
                super.insertString(i, s, attributeset);
                return;
            }
            if (autoComboBox != null && s2 != null)
                autoComboBox.setSelectedValue(s2);
            super.remove(0, getLength());
            super.insertString(0, s2, attributeset);
            setSelectionStart(j + 1);
            setSelectionEnd(getLength());
        }

        @Override
        public void remove(int i, int j) throws BadLocationException {
            int k = getSelectionStart();
            if (k > 0)
                k--;
            String s = getMatch(getText(0, k));
            if (!isStrict && s == null) {
                super.remove(i, j);
            } else {
                super.remove(0, getLength());
                super.insertString(0, s, null);
            }
            if (autoComboBox != null && s != null)
                autoComboBox.setSelectedValue(s);
            try {
                setSelectionStart(k);
                setSelectionEnd(getLength());
            } catch (Exception ignored) {
            }
        }

    }
}

class AutoComboBox extends JComboBox<Object> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private boolean isFired;

    public AutoComboBox(List<Object> airportsList) {
        isFired = false;
        AutoTextFieldEditor autoTextFieldEditor = new AutoTextFieldEditor(airportsList);
        setEditable(true);
        setModel(new DefaultComboBoxModel<Object>(airportsList.toArray()) {

            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            protected void fireContentsChanged(Object obj, int i, int j) {
                if (!isFired)
                    super.fireContentsChanged(obj, i, j);
            }

        });
        setEditor(autoTextFieldEditor);
    }

    void setSelectedValue(Object obj) {
        if (!isFired) {
            isFired = true;
            setSelectedItem(obj);
            fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder, SELECTED));
            isFired = false;
        }
    }

    @Override
    protected void fireActionEvent() {
        if (!isFired)
            super.fireActionEvent();
    }

    private class AutoTextFieldEditor extends BasicComboBoxEditor {

        AutoTextFieldEditor(List<Object> airportsList) {
            editor = new AutoTextField(airportsList, AutoComboBox.this);
        }
    }

}
