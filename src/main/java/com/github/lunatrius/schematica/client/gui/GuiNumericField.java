package com.github.lunatrius.schematica.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

/** An editable, bounded integer field with decrement and increment buttons. */
public class GuiNumericField extends GuiButton {

    private static final int DEFAULT_VALUE = 0;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 12;

    private final GuiTextField guiTextField;
    private final GuiButton guiButtonDec;
    private final GuiButton guiButtonInc;

    private String previous = String.valueOf(DEFAULT_VALUE);
    private int value = DEFAULT_VALUE;
    private int minimum = Integer.MIN_VALUE;
    private int maximum = Integer.MAX_VALUE;
    private boolean wasFocused;

    public GuiNumericField(FontRenderer fontRenderer, int id, int x, int y) {
        this(fontRenderer, id, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public GuiNumericField(FontRenderer fontRenderer, int id, int x, int y, int width) {
        this(fontRenderer, id, x, y, width, DEFAULT_HEIGHT);
    }

    public GuiNumericField(FontRenderer fontRenderer, int id, int x, int y, int width, int height) {
        super(id, x, y, width, height, "");
        this.guiTextField = new GuiTextField(fontRenderer, x + 1, y + 1, width - BUTTON_WIDTH * 2 - 2, height - 2);
        this.guiButtonDec = new GuiButton(0, x + width - BUTTON_WIDTH * 2, y, BUTTON_WIDTH, height, "-");
        this.guiButtonInc = new GuiButton(1, x + width - BUTTON_WIDTH, y, BUTTON_WIDTH, height, "+");
        setValue(DEFAULT_VALUE);
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int x, int y) {
        if (!this.enabled || !this.visible) {
            return false;
        }

        if (this.wasFocused && !this.guiTextField.isFocused()) {
            this.wasFocused = false;
            return true;
        }

        this.wasFocused = this.guiTextField.isFocused();
        return this.guiButtonDec.mousePressed(minecraft, x, y) || this.guiButtonInc.mousePressed(minecraft, x, y);
    }

    @Override
    public void drawButton(Minecraft minecraft, int x, int y) {
        if (this.visible) {
            this.guiTextField.drawTextBox();
            this.guiButtonDec.drawButton(minecraft, x, y);
            this.guiButtonInc.drawButton(minecraft, x, y);
        }
    }

    public void mouseClicked(int x, int y, int action) {
        if (!this.enabled || !this.visible) {
            return;
        }

        this.guiTextField.mouseClicked(x, y, action);
        if (!this.guiTextField.isFocused()) {
            commitTemporaryText();
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        if (this.guiButtonInc.mousePressed(minecraft, x, y)) {
            increment();
        } else if (this.guiButtonDec.mousePressed(minecraft, x, y)) {
            decrement();
        }
    }

    public boolean keyTyped(char character, int code) {
        if (!this.enabled || !this.visible || !this.guiTextField.isFocused()) {
            return false;
        }

        int oldCursorPosition = this.guiTextField.getCursorPosition();
        if (!this.guiTextField.textboxKeyTyped(character, code)) {
            return false;
        }

        String text = this.guiTextField.getText();
        if (text.length() == 0 || text.equals("-")) {
            return true;
        }

        try {
            long parsedValue = Long.parseLong(text);
            int clampedValue = clamp(parsedValue);
            this.value = clampedValue;
            this.previous = String.valueOf(clampedValue);

            if (parsedValue != clampedValue) {
                this.guiTextField.setText(this.previous);
                this.guiTextField.setCursorPosition(this.previous.length());
            }
            return true;
        } catch (NumberFormatException ignored) {
            this.guiTextField.setText(this.previous);
            this.guiTextField.setCursorPosition(oldCursorPosition);
            return false;
        }
    }

    public void updateCursorCounter() {
        this.guiTextField.updateCursorCounter();
    }

    public boolean isFocused() {
        return this.guiTextField.isFocused();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.guiTextField.setEnabled(enabled);
        this.guiButtonDec.enabled = enabled;
        this.guiButtonInc.enabled = enabled;
        if (!enabled) {
            this.guiTextField.setFocused(false);
            this.wasFocused = false;
        }
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
        if (this.maximum < minimum) {
            this.maximum = minimum;
        }
        setValue(this.value);
    }

    public int getMinimum() {
        return this.minimum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
        if (this.minimum > maximum) {
            this.minimum = maximum;
        }
        setValue(this.value);
    }

    public int getMaximum() {
        return this.maximum;
    }

    public void setValue(int value) {
        this.value = clamp(value);
        this.previous = String.valueOf(this.value);
        this.guiTextField.setText(this.previous);
    }

    public int getValue() {
        return this.value;
    }

    private int clamp(long value) {
        if (value < this.minimum) {
            return this.minimum;
        }
        if (value > this.maximum) {
            return this.maximum;
        }
        return (int) value;
    }

    private void increment() {
        if (this.value < this.maximum) {
            setValue((long) this.value + 1L);
        }
    }

    private void decrement() {
        if (this.value > this.minimum) {
            setValue((long) this.value - 1L);
        }
    }

    private void setValue(long value) {
        this.value = clamp(value);
        this.previous = String.valueOf(this.value);
        this.guiTextField.setText(this.previous);
    }

    private void commitTemporaryText() {
        String text = this.guiTextField.getText();
        if (text.length() == 0 || text.equals("-")) {
            this.guiTextField.setText(this.previous);
        }
    }
}
