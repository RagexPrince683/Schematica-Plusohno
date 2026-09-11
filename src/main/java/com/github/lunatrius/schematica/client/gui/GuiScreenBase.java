package com.github.lunatrius.schematica.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

/** Common screen behavior used by Schematica's screens with editable text fields. */
public class GuiScreenBase extends GuiScreen {

    protected final GuiScreen parentScreen;
    protected final List<GuiTextField> textFields = new ArrayList<>();

    public GuiScreenBase(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        for (GuiTextField textField : this.textFields) {
            textField.updateCursorCounter();
        }
    }

    @Override
    protected void keyTyped(char character, int keyCode) {
        boolean handled = false;
        for (GuiTextField textField : this.textFields) {
            handled |= textField.textboxKeyTyped(character, keyCode);
        }
        if (!handled) {
            super.keyTyped(character, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (GuiTextField textField : this.textFields) {
            textField.mouseClicked(mouseX, mouseY, mouseButton);
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
