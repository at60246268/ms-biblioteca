

# Upgrade Plan: ms-biblioteca (20260409010304)

- **Generated**: 2026-04-09 01:03:04
- **HEAD Branch**: N/A
- **HEAD Commit ID**: N/A

> Note: This project is not a git repository. Changes are not version-controlled during this upgrade.

## Available Tools

**JDKs**
- JDK 21.0.10: `C:\Users\CLIENTE\Downloads\jdk-21_windows-x64_bin\jdk-21.0.10\bin` (current JAVA_HOME, used for baseline in step 2)
- JDK 25: **<TO_BE_INSTALLED>** (required by step 3 and step 4 Final Validation)

**Build Tools**
- Maven 3.9.6: `C:\Users\CLIENTE\.maven\apache-maven-3.9.6\bin`
- Maven Wrapper: 3.9.14 → **<TO_BE_UPGRADED>** to 4.0.0 (Maven 4.0+ required for Java 25 compatibility; update `.mvn/wrapper/maven-wrapper.properties`)

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20260409010304
- Run tests before and after the upgrade: true

## Upgrade Goals

- Upgrade Java from 21 to 25 (latest LTS)

### Technology Stack

| Technology/Dependency | Current  | Min Compatible | Why Incompatible                                                        |
| --------------------- | -------- | -------------- | ----------------------------------------------------------------------- |
| Java                  | 21       | 25             | User requested                                                          |
| Spring Boot           | 3.5.7    | 3.3.x          | Spring Boot 3.3+ supports Java 25; 3.5.7 already compatible            |
| Maven Wrapper         | 3.9.14   | 4.0.0          | Maven 4.0+ required for Java 25                                         |
| Lombok                | managed  | 1.18.34+       | Older Lombok may fail annotation processing on Java 25                  |
| MapStruct             | 1.6.3    | 1.6.3          | -                                                                       |
| jjwt                  | 0.12.6   | 0.12.6         | -                                                                       |
| springdoc-openapi     | 2.8.6    | 2.8.6          | -                                                                       |

### Derived Upgrades

- Upgrade Maven Wrapper from 3.9.14 to 4.0.0 (Maven 4.0+ is required for Java 25; update `.mvn/wrapper/maven-wrapper.properties` `distributionUrl`)
- Update `java.version` property in `pom.xml` from 21 to 25 (compiler source/target is set via Spring Boot parent)

## Upgrade Steps

- **Step 1: Setup Environment**
  - **Rationale**: Install JDK 25 (not currently available on the machine) needed to compile and run the project with the target Java version.
  - **Changes to Make**:
    - [ ] Install JDK 25
    - [ ] Confirm JDK 25 is available and executable
  - **Verification**:
    - Command: `#appmod-list-jdks` to confirm installation
    - Expected: JDK 25 listed with a valid path

---

- **Step 2: Setup Baseline**
  - **Rationale**: Establish pre-upgrade compilation and test results with the current JDK 21 to form acceptance criteria.
  - **Changes to Make**:
    - [ ] Run baseline compilation with JDK 21
    - [ ] Run baseline tests with JDK 21
    - [ ] Document results in progress.md
  - **Verification**:
    - Command: `mvn clean test-compile -q` then `mvn clean test`
    - JDK: `C:\Users\CLIENTE\Downloads\jdk-21_windows-x64_bin\jdk-21.0.10\bin`
    - Expected: Document SUCCESS/FAILURE and test pass count (forms acceptance criteria)

---

- **Step 3: Upgrade Java to 25 and Maven Wrapper to 4.0.0**
  - **Rationale**: Apply core upgrade — bump `java.version` to 25 in `pom.xml` and update the Maven wrapper to 4.0.0 (required for Java 25). Spring Boot 3.5.7 already supports Java 25, so no intermediate step is needed.
  - **Changes to Make**:
    - [ ] Update `<java.version>21</java.version>` → `<java.version>25</java.version>` in `pom.xml`
    - [ ] Update `distributionUrl` in `.mvn/wrapper/maven-wrapper.properties` to Maven 4.0.0
    - [ ] Fix any compilation errors introduced by Java 25 API/language changes
  - **Verification**:
    - Command: `mvn clean test-compile -q`
    - JDK: JDK 25 (from Step 1)
    - Expected: Compilation SUCCESS (both main and test sources)

---

- **Step 4: Final Validation**
  - **Rationale**: Verify all upgrade goals met. Compile and run full test suite with JDK 25.
  - **Changes to Make**:
    - [ ] Verify `java.version=25` and Maven wrapper 4.0.0 in project files
    - [ ] Resolve any remaining TODOs from previous steps
    - [ ] Clean rebuild with JDK 25
    - [ ] Run full test suite and fix ALL failures (iterative fix loop until 100% pass)
  - **Verification**:
    - Command: `mvn clean test`
    - JDK: JDK 25 (from Step 1)
    - Expected: Compilation SUCCESS + 100% tests pass (≥ baseline from Step 2)

## Key Challenges

- **Maven 4.0 Compatibility**
  - **Challenge**: Maven 4.0.0 introduced some breaking changes (POM model changes, plugin APIs). Updating the wrapper may cause plugin issues.
  - **Strategy**: Update the wrapper `distributionUrl` to Maven 4.0.0. If plugin errors arise, check for plugin versions compatible with Maven 4.

- **Java 25 Annotation Processors (Lombok/MapStruct)**
  - **Challenge**: Annotation processors must be compatible with Java 25's compiler. Incompatible versions may cause silent code-generation failures.
  - **Strategy**: Spring Boot 3.5.7 BOM ships Lombok 1.18.34+, which supports Java 25. MapStruct 1.6.3 supports Java 25. Verify that generated sources compile cleanly.

## Plan Review

- All placeholders resolved.
- GIT_AVAILABLE=false — project is not under version control. All changes remain uncommitted in the working directory.
- Direct upgrade Java 21 → 25 is safe: Spring Boot 3.5.7 already supports Java 25; no intermediate version required.
- No EOL dependencies detected.
- Maven wrapper 4.0.0 upgrade is the only build-tool change required.
