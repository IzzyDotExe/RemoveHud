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

    public HUDItems getSelected() {
        return _selected;
    }

    public void setSelected(HUDItems _selected) {
        this._selected = _selected;
    }

    public void MoveX(int offset) throws NoSuchMethodException {
        try {
            HUD_MANAGER.setElementXOffset(_selected, offset);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void MoveY(int offset) throws NoSuchMethodException {
        try {
            HUD_MANAGER.setElementYOffset(_selected, offset);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOrigin(OriginPoint origin) throws NoSuchMethodException {
        try {
            HUD_MANAGER.setElementOrigin(_selected, origin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public OriginPoint getOrigin() throws NoSuchMethodException {
        try {
            return HUD_MANAGER.getElementOrigin(_selected);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

}

