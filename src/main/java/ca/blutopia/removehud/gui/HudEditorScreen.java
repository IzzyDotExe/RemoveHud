package ca.blutopia.removehud.gui;

import ca.blutopia.removehud.config.HUDItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

public class HudEditorScreen extends Screen {

    ButtonWidget moveLeftButton;
    ButtonWidget moveRightButton;
    ButtonWidget moveUpButton;
    ButtonWidget moveDownButton;

    long _windowHandle;

    private SelectedItem _selected;

    public HudEditorScreen() {

        super(Text.of("HUD editor"));

        assert this.client != null;

        _selected = new SelectedItem(HUDItems.Hp);

        moveLeftButton = ButtonWidget.builder(Text.of("<"), this::MoveSelectedItemLeft)
                .dimensions(
                        0, 0,
                        20, 20
                )
                .build();

        moveRightButton = ButtonWidget.builder(Text.of(">"), this::MoveSelectedItemRight)
                .dimensions(
                        20, 20,
                        20, 20
                )
                .build();

        moveUpButton = ButtonWidget.builder(Text.of("^"), this::MoveSelectedItemUp)
                .dimensions(
                        width/2-20-30, height/2-20,
                        20, 20
                )
                .build();

        moveDownButton = ButtonWidget.builder(Text.of("v"), this::MoveSelectedItemDown)
                .dimensions(
                        width/2-20-30, height/2-20,
                        20, 20
                )
                .build();

        _windowHandle = MinecraftClient.getInstance().getWindow().getHandle();

    }

    private void MoveSelectedItemLeft(ButtonWidget button) {
        try {
            _selected.MoveX(-1);
        } catch (NoSuchMethodException e) {
            return;
        }
    }
    private void MoveSelectedItemRight(ButtonWidget button) {
        try {
            _selected.MoveX(1);
        } catch (NoSuchMethodException e) {
            return;
        }
    }

    private void MoveSelectedItemDown(ButtonWidget button) {
        try {
            _selected.MoveY(-1);
        } catch (NoSuchMethodException e) {
            return;
        }
    }
    private void MoveSelectedItemUp(ButtonWidget button) {
        try {
            _selected.MoveY(1);
        } catch (NoSuchMethodException e) {
            return;
        }
    }


    @Override
    protected void init() {

        super.init();
        addDrawableChild(moveLeftButton);
        addDrawableChild(moveRightButton);
//        addDrawableChild(moveDownButton);
//        addDrawableChild(moveUpButton);

    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        try {
            _selected.MoveX(deltaX > 0? 1 : deltaX == 0? 0 : -1);
        } catch (NoSuchMethodException e) {
            return false;
        }

        try {
            _selected.MoveY(deltaY > 0? 1 : deltaY == 0? 0 : -1);
        } catch (NoSuchMethodException e) {
            return false;
        }

        System.out.println(deltaX + " / " + deltaY);

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        glfwSetInputMode(_windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        glfwSetInputMode(_windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        return super.mouseReleased(mouseX, mouseY, button);

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }
}
