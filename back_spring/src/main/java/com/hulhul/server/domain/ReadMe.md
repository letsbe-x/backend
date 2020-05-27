
## @repository
> JpaRepository의 @Repository를 안붙여도 되는 이유

JpaRepository의 기본 구현체인 SimpleJpaRepository가 가지고 있기때문에, 중복선언 할 필요가 없습니다!


## JPA / JPA REPO


## JPA N:1관계에서 FK는 N에 있어야 맞는 설계

## 에러목록
> 	ERROR : LazyInitializationException: could not initialize proxy – no Session
P | Mysql에는 정상적으로 들어왔으나 안되는 문제 :

Fetch.LAZY

영속성 컨텍스트를 벗어난 JPA 프록시 객체 -> //LAZY 문제;;; User가 안만들어졌네;;!
 
![ref](https://bebong.tistory.com/entry/JPA-Lazy-Evaluation-LazyInitializationException-could-not-initialize-proxy-%E2%80%93-no-Session)
