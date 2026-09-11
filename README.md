## Welcome to Schematica Plus!

### Usage:

When holding the tool item,
- Hold `LCONTROL` and scroll to switch current tool mode.
- Use right click and left click to move, place and select.
- Use "Execute" key bind to paste, etc.

Default tool item is `minecraft:stick`.

**See more detailed info on [Curseforge](https://www.curseforge.com/minecraft/mc-mods/schematica-plus) if you are new to the mod.**

### Keybinds:

| Key | Action |
|-----|--------|
| `M` | Open Schematic Management GUI |
| `N` | Open Save Schematic GUI |
| `Enter` | Execute current tool action (save, paste, fill, delete, replace) |
| `LCTRL + Scroll` | Cycle tool mode (while holding tool item) |
| Left Click | Set point A / Pick primary block (while holding tool item) |
| Right Click | Set point B / Pick secondary block / Place schematic (while holding tool item) |

---

### Requirements and optional integrations

- **Required:** Minecraft Forge 10.13.4.1614 for Minecraft 1.7.10.
- **Not required:** LunatriusCore. Schematica Plus now contains its own internal implementations of the small set of
  vector, GUI, and inventory utilities it uses.
- **Optional:** The Lord of the Rings Mod integration is detected at runtime and remains optional.

Schematica Plus does not register or bundle a replacement companion mod, so a separately installed LunatriusCore can
coexist without package or resource conflicts.

![play GTNH in multiplayer](temp.png)

---

I'm currently working on other projects, so development is on hold for now.

If you’d like to contribute to this enhanced mod, feel free to go ahead.

### Changes from GTNH-ver:
- Added most functions from [Litematica](https://github.com/maruohon/litematica/), made it more user-friendly.

For example:
- Loading multiple schematic instances.
- Pasting schematics (including NBT tags, block states, entities, etc.) directly into the world when having permissions.
- Storing blocks and entities with NBT tags.
- Different edit modes, making it a lite version of World Edit (jk).
- Supports `.litematic` (I have zero idea on why I decided to implement this, but it does work really well).
- Modern GUI from [Litematica](https://github.com/maruohon/litematica/) (Still working on this).

---

### Changes from Original:
- Store Coordinates & rotation of schematics per world/server. No more re-entering coordinates for large builds!
- Fix heavy lag when having lotr armor stands/weapon racks in loaded schematic
- Updated Chinese translation

### Standalone implementation notes

The former companion-library calls were replaced with mutable vectors under Schematica's own package, local base and
numeric-field GUI controls, and direct vanilla inventory counting. The numeric control retains editable text entry,
bounded increment/decrement buttons, disabled-state handling, and owner-screen change events. Vector conversions retain
the original floor semantics. Build dependencies and Forge metadata now require only Forge; LOTR remains a compile-only,
runtime-optional integration. Client-only queue event handling is registered by the client proxy rather than being
loaded during common dedicated-server initialization.

This changes Java-facing types previously exposed by `CommonProxy`, `ClientProxy`, `SchematicWorld`, and renderer
classes from `com.github.lunatrius.core.util.vector.*` to
`com.github.lunatrius.schematica.util.vector.*`. Binary integrations compiled against those old signatures must be
recompiled and update their imports. Schematic formats, NBT data, packet layouts, configuration keys, key bindings,
and data file locations are unchanged.

The supplied LunatriusCore tree is retained only as a source reference: it is not a Gradle subproject, source set,
resource input, dependency, or packaged component. Verification for this change included source and resource searches
for old imports, dependency declarations, and resource paths; review of common versus client proxy initialization; and
static review of the GUI event and vector conversion paths. No in-game runtime verification was performed.
