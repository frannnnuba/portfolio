package com.store.api.Entity;

import java.util.Set;

public enum Rols {
    ADMIN(Set.of(Permissions.DELETING,Permissions.WRITING,Permissions.READING)),
    CLIENT(Set.of(Permissions.READING));

    private final Set<Permissions> permissions;

    Rols(Set<Permissions> permisions){
        this.permissions=permisions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
