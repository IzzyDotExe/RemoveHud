package ca.blutopia.removehud.gui;

import ca.blutopia.removehud.ModConfig;
import ca.blutopia.removehud.RemoveHud;
import ca.blutopia.removehud.config.HUDItems;

import java.lang.reflect.InvocationTargetException;

public class SelectedItem {

    private HUDItems _selected;

    public SelectedItem(HUDItems def) {
        _selected = def;
    }

    private String getSelectedName() {
        return _selected.name();
    }


    public void MoveX(int offset) throws NoSuchMethodException {

        var setMethod = ModConfig.class.getMethod("set"+getSelectedName()+"XOffset", int.class);

        try {
            setMethod.invoke(ModConfig.INSTANCE, offset);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    public void MoveY(int offset) throws NoSuchMethodException {

        var setMethod = ModConfig.class.getMethod("set"+getSelectedName()+"YOffset", int.class);

        try {
            setMethod.invoke(ModConfig.INSTANCE, offset);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
