/*
 * Created on 02.04.2005
 */
package net.sourceforge.ganttproject.gui.options;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import net.sourceforge.ganttproject.gui.options.model.EnumerationOption;
import net.sourceforge.ganttproject.gui.options.model.GPOptionGroup;
import net.sourceforge.ganttproject.language.GanttLanguage;

/**
 * @author bard
 */
class EnumerationOptionComboBoxModel extends AbstractListModel implements
        ComboBoxModel {
    private final List myValues;

    private Item mySelectedItem;

    private final EnumerationOption myOption;

    public EnumerationOptionComboBoxModel(EnumerationOption option, GPOptionGroup group) {
        myOption = option;
        String currentValue = option.getValue();
        Item currentItem = null;
        String[] ids = option.getAvailableValues();
        String[] i18nedValues = geti18nedValues(option, group);

        myValues = new ArrayList(ids.length);
        for (int i = 0; i < ids.length; i++) {
            Item nextItem = new Item(ids[i], i18nedValues[i]);
            myValues.add(nextItem);
            if (ids[i].equals(currentValue)) {
                currentItem = nextItem;
            }
        }
        if (currentItem != null) {
            setSelectedItem(currentItem);
        }
    }

    public void setSelectedItem(Object item) {
        mySelectedItem = (Item) item;
        myOption.setValue(mySelectedItem.myID);
    }

    public Object getSelectedItem() {
        return mySelectedItem;
    }

    public int getSize() {
        return myValues.size();
    }

    public Object getElementAt(int index) {
        return myValues.get(index);
    }

    String[] geti18nedValues(EnumerationOption option, GPOptionGroup group) {
        String[] ids = option.getAvailableValues();
        String[] result = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            String key = OptionsPageBuilder.I18N.getCanonicalOptionValueLabelKey(ids[i]);
            String value = GanttLanguage.getInstance().getText(key);

            if (value == null) {
                key = group.getI18Nkey(key);
                if (key != null) {
                    value = GanttLanguage.getInstance().getText(key);
                }
            }
            result[i] = value==null ? ids[i] : value;
        }
        return result;
    }

    private static class Item {
        private final String myID;

        private final String myDisplayValue;

        public Item(String id, String displayValue) {
            myID = id;
            myDisplayValue = displayValue;
        }

        public String toString() {
            return myDisplayValue;
        }
    }
}
