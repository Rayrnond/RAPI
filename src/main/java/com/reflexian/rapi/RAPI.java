package com.reflexian.rapi;

import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.annotation.Registrar;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public final class RAPI {

    private static JavaPlugin instance;

    public RAPI(JavaPlugin javaPlugin) {
        instance=javaPlugin;
    }

    public void init() {
        loadAnnotations();
        loadCommands();
    }


    private void loadCommands() {

        try {
            String[] pack = instance.getClass().getPackage().getName().split("\\.");
            try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(pack[0] + "." + pack[1]).enableClassInfo().scan()) {
                List<Class<?>> commands = scanResult.getClassesWithAnnotation(CommandInfo.class.getName()).loadClasses();
                for (Class<?> clazz : commands) {
                    Object object = clazz.newInstance();
                    CommandInfo commandInfo = clazz.getDeclaredAnnotation(CommandInfo.class);
                    if (!(object instanceof com.reflexian.rapi.api.command.Command)) continue;
                    com.reflexian.rapi.api.command.Command command = (com.reflexian.rapi.api.command.Command) object;

                    command.setName(commandInfo.name());
                    command.setDescription(commandInfo.description());
                    command.setAliases(Arrays.asList(commandInfo.aliases()));

                    try {
                        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                        bukkitCommandMap.setAccessible(true);
                        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
                        commandMap.register(instance.getName().toLowerCase(), command);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAnnotations() {
        String[] pack = instance.getClass().getPackage().getName().split("\\.");
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(pack[0]+"."+pack[1]).enableClassInfo().scan()) {
            List<Class<?>> commands = scanResult.getClassesWithAnnotation(Registrar.class.getName()).loadClasses();
            for (Class<?> clazz : commands) {
                Object object = clazz.newInstance();
                Registrar registrar = clazz.getDeclaredAnnotation(Registrar.class);
                if (object instanceof Listener)  {
                    if (!registrar.register()) continue;
                    Bukkit.getPluginManager().registerEvents((Listener) object,instance);
                } else {
                    throw new RuntimeException("Registrar should only be used in classes instances of Bukkit Listener!");
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
