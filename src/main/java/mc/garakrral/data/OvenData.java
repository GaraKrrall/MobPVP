package mc.garakrral.data;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "oven_data")
public class OvenData implements ConfigData {
    @ConfigEntry.Gui.Excluded
    public List<OvenTask> activeOvens = new ArrayList<>();

    public boolean activate(String pos, String recipeId, int totalTime) {
        for (OvenTask t : activeOvens) {
            if (t.pos.equals(pos)) return false;
        }
        OvenTask task = new OvenTask();
        task.pos = pos;
        task.recipeId = recipeId;
        task.remainingTicks = totalTime;
        task.totalTicks = totalTime;
        activeOvens.add(task);
        return true;
    }

    public void deactivate(String pos) {
        activeOvens.removeIf(t -> t.pos.equals(pos));
    }

    public OvenTask get(String pos) {
        for (OvenTask t : activeOvens) {
            if (t.pos.equals(pos)) return t;
        }
        return null;
    }

    public static class OvenTask {
        public String pos;
        public String recipeId;
        public int remainingTicks;
        public int totalTicks;
    }
}
