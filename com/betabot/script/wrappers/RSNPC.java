package com.betabot.script.wrappers;

import com.betabot.script.api.MethodContext;

import java.lang.ref.SoftReference;

/**
 * Represents a non-player character.
 */
public class RSNPC extends RSCharacter {

	private final SoftReference<com.betabot.client.RSNPC> npc;

	public RSNPC(final MethodContext ctx, final com.betabot.client.RSNPC npc) {
		super(ctx);
		this.npc = new SoftReference<com.betabot.client.RSNPC>(npc);
	}

	@Override
	protected com.betabot.client.RSCharacter getAccessor() {
		return npc.get();
	}

	public String[] getActions() {
		com.betabot.client.RSNPCDef def = getDefInternal();
		if (def != null) {
			return def.getActions();
		}
		return new String[0];
	}

	public int getID() {
		com.betabot.client.RSNPCDef def = getDefInternal();
		if (def != null) {
			return def.getType();
		}
		return -1;
	}

	@Override
	public String getName() {
		com.betabot.client.RSNPCDef def = getDefInternal();
		if (def != null) {
			return def.getName();
		}
		return "";
	}

	@Override
	public int getLevel() {
		com.betabot.client.RSNPC c = npc.get();
		if (c == null) {
			return -1;
		} else {
			return c.getLevel();
		}
	}

	/**
	 * @return <tt>true</tt> if RSNPC is interacting with RSPlayer; otherwise
	 *         <tt>false</tt>.
	 */
	@Override
	public boolean isInteractingWithLocalPlayer() {
		RSNPC npc = methods.npcs.getNearest(getID());
		return npc.getInteracting() != null && npc.getInteracting().equals(
				methods.players.getMyPlayer());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (final String act : getActions()) {
			sb.append(act);
			sb.append(",");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return "NPC[" + getName() + "],actions=[" + sb.toString() + "]"
				+ super.toString();
	}

	com.betabot.client.RSNPCDef getDefInternal() {
		com.betabot.client.RSNPC c = npc.get();
		if (c == null) {
			return null;
		} else {
			return c.getRSNPCDef();
		}
	}
	
	/**
	 * Turns towards the RSNPC.
	 * @author LastCoder
	 * @return <tt>true</tt> - If RSNPC is on screen after attempting to move camera angle.
	 */
	public boolean turnTo() {
		final RSNPC n = methods.npcs.getNearest(getID());
		if(n != null) {
			if(!n.isOnScreen()) {
				methods.camera.turnTo(n);
				return n.isOnScreen();
			}
		}
		return false;
	}

}
