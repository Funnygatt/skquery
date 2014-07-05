package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skriptaddon.skaddonlib.util.Reflection;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.lang.reflect.InvocationTargetException;


@Patterns("make %livingentities% pathfind to %location% with speed %number%")
public class EffPathfind extends Effect {

    private Expression<LivingEntity> entity;
    private Expression<Location> loc;
    private Expression<Number> speed;

    @Override
    protected void execute(Event event) {
        Location l = loc.getSingle(event);
        Number s = speed.getSingle(event);
        if(l == null || s == null) return;
        for (LivingEntity e : entity.getAll(event)) {
            if (e instanceof Player) continue;
            try {
                Object entityInsentient = Reflection.obcClass("entity.CraftLivingEntity").getMethod("getHandle").invoke(e);
                Object navigation = Reflection.nmsClass("EntityInsentient").getMethod("getNavigation").invoke(entityInsentient);
                navigation.getClass().getMethod("a", double.class, double.class, double.class, double.class).invoke(navigation, l.getX(), l.getY(), l.getZ(), s.doubleValue());
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "pathfind";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        entity = (Expression<LivingEntity>) expressions[0];
        loc = (Expression<Location>) expressions[1];
        speed = (Expression<Number>) expressions[2];
        return true;
    }
}