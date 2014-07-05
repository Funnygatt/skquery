package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;


@Patterns("make %entities% leash %livingentities%")
public class EffLeash extends Effect {

    private Expression<Entity> holder;
    private Expression<LivingEntity> target;

    @Override
    protected void execute(Event event) {
        for (Entity h : holder.getAll(event)) {
            for (LivingEntity t : target.getAll(event)) {
                t.setLeashHolder(h);
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "leash";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        holder = (Expression<Entity>) expressions[0];
        target = (Expression<LivingEntity>) expressions[1];
        return true;
    }
}