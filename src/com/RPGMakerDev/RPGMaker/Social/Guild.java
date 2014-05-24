package com.RPGMakerDev.RPGMaker.Social;

import com.RPGMakerDev.RPGMaker.EntityData.RPGEntity;
import java.util.ArrayList;
import org.json.JSONStringer;

public class Guild {

    public enum GuildType {

	Small,
	Normal,
	Large,
	Huge
    }

    private String name;
    private RPGEntity guildMaster;
    private ArrayList<RPGEntity> members;
    private GuildType type;

    /**
     * Creates a new guild
     *
     * @param guildMaster the RPGEntity to be appointed as Guild Master
     * @param name the name of the guild
     */
    public Guild(RPGEntity guildMaster, String name) {
	this.members = new ArrayList<>();
	this.guildMaster = guildMaster;
	this.name = name;
	this.type = GuildType.Small;
	// This should be the last thing to execute
	this.guildMaster.setGuild(this);
    }

    /**
     * Gets the Guild Master of the guild
     *
     * @return the RPGEntity who is the Guild Master
     */
    public RPGEntity getGuildMaster() {
	return guildMaster;
    }

    /**
     * Checks if the RPGEntity is a member of the guild
     *
     * @param member The RPGEntity to check
     * @return true if it is a member, false if not.
     */
    public boolean hasMember(RPGEntity member) {
	return members.contains(member);
    }

    /**
     * Adds a member to the guild
     *
     * @param member
     */
    public void addMember(RPGEntity member) {
	if (member.getType() == RPGEntity.RPGEntityType.PLAYER) {
	    members.add(member);
	} else {
	    throw new IllegalArgumentException("RPGEntity is not a Player!");
	}
    }

    /**
     * Removes the member from the guild
     *
     * @param member the member to remove
     */
    public void kickMember(RPGEntity member) {
	if (members.contains(member)) {
	    members.remove(member);
	}
    }

    /**
     * Saves the guild to disk
     */
    public void save() {
	JSONStringer stringer = new JSONStringer();
	stringer.object()
		.key("name")
		.value(name)
		.key("guildmaster")
		.value(guildMaster.getPlayer().getUniqueId())
		.key("members")
		.array();
	for (RPGEntity member : members) {
	    stringer.object()
		    .key("uuid")
		    .value(member.getPlayer().getUniqueId())
		    .endObject();
	}
	stringer.endArray()
		.endObject();

	System.out.println(stringer.toString());
    }

}
