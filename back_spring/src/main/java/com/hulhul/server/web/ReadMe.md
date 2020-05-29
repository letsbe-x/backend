
## Spring 2.x Redirect
String -> RedirectView
```java
@RequestMapping("/api")
public RedirectView swagger() {
	return new RedirectView("/swagger-ui.html");
}
```


### ERROR
```java
```

Rest-data와 Swagger-ui 2.9.2 버전 호환 안되는 경우 

