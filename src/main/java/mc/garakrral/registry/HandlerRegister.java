package mc.garakrral.registry;

import mc.garakrral.handler.oven.OvenTickHandler;

public class HandlerRegister {
    public static void registerHandlers(){
        OvenTickHandler.register();
    }
}
