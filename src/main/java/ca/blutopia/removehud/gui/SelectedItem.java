package ca.blutopia.removehud.gui;

import ca.blutopia.removehud.HUDManager;
import ca.blutopia.removehud.config.ModConfig;
import ca.blutopia.removehud.RemoveHud;
import ca.blutopia.removehud.config.HUDItems;
import ca.blutopia.removehud.config.OriginPoint;

import java.lang.reflect.InvocationTargetException;

public class SelectedItem {
    private static final ModConfig CONFIG = RemoveHud.HudManagerInstance.ConfigInstance;
    private static final HUDManager HUD_MANAGER = RemoveHud.HudManagerInstance;
    private HUDItems _selected;

    public SelectedItem(HUDItems def) {
        _selected = def;
    }

    private String getSelectedName() {
        return _selected.name();
    }


    public void MoveX(int offset) throws NoSuchMethodException {

        var setMethod = HUDManager.class.getMethod("set"+getSelectedName()+"XOffset", int.class);

        try {
            setMethod.invoke(HUD_MANAGER, offset);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    public void MoveY(int offset) throws NoSuchMethodException {

        var setMethod = HUDManager.class.getMethod("set"+getSelectedName()+"YOffset", int.class);

        try {
            setMethod.invoke(HUD_MANAGER, offset);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOrigin(OriginPoint origin) throws NoSuchMethodException {
        var setMethod = HUDManager.class.getMethod("set"+getSelectedName()+"Origin", OriginPoint.class);
        try {
            setMethod.invoke(HUD_MANAGER, origin);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public OriginPoint getOrigin() throws NoSuchMethodException {
        var getMethod = HUDManager.class.getMethod("get"+getSelectedName()+"Origin");
        try {
           var origin = getMethod.invoke(HUD_MANAGER);
           return (OriginPoint)origin;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}

