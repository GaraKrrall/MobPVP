package mc.garakrral.scheduler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.MinecraftServer;

public class ServerScheduler {

    private static final List<ScheduledTask> TASKS = new LinkedList<>();

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(ServerScheduler::tick);
    }

    public static void schedule(int delayTicks, Runnable task) {
        TASKS.add(new ScheduledTask(delayTicks, task));
    }

    private static void tick(MinecraftServer server) {
        Iterator<ScheduledTask> it = TASKS.iterator();
        while (it.hasNext()) {
            ScheduledTask t = it.next();
            t.ticks--;
            if (t.ticks <= 0) {
                try {
                    t.task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                it.remove();
            }
        }
    }

    private static class ScheduledTask {
        int ticks;
        final Runnable task;

        ScheduledTask(int ticks, Runnable task) {
            this.ticks = ticks;
            this.task = task;
        }
    }
}
