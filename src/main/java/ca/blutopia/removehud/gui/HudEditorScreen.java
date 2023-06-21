package ca.blutopia.removehud.gui;

import ca.blutopia.removehud.HUDManager;
import ca.blutopia.removehud.RemoveHud;
import ca.blutopia.removehud.config.HUDItems;
import ca.blutopia.removehud.config.OriginPoint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

public class HudEditorScreen extends Screen {

    ButtonWidget moveLeftButton;
    ButtonWidget moveRightButton;
    ButtonWidget moveUpButton;
    ButtonWidget moveDownButton;
    CyclingButtonWidget<OriginPoint> widg;
    CyclingButtonWidget<HUDItems> wid2;
    long _windowHandle;

    private SelectedItem _selected;

    public HudEditorScreen() {

        super(Text.of("HUD editor"));

        RemoveHud.HudManagerInstance.ConfigInstance.EnableEditor = true;

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
                        20, 0,
                        20, 20
                )
                .build();

        moveUpButton = ButtonWidget.builder(Text.of("^"), this::MoveSelectedItemUp)
                .dimensions(
                        40, 0,
                        20, 20
                )
                .build();

        moveDownButton = ButtonWidget.builder(Text.of("v"), this::MoveSelectedItemDown)
                .dimensions(
                        60, 0,
                        20, 20
                )
                .build();

        try {
            widg = new CyclingButtonWidget.Builder<OriginPoint>(x -> {
                return switch (x) {

                    case ORIGIN -> Text.of("origin");
                    case TOPLEFT -> Text.of("topleft");
                    case BOTTOMLEFFT -> Text.of("bottomleft");
                    case TOPRIGHT -> Text.of("topright");
                    case BOTTONRIGHT -> Text.of("bottomright");
                };
            }).initially(_selected.getOrigin())
                    .values(OriginPoint.values())
                    .build(20, 30, 120, 20, Text.of("Origin"), (sender, value) -> {
                        try {
                            _selected.setOrigin(value);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


        wid2 = new CyclingButtonWidget.Builder<HUDItems>(x->{
            return Text.of(x.name());
        }).initially(_selected.getSelected())
                .values(HUDItems.values())
                .build(20, 80, 120, 20, Text.of("Hud Item"), (sender,value) -> {
                    _selected.setSelected(value);
                    try {
                        widg.setValue(_selected.getOrigin());
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });


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
        addDrawableChild(moveDownButton);
        addDrawableChild(moveUpButton);

        addDrawableChild(widg);
        addDrawableChild(wid2);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        RemoveHud.HudManagerInstance.ConfigInstance.EnableEditor = false;
        return super.shouldCloseOnEsc();
    }

    @Override
    public void close() {
        RemoveHud.HudManagerInstance.ConfigInstance.EnableEditor = false;
        super.close();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

        if (!checkButtonOver(mouseX, mouseY)) return false;

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


    private boolean checkButtonOver(double mouseX, double mouseY) {
        if (moveRightButton.isMouseOver(mouseX, mouseY))
            return false;

        if (moveLeftButton.isMouseOver(mouseX, mouseY))
            return false;

        if (moveDownButton.isMouseOver(mouseX, mouseY))
            return false;

        if (moveUpButton.isMouseOver(mouseX, mouseY))
            return false;
        return true;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }


}
