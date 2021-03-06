package com.betabot.event.impl;

import com.betabot.script.api.MethodContext;
import com.betabot.script.wrappers.RSGroundItem;
import com.betabot.script.wrappers.RSPlayer;
import com.betabot.script.wrappers.RSTile;

import com.betabot.bot.Bot;
import com.betabot.event.listeners.PaintListener;

import java.awt.*;

public class DrawGround implements PaintListener {

	private final MethodContext ctx;

	public DrawGround(Bot bot) {
		this.ctx = bot.getMethodContext();
	}

	public void onRepaint(final Graphics render) {
		if (!ctx.game.isLoggedIn()) {
			return;
		}
		final RSPlayer player = ctx.players.getMyPlayer();
		if (player == null) {
			return;
		}
		render.setColor(Color.WHITE);
		final RSTile location = player.getLocation();
		for (int x = location.getX() - 25; x < location.getX() + 25; x++) {
			for (int y = location.getY() - 25; y < location.getY() + 25; y++) {
				final RSGroundItem[] item = ctx.groundItems.getAllAt(x, y);
				if ((item == null) || (item.length == 0)) {
					continue;
				}
				final Point screen = ctx.calc.tileToScreen(item[0].getLocation());
				if (ctx.calc.pointOnScreen(screen)) {
					render.drawString("" + item[0].getItem().getID(), location.getX() - 10, location.getY());
				}
			}
		}
	}
}
