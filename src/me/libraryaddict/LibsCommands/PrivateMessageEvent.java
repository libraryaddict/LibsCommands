package me.libraryaddict.LibsCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PrivateMessageEvent extends Event implements Cancellable {
  private static final HandlerList handlers = new HandlerList();
  public static HandlerList getHandlerList() {
    return handlers;
  }
  private boolean cancelled = false;
  private String displayNameReceiver;
  private String displayNameSender;
  private String message;
  private CommandSender receiver;
  private boolean replying;

  private CommandSender sender;

  public PrivateMessageEvent(CommandSender sender, CommandSender receiver,
      String message, boolean reply) {
    this.sender = sender;
    this.receiver = receiver;
    if (Bukkit.getPlayerExact(sender.getName()) != null)
      displayNameSender = Bukkit.getPlayerExact(sender.getName())
          .getDisplayName();
    else
      displayNameSender = sender.getName();
    if (Bukkit.getPlayerExact(receiver.getName()) != null)
      displayNameReceiver = Bukkit.getPlayerExact(receiver.getName())
          .getDisplayName();
    else
      displayNameReceiver = receiver.getName();
    this.message = message;
    this.replying = reply;
  }

  public HandlerList getHandlers() {
    return handlers;
  }

  public String getMessage() {
    return message;
  }

  public CommandSender getReceiver() {
    return receiver;
  }

  public String getReceiverDisplayName() {
    return displayNameReceiver;
  }

  public CommandSender getSender() {
    return sender;
  }

  public String getSenderDisplayName() {
    return displayNameSender;
  }

  public boolean isCancelled() {
    return cancelled;
  }

  public boolean isReply() {
    return replying;
  }

  public void setCancelled(boolean arg0) {
    cancelled = arg0;
  }

  public void setMessage(String newMsg) {
    message = newMsg;
  }

}
