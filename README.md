
[![Hackage-Deps](https://img.shields.io/hackage-deps/v/lens.svg?style=for-the-badge)](https://github.com/m-devs/SimpleTrust/blob/master/simpletrust/build.gradle)
[![](https://jitpack.io/v/m-devs/SimpleTrust.svg?style=for-the-badge)](https://jitpack.io/#m-devs/SimpleTrust)

# SimpleTrust
A simple library for trusting self signed domains.

## Using SimpleTrust
SimpleTrust can be obtained from JitPack. It's also possible to clone the repository and depend on the modules locally.

## Developer Guide

### 1. Add JitPack to your root build.gradle
```Gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### 2. Add the dependency
```Gradle
	dependencies {
	        implementation 'com.github.m-devs:SimpleTrust:1.0.0'
	}
```

### 3. Create the SimpleTrust object
```Java
SimpleTrust simpleTrust = new SimpleTrust();
simpleTrust.addTrusted("your-trusted-domain.com");
simpleTrust.load();
```

### 4. Profit!

## Information
Google Play Store wont allow you to upload your application if you use `TrustManager` improperly. Make sure you wont add empty strings to your trusted domains array. Especially if you are using the `CONTAIN_MODE` in your `SimpleTrust` object.

## Requirement
```Gradle
...
defaultConfig {
        minSdkVersion 16
	...
}
...
```

## For alternative usage
You can find a more detailed guide [here](./Guide.md)
