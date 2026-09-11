# Pull Request: Remove LunatriusCore dependency

## Changed

- Removed the LunatriusCore build and mod metadata requirements.
- Added Schematica-owned mutable integer, float, and double vector implementations.
- Added local GUI screen and bounded numeric control implementations.
- Replaced the companion-library inventory counter with direct Minecraft inventory traversal.
- Moved client queue ticking out of the common handler so dedicated-server initialization does not load client types.
- Kept the LOTR integration optional and isolated behind its existing runtime detection.

## Compatibility

- Minecraft Forge 10.13.4.1614 for Minecraft 1.7.10 is now the only required mod dependency.
- Public and publicly reachable vector types now use
  `com.github.lunatrius.schematica.util.vector` instead of `com.github.lunatrius.core.util.vector`. Consumers of those
  Java signatures must update imports and recompile.
- Save formats, NBT handling, network packet formats, configuration, key bindings, and file locations are unchanged.

## Verification

- Reviewed all former LunatriusCore callers and both sided proxy initialization paths.
- Searched source, resources, and build configuration for residual dependency declarations, imports, and resource
  paths.
- Performed Gradle source validation only; no in-game runtime verification was performed.
