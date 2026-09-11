# Pull Request: Complete standalone LunatriusCore replacements

## Compilation fixes

- Restored the three `GuiNumericField` constructor overloads. Four control-screen call sites require the configurable
  width-and-height overload.
- Restored `GuiNumericField.setEnabled(boolean)`. Five control-screen call sites use it to disable coordinates when no
  schematic is loaded and to disable the layer value when layer rendering is off.
- Restored `Vector3d.lengthSquaredTo(Vector3d)`, which the schematic chunk comparator requires. Together, the missing
  constructors, enabled-state calls, and distance method accounted for the 11 compiler diagnostics caused by the
  incomplete replacements.

## Behavior restored

- Replaced the temporary numeric push button with the original composite layout: an editable text field and 12-pixel
  decrement and increment buttons, while retaining the caller-supplied dimensions and control IDs.
- Restored numeric focus, mouse, keyboard, cursor, range, and owner-notification paths. Button clicks are applied once,
  disabled or hidden fields reject input, temporary empty and minus-only input is accepted, malformed input is safely
  rolled back, and programmatic changes update the last valid text.
- Added overflow-safe stepping and bounded long parsing before committing integer values.
- Restored Escape navigation and ordinary text-field handling in the shared screen base. Save-screen initialization now
  clears its old text fields when Minecraft resizes or reinitializes the screen.
- Restored floor-based double/float-to-integer vector conversion, including negative fractional coordinates, and the
  destination overloads used by the reference API. Double-to-float conversion also retains the reference's deliberate
  floor behavior.
- Kept exact vector equality together with matching hash implementations. This deliberately differs from the
  reference's epsilon equality, which did not provide a compatible hash implementation and would violate collection
  contracts.

## Dependency audit

- Compared every replacement introduced by the dependency-removal commit with the supplied reference and reviewed all
  production callers. The inventory traversal and client-only queue split remain behaviorally sufficient for the
  Schematica call sites; existing Schematica-owned file, GUI factory, configuration, and initialization helpers do not
  depend on companion-library classes.
- Confirmed the normal Gradle layout only uses `src/main`; the preserved `LUNATRIUSCOREREFERENCE` directory is not a
  source set, resource directory, subproject, dependency, or packaged input.
- Confirmed production sources, resources, dependency declarations, and mod metadata contain no LunatriusCore imports,
  resource paths, required-mod entries, or bundled mod entry point.
- The adapted implementations remain under `com.github.lunatrius.schematica`. The repository MIT license retains the
  original Lunatrius copyright and attribution.

## Verification and uncertainty

- Static source, caller, dependency declaration, source-set, metadata, and event-flow reviews completed.
- Whitespace/error-marker validation completed with `git diff --check`.
- Production compilation and generated-JAR inspection were intentionally not run because the contributor instruction
  forbids compiling binary files. A dependency-report-only Gradle invocation was attempted, but configuration stopped
  during Groovy semantic analysis because the environment's Java class-file version 69 is unsupported. Consequently,
  resolved dependency output, compilation, packaging, and in-game runtime behavior remain unverified in this
  environment; no runtime-success claim is made.
