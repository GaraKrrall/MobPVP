package com.kaplanlib.api.builder;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;

public class EntityAttributeBuilder<T extends MobEntity> {

    private final EntityType<T> entityType;
    private DefaultAttributeContainer.Builder attributes;

    private EntityAttributeBuilder(EntityType<T> entityType) {
        this.entityType = entityType;
    }

    public static <T extends MobEntity> EntityAttributeBuilder<T> create(EntityType<T> entityType) {
        return new EntityAttributeBuilder<>(entityType);
    }

    public EntityAttributeBuilder<T> attributes(DefaultAttributeContainer.Builder attributes) {
        this.attributes = attributes;
        return this;
    }

    public void build() {
        if (attributes != null) {
            FabricDefaultAttributeRegistry.register(entityType, attributes);
        }
    }
}
