

## 컨트롤러에서는 엔티티를 절대 직접 반환하지 말자.

1 - Entity -> JSON 시 무한루프 걸린다.

2 - Entity는 충분히 변경의 여지가 있는데, 엔티티 변경시 API 스펙 자체가 변경된다.

실무에서 웬만하면 DTO로 변환해서 반환하자.
그렇게 하면 JSON 생성 라이브러리 때문에 생기는 문제는 없다.


## Spring 2.x Redirect
String -> RedirectView
```java
@RequestMapping("/api")
public RedirectView swagger() {
	return new RedirectView("/swagger-ui.html");
}
```

## Entity 양방향 매핑으로 인한 순환참조
User-Post 양방향으로 인한 List 호출시 무한루프
> fasterxml.jackson.databind.JsonMappingException: Infinite recursion (StackOverflowError) (through reference chain: com.hulhul.server.domain.post.Post[\"user\"]->com.hulhul.server.domain.user.User$HibernateProxy$kdzAaJ39[\"posts\"]->org.hibernate.collection.internal.PersistentBag[0]->com.hulhul.server.domain.post.Post[\"user\"]->com.hulhul.server.domain.user.User$HibernateProxy$kdzAaJ39[\"posts\"]->org.hibernate.collection.internal.PersistentBag[0]->com.hulhul.server.domain.post.Post[\"user\"]-

**해결방법**
1. @JsonIgnore : 이 어노테이션을 붙이면 json 데이터에 해당 프로퍼티는 null로 들어가게 된다. 즉, 데이터에 아예 포함이 안되게 된다.

2. @JsonManagedReference와 @JsonBackReference : 이 두개의 어노테이션이야말로 순환참조를 방어하기 위한 Annotation이다. 부모 클래스 (User entity)에 @JsonManagedReference를, 자식 클래스측(Account entity)에 @JsonBackReference 어노테이션을 추가해주면 된다.

3. DTO 사용 : 위와 같은 상황이 발생하게 된 주 원인은 '양방향 맵핑'이기도 하지만, 더 정확하게는 entity 자체를 response로 리턴한데에 있다. entity 자체를 return 하지 말고, dto 객체를 만들어 필요한 데이터만 옮겨담아 client로 리턴하면 순환참조와 관련된 문제는 애초에 방어할수 있다.

4. 맵핑 재설정 : 사람마다 다르지만 양방향 맵핑이 꼭 필요한지 다시 한번 생각해볼 필요가 있다. 만약 양쪽에서 접근 할 필요가 없다면 단방향 맵핑을 하면 자연스레 순환참조가 해결된다.

---
ref : https://stackoverflow.com/questions/37392733/difference-between-jsonignore-and-jsonbackreference-jsonmanagedreference/37393711#37393711

### ERROR
```java
```

Rest-data와 Swagger-ui 2.9.2 버전 호환 안되는 경우



```java
org.springframework.dao.InvalidDataAccessApiUsageException: detached entity passed to persist: com.hulhul.server.domain.category.Category; nested exception is org.hibernate.PersistentObjectException: detached entity passed to persist: com.hulhul.server.domain.category.Category 	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:319) 
```

CascadeType.ALL을 CascadeType.MERGED나 CascadeType.DETACH로 바꿔준다.

생명주기 확인해볼것 : https://blog.woniper.net/266



