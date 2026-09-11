package com.github.lunatrius.schematica.handler.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

import com.github.lunatrius.schematica.handler.QueueTickHandler;
import com.github.lunatrius.schematica.reference.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/** Runs local schematic capture work only when connected to a remote server. */
public class ClientQueueTickHandler {

    public static final ClientQueueTickHandler INSTANCE = new ClientQueueTickHandler();

    private ClientQueueTickHandler() {}

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        try {
            final EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
            if (player != null && player.sendQueue != null
                && !player.sendQueue.getNetworkManager()
                    .isLocalChannel()) {
                QueueTickHandler.INSTANCE.processQueue();
            }
        } catch (Exception e) {
            Reference.logger.error("Something went wrong while processing the client schematic queue", e);
        }
    }
}
