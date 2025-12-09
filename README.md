# December 8th, 2025:Setup + Debugging Log
1. JDK / Gradle configuration errors
Error: IntelliJ - "Found invalid Gradle JVM configuration – Please fix JAVA_HOME environment variable and java -version reported Java 1.8."
Cause: Only Java 8/11/17 were installed and JAVA_HOME was pointing to the wrong JDK through my .zshrc
Fix:
Installed OpenJDK 21 with Homebrew: brew install openjdk@21.
Cleaned and rewrote ~/.zshrc 
Set Project SDK and Gradle JVM in IntelliJ to “Homebrew OpenJDK 21.0.9”.
After that, the project built and DemoApplication.main() ran successfully.

2. Git / GitHub permission error (403)
Error: git push -u origin main → remote: Permission to trishajain12/quicken_project.git denied to tjain010.
Cause: Keychain Access had my old school GitHub account, so pushes were authenticated as the wrong user.
Fix:
Opened Keychain Access, searched github.com, and deleted existing GitHub password entries.
Created a new public GitHub repo quicken_project under trishajain12.
When prompted, logged in as trishajain12 using a Personal Access Token with repo scope.
Push succeeded and the repo is now public.
