package br.com.brunoxkk0.syrxontime.utils;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class ConfigAPI {
	
	File theFile;
	FileConfiguration config;
	private static Random random = new Random();
	boolean newDefaultValueToSave = false;


	/**
	 * Creates a new Config Object for the config.yml File of
	 * the specified Plugin
	 *
	 * @param  plugin The Instance of the Plugin, the config.yml is referring to
	 */
	public ConfigAPI(Plugin plugin) {
		this.theFile = new File("plugins/" + plugin.getDescription().getName().replace(" ", "_") + "/config.yml");
		this.config = YamlConfiguration.loadConfiguration(this.theFile);
	}

	/**
	 * Creates a new Directory if doest not exist at the targed directory
	 *
	 * @param  assetName The asset name you want to copy
	 * @param  targetFolder The target folder you want to paste the theFile in
	 */
	public static File copyAsset(String assetName, File targetFolder, Plugin plugin) {
		try{
			File file = new File(targetFolder, assetName);
			if (!file.exists()){
				InputStream inputStream = plugin.getResource(assetName);
				try {
					Files.copy(inputStream, file.getAbsoluteFile().toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return file;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a new Config Object for the configName.yml File of
	 * the specified Plugin + copy default configs if asked to +
	 * a header information about EverNife Config Manager
	 *
	 * @param  plugin The Instance of the Plugin, the name.yml is referring to
	 */
	public ConfigAPI(Plugin plugin, String configName, boolean copyDefaults, boolean header) {

		this.theFile = new File("plugins/" + plugin.getDescription().getName().replace(" ", "_") + "/" + configName);

		if (!theFile.exists()) {
			File theFileParentDir = theFile.getParentFile();
			theFileParentDir.mkdirs();
			if (copyDefaults){
				copyAsset(configName,theFileParentDir,plugin);
			}
		}

		this.config = YamlConfiguration.loadConfiguration(this.theFile);
		if (header){
			// Site that i used to make this http://patorjk.com/software/taag/#p=display&f=Doom&t=FinalCraft
			this.config.options().header("--------------------------------------------------------" +
					"\n      ______ _             _ _____            __ _   " +
					"\n      |  ___(_)           | /  __ \\          / _| |  " +
					"\n      | |_   _ _ __   __ _| | /  \\/_ __ __ _| |_| |_ " +
					"\n      |  _| | | '_ \\ / _` | | |   | '__/ _` |  _| __|" +
					"\n      | |   | | | | | (_| | | \\__/\\ | | (_| | | | |_ " +
					"\n      \\_|   |_|_| |_|\\__,_|_|\\____/_|  \\__,_|_|  \\__|" +
					"\n  " +
					"\n  " +
					"\n  " +
					"\n              EverNife's Config Manager" +
					"\n" +
					"\n Plugin: " + plugin.getName().replace("EverNife","") +
					"\n Author: " + (plugin.getDescription().getAuthors().size() > 0 ? plugin.getDescription().getAuthors().get(0) : "Desconhecido") +
					"\n" +
					"\n N\u00E3o edite esse arquivo caso voc\u00EA n\u00E3o saiba usa-lo!" +
					"\n-------------------------------------------------------");
		}
	}

	public ConfigAPI(Plugin plugin, String configName) {
		this(plugin,configName,true,false);
	}

	public ConfigAPI(Plugin plugin, String configName, boolean copyDefaults) {
		this(plugin,configName,copyDefaults,true);
	}
	
	/**
	 * Creates a new Config Object for the specified File
	 *
	 * @param  theFile The File for which the Config object is created for
	 */
	public ConfigAPI(File theFile) {
		this.theFile = theFile;
		this.config = YamlConfiguration.loadConfiguration(this.theFile);
	}
	
	/**
	 * Creates a new Config Object for the specified File and FileConfiguration
	 *
	 * @param  theFile The File to save to
	 * @param  config The FileConfiguration
	 */
	public ConfigAPI(File theFile, FileConfiguration config) {
		this.theFile = theFile;
		this.config=config;
	}
	
	/**
	 * Creates a new Config Object for the File with in
	 * the specified FCLocationController
	 *
	 * @param  path The Path of the File which the Config object is created for
	 */
	public ConfigAPI(String path) {
		this.theFile = new File(path);
		this.config = YamlConfiguration.loadConfiguration(this.theFile);
	}
	
	/**
	 * Returns the File the Config is handling
	 *
	 * @return      The File this Config is handling
	 */ 
	public File getTheFile() {
		return this.theFile;
	}
	
	/**
	 * Converts this Config Object into a plain FileConfiguration Object
	 *
	 * @return      The converted FileConfiguration Object
	 */ 
	public FileConfiguration getConfiguration() {
		return this.config;
	}
	
	protected void store(String path, Object value) {
		this.config.set(path, value);
	}
	
	/**
	 * Sets the Value for the specified Path
	 *
	 * @param  path The path in the Config File
	 * @param  value The Value for that Path
	 */
	public void setValue(String path, Object value) {
		if (value == null) {
			this.store(path, value);
			this.store(path + "_extra", null);
		}
		else if (value instanceof Inventory) {
			for (int i = 0; i < ((Inventory) value).getSize(); i++) {
				setValue(path + "." + i, ((Inventory) value).getItem(i));
			}
		}
		else if (value instanceof Date) {
			this.store(path, String.valueOf(((Date) value).getTime()));
		}
		else if (value instanceof Long) {
			this.store(path, value);
		}
		else if (value instanceof UUID) {
			this.store(path, value.toString());
		}
		else if (value instanceof Sound) {
			this.store(path, String.valueOf(value));
		}
		else if (value instanceof ItemStack) {
			this.store(path, new ItemStack((ItemStack) value));
		}
		else if (value instanceof Location) {
			setValue(path + ".x", ((Location) value).getX());
			setValue(path + ".y", ((Location) value).getY());
			setValue(path + ".z", ((Location) value).getZ());
			setValue(path + ".pitch", ((Location) value).getPitch());
			setValue(path + ".yaw", ((Location) value).getYaw());
			setValue(path + ".worldName", ((Location) value).getWorld().getName());
		}
		else if (value instanceof Chunk) {
			setValue(path + ".x", ((Chunk) value).getX());
			setValue(path + ".z", ((Chunk) value).getZ());
			setValue(path + ".worldName", ((Chunk) value).getWorld().getName());
		}
		else if (value instanceof World) {
			this.store(path, ((World) value).getName());
		}
		else this.store(path, value);
	}
	
	/**
	 * Saves the Config Object to its File
	 */ 
	public void save() {
		try {
			config.save(theFile);
		} catch (IOException e) {
		}
	}

	/**
	 * Saves the Config Object to its File if there is any new DefaultValue set
	 */
	public void saveIfNewDefaults() {
		if (newDefaultValueToSave){
			save();
			newDefaultValueToSave = false;
		}
	}
	
	/**
	 * Saves the Config Object to a File
	 * 
	 * @param  file The File you are saving this Config to
	 */ 
	public void save(File file) {
		try {
			config.save(file);
		} catch (IOException e) {
		}
	}


    public List getOrSetDefaultValue(String path, List def) {
        return (List) getOrSetDefaultValue(path,(Object)def);
    }

    public Integer getOrSetDefaultValue(String path, Integer def) {
        return (Integer) getOrSetDefaultValue(path,(Object)def);
    }

    public Double getOrSetDefaultValue(String path, Double def) {
        return (Double) getOrSetDefaultValue(path,(Object)def);
    }

    public Float getOrSetDefaultValue(String path, Float def) {
        return (Float) getOrSetDefaultValue(path,(Object)def);
    }

    public String getOrSetDefaultValue(String path, String def) {
        return (String) getOrSetDefaultValue(path,(Object)def);
    }

	public boolean getOrSetDefaultValue(String path, boolean def) {
		return (boolean) getOrSetDefaultValue(path,(Object)def);
	}

    public Object getOrSetDefaultValue(String path, Object value) {
		if (!contains(path)){
			setValue(path, value);
			return value;
		}else {
			Object object = getValue(path);
			if (object.getClass() != value.getClass()){
				object = value;
				setValue(path,value);
			}

			if (!newDefaultValueToSave) newDefaultValueToSave = true;
			return object;
		}
	}

	public Object pegaOuSalva(String path, Object value){
	    if(!contains(path)){
	        setValue(path, value);
	        save();
	        return value;
        }else{
	        return getValue(path);
        }
    }

	/**
	 * Sets the Value for the specified Path 
	 * (IF the Path does not yet exist)
	 *
	 * @param  path The path in the Config File
	 * @param  value The Value for that Path
	 */
	public void setDefaultValue(String path, Object value) {
		if (!contains(path)) setValue(path, value);
	}

	/**
	 * Checks whether the Config contains the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      True/false
	 */ 
	public boolean contains(String path) {
		return config.contains(path);
	}
	
	/**
	 * Returns the Object at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Value at that Path
	 */ 
	public Object getValue(String path) {
		return config.get(path);
	}
	
	/**
	 * Returns the ItemStack at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The ItemStack at that Path
	 */ 
	public ItemStack getItem(String path) {
		ItemStack item = config.getItemStack(path);
		if (item == null) return null;
		return item;
	}

	/**
	 * Returns a randomly chosen String from an
	 * ArrayList at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      A randomly chosen String from the ArrayList at that Path
	 */ 
	public String getRandomStringfromList(String path) {
		return getStringList(path).get(random.nextInt(getStringList(path).size()));
	}
	
	/**
	 * Returns a randomly chosen Integer from an
	 * ArrayList at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      A randomly chosen Integer from the ArrayList at that Path
	 */ 
	public int getRandomIntfromList(String path) {
		return getIntList(path).get(random.nextInt(getIntList(path).size()));
	}
	
	/**
	 * Returns the String at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The String at that Path
	 */
	public String getString(String path) {
		return config.getString(path);
	}
	public String getString(String path, String def) {
		return config.getString(path,def);
	}

	/**
	 * Returns the Integer at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Integer at that Path
	 */
	public int getInt(String path) {
		return config.getInt(path);
	}
	public int getInt(String path, int def) {
		return config.getInt(path,def);
	}

	/**
	 * Returns the Boolean at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Boolean at that Path
	 */
	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}
	public boolean getBoolean(String path,boolean def) {
		return config.getBoolean(path,def);
	}

	/**
	 * Returns the StringList at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The StringList at that Path
	 */
	public List<String> getStringList(String path) {
		return config.getStringList(path);
	}

    /**
     * Returns the StringList at the specified Path
     *
     * @param  path The path in the Config File
     * @return      The StringList at that Path
     */
    public List<String> getStringList(String path, List<String> def) {
        if (contains(path)) return config.getStringList(path);
        return def;
    }

	/**
	 * Returns the ItemList at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The ItemList at that Path
	 */ 
	public List<ItemStack> getItemList(String path) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String key: getKeys(path)) {
			if (!key.endsWith("_extra")) list.add(getItem(path + "." + key));
		}
		return list;
	}
	
	/**
	 * Returns the IntegerList at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The IntegerList at that Path
	 */ 
	public List<Integer> getIntList(String path) {
		return config.getIntegerList(path);
	}
	
	/**
	 * Recreates the File of this Config
	 */ 
	public void createFile() {
		try {
			this.theFile.createNewFile();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Returns the Float at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Float at that Path
	 */ 
	public Float getFloat(String path) {
		return Float.valueOf(String.valueOf(getValue(path)));
	}
	
	/**
	 * Returns the Long at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Long at that Path
	 */ 
	public Long getLong(String path) {
		return Long.valueOf(String.valueOf(getValue(path)));
	}
	public Long getLong(String path, long def) {
		if (!contains(path)){
			return def;
		}
		return getLong(path);
	}

	/**
	 * Returns the Sound at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Sound at that Path
	 */ 
	public Sound getSound(String path) {
		return Sound.valueOf(getString(path));
	}
	
	/**
	 * Returns the Date at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Date at that Path
	 */ 
	public Date getDate(String path) {
		if (contains(path)){
			return new Date(getLong(path));
		}
		return null;
	}
	
	/**
	 * Returns the Chunk at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Chunk at that Path
	 */ 
	public Chunk getChunk(String path) {
		return Bukkit.getWorld(getString(path + ".worldName")).getChunkAt(getInt(path + ".x"), getInt(path + ".z"));
	}
	
	/**
	 * Returns the UUID at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The UUID at that Path
	 */ 
	public UUID getUUID(String path) {
		if (contains(path)){
			return UUID.fromString(getString(path));
		}
		return null;
	}

	/**
	 * Returns the UUID at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The UUID at that Path
	 */
	public UUID getUUID(String path, UUID def) {
		return UUID.fromString(getString(path,def.toString()));
	}
	
	/**
	 * Returns the World at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The World at that Path
	 */ 
	public World getWorld(String path) {
		return Bukkit.getWorld(getString(path));
	}
	
	/**
	 * Returns the Double at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Double at that Path
	 */
	public Double getDouble(String path) {
		return config.getDouble(path);
	}
	public Double getDouble(String path, double def) {
		return config.getDouble(path,def);
	}

	/**
	 * Returns the FCLocationController at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The FCLocationController at that Path
	 */ 
	public Location getLocation(String path) {
		if (this.contains(path + ".pitch")) {
			return new Location(
				Bukkit.getWorld(
				getString(path + ".worldName")),
				getDouble(path + ".x"),
				getDouble(path + ".y"),
				getDouble(path + ".z"),
				getFloat(path + ".yaw"),
				getFloat(path + ".pitch")
			);
		}
		else {
			return new Location(
				Bukkit.getWorld(
				this.getString(path + ".worldName")),
				this.getDouble(path + ".x"),
				this.getDouble(path + ".y"),
				this.getDouble(path + ".z")
			);
		}
	}
	
	@Deprecated
	public void setLocation(String path, Location location) {
		setValue(path + ".x", location.getX());
		setValue(path + ".y", location.getY());
		setValue(path + ".z", location.getZ());
		setValue(path + ".worldName", location.getWorld().getName());
	}
	
	@Deprecated
	public void setInventory(String path, Inventory inventory) {
		for (int i = 0; i < inventory.getSize(); i++) {
			setValue(path + "." + i, inventory.getItem(i));
		}
	}
	
	/**
	 * Gets the Contents of an Inventory at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @param  size The Size of the Inventory
	 * @param  title The Title of the Inventory
	 * @return      The generated Inventory
	 */ 
	public Inventory getInventory(String path, int size, String title) {
		Inventory inventory = Bukkit.createInventory(null, size, title);
		for (int i = 0; i < size; i++) {
			inventory.setItem(i, getItem(path + "." + i));
		}
		return inventory;
	}
	
	/**
	 * Returns all Paths in this Config
	 *
	 * @return      All Paths in this Config
	 */ 
	public Set<String> getKeys() {
		return config.getKeys(false);
	}
	
	/**
	 * Returns all Sub-Paths in this Config
	 *
	 * @param  path The path in the Config File
	 * @return      All Sub-Paths of the specified Path
	 */ 
	public Set<String> getKeys(String path) {
		if (contains(path)){
			return config.getConfigurationSection(path).getKeys(false);
		}
		return Collections.emptySet();
	}
	
	/**
	 * Reloads the Configuration File
	 */ 
	public void reload() {
		this.config = YamlConfiguration.loadConfiguration(this.theFile);
	}
}
