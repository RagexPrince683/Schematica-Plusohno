package com.github.lunatrius.schematica.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

/** A compact decrement/value/increment button for bounded integer settings. */
public class GuiNumericField extends GuiButton {

    private static final int DEFAULT_WIDTH = 100;
    private static final int CONTROL_WIDTH = 20;

    private int value;
    private int minimum = Integer.MIN_VALUE;
    private int maximum = Integer.MAX_VALUE;

    public GuiNumericField(FontRenderer fontRenderer, int id, int x, int y) {
        super(id, x, y, DEFAULT_WIDTH, 20, "0");
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = MathHelper.clamp_int(value, this.minimum, this.maximum);
        updateDisplayString();
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
        if (this.maximum < minimum) {
            this.maximum = minimum;
        }
        setValue(this.value);
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
        if (this.minimum > maximum) {
            this.minimum = maximum;
        }
        setValue(this.value);
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (!super.mousePressed(minecraft, mouseX, mouseY)) {
            return false;
        }

        if (mouseX < this.xPosition + CONTROL_WIDTH) {
            if (this.value > this.minimum) {
                setValue(this.value - 1);
            }
        } else if (mouseX >= this.xPosition + this.width - CONTROL_WIDTH) {
            if (this.value < this.maximum) {
                setValue(this.value + 1);
            }
        }
        return true;
    }

    private void updateDisplayString() {
        this.displayString = "-   " + this.value + "   +";
    }
}
