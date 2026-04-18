# UI Automation Framework

This project uses Selenium with Java to automate web applications, 
along with Gradle for build automation and dependency management.
The test cases are structured using JUnit 4 as the testing framework 
for automating secnarios for Amazon and Flipkart on all the platforms ie. 
Web, MWeb, Android and iOS.

## Common Installation

- Install [IntelliJ IDEA](https://www.jetbrains.com/idea/download)
- Install Git
-  - Using Homebrew
      ```bash
      /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
      ```

- Once Homebrew is installed, install Git:
  ```bash
  brew install git
  ```

- Verify the installation
  ```bash
  git --version
  ```


- Install Java
    - JDK 21
      ```bash
      brew install openjdk@21
      ```

    - Link Java 21 to Your System (not necessary as it to set it to default)
      ```bash
      sudo ln -sfn $(brew --prefix openjdk@21)/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk
      ```

    - Set JAVA_HOME Environment Variable after entering 1st line then enter 2nd line
      ```bash
      echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> ~/.zshrc
      
      source ~/.zshrc
      ```

    - Verify Installation
      ```bash
      java -version
      ```

## Cloning repository

- Redirect to the directory you widh to clone the project via terminal
    ```bash
    cd /path/to/your/directory
    ```

- cloning repository via SSH
    ```bash
    git clone https://github.com/vaibhavGupta-13/automation-framework-sdet.git
    ```

- Enter Passphrase when asked

- Build the Project
    ```bash 
    ./gradlew clean build
    ```

## Run automation Test Locally

- To run web automation on the local machine
    - Set environment variable for mac
      ```bash
      export PRODUCT=amazon and export ENVIRONMENT=production and export TAG=@test13 and export PLATFORM=web and export IS_CI=false and export IS_LAMBDATEST=false
      ```

    - To run
      ```bash
      ./gradlew clean run
      ```

- Below is the explanation for above command line
    - Setting Variable for PRODUCT ie. amazon|| flipkart
      ```bash
      export PRODUCT=amazon
      ```

    - Setting Variable for ENVIRONMENT ie. production||preprod
      ```bash
      export ENVIRONMENT=production
      ```

    - Setting Variable for PLATFORM ie. web||mweb||android||ios
      ```bash
      export PLATFORM=web
      ```

    - Setting Variable for TAG to run specific scenario
      ```bash
      export TAG=@test13
      ```

## Android setUp

- Install Appium
    - Install node
        ```bash
        brew install node
        ```

    - Verify that npm was installed correctly
        ```bash
        npm -v
        ```

    - Install Appium via npm
        ```bash
        npm install -g appium
        ```

    - Verify the installation
        ```bash
        appium -v   
        ```

    - To check if you have all the required dependencies installed
        ```bash
        npm install -g appium-doctor
        appium-doctor
        ```

    - Start Appium Server by running
        ```bash
        appium
        ```

    - Install Appium Drivers specifically UiAutomator2
        ```bash
         appium driver install uiautomator2
        ``` 

    - Update Appium Drivers specifically UiAutomator2
      ```bash
       appium driver update uiautomator2
      ```

    - To fetch list of devices when connect physically or virtually
        - Install only once
            ```bash
            brew install android-platform-tools
            ```
        - Now only hit below command
            ```bash
            adb devices
            ```

        - Install [Android Studio](https://developer.android.com/studio)
            - Create a vitual device
                - When Android studio is welcome app is open
                - Then click on More Actions dropdown
                - And click on Virtual Device Manager
                - Create a new device by selecting android version
                - Add the commands in terminal for file zshrc
                  ```bash
                     nano ~/.zshrc
                    ```
                - Add ANDROID_SDK_ROOT path
                    ```bash
                     export ANDROID_SDK_ROOT=/Users/honasa/Library/Android/sdk
                     export PATH=$PATH:$ANDROID_SDK_ROOT/emulator
                     export PATH=$PATH:$ANDROID_SDK_ROOT/platform-tools
                    ```
                - Source the file
                  ```bash
                     source ~/.zshrc
                    ```

- Install [Appium Inspector](https://github.com/appium/appium-inspector/releases) with arm64.dmg for mac
    - Set Appium server URL
        ```bash 
        127.0.0.1
        ```
    - Set Appium port To
        ```bash
        4723
        ```
- To Fetch appPackage and appActivity for Android
    ```bash
    adb shell dumpsys window | grep -E "mCurrentFocus|mFocusedApp"
    ``` 
  You'll get something like below
    ```bash
    mCurrentFocus=Window{d61d214 u0 com.drsheths.app.android/com.drsheths.app.android.MainActivity}
    mFocusedApp=ActivityRecord{bf570f5 u0 com.drsheths.app.android/.MainActivity t10}
    ```
  Then you can see
    - **appPackage:** com.drsheths.app.android
    - **appActivity:** com.drsheths.app.android.MainActivity

- Capability which can be added in JSON Representation
    ```bash
    {
    "appium:automationName": "UiAutomator2",
    "appium:platformName": "Android",
    "appium:platformVersion": "13",
    "appium:deviceName": "Pixel_8_Pro",
    "appium:app": "path/to/your/apk",
    "appium:autoGrantPermissions": true
    }
    ```

- Start Two Appium Server Instances for each device
   ```bash
   appium -p 4723 --default-capabilities '{"udid": "emulator-5554"}'
   
   appium -p 4724 --default-capabilities '{"udid": "RZCX81ZWSHA"}'
   ```

## Android Device setUp

- Enable Developer Option
    - Go to "About device"
    - Find build number and tap for 6~7 times until developer option is enabled message has been prompted


- Developer Option settings
    - Enable "USB debugging"
    - Enable "Disable permission monitoring"

## iOS setUp

- Install [Xcode](https://developer.apple.com/xcode/)
    - Find location for webDriverAgent
        ```bash
        find . -name "appium-webdriveragent" 
        ```
      You'll get app path ending with appium-webdriveragent
        ```bash
        ./.appium/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent
        ```

    - open file via Xcode and then click on WebDriverAgent.xcodeproj
        ```bash
        cd ./.appium/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent
        open WebDriverAgent.xcodeproj
        ```

    - click on WebDriverAgent and then select WebDriverAgentRunner
        - Select Signing & Capabilities and check if developer name is set in Team section
            - if not then go [Apple Developer](https://developer.apple.com) and create an account
            - In Bundle Identifier give a unique string eg.
                ```bash 
                com.facebook141.WebDriverAgentRunner
                ```

- To get UDID for iPhone devices
    - Window > Devices & Simulators (⌘ ⇧ 2) > Select device > Identifier

- Capabilities eg.
    ```bash 
    {
    "appium:automationName": "XCUITest",
    "appium:platformName": "iOS",
    "appium:udid": "998838773-123344565676778",
    "appium:bundleId": "com.thedermacoapp",
    "appium:platformVersion": "18.0.1"
    }
    ```
- When connecting physical device
    - Right click on Appium Server GUI > Contents > Resources > App > node_modules > appium > node_modules >
      Appium-webdriver agent
    - Check for WebDriverAgent.XCodeProj
    - Open WebDriverAgent.XCodeProj in XCode
    - If project is not open click on folder icon present on top right corner. Click on WebDriverAgent
    - In centre panel > TARGETS Select WebDriverAgentRunner
    - Click on Signing and Capabilities
    - Click on "Team" Drop down to sign in your app
    - You can select existing certificate or new certificate where you have to apple certificate
    - Find WebDriverAgent project on top.
    - select WebDriverAgentRunner from drop down
    - Select targeted device from drop down on which you want to execute test cases
    - Click on "Product" menu
    - Click on "Test"
    - It will install WebDriverAgent on you targeted device
    - Now you can try to launch new session from appium inspector

- To list virtual iOS devices
    ```bash
     xcrun simctl list
    ```

- To list physical device
    - Instal only once
        ```bash
        brew install libimobiledevice
        ```
    - Hit below command to fetch UDID
        ```bash
        idevice_id -l
        ```
    - To Fetch bundleId for iOS
        - Navigate to the directory where your file is
            ```bash
            cd /Users/honasa/Desktop/honasa-automation-common/src/test/java/com/honasa/apk/drs/
            ``` 
        - Run the following commands
            ```bash
            # 1. Unzip the .ipa file
            unzip -q DRS_iOS.ipa -d drs_ios_unzipped
  
            # 2. Find the Info.plist file
            cd drs_ios_unzipped/Payload/*.app/
  
            # 3. Extract the bundle ID
            /usr/libexec/PlistBuddy -c "Print CFBundleIdentifier" Info.plist
          ```
        - Navigate to the directory where your file is
          ```bash
          com.drshethsapp
          ```
        - Cleanup:-you can remove the unzipped folder
          ```bash
          cd ../../..
          rm -rf drs_ios_unzipped
          ```


## Documentation

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Appium Documentation](https://appium.io/docs/en/latest/)
    - [intro](https://appium.io/docs/en/latest/intro/)
    - [ecosystem](https://appium.io/docs/en/latest/ecosystem/)
- [Xcode Documentation](https://developer.apple.com/documentation)
    - [devices-and-simulator](https://developer.apple.com/documentation/xcode/devices-and-simulator)
    - [debugging](https://developer.apple.com/documentation/xcode/debugging)
- [Xcode Help](https://help.apple.com/xcode/mac/current/)
- [Xpath Axes](https://www.w3schools.com/xml/xpath_axes.asp)
- [Android](https://developer.android.com/training/testing)