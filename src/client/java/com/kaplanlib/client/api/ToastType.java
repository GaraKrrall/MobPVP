package com.kaplanlib.client.api;

import net.minecraft.client.toast.SystemToast;

public enum ToastType {
    PERIODIC_NOTIFICATION(SystemToast.Type.PERIODIC_NOTIFICATION),
    CHUNK_LOAD_FAILURE(SystemToast.Type.WORLD_ACCESS_FAILURE),
    NARRATOR_TOGGLE(SystemToast.Type.NARRATOR_TOGGLE);

    private final SystemToast.Type vanilla;

    ToastType(SystemToast.Type vanilla) {
        this.vanilla = vanilla;
    }

    public SystemToast.Type getVanilla() {
        return vanilla;
    }
}
