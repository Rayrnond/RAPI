# Raymond's API

This library is a useful tool for Spigot Minecraft Plugin Developers. Included are many utility tools to decrease the time spent developing/copy pasting (we see you!). Made for Spigot development, not Bungeecord.

## Table of Contents
* [Features](https://github.com/Rayrnond/RAPI/tree/master#features)
  * [Current Features](https://github.com/Rayrnond/RAPI/tree/master#current-features)
  * [Planned Features](https://github.com/Rayrnond/RAPI/tree/master#planned-features)
* [Download](https://github.com/Rayrnond/RAPI/tree/master#download)
  * [Maven](https://github.com/Rayrnond/RAPI/tree/master#maven)
  * [Gradle](https://github.com/Rayrnond/RAPI/tree/master#gradle)
* [Examples](https://github.com/Rayrnond/RAPI/tree/master#examples)


## Features

### Current Features

* A very cool way to register listener classes
* No plugin.yml required commands
 * Two events
    * PlayerJumpEvent
    * PlayerRelocateEvent (a cancellable version of PlayerMoveEvent)
* StringUtils class
* **More Soon!**

### Planned Features
* To be determined...
 
 
## Download

### Maven
 
 ```xml

    <repositories>
	  <repository>
	    <id>reflexian-repo</id>
	    <url>https://repository.reflexian.com</url>
	  </repository>
    </repositories>

    <dependencies>  
	  <dependency>
	    <groupId>com.reflexian</groupId>
	    <artifactId>rapi</artifactId>
	    <version>1.0</version>
	  </dependency>
    </dependencies>  
  ```
  
  ## Examples
  
  Create a new command with name ``/long``.
  ```java
@CommandInfo(name="long",description = "This is a long description!", aliases = {"l","longcommand"})
public class TestCommand extends Command {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("You used /long!");
        return true;
    }
}
  ```
  
  Register all of your listeners in a class without using ``PluginManager#registerEvents``!
  ```java
  
@Registrar(type = Registrar.Type.LISTENER)
public class TestListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage("WELCOME TO OUR SERVER " + event.getPlayer().getName()+"!");
    }

}
```


Prevent players from jumping.
```java
@Registrar(type = Registrar.Type.LISTENER)
public class TestListener implements Listener {

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        event.setCancelled(true);
    }
    
}
```
