package com.github.lunatrius.schematica.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

/** Common screen behavior for screens containing ordinary and numeric text fields. */
public class GuiScreenBase extends GuiScreen {

    protected final GuiScreen parentScreen;
    protected final List<GuiTextField> textFields = new ArrayList<GuiTextField>();

    public GuiScreenBase() {
        this(null);
    }

    public GuiScreenBase(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.textFields.clear();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseEvent) {
        for (GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                ((GuiNumericField) button).mouseClicked(mouseX, mouseY, mouseEvent);
            }
        }

        for (GuiTextField textField : this.textFields) {
            textField.mouseClicked(mouseX, mouseY, mouseEvent);
        }

        super.mouseClicked(mouseX, mouseY, mouseEvent);
    }

    @Override
    protected void keyTyped(char character, int code) {
        if (code == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(this.parentScreen);
            return;
        }

        for (GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                GuiNumericField numericField = (GuiNumericField) button;
                int oldValue = numericField.getValue();
                boolean handled = numericField.keyTyped(character, code);
                if (handled && numericField.getValue() != oldValue) {
                    actionPerformed(numericField);
                }
            }
        }

        boolean handled = false;
        for (GuiTextField textField : this.textFields) {
            handled |= textField.textboxKeyTyped(character, code);
        }

        if (!handled) {
            super.keyTyped(character, code);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        for (GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                ((GuiNumericField) button).updateCursorCounter();
            }
        }

        for (GuiTextField textField : this.textFields) {
            textField.updateCursorCounter();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (GuiTextField textField : this.textFields) {
            textField.drawTextBox();
        }
    }
}
