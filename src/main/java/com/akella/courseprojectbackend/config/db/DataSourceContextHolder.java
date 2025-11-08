package com.akella.courseprojectbackend.config.db;

import com.akella.courseprojectbackend.enums.Role;

public class DataSourceContextHolder {
    private static final ThreadLocal<Role> CONTEXT = new ThreadLocal<>();

    public static void set(Role key) {
        CONTEXT.set(key);
    }

    public static Role get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
