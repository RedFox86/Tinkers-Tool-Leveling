package net.redfox.tleveling.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.loading.FMLPaths;
import net.redfox.tleveling.TinkersLeveling;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonConfigReader {
  public static final Runnable MODIFIERS = () -> writeJsonFile(getFilePathAsString("modifiers"), createDefaultJsonObject(createJsonArray(
      new JsonObjectBuilder().add("item", "tconstruct:pickaxe").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),

          new JsonObjectBuilder().add("modifier", "tconstruct:blasting").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:hydraulic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:lightspeed").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).build()
          )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:sledge_hammer").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),

          new JsonObjectBuilder().add("modifier", "tconstruct:blasting").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:hydraulic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:lightspeed").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:vein_hammer").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),

          new JsonObjectBuilder().add("modifier", "tconstruct:blasting").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:hydraulic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:lightspeed").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:mattock").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:pickadze").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:excavator").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:hand_axe").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:broad_axe").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:kama").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 4).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 4).add("max", 5).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:scythe").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 4).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 4).add("max", 5).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:dagger").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 4).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:fiery").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:knockback").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:necrotic").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:severing").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sweeping_edge").add("weight", 3).add("max", 4).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:antiaquatic").add("weight", 1).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:bane_of_sssss").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:pierce").add("weight", 2).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sharpness").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:smite").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:swiftstrike").add("weight", 2).add("max", 5).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:sword").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 4).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:fiery").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:freezing").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:knockback").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:necrotic").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:padded").add("weight", 1).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:severing").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sweeping_edge").add("weight", 3).add("max", 4).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:antiaquatic").add("weight", 1).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:bane_of_sssss").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:cooling").add("weight", 1).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:killager").add("weight", 1).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:pierce").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sharpness").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:smite").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:swiftstrike").add("weight", 2).add("max", 5).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:cleaver").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 4).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:fiery").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:freezing").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:knockback").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:necrotic").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:padded").add("weight", 1).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:severing").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sweeping_edge").add("weight", 3).add("max", 4).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:antiaquatic").add("weight", 1).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:bane_of_sssss").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:cooling").add("weight", 1).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:killager").add("weight", 1).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:pierce").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sharpness").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:smite").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:swiftstrike").add("weight", 2).add("max", 5).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:crossbow").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 4).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:impaling").add("weight", 1).add("max", 4).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:power").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:punch").add("weight", 3).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:quick_charge").add("weight", 4).add("max", 3).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:longbow").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 4).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:impaling").add("weight", 1).add("max", 4).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:power").add("weight", 5).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:punch").add("weight", 3).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:quick_charge").add("weight", 4).add("max", 3).build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:plate_helmet").add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 3).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:ricochet").add("weight", 1).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:springy").add("weight", 1).add("max", 5).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:thorns").add("weight", 1).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:respiration").add("weight", 3).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:protection").add("weight", 5).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:projectile_protection").add("weight", 3).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:blast_protection").add("weight", 3).add("max", 3).build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:fire_protection").add("weight", 3).add("max", 3).build()
      )).build()
  )));

  public static JsonObject getOrCreateJsonFile(String fileName, Runnable create) {
    if (!new File(getFilePathAsString(fileName)).exists())
      create.run();
    return readJsonFile(fileName);
  }

  private static void writeJsonFile(String fileName, JsonObject jsonObject) {
    File file = new File(fileName);
    try {
      if (file.getParentFile() != null) {
        file.getParentFile().mkdirs();
      }

      file.createNewFile();

      try (FileWriter writer = new FileWriter(file)) {
        new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject, writer);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static JsonObject readJsonFile(String fileName) {
    File file = new File(getFilePathAsString(fileName));
    Gson gson = new Gson();
    try (FileReader reader = new FileReader(file)) {
      return gson.fromJson(reader, JsonObject.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static JsonObject createDefaultJsonObject(JsonElement jsonElement) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.add("values", jsonElement);
    return jsonObject;
  }

  private static JsonArray createJsonArray(JsonObject... jsonObjects) {
    JsonArray jsonArray = new JsonArray();
    for (JsonObject jsonObject : jsonObjects) {
      jsonArray.add(jsonObject);
    }
    return jsonArray;
  }

  private static String getFilePathAsString(String filePath) {
    return FMLPaths.CONFIGDIR.get().resolve(TinkersLeveling.MOD_ID + "/" + filePath + ".json").toString();
  }

  public static class JsonObjectBuilder {
    private final List<String> keys;
    private final List<Object> values;

    public JsonObjectBuilder() {
      this.keys = new ArrayList<>();
      this.values = new ArrayList<>();
    }

    public JsonObjectBuilder add(String key, Object value) {
      this.keys.add(key);
      this.values.add(value);
      return this;
    }

    public JsonObject build() {
      JsonObject jsonObject = new JsonObject();
      for (int i = 0; i < this.keys.size(); i++) {
        Object value = this.values.get(i);
        if (value instanceof String s) {
          jsonObject.addProperty(this.keys.get(i), s);
        } else if (value instanceof Number n) {
          jsonObject.addProperty(this.keys.get(i), n);
        } else if (value instanceof Boolean b) {
          jsonObject.addProperty(this.keys.get(i), b);
        } else if (value instanceof JsonElement e) {
          jsonObject.add(this.keys.get(i), e);
        }
      }

      return jsonObject;
    }
  }
}
