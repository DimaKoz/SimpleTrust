### 1. Add jitpack to your root build.gradle
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

### 3.1 Create the SimpleTrust object
```Java
SimpleTrust simpleTrust = new SimpleTrust();
simpleTrust.addTrusted("your-trusted-domain.com");
simpleTrust.load();
```

### 3.2 Create the SimpleTrust object
```Java
SimpleTrust simpleTrust = new SimpleTrust();
simpleTrust.addTrusted(new String[]{
  "your-trusted-domain.com",
  "your-second-trusted.com"
});
simpleTrust.load();
```

### 3.3 Create the SimpleTrust object
```Java
SimpleTrust simpleTrust = new SimpleTrust("your-trusted-domain.com");
simpleTrust.load();
```

### 3.4 Create the SimpleTrust object
```Java
SimpleTrust simpleTrust = new SimpleTrust(new String[]{
  "your-trusted-domain.com",
  "your-second-trusted.com"
});
simpleTrust.load();
```

### 4. Profit!

## Other Methods
```Java
SimpleTrust simpleTrust = new SimpleTrust();
...
```

### Set the host name check mode
```Java
simpleTrust.setMode(SimpleTrust.EQUAL_MODE);
```
Or
```Java
simpleTrust.setMode(SimpleTrust.CONTAIN_MODE);
```

### Reset the your TrustManager
```Java
simpleTrust.reset();
```

### Get the hostname verifier
(can be null)
```Java
HostnameVerifier hostnameVerifier = simpleTrust.getHostnameVerifier();
```

### Get the trust manager
(can be null)
```Java
TrustManager[] trustManager = simpleTrust.getTrustManagers();
```
