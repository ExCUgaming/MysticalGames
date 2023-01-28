package com.mysticalsurvival.util;

import com.mysticalsurvival.Games;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class DataWriter {

    public DataWriter() {}

    public void makeDataFile(File file) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void writeConfigSection(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.createSection(key);
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteConfigSection(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(key, null);
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeIntData(File file, String key, int val) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(key, val);
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeShortData(File file, String key, short val) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(key, val);
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeBooleanData(File file, String key, boolean val) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(key, val);
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStringData(File file, String key, String val) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(key, val);
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeListData(File file, String key, List val) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(key, val);
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLocationData(File file, String key, Location loc) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(key, loc);
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeObjectData(File file, String key, Object obj) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(key, obj);
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Integer readIntData(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt(key);

    }

    public short readShortData(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return 0;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return (short) config.getInt(key);

    }

    public boolean readBooleanData(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getBoolean(key);

    }

    public String readStringData(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return "error";
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getString(key);

    }

    public List readListData(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getList(key);

    }

    public Location readLocationData(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getLocation(key);

    }

    public Set<String> listKeys(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getConfigurationSection(key).getKeys(false);

    }

    public ConfigurationSection readConfigSection(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getConfigurationSection(key);

    }

    public double readDouble(File file, String key) {
        Games.getPluginDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return 0;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getDouble(key);

    }
}
